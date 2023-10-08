package com.rob729.newsfeed.initalizers

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import com.pluto.Pluto
import com.pluto.plugins.layoutinspector.PlutoLayoutInspectorPlugin
import com.pluto.plugins.network.PlutoNetworkPlugin
import com.pluto.plugins.rooms.db.PlutoRoomsDBWatcher
import com.pluto.plugins.rooms.db.PlutoRoomsDatabasePlugin
import com.rob729.newsfeed.database.NewsDatabase

class PlutoInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        Pluto.Installer(context as Application)
            .addPlugin(PlutoNetworkPlugin())
            .addPlugin(PlutoLayoutInspectorPlugin())
            .addPlugin(PlutoRoomsDatabasePlugin())
            .install()

        PlutoRoomsDBWatcher.watch("news_database", NewsDatabase::class.java)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}