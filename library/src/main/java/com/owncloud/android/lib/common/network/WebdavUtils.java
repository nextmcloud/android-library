/* ownCloud Android Library is available under MIT license
 *   Copyright (C) 2015 ownCloud Inc.
 *   Copyright (C) 2012 Bartek Przybylski
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

package com.owncloud.android.lib.common.network;

import android.net.Uri;

import androidx.annotation.Nullable;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.jackrabbit.webdav.property.DavPropertyName;
import org.apache.jackrabbit.webdav.property.DavPropertyNameSet;
import org.apache.jackrabbit.webdav.xml.Namespace;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WebdavUtils {
    private static final SimpleDateFormat DATETIME_FORMATS[] = {
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US),
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US),
            new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US),
            new SimpleDateFormat("EEEEEE, dd-MMM-yy HH:mm:ss zzz", Locale.US),
            new SimpleDateFormat("EEE MMMM d HH:mm:ss yyyy", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US)
    };

    public static @Nullable
    Date parseResponseDate(String date) {
        Date returnDate;
        SimpleDateFormat format;
        for (int i = 0; i < DATETIME_FORMATS.length; ++i) {
            try {
                format = DATETIME_FORMATS[i];
                synchronized (format) {
                    returnDate = format.parse(date);
                }
                return returnDate;
            } catch (ParseException e) {
                // this is not the format
            }
        }
        return null;
    }

    /**
     * Encodes a path according to URI RFC 2396. 
     * 
     * If the received path doesn't start with "/", the method adds it.
     * 
     * @param remoteFilePath    Path
     * @return                  Encoded path according to RFC 2396, always starting with "/"
     */
    public static String encodePath(String remoteFilePath) {
        String encodedPath = Uri.encode(remoteFilePath, "/");
        if (!encodedPath.startsWith("/"))
            encodedPath = "/" + encodedPath;
        return encodedPath;
    }

    /**
     * Builds a DavPropertyNameSet with all prop
     * For using instead of DavConstants.PROPFIND_ALL_PROP
     * @return
     */
    public static DavPropertyNameSet getAllPropSet() {
        Namespace ocNamespace = Namespace.getNamespace(WebdavEntry.NAMESPACE_OC);
        Namespace ncNamespace = Namespace.getNamespace(WebdavEntry.NAMESPACE_NC);
        DavPropertyNameSet propSet = new DavPropertyNameSet();
        propSet.add(DavPropertyName.DISPLAYNAME);
        propSet.add(DavPropertyName.GETCONTENTTYPE);
        propSet.add(DavPropertyName.RESOURCETYPE);
        propSet.add(DavPropertyName.GETCONTENTLENGTH);
        propSet.add(DavPropertyName.GETLASTMODIFIED);
        propSet.add(DavPropertyName.CREATIONDATE);
        propSet.add(DavPropertyName.GETETAG);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_NAME_PERMISSIONS, ocNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_NAME_REMOTE_ID, ocNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_NAME_SIZE, ocNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_FAVORITE, ocNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_IS_ENCRYPTED, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_MOUNT_TYPE, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_OWNER_ID, ocNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_OWNER_DISPLAY_NAME, ocNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_UNREAD_COMMENTS, ocNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_HAS_PREVIEW, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_NOTE, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_SHAREES, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_RICH_WORKSPACE, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_CREATION_TIME, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_UPLOAD_TIME, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_LOCK, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_LOCK_OWNER_TYPE, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_LOCK_OWNER, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_LOCK_OWNER_DISPLAY_NAME, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_LOCK_OWNER_EDITOR, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_LOCK_TIME, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_LOCK_TIMEOUT, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_LOCK_TOKEN, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_IS_ENCRYPTED, ncNamespace);

        return propSet;
    }

    /**
     * Builds a DavPropertyNameSet with properties for files
     * @return
     */
    public static DavPropertyNameSet getFilePropSet() {
        Namespace ocNamespace = Namespace.getNamespace(WebdavEntry.NAMESPACE_OC);
        Namespace ncNamespace = Namespace.getNamespace(WebdavEntry.NAMESPACE_NC);
        
        DavPropertyNameSet propSet = new DavPropertyNameSet();
        propSet.add(DavPropertyName.DISPLAYNAME);
        propSet.add(DavPropertyName.GETCONTENTTYPE);
        propSet.add(DavPropertyName.RESOURCETYPE);
        propSet.add(DavPropertyName.GETCONTENTLENGTH);
        propSet.add(DavPropertyName.GETLASTMODIFIED);
        propSet.add(DavPropertyName.CREATIONDATE);
        propSet.add(DavPropertyName.GETETAG);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_NAME_PERMISSIONS, ocNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_NAME_REMOTE_ID, ocNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_NAME_SIZE, ocNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_FAVORITE, ocNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_HAS_PREVIEW, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_SHAREES, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_CREATION_TIME, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_UPLOAD_TIME, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_LOCK, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_LOCK_OWNER_TYPE, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_LOCK_OWNER, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_LOCK_OWNER_DISPLAY_NAME, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_LOCK_OWNER_EDITOR, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_LOCK_TIME, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_LOCK_TIMEOUT, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_LOCK_TOKEN, ncNamespace);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_IS_ENCRYPTED, ncNamespace);

        return propSet;
    }

    /**
     * Builds a DavPropertyNameSet with properties for trashbin
     * @return
     */
    public static DavPropertyNameSet getTrashbinPropSet() {
        DavPropertyNameSet propSet = new DavPropertyNameSet();
        propSet.add(DavPropertyName.RESOURCETYPE);
        propSet.add(DavPropertyName.GETCONTENTTYPE);
        propSet.add(DavPropertyName.GETCONTENTLENGTH);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_NAME_SIZE, Namespace.getNamespace(WebdavEntry.NAMESPACE_OC));
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_NAME_REMOTE_ID, Namespace.getNamespace(WebdavEntry.NAMESPACE_OC));
        propSet.add(WebdavEntry.TRASHBIN_FILENAME, Namespace.getNamespace(WebdavEntry.NAMESPACE_NC));
        propSet.add(WebdavEntry.TRASHBIN_ORIGINAL_LOCATION, Namespace.getNamespace(WebdavEntry.NAMESPACE_NC));
        propSet.add(WebdavEntry.TRASHBIN_DELETION_TIME, Namespace.getNamespace(WebdavEntry.NAMESPACE_NC));

        return propSet;
    }

    /**
     * Builds a DavPropertyNameSet with properties for versions
     * @return
     */
    public static DavPropertyNameSet getFileVersionPropSet() {
        DavPropertyNameSet propSet = new DavPropertyNameSet();
        propSet.add(DavPropertyName.GETCONTENTTYPE);
        propSet.add(DavPropertyName.RESOURCETYPE);
        propSet.add(DavPropertyName.GETCONTENTLENGTH);
        propSet.add(DavPropertyName.GETLASTMODIFIED);
        propSet.add(DavPropertyName.CREATIONDATE);
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_NAME_REMOTE_ID, Namespace.getNamespace(WebdavEntry.NAMESPACE_OC));
        propSet.add(WebdavEntry.EXTENDED_PROPERTY_NAME_SIZE, Namespace.getNamespace(WebdavEntry.NAMESPACE_OC));

        return propSet;
    }

    /**
     * Builds a DavPropertyNameSet with properties for chunks
     */
    public static DavPropertyNameSet getChunksPropSet() {
        DavPropertyNameSet propSet = new DavPropertyNameSet();
        propSet.add(DavPropertyName.GETCONTENTTYPE);
        propSet.add(DavPropertyName.RESOURCETYPE);

        return propSet;
    }

    /**
     *
     * @param rawEtag
     * @return
     */
    public static String parseEtag(String rawEtag) {
        if (rawEtag == null || rawEtag.length() == 0) {
            return "";
        }
        if (rawEtag.endsWith("-gzip")) {
            rawEtag = rawEtag.substring(0, rawEtag.length() - 5);
        }
        if (rawEtag.length() >= 2 && rawEtag.startsWith("\"") && rawEtag.endsWith("\"")) {
            rawEtag = rawEtag.substring(1, rawEtag.length() - 1);
        }
        return rawEtag;
    }


    /**
     *
     * @param method
     * @return
     */
    public static String getEtagFromResponse(HttpMethod method) {
        Header eTag = method.getResponseHeader("OC-ETag");
        if (eTag == null) {
            eTag = method.getResponseHeader("oc-etag");
        }
        if (eTag == null) {
            eTag = method.getResponseHeader("ETag");
        }
        if (eTag == null) {
            eTag = method.getResponseHeader("etag");
        }
        String result = "";
        if (eTag != null) {
            result = parseEtag(eTag.getValue());
        }
        return result;
    }

}
