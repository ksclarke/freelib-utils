
package info.freelibrary.util;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests of the <code>XMLBundleControl</code> class.
 * 
 * @author <a href="mailto:ksclarke@gmail.com">Kevin S. Clarke</a>
 */
public class XMLBundleControlTest {

    XMLBundleControl myControl = new XMLBundleControl();

    @Test
    public void getFormatsString() {
        boolean successful = false;

        assertEquals("xml", myControl.getFormats("yada").get(0));

        try {
            myControl.getFormats(null).get(0);
        } catch (NullPointerException details) {
            successful = true;
        }

        assertTrue(successful);
    }

    @Test
    public void newBundleStringLocaleStringClassLoaderBoolean() {
        boolean successful = false;

        try {
            myControl.newBundle("aBaseName", Locale.getDefault(), "aFormat",
                    getClass().getClassLoader(), true);
        } catch (NullPointerException details) {
            fail(details.getMessage());
        } catch (IOException details) {
            fail(details.getMessage());
        } catch (InstantiationException details) {
            fail(details.getMessage());
        } catch (IllegalAccessException details) {
            fail(details.getMessage());
        }

        try {
            myControl.newBundle(null, Locale.getDefault(), "aFormat",
                    getClass().getClassLoader(), true);
        } catch (NullPointerException details) {
            successful = true;
        } catch (IOException details) {
            fail(details.getMessage());
        } catch (InstantiationException details) {
            fail(details.getMessage());
        } catch (IllegalAccessException details) {
            fail(details.getMessage());
        }

        assertTrue(successful);
        successful = false;

        try {
            myControl.newBundle("aBaseName", null, "aFormat", getClass()
                    .getClassLoader(), true);
        } catch (NullPointerException details) {
            successful = true;
        } catch (IOException details) {
            fail(details.getMessage());
        } catch (InstantiationException details) {
            fail(details.getMessage());
        } catch (IllegalAccessException details) {
            fail(details.getMessage());
        }

        assertTrue(successful);
        successful = false;

        try {
            Locale locale = Locale.getDefault();
            ClassLoader cl = getClass().getClassLoader();
            myControl.newBundle("aBaseName", locale, null, cl, true);
        } catch (NullPointerException details) {
            successful = true;
        } catch (IOException details) {
            fail(details.getMessage());
        } catch (InstantiationException details) {
            fail(details.getMessage());
        } catch (IllegalAccessException details) {
            fail(details.getMessage());
        }

        assertTrue(successful);
        successful = false;

        try {
            Locale locale = Locale.getDefault();
            myControl.newBundle("aBaseName", locale, "aFormat", null, true);
        } catch (NullPointerException details) {
            successful = true;
        } catch (IOException details) {
            fail(details.getMessage());
        } catch (InstantiationException details) {
            fail(details.getMessage());
        } catch (IllegalAccessException details) {
            fail(details.getMessage());
        }

        assertTrue(successful);
    }

    @Test
    public void newBundle() {
        ResourceBundle bundle =
                ResourceBundle.getBundle("FreeLib-Utils_Messages", myControl);

        // you can also put files in a structure info.freelibrary.util.Messages

        bundle.getString("jarClassLoader.init");
    }
}
