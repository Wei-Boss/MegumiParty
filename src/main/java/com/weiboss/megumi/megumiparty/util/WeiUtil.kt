package com.weiboss.megumi.megumiparty.util

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*
import java.util.regex.Pattern

class WeiUtil {
    companion object {
        fun onReplace(text: String): String {
            return text
                    .replace("&", "§")
        }

        fun onReplace(texts: List<String>): List<String> {
            val list = ArrayList<String>()
            for (s in texts) {
                list.add(onReplace(s))
            }
            return list
        }

        fun isNumber(a: String): Boolean {
            return a.matches("^[0-9]*[1-9][0-9]*$".toRegex())
        }

        fun isFloat(a: String): Boolean {
            val pattern = Pattern.compile("^[-\\+]?[.\\d]*$")
            return pattern.matcher(a).matches()
        }

        fun copyFile(inputStream: InputStream, file: File) {
            try {
                val fileOutputStream = FileOutputStream(file)
                var read: Int = -1
                inputStream.use {
                    input ->
                    fileOutputStream.use {
                        with({read = input.read();read}() != -1) {
                            it.write(read)
                        }
                    }

                }
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }

        fun createItem(id: Int, data: Int, amount: Int, name: String?, lore: List<String>?): ItemStack {
            val item = ItemStack(Material.getMaterial(id), amount)
            item.durability = data.toShort()
            val meta = item.itemMeta
            if (name != null)
                meta.displayName = onReplace(name)
            if (lore != null)
                meta.lore = onReplace(lore)
            item.itemMeta = meta
            return item
        }

        fun executionCmd(p: Player, cmd: List<String>) {
            for (s in cmd) {
                val type = s.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
                val command = s.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1].replace("%player%", p.name)
                if (type.equals("op", ignoreCase = true)) {
                    opCmd(p, command)
                }
                if (type.equals("console", ignoreCase = true)) {
                    consoleCmd(command)
                }
                if (type.equals("player", ignoreCase = true)) {
                    playerCmd(p, command)
                }
            }
        }

        fun opCmd(p: Player, cmd: String) {
            if (p.isOp) {
                p.performCommand(cmd)
            } else {
                p.isOp = true
                p.performCommand(cmd)
                p.isOp = false
            }
        }

        fun consoleCmd(cmd: String) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd)
        }

        fun playerCmd(p: Player, cmd: String) {
            p.performCommand(cmd)
        }
    }
}