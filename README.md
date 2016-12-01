This framework include below features :
* MVVM architecture.
* Dialog.
* Photo Picker.
* Font.
* Permission.
* Preference.
* Validation.
* Location.
* File Picker.
* RecycleView Adapter.
* Log-cat.
* Image Compressor.
* Drawable Color Change.
* EventBus

### 1. MVVM architecture
* in this struct i have divided the whole project into four part 
  * ACTIVITY
  * FRAGMENT
  * VIEWMODEL
  * NAVIGATOR.
* all of you know about the activity, fragment and viewmodel but I have totally change this things like below.
* ACTIVITY will divid in two ways ```java MvvmActivity<BindObject, ViewModelObject> AND NavigatingMvvmActivity<NavigatorObject, BindObject, ViewModelObject>```.
* FRAGMENT will divid in two ways ```java MvvmFragment<BindObject, ViewModelObject> AND NavigatingMvvmFramgment<NavigatorObject, BindObject, ViewModelObject>```.
* DIALOGFRAGMENT will divid in two ways ```java NavigatingMvvmDialogFragment<BindObject, ViewModelObject> AND NavigatingMvvmDialogFragment<NavigatorObject, BindObject, ViewModelObject>```.
* also it will handle the ACTIVITY AND FRAGMNET event for binding the other component for example you have display progressdialog and press back then dialog will automatically cancel at that time when you use activity or fragment dialog.

### 2. Dialog
* i have create two type of dialog 1.Prompt and 2.Alert dialog.
* Prompt dialog use for message display purpose with type(INFO, HELP, ERROR/WRONG, SUCCESS, WARNING).
* Alert dialog use for message or customise.
* Code :-
```groovy
//Add Dependencies for app level build.gradle
repositories {
    jcenter()
    mavenCentral()
}
dependencies {
  compile 'com.kevadiyakrunalk:rxdialog:1.0@aar'
}
```
```java
//- Prompt Dialog
    new RxPromptDialog
         .Builder(this)
         .title(R.string.dialog_title) //for dialog title 
         .message(R.string.dialog_message) //for user mnessage
         .cancellable(Boolean.TRUE)
         .type(DialogType.INFO) //type of prompt to given user INFO, SUCCESS, WRONG/FAIL, HELP etc.
         .canceledOnTouchOutside(Boolean.FALSE)
         
          // set dialog button, use Either singleButton or doubleButton
         .singleButton(R.string.dialog_cancel)
         .doubleButton(context.getString(R.string.dialog_ok), context.getString(R.string.dialog_cancel))
         
         // set animation default or custom, use Either setDefaultAnimation or setInAnimation & setOutAnimation
         .setDefaultAnimation(true)
         .setInAnimation(animIn)
         .setOutAnimation(animOut)
         
         .toObservable()
         .observeOn(AndroidSchedulers.mainThread())
         .subscribe(new Action1<Integer>() {
             @Override
             public void call(Integer which) {
                 Log.e("Alert", "Which->" + which); 
                 // Button id return for 
                 // two button  - POSITIVE = -1, NEGATIVE = -2
                 // one button  - NEUTRAL = -3
                 // dialog cancel - CANCEL = -4
                 // dialog dismiss - DISMISS = -5
             }
             }
         });
         
//- Alert Dialog
    new RxAlertDialog
          .Builder(this)
          .title(R.string.dialog_title)
          .message(R.string.dialog_message) // for user message
          .image(R.drawable.sample_img) //[optional] with image
          .view(R.layout.custom_dialog) //[optional] set custom view 
          .cancellable(Boolean.FALSE)
          .canceledOnTouchOutside(Boolean.FALSE)
          
          // set dialog button, use Either singleButton or doubleButton
          .singleButton(R.string.dialog_cancel)
          .doubleButton(context.getString(R.string.dialog_ok), context.getString(R.string.dialog_cancel))
          
          // set animation default or custom, use Either setDefaultAnimation or setInAnimation & setOutAnimation
          .setDefaultAnimation(true)
          .setInAnimation(animIn)
          .setOutAnimation(animOut)
         
          .toObservable()
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Action1<Integer>() {
              @Override
              public void call(Integer which) {
                  Log.e("Alert", "Which->" + which);
              }
          })          
//- Progress Dialog 
     new RxProgressDialog
           .Builder(this)
           .message("Please Wait…")
           .cancellable(false)
           .toObservable(zipObservable)
           .compose(dialogFragment.<String>bindUntilEvent(FragmentEvent.PAUSE)) //[optional] for bind progress in activity or fragment life cycle.
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe(new Subscriber<String>() {
               @Override
               public void onCompleted() {

               }

               @Override
               public void onError(Throwable e) {
                   Log.e("RESULT", e.toString());
               }

               @Override
               public void onNext(String s) {
                   Log.e("RESULT", s);
               }
           });
```
### 3. Photo Picker
* you can pick image from GALLERY or CAMERA with three type of transformers like URI, BITMAP and FILE.
* there are two type of Gallery image pick single and multiple with max limit.
* also it's handle the drive/google photo pick.
* Code :-
```groovy
//add dependencies for app level build.gradle
repositories {
    jcenter()
    mavenCentral()
}
dependencies {
  compile 'com.kevadiyakrunalk:rxphotopicker:1.0@aar'
}
```
```java
// - Single image pick
   RxPhotoPicker
    .getInstance(this)
    .pickSingleImage(
      Sources.GALLERY /*you have use source as a pick from gallery or camera*/, 
      Transformers.URI /*you have set Transformers as your actual image getting format like Uri, Bitmap or File*/, 
      new PhotoInterface<
       Uri/*Set argument based on your transformers for example Transformers.URI to Uri, Transformers.BITMAP to Bitmap*/>(){
         @Override
         public void onPhotoResult(Uri uri) {
           //here is your output based on Transformers Like URI, BITMAP or FILE.
         }
       }, 
       Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) /*[Option param] this param only use when you have pick image as a file format*/);
// - Multiple image pick
   RxPhotoPicker
    .getInstance(this)
    .pickMultipleImage(
      Transformers.URI /*you have set Transformers as your actual image getting format like Uri, Bitmap or File*/,
      new PhotoInterface<List<Uri
       /*Set argument based on your transformers for example Transformers.URI to Uri, Transformers.BITMAP to Bitmap*/>>() { 
        @Override
        public void onPhotoResult(List<Uri> uri) {
          //here is your output based on Transformers Like URI, BITMAP or FILE.
        }
     }, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
```
### 4. Permission
* you can directly use with two line of code, no additional code required like onRequestPermissionsResult or check the granted or not.
* Code :-
```groovy
//add dependencies for app level build.gradle
repositories {
    jcenter()
    mavenCentral()
}
dependencies {
  compile 'com.kevadiyakrunalk:rxpermissions:1.0@aar'
}
```
```java
// - Single
   RxPermissions
    .getInstance(this)
    .checkMPermission(new PermissionResult() {
       @Override
       public void onPermissionResult(String permission, boolean granted) {
         //here is your permission list by comma separate and result of granted or not.
       }
    }, Manifest.permission.CAMERA);
// - Multiple
   RxPermissions
    .getInstance(this)
    .checkMPermission(new PermissionResult() {
        @Override
        public void onPermissionResult(String permission, boolean granted) {
          //here is your permission list by comma separate and result of granted or not.
        }
     }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
```
### 5. Custom Font
* in this feature you can directly set custom font with using set application level default, set layout based on attribute, set particular class based and style based.
  
