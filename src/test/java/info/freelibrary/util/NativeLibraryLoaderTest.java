
package info.freelibrary.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import info.freelibrary.util.NativeLibraryLoader.Architecture;

/**
 * Test of NativeLibraryLoader.
 */
public class NativeLibraryLoaderTest {

    private static final String OS_ARCH = "os.arch";

    private static final String OS_NAME = "os.name";

    private String myOS;

    private String myArchitecture;

    /**
     * Sets up the test environment.
     *
     * @throws Exception If there is trouble setting up the environment.
     */
    @Before
    public void setUp() throws Exception {
        myOS = System.getProperty(OS_NAME);
        myArchitecture = System.getProperty(OS_ARCH);
    }

    /**
     * Cleans up the test environment.
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        System.setProperty(OS_NAME, myOS);
        System.setProperty(OS_ARCH, myArchitecture);
    }

    /**
     * Tests Architecture.
     */
    @Test
    public void testArchitectureLinux32NotNull() {
        assertNotNull("Linux 32 should not be null", Architecture.valueOf("LINUX_32"));
    }

    /**
     * Tests Architecture.
     */
    @Test
    public void testArchitectureLinux64NotNull() {
        assertNotNull("Linux 64 should not be null", Architecture.valueOf("LINUX_64"));
    }

    /**
     * Tests Architecture.
     */
    @Test
    public void testArchitectureUnknownNotNull() {
        assertNotNull("Unknown should not be null", Architecture.valueOf("UNKNOWN"));
    }

    /**
     * Tests Architecture.
     */
    @Test
    public void testArchitectureLinuxArmNotNull() {
        assertNotNull("Linux ARM should not be null", Architecture.valueOf("LINUX_ARM"));
    }

    /**
     * Tests Architecture.
     */
    @Test
    public void testArchitectureWindows32NotNull() {
        assertNotNull("Windows 32 should not be null", Architecture.valueOf("WINDOWS_32"));
    }

    /**
     * Tests Architecture.
     */
    @Test
    public void testArchitectureWindows64NotNull() {
        assertNotNull("Windows 64 should not be null", Architecture.valueOf("WINDOWS_64"));
    }

    /**
     * Tests Architecture.
     */
    @Test
    public void testArchitectureOsx32NotNull() {
        assertNotNull("OSX 32 should not be null", Architecture.valueOf("OSX_32"));
    }

    /**
     * Tests Architecture.
     */
    @Test
    public void testArchitectureOsx64NotNull() {
        assertNotNull("OSX 64 should not be null", Architecture.valueOf("OSX_64"));
    }

    /**
     * Tests Architecture.
     */
    @Test
    public void testArchitectureOsxPpcNotNull() {
        assertNotNull("OSX PPC should not be null", Architecture.valueOf("OSX_PPC"));
    }

    /**
     * Tests Architecture.
     */
    @Test
    public void testArchitectureEnum() {
        assertEquals("Architecture should contain 9 values", 9, NativeLibraryLoader.Architecture.values().length);
    }

    /**
     * Tests the NativeLibraryLoader constructor.
     *
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        final Constructor<NativeLibraryLoader> constructor = NativeLibraryLoader.class.getDeclaredConstructor();

        assertTrue("Constructor should be private", Modifier.isPrivate(constructor.getModifiers()));

        constructor.setAccessible(true);
        constructor.newInstance();
    }

}
