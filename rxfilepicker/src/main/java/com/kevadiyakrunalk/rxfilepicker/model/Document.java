package com.kevadiyakrunalk.rxfilepicker.model;

import android.text.TextUtils;

import com.kevadiyakrunalk.rxfilepicker.R;
import com.kevadiyakrunalk.rxfilepicker.PickerManager;
import com.kevadiyakrunalk.rxfilepicker.util.Utils;

import java.io.File;
import java.util.ArrayList;

/**
 * The type Document.
 */
public class Document extends BaseFile {
    private String mimeType;
    private String size;

    /**
     * Instantiates a new Document.
     *
     * @param id    the id
     * @param title the title
     * @param path  the path
     */
    public Document(int id, String title, String path) {
        super(id, title, path);
    }

    /**
     * Instantiates a new Document.
     */
    public Document() {
        super(0, null, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Document)) return false;

        Document document = (Document) o;

        return id == document.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets mime type.
     *
     * @return the mime type
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Sets mime type.
     *
     * @param mimeType the mime type
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * Gets size.
     *
     * @return the size
     */
    public String getSize() {
        return size;
    }

    /**
     * Sets size.
     *
     * @param size the size
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return new File(this.path).getName();
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.name = title;
    }

    /**
     * Gets type drawable.
     *
     * @return the type drawable
     */
    public int getTypeDrawable() {
        ArrayList<FileType> fileTypes = PickerManager.getInstance().getFilesType();
        for (FileType fileType: fileTypes) {
            if(fileType.getGroupTitle().equalsIgnoreCase(getFileType()))
                return fileType.getGroupIcon();
        }
        return R.drawable.ic_file;
    }

    /**
     * Is this type boolean.
     *
     * @param type the type
     * @return the boolean
     */
    public boolean isThisType(CharSequence type) {
        if (getFileType().equalsIgnoreCase(String.valueOf(type)))
            return true;
        return false;
    }

    /**
     * Gets file type.
     *
     * @return the file type
     */
    public String getFileType() {
        String fileExtension = Utils.getFileExtension(new File(this.path));
        if (TextUtils.isEmpty(fileExtension))
            return "Unknown";
        else {
            ArrayList<FileType> fileTypes = PickerManager.getInstance().getFilesType();
            for (FileType fileType : fileTypes) {
                String[] types = fileType.getGroupExtension().split(",");
                if (Utils.contains(types, this.path))
                    return fileType.getGroupTitle();
            }
            return "Unknown";
        }
    }
}
