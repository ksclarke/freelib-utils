
package info.freelibrary.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.custommonkey.xmlunit.XMLUnit;
import org.junit.BeforeClass;
import org.junit.Test;

import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.tests.XOMTestCase;

/**
 * Tests for the {@link FileUtils} class.
 *
 * @author <a href="mailto:ksclarke@ksclarke.io">ksclarke@ksclarke.io</a>
 */
public class FileUtilsTest {

    private final File TMP_DIR = new File(System.getProperty("java.io.tmpdir"));

    private static Document EXPECTED;

    /**
     * Sets up a directory structure used for testing.
     */
    @BeforeClass
    public static void setUpBeforeClass() {
        final SimpleDateFormat formatter = new SimpleDateFormat(FileUtils.DATE_FORMAT);
        final File tf0 = new File("src/test/resources/test_folder");
        final File tf1 = new File(tf0, "test_folder");
        final File tf1_1 = new File(tf1, "test_file1.txt");
        final File tf1_2 = new File(tf1, "test_file2.txt");
        final File tf2 = new File(tf0, "test_folder2");
        final File tf2_tf1 = new File(tf2, "test_folder");
        final File tf2_tf1_1 = new File(tf2_tf1, "test_file1.txt");
        final File tf2_1 = new File(tf2, "test_file1.txt");
        final File tf0_1 = new File(tf0, "test_file1.txt");

        final Element root = new Element("dir");
        root.addAttribute(new Attribute("path", tf0.getAbsolutePath()));
        root.addAttribute(new Attribute("modified", formatter.format(new Date(tf0.lastModified()))));
        root.addAttribute(new Attribute("permissions", "rw"));

        final Element tFolder1 = new Element("dir");
        tFolder1.addAttribute(new Attribute("path", tf1.getAbsolutePath()));
        tFolder1.addAttribute(new Attribute("modified", formatter.format(new Date(tf1.lastModified()))));
        tFolder1.addAttribute(new Attribute("permissions", "rw"));

        final Element tFolder1File1 = new Element("file");
        tFolder1File1.addAttribute(new Attribute("path", tf1_1.getAbsolutePath()));
        tFolder1File1.addAttribute(new Attribute("modified", formatter.format(new Date(tf1_1.lastModified()))));
        tFolder1File1.addAttribute(new Attribute("permissions", "rw"));

        final Element tFolder1File2 = new Element("file");
        tFolder1File2.addAttribute(new Attribute("path", tf1_2.getAbsolutePath()));
        tFolder1File2.addAttribute(new Attribute("modified", formatter.format(new Date(tf1_2.lastModified()))));
        tFolder1File2.addAttribute(new Attribute("permissions", "rw"));

        final Element tFolder2 = new Element("dir");
        tFolder2.addAttribute(new Attribute("path", tf2.getAbsolutePath()));
        tFolder2.addAttribute(new Attribute("modified", formatter.format(new Date(tf2.lastModified()))));
        tFolder2.addAttribute(new Attribute("permissions", "rw"));

        final Element tFolder2tFolder1 = new Element("dir");
        tFolder2tFolder1.addAttribute(new Attribute("path", tf2_tf1.getAbsolutePath()));
        tFolder2tFolder1.addAttribute(new Attribute("modified", formatter.format(new Date(tf2_tf1.lastModified()))));
        tFolder2tFolder1.addAttribute(new Attribute("permissions", "rw"));

        final Element tFolder2tFolder1File1 = new Element("file");
        final Date tf2_tf1_1_date = new Date(tf2_tf1_1.lastModified());
        tFolder2tFolder1File1.addAttribute(new Attribute("path", tf2_tf1_1.getAbsolutePath()));
        tFolder2tFolder1File1.addAttribute(new Attribute("modified", formatter.format(tf2_tf1_1_date)));
        tFolder2tFolder1File1.addAttribute(new Attribute("permissions", "rw"));

        final Element tFolder2File1 = new Element("file");
        tFolder2File1.addAttribute(new Attribute("path", tf2_1.getAbsolutePath()));
        tFolder2File1.addAttribute(new Attribute("modified", formatter.format(new Date(tf2_1.lastModified()))));
        tFolder2File1.addAttribute(new Attribute("permissions", "rw"));

        final Element tFolder0File1 = new Element("file");
        tFolder0File1.addAttribute(new Attribute("path", tf0_1.getAbsolutePath()));
        tFolder0File1.addAttribute(new Attribute("modified", formatter.format(new Date(tf0_1.lastModified()))));
        tFolder0File1.addAttribute(new Attribute("permissions", "rw"));

        EXPECTED = new Document(root);
        root.appendChild(tFolder1);
        tFolder1.appendChild(tFolder1File1);
        tFolder1.appendChild(tFolder1File2);
        tFolder2tFolder1.appendChild(tFolder2tFolder1File1);
        tFolder2.appendChild(tFolder2tFolder1);
        tFolder2.appendChild(tFolder2File1);
        root.appendChild(tFolder2);
        root.appendChild(tFolder0File1);
    }

    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        final Constructor<FileUtils> constructor = FileUtils.class.getDeclaredConstructor();
        assertTrue("Constructor should be private", Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testToHashMapFilePath() throws IOException {
        final Map<String, List<String>> map = FileUtils.toHashMap("src/test/resources/test_folder");

        assertEquals(StringUtils.read(new File("target/test-classes/test_folder-map.txt")), map.toString());
    }

    @Test
    public void testToHashMapFilePathPatternAll() throws IOException {
        final Map<String, List<String>> map = FileUtils.toHashMap("src/test/resources/test_folder", ".*");

        assertEquals(StringUtils.read(new File("target/test-classes/test_folder-map.txt")), map.toString());
    }

    @Test
    public void testToHashMapFilePathPatternTxt() throws IOException {
        final Map<String, List<String>> map = FileUtils.toHashMap("src/test/resources/test_folder", ".*\\.txt");

        assertEquals(StringUtils.read(new File("target/test-classes/test_folder-map.txt")), map.toString());
    }

    @Test(expected = FileNotFoundException.class)
    public void testToElementStringStringBooleanBad() throws FileNotFoundException {
        FileUtils.toElement("not_found", ".*", true);
    }

    @Test(expected = MalformedURLException.class)
    public void testToFileURLBad() throws MalformedURLException {
        FileUtils.toFile(new URL("http://example.org"));
    }

    @Test
    public void testToFileURL() throws MalformedURLException {
        final String path = new File("target/test-classes/test_folder-map.txt").getAbsolutePath();
        final URL url = new URL("file://" + path);
        final File testFile = FileUtils.toFile(url);

        assertTrue("Failed to test: " + url, testFile.exists());
    }

    @Test(expected = FileNotFoundException.class)
    public void testListFilesFileFilenameFilterBooleanString() throws FileNotFoundException {
        FileUtils.listFiles(new File("doesnotexist"), new RegexFileFilter(".*"), true, new String[] {});
    }

    @Test
    public void testListFilesFileFilenameFilterBooleanStringFileTarget() throws FileNotFoundException {
        assertEquals(1, FileUtils.listFiles(new File("target/test-classes/test_folder-map.txt"), new RegexFileFilter(
                ".*"), false, new String[] {}).length);
    }

    @Test
    public void testListFilesFileFilenameFilterBooleanStringFileTargetJpg() throws FileNotFoundException {
        assertEquals(0, FileUtils.listFiles(new File("target/test-classes/test_folder-map.txt"), new RegexFileFilter(
                ".*\\.jpg"), false, new String[] {}).length);
    }

    @Test
    public void testListFilesFileFilenameFilterBooleanStringFileTargetIgnored() throws FileNotFoundException {
        final File file = new File("src/test/resources/test_folder");
        assertEquals(3, FileUtils.listFiles(file, new RegexFileFilter(".*\\.txt"), true, new String[] {
            "test_folder" }).length);
    }

    @Test
    public void testStripExtFile() {
        assertEquals("test_folder-map", FileUtils.stripExt(new File("target/test-classes/test_folder-map.txt")));
    }

    @Test
    public void testStripExtFileNoDot() {
        assertEquals("test_file", FileUtils.stripExt(new File("src/test/resources/test_file")));
    }

    @Test
    public void testGetExtString() {
        assertEquals("txt", FileUtils.getExt("target/test-classes/test_folder-map.txt"));
    }

    @Test
    public void testGetExtStringNoExt() {
        assertEquals("", FileUtils.getExt("src/test/resources/test_file"));
    }

    @Test
    public void testGetSizeEmpty() {
        assertEquals(0, FileUtils.getSize(new File("doesnotexist")));
    }

    @Test
    public void testGetSizeNull() {
        assertEquals(0, FileUtils.getSize((File) null));
    }

    @Test
    public void testCopyFileFile() throws IOException {
        final File oldFile = new File("src/test/resources/test_file");
        final File newFile = new File(TMP_DIR, oldFile.getName());

        FileUtils.copy(oldFile, newFile);

        assertTrue(newFile.exists());
    }

    @Test
    public void testCopyFileDir() throws IOException {
        final File oldDir = new File("src/test/resources/test_folder");
        final File newDir = new File(TMP_DIR, oldDir.getName());

        FileUtils.copy(oldDir, newDir);
    }

    @Test(expected = IOException.class)
    public void testCopyFileFileDir() throws IOException {
        FileUtils.copy(new File("src/test/resources/test_file"), TMP_DIR);
    }

    /**
     * Test method for {@link FileUtils#getSize(File)}
     */
    @Test
    public void testGetSize() {
        assertEquals(25403, FileUtils.getSize(new File("src/test/resources/test_folder")));
    }

    /**
     * Test method for {@link FileUtils#sizeFromBytes(long)}
     */
    @Test
    public void testSizeFromBytes() {
        assertEquals("40 kilobytes", FileUtils.sizeFromBytes(41787));
    }

    /**
     * Test method for {@link FileUtils#delete(File)}
     */
    @Test
    public void testDeleteOfEmptyDir() {
        final File dir = new File("target/test-classes/test_folder");

        try {
            if (!dir.exists()) {
                FileUtils.copy(new File("src/test/resources/test_folder"), dir);
            }
        } catch (final IOException details) {
            fail(details.getMessage());
        }

        assertTrue(FileUtils.delete(dir));
    }

    /**
     * Test method for {@link FileUtils#toXML(String)}
     */
    @Test
    public void testToXMLString() {
        try {
            final String xml = FileUtils.toXML("src/test/resources/test_folder");
            final Document found = new Builder().build(xml, "");
            final Document expected = stripLowerLevels();

            XOMTestCase.assertEquals("[" + expected.toXML() + "] vs. [" + found.toXML() + "]", expected, found);
        } catch (final Exception details) {
            fail(details.getMessage());
        }
    }

    /**
     * Test method for {@link FileUtils#toElement(String)}
     */
    @Test
    public void testToElementString() {
        try {
            final org.w3c.dom.Document expected = XMLUnit.buildControlDocument(stripLowerLevels().toXML());
            final org.w3c.dom.Element element = FileUtils.toElement("src/test/resources/test_folder");
            final org.w3c.dom.Document found = element.getOwnerDocument();

            found.appendChild(element);

            if (!XMLUnit.compareXML(expected, found).similar()) {
                fail("[" + DOMUtils.toXML(expected) + "] vs. [" + DOMUtils.toXML(element) + "]");
            }
        } catch (final Exception details) {
            fail(details.getMessage());
        }
    }

    /**
     * Test method for {@link FileUtils#toElement(String, boolean)}
     */
    @Test
    public void testToElementStringBoolean() {
        try {
            final org.w3c.dom.Document expected = XMLUnit.buildControlDocument(EXPECTED.toXML());
            final org.w3c.dom.Element element = FileUtils.toElement("src/test/resources/test_folder", true);
            final org.w3c.dom.Document found = element.getOwnerDocument();

            found.appendChild(element);

            if (!XMLUnit.compareXML(expected, found).similar()) {
                fail("[" + DOMUtils.toXML(expected) + "] vs. [" + DOMUtils.toXML(element) + "]");
            }
        } catch (final Exception details) {
            fail(details.getMessage());
        }

        try {
            final org.w3c.dom.Document expected = XMLUnit.buildControlDocument(EXPECTED.toXML());
            final org.w3c.dom.Element element = FileUtils.toElement("src/test/resources/test_folder", false);
            final org.w3c.dom.Document found = element.getOwnerDocument();

            found.appendChild(element);

            if (XMLUnit.compareXML(expected, found).similar()) {
                fail("[" + DOMUtils.toXML(expected) + "] vs. [" + DOMUtils.toXML(element) + "]");
            }
        } catch (final Exception details) {
            fail(details.getMessage());
        }
    }

    /**
     * Test method for {@link FileUtils#toElement(String, String, boolean)}
     */
    @Test
    public void testToElementStringStringBoolean() {
        try {
            final org.w3c.dom.Document expected = XMLUnit.buildControlDocument(EXPECTED.toXML());
            final org.w3c.dom.Element element = FileUtils.toElement("src/test/resources/test_folder", ".*", true);
            final org.w3c.dom.Document found = element.getOwnerDocument();

            found.appendChild(element);

            if (!XMLUnit.compareXML(expected, found).similar()) {
                fail("[" + DOMUtils.toXML(expected) + "] vs. [" + DOMUtils.toXML(element) + "]");
            }
        } catch (final Exception details) {
            fail(details.getMessage());
        }

        try {
            final org.w3c.dom.Document expected = XMLUnit.buildControlDocument(EXPECTED.toXML());
            final org.w3c.dom.Element element = FileUtils.toElement("src/test/resources/test_folder", ".*", false);
            final org.w3c.dom.Document found = element.getOwnerDocument();

            found.appendChild(element);

            if (XMLUnit.compareXML(expected, found).similar()) {
                fail("[" + DOMUtils.toXML(expected) + "] vs. [" + DOMUtils.toXML(element) + "]");
            }
        } catch (final Exception details) {
            fail(details.getMessage());
        }
    }

    /**
     * Test method for {@link FileUtils#toDocument(String)}
     */
    @Test
    public void testToDocumentString() {
        try {
            final org.w3c.dom.Document expected = XMLUnit.buildControlDocument(stripLowerLevels().toXML());
            final org.w3c.dom.Document found = FileUtils.toDocument("src/test/resources/test_folder");

            if (!XMLUnit.compareXML(expected, found).similar()) {
                fail("[" + DOMUtils.toXML(expected) + "] vs. [" + DOMUtils.toXML(found) + "]");
            }
        } catch (final Exception details) {
            fail(details.getMessage());
        }
    }

    /**
     * Test method for {@link FileUtils.toXML(String, String)}
     */
    @Test
    public void testToXMLStringString() {
        try {
            final Document found = new Builder().build(FileUtils.toXML("src/test/resources/test_folder", ".*txt"),
                    "");
            final Document expected = getShallowFilePatternDocument();

            XOMTestCase.assertEquals("[" + expected.toXML() + "] vs. [" + found.toXML() + "]", expected, found);
        } catch (final Exception details) {
            fail(details.getMessage());
        }
    }

    /**
     * Test method for {@link FileUtils.toXML(String, Boolean)}
     */
    @Test
    public void testToXMLStringBoolean() {
        try {
            final Document found = new Builder().build(FileUtils.toXML("src/test/resources/test_folder", true), "");

            XOMTestCase.assertEquals("[" + EXPECTED.toXML() + "] vs. [" + found.toXML() + "]", EXPECTED, found);
        } catch (final Exception details) {
            fail(details.getMessage());
        }

        try {
            final Document found = new Builder().build(FileUtils.toXML("src/test/resources/test_folder", false), "");
            final Document expected = stripLowerLevels();

            XOMTestCase.assertEquals("[" + expected.toXML() + "] vs. [" + found.toXML() + "]", expected, found);
        } catch (final Exception details) {
            fail(details.getMessage());
        }
    }

    /**
     * Test method for {@link FileUtils.toXML(String, String, Boolean)}
     */
    @Test
    public void testToXMLStringStringBoolean() {
        final String path = "src/test/resources/test_folder";

        try {
            final Document found = new Builder().build(FileUtils.toXML(path, ".*", true), "");

            XOMTestCase.assertEquals("[" + EXPECTED.toXML() + "] vs. [" + found.toXML() + "]", EXPECTED, found);
        } catch (final Exception details) {
            fail(details.getMessage());
        }

        try {
            final Document found = new Builder().build(FileUtils.toXML(path, ".*", false), "");
            final Document expected = stripLowerLevels();

            XOMTestCase.assertEquals("[" + expected.toXML() + "] vs. [" + found.toXML() + "]", expected, found);
        } catch (final Exception details) {
            fail(details.getMessage());
        }

        try {
            final Document found = new Builder().build(FileUtils.toXML(path, ".*txt", true), "");

            XOMTestCase.assertEquals("[" + EXPECTED.toXML() + "] vs. [" + found.toXML() + "]", EXPECTED, found);
        } catch (final Exception details) {
            fail(details.getMessage());
        }

        try {
            final Document found = new Builder().build(FileUtils.toXML(path, ".*txt", false), "");
            final Document expected = getShallowFilePatternDocument();

            XOMTestCase.assertEquals("[" + expected.toXML() + "] vs. [" + found.toXML() + "]", expected, found);
        } catch (final Exception details) {
            fail(details.getMessage());
        }
    }

    /**
     * Strips lower levels from an XML file structure.
     *
     * @return
     */
    private Document stripLowerLevels() {
        final Element root = ((Document) EXPECTED.copy()).getRootElement();

        for (int index = 0; index < root.getChildCount(); index++) {
            final Elements children = ((Element) root.getChild(index)).getChildElements();

            for (int cIndex = 0; cIndex < children.size(); cIndex++) {
                children.get(cIndex).detach();
            }
        }

        return root.getDocument();
    }

    private Document getShallowFilePatternDocument() {
        final SimpleDateFormat formatter = new SimpleDateFormat(FileUtils.DATE_FORMAT);
        final File dir = new File("src/test/resources/test_folder");
        final File file = new File(dir, "test_file1.txt");
        final Element dirElem = new Element("dir");
        final Element fileElem = new Element("file");

        dirElem.addAttribute(new Attribute("path", dir.getAbsolutePath()));
        dirElem.addAttribute(new Attribute("modified", formatter.format(new Date(dir.lastModified()))));
        dirElem.addAttribute(new Attribute("permissions", "rw"));
        fileElem.addAttribute(new Attribute("path", file.getAbsolutePath()));
        fileElem.addAttribute(new Attribute("modified", formatter.format(new Date(file.lastModified()))));
        fileElem.addAttribute(new Attribute("permissions", "rw"));

        dirElem.appendChild(fileElem);
        return new Document(dirElem);
    }
}
