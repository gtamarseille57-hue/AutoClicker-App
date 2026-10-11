package com.example.autoclicker

import android.content.Context
import java.io.File
import java.io.FileOutputStream

data class RecordedAction(
    val actionType: String,
    val x: Float,
    val y: Float,
    val timestamp: Long
)

class RecordingManager(private val context: Context) {
    private val list = mutableListOf<RecordedAction>()

    fun startRecording() {
        list.clear()
    }

    fun append(action: RecordedAction) {
        list.add(action)
    }

    fun stopAndSave(name: String) {
        val directory = File(context.filesDir, "recordings")
        if (!directory.exists()) directory.mkdirs()

        val file = File(directory, "$name.json")
        FileOutputStream(file).use { output ->
            output.write(list.joinToString(prefix = "[", postfix = "]") {
                "{\"actionType\":\"${it.actionType}\",\"x\":${it.x},\"y\":${it.y},\"timestamp\":${it.timestamp}}"
            }.toByteArray())
        }
    }

    fun getRecordings(): List<File> {
        val directory = File(context.filesDir, "recordings")
        return if (directory.exists()) directory.listFiles()?.toList() ?: emptyList() else emptyList()
    }
}
