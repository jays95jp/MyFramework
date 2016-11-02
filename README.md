This framework include below features :

  1. MVVM architecture.

  2. Dialog.

  3. Photo Picker.

  4. Font.

  5. Permission.

  6. Preference.

  7. Validation.

  8. Location.

  9. File Picker.

  10. RecycleView Adapter.

  11. Log-cat.

  12. Image Compressor.

  13. Drawable Color Change.

  14. EventBus


### 1. MVVM architecture ###
  - in this struct i have divided the whole project into four part 

    1.ACTIVITY

    2.FRAGMENT

    3.VIEWMODEL

    4.NAVIGATOR.

  - all of you know about the activity, fragment and viewmodel but I have totally change this things like below.

  - ACTIVITY will divid in two ways MvvmActivity<BindObject, ViewModelObject> AND NavigatingMvvmActivity<NavigatorObject, BindObject, ViewModelObject>.

  - FRAGMENT will divid in two ways MvvmFragment<BindObject, ViewModelObject> AND NavigatingMvvmFramgment<NavigatorObject, BindObject, ViewModelObject>.

  - DIALOGFRAGMENT will divid in two ways NavigatingMvvmDialogFragment<BindObject, ViewModelObject> AND NavigatingMvvmDialogFragment<NavigatorObject, BindObject, ViewModelObject>.

  - also it will handle the ACTIVITY AND FRAGMNET event for binding the other component for example you have display progressdialog and press back then dialog will automatically cancel at that time when you use activity or fragment dialog.

### 2. Dialog ###
  - i have create two type of dialog 1.Prompt and 2.Alert dialog.

  - Prompt dialog use for message display purpose with type(INFO, HELP, ERROR/WRONG, SUCCESS, WARNING).

  - Alert dialog use for message or customise.

### 3. Photo Picker ###
  - you can pick image from GALLERY or CAMERA with three type of transformers like URI, BITMAP and FILE.

  - there are two type of Gallery image pick single and multiple with max limit.

  - also it's handle the drive/google photo pick.

### 4. Font ###
  - in this feature you can directly set custom font with using set application level default, set layout based on attribute, set particular class based and style based.

### 5. Permission ###
  - you can directly use with two line of code, no additional code required like onRequestPermissionsResult or check the granted or not.

### 6. Preference ###
  - you can use two ways simple and with RxJava also it will apply ENCRYPTION MODEL, so your data will be secure.

### 7. Validation ###
  - in this feature you can apply the basic validation like Age, Digit, Email, InList, Ip4, Length, MaxLength, MinLength, NonEmpty, Pattern, Phone and Custom etc.

  - also you can apply onValueChanged and onFocusChanged event/listener.

### 8. Location ###
  - get current location, search places, get address from latitude and longitude.

  - allow geofence.

  - mock Location set.

  - search please to get detail.

### 9. File Picker ###
  - you can pick file from particular DIR and also apply a group of file extension for example you will pick PPT{ppt, pptx}, DOC{doc, docx, dot, dotx}.

  - you can select/pick multiple files from particular DIR with max file counter limit.

### 10. RecycleView Adapter ###
  - in this feature you can create adapter with any type of data using single line of code.

  - and provide the onclick, onlongclick of rootview and also you can apply individual view based on ResId.

  - you will get onBind listener if required for any further customisation as per your requirements.

  - set load more listener for on demand based data load.

  - also provide the custom layout handler as per your requirements.

  - both side swipe menu support.

  - multiple layout support (header, footer, customs xml etc).

  - Also I used databinding concept, so don’t worry about null value :) 

### 11. Log-cat ###
  - for use display your value in console with class name, which line number to call and your data set.

### 12. Image Compressor ###
  - you can compress image with based on maxWidth, maxHeight, format and quality.

### 13. Drawable Color Change ###
  - change drawable color for example your image is white color then you can change same image into red color.

### 14. EventBus ###
  - used for data passing from one class to other class.

### Setting ###
  - app level gradle file to use.
  - repositories {
      - jcenter()
      - mavenCentral()
      - maven { url 'https://dl.bintray.com/kevadiyak/MyFramework/' }
  - }
  - dependencies {
      - ### 1.Common Utils ###
        - compile 'com.kevadiyak:commonutils:1.0.0@aar'
      - ### 2. Custom font ###
        - compile 'com.kevadiyak:customfont:1.0.0@aar'
      - ### 3. MVVM Architecture ###
        - compile 'com.kevadiyak:mvvmarchitecture:1.0.0@aar'
      - ### 4. Recycleview Adapter ###
        - compile 'com.kevadiyak:recycleadapter:1.0.0@aar'
      - ### 5. Alert/Prompt/Progress Dialog ###
        - compile 'com.kevadiyak:rxdialog:1.0.0@aar'
      - ### 6. File Picker ###
        - compile 'com.kevadiyak:rxfilepicker:1.0.0@aar'
      - ### 7. Location ###
        - compile 'com.kevadiyak:rxlocation:1.0.0@aar'
      - ### 8. Permission ###
        - compile 'com.kevadiyak:rxpermissions:1.0.0@aar'
      - ### 9. Image Picker ###
        - compile 'com.kevadiyak:rxphotopicker:1.0.0@aar'
      - ### 10. Preference ###
        - compile 'com.kevadiyak:rxpreference:1.0.0@aar'
      - ### 11. Validation ###
        - compile 'com.kevadiyak:rxvalidation:1.0.0@aar' 
  - }