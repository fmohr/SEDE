from typing import IO
import logging
import io
from http import server
import requests
import re
import os

def is_bytes(something)->bool:
    return isinstance(something, (bytes, bytearray))

def is_str(something)-> bool:
    return isinstance(something, str)


def out_write_string(wfile:IO, payload:str, *args, **kwargs) -> None:
    if not is_str(payload):
        raise ValueError("Unexpected type: " + str(payload.__class__) + ". Expected string.")
    out_write_bytes(wfile, payload.encode(), *args, **kwargs)

def out_write_bytes(wfile: IO, payload, close: bool = True) -> None:
    if not is_bytes(payload):
        raise ValueError("Unexpected type: " + str(payload.__class__) + ". Expected bytes-like.")
    wfile.write(payload)
    if close:
        wfile.close()

def out_write(wfile:IO, payload, *args, **kwargs) -> None:
    if is_str(payload):
        out_write_string(wfile, payload, *args, **kwargs)
    elif is_bytes(payload):
        out_write_bytes(wfile, payload, *args, **kwargs)
    else:
        raise ValueError("Unexpected type: " + str(payload.__class__))

def in_read(rfile: IO, content_length = None, close: bool = True):
    if content_length is None:
        content = rfile.read()
    else:
        content = rfile.read(content_length)
    if close:
        rfile.close()
    return content

def in_read_string(*args, **kwargs) -> str:
    content = in_read(*args, **kwargs)
    if is_str(content):
        return content
    elif is_bytes(content):
        return content.decode()
    else:
        raise ValueError("Unexpected type: " + str(content.__class__) + ". Expected string.")

def in_read_bytes(*args, **kwargs) -> bytes:
    content = in_read(*args, **kwargs)
    if is_str(content):
        return content.encode()
    elif is_bytes(content):
        return content
    else:
        raise ValueError("Unexpected type: " + str(content.__class__) + ". Expected bytes-like.")

class BasicClientRequest(object):

    def write_bytes(self, bytes_payload:bytes, *args, **kwargs):
        out_write(self.send(), bytes_payload, *args, **kwargs)

    def send(self) -> bytes:
        return io.BytesIO()

    def receive(self) -> bytes:
        return io.BytesIO()

    def __enter__(self):
        return self

    def __exit__(self, exc_type, exc_value, traceback):
        pass

    def send_receive_str(self, payload: str = None) -> str:
        with(self):
            if payload is not None:
                out_write(self.send(), payload, close=False)
            return in_read_string(self.receive())

    def send_receive_bytes(self, payload = None) -> str:
        with(self):
            if payload is not None:
                out_write(self.send(), payload, close=False)
            return in_read_bytes(self.receive())



class ReadFileRequest(BasicClientRequest):
    filepath : str
    def __init__(self, filepath:str):
        self.filepath = filepath
    
    def receive(self) -> IO:
        self.filep = open(self.filepath, "rb")
        return self.filep

    def __exit__(self, exc_type, exc_value, traceback):
        try:
            self.filep.close()
        except Exception as e:
            logging.error("Error closing file{} : {}".format(self.filepath, e))
            pass

class WriteFileRequest(BasicClientRequest):
    filepath : str
    def __init__(self, filepath:str):
        self.filepath = filepath

    def send(self) -> IO:
        directory = os.path.dirname(self.filepath)
        if not os.path.exists(directory):
            os.makedirs(directory)
        self.filep = open(self.filepath, "wb")
        return self.filep

    def __exit__(self, exc_type, exc_value, traceback):
        try:
            self.filep.close()
        except Exception as e:
            logging.error("Error closing file{} : {}".format(self.filepath, e))
            pass

class BasicServerResponse(object):
    def receive(self, inputstream: IO, outputstream: IO, **kwargs):
        pass

class FileServerResoponse(BasicServerResponse):
    def receive(self, inputstream: IO, outputstream: IO, **kwargs):
        filepath = in_read_string(inputstream)
        with ReadFileRequest(filepath) as filer:
            filecontent = filer.send_receive_str()
        out_write_string(outputstream, filecontent)

class ByteServerResponse(BasicServerResponse):
    def __init__(self, response_function: callable = None):
        if response_function is not None:
            self.receive_bytes = response_function

    def receive(self, inputstream: IO, outputstream: IO, closeinput = True, content_length = None, closeoutput = False, **kwargs):
        input_bytes = in_read_bytes(inputstream, close=closeinput, content_length=content_length)
        server_out = self.receive_bytes(input_bytes, **kwargs)
        out_write(outputstream, server_out, close=closeoutput)

    def receive_bytes(self, input_bytes: bytes, **kwargs) -> str:
        pass

