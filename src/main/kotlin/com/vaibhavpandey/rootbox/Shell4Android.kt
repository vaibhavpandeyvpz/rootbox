package com.vaibhavpandey.rootbox

import java.io.File

class Shell4Android {

    fun isRootGranted() : Boolean {
        val result = ShellExec("su").run("id");
        if (result.code != 0) {
            return result.output.any { it.contains("uid=0") }
        }
        return false
    }

    fun isRootPresent() : Boolean {
        if (File("/system/app/Superuser.apk").exists()) {
            return true;
        }
        val paths = arrayOf(
                "/sbin/",
                "/su/bin/",
                "/su/xbin/",
                "/system/bin/",
                "/system/xbin/",
                "/data/local/xbin/",
                "/data/local/bin/")
        var search: File
        for (path: String in paths) {
            search = File(path, "su")
            if (search.exists()) {
                return true;
            }
        }
        return false;
    }
}
