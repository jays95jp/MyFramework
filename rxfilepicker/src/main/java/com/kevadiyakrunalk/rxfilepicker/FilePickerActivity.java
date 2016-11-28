package com.kevadiyakrunalk.rxfilepicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.kevadiyakrunalk.rxfilepicker.R;
import com.kevadiyakrunalk.rxfilepicker.fragment.DocFragment;
import com.kevadiyakrunalk.rxfilepicker.fragment.DocPickerFragment;
import com.kevadiyakrunalk.rxfilepicker.util.FragmentUtil;

import java.util.ArrayList;

/**
 * The type File picker activity.
 */
public class FilePickerActivity extends AppCompatActivity implements DocFragment.PhotoPickerFragmentListener,
        PickerManagerListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(PickerManager.getInstance().getTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_activity);

        if (savedInstanceState == null) {
            initView();
        }
    }

    private void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            setUpToolbar();

            ArrayList<String> selectedPaths = intent.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS);

            setToolbarTitle(0);

            PickerManager.getInstance().setPickerManagerListener(this);
            openSpecificFragment(selectedPaths);
        }
    }

    private void setUpToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void openSpecificFragment(ArrayList<String> selectedPaths) {
        DocPickerFragment photoFragment = DocPickerFragment.newInstance(selectedPaths);
        FragmentUtil.addFragment(this, R.id.container, photoFragment);
    }

    private void setToolbarTitle(int count) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (count > 0)
                actionBar.setTitle("Attachments (" + count + "/" + PickerManager.getInstance().getMaxCount() + ")");
            else {
                actionBar.setTitle("Select a document");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_file_picker, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_done) {
            Intent intent = new Intent();
            intent.putStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS, PickerManager.getInstance().getSelectedFiles());
            setResult(RESULT_OK, intent);
            finish();
            return true;
        } else if (i == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(int currentCount) {
        setToolbarTitle(currentCount);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
    }
}
