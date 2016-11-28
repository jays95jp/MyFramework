package com.kevadiyakrunalk.rxfilepicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * The type File pick activity.
 */
public class FilePickActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            handleIntent(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        intent = new Intent(getApplicationContext(), FilePickerActivity.class);
        startActivityForResult(intent, FilePickerConst.REQUEST_CODE_DOC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        RxFilePicker.getInstance(this).onActivityResult(requestCode, resultCode, data);
        finish();
    }
}