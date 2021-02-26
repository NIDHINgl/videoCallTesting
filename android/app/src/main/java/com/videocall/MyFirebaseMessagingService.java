package com.videocall;

import android.app.ActivityManager;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Iterator;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

public class MyFirebaseMessagingService
        extends FirebaseMessagingService {

    private static DeviceEventManagerModule.RCTDeviceEventEmitter eventEmitter = null;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            String notifDataType = remoteMessage.getData().get("type");
            String startCallType="incomingcall";
            String disconnectCallType="calldisconnected";
            if(startCallType.equals(notifDataType)|| disconnectCallType.equals(notifDataType)) {
                    showIncomingCallScreen(remoteMessage,!isAppRunning());
                    return;
            }
        } catch (Exception e) {
            
        }
    }

    private void showIncomingCallScreen(RemoteMessage remoteMessage,boolean isAppRunning) {
        String notifDataType = remoteMessage.getData().get("type");
        String startCallType="incomingcall";
        String disconnectCallType="calldisconnected";
        if( startCallType.equals(notifDataType)) {
                Intent i = new Intent(getApplicationContext(), IncomingCallScreenActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                i.putExtra("CALLER_NAME", remoteMessage.getData().get("callerName"));
                i.putExtra("CALL_TYPE",remoteMessage.getData().get("type"));
                i.putExtra("APP_STATE",isAppRunning);
                startActivity(i);
        }else if(disconnectCallType.equals((notifDataType))){
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager
                    .getInstance(MyFirebaseMessagingService.this);
            localBroadcastManager.sendBroadcast(new Intent(
                    "com.incomingcallscreenactivity.action.close"));
        }
        }

    private boolean isAppRunning() {
        ActivityManager m = (ActivityManager) this.getSystemService( ACTIVITY_SERVICE );
        List<ActivityManager.RunningTaskInfo> runningTaskInfoList =  m.getRunningTasks(10);
        Iterator<ActivityManager.RunningTaskInfo> itr = runningTaskInfoList.iterator();
        int n=0;
        while(itr.hasNext()){
            n++;
            itr.next();
        }
        if(n==1){ // App is killed
            return false;
        }
        return true; // App is in background or foreground
    }
}