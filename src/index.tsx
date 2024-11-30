import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-dpi-metric' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const DpiMetric = NativeModules.DpiMetric
  ? NativeModules.DpiMetric
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

const { deviceInch, dpi, isTablet } = DpiMetric.getConstants();

export function multiply(a: number, b: number): Promise<number> {
  return DpiMetric.multiply(a, b);
}

export { deviceInch, dpi, isTablet };
