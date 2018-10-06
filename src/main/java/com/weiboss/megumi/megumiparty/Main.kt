package com.weiboss.megumi.megumiparty

import com.weiboss.megumi.megumiparty.file.FileManager
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    private var instance: Main? = null
    private var fileManager: FileManager? = null

    override fun onEnable() {
        instance = this
        fileManager = FileManager(this)
        logger.info("加载成功!")
    }

    override fun onDisable() {
        logger.info("卸载成功!")
    }

    fun getInstance(): Main? {
        return instance
    }

    fun getFileManager(): FileManager? {
        return fileManager
    }
}