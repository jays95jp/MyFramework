package com.kevadiyak.rxfilepicker.util;

import com.kevadiyak.rxfilepicker.cursors.DocScannerTask;
import com.kevadiyak.rxfilepicker.cursors.loadercallbacks.FileResultCallback;
import com.kevadiyak.rxfilepicker.model.Document;

/**
 * The type Media store helper.
 */
public class MediaStoreHelper {

    /**
     * Gets docs.
     *
     * @param fileResultCallback the file result callback
     */
    public static void getDocs(FileResultCallback<Document> fileResultCallback) {
        new DocScannerTask(fileResultCallback).execute();
    }
}