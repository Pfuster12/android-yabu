package com.android.yabu.utils

import android.content.Context
import java.io.*

/**
 * Common File IO util methods.
 */
object FileUtils {

    /**
     * Read file contents.
     * @return [String] contents of file.
     */
    fun readFile(context: Context?, name: String): String {
        // init an input,
        var buffer: BufferedReader? = null

        // init the string builder,
        val stringBuilder = StringBuilder()

        try {
            val input = InputStreamReader(context?.openFileInput(name))
            buffer = BufferedReader(input)

            while (true) {
                val line = buffer.readLine() ?: break

                stringBuilder.append(line)
            }
        } catch (e: FileNotFoundException) {
            error("${javaClass.simpleName} - $e")
        } catch (e: IOException) {
            error("${javaClass.simpleName} - $e")
        } finally {
            buffer?.close()

            return stringBuilder.toString()
        }
    }

    /**
     * Write file to disk.
     * @return true if successful.
     */
    fun writeFile(context: Context?, name: String, data: String): Boolean {
        // init a stream writer,
        var writer: OutputStreamWriter? = null

        // stores a success flag,
        var success = false

        // try to write to file,
        try {
            writer = OutputStreamWriter(context?.openFileOutput(name, Context.MODE_PRIVATE))

            writer.write(data)

            success = true
        } catch (e: FileNotFoundException) {
            success = false
        } catch (e: IOException) {
            success = false
        } finally {
            writer?.close()

            return success
        }
    }
}