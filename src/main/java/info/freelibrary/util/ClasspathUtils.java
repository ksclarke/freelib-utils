/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */
package info.freelibrary.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClasspathUtils {

	private static final String CLASSPATH = "java.class.path";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ClasspathUtils.class);

	public static String[] getDirs() {
		ArrayList<String> list = new ArrayList<String>();

		for (String filename : System.getProperty(CLASSPATH).split(":")) {
			File file = new File(filename);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Checking to see if {} is a dir ({})",
						new String[] { file.getAbsolutePath(),
								file.isDirectory() ? "yes" : "no" });
			}
			
			if (file.isDirectory()) {
				list.add(file.getAbsolutePath());
			}
		}

		return list.toArray(new String[list.size()]);
	}

	public static File[] getDirFiles() {
		ArrayList<File> list = new ArrayList<File>();

		for (String filename : System.getProperty(CLASSPATH).split(":")) {
			File file = new File(filename);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Checking to see if {} is a dir ({})",
						new String[] { file.getAbsolutePath(),
								file.isDirectory() ? "yes" : "no" });
			}
			
			if (file.isDirectory()) {
				list.add(file);
			}
		}

		return list.toArray(new File[list.size()]);
	}

	public static String[] getDirs(FilenameFilter aFilter) {
		ArrayList<String> list = new ArrayList<String>();

		for (String filename : System.getProperty(CLASSPATH).split(":")) {
			File file = new File(filename);

			if (aFilter.accept(file.getParentFile(), file.getName())
					&& file.isDirectory()) {
				list.add(file.getAbsolutePath());
			}
		}

		return list.toArray(new String[list.size()]);
	}

	public static File[] getDirFiles(FilenameFilter aFilter) {
		ArrayList<File> list = new ArrayList<File>();

		for (String filename : System.getProperty(CLASSPATH).split(":")) {
			File file = new File(filename);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Checking to see if {} is a dir ({})",
						new String[] { file.getAbsolutePath(),
								file.isDirectory() ? "yes" : "no" });
			}

			if (aFilter.accept(file.getParentFile(), file.getName())
					&& file.isDirectory()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("{} is a directory", file.getAbsolutePath());
				}

				list.add(file);
			}
		}

		return list.toArray(new File[list.size()]);
	}

	public static String[] getJars() {
		ArrayList<String> list = new ArrayList<String>();
		FileExtFileFilter filter = new FileExtFileFilter("jar");

		for (String part : System.getProperty(CLASSPATH).split(":")) {
			File file = new File(part);

			if (filter.accept(file.getParentFile(), file.getName())) {
				list.add(file.getAbsolutePath());
			}
		}

		return list.toArray(new String[list.size()]);
	}

	public static String[] getJars(FilenameFilter aFilter) {
		ArrayList<String> list = new ArrayList<String>();
		FileExtFileFilter filter = new FileExtFileFilter("jar");

		for (String part : System.getProperty(CLASSPATH).split(":")) {
			File file = new File(part);
			File parent = file.getParentFile();
			String name = file.getName();

			if (filter.accept(parent, name) && aFilter.accept(parent, name)) {
				list.add(file.getAbsolutePath());
			}
		}

		return list.toArray(new String[list.size()]);
	}

	public static JarFile[] getJarFiles() throws IOException {
		ArrayList<JarFile> list = new ArrayList<JarFile>();
		FileExtFileFilter filter = new FileExtFileFilter("jar");

		for (String part : System.getProperty(CLASSPATH).split(":")) {
			File file = new File(part);

			if (filter.accept(file.getParentFile(), file.getName())) {
				list.add(new JarFile(file));
			}
		}

		return list.toArray(new JarFile[list.size()]);
	}

	public static JarFile[] getJarFiles(FilenameFilter aFilter)
			throws IOException {
		ArrayList<JarFile> list = new ArrayList<JarFile>();
		FileExtFileFilter filter = new FileExtFileFilter("jar");

		for (String part : System.getProperty(CLASSPATH).split(":")) {
			File file = new File(part);
			File parent = file.getParentFile();
			String name = file.getName();

			if (filter.accept(parent, name) && aFilter.accept(parent, name)) {
				list.add(new JarFile(file));
			}
		}

		return list.toArray(new JarFile[list.size()]);
	}

	public static InputStream findFirst(String aFilename) throws IOException {
		FileExtFileFilter filter = new FileExtFileFilter("jar");

		for (String cpEntry : System.getProperty(CLASSPATH).split(":")) {
			File file = new File(cpEntry);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Checking {} for {}", new String[] { cpEntry,
						aFilename });
			}

			if (file.isDirectory()) {
				for (String name : file.list()) {
					File target = new File(file, aFilename);

					if (target.exists()) {
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Found {} in {}", new String[] {
									aFilename, cpEntry });
						}

						return new FileInputStream(target);
					}
				}
			}
			else if (filter.accept(file.getParentFile(), file.getName())) {
				JarFile jarFile = new JarFile(file);
				JarEntry jarEntry = jarFile.getJarEntry(aFilename);

				if (jarEntry != null && jarEntry.getSize() > 0) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Found {} in {}", new String[] {
								aFilename, cpEntry });
					}

					return jarFile.getInputStream(jarEntry);
				}
			}
		}

		return null;
	}
}
