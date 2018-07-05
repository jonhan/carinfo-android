package com.hjonas.carattr

import android.app.Application
import com.hjonas.data.ApiManager

class CarAttrApp : Application() {

  override fun onCreate() {
    super.onCreate()

    ApiManager.debuggable = BuildConfig.DEBUG
  }
}
