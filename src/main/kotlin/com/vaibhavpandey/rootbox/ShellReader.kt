package com.vaibhavpandey.rootbox

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

internal class ShellReader constructor(val stream: InputStream, name: String) : Thread(name) {

    var contents: MutableList<String> = ArrayList()

    override fun run() {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(InputStreamReader(stream))
            var line: String?
            while (true) {
                line = reader.readLine()
                if (null == line)
                    break
                contents.add(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            reader?.close()
        }
    }
}
