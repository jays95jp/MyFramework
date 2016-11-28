package com.kevadiyakrunalk.rxfilepicker.model;

/**
 * The type Base file.
 */
public class BaseFile {
    /**
     * The Id.
     */
    protected int id;
    /**
     * The Name.
     */
    protected String name;
    /**
     * The Path.
     */
    protected String path;

    /**
     * Instantiates a new Base file.
     *
     * @param id   the id
     * @param name the name
     * @param path the path
     */
    public BaseFile(int id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseFile)) return false;

        BaseFile baseFile = (BaseFile) o;

        return id == baseFile.id;
    }

    /**
     * Gets path.
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets path.
     *
     * @param path the path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }
}
