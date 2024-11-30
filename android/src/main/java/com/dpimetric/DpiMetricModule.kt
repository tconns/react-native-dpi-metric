package com.dpimetric

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import kotlin.math.pow
import kotlin.math.sqrt

class DpiMetricModule(private val reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String {
    return NAME
  }

  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  @ReactMethod
  fun multiply(a: Double, b: Double, promise: Promise) {
    promise.resolve(a * b)
  }

  override fun getConstants(): MutableMap<String, Any>? {
    val constants: MutableMap<String, Any> = HashMap()
    constants["deviceInch"] = deviceInch()
    constants["dpi"] = getDpi()
    constants["isTablet"] = isTablet()
    return constants
  }

  @SuppressLint("ObsoleteSdkInt")
  fun deviceInch(): Double {
    val dm: DisplayMetrics = reactContext.resources.displayMetrics
    val windowManager = reactContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      windowManager.defaultDisplay.getRealMetrics(dm)
    } else {
      windowManager.defaultDisplay.getMetrics(dm)
    }

    val x: Double = (dm.widthPixels / dm.xdpi.toDouble()).pow(2.0)
    val y: Double = (dm.heightPixels / dm.ydpi.toDouble()).pow(2.0)
    val screenInches = sqrt(x + y)

    return if (screenInches < 30) {
      screenInches
    } else {
      val density = (dm.density * 160).toDouble()
      val x2 = (dm.widthPixels / density).pow(2.0)
      val y2 = (dm.heightPixels / density).pow(2.0)
      sqrt(x2 + y2)
    }
  }

  fun getDpi(): Int {
    val dm: DisplayMetrics = reactContext.resources.displayMetrics
    return dm.densityDpi
  }

  fun isTablet(): Boolean {
    // TODO: Implement some actually useful functionality
    if ((reactContext.resources.configuration.screenLayout and
        Configuration.SCREENLAYOUT_SIZE_MASK) >=
      Configuration.SCREENLAYOUT_SIZE_LARGE
    ) {
      return true
    }
    return false
  }

  companion object {
    const val NAME = "DpiMetric"
  }
}
