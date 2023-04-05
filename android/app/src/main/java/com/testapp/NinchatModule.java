package com.testapp;

import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.ninchat.client.Props;
import com.ninchat.sdk.NinchatConfiguration;
import com.ninchat.sdk.NinchatSDKEventListener;
import com.ninchat.sdk.NinchatSession;
import com.ninchat.sdk.models.NinchatSessionCredentials;

import java.util.Objects;

public class NinchatModule extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = "NinchatModule";
    private final ReactApplicationContext reactContext;

    private NinchatSession ninchatSession = null;
    private NinchatConfiguration ninchatConfiguration = null;

    public NinchatModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void createCalendarEvent(String name, String location) {
        Log.d("CalendarModule", "Create event called with name: " + name
                + " and location: " + location);
        /*Intent intent = new Intent(reactContext, NinchatDummyActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        reactContext.startActivity(intent);*/

        String configurationKey = "site_config";
        ninchatConfiguration = new NinchatConfiguration.Builder()
        .setUserName("React-native-user")
                .create();

        NinchatSession ninchatSession = new NinchatSession.Builder(reactContext, configurationKey)
                .setConfiguration(ninchatConfiguration) // If we want session to be persistence uncomment next line
                .setEventListener(eventListener)
                .create();
        ninchatSession.setServerAddress("api.ninchat.com");
        // ninchatSession.setSiteSecret("site_secret");
        ninchatSession.start(Objects.requireNonNull(this.reactContext.getCurrentActivity()), 1025);
    }

    private NinchatSDKEventListener eventListener = new NinchatSDKEventListener() {
        @Override
        public void onSessionInitiated(NinchatSessionCredentials sessionCredentials) {
            Log.e(">>", "session initiated");
        }

        @Override
        public void onSessionInitFailed() {
            Log.e(">>", "session failed");
        }

        @Override
        public void onSessionStarted() {
            Log.e(">>", "session started");
        }

        @Override
        public void onSessionEvent(Props params) {
            super.onSessionEvent(params);
        }

        @Override
        public void onSessionError(Exception error) {
            Log.e(">>", error.getLocalizedMessage());
        }
    };
}
