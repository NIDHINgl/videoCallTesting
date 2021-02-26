import React, { Component } from "react";
import { DeviceEventEmitter } from "react-native";

export default class MainComponent extends Component {
  componentDidMount() {
    DeviceEventEmitter.addListener("accept", () => {
      //Do something!
    });
    DeviceEventEmitter.addListener("reject", () => {
      //Do something!
    });
  }
}
