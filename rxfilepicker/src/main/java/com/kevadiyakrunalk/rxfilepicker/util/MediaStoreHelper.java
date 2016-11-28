package com.kevadiyakrunalk.rxfilepicker.util;

import com.kevadiyakrunalk.rxfilepicker.cursors.DocScannerTask;
import com.kevadiyakrunalk.rxfilepicker.cursors.loadercallbacks.FileResultCallback;
import com.kevadiyakrunalk.rxfilepicker.model.Document;

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