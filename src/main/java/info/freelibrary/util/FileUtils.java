/**
 * Licensed under the GNU LGPL v.2.1 or later.
 */
package info.freelibrary.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Formatter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class FileUtils implements FileUtilConstants {

	private FileUtils() {}

	/**
	 * Creates a string of XML that describes the supplied file or directory
	 * (and, possibly, all its subdirectories). Includes absolute path, last
	 * modified time, read/write permissions, etc. By default, it only descends
	 * one directory.
	 * 
	 * @param aFilePath A file system path to be turned into as XML
	 * @return Am XML string representation of the file structure
	 * @throws FileNotFoundException If the supplied file or directory can not
	 *         be found
	 * @throws ParserConfigurationException If the default XML parser for the
	 *         JRE isn't configured correctly
	 */
	public static String toXML(String aFilePath) throws FileNotFoundException,
			ParserConfigurationException {
		return toXML(aFilePath, ".*");
	}

	/**
	 * Creates an <code>Element</code> that describes the supplied file or
	 * directory (and, possibly, all its subdirectories). Includes absolute
	 * path, last modified time, read/write permissions, etc. By default, it
	 * only descends one directory.
	 * 
	 * @param aFilePath A file system path to turn into XML
	 * @return An element representation of the file structure
	 * @throws FileNotFoundException If the supplied file does not exist
	 * @throws ParserConfigurationException If the default XML parser for the
	 *         JRE isn't configured correctly
	 */
	public static Element toElement(String aFilePath)
			throws FileNotFoundException, ParserConfigurationException {
		return toElement(aFilePath, ".*");
	}

	/**
	 * Creates an <code>Element</code> that describes the supplied file or
	 * directory (and, possibly, all its subdirectories). Includes absolute
	 * path, last modified time, read/write permissions, etc.
	 * 
	 * @param aFilePath A file system path to turn into XML
	 * @param aBool A boolean indicating whether the XML should contain more
	 *        than one level
	 * @return An element representation of the file structure
	 * @throws FileNotFoundException If the supplied file does not exist
	 * @throws ParserConfigurationException If the default XML parser for the
	 *         JRE isn't configured correctly
	 */
	public static Element toElement(String aFilePath, boolean aBool)
			throws FileNotFoundException, ParserConfigurationException {
		return toElement(aFilePath, ".*", aBool);
	}

	/**
	 * Creates an <code>Document</code> that describes the supplied file or
	 * directory (and, possibly, all its subdirectories). Includes absolute
	 * path, last modified time, read/write permissions, etc. By default, it
	 * only descends one directory.
	 * 
	 * @param aFilePath A file system path to turn into XML
	 * @return A document representation of the file structure
	 * @throws FileNotFoundException If the supplied file does not exist
	 * @throws ParserConfigurationException If the default XML parser for the
	 *         JRE isn't configured correctly
	 */
	public static Document toDocument(String aFilePath)
			throws FileNotFoundException, ParserConfigurationException {
		return toDocument(aFilePath, ".*");
	}

	/**
	 * Creates a string of XML that describes the supplied file or directory
	 * (and all its subdirectories). Includes absolute path, last modified time,
	 * read/write permissions, etc.
	 * 
	 * @param aFilePath The file's or directory's structure to be returned as
	 *        XML
	 * @param aPattern A regular expression that file names must match
	 * @return A string of XML describing the supplied file system path's
	 *         structure
	 * @throws FileNotFoundException If the supplied file or directory can not
	 *         be found
	 * @throws ParserConfigurationException If the default XML parser for the
	 *         JRE isn't configured correctly
	 */
	public static String toXML(String aFilePath, String aPattern)
			throws FileNotFoundException, ParserConfigurationException {
		return toXML(aFilePath, aPattern, false);
	}

	public static String toXML(String aFilePath, boolean aDeepConversion)
			throws FileNotFoundException, ParserConfigurationException {
		return toXML(aFilePath, ".*", aDeepConversion);
	}

	public static String toXML(String aFilePath, String aPattern,
			boolean aDeepConversion) throws FileNotFoundException,
			ParserConfigurationException {
		return DOMUtils.toXML(toElement(aFilePath, aPattern, aDeepConversion));
	}

	public static Element toElement(String aFilePath, String aPattern)
			throws FileNotFoundException, ParserConfigurationException {
		return toElement(aFilePath, aPattern, false);
	}

	public static Element toElement(String aFilePath, String aPattern,
			boolean aDeepConversion) throws FileNotFoundException,
			ParserConfigurationException {
		RegexFileFilter filter = new RegexFileFilter(aPattern);
		File file = new File(aFilePath);

		if (file.exists() && file.canRead()) {
			return add(file, null, filter, aDeepConversion);
		}

		throw new FileNotFoundException(aFilePath);
	}

	public static Document toDocument(String aFilePath, String aPattern)
			throws FileNotFoundException, ParserConfigurationException {
		return toDocument(aFilePath, aPattern, false);
	}

	public static Document toDocument(String aFilePath, String aPattern,
			boolean aDeepConversion) throws FileNotFoundException,
			ParserConfigurationException {
		Element element = toElement(aFilePath, aPattern, aDeepConversion);
		return element.getOwnerDocument();
	}

	public static File toFile(URL aURL) throws MalformedURLException {
		if (aURL.getProtocol().equals("file")) {
			return new File(aURL.toString().replace("file:", ""));
		}

		throw new MalformedURLException("Not a file URL");
	}

	public static File[] listFiles(File aDir, FilenameFilter aFilter)
			throws FileNotFoundException {
		return listFiles(aDir, aFilter, false, null);
	}

	public static File[] listFiles(File aDir, FilenameFilter aFilter,
			boolean aDeepListing) throws FileNotFoundException {
		return listFiles(aDir, aFilter, aDeepListing, null);
	}

	public static File[] listFiles(File aDir, FilenameFilter aFilter,
			boolean aDeepListing, String[] aIgnoreList)
			throws FileNotFoundException {
		if (!aDir.exists()) {
			throw new FileNotFoundException(aDir.getAbsolutePath());
		}

		if (!aDeepListing) {
			return aDir.listFiles(aFilter);
		}
		else {
			ArrayList<File> fileList = new ArrayList<File>();
			String[] ignoreList;

			if (aIgnoreList == null) {
				ignoreList = new String[0];
			}
			else {
				ignoreList = aIgnoreList;
			}

			for (File file : aDir.listFiles()) {
				String fileName = file.getName();

				if (aFilter.accept(aDir, fileName)) {
					fileList.add(file);
				}

				if (file.isDirectory()
						&& (Arrays.binarySearch(ignoreList, fileName) < 0)) {
					File[] files = listFiles(file, aFilter, aDeepListing);
					fileList.addAll(Arrays.asList(files));
				}

			}

			return fileList.toArray(new File[fileList.size()]);
		}
	}

	/**
	 * Deletes a directory and all its children.
	 * 
	 * @param aDir A directory to delete
	 */
	public static boolean delete(File aDir) {
		if (aDir.exists()) {
			File[] files = aDir.listFiles();
			
			for (int index = 0; index < files.length; index++) {
				if (files[index].isDirectory()) {
					delete(files[index]);
				}
				else {
					files[index].delete();
				}
			}
		}

		return aDir.delete();
	}

	public static void copy(File aFromFile, File aToFile) throws IOException {
		if ((aFromFile.isDirectory() && aToFile.isFile())
				|| (aFromFile.isFile() && aToFile.isDirectory())) {
			throw new IOException(
					"Can't copy file to directory or directory to file");
		}

		if (aFromFile.isDirectory()) {
			if (!aToFile.exists() && !aToFile.mkdirs()) {
				throw new RuntimeException("Unable to create new directory: "
						+ aToFile.getAbsolutePath());
			}

			for (File file : aFromFile.listFiles()) {
				copy(file, new File(aToFile, file.getName()));
			}
		}
		else {
			copyFile(aFromFile, aToFile);
		}
	}

	public static String sizeFromBytes(long aByteCount) {
		long count;

		if ((count = aByteCount / 1073741824) > 0) {
			return count + " gigabytes";
		}
		else if ((count = aByteCount / 1048576) > 0) {
			return count + " megabytes";
		}
		else if ((count = aByteCount / 1024) > 0) {
			return count + " kilobytes";
		}

		return count + " bytes";
	}

	public static String hash(File aFile, String aAlgorithm)
			throws NoSuchAlgorithmException, FileNotFoundException, IOException {
		MessageDigest md = MessageDigest.getInstance(aAlgorithm);
		FileInputStream inStream = new FileInputStream(aFile);
		DigestInputStream mdStream = new DigestInputStream(inStream, md);
		byte[] bytes = new byte[8192];
		while (mdStream.read(bytes) != -1);
		Formatter formatter = new Formatter();

		for (byte bite : md.digest()) {
			formatter.format("%02x", bite);
		}

		return formatter.toString();
	}

	public static String getMimeType(String aFileUrl) throws IOException {
		return URLConnection.getFileNameMap().getContentTypeFor(aFileUrl);
	}

	private static void copyFile(File aSourceFile, File aDestFile)
			throws IOException {
		FileChannel source = null;
		FileChannel dest = null;

		// destructive copy
		if (aDestFile.exists()) {
			aDestFile.delete();
		}

		aDestFile.createNewFile();

		try {
			source = new FileInputStream(aSourceFile).getChannel();
			dest = new FileOutputStream(aDestFile).getChannel();

			dest.transferFrom(source, 0, source.size());
		}
		finally {
			if (source != null) {
				source.close();
			}

			if (dest != null) {
				dest.close();
			}
		}
	}

	private static Element add(File aFile, Element aParent,
			RegexFileFilter aFilter, boolean aDeepAdd)
			throws ParserConfigurationException, FileNotFoundException {
		Element element;
		String tagName;

		if (aFile.isDirectory()) {
			tagName = DIR_TYPE;
		}
		else {
			tagName = FILE_TYPE;
		}

		if (aParent == null) {
			element = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.newDocument().createElement(tagName);
		}
		else {
			element = aParent.getOwnerDocument().createElement(tagName);
			aParent.appendChild(element);
		}

		copyMetadata(aFile, element);

		if (aFile.isDirectory()) {
			if (aDeepAdd) {
				for (File dir : listFiles(aFile, new RegexDirFilter(".*"))) {
					element.appendChild(add(dir, element, aFilter, aDeepAdd));
				}
			}

			for (File file : listFiles(aFile, aFilter)) {
				element.appendChild(add(file, element, aFilter, aDeepAdd));
			}
		}

		return element;
	}

	private static void copyMetadata(File aFile, Element aElement) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		StringBuilder permissions = new StringBuilder();
		Date date = new Date(aFile.lastModified());

		aElement.setAttribute(FILE_PATH, aFile.getAbsolutePath());
		aElement.setAttribute(MODIFIED, formatter.format(date));

		if (aFile.canRead()) {
			permissions.append('r');
		}

		if (aFile.canWrite()) {
			permissions.append('w');
		}

		aElement.setAttribute("permissions", permissions.toString());
	}
}
