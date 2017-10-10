
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

public class NativeLibraryLoaderTest {

    private static final String OS_ARCH = "os.arch";

    private static final String OS_NAME = "os.name";

    private String myOS;

    private String myArchitecture;

    @Before
    public void setUp() throws Exception {
        myOS = System.getProperty(OS_NAME);
        myArchitecture = System.getProperty(OS_ARCH);
    }

    @After
    public void tearDown() throws Exception {
        System.setProperty(OS_NAME, myOS);
        System.setProperty(OS_ARCH, myArchitecture);
    }

    @Test
    public void testArchitectureLinux32NotNull() {
        assertNotNull("Linux 32 should not be null", Architecture.valueOf("LINUX_32"));
    }

    @Test
    public void testArchitectureLinux64NotNull() {
        assertNotNull("Linux 64 should not be null", Architecture.valueOf("LINUX_64"));
    }

    @Test
    public void testArchitectureUnknownNotNull() {
        assertNotNull("Unknown should not be null", Architecture.valueOf("UNKNOWN"));
    }

    @Test
    public void testArchitectureLinuxArmNotNull() {
        assertNotNull("Linux ARM should not be null", Architecture.valueOf("LINUX_ARM"));
    }

    @Test
    public void testArchitectureWindows32NotNull() {
        assertNotNull("Windows 32 should not be null", Architecture.valueOf("WINDOWS_32"));
    }

    @Test
    public void testArchitectureWindows64NotNull() {
        assertNotNull("Windows 64 should not be null", Architecture.valueOf("WINDOWS_64"));
    }

    @Test
    public void testArchitectureOsx32NotNull() {
        assertNotNull("OSX 32 should not be null", Architecture.valueOf("OSX_32"));
    }

    @Test
    public void testArchitectureOsx64NotNull() {
        assertNotNull("OSX 64 should not be null", Architecture.valueOf("OSX_64"));
    }

    @Test
    public void testArchitectureOsxPpcNotNull() {
        assertNotNull("OSX PPC should not be null", Architecture.valueOf("OSX_PPC"));
    }

    @Test
    public void testArchitectureEnum() {
        assertEquals("Architecture should contain 9 values", 9, NativeLibraryLoader.Architecture.values().length);
    }

    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        final Constructor<NativeLibraryLoader> constructor = NativeLibraryLoader.class.getDeclaredConstructor();
        assertTrue("Constructor should be private", Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

}
