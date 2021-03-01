package com.videocall;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.swmansion.gesturehandler.react.RNGestureHandlerEnabledRootView;

import expo.modules.splashscreen.singletons.SplashScreen;
import expo.modules.splashscreen.SplashScreenImageResizeMode;
import com.b8ne.RNPusherPushNotifications.NotificationsMessagingService;

public class MainActivity extends ReactActivity {

    protected void onStart() {
        super.onStart();
    
        ReactInstanceManager reactInstanceManager = getReactNativeHost().getReactInstanceManager();
        NotificationsMessagingService.read(reactInstanceManager, this);
      }
    
      @Override
      public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        ReactInstanceManager reactInstanceManager = getReactNativeHost().getReactInstanceManager();
        NotificationsMessagingService.read(reactInstanceManager, this);
      }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // SplashScreen.show(...) has to be called after super.onCreate(...)
    // Below line is handled by '@expo/configure-splash-screen' command and it's discouraged to modify it manually
    SplashScreen.show(this, SplashScreenImageResizeMode.CONTAIN, ReactRootView.class, false);
  }


    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "main";
    }

    @Override
    protected ReactActivityDelegate createReactActivityDelegate() {
        return new ReactActivityDelegate(this, getMainComponentName()) {
            @Override
            protected ReactRootView createRootView() {
                return new RNGestureHandlerEnabledRootView(MainActivity.this);
            }
        };
    }
}
