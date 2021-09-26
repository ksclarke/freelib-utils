
package info.freelibrary.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.jar.JarFile;

/**
 * Utility class to load a native library that lives in the current classpath.
 */
public final class NativeLibraryLoader {

    /**
     * The logger used by the native library loader.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NativeLibraryLoader.class, MessageCodes.BUNDLE);

    /**
     * A constant for OS name.
     */
    private static final String OS_NAME = "os.name";

    /**
     * A constant for OS architecture.
     */
    private static final String OS_ARCH = "os.arch";

    /**
     * A constant prefix for libraries.
     */
    private static final String LIB_PREFIX = "lib";

    /**
     * Possible architectures of native libraries.
     */
    public enum Architecture {
        UNKNOWN, LINUX_32, LINUX_64, LINUX_ARM, WINDOWS_32, WINDOWS_64, OSX_32, OSX_64, OSX_PPC
    }

    /**
     * Possible processors for native libraries.
     */
    private enum Processor {
        UNKNOWN, INTEL_32, INTEL_64, PPC, ARM
    }

    /**
     * An architecture assignment with unknown as the default.
     */
    private static Architecture myArchitecture = Architecture.UNKNOWN;

    /**
     * Constructor for the NativeLibraryLoader.
     *
     * @throws IOException
     */
    private NativeLibraryLoader() {
        // This is intentionally left empty
    }

    /**
     * Loads a native library from the classpath.
     *
     * @param aNativeLibrary A native library to load from the classpath
     * @throws IOException If there is trouble reading from the Jar file or file system
     */
    public static void load(final String aNativeLibrary) throws IOException {
        final String libFileName = getPlatformLibraryName(aNativeLibrary);
        final File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        final File libFile = new File(tmpDir, libFileName);

        // Check to see whether it already exists before we go creating it again?
        if (!libFile.exists() || libFile.length() == 0) {
            final URL url = ClasspathUtils.findFirst(libFileName);

            if (url == null) {
                throw new FileNotFoundException(LOGGER.getMessage(MessageCodes.UTIL_002, aNativeLibrary));
            }

            try (JarFile jarFile = new JarFile(url.getFile());
                InputStream inStream = jarFile.getInputStream(jarFile.getJarEntry(libFileName));
                OutputStream outStream = Files.newOutputStream(Paths.get(libFile.getAbsolutePath()))) {
                IOUtils.copyStream(inStream, new BufferedOutputStream(outStream));
            }
        }

        if (libFile.exists() && libFile.length() > 0) {
            System.load(libFile.getAbsolutePath());
        } else {
            throw new IOException(LOGGER.getI18n(MessageCodes.UTIL_039, libFile));
        }
    }

    /**
     * Gets the architecture of the machine running the JVM.
     *
     * @return The architecture of the machine running the JVM
     */
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public static Architecture getArchitecture() {
        if (Architecture.UNKNOWN == myArchitecture) {
            final Processor processor = getProcessor();

            if (Processor.UNKNOWN != processor) {
                final String name = System.getProperty(OS_NAME).toLowerCase(Locale.US);

                if (name.indexOf("nix") >= 0 || name.indexOf("nux") >= 0) {
                    if (Processor.INTEL_32 == processor) {
                        myArchitecture = Architecture.LINUX_32;
                    } else if (Processor.INTEL_64 == processor) {
                        myArchitecture = Architecture.LINUX_64;
                    } else if (Processor.ARM == processor) {
                        myArchitecture = Architecture.LINUX_ARM;
                    }
                } else if (name.indexOf("win") >= 0) {
                    if (Processor.INTEL_32 == processor) {
                        myArchitecture = Architecture.WINDOWS_32;
                    } else if (Processor.INTEL_64 == processor) {
                        myArchitecture = Architecture.WINDOWS_64;
                    }
                } else if (name.indexOf("mac") >= 0) {
                    if (Processor.INTEL_32 == processor) {
                        myArchitecture = Architecture.OSX_32;
                    } else if (Processor.INTEL_64 == processor) {
                        myArchitecture = Architecture.OSX_64;
                    } else if (Processor.PPC == processor) {
                        myArchitecture = Architecture.OSX_PPC;
                    }
                }
            }
        }

        LOGGER.debug(MessageCodes.UTIL_023, myArchitecture, System.getProperty(OS_NAME).toLowerCase(Locale.US));
        return myArchitecture;
    }

    private static Processor getProcessor() {
        Processor processor = Processor.UNKNOWN;
        int bits;

        // Note that this is actually the architecture of the installed JVM.
        final String arch = System.getProperty(OS_ARCH).toLowerCase(Locale.US);

        if (arch.indexOf("arm") >= 0) {
            processor = Processor.ARM;
        } else if (arch.indexOf("ppc") >= 0) {
            processor = Processor.PPC;
        } else if (arch.indexOf("86") >= 0 || arch.indexOf("amd") >= 0) {
            bits = 32;

            if (arch.indexOf("64") >= 0) {
                bits = 64;
            }

            processor = 32 == bits ? Processor.INTEL_32 : Processor.INTEL_64;
        }

        LOGGER.debug(MessageCodes.UTIL_024, processor, System.getProperty(OS_ARCH).toLowerCase(Locale.US));
        return processor;
    }

    /**
     * Gets the library name for the current platform.
     *
     * @param aLibraryName The platform-independent library name
     * @return The library name for the current platform
     */
    public static String getPlatformLibraryName(final String aLibraryName) {
        String libName = null;

        switch (getArchitecture()) {
            case LINUX_32:
            case LINUX_64:
            case LINUX_ARM:
                libName = LIB_PREFIX + aLibraryName + ".so";
                break;
            case WINDOWS_32:
            case WINDOWS_64:
                libName = aLibraryName + ".dll";
                break;
            case OSX_32:
            case OSX_64:
                libName = LIB_PREFIX + aLibraryName + ".dylib";
                break;
            default:
                LOGGER.warn("Unexpected architecture value: {}", getArchitecture());
                break;
        }

        LOGGER.debug(MessageCodes.UTIL_025, libName);
        return libName;
    }

}
