package io.github.charliecpshaw.cluedo

import android.app.Application
import io.github.charliecpshaw.cluedo.data.AppContainer
import io.github.charliecpshaw.cluedo.data.AppDataContainer

class CluedoApplication : Application() {
  lateinit var container: AppContainer

  override fun onCreate() {
    super.onCreate()
    container = AppDataContainer(this)
  }
}
