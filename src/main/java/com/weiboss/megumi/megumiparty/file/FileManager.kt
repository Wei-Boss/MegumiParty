package com.weiboss.megumi.megumiparty.file

import com.weiboss.megumi.megumiparty.Main
import com.weiboss.megumi.megumiparty.util.WeiUtil
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class FileManager constructor(plugin: Main) {
    var plugin: Main? = null
    var configFile: File? = null
    var config: YamlConfiguration? = null

    init {
        this.plugin = plugin
        config = YamlConfiguration()
        configFile = File(plugin.dataFolder, "config.yml")
        try {
            if (!configFile!!.exists()) {
                configFile!!.parentFile.mkdirs()
                WeiUtil.copyFile(plugin.getResource("config.yml"), configFile!!)
                plugin.logger.info("File: 已生成 config.yml 文件")
            }
            else plugin.logger.info("File: 已加载 config.yml 文件")
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
        config = YamlConfiguration.loadConfiguration(configFile)
    }
}