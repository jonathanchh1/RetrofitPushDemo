package com.emi.retrofitpushdemo

import android.content.Context
import okhttp3.Cache
import java.io.File

class CacheSources {

    companion object{
        fun cacheDir(context : Context) : Cache{
            val cacheDir = context.cacheDir.parent
            val file = File(cacheDir, "test")
            val cacheSize: Long = 10 * 1024 * 1024
            return Cache(file, cacheSize)
        }
    }
}