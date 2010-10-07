/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */
package info.freelibrary.util;

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
}
