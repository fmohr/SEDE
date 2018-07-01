from typing import IO
import logging
import io
from http import server
import requests
import re

def out_write_string(wfile: IO, payload: str, close: bool = True) -> None:
    wfile.write(payload.encode())
    if close:
        wfile.close()

def in_read_string(rfile: IO, close: bool = True) -> str:
    content = rfile.read()
    if close:
        rfile.close()
    if isinstance(content, str):
        return content
    else:
        return content.decode()


class BasicClientRequest(object):

    def send(self) -> IO:
        return io.BytesIO()

    def receive(self) -> IO:
        return io.BytesIO()

    def __enter__(self):
        pass 

    def __exit__(self, exc_type, exc_value, traceback):
        pass

    def send_receive(self, payload: str = None) -> str:
        with(self):
            if payload is not None:
                out_write_string(self.send(), payload)
            return in_read_string(self.receive())

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
        filecontent = ReadFileRequest(filepath).send_receive()
        out_write_string(outputstream, filecontent)

class StringServerResponse(BasicServerResponse):
    def receive(self, inputstream: IO, outputstream: IO, **kwargs):
        input_string = in_read_string(inputstream)
        server_out = self.receive_str(input_string, **kwargs)
        out_write_string(outputstream, server_out)

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

    def send(self) -> IO:
        return self.payload

    def receive(self) -> IO:
        return io.BytesIO(self._response_body())
        
    def _response_body(self) -> bytes:
        if self.method_is_post:
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
    def __init__(self, port: int):
        self.context_handlers = list()

    def add_context(self, context_pattern: str, responder: callable) -> None:
        context_prog = re.compile(context_pattern)
        self.context_handlers.append((context_prog, responder))

        
    class Handler(server.BaseHTTPRequestHandler):
        def __init__(self, context_handlers, *args, **kwargs):
            super.__init__(*args, **kwargs)
            self.context_handlers = context_handlers
        def do_GET(self):
            for context, responder in self.context_handlers:
                matching = context.match(self.path)
                if matching:
                    url_inputs = matching.groupdict()
                    request_responder : BasicServerResponse = responder()
                    self.send_header("Content-type", "text/html")
                    self.end_headers()
                    self.send_response(200)
                    request_responder.receive(inputstream=self.rfile, outputstream=self.wfile, **url_inputs)
                    return

            self.send_response(404)
            self.send_header("Content-type", "text/html")
            self.end_headers()
            self.wfile.write(("You accessed path: %s. No matching context found."% self.path).encode())

    def __call__(self, *args, **kwargs):
        return MultiContextHandler.Handler(self.context_handlers, *args, **kwargs)
