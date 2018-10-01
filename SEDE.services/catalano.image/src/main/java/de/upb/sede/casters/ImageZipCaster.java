package de.upb.sede.casters;

import org.apache.commons.io.IOUtils;
import org.json.simple.parser.ParseException;

import java.io.*;

public class ImageZipCaster {

	public InputStream cfs_ImageZip(InputStream is) {
		return is;
	}

	public void cts_ImageZip(OutputStream os, InputStream is) throws IOException {
		IOUtils.copy(is, os);
	}
}
