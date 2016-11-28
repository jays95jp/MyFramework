package com.kevadiyakrunalk.rxfilepicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.kevadiyakrunalk.rxfilepicker.model.FileType;

import java.io.File;
import java.util.ArrayList;

/**
 * The type Rx file picker.
 */
public class RxFilePicker {
    private Context context;
    private FileResult fileResult;
    /**
     * The S singleton.
     */
    static RxFilePicker sSingleton;

    /**
     * Instantiates a new Rx file picker.
     *
     * @param ctx the ctx
     */
    public RxFilePicker(Context ctx) {
        context = ctx;
    }

    /**
     * Gets instance.
     *
     * @param ctx the ctx
     * @return the instance
     */
    public static RxFilePicker getInstance(Context ctx) {
        if (sSingleton == null) {
            synchronized (RxFilePicker.class) {
                sSingleton = new RxFilePicker(ctx);
            }
        }
        return sSingleton;
    }

    /**
     * Sets file type.
     *
     * @param type the type
     * @return the file type
     */
    public RxFilePicker setFileType(ArrayList<FileType> type) {
        PickerManager.getInstance().setFilesType(type);
        return sSingleton;
    }

    /**
     * Sets directory.
     *
     * @param directory the directory
     * @return the directory
     */
    public RxFilePicker setDirectory(File directory) {
        PickerManager.getInstance().setDirectory(directory);
        return sSingleton;
    }

    /**
     * Sets max count.
     *
     * @param maxCount the max count
     * @return the max count
     */
    public RxFilePicker setMaxCount(int maxCount) {
        PickerManager.getInstance().setMaxCount(maxCount);
        return sSingleton;
    }

    /**
     * Sets activity theme.
     *
     * @param theme the theme
     * @return the activity theme
     */
    public RxFilePicker setActivityTheme(int theme) {
        PickerManager.getInstance().setTheme(theme);
        return sSingleton;
    }

    /**
     * Pick document.
     *
     * @param context the context
     * @param result  the result
     */
    public void pickDocument(Activity context, FileResult result) {
        fileResult = result;
        Intent intent = new Intent(context, FilePickActivity.class);
        context.startActivity(intent);
    }

    /**
     * Pick document.
     *
     * @param context the context
     * @param result  the result
     */
    public void pickDocument(Fragment context, FileResult result) {
        fileResult = result;
        Intent intent = new Intent(context.getActivity(), FilePickActivity.class);
        context.startActivity(intent);
    }

    /**
     * On activity result.
     *
     * @param requestCode the request code
     * @param resultCode  the result code
     * @param data        the data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FilePickerConst.REQUEST_CODE_DOC:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    fileResult.PickFileList(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
                }
                break;
        }
    }

    /**
     * The interface File result.
     */
    public interface FileResult{
        /**
         * Pick file list.
         *
         * @param list the list
         */
        public void PickFileList(ArrayList<String> list);
    }
}
