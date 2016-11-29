package com.kevadiyakrunalk.rxphotopicker;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.Log;

//import com.mylibrary.common.FileUtil;
import com.kevadiyakrunalk.commonutils.common.FileUtil;
import com.yalantis.ucrop.UCrop;

import java.util.ArrayList;

/**
 * The type Photo activity.
 */
public class PhotoActivity extends Activity {
    private static final String KEY_CAMERA_PICTURE_URL = "cameraPictureUrl";
    private static final String KEY_CROP_PICTURE_URL = "cropPictureUrl";

    /**
     * The constant IMAGE_SOURCE.
     */
    public static final String IMAGE_SOURCE = "image_source";
    /**
     * The constant ALLOW_MULTIPLE_IMAGES.
     */
    public static final String ALLOW_MULTIPLE_IMAGES = "allow_multiple_images";
    public static final String ALLOW_IMAGE_CROP = "allow_image_crop";

    private Uri cameraPictureUrl, cropPictureUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            handleIntent(getIntent());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_CAMERA_PICTURE_URL, cameraPictureUrl);
        outState.putParcelable(KEY_CROP_PICTURE_URL, cropPictureUrl);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        cameraPictureUrl = savedInstanceState.getParcelable(KEY_CAMERA_PICTURE_URL);
        cropPictureUrl = savedInstanceState.getParcelable(KEY_CROP_PICTURE_URL);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.SELECT_PHOTO:
                    handleGalleryResult(data);
                    break;
                case Constant.TAKE_PHOTO:
                    handleCameraResult(cameraPictureUrl);
                    break;

                case UCrop.REQUEST_CROP:
                    Uri resultUri = UCrop.getOutput(data);
                    RxPhotoPicker.getInstance(getApplicationContext()).onImagePicked(resultUri);
                    finish();
                    break;
            }
        } else {
            if(requestCode == UCrop.REQUEST_CROP) {
                Log.e("Error", "Ucrop");
                Throwable cropError = UCrop.getError(data);
                cropError.printStackTrace();
            }
            finish();
        }
    }

    private void handleCameraResult(Uri cameraPictureUrl) {
        if(getIntent().getBooleanExtra(ALLOW_IMAGE_CROP, false)) {
            cropPictureUrl = FileUtil.getInstance(getApplicationContext()).createImageUri();
            UCrop.of(cameraPictureUrl, cropPictureUrl)
                    .start(this);
        } else
            RxPhotoPicker.getInstance(getApplicationContext()).onImagePicked(cameraPictureUrl);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void handleGalleryResult(Intent data) {
        if (getIntent().getBooleanExtra(ALLOW_MULTIPLE_IMAGES, false)) {
            ArrayList<Uri> imageUris = new ArrayList<>();
            ClipData clipData = data.getClipData();
            if (clipData != null) {
                int size = clipData.getItemCount();
                for (int i = 0; i < size; i++) {
                    imageUris.add(clipData.getItemAt(i).getUri());
                }
            } else {
                imageUris.add(data.getData());
            }
            RxPhotoPicker.getInstance(getApplicationContext()).onImagesPicked(imageUris);
        } else {
            if(getIntent().getBooleanExtra(ALLOW_IMAGE_CROP, false)) {
                cropPictureUrl = FileUtil.getInstance(getApplicationContext()).createImageUri();
                UCrop.of(data.getData(), cropPictureUrl)
                        .start(this);
            } else
                RxPhotoPicker.getInstance(getApplicationContext()).onImagePicked(data.getData());
        }
    }

    private void handleIntent(Intent intent) {
        Sources sourceType = Sources.values()[intent.getIntExtra(IMAGE_SOURCE, 0)];
        final int[] chooseCode = {0};
        final Intent[] pictureChooseIntent = {null};

        switch (sourceType) {
            case CAMERA:
                cameraPictureUrl = FileUtil.getInstance(getApplicationContext()).createImageUri();
                pictureChooseIntent[0] = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                pictureChooseIntent[0].putExtra(MediaStore.EXTRA_OUTPUT, cameraPictureUrl);
                chooseCode[0] = Constant.TAKE_PHOTO;
                startActivityForResult(pictureChooseIntent[0], chooseCode[0]);
                break;
            case GALLERY:
                pictureChooseIntent[0] = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    //pictureChooseIntent[0] = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    pictureChooseIntent[0].putExtra(Intent.EXTRA_ALLOW_MULTIPLE, getIntent().getBooleanExtra(ALLOW_MULTIPLE_IMAGES, false));
                    pictureChooseIntent[0].addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);

                } else {
                    //pictureChooseIntent[0] = new Intent(Intent.ACTION_GET_CONTENT);
                }
                //pictureChooseIntent[0].putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                pictureChooseIntent[0].addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                pictureChooseIntent[0].setType("image/*");
                chooseCode[0] = Constant.SELECT_PHOTO;
                startActivityForResult(pictureChooseIntent[0], chooseCode[0]);
                break;
        }
    }
}