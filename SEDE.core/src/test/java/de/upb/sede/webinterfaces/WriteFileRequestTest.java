package de.upb.sede.webinterfaces;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

import de.upb.sede.webinterfaces.client.WriteFileRequest;

public class WriteFileRequestTest {
	@Test
	public void writeBasicFile() throws IOException {
		String filePath = "testrsc/temp/a.txt";
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
		WriteFileRequest writeFile = new WriteFileRequest(filePath, "ok");
		String answer = writeFile.send("Hello, filesystem!");

		String testRead = "";
		try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
			testRead = stream.collect(Collectors.joining());
		}
		Assert.assertEquals("Hello, filesystem!", testRead);
		Assert.assertEquals("ok", answer);
	}
}
