
package info.freelibrary.util;

/*

 ===============================================================================
 This class (Pairtree) was originally written by Justin Littman (jlit@loc.gov)
 It has been modified and renamed by Kevin S. Clarke (ksclarke@gmail.com)
 ===============================================================================

 This software is a work of the United States Government and is not subject 
 to copyright protection in the United States. 

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR THE UNITED STATES BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.


 Foreign copyrights may apply. To the extent that foreign copyrights in the 
 software exist outside the United States, the following terms apply:

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.

 */

import info.freelibrary.util.InvalidPPathException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PairtreeUtilsTest {

    @Before
    public void setup() {
        PairtreeUtils.setSeparator('/');
    }

    @Test
    public void testMapToPPath() {
        assertEquals("ab/cd", PairtreeUtils.mapToPPath("abcd"));
        assertEquals("ab/cd/ef/g", PairtreeUtils.mapToPPath("abcdefg"));
        assertEquals("12/-9/86/xy/4", PairtreeUtils.mapToPPath("12-986xy4"));

        assertEquals("13/03/0_/45/xq/v_/79/38/42/49/5", PairtreeUtils
                .mapToPPath(null, "13030_45xqv_793842495", null));
        assertEquals("13/03/0_/45/xq/v_/79/38/42/49/5/793842495", PairtreeUtils
                .mapToPPath(null, "13030_45xqv_793842495", "793842495"));
        assertEquals("/data/13/03/0_/45/xq/v_/79/38/42/49/5", PairtreeUtils
                .mapToPPath("/data", "13030_45xqv_793842495", null));
        assertEquals("/data/13/03/0_/45/xq/v_/79/38/42/49/5", PairtreeUtils
                .mapToPPath("/data/", "13030_45xqv_793842495", null));
        assertEquals("/data/13/03/0_/45/xq/v_/79/38/42/49/5/793842495",
                PairtreeUtils.mapToPPath("/data", "13030_45xqv_793842495",
                        "793842495"));

    }

    @Test
    public void testIdCleaning() {
        assertEquals("ark+=13030=xt12t3", PairtreeUtils
                .cleanId("ark:/13030/xt12t3"));
        assertEquals("http+==n2t,info=urn+nbn+se+kb+repos-1", PairtreeUtils
                .cleanId("http://n2t.info/urn:nbn:se:kb:repos-1"));
        assertEquals("what-the-^2a@^3f#!^5e!^3f", PairtreeUtils
                .cleanId("what-the-*@?#!^!?"));
    }

    @Test
    public void testIdUncleaning() {
        assertEquals("ark:/13030/xt12t3", PairtreeUtils
                .uncleanId("ark+=13030=xt12t3"));
        assertEquals("http://n2t.info/urn:nbn:se:kb:repos-1", PairtreeUtils
                .uncleanId("http+==n2t,info=urn+nbn+se+kb+repos-1"));
        assertEquals("what-the-*@?#!^!?", PairtreeUtils
                .uncleanId("what-the-^2a@^3f#!^5e!^3f"));
    }

    @Test
    public void testMapToPPathWithIdCleaning() {
        assertEquals("ar/k+/=1/30/30/=x/t1/2t/3", PairtreeUtils
                .mapToPPath("ark:/13030/xt12t3"));

        assertEquals("ht/tp/+=/=n/2t/,i/nf/o=/ur/n+/nb/n+/se/+k/b+/re/po/s-/1",
                PairtreeUtils
                        .mapToPPath("http://n2t.info/urn:nbn:se:kb:repos-1"));
        assertEquals("wh/at/-t/he/-^/2a/@^/3f/#!/^5/e!/^3/f", PairtreeUtils
                .mapToPPath("what-the-*@?#!^!?"));
    }

    @Test
    public void testExtractEncapsulatingDir() throws InvalidPPathException {
        assertNull(PairtreeUtils.extractEncapsulatingDirFromPPath("ab"));
        assertNull(PairtreeUtils.extractEncapsulatingDirFromPPath("ab/cd"));
        assertNull(PairtreeUtils.extractEncapsulatingDirFromPPath("ab/cd/"));
        assertNull(PairtreeUtils.extractEncapsulatingDirFromPPath("ab/cd/ef/g"));
        assertNull(PairtreeUtils
                .extractEncapsulatingDirFromPPath("ab/cd/ef/g/"));
        assertEquals("h", PairtreeUtils
                .extractEncapsulatingDirFromPPath("ab/cd/ef/g/h"));
        assertEquals("h", PairtreeUtils
                .extractEncapsulatingDirFromPPath("ab/cd/ef/g/h/"));
        assertEquals("efg", PairtreeUtils
                .extractEncapsulatingDirFromPPath("ab/cd/efg"));
        assertEquals("efg", PairtreeUtils
                .extractEncapsulatingDirFromPPath("ab/cd/efg/"));
        assertEquals("h", PairtreeUtils
                .extractEncapsulatingDirFromPPath("ab/cd/ef/g/h"));
        assertEquals("h", PairtreeUtils
                .extractEncapsulatingDirFromPPath("ab/cd/ef/g/h/"));

        assertNull(PairtreeUtils.extractEncapsulatingDirFromPPath("/data",
                "/data/ab"));
        assertNull(PairtreeUtils.extractEncapsulatingDirFromPPath("/data/",
                "/data/ab"));
        assertEquals("h", PairtreeUtils.extractEncapsulatingDirFromPPath(
                "/data", "/data/ab/cd/ef/g/h"));
        assertEquals("h", PairtreeUtils.extractEncapsulatingDirFromPPath(
                "/data/", "/data/ab/cd/ef/g/h"));

    }

    @Test
    public void testMapToId() throws InvalidPPathException {
        assertEquals("ab", PairtreeUtils.mapToId("ab"));
        assertEquals("abcd", PairtreeUtils.mapToId("ab/cd"));
        assertEquals("abcd", PairtreeUtils.mapToId("ab/cd/"));
        assertEquals("abcdefg", PairtreeUtils.mapToId("ab/cd/ef/g"));
        assertEquals("abcdefg", PairtreeUtils.mapToId("ab/cd/ef/g/"));
        assertEquals("abcdefg", PairtreeUtils.mapToId("ab/cd/ef/g/h"));
        assertEquals("abcdefg", PairtreeUtils.mapToId("ab/cd/ef/g/h/"));
        assertEquals("abcd", PairtreeUtils.mapToId("ab/cd/efg"));
        assertEquals("abcd", PairtreeUtils.mapToId("ab/cd/efg/"));
        assertEquals("abcdefg", PairtreeUtils.mapToId("ab/cd/ef/g/h"));
        assertEquals("abcdefg", PairtreeUtils.mapToId("ab/cd/ef/g/h/"));

        assertEquals("ab/cd/ef/g", PairtreeUtils.mapToPPath("abcdefg"));
        assertEquals("12-986xy4", PairtreeUtils.mapToId("12/-9/86/xy/4"));

        assertEquals("13030_45xqv_793842495", PairtreeUtils
                .mapToId("13/03/0_/45/xq/v_/79/38/42/49/5"));
        assertEquals("13030_45xqv_793842495", PairtreeUtils
                .mapToId("13/03/0_/45/xq/v_/79/38/42/49/5/793842495"));
        assertEquals("13030_45xqv_793842495", PairtreeUtils.mapToId("/data",
                "/data/13/03/0_/45/xq/v_/79/38/42/49/5"));
        assertEquals("13030_45xqv_793842495", PairtreeUtils.mapToId("/data/",
                "/data/13/03/0_/45/xq/v_/79/38/42/49/5"));
        assertEquals("13030_45xqv_793842495", PairtreeUtils.mapToId("/data",
                "/data/13/03/0_/45/xq/v_/79/38/42/49/5/793842495"));
    }

    @Test(expected = InvalidPPathException.class)
    public void testInvalidExtractEncapsulatingDir1()
            throws InvalidPPathException {
        PairtreeUtils.extractEncapsulatingDirFromPPath("abc");
    }

    @Test(expected = InvalidPPathException.class)
    public void testInvalidExtractEncapsulatingDir2()
            throws InvalidPPathException {
        PairtreeUtils.extractEncapsulatingDirFromPPath("ab/cdx/efg/");
    }

    @Test
    public void testMapToIdWithIdCleaning() throws InvalidPPathException {
        assertEquals("ark:/13030/xt12t3", PairtreeUtils
                .mapToId("ar/k+/=1/30/30/=x/t1/2t/3"));

        // This example from the spec is wrong
        // assertEquals("http://n2t.info/urn:nbn:se:kb:repos-1",
        // PairtreeUtils.mapToId("ht/tp/+=/=n/2t/,i/nf/o=/ur/n+/n/bn/+s/e+/kb/+/re/p/os/-1"));
        assertEquals(
                "http://n2t.info/urn:nbn:se:kb:repos-1",
                PairtreeUtils
                        .mapToId("ht/tp/+=/=n/2t/,i/nf/o=/ur/n+/nb/n+/se/+k/b+/re/po/s-/1"));
        assertEquals("what-the-*@?#!^!?", PairtreeUtils
                .mapToId("wh/at/-t/he/-^/2a/@^/3f/#!/^5/e!/^3/f"));
    }
}
