package com.kevadiyakrunalk.rxfilepicker;

import com.kevadiyakrunalk.rxfilepicker.R;
import com.kevadiyakrunalk.rxfilepicker.model.BaseFile;
import com.kevadiyakrunalk.rxfilepicker.model.FileType;

import java.io.File;
import java.util.ArrayList;

/**
 * The type Picker manager.
 */
public class PickerManager {
    private static PickerManager ourInstance = new PickerManager();
    private int maxCount = FilePickerConst.DEFAULT_MAX_COUNT;
    private int currentCount;
    private PickerManagerListener pickerManagerListener;
    private ArrayList<FileType> filesType;
    private File directory;
    private ArrayList<String> alreadySelectedFiles;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static PickerManager getInstance() {
        return ourInstance;
    }

    private ArrayList<BaseFile> docFiles;

    private int theme = R.style.AppTheme;

    private PickerManager() {
        docFiles = new ArrayList<>();
    }

    /**
     * Sets max count.
     *
     * @param count the count
     */
    public void setMaxCount(int count) {
        clearSelections();
        this.maxCount = count;
    }

    /**
     * Gets current count.
     *
     * @return the current count
     */
    public int getCurrentCount() {
        return currentCount;
    }

    /**
     * Gets max count.
     *
     * @return the max count
     */
    public int getMaxCount() {
        return maxCount;
    }

    /**
     * Gets files type.
     *
     * @return the files type
     */
    public ArrayList<FileType> getFilesType() {
        return filesType;
    }

    /**
     * Sets files type.
     *
     * @param filesType the files type
     */
    public void setFilesType(ArrayList<FileType> filesType) {
        this.filesType = filesType;
    }

    /**
     * Gets directory.
     *
     * @return the directory
     */
    public File getDirectory() {
        return directory;
    }

    /**
     * Sets directory.
     *
     * @param directory the directory
     */
    public void setDirectory(File directory) {
        this.directory = directory;
    }

    /**
     * Sets picker manager listener.
     *
     * @param pickerManagerListener the picker manager listener
     */
    public void setPickerManagerListener(PickerManagerListener pickerManagerListener) {
        this.pickerManagerListener = pickerManagerListener;
    }

    /**
     * Add.
     *
     * @param file the file
     */
    public void add(BaseFile file) {
        if (file != null && shouldAdd()) {
            docFiles.add(file);
            currentCount++;

            if (pickerManagerListener != null)
                pickerManagerListener.onItemSelected(currentCount);
        }
    }

    /**
     * Remove.
     *
     * @param file the file
     */
    public void remove(BaseFile file) {
       if (docFiles.contains(file)) {
            docFiles.remove(file);

            currentCount--;

            if (pickerManagerListener != null)
                pickerManagerListener.onItemSelected(currentCount);
        }
    }

    /**
     * Should add boolean.
     *
     * @return the boolean
     */
    public boolean shouldAdd() {
        return currentCount < maxCount;
    }

    /**
     * Gets selected files.
     *
     * @return the selected files
     */
    public ArrayList<String> getSelectedFiles() {
        return getSelectedFilePaths(docFiles);
    }

    /**
     * Gets selected file paths.
     *
     * @param files the files
     * @return the selected file paths
     */
    public ArrayList<String> getSelectedFilePaths(ArrayList<BaseFile> files) {
        ArrayList<String> paths = new ArrayList<>();
        for (int index = 0; index < files.size(); index++) {
            paths.add(files.get(index).getPath());
        }
        return paths;
    }

    /**
     * Clear selections.
     */
    public void clearSelections() {
        docFiles.clear();
        currentCount = 0;
        maxCount = 0;
    }

    /**
     * Gets theme.
     *
     * @return the theme
     */
    public int getTheme() {
        return theme;
    }

    /**
     * Sets theme.
     *
     * @param theme the theme
     */
    public void setTheme(int theme) {
        this.theme = theme;
    }
}
