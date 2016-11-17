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

### 3. Photo Picker
* you can pick image from GALLERY or CAMERA with three type of transformers like URI, BITMAP and FILE.
* there are two type of Gallery image pick single and multiple with max limit.
* also it's handle the drive/google photo pick.
* Code
```groovy
//add dependencies for app level build.gradle
dependencies {
  compile 'com.kevadiyak:rxphotopicker:1.0.0@aar'
}
```
```java
// - Single image pick
   RxPhotoPicker
    .getInstance(context)
    .pickSingleImage(
      Sources.GALLERY /*you have use source as a pick from gallery or camera*/, 
      Transformers.URI /*you have set Transformers as your actual image getting format like Uri, Bitmap or File*/, 
      new PhotoInterface<
       Uri/*Set argument based on your transformers for example Transformers.URI to Uri, Transformers.BITMAP to Bitmap*/>(){
         @Override
         public void onPhotoResult(Uri uri) {
           //here is you output based on Transformers Like URI, BITMAP or FILE.
         }
       }, 
       Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) /*[Option param] this param only use when you have pick image as a file format*/);
// - Multiple image pick
   RxPhotoPicker
    .getInstance(context)
    .pickMultipleImage(
      Transformers.URI /*you have set Transformers as your actual image getting format like Uri, Bitmap or File*/,
      new PhotoInterface<List<Uri
       /*Set argument based on your transformers for example Transformers.URI to Uri, Transformers.BITMAP to Bitmap*/>>() { 
        @Override
        public void onPhotoResult(List<Uri> uri) {
          //here is you output based on Transformers Like URI, BITMAP or FILE.
        }
     }, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
```
### 4. Permission
* you can directly use with two line of code, no additional code required like onRequestPermissionsResult or check the granted or not.

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

### 10. RecycleView Adapter
* in this feature you can create adapter with any type of data using single line of code.
* and provide the onclick, onlongclick of rootview and also you can apply individual view based on ResId.
* you will get onBind listener if required for any further customisation as per your requirements.
* set load more listener for on demand based data load.
* also provide the custom layout handler as per your requirements.
* both side swipe menu support.
* multiple layout support (header, footer, customs xml etc).
* Also I used databinding concept, so don’t worry about null value :) 

### 11. Log-cat
* for use display your value in console with class name, which line number to call and your data set.

### 12. Image Compressor
* you can compress image with based on maxWidth, maxHeight, format and quality.

### 13. Drawable Color Change
* change drawable color for example your image is white color then you can change same image into red color.

### 14. EventBus
* used for data passing from one class to other class.

### Setting
* app level build.gradle file to use.
```groovy
repositories {
    jcenter()
    mavenCentral()
    /* maven { url 'https://dl.bintray.com/kevadiyak/MyFramework/' } */
}
dependencies {
    compile 'com.kevadiyak:commonutils:1.0.0@aar' /* Common Utils */
    compile 'com.kevadiyak:customfont:1.0.0@aar' /* Custom font */
    compile 'com.kevadiyak:mvvmarchitecture:1.0.0@aar' /* MVVM Architecture */
    compile 'com.kevadiyak:recycleadapter:1.0.0@aar' /* Recycleview Adapter */
    compile 'com.kevadiyak:rxdialog:1.0.0@aar' /* Alert/Prompt/Progress Dialog */
    compile 'com.kevadiyak:rxfilepicker:1.0.0@aar' /* File Picker */
    compile 'com.kevadiyak:rxlocation:1.0.0@aar' /* Location */
    compile 'com.kevadiyak:rxpermissions:1.0.0@aar' /* Runtime Permission */
    compile 'com.kevadiyak:rxphotopicker:1.0.0@aar' /* Photo/Image Picker */
    compile 'com.kevadiyak:rxpreference:1.0.0@aar' /* Save or Get data from preference */
    compile 'com.kevadiyak:rxvalidation:1.0.0@aar' /* Validation */  
}
```
