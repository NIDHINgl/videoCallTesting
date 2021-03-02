import React, { useEffect, useState } from "react";
import PushNotification from "react-native-push-notification";
import { DeviceEventEmitter } from "react-native";

const RemotePushController = () => {
  useEffect(() => {
    PushNotification.createChannel(
      {
        channelId: "638137827026",
        channelName: "video noti",
        channelDescription: "notification",
        playSound: true,
        soundName: "noti.mp3",
        importance: 4,
        vibrate: true,
      },
      (created) => console.log(`createChannel returned '${created}'`) // (optional) callback returns whether the channel was created, false means it already existed.
    );
    PushNotification.configure({
      // (optional) Called when Token is generated (iOS and Android)

      onRegister: function (token) {
        console.log("TOKEN:", token);
      }, // (required) Called when a remote or local notification is opened or received
      onNotification: function (notification) {
        console.log("REMOTE NOTIFICATION ==>", notification); // process the notification here
      },
      onNotificationOpenedApp: function (remoteMessage) {
        console.log(
          "Notification caused app to open from background state:",
          remoteMessage.notification
        );
      },
      getInitialNotification: function (remoteMessage) {
        console.log(
          "Notification caused app to open from quit state:",
          remoteMessage.notification
        );
      },

      // Android only: GCM or FCM Sender ID
      senderID: "638137827026",
      popInitialNotification: true,
      requestPermissions: true,
      playSound: true,
      soundName: "noti.mp3",
    });
  }, []);

  return null;
};
export default RemotePushController;
