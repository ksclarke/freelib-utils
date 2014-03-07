
package info.freelibrary.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;

import org.junit.Test;
import org.slf4j.LoggerFactory;

/**
 * Tests for the {@link FileUtils} class.
 * 
 * @author <a href="mailto:ksclarke@gmail.com">ksclarke@gmail.com</a>
 */
public class FileUtilsTest {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FileUtilsTest.class);

    /**
     * Tests method for {@link FileUtils#getSize(File)}
     */
    @Test
    public void testGetSize() {
        assertEquals(41787, FileUtils.getSize(new File("src/test/resources/test_folder")));
    }

    /**
     * Tests method for {@link FileUtils#sizeFromBytes(long)}
     */
    @Test
    public void testSizeFromBytes() {
        assertEquals("40 kilobytes", FileUtils.sizeFromBytes(41787));
    }

    /**
     * Tests method for (@link {@link FileUtils#toElement(String, String, boolean)}
     */
    @Test
    public void testToElementStringStringBoolean() {
        File file = new File("src/test/resources/test_folder");
        String path = file.getAbsolutePath();

        try {
            String xml = DOMUtils.toXML(FileUtils.toElement(path, ".*", false));
            Document doc = new Builder().build(xml, "");
            Element root = doc.getRootElement();
            Element dir1 = (Element) root.getChild(0);
            Element dir2 = (Element) root.getChild(1);
            Element file1 = (Element) root.getChild(2);

            if (!dir1.getLocalName().equals("dir") || !dir1.getAttribute("path").getValue().endsWith("test_folder")) {
                fail("Failed to find expected 'test_folder'");
            } else if (!dir2.getLocalName().equals("dir") ||
                    !dir2.getAttribute("path").getValue().endsWith("test_folder2")) {
                fail("Failed to find expected 'test_folder1'");
            } else if (!file1.getLocalName().equals("file") ||
                    !file1.getAttribute("path").getValue().endsWith("test_file1.txt")) {
                fail("Failed to find expected 'test_file1.txt'");
            }
        } catch (Exception details) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(details.getMessage(), details);
            }

            fail(details.getMessage());
        }
    }
}
