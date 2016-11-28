package com.kevadiyakrunalk.rxfilepicker.model;

public class FileType {
    /**
     * The Group title.
     */
    String GroupTitle;
    /**
     * The Group icon.
     */
    int GroupIcon;
    /**
     * The Group extension.
     */
    String GroupExtension;

    /**
     * Gets group title.
     *
     * @return the group title
     */
    public String getGroupTitle() {
        return GroupTitle;
    }

    /**
     * Sets group title.
     *
     * @param groupTitle the group title
     */
    public void setGroupTitle(String groupTitle) {
        GroupTitle = groupTitle;
    }

    /**
     * Gets group icon.
     *
     * @return the group icon
     */
    public int getGroupIcon() {
        return GroupIcon;
    }

    /**
     * Sets group icon.
     *
     * @param groupIcon the group icon
     */
    public void setGroupIcon(int groupIcon) {
        GroupIcon = groupIcon;
    }

    /**
     * Gets group extension.
     *
     * @return the group extension
     */
    public String getGroupExtension() {
        return GroupExtension;
    }

    /**
     * Sets group extension.
     *
     * @param groupExtension the group extension
     */
    public void setGroupExtension(String groupExtension) {
        GroupExtension = groupExtension;
    }

    @Override
    public String toString() {
        return "FileType{" +
                "GroupTitle='" + GroupTitle + '\'' +
                ", GroupIcon=" + GroupIcon +
                ", GroupExtension='" + GroupExtension + '\'' +
                '}';
    }
}
