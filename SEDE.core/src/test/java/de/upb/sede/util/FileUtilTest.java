package de.upb.sede.util;

import org.junit.Assert;
import org.junit.Test;


public class FileUtilTest {
	@Test
	public void test_readFileAsString() {
		String testFilePath = "testrsc/FileUtil.readFileAsString_Testfile.txt";
		Assert.assertEquals("A\nB\nC\nD", FileUtil.readFileAsString(testFilePath));
	}
}
