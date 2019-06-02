package de.upb.sede.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Streams {

	/**
	 * Reads the content in the given input stream chunkwise and fills it into a byte array output stream.
	 */
	public static ExtendedByteArrayOutputStream InReadChunked(InputStream is) {

		try (InputStream inputStream = is) {

			ExtendedByteArrayOutputStream result = new ExtendedByteArrayOutputStream(1024);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) != -1) {
				result.write(buffer, 0, length);
			}
			return result;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static List<String> InReadLines(InputStream is) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            List<String> lines = new ArrayList<>();
            String line;
            while((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

	/**
	 * Reads the content in the given input stream and fills it into a byte array.
	 */
	public static byte[] InReadByteArr(InputStream is) {
		return InReadChunked(is).toByteArray();
	}

	/**
	 * Reads the content in the given input stream and converts it to a string.
	 */
	public static String InReadString(InputStream is) {
		try {
			return InReadChunked(is).toString(StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Writes the given content into the given outputstream. if close is true,
	 * closes the stream afterwards.
	 */
	public static void OutWriteString(OutputStream outstream, String content, boolean close) {

		try {
			outstream.write(content.getBytes(StandardCharsets.UTF_8.name()));
			if (close) {
				outstream.close();
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Reads a line of characters out of the given input stream.
	 * @return the read string
	 */
	public static String InReadLine(InputStream in) {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		try {
			return br.readLine();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Returns the stacktrace of the given exception as a String.
	 * @param ex Exception whose stack trace will be returned.
	 * @return Stack trace of the given exception.
	 */
	public static String ErrToString(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String sStackTrace = sw.toString();
		return sStackTrace;
	}

	/**
	 * Returns an inputstream which is empty.
	 * Using read() will return -1;
	 * @return empty input stream.
	 */
	public static InputStream EmptyInStream(){
		return new EmptyIn();
	}

    private static class EmptyIn extends InputStream {

		@Override
		public int read() throws IOException {
			return -1; // end of stream.
		}
	}

	/**
	 * Returns an output stream. Writing into the given output stream will discard the the input.
	 * write method of the stream doesn't do anything.
	 *
	 * @return outputstream which discards the input.
	 */
	public static OutputStream DiscardOutStream() {
		return new DiscardingOut();
	}

	private static class DiscardingOut extends OutputStream {
		@Override
		public void write(int b) throws IOException {
			// do nothing..
		}
		@Override
		public void write(byte b[], int off, int len) throws IOException {
			if (b == null) {
				throw new NullPointerException();
			}
			// do nothing..
		}

	}



    public static OutputStream safeSystemErr() {
        return new FilterOutputStream(System.err) {
            @Override
            public void close() throws IOException {
                // Ignore
            }
        };
    }

    public static OutputStream safeSystemOut() {
        return new FilterOutputStream(System.out) {
            @Override
            public void close() throws IOException {
                // Ignore
            }
        };
    }

    /**
     * An {@code InputStream} which reads from the source {@code InputStream}. In addition, when the {@code InputStream} is
     * closed, all threads blocked reading from the stream will receive an end-of-stream.
     */
    public static class GraciousCloseInputStream extends InputStream {
        private final Lock lock = new ReentrantLock();
        private final Condition condition = lock.newCondition();
        private final byte[] buffer;
        private int readPos;
        private int writePos;
        private boolean closed;
        private boolean inputFinished;


        public GraciousCloseInputStream(InputStream source) {
            this(source, 1024);
        }

        public GraciousCloseInputStream(final InputStream source, int bufferLength) {
            buffer = new byte[bufferLength];
            Runnable consume = new Runnable() {
                public void run() {
                    try {
                        while (true) {
                            int pos;
                            lock.lock();
                            try {
                                while (!closed && writePos == buffer.length && writePos != readPos) {
                                    // buffer is full, wait until it has been read
                                    condition.await();
                                }
                                assert writePos >= readPos;
                                if (closed) {
                                    // stream has been closed, don't bother reading anything else
                                    inputFinished = true;
                                    condition.signalAll();
                                    return;
                                }
                                if (readPos == writePos) {
                                    // buffer has been fully read, start at the beginning
                                    readPos = 0;
                                    writePos = 0;
                                }
                                pos = writePos;
                            } finally {
                                lock.unlock();
                            }

                            int nread = source.read(buffer, pos, buffer.length - pos);

                            lock.lock();
                            try {
                                if (nread > 0) {
                                    // Have read some data - let readers know
                                    assert writePos >= readPos;
                                    writePos += nread;
                                    assert buffer.length >= writePos;
                                    condition.signalAll();
                                }
                                if (nread < 0) {
                                    // End of the stream
                                    inputFinished = true;
                                    condition.signalAll();
                                    return;
                                }
                            } finally {
                                lock.unlock();
                            }
                        }
                    } catch (Throwable throwable) {
                        lock.lock();
                        try {
                            inputFinished = true;
                            condition.signalAll();
                        } finally {
                            lock.unlock();
                        }
                        throw Uncheck.throwAsUncheckedException(throwable);
                    }
                }
            };
            Thread thread = new Thread(consume);
            thread.setName("DisconnectableInputStream source reader");
            thread.setDaemon(true);
            thread.start();
        }

        @Override
        public int read(byte[] bytes, int pos, int count) throws IOException {
            lock.lock();
            try {
                while (!inputFinished && !closed && readPos == writePos) {
                    condition.await();
                }
                if (closed) {
                    return -1;
                }

                // Drain the buffer before returning end-of-stream
                if (writePos > readPos) {
                    int nread = Math.min(count, writePos - readPos);
                    System.arraycopy(buffer, readPos, bytes, pos, nread);
                    readPos += nread;
                    assert writePos >= readPos;
                    condition.signalAll();
                    return nread;
                }

                assert inputFinished;
                return -1;
            } catch (InterruptedException e) {
                throw Uncheck.throwAsUncheckedException(e);
            } finally {
                lock.unlock();
            }
        }

        @Override
        public int read() throws IOException {
            byte[] buffer = new byte[1];
            while (true) {
                int nread = read(buffer);
                if (nread < 0) {
                    return -1;
                }
                if (nread == 1) {
                    return 0xff & buffer[0];
                }
            }
        }

        /**
         * Closes this {@code InputStream} for reading. Any threads blocked in read() will receive an end-of-stream. Also requests that the
         * reader thread stop reading, if possible, but does not block waiting for this to happen.
         *
         * <p>NOTE: this method does not close the source input stream.</p>
         */
        @Override
        public void close() throws IOException {
            lock.lock();
            try {
                closed = true;
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }
}
