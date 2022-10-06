/* ownCloud Android Library is available under MIT license
 *   Copyright (C) 2015 ownCloud Inc.
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

package com.owncloud.android.lib.resources.files.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.owncloud.android.lib.common.network.WebdavEntry;
import com.owncloud.android.lib.resources.files.FileUtils;
import com.owncloud.android.lib.resources.shares.ShareeUser;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Contains the data of a Remote File from a WebDavEntry.
 *
 * @author masensio
 */
public class RemoteFile implements Parcelable, Serializable {
    /**
     * Generated - should be refreshed every time the class changes!!
     */
    private static final long serialVersionUID = 3130865437811248451L;

    private String remotePath;
    private String mimeType;
    private long length;
    private long creationTimestamp;
    private long modifiedTimestamp;
    private long uploadTimestamp;
    private String etag;
    private String permissions;
    private String remoteId;
    private long size;
    private boolean favorite;
    private boolean encrypted;
    private WebdavEntry.MountType mountType;
    private String ownerId;
    private String ownerDisplayName;
    private int unreadCommentsCount;
    private boolean hasPreview;
    private String note;
    private ShareeUser[] sharees;
    private String richWorkspace;
    private boolean isLocked;
    private FileLockType lockType;
    private String lockOwner;
    private String lockOwnerDisplayName;
    private long lockTimestamp;
    private String lockOwnerEditor;
    private long lockTimeout;
    private String lockToken;

    public RemoteFile() {
        resetData();
    }

    /**
     * Create new {@link RemoteFile} with given path.
     * <p>
     * The path received must be URL-decoded. Path separator must be OCFile.PATH_SEPARATOR, and it must be the first character in 'path'.
     *
     * @param path The remote path of the file.
     */
    public RemoteFile(String path) {
        resetData();
        if (path == null || path.length() <= 0 || !path.startsWith(FileUtils.PATH_SEPARATOR)) {
            throw new IllegalArgumentException("Trying to create a OCFile with a non valid remote path: " + path);
        }
        remotePath = path;
    }

    public RemoteFile(WebdavEntry we) {
        this(we.decodedPath());
        setCreationTimestamp(we.getCreateTimestamp());
        setLength(we.getContentLength());
        setMimeType(we.getContentType());
        setModifiedTimestamp(we.getModifiedTimestamp());
        setUploadTimestamp(we.getUploadTimestamp());
        setEtag(we.getETag());
        setPermissions(we.getPermissions());
        setRemoteId(we.getRemoteId());
        setSize(we.getSize());
        setFavorite(we.isFavorite());
        setEncrypted(we.isEncrypted());
        setMountType(we.getMountType());
        setOwnerId(we.getOwnerId());
        setOwnerDisplayName(we.getOwnerDisplayName());
        setNote(we.getNote());
        setUnreadCommentsCount(we.getUnreadCommentsCount());
        setHasPreview(we.isHasPreview());
        setSharees(we.getSharees());
        setRichWorkspace(we.getRichWorkspace());
        setLocked(we.isLocked());
        setLockType(we.getLockOwnerType());
        setLockOwner(we.getLockOwnerId());
        setLockOwnerDisplayName(we.getLockOwnerDisplayName());
        setLockOwnerEditor(we.getLockOwnerEditor());
        setLockTimestamp(we.getLockTimestamp());
        setLockTimeout(we.getLockTimeout());
        setLockToken(we.getLockToken());
    }

    /**
     * Used internally. Reset all file properties
     */
    private void resetData() {
        remotePath = null;
        mimeType = null;
        length = 0;
        creationTimestamp = 0;
        modifiedTimestamp = 0;
        etag = null;
        permissions = null;
        remoteId = null;
        size = 0;
        favorite = false;
        encrypted = false;
        ownerId = "";
        ownerDisplayName = "";
        note = "";
        isLocked = false;
        lockOwner = null;
        lockType = null;
        lockOwnerDisplayName = null;
        lockOwnerEditor = null;
        lockTimestamp = 0;
        lockTimeout = 0;
        lockToken = null;
    }

    /**
     * Parcelable Methods
     */
    public static final Parcelable.Creator<RemoteFile> CREATOR
            = new Parcelable.Creator<RemoteFile>() {
        @Override
        public RemoteFile createFromParcel(Parcel source) {
            return new RemoteFile(source);
        }

        @Override
        public RemoteFile[] newArray(int size) {
            return new RemoteFile[size];
        }
    };


    /**
     * Reconstruct from parcel
     *
     * @param source The source parcel
     */
    protected RemoteFile(Parcel source) {
        readFromParcel(source);
    }

    public void readFromParcel(Parcel source) {
        remotePath = source.readString();
        mimeType = source.readString();
        length = source.readLong();
        creationTimestamp = source.readLong();
        modifiedTimestamp = source.readLong();
        etag = source.readString();
        permissions = source.readString();
        remoteId = source.readString();
        size = source.readLong();
        favorite = Boolean.parseBoolean(source.readString());
        encrypted = Boolean.parseBoolean(source.readString());
        mountType = (WebdavEntry.MountType) source.readSerializable();
        ownerId = source.readString();
        ownerDisplayName = source.readString();
        hasPreview = Boolean.parseBoolean(source.readString());
        note = source.readString();
        source.readParcelableArray(ShareeUser.class.getClassLoader());
        isLocked = source.readInt() == 1;
        lockType = FileLockType.fromValue(source.readInt());
        lockOwner = source.readString();
        lockOwnerDisplayName = source.readString();
        lockOwnerEditor = source.readString();
        lockTimestamp = source.readLong();
        lockTimeout = source.readLong();
        lockToken = source.readString();
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(remotePath);
        dest.writeString(mimeType);
        dest.writeLong(length);
        dest.writeLong(creationTimestamp);
        dest.writeLong(modifiedTimestamp);
        dest.writeString(etag);
        dest.writeString(permissions);
        dest.writeString(remoteId);
        dest.writeLong(size);
        dest.writeString(Boolean.toString(favorite));
        dest.writeString(Boolean.toString(encrypted));
        dest.writeSerializable(mountType);
        dest.writeString(ownerId);
        dest.writeString(ownerDisplayName);
        dest.writeString(Boolean.toString(hasPreview));
        dest.writeString(note);
        dest.writeParcelableArray(sharees, 0);
        dest.writeInt(isLocked ? 1 : 0);
        dest.writeInt(lockType != null ? lockType.getValue() : -1);
        dest.writeString(lockOwner);
        dest.writeString(lockOwnerDisplayName);
        dest.writeString(lockOwnerEditor);
        dest.writeLong(lockTimestamp);
        dest.writeLong(lockTimeout);
        dest.writeString(lockToken);
    }

