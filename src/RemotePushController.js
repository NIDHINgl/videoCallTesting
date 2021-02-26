import React, { useEffect, useState } from "react";
import PushNotification from "react-native-push-notification";
import { DeviceEventEmitter } from "react-native";

const RemotePushController = () => {
  useEffect(() => {
    PushNotification.configure({
      // (optional) Called when Token is generated (iOS and Android)
      onRegister: function (token) {
        console.log("TOKEN:", token);
      }, // (required) Called when a remote or local notification is opened or received
      onNotification: function (notification) {
        // DeviceEventEmitter.addListener("accept", () => {
        //   //Do something!
        // });
        // DeviceEventEmitter.addListener("reject", () => {
        //   //Do something!
        // });
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
    });
  }, []);

  return null;
};
export default RemotePushController;
