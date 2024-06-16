import {NativeModules, Platform} from 'react-native';

const LINKING_ERROR =
    "The package 'react-native-custom-bottom-tabs-library' doesn't seem to be linked. Make sure: \n\n" +
    Platform.select({ios: "- You have run 'pod install'\n", default: ''}) +
    '- You rebuilt the app after installing the package\n' +
    '- You are not using Expo Go\n';

const CustomBottomTabsLibrary = NativeModules.CustomBottomTabsLibrary
    ? NativeModules.CustomBottomTabsLibrary
    : new Proxy(
          {},
          {
              get() {
                  throw new Error(LINKING_ERROR);
              },
          },
      );

export function setBottomTabsComponent(componentId: string, height: number = 0) {
    return CustomBottomTabsLibrary.setBottomTabsComponent(componentId, height);
}

export function show() {
    CustomBottomTabsLibrary.setVisible(true);
}

export function hide() {
    CustomBottomTabsLibrary.setVisible(false);
}