    @SuppressFBWarnings(value = "STT_STRING_PARSING_A_FIELD", justification = "remoteId contains cloud id and local id")
    public String getLocalId() {
        return remoteId.substring(0, 8).replaceAll("^0*", "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RemoteFile that = (RemoteFile) o;
        return length == that.length && creationTimestamp == that.creationTimestamp && modifiedTimestamp == that.modifiedTimestamp && uploadTimestamp == that.uploadTimestamp && size == that.size && favorite == that.favorite && encrypted == that.encrypted && unreadCommentsCount == that.unreadCommentsCount && hasPreview == that.hasPreview && isLocked == that.isLocked && lockTimestamp == that.lockTimestamp && lockTimeout == that.lockTimeout && Objects.equals(remotePath, that.remotePath) && Objects.equals(mimeType, that.mimeType) && Objects.equals(etag, that.etag) && Objects.equals(permissions, that.permissions) && Objects.equals(remoteId, that.remoteId) && mountType == that.mountType && Objects.equals(ownerId, that.ownerId) && Objects.equals(ownerDisplayName, that.ownerDisplayName) && Objects.equals(note, that.note) && Arrays.equals(sharees, that.sharees) && Objects.equals(richWorkspace, that.richWorkspace) && lockType == that.lockType && Objects.equals(lockOwner, that.lockOwner) && Objects.equals(lockOwnerDisplayName, that.lockOwnerDisplayName) && Objects.equals(lockOwnerEditor, that.lockOwnerEditor) && Objects.equals(lockToken, that.lockToken);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(remotePath, mimeType, length, creationTimestamp, modifiedTimestamp, uploadTimestamp, etag, permissions, remoteId, size, favorite, encrypted, mountType, ownerId, ownerDisplayName, unreadCommentsCount, hasPreview, note, richWorkspace, isLocked, lockType, lockOwner, lockOwnerDisplayName, lockTimestamp, lockOwnerEditor, lockTimeout, lockToken);
        result = 31 * result + Arrays.hashCode(sharees);
        return result;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public long getModifiedTimestamp() {
        return modifiedTimestamp;
    }

    public void setModifiedTimestamp(long modifiedTimestamp) {
        this.modifiedTimestamp = modifiedTimestamp;
    }

    public long getUploadTimestamp() {
        return uploadTimestamp;
    }

    public void setUploadTimestamp(long uploadTimestamp) {
        this.uploadTimestamp = uploadTimestamp;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isEncrypted() {
        return encrypted;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }

    public WebdavEntry.MountType getMountType() {
        return mountType;
    }

    public void setMountType(WebdavEntry.MountType mountType) {
        this.mountType = mountType;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerDisplayName() {
        return ownerDisplayName;
    }

    public void setOwnerDisplayName(String ownerDisplayName) {
        this.ownerDisplayName = ownerDisplayName;
    }

    public int getUnreadCommentsCount() {
        return unreadCommentsCount;
    }

    public void setUnreadCommentsCount(int unreadCommentsCount) {
        this.unreadCommentsCount = unreadCommentsCount;
    }

    public boolean isHasPreview() {
        return hasPreview;
    }

    public void setHasPreview(boolean hasPreview) {
        this.hasPreview = hasPreview;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ShareeUser[] getSharees() {
        return sharees;
    }

    public void setSharees(ShareeUser[] sharees) {
        this.sharees = sharees;
    }

    public String getRichWorkspace() {
        return richWorkspace;
    }

    public void setRichWorkspace(String richWorkspace) {
        this.richWorkspace = richWorkspace;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public FileLockType getLockType() {
        return lockType;
    }

    public void setLockType(FileLockType lockType) {
        this.lockType = lockType;
    }

    public String getLockOwner() {
        return lockOwner;
    }

    public void setLockOwner(String lockOwner) {
        this.lockOwner = lockOwner;
    }

    public String getLockOwnerDisplayName() {
        return lockOwnerDisplayName;
    }

    public void setLockOwnerDisplayName(String lockOwnerDisplayName) {
        this.lockOwnerDisplayName = lockOwnerDisplayName;
    }

    public long getLockTimestamp() {
        return lockTimestamp;
    }

    public void setLockTimestamp(long lockTimestamp) {
        this.lockTimestamp = lockTimestamp;
    }

    public String getLockOwnerEditor() {
        return lockOwnerEditor;
    }

    public void setLockOwnerEditor(String lockOwnerEditor) {
        this.lockOwnerEditor = lockOwnerEditor;
    }

    public long getLockTimeout() {
        return lockTimeout;
    }

    public void setLockTimeout(long lockTimeout) {
        this.lockTimeout = lockTimeout;
    }

    public String getLockToken() {
        return lockToken;
    }

    public void setLockToken(String lockToken) {
        this.lockToken = lockToken;
    }
}
