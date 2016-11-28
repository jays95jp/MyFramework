package com.kevadiyakrunalk.rxfilepicker.cursors;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.kevadiyakrunalk.rxfilepicker.PickerManager;
import com.kevadiyakrunalk.rxfilepicker.cursors.loadercallbacks.FileResultCallback;
import com.kevadiyakrunalk.rxfilepicker.model.Document;
import com.kevadiyakrunalk.rxfilepicker.model.FileType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Doc scanner task.
 */
public class DocScannerTask extends AsyncTask<Void, Void, List<Document>> {
    private final FileResultCallback<Document> resultCallback;

    /**
     * Instantiates a new Doc scanner task.
     *
     * @param fileResultCallback the file result callback
     */
    public DocScannerTask(FileResultCallback<Document> fileResultCallback) {
        this.resultCallback = fileResultCallback;
    }

    @Override
    protected List<Document> doInBackground(Void... voids) {
        ArrayList<Document> documents = new ArrayList<>();
        return getAllFilesOfDir(PickerManager.getInstance().getDirectory(), documents);
    }

    @Override
    protected void onPostExecute(List<Document> documents) {
        super.onPostExecute(documents);
        if (resultCallback != null) {
            resultCallback.onResultCallback(documents);
        }
    }

    private ArrayList<Document> getAllFilesOfDir(File directory, ArrayList<Document> documents) {
        final File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file != null) {
                    if (file.isDirectory()) {  // it is a folder...
                        getAllFilesOfDir(file, documents);
                    } else {  // it is a file...
                        String path = file.getAbsolutePath();
                        String title = file.getName();
                        if (path != null && contains(path)) {
                            Document document = new Document(documents.size(), title, path);

                            String mimeType = "";
                            try {
                                mimeType = file.toURI().toURL().openConnection().getContentType();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (mimeType != null && !TextUtils.isEmpty(mimeType))
                                document.setMimeType(mimeType);
                            else
                                document.setMimeType("");

                            document.setSize(file.length() + "");

                            if (!documents.contains(document))
                                documents.add(document);
                        }
                    }
                }
            }
        }
        return documents;
    }

    /**
     * Contains boolean.
     *
     * @param path the path
     * @return the boolean
     */
    boolean contains(String path) {
        //Log.e("File", path);
        ArrayList<FileType> fileTypes = PickerManager.getInstance().getFilesType();
        for (FileType type: fileTypes) {
            String[] types = type.getGroupExtension().split(",");
            for (String string : types) {
                if (path.endsWith(string))
                    return true;
            }
        }
        return false;
    }
}
