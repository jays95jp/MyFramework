package com.kevadiyakrunalk.myframework.viewmodels;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.kevadiyakrunalk.commonutils.common.Logs;
import com.kevadiyakrunalk.mvvmarchitecture.common.BaseViewModel;
import com.kevadiyakrunalk.rxpermissions.PermissionResult;
import com.kevadiyakrunalk.rxpermissions.RxPermissions;
import com.kevadiyakrunalk.rxphotopicker.PhotoInterface;
import com.kevadiyakrunalk.rxphotopicker.RxPhotoPicker;
import com.kevadiyakrunalk.rxphotopicker.Sources;
import com.kevadiyakrunalk.rxphotopicker.Transformers;

import java.io.File;
import java.util.List;

public class PhotoFragmentViewModel extends BaseViewModel {
    private Context context;
    private Logs logs;
    private ImageView imageView;

    public PhotoFragmentViewModel(Context context, Logs logs) {
        this.context = context;
        this.logs = logs;
    }

    public void setImageView(ImageView view) {
        imageView = view;
    }

    public void onGalleryUri(View view) {
        if(RxPermissions.getInstance(context).isMarshmallow()) {
            RxPermissions.getInstance(context).checkMPermission(new PermissionResult() {
                @Override
                public void onPermissionResult(String permission, boolean granted) {
                    if(granted) {
                        RxPhotoPicker.getInstance(context)
                                .pickSingleImage(Sources.GALLERY, Transformers.URI, true, new PhotoInterface<Uri>() {
                                    @Override
                                    public void onPhotoResult(Uri uri) {
                                        logs.error("gallery", "Uri -> " + uri);
                                        imageView.setImageURI(uri);
                                    }
                                });
                    }
                }
            }, Manifest.permission.READ_EXTERNAL_STORAGE);
        } else {
            RxPhotoPicker.getInstance(context)
                    .pickSingleImage(Sources.GALLERY, Transformers.URI, true, new PhotoInterface<Uri>() {
                        @Override
                        public void onPhotoResult(Uri uri) {
                            logs.error("gallery", "Uri -> " + uri);
                            imageView.setImageURI(uri);
                        }
                    });
        }
    }

    public void onGalleryBitmap(View view) {
        if(RxPermissions.getInstance(context).isMarshmallow()) {
            RxPermissions.getInstance(context).checkMPermission(new PermissionResult() {
                @Override
                public void onPermissionResult(String permission, boolean granted) {
                    if(granted) {
                        RxPhotoPicker.getInstance(context)
                                .pickSingleImage(Sources.GALLERY, Transformers.BITMAP, true, new PhotoInterface<Bitmap>() {
                                    @Override
                                    public void onPhotoResult(Bitmap bitmap) {
                                        logs.error("gallery", "Bitmap -> " + bitmap.toString());
                                        imageView.setImageBitmap(bitmap);
                                    }
                                });
                    }
                }
            }, Manifest.permission.READ_EXTERNAL_STORAGE);
        } else {
            RxPhotoPicker.getInstance(context)
                    .pickSingleImage(Sources.GALLERY, Transformers.BITMAP, true, new PhotoInterface<Bitmap>() {
                        @Override
                        public void onPhotoResult(Bitmap bitmap) {
                            logs.error("gallery", "Bitmap -> " + bitmap.toString());
                            imageView.setImageBitmap(bitmap);
                        }
                    });
        }
    }

