package info.freelibrary.util;

import info.freelibrary.util.XMLBundleControl;
import info.freelibrary.util.XMLResourceBundle;

import java.io.File;
import java.util.ResourceBundle;

public abstract class I18nObject {

	private final XMLResourceBundle BUNDLE;

	public I18nObject() {
		BUNDLE = (XMLResourceBundle) ResourceBundle.getBundle("Messages",
				new XMLBundleControl());
	}

	public I18nObject(String aBundleName) {
		BUNDLE = (XMLResourceBundle) ResourceBundle.getBundle(aBundleName,
				new XMLBundleControl());
	}

	protected String getI18n(String aMessage) {
		return normalizeWS(BUNDLE.get(aMessage));
	}

	protected String getI18n(String aMessage, long aLongDetail) {
		return normalizeWS(BUNDLE.get(aMessage, Long.toString(aLongDetail)));
	}

	protected String getI18n(String aMessage, int aIntDetail) {
		return normalizeWS(BUNDLE.get(aMessage, Integer.toString(aIntDetail)));
	}

	protected String getI18n(String aMessage, String aDetail) {
		return normalizeWS(BUNDLE.get(aMessage, aDetail));
	}

	protected String getI18n(String aMessage, String[] aDetailsArray) {
		return normalizeWS(BUNDLE.get(aMessage, aDetailsArray));
	}

	protected String getI18N(String aMessage, Exception aException) {
		return normalizeWS(BUNDLE.get(aMessage, aException.getMessage()));
	}

	protected String getI18n(String aMessage, File aFile) {
		return normalizeWS(BUNDLE.get(aMessage, aFile.getAbsolutePath()));
	}

	protected String getI18n(String aMessage, File[] aFileArray) {
		String[] fileNames = new String[aFileArray.length];

		for (int index = 0; index < fileNames.length; index++) {
			fileNames[index] = aFileArray[index].getAbsolutePath();
		}

		return normalizeWS(BUNDLE.get(aMessage, fileNames));
	}

	protected String getI18n(String aMessage, Object[] aObjArray) {
		String[] strings = new String[aObjArray.length];

		for (int index = 0; index < aObjArray.length; index++) {
			if (aObjArray[index] instanceof File) {
				strings[index] = ((File) aObjArray[index]).getAbsolutePath();
			}
			else {
				strings[index] = aObjArray[index].toString();
			}
		}

		return normalizeWS(BUNDLE.get(aMessage, strings));
	}

	private String normalizeWS(String aMessage) {
		return aMessage.replaceAll("\\s+", " ");
	}
}
