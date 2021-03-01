package com.videocall;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
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
            Log.e("IncomingCall","message has been received");
            String notifDataType = remoteMessage.getData().get("type");
            String startCallType="incomingcall";
            String disconnectCallType="calldisconnected";
            // if(startCallType.equals(notifDataType)|| disconnectCallType.equals(notifDataType)) {
            //         showIncomingCallScreen(remoteMessage,!isAppRunning());
            //         return;
            // }
            createNotificationChannel(getApplicationContext());
            showNotification(getApplicationContext());
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

        private void showNotification(Context context) {
            Intent notificationIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
            PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, 0,
                    notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            android.app.Notification notification = new NotificationCompat.Builder(context, "9009")
                    .setContentTitle("Incoming call")
                    .setContentText("(919) 555-1234")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_CALL)
                    .setFullScreenIntent(fullScreenPendingIntent, true)
                    .build();
            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, notification);
        }

        private void createNotificationChannel(Context context) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String name = context.getString(R.string.channel_name);
                String descriptionText = context.getString(R.string.channel_description);
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel notificationChannel = new NotificationChannel("9009", name, importance);
                // Register the channel with the system
                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(notificationChannel);
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