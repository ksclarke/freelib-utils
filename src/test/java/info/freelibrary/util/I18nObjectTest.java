package info.freelibrary.util;

import static org.junit.Assert.*;

import java.io.IOException;

import info.freelibrary.util.test.I18nObjectWrapper;

import org.junit.Test;

public class I18nObjectTest {

	@Test
	public void testGetI18nString() {
		assertEquals("Retrieving pairtree_root directory at {}",
				new I18nObjectWrapper().getI18n("pt.retrieving_root"));
	}

	@Test
	public void testGetI18Exception() {
		String exceptionMsg = "MY EXCEPTION";
		Exception exception = new IOException(exceptionMsg);
		I18nObjectWrapper test = new I18nObjectWrapper();

		assertEquals(exceptionMsg, test.getI18N("test.my.exception", exception));
	}

	@Test
	public void testGetI18nStringLong() {
		// fail("Not yet implemented");
	}

	@Test
	public void testGetI18nStringInt() {
		// fail("Not yet implemented");
	}

	@Test
	public void testGetI18nStringString() {
		// fail("Not yet implemented");
	}

	@Test
	public void testGetI18nStringStringArray() {
		// fail("Not yet implemented");
	}

	@Test
	public void testGetI18nStringFile() {
		// fail("Not yet implemented");
	}

	@Test
	public void testGetI18nStringFileArray() {
		// fail("Not yet implemented");
	}

	@Test
	public void testGetI18nStringObjectArray() {
		// fail("Not yet implemented");
	}

}
