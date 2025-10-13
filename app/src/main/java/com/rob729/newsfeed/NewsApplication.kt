package com.rob729.newsfeed

import android.app.Application
import com.rob729.newsfeed.di.AppGraph
import dev.zacsweers.metro.createGraphFactory

class NewsApplication: Application() {

     val graph by lazy { createGraphFactory<AppGraph.Factory>().create(this.applicationContext) }
}
