package com.guruthedev.stroragefiles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.io.*

class ExternalStorage : AppCompatActivity() {
    private val filePath = "MyFileStorage"
    internal var myExternalFile: File?=null
    private val isExternalStorageReadOnly: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)
    }
    private val isExternalStorageAvailable: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED.equals(extStorageState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_external_storage)

        val fileName = findViewById<EditText>(R.id.editFile)
        val fileData = findViewById<EditText>(R.id.editData)
        val readData = findViewById<TextView>(R.id.textView2)
        val saveButton = findViewById<Button>(R.id.btnSave) as Button
        val viewButton = findViewById<Button>(R.id.btnView)

        saveButton.setOnClickListener(View.OnClickListener {
            myExternalFile = File(getExternalFilesDir(filePath), fileName.text.toString())
            try {
                val fileOutPutStream = FileOutputStream(myExternalFile)
                fileOutPutStream.write(fileData.text.toString().toByteArray())
                fileOutPutStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            fileName.text.removeSpan(fileName)
            fileData.text.removeSpan(fileData)

            Toast.makeText(applicationContext,"data save",Toast.LENGTH_SHORT).show()

        })
        viewButton.setOnClickListener(View.OnClickListener {
            myExternalFile = File(getExternalFilesDir(filePath), fileName.text.toString())

            val filename = fileName.text.toString()
            myExternalFile = File(getExternalFilesDir(filePath),filename)
            if(filename.toString().trim()!=""){
                var fileInputStream = FileInputStream(myExternalFile)
                var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder: StringBuilder = StringBuilder()
                var text: String? = null
                while (run {
                        text = bufferedReader.readLine()
                        text
                    } != null) {
                    stringBuilder.append(text)
                }
                fileInputStream.close()
                //Displaying data on EditText
                readData.text=stringBuilder.toString()
                Toast.makeText(applicationContext,stringBuilder.toString(),Toast.LENGTH_SHORT).show()
            }
        })

        if (!isExternalStorageAvailable || isExternalStorageReadOnly) {
            saveButton.isEnabled = false
        }
    }

}
