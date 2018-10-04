package com.esly.universeimages.views;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

/**
 * Created by Esly on 25/05/2015.
 */
public class ParsePushApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "t9MAHgfqNctlv6pIdVTkXiNEUZsKUr50Aw3VTwm5", "DjhOnKJjwtfXEY6euWxRSrUAswagKz7gKP7iTNxT");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });

    }
}
