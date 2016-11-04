package com.kevadiyak.myframework.app;

import android.app.Application;
import android.content.Context;

import com.kevadiyak.customfont.CustomFontConfig;
import com.kevadiyak.myframework.R;
import com.kevadiyak.myframework.other.font.CustomViewWithTypefaceSupport;
import com.kevadiyak.myframework.other.font.TextField;
//import com.squareup.leakcanary.LeakCanary;

public class MyApplication extends Application {//MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initFont();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        /*if (LeakCanary.isInAnalyzerProcess(this))
            return;
        LeakCanary.install(this);*/
        //MultiDex.install(this);
    }

    public void initFont() {
        CustomFontConfig.Builder builder = new CustomFontConfig.Builder();
        //builder.setDefaultFontPath("fonts/Roboto-ThinItalic.ttf");
        builder.setFontAttrId(R.attr.fontPath);
        builder.addCustomViewWithSetTypeface(CustomViewWithTypefaceSupport.class);
        builder.addCustomStyle(TextField.class, R.attr.textFieldStyle);

        CustomFontConfig.initDefault(builder.build());
    }
}
