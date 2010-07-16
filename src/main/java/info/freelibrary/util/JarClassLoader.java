/**
 * 
 */
package info.freelibrary.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class JarClassLoader extends URLClassLoader {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JarClassLoader.class);

	private static final ResourceBundle RB = ResourceBundle.getBundle(
			"Messages", new XMLBundleControl());

	private static final String CLASSPATH = "java.class.path";

	private static final String MAIN_JAR = System.getProperty(CLASSPATH);

	private static final String PATH = System.getProperty("user.home") + "/";

	private static final String HOME = System.getProperty("user.dir") + "/";

	private static final String JAR_URL_PROTOCOL = "jar:file://";

	public JarClassLoader(String aMainClassName) throws Exception {
		super(getJarURLs());

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(RB.getString("jarClassLoader.init"), aMainClassName);
		}

		loadClass(aMainClassName).newInstance();
	}

	public JarClassLoader(URL[] aURLs, String aMainClassName) throws Exception {
		super(aURLs);

		loadClass(aMainClassName).newInstance();
	}

	public JarClassLoader(List<URL> aListOfURLs, String aMainClassName)
			throws Exception {
		super(aListOfURLs.toArray(new URL[aListOfURLs.size()]));

		loadClass(aMainClassName).newInstance();
	}

	public Class<?> loadClass(String aName) throws ClassNotFoundException {
		Class<?> loadedClass = findLoadedClass(aName);

		if (loadedClass == null) {
			try {
				loadedClass = findClass(aName);
			}
			catch (ClassNotFoundException details) {
			}

			if (loadedClass == null) {
				loadedClass = super.loadClass(aName);
			}
		}

		return loadedClass;
	}

	private static URL[] getJarURLs() throws IOException {
		JarFile jarFile = new JarFile(MAIN_JAR);
		Manifest manifest = jarFile.getManifest();
		Attributes attributes = manifest.getMainAttributes();
		String classpath = attributes.getValue("Class-Path");
		StringTokenizer tokenizer = new StringTokenizer(classpath);
		List<URL> urlList = new LinkedList<URL>();

		urlList.add(new URL(JAR_URL_PROTOCOL + HOME + MAIN_JAR + "!/"));

		while (tokenizer.hasMoreTokens()) {
			String jarPath = tokenizer.nextToken();
			urlList.add(new URL(JAR_URL_PROTOCOL + PATH + jarPath + "!/"));
		}

		return urlList.toArray(new URL[urlList.size()]);
	}
}
