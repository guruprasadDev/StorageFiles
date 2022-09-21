package com.guruthedev.stroragefiles

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.io.*
import java.lang.Exception
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fileName = findViewById<EditText>(R.id.editFile)
        val fileData = findViewById<EditText>(R.id.editData)
        val read = findViewById<TextView>(R.id.textView2)


        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnView = findViewById<Button>(R.id.btnView)

        btnSave.setOnClickListener {
            val file:String = fileName.text.toString()
            val data:String = fileData.text.toString()
            val fileOutputStream:FileOutputStream
            try {
                fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
                fileOutputStream.write(data.toByteArray())
            }catch (e:FileNotFoundException){
                e.printStackTrace()
            }catch (e:NumberFormatException){
                e.printStackTrace()
            }catch (e:IOException){
                e.printStackTrace()
            }catch (e:Exception){
                e.printStackTrace()
            }
            Toast.makeText(applicationContext," Data Save in Storage",Toast.LENGTH_LONG).show()
            fileName.text.clear()
            fileData.text.clear()
        }

        btnView.setOnClickListener {
            val fileName = fileName.text.toString()
            if(fileName.toString()!=null && fileName.toString().trim()!=""){
                var fileInputStream:FileInputStream?=null
                fileInputStream = openFileInput(fileName)
                var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader:BufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder:StringBuilder = StringBuilder()
                var text:String? = null

                while (run {
                        text = bufferedReader.readLine()
                        text
                    } != null){
                    stringBuilder.append(text)
                }
                //Displaying data on Edittext
                fileData.setText(stringBuilder.toString().toString())
            }else{
                Toast.makeText(applicationContext,"File name cannot be blank",Toast.LENGTH_LONG).show()
            }
        }

    }
}