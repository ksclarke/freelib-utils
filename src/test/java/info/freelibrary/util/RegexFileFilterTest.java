
package info.freelibrary.util;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FilenameFilter;

import org.junit.Test;

public class RegexFileFilterTest {

    /**
     * Tests RegexFileFilter constructor.
     */
    @Test
    public void testConstructor() {
        FilenameFilter filter;
        File tmpFile;

        try {
            tmpFile = File.createTempFile("totes", "");
            filter = new RegexFileFilter("^" + tmpFile.getName() + "$");
            tmpFile.deleteOnExit();

            if (!filter.accept(tmpFile.getParentFile(), tmpFile.getName())) {
                fail("Failed to match supplied file name");
            }
        } catch (final Exception details) {
            fail(details.getMessage());
        }
    }

    /**
     * Tests RegexFileFilter constructor with case insensitive option.
     */
    @Test
    public void testCaseInsensitiveConstructor() {
        FilenameFilter filter;
        String pattern;
        File file;

        try {
            file = File.createTempFile("totes", "");
            pattern = "^" + file.getName().toUpperCase() + "$";
            filter = new RegexFileFilter(pattern, true);
            file.deleteOnExit();

            if (!filter.accept(file.getParentFile(), file.getName())) {
                fail("Failed to match supplied file name");
            }
        } catch (final Exception details) {
            fail(details.getMessage());
        }
    }

}
