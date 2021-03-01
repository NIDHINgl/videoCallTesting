import React, { useEffect, useState } from "react";
import PushNotification from "react-native-push-notification";
import { DeviceEventEmitter } from "react-native";
import RNPusherPushNotifications from "react-native-pusher-push-notifications";

// Get your interest
const donutsInterest = "debug-donuts";

const RemotePushController = () => {
  // useEffect(() => {
  //   PushNotification.configure({
  //     // (optional) Called when Token is generated (iOS and Android)
  //     onRegister: function (token) {
  //       console.log("TOKEN:", token);
  //     }, // (required) Called when a remote or local notification is opened or received
  //     onNotification: function (notification) {
  //       // DeviceEventEmitter.addListener("accept", () => {
  //       //   //Do something!
  //       // });
  //       // DeviceEventEmitter.addListener("reject", () => {
  //       //   //Do something!
  //       // });
  //       console.log("REMOTE NOTIFICATION ==>", notification); // process the notification here
  //     },
  //     onNotificationOpenedApp: function (remoteMessage) {
  //       console.log(
  //         "Notification caused app to open from background state:",
  //         remoteMessage.notification
  //       );
  //     },
  //     getInitialNotification: function (remoteMessage) {
  //       console.log(
  //         "Notification caused app to open from quit state:",
  //         remoteMessage.notification
  //       );
  //     },
  //     // Android only: GCM or FCM Sender ID
  //     senderID: "638137827026",
  //     popInitialNotification: true,
  //     requestPermissions: true,
  //   });
  // }, []);
  useEffect(() => {
    RNPusherPushNotifications.setInstanceId(
      "bd4cfaae-7f93-471e-869c-598f5db2151f"
    );

    // Init interests after registration
    RNPusherPushNotifications.on("registered", () => {
      subscribe(donutsInterest);
    });

    // Setup notification listeners
    RNPusherPushNotifications.on("notification", handleNotification);
  }, []);

  const handleNotification = (notification) => {
    console.log(notification);
  };

  // Subscribe to an interest
  const subscribe = (interest) => {
    // Note that only Android devices will respond to success/error callbacks
    RNPusherPushNotifications.subscribe(
      interest,
      (statusCode, response) => {
        console.error(statusCode, response);
      },
      () => {
        console.log("Success");
      }
    );
  };

  // Unsubscribe from an interest
  const unsubscribe = (interest) => {
    RNPusherPushNotifications.unsubscribe(
      interest,
      (statusCode, response) => {
        console.tron.logImportant(statusCode, response);
      },
      () => {
        console.tron.logImportant("Success");
      }
    );
  };

  return null;
};
export default RemotePushController;
