package com.cis.kotlincontentprovider2

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    val uri = Uri.parse("content://kr.co.cis.dbprovider")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        selectBtn.setOnClickListener {
            val cursor = contentResolver.query(uri, null, null, null, null)!!
            tv.text = ""

            while (cursor.moveToNext()) {
                val idxPos = cursor.getColumnIndex("idx")
                val textDataPos = cursor.getColumnIndex("textData")
                val intDataPos = cursor.getColumnIndex("intData")
                val floatDataPos = cursor.getColumnIndex("floatData")
                val dateDataPos = cursor.getColumnIndex("dateData")

                val idx = cursor.getInt(idxPos)
                val textData = cursor.getString(textDataPos)
                val intData = cursor.getInt(intDataPos)
                val floatData = cursor.getDouble(floatDataPos)
                val dateData = cursor.getString(dateDataPos)

                tv.append("idx : ${idx}\n")
                tv.append("textData : ${textData}\n")
                tv.append("intData : ${intData}\n")
                tv.append("floatData : ${floatData}\n")
                tv.append("dateData : ${dateData}\n\n")
            }
        }

        insertBtn.setOnClickListener {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = sdf.format(Date())

            val cv1 = ContentValues()
            cv1.put("textData", "문자열3")
            cv1.put("intData", 300)
            cv1.put("floatData", 33.33)
            cv1.put("dateData", date)

            val cv2 = ContentValues()
            cv2.put("textData", "문자열4")
            cv2.put("intData", 400)
            cv2.put("floatData", 44.4)
            cv2.put("dateData", date)
//            val arg1 = arrayOf("문자열1", "100", "11.11", date)
//            val arg2 = arrayOf("문자열2", "200", "22.22", date)

            val uri = Uri.parse(uri.toString())

            contentResolver.insert(uri, cv1)
            contentResolver.insert(uri, cv2)

            tv.text = "저장 완료"
        }

        updateBtn.setOnClickListener {
            val cv = ContentValues()
            cv.put("textData", "문자열3")

            val where = "idx=?"
            val args = arrayOf("3")

            val uri = Uri.parse(uri.toString())

            contentResolver.update(uri, cv, where, args)

            tv.text = "수정 완료"
        }

        deleteBtn.setOnClickListener {
            val where = "idx=?"
            val args = arrayOf("3")

            val uri = Uri.parse(uri.toString())

            contentResolver.delete(uri, where, args)

            tv.text = "삭제 완료"
        }    }
}