class StringServerResponse(BasicServerResponse):
    def __init__(self, response_function: callable = None):
        if response_function is not None:
            self.receive_str = response_function

    def receive(self, inputstream: IO, outputstream: IO, closeinput = True, content_length = None, closeoutput = False, **kwargs):
        input_string = in_read_string(inputstream, close=closeinput, content_length=content_length)
        server_out = self.receive_str(input_string, **kwargs)
        out_write_string(outputstream, server_out, close=closeoutput)

    def receive_str(self, input_string: str, **kwargs) -> str:
        pass


class HttpClientRequest(BasicClientRequest):
    url:str
    payload:io.BytesIO
    method_is_post: bool = True
    response = None

    def __init__(self, url:str):
        if url.startswith("http://"):
            self.url = url
        else:
            self.url = "http://" + url

        self.payload = io.BytesIO()

    def write_bytes(self, bytes_payload: bytes, *args, **kwargs):
        if is_bytes(self.payload):
            self.payload: bytes
            self.payload += bytes_payload
        else:
            self.payload = bytes_payload

    def send(self) -> IO:
        if is_bytes(self.payload):
            raise("After writing bytes dont use send again.")
        else:
            return self.payload

    def receive(self) -> IO:
        return io.BytesIO(self._response_body())
        
    def _response_body(self) -> bytes:
        if self.method_is_post:
            if is_bytes(self.payload):
                body = self.payload
            else:
                body = self.payload.getvalue()

            self.response = requests.request("POST", self.url, data=body)
        else:
            self.response = requests.request("GET", self.url) 
        return self.response.content
        
    def __exit__(self, exc_type, exc_value, traceback):
        if self.response is not None:
            try:
                self.response.close()
            except Exception:
                pass

class MultiContextHandler(object):
    context_handlers : list
    def __init__(self):
        self.context_handlers = list()

    def add_context(self, context_pattern: str, responder: callable, first=False) -> None:
        context_prog = re.compile(context_pattern)
        if first:
            self.context_handlers.insert(0, (context_prog, responder))
        else:
            self.context_handlers.append((context_prog, responder))

    class Handler(server.BaseHTTPRequestHandler):
        def __init__(self, context_handlers, *args, **kwargs):
            self.context_handlers = context_handlers
            # print("Context handler is {} big.".format(len(self.context_handlers)))
            super().__init__(*args, **kwargs)

        def do_POST(self):
            content_length = int(self.headers.get('Content-Length'))
            body = in_read_bytes(self.rfile, content_length=content_length)
            self.serve(body)

        def do_GET(self):
            self.serve(bytes())

        def serve(self, input_bytes):
            for context, responder in self.context_handlers:
                matching = context.match(self.path)
                if matching:
                    url_inputs = matching.groupdict()

                    if callable(responder):
                        request_responder: BasicServerResponse = responder()
                    else:
                        request_responder: BasicServerResponse = responder
                    self.send_response(200)
                    self.send_header("Content-type", "text/html")
                    self.end_headers()
                    if isinstance(request_responder, ByteServerResponse):
                        server_out = request_responder.receive_bytes(input_bytes, **url_inputs)
                        out_write(self.wfile, server_out, close=False)
                    elif isinstance(request_responder, StringServerResponse):
                        server_out = request_responder.receive_str(input_bytes.decode(), **url_inputs)
                        out_write(self.wfile, server_out, close=False)
                    else:
                        inputstream = io.BytesIO(input_bytes)
                        # Change the close method of wfile in order to prevent 'close' being called.
                        old_close = self.wfile.close
                        self.wfile.close = lambda : None
                        request_responder.receive(inputstream=inputstream, outputstream=self.wfile, closeinput=False, closeoutput=False, **url_inputs)
                        self.wfile.close = old_close
                    self.flush_headers()
                    return

            self.send_response(404)
            self.send_header("Content-type", "text/html")
            self.end_headers()
            self.wfile.write(("You accessed path: %s. No matching context found."% self.path).encode())

    def __call__(self, *args, **kwargs):
        return MultiContextHandler.Handler(self.context_handlers, *args, **kwargs)


class BasicServerTest(object):
    def __init__(self):
        handler = MultiContextHandler()
        helloResponse = StringServerResponse(lambda hello, **kwargs: "Hello to u too! You said '{}'.".format(hello))
        handler.add_context("/hallo", helloResponse)
        nameResponse = StringServerResponse(lambda hello, **kwargs: "Hello to u, {}! You said '{}'.".format(kwargs["name"],hello))
        handler.add_context("/hallo/(?P<name>[A-Za-z]+)", nameResponse, first=True)
        httpserver = server.HTTPServer(("", 8080), handler)
        try:
            httpserver.serve_forever()
        except KeyboardInterrupt:
            httpserver.shutdown()

# BasicServerTest()