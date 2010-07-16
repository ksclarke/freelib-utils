package info.freelibrary.util;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import junit.framework.TestCase;

public class XMLBundleControlTest extends TestCase {

	XMLBundleControl myControl;

	@Override
	protected void setUp() throws Exception {
		myControl = new XMLBundleControl();
	}

	public void testGetFormatsString() {
		boolean successful = false;

		assertEquals("xml", myControl.getFormats("yada").get(0));

		try {
			myControl.getFormats(null).get(0);
		}
		catch (NullPointerException details) {
			successful = true;
		}

		assertTrue(successful);
	}

	public void testNewBundleStringLocaleStringClassLoaderBoolean() {
		boolean successful = false;

		try {
			myControl.newBundle("aBaseName", Locale.getDefault(), "aFormat",
					getClass().getClassLoader(), true);
		}
		catch (NullPointerException details) {
			fail(details.getMessage());
		}
		catch (IOException details) {
			fail(details.getMessage());
		}
		catch (InstantiationException details) {
			fail(details.getMessage());
		}
		catch (IllegalAccessException details) {
			fail(details.getMessage());
		}

		try {
			myControl.newBundle(null, Locale.getDefault(), "aFormat",
					getClass().getClassLoader(), true);
		}
		catch (NullPointerException details) {
			successful = true;
		}
		catch (IOException details) {
			fail(details.getMessage());
		}
		catch (InstantiationException details) {
			fail(details.getMessage());
		}
		catch (IllegalAccessException details) {
			fail(details.getMessage());
		}

		assertTrue(successful);
		successful = false;

		try {
			myControl.newBundle("aBaseName", null, "aFormat", getClass()
					.getClassLoader(), true);
		}
		catch (NullPointerException details) {
			successful = true;
		}
		catch (IOException details) {
			fail(details.getMessage());
		}
		catch (InstantiationException details) {
			fail(details.getMessage());
		}
		catch (IllegalAccessException details) {
			fail(details.getMessage());
		}

		assertTrue(successful);
		successful = false;

		try {
			myControl.newBundle("aBaseName", Locale.getDefault(), null,
					getClass().getClassLoader(), true);
		}
		catch (NullPointerException details) {
			successful = true;
		}
		catch (IOException details) {
			fail(details.getMessage());
		}
		catch (InstantiationException details) {
			fail(details.getMessage());
		}
		catch (IllegalAccessException details) {
			fail(details.getMessage());
		}

		assertTrue(successful);
		successful = false;

		try {
			myControl.newBundle("aBaseName", Locale.getDefault(), "aFormat",
					null, true);
		}
		catch (NullPointerException details) {
			successful = true;
		}
		catch (IOException details) {
			fail(details.getMessage());
		}
		catch (InstantiationException details) {
			fail(details.getMessage());
		}
		catch (IllegalAccessException details) {
			fail(details.getMessage());
		}

		assertTrue(successful);
	}

	public void testNewBundle() {
		ResourceBundle bundle = ResourceBundle
				.getBundle("Messages", myControl);
		// you can also put files in a structure info.freelibrary.util.Messages
		
		bundle.getString("jarClassLoader.init");
	}
}