### 6. Preference
* you can use two ways simple and with RxJava also it will apply ENCRYPTION MODEL, so your data will be secure.

### 7. Validation
* in this feature you can apply the basic validation like Age, Digit, Email, InList, Ip4, Length, MaxLength, MinLength, NonEmpty, Pattern, Phone and Custom etc.
* also you can apply onValueChanged and onFocusChanged event/listener.

### 8. Location
* get current location, search places, get address from latitude and longitude.
* allow geofence.
* mock Location set.
* search please to get detail.

### 9. File Picker
* you can pick file from particular DIR and also apply a group of file extension for example you will pick PPT{ppt, pptx}, DOC{doc, docx, dot, dotx}.
* you can select/pick multiple files from particular DIR with max file counter limit.
* Code :-
```groovy
//add dependencies for app level build.gradle
repositories {
    jcenter()
    mavenCentral()
}
dependencies {
  compile 'com.kevadiyakrunalk:rxfilepicker:1.0@aar'
}
```
```xml
<style name="FilePickerTheme" parent="Theme.AppCompat.Light.DarkActionBar">
    <!-- Customize your theme here. -->
    <item name="colorPrimary">@color/colorPrimary</item>
    <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
    <item name="colorAccent">@color/colorAccent</item>
</style>
```    
```java
   private int MAX_ATTACHMENT_COUNT = 5;
   private ArrayList<FileType> fileTypes;
   fileTypes = new ArrayList<>();
   FileType fileType = new FileType();
   fileType.setGroupTitle("PDF");
   fileType.setGroupIcon(R.drawable.ic_pdf);
   fileType.setGroupExtension("pdf");
   fileTypes.add(fileType);

   fileType = new FileType();
   fileType.setGroupTitle("PPT");
   fileType.setGroupIcon(R.drawable.icon_ppt);
   fileType.setGroupExtension("ppt,pptx");
   fileTypes.add(fileType);

   fileType = new FileType();
   fileType.setGroupTitle("DOC");
   fileType.setGroupIcon(R.drawable.ic_doc);
   fileType.setGroupExtension("doc,docx,dot,dotx");
   fileTypes.add(fileType);

   fileType = new FileType();
   fileType.setGroupTitle("XLS");
   fileType.setGroupIcon(R.drawable.ic_xls);
   fileType.setGroupExtension("xls,xlsx");
   fileTypes.add(fileType);

   fileType = new FileType();
   fileType.setGroupTitle("TXT");
   fileType.setGroupIcon(R.drawable.ic_txt);
   fileType.setGroupExtension("txt");
   fileTypes.add(fileType);
        
   RxFilePicker
    .getInstance(this)
    .setMaxCount(MAX_ATTACHMENT_COUNT)
    .setFileType(fileTypes)
    .setDirectory(Environment.getExternalStorageDirectory())
    .setActivityTheme(R.style.FilePickerTheme)
    .pickDocument(this, new RxFilePicker.FileResult() {
        @Override
        public void PickFileList(ArrayList<String> list) {
             Log.e("Files", list.toString());
        }
    });
```

