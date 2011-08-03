/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */
package info.freelibrary.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

public class XMLResourceBundle extends ResourceBundle {

	private Properties myProperties;

	XMLResourceBundle(InputStream stream) throws IOException {
		myProperties = new Properties();
		myProperties.loadFromXML(stream);
	}

	protected Object handleGetObject(String key) {
		return myProperties.getProperty(key);
	}

	public Enumeration<String> getKeys() {
		final Enumeration<?> enumeration = myProperties.elements();

		return new Enumeration<String>() {

			public boolean hasMoreElements() {
				return enumeration.hasMoreElements();
			}

			public String nextElement() {
				return (String) enumeration.nextElement();
			}
		};
	}

	public String get(String aMessage, String[] aDetailsArray) {
		return StringUtils.formatMessage(super.getString(aMessage),
				aDetailsArray);
	}

	public String get(String aMessage, String aDetail) {
		return StringUtils.formatMessage(super.getString(aMessage),
				new String[] { aDetail });
	}
	
	public String get(String aMessage) {
		return getString(aMessage);
	}
	
	public String get(String aMessage, File[] aFileArray) {
		String[] strings = new String[aFileArray.length];
		
		for (int index = 0; index < strings.length; index++) {
			strings[index] = aFileArray[index].getAbsolutePath();
		}
		
		return StringUtils.formatMessage(super.getString(aMessage), strings);
	}
	
	public String get(String aMessage, File aFile) {
		return StringUtils.formatMessage(super.getString(aMessage),
				new String[] { aFile.getAbsolutePath() });
	}
	
	public String get(File aFile) {
		return getString(aFile.getAbsolutePath());
	}
	
	public String get(String aMessage, Object[] aDetailsArray) {
		String[] strings = new String[aDetailsArray.length];
		
		for (int index = 0; index < strings.length; index++) {
			strings[index] = aDetailsArray[index].toString();
		}
		
		return StringUtils.formatMessage(super.getString(aMessage), strings);
	}
	
	public String get(String aMessage, Object aDetail) {
		return StringUtils.formatMessage(super.getString(aMessage),
				new String[] { aDetail.toString() });
	}
	
	public String get(Object aObject) {
		return getString(aObject.toString());
	}
}
