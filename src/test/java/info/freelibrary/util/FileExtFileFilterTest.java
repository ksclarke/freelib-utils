
package info.freelibrary.util;

import static info.freelibrary.util.Constants.PERIOD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;

import org.junit.Test;

/**
 * Tests FileExtFileFilter.
 */
public class FileExtFileFilterTest {

    /** A file extension for JPEGs. */
    private static final String JPG = "jpg";

    /** A file extension for TIFFs. */
    private static final String TIFF = "tif";

    /** A constant for a single JPEG extension. */
    private static final String BOX_JPG = "[.jpg]";

    /** A constant for a JPEG and TIFF extension. */
    private static final String BOX_JPG_TIFF = "[.jpg, .tif]";

    /**
     * Tests toString().
     */
    @Test
    public final void testFileExtFileFilterString() {
        assertEquals(new FileExtFileFilter(PERIOD + JPG).toString(), BOX_JPG);
        assertEquals(new FileExtFileFilter(JPG).toString(), BOX_JPG);
    }

    /**
     * Tests toString().
     */
    @Test
    public final void testFileExtFileFilterStringArray() {
        assertEquals(new FileExtFileFilter(PERIOD + JPG, TIFF).toString(), BOX_JPG_TIFF);
        assertEquals(new FileExtFileFilter(JPG, PERIOD + TIFF).toString(), BOX_JPG_TIFF);
    }

    /**
     * Tests toString().
     */
    @Test
    public final void testToString() {
        assertEquals(new FileExtFileFilter(JPG, TIFF).toString(), BOX_JPG_TIFF);
    }

    /**
     * Tests accept().
     */
    @Test
    public final void testAccept() {
        final String[] fileNames = new File("src/test/resources").list(new FileExtFileFilter("txt"));

        assertEquals(2, fileNames.length);
        assertTrue(Arrays.stream(fileNames).anyMatch("80_char_test_1.txt"::equals));
    }

    /**
     * Tests filters().
     */
    @Test
    public final void testFilters() {
        assertTrue(new FileExtFileFilter(PERIOD + JPG, TIFF).filters(TIFF));
        assertTrue(new FileExtFileFilter(JPG, PERIOD + TIFF).filters(TIFF));
        assertTrue(new FileExtFileFilter(PERIOD + JPG, TIFF).filters(PERIOD + TIFF));
        assertTrue(new FileExtFileFilter(JPG, PERIOD + TIFF).filters(PERIOD + TIFF));
        assertTrue(new FileExtFileFilter(TIFF).filters(PERIOD + TIFF));
        assertTrue(new FileExtFileFilter(PERIOD + TIFF).filters(PERIOD + TIFF));
    }

}
