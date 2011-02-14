package info.freelibrary.util;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileUtilsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {}

	@Test
	public void testGetSize() {
		assertEquals(41787,
				FileUtils.getSize(new File("src/test/resources/test_folder")));
	}

	@Test
	public void testSizeFromBytes() {
		assertEquals("40 kilobytes", FileUtils.sizeFromBytes(41787));
	}

}
