package ai.services.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class FileUtilTest {
	@Test
	public void test_readFileAsString() {
		String testFilePath = FileUtil.getPathOfResource("ReadTest.txt");
		Assert.assertEquals("A\nB\nC\nD\n", FileUtil.readFileAsString(testFilePath));
	}

	@Test
	public void test_readResources() {
		String pathToResource = FileUtil.getPathOfResource(
				"json-schema/requests/DataPutRequest.schema.json");
		assertThat(pathToResource, is(not(nullValue())));
		assertThat(new File(pathToResource).exists(), is(true));
		String content = FileUtil.
				readResourceAsString("json-schema/requests/DataPutRequest.schema.json");
		String content1 = FileUtil.readFileAsString(pathToResource);
		// assertThat(content.trim(), is(equalTo(content1.trim())));
	}
	@Test
	public void test_readTestResources() {
		String pathToResource = FileUtil.getPathOfResource(
				"config/A-classconf.json");
		assertThat(pathToResource, is(not(nullValue())));
		assertThat(new File(pathToResource).exists(), is(true));
		String content = FileUtil.
				readResourceAsString("config/A-classconf.json");
		String content1 = FileUtil.readFileAsString(pathToResource);
		// assertThat(content, is(equalTo(content1)));
	}
}
