import RNPusherPushNotifications from "react-native-pusher-push-notifications";

// Get your interest
const donutsInterest = "debug-donuts";

// Initialize notifications
export const init = () => {
  // Set your app key and register for push
  RNPusherPushNotifications.setInstanceId(
    "bd4cfaae-7f93-471e-869c-598f5db2151f"
  );

  // Init interests after registration
  RNPusherPushNotifications.on("registered", () => {
    subscribe(donutsInterest);
  });

  // Setup notification listeners
  RNPusherPushNotifications.on("notification", handleNotification);
};

// Handle notifications received
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
