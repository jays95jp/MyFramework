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
import com.kevadiyakrunalk.rxphotopicker.CropOption;
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
        CropOption.Builder builder = new CropOption.Builder();
        builder.setOutputHW(690, 690);
        builder.setAspectRatio(3, 2);
        builder.setScale(true);

        if(RxPermissions.getInstance(context).isMarshmallow()) {
            RxPermissions.getInstance(context).checkMPermission(new PermissionResult() {
                @Override
                public void onPermissionResult(String permission, boolean granted) {
                    if(granted) {
                        RxPhotoPicker.getInstance(context)
                                .pickSingleImage(Sources.GALLERY, Transformers.URI, true, builder, new PhotoInterface<Uri>() {
                                    @Override
                                    public void onPhotoResult(Uri uri) {
                                        if(uri != Uri.EMPTY) {
                                            logs.error("gallery", "Uri -> " + uri);
                                            imageView.setImageURI(uri);
                                        } else
                                            logs.error("gallery", "Uri -> EMPTY");
                                    }
                                });
                    }
                }
            }, Manifest.permission.READ_EXTERNAL_STORAGE);
        } else {
            RxPhotoPicker.getInstance(context)
                    .pickSingleImage(Sources.GALLERY, Transformers.URI, true, builder, new PhotoInterface<Uri>() {
                        @Override
                        public void onPhotoResult(Uri uri) {
                            if(uri != Uri.EMPTY) {
                                logs.error("gallery", "Uri -> " + uri);
                                imageView.setImageURI(uri);
                            } else
                                logs.error("gallery", "Uri -> EMPTY");
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
                                        if(bitmap != null) {
                                            logs.error("gallery", "Bitmap -> " + bitmap.toString());
                                            imageView.setImageBitmap(bitmap);
                                        } else
                                            logs.error("gallery", "Bitmap -> NULL");
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
                            if(bitmap != null) {
                                logs.error("gallery", "Bitmap -> " + bitmap.toString());
                                imageView.setImageBitmap(bitmap);
                            } else
                                logs.error("gallery", "Bitmap -> NULL");
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
                                        if(file != null) {
                                            logs.error("gallery", "File -> " + file.getName() + " ,Size -> " + fileSize(file.length()));
                                            imageView.setImageURI(Uri.parse(file.getPath()));
                                        } else
                                            logs.error("gallery", "File -> NULL");
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
                            if(file != null) {
                                logs.error("gallery", "File -> " + file.getName() + " ,Size -> " + fileSize(file.length()));
                                imageView.setImageURI(Uri.parse(file.getPath()));
                            } else
                                logs.error("gallery", "File -> NULL");
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
                                        if(uri != Uri.EMPTY) {
                                            logs.error("camera", "Uri -> " + uri);
                                            imageView.setImageURI(uri);
                                        } else
                                            logs.error("camera", "Uri -> EMPTY");
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
                            if(uri != Uri.EMPTY) {
                                logs.error("camera", "Uri -> " + uri);
                                imageView.setImageURI(uri);
                            } else
                                logs.error("camera", "Uri -> EMPTY");
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
                                        if(bitmap != null) {
                                            logs.error("camera", "Bitmap -> " + bitmap.toString());
                                            imageView.setImageBitmap(bitmap);
                                        } else
                                            logs.error("camera", "Bitmap -> NULL");
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
                            if(bitmap != null) {
                                logs.error("camera", "Bitmap -> " + bitmap.toString());
                                imageView.setImageBitmap(bitmap);
                            } else
                                logs.error("camera", "Bitmap -> NULL");
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
                                        if(file != null) {
                                            logs.error("camera", "File -> " + file.getName() + " ,Size -> " + fileSize(file.length()));
                                            imageView.setImageURI(Uri.parse(file.getPath()));
                                        } else
                                            logs.error("camera", "File -> NULL");
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
                            if(file != null) {
                                logs.error("camera", "File -> " + file.getName() + " ,Size -> " + fileSize(file.length()));
                                imageView.setImageURI(Uri.parse(file.getPath()));
                            } else
                                logs.error("camera", "File -> NULL");
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
                                        if(uri1 != Uri.EMPTY) {
                                            logs.error("gallery multiple", "Uri -> " + uri1);
                                            imageView.setImageURI(uri1);
                                        } else
                                            logs.error("gallery multiple", "Uri -> EMPTY");
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
                                if(uri1 != Uri.EMPTY) {
                                    logs.error("gallery multiple", "Uri -> " + uri1);
                                    imageView.setImageURI(uri1);
                                } else
                                    logs.error("gallery multiple", "Uri -> EMPTY");
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
                                            if(bitmap1 != null) {
                                                logs.error("gallery multiple", "Bitmap -> " + bitmap1.toString());
                                                imageView.setImageBitmap(bitmap1);
                                            } else
                                                logs.error("gallery multiple", "Bitmap -> NULL");
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
                                if(bitmap1 != null) {
                                    logs.error("gallery multiple", "Bitmap -> " + bitmap1.toString());
                                    imageView.setImageBitmap(bitmap1);
                                } else
                                    logs.error("gallery multiple", "Bitmap -> NULL");
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
                                            if(file1 != null) {
                                                logs.error("gallery multiple", "File -> " + file1.getName() + " ,Size -> " + fileSize(file1.length()));
                                                imageView.setImageURI(Uri.parse(file1.getPath()));
                                            } else
                                                logs.error("gallery multiple", "File -> NULL");
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
                                if(file1 != null) {
                                    logs.error("gallery multiple", "File -> " + file1.getName() + " ,Size -> " + fileSize(file1.length()));
                                    imageView.setImageURI(Uri.parse(file1.getPath()));
                                } else
                                    logs.error("gallery multiple", "File -> NULL");
                            }
                        }
                    }, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));//context.getFilesDir());
        }
    }

    public String fileSize(long size) {
        long kb = size / 1024;
        if(kb >= 1024)
            return (kb/1024) + " Mb";
        else
            return kb + " Kb";
    }
}
