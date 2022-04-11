/* Nextcloud Android Library is available under MIT license
 *
 *   @author Tobias Kaminsky
 *   Copyright (C) 2018 Tobias Kaminsky
 *   Copyright (C) 2018 Nextcloud GmbH
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *   EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *   MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *   NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 *   BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 *   ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *   CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 *
 */

package com.owncloud.android.lib.resources.status;

        import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OwnCloudVersionTest {
    @Test
    public void testOwnCloudVersion() {
        OwnCloudVersion version = new OwnCloudVersion("17.0.0");

        assertEquals(0, version.compareTo(OwnCloudVersion.nextcloud_17));

        version = new OwnCloudVersion("17.99.99");

        assertEquals(0, version.compareTo(new OwnCloudVersion(0x11636300))); // 13.99.99 in hex
    }
    
    @Test
    public void testGetMajorVersion() {
        OwnCloudVersion version = new OwnCloudVersion("12.0.0");
        assertEquals(12, version.getMajorVersionNumber());

        version = new OwnCloudVersion("19.0.0");
        assertEquals(19, version.getMajorVersionNumber());
    }
    
    @Test
    public void testSamMajorVersion() {
        OwnCloudVersion version = new OwnCloudVersion("12.0.0");
        
        assertTrue(version.isSameMajorVersion(new OwnCloudVersion("12.99.99")));
        assertFalse(version.isSameMajorVersion(new OwnCloudVersion("13.0.0")));
    }

    @Test
    public void testOwnCloudVersionFailure() {
        OwnCloudVersion version = new OwnCloudVersion("");

        assertFalse(version.isVersionValid());
    }

    @Test
    public void testSupportNC16() {
        assertFalse(OwnCloudVersion.nextcloud_16.isShareesOnDavSupported());
    }

    @Test
    public void testSupportNC17() {
        assertTrue(OwnCloudVersion.nextcloud_17.isShareesOnDavSupported());
    }

    @Test
    public void testSupportNC18() {
        assertTrue(OwnCloudVersion.nextcloud_18.isShareesOnDavSupported());
    }

    @Test
    public void testSupportNC21() {
        OwnCloudVersion version = new OwnCloudVersion("21.0.0");

        assertEquals(0, version.compareTo(NextcloudVersion.nextcloud_21));

        version = new NextcloudVersion("21.99.99");

        assertEquals(0, version.compareTo(new OwnCloudVersion(0x15636300))); // 21.99.99 in hex
    }
}
