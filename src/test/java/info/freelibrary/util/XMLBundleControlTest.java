
package info.freelibrary.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

/**
 * Tests of the <code>XMLBundleControl</code> class.
 * 
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class XMLBundleControlTest {

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
        assertEquals("xml", myControl.getFormats("yada").get(0));

        try {
            myControl.getFormats(null).get(0);
            fail("Failed to throw expected NullPointerException");
        } catch (Exception details) {
            assertTrue(details instanceof NullPointerException);
        }
    }

    /**
     * Tests standard new bundle construction.
     */
    @Test
    public void newBundleStringLocaleStringClassLoaderBoolean() {
        try {
            myControl.newBundle("aBaseName", Locale.getDefault(), "aFormat", getClass().getClassLoader(), true);
        } catch (Exception details) {
            fail(details.getMessage());
        }
    }

    /**
     * Tests new bundle construction when a null is passed in as a name.
     */
    @Test
    public void newBundleWithNullBundleName() {
        try {
            myControl.newBundle(null, Locale.getDefault(), "aFormat", getClass().getClassLoader(), true);
            fail("Failed to throw expected NullPointerException");
        } catch (Exception details) {
            assertTrue(details instanceof NullPointerException);
        }
    }

    /**
     * Tests new bundle construction when a null is passed in as a locale.
     */
    @Test
    public void newBundleWithNullLocale() {
        try {
            myControl.newBundle("aBaseName", null, "aFormat", getClass().getClassLoader(), true);
            fail("Failed to throw expected NullPointerException");
        } catch (Exception details) {
            assertTrue(details instanceof NullPointerException);
        }
    }

    /**
     * Tests new bundle construction when a null is passed in as a format.
     */
    @Test
    public void newBundleWithNullFormat() {
        try {
            Locale locale = Locale.getDefault();
            ClassLoader cl = getClass().getClassLoader();

            myControl.newBundle("aBaseName", locale, null, cl, true);
            fail("Failed to throw expected NullPointerException");
        } catch (Exception details) {
            assertTrue(details instanceof NullPointerException);
        }
    }

    /**
     * Tests new bundle construction when a null is passed in as a <code>ClassLoader</code>.
     */
    @Test
    public void newBundleWithNullClassLoader() {
        try {
            Locale locale = Locale.getDefault();
            myControl.newBundle("aBaseName", locale, "aFormat", null, true);
            fail("Failed to throw expected NullPointerException");
        } catch (Exception details) {
            assertTrue(details instanceof NullPointerException);
        }
    }

}
