
package info.freelibrary.util;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FilenameFilter;

import org.junit.Test;

/**
 * A test of <code>RegexFileFilter</code>
 */
public class RegexFileFilterTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegexFileFilterTest.class, Constants.BUNDLE_NAME);

    private static final String TOTES = "totes";

    private static final String CARROT = "^";

    private static final String EMPTY = "";

    private static final String DOLLAR_SIGN = "$";

    /**
     * Tests RegexFileFilter constructor.
     */
    @Test
    public void testConstructor() {
        try {
            final File tmpFile = File.createTempFile(TOTES, EMPTY);
            final FilenameFilter filter = new RegexFileFilter(CARROT + tmpFile.getName() + DOLLAR_SIGN);

            tmpFile.deleteOnExit();

            if (!filter.accept(tmpFile.getParentFile(), tmpFile.getName())) {
                fail(LOGGER.getMessage(MessageCodes.UTIL_047, tmpFile));
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
        try {
            final File file = File.createTempFile(TOTES, EMPTY);
            final String pattern = CARROT + file.getName().toUpperCase() + DOLLAR_SIGN;
            final FilenameFilter filter = new RegexFileFilter(pattern, true);

            file.deleteOnExit();

            if (!filter.accept(file.getParentFile(), file.getName())) {
                fail("Failed to match supplied file name");
            }
        } catch (final Exception details) {
            fail(details.getMessage());
        }
    }

}
