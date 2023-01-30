package com.fengxing.album

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPref: SharedPreferences
    private var activated by Delegates.notNull<Boolean>()

    private lateinit var listView: ListView

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun saveValue(key: String, value: String) {
        val editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_activate -> {
                if (!activated) {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("激活软件")

                    val input = EditText(this)
                    builder.setView(input)

                    builder.setPositiveButton("确定") { dialog, _ ->
                        val inputText = input.text.toString()
                        val psdLst = listOf("YYsqS829", "Crsm829M", "Js86A9kC", "Ts225625")
                        if (inputText in psdLst) {
                            Toast.makeText(applicationContext, "软件激活成功！", Toast.LENGTH_SHORT).show()
                            saveValue("1", inputText)

                            listView.setOnItemClickListener { _, _, _, _ ->
                                    val intent = Intent(Intent.ACTION_VIEW)
                                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                                    intent.data = Uri.parse("https://www.bilibili.com/video/BV1CB4y1D7zq")
                                    startActivity(intent)
                                }

                            dialog.cancel()
                        } else {
                            Toast.makeText(applicationContext, "激活码错误", Toast.LENGTH_SHORT).show()
                            dialog.cancel()
                        }
                    }

                    builder.setNegativeButton("取消") { dialog, _ ->
                        dialog.cancel()
                    }

                    val dialog = builder.create()
                    dialog.show()
                } else {
                    Toast.makeText(applicationContext, "您已激活", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.action_website -> {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.data = Uri.parse("http://www.jyfx.xyz")
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPref = getSharedPreferences("setting_prefs", Context.MODE_PRIVATE)
        activated = sharedPref.contains("1")

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.overflowIcon = ContextCompat.getDrawable(this, R.drawable.more_horiz_white_24dp)

        listView = findViewById(R.id.list_view)
        val features = listOf("一键生成音源", "一键生成 midi","一键调音", "一键 AI 填词", "一键混音", "一键生成 PV")
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, features)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, _, _ ->
            if (!activated) {
                val intent = Intent(this, VideoPlayActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.data = Uri.parse("https://www.bilibili.com/video/BV1CB4y1D7zq")
                startActivity(intent)
            }
        }
    }
}