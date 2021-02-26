import { StatusBar } from "expo-status-bar";
import React, { useEffect } from "react";
import { StyleSheet, Text, View } from "react-native";
import RemotePushController from "./src/RemotePushController";
import { DeviceEventEmitter } from "react-native";

export default function App() {
  useEffect(() => {
    DeviceEventEmitter.addListener("accept", () => {
      console.log("kkk");
    });
  });
  return (
    <View style={styles.container}>
      <Text>Open up App.js to start working on your app!</Text>
      <StatusBar style="auto" />
      <RemotePushController />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    alignItems: "center",
    justifyContent: "center",
  },
});
