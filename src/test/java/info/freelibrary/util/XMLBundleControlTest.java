
package info.freelibrary.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests of the <code>XMLBundleControl</code> class.
 */
public class XMLBundleControlTest {

    private static final String BUNDLE_NAME = "test_freelib-utils_messages";

    private static final String XML_FORMAT = "xml";

    private XMLBundleControl myControl;

    /**
     * Sets up the <code>XMLBundleControl</code> used in the tests.
     */
    @Before
    public void setup() {
        myControl = new XMLBundleControl();
    }

    /**
     * Tests {@link XMLBundleControl#getFormats(String)}.
     */
    @Test
    public void testGetFormatsString() {
        // The only format XMLBundleControl returns is "xml"
        assertEquals(XML_FORMAT, myControl.getFormats("yada").get(0));

        try {
            // If we request a null though we should get an exception
            myControl.getFormats(null).get(0);
            fail("Failed to throw expected NullPointerException");
        } catch (final Exception details) {
            assertTrue(details instanceof NullPointerException);
        }
    }

    /**
     * Tests standard new bundle construction.
     */
    @Test
    public void newBundleStringLocaleStringClassLoaderBoolean() {
        final ClassLoader classLoader = getClass().getClassLoader();

        try {
            if (myControl.newBundle(BUNDLE_NAME, Locale.US, XML_FORMAT, classLoader, true) == null) {
                fail("Failed to get the en_US message bundle");
            }
        } catch (final Exception details) {
            fail(details.getMessage());
        }
    }

    /**
     * Tests new bundle construction when a null is passed in as a name.
     */
    @Test
    public void newBundleWithNullBundleName() {
        final ClassLoader classLoader = getClass().getClassLoader();

        try {
            myControl.newBundle(null, Locale.US, XML_FORMAT, classLoader, true);
            fail("Failed to throw expected NullPointerException");
        } catch (final Exception details) {
            assertTrue(details instanceof NullPointerException);
        }
    }

    /**
     * Tests new bundle construction when a null is passed in as a locale.
     */
    @Test
    public void newBundleWithNullLocale() {
        final ClassLoader classLoader = getClass().getClassLoader();

        try {
            myControl.newBundle(BUNDLE_NAME, null, XML_FORMAT, classLoader, true);
            fail("Failed to throw expected NullPointerException");
        } catch (final Exception details) {
            assertTrue(details instanceof NullPointerException);
        }
    }

    /**
     * Tests new bundle construction when a null is passed in as a format.
     */
    @Test
    public void newBundleWithNullFormat() {
        final ClassLoader classLoader = getClass().getClassLoader();

        try {
            myControl.newBundle(BUNDLE_NAME, Locale.US, null, classLoader, true);
            fail("Failed to throw expected NullPointerException");
        } catch (final Exception details) {
            assertTrue(details instanceof NullPointerException);
        }
    }

    /**
     * Tests new bundle construction when a null is passed in as a <code>ClassLoader</code>.
     */
    @Test
    public void newBundleWithNullClassLoader() {
        try {
            myControl.newBundle(BUNDLE_NAME, Locale.US, XML_FORMAT, null, true);
            fail("Failed to throw expected NullPointerException");
        } catch (final Exception details) {
            assertTrue(details instanceof NullPointerException);
        }
    }

}
