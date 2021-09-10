package com.example.syntheticalproject.app

import me.hgj.jetpackmvvm.base.BaseApp
import kotlin.properties.Delegates

class App : BaseApp() {
    companion object{
        var instance: App by Delegates.notNull()
        fun instance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}