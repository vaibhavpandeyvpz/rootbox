package com.vaibhavpandey.rootbox

import java.io.*
import java.io.OutputStreamWriter

class ShellExec constructor(vararg val executable: String, val environment: Map<String, String>?, val cwd: String?) {

    constructor(vararg executable: String, environment: Map<String, String>?) : this(*executable, environment = environment, cwd = null)

    constructor(vararg executable: String) : this(*executable, environment = null)

    fun run(vararg commands: String) : ShellResult = run(*commands, stdout = false)

    fun run(vararg commands: String, stdout: Boolean = true) : ShellResult = run(*commands, stdout = stdout, stderr = false)

    fun run(vararg commands: String, stdout: Boolean = false, stderr: Boolean = false) : ShellResult {
        var code: Int = 126
        var process: Process? = null
        var writer: OutputStreamWriter? = null
        var STDERR: ShellReader? = null
        var STDOUT: ShellReader? = null
        try {
            val builder: ProcessBuilder = ProcessBuilder(*executable)
            if (environment != null) {
                builder.environment().putAll(environment)
            }
            if (cwd != null) {
                builder.directory(File(cwd))
            }
            process = builder.start()
            if (stdout) {
                STDOUT = ShellReader(process.getInputStream(), "reader:stdout")
                STDOUT.start()
            }
            if (stderr) {
                STDERR = ShellReader(process.getErrorStream(), "reader:stderr")
                STDERR.start()
            }
            writer = OutputStreamWriter(process.getOutputStream())
            for (command: String in commands) {
                writer.write(command)
                writer.write("\n")
                writer.flush()
            }
            writer.write("exit")
            writer.write("\n")
            writer.flush()
            code = process.waitFor()
            writer.close()
            STDERR?.join()
            STDOUT?.join()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            writer?.close()
            process?.destroy()
        }
        return ShellResult(
                code,
                error = STDERR?.contents ?: ArrayList(),
                output = STDOUT?.contents ?: ArrayList())
    }
}