### 10. RecycleView Adapter
* in this feature you can create adapter with any type of data using single line of code.
* and provide the onclick, onlongclick of rootview and also you can apply individual view based on ResId.
* you will get onBind listener if required for any further customisation as per your requirements.
* set load more listener for on demand based data load.
* also provide the custom layout handler as per your requirements.
* both side swipe menu support.
* multiple layout support (header, footer, customs xml etc).
* Also I used databinding concept, so don’t worry about null value :) 
```groovy
//add dependencies for app level build.gradle
repositories {
    jcenter()
    mavenCentral()
}
dependencies {
  compile 'com.kevadiyakrunalk:recycleadapter:1.0@aar'
}
```
```java
```

### 11. Log-cat
* for use display your value in console with class name, which line number to call and your data set.
```groovy
//add dependencies for app level build.gradle
repositories {
    jcenter()
    mavenCentral()
}
dependencies {
  compile 'com.kevadiyakrunalk:commonutils:1.0@aar'
}
```
```java
   Logs.getInstance(this).verbose(TAG, Message);
   Logs.getInstance(this).debug(TAG, Message);
   Logs.getInstance(this).info(TAG, Message);
   Logs.getInstance(this).warn(TAG, Message);
   Logs.getInstance(this).error(TAG, Message);
```
### 12. Image Compressor
* you can compress image with based on maxWidth, maxHeight, format and quality.
```groovy
//add dependencies for app level build.gradle
repositories {
    jcenter()
    mavenCentral()
}
dependencies {
  compile 'com.kevadiyakrunalk:commonutils:1.0@aar'
}
```
```java
   new Compressor.Builder(this, this.getFilesDir().getPath())
       .setMaxWidth(612.0f).setMaxHeight(816.0f).setQuality(80) //default option
       .build().compressToFileAsObservable(file)
       .subscribe(new Action1<File>() {
           @Override
           public void call(File file) {
               logs.error("Compressor", "File -> " + (file.length()/1024) + " KB");
           }
       });
```
### 13. Drawable Color Change
* change drawable color for example your image is white color then you can change same image into red color.
```groovy
//add dependencies for app level build.gradle
repositories {
    jcenter()
    mavenCentral()
}
dependencies {
  compile 'com.kevadiyakrunalk:commonutils:1.0@aar'
}
```
```java
   imageView.setImageDrawable(
     DrawableColorChange.getInstance(this).changeColorById(R.drawable.ic_file, R.color.colorAccent));
   imageView.setImageDrawable(
     DrawableColorChange.getInstance(this).changeColorByColor(ContextCompat.getDrawable(this, R.drawable.ic_file),
     Color.Red));  
```

### 14. EventBus
* used for data passing from one class to other class.

### Setting
* app level build.gradle file to use.
```groovy
repositories {
    jcenter()
    mavenCentral()
}
dependencies {
    compile 'com.kevadiyakrunalk:commonutils:1.0@aar' /* Common Utils */
    compile 'com.kevadiyakrunalk:customfont:1.0@aar' /* Custom font */
    compile 'com.kevadiyakrunalk:mvvmarchitecture:1.0@aar' /* MVVM Architecture */
    compile 'com.kevadiyakrunalk:recycleadapter:1.0@aar' /* Recycleview Adapter */
    compile 'com.kevadiyakrunalk:rxdialog:1.0@aar' /* Alert/Prompt/Progress Dialog */
    compile 'com.kevadiyakrunalk:rxfilepicker:1.0@aar' /* File Picker */
    compile 'com.kevadiyakrunalk:rxlocation:1.1@aar' /* Location */
    compile 'com.kevadiyakrunalk:rxpermissions:1.0@aar' /* Runtime Permission */
    compile 'com.kevadiyakrunalk:rxphotopicker:1.0@aar' /* Photo/Image Picker */
    compile 'com.kevadiyakrunalk:rxpreference:1.0@aar' /* Save or Get data from preference */
    compile 'com.kevadiyakrunalk:rxvalidation:1.0@aar' /* Validation */   
}
```