    public void onGalleryFile(View view) {
        if(RxPermissions.getInstance(context).isMarshmallow()) {
            RxPermissions.getInstance(context).checkMPermission(new PermissionResult() {
                @Override
                public void onPermissionResult(String permission, boolean granted) {
                    if(granted) {
                        RxPhotoPicker.getInstance(context)
                                .pickSingleImage(Sources.GALLERY, Transformers.FILE, true, new PhotoInterface<File>() {
                                    @Override
                                    public void onPhotoResult(File file) {
                                        logs.error("gallery", "File -> " + file.getName());
                                        imageView.setImageURI(Uri.parse(file.getPath()));
                                    }
                                }, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));//context.getFilesDir());
                    }
                }
            }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            RxPhotoPicker.getInstance(context)
                    .pickSingleImage(Sources.GALLERY, Transformers.FILE, true, new PhotoInterface<File>() {
                        @Override
                        public void onPhotoResult(File file) {
                            logs.error("gallery", "File -> " + file.getName());
                            imageView.setImageURI(Uri.parse(file.getPath()));
                        }
                    }, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));//context.getFilesDir());
        }
    }

    public void onCameraUri(View view) {
        if(RxPermissions.getInstance(context).isMarshmallow()) {
            RxPermissions.getInstance(context).checkMPermission(new PermissionResult() {
                @Override
                public void onPermissionResult(String permission, boolean granted) {
                    if(granted) {
                        RxPhotoPicker.getInstance(context)
                                .pickSingleImage(Sources.CAMERA, Transformers.URI, true, new PhotoInterface<Uri>() {
                                    @Override
                                    public void onPhotoResult(Uri uri) {
                                        logs.error("camera", "Uri -> " + uri);
                                        imageView.setImageURI(uri);
                                    }
                                });
                    }
                }
            }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
        } else {
            RxPhotoPicker.getInstance(context)
                    .pickSingleImage(Sources.CAMERA, Transformers.URI, true, new PhotoInterface<Uri>() {
                        @Override
                        public void onPhotoResult(Uri uri) {
                            logs.error("camera", "Uri -> " + uri);
                            imageView.setImageURI(uri);
                        }
                    });
        }
    }

    public void onCameraBitmap(View view) {
        if(RxPermissions.getInstance(context).isMarshmallow()) {
            RxPermissions.getInstance(context).checkMPermission(new PermissionResult() {
                @Override
                public void onPermissionResult(String permission, boolean granted) {
                    if(granted) {
                        RxPhotoPicker.getInstance(context)
                                .pickSingleImage(Sources.CAMERA, Transformers.BITMAP, true, new PhotoInterface<Bitmap>() {
                                    @Override
                                    public void onPhotoResult(Bitmap bitmap) {
                                        logs.error("camera", "Bitmap -> " + bitmap.toString());
                                        imageView.setImageBitmap(bitmap);
                                    }
                                });
                    }
                }
            }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
        } else {
            RxPhotoPicker.getInstance(context)
                    .pickSingleImage(Sources.CAMERA, Transformers.BITMAP, true, new PhotoInterface<Bitmap>() {
                        @Override
                        public void onPhotoResult(Bitmap bitmap) {
                            logs.error("camera", "Bitmap -> " + bitmap.toString());
                            imageView.setImageBitmap(bitmap);
                        }
                    });
        }
    }

    public void onCameraFile(View view) {
        if(RxPermissions.getInstance(context).isMarshmallow()) {
            RxPermissions.getInstance(context).checkMPermission(new PermissionResult() {
                @Override
                public void onPermissionResult(String permission, boolean granted) {
                    if(granted) {
                        RxPhotoPicker.getInstance(context)
                                .pickSingleImage(Sources.CAMERA, Transformers.FILE, true, new PhotoInterface<File>() {
                                    @Override
                                    public void onPhotoResult(File file) {
                                        logs.error("camera", "File -> " + file.getName());
                                        imageView.setImageURI(Uri.parse(file.getPath()));
                                    }
                                }, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));//context.getFilesDir());
                    }
                }
            }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
        } else {
            RxPhotoPicker.getInstance(context)
                    .pickSingleImage(Sources.CAMERA, Transformers.FILE, true, new PhotoInterface<File>() {
                        @Override
                        public void onPhotoResult(File file) {
                            logs.error("camera", "File -> " + file.getName());
                            imageView.setImageURI(Uri.parse(file.getPath()));
                        }
                    }, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));//context.getFilesDir());
        }

    }

    public void onGalleryMultipleUri(View view) {
        if(RxPermissions.getInstance(context).isMarshmallow()) {
            RxPermissions.getInstance(context).checkMPermission(new PermissionResult() {
                @Override
                public void onPermissionResult(String permission, boolean granted) {
                    RxPhotoPicker.getInstance(context)
                            .pickMultipleImage(Transformers.URI, new PhotoInterface<List<Uri>>() {
                                @Override
                                public void onPhotoResult(List<Uri> uri) {
                                    for (Uri uri1 : uri) {
                                        logs.error("gallery multiple", "Uri -> " + uri1);
                                    }
                                }
                            });
                }
            }, Manifest.permission.READ_EXTERNAL_STORAGE);
        } else {
            RxPhotoPicker.getInstance(context)
                    .pickMultipleImage(Transformers.URI, new PhotoInterface<List<Uri>>() {
                        @Override
                        public void onPhotoResult(List<Uri> uri) {
                            for (Uri uri1 : uri) {
                                logs.error("gallery multiple", "Uri -> " + uri1);
                            }
                        }
                    });
        }
    }

    public void onGalleryMultipleBitmap(View view) {
        if(RxPermissions.getInstance(context).isMarshmallow()) {
            RxPermissions.getInstance(context).checkMPermission(new PermissionResult() {
                @Override
                public void onPermissionResult(String permission, boolean granted) {
                    if(granted) {
                        RxPhotoPicker.getInstance(context)
                                .pickMultipleImage(Transformers.BITMAP, new PhotoInterface<List<Bitmap>>() {
                                    @Override
                                    public void onPhotoResult(List<Bitmap> bitmap) {
                                        for (Bitmap bitmap1 : bitmap) {
                                            logs.error("gallery multiple", "Bitmap -> " + bitmap1.toString());
                                        }
                                    }
                                });
                    }
                }
            }, Manifest.permission.READ_EXTERNAL_STORAGE);
        } else {
            RxPhotoPicker.getInstance(context)
                    .pickMultipleImage(Transformers.BITMAP, new PhotoInterface<List<Bitmap>>() {
                        @Override
                        public void onPhotoResult(List<Bitmap> bitmap) {
                            for (Bitmap bitmap1 : bitmap) {
                                logs.error("gallery multiple", "Bitmap -> " + bitmap1.toString());
                            }
                        }
                    });
        }
    }

    public void onGalleryMultipleFile(View view) {
        if(RxPermissions.getInstance(context).isMarshmallow()) {
            RxPermissions.getInstance(context).checkMPermission(new PermissionResult() {
                @Override
                public void onPermissionResult(String permission, boolean granted) {
                    if(granted) {
                        RxPhotoPicker.getInstance(context)
                                .pickMultipleImage(Transformers.FILE, new PhotoInterface<List<File>>() {
                                    @Override
                                    public void onPhotoResult(List<File> file) {
                                        for(File file1 : file) {
                                            logs.error("gallery multiple", "File -> " + file1.getName());
                                        }
                                    }
                                }, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));//context.getFilesDir());
                    }
                }
            }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            RxPhotoPicker.getInstance(context)
                    .pickMultipleImage(Transformers.FILE, new PhotoInterface<List<File>>() {
                        @Override
                        public void onPhotoResult(List<File> file) {
                            for(File file1 : file) {
                                logs.error("gallery multiple", "File -> " + file1.getName());
                            }
                        }
                    }, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));//context.getFilesDir());
        }
    }
}
