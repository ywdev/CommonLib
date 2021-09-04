package com.ywcommon.common.demotest

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.ywcommon.common.R

class CountActivity : AppCompatActivity() {

    private lateinit var viewModel: MyViewModel3
    private lateinit var toolbar : Toolbar
    private lateinit var plusBtn : Button
    private lateinit var clearBtn : Button
    private lateinit var generateBtn : Button
    private lateinit var countText : TextView
    private lateinit var sp : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count)
        toolbar = findViewById(R.id.toolbar_function)
        plusBtn = findViewById(R.id.plus_one_btn)
        clearBtn = findViewById(R.id.clear_btn)
        generateBtn = findViewById(R.id.generate)
        countText = findViewById(R.id.count_text)
        sp = getPreferences(Context.MODE_PRIVATE)
        val countReserved = sp.getInt("count_reserved", 0)
        viewModel = ViewModelProvider(this, MyViewModelFactory(countReserved))
                .get(MyViewModel3::class.java)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        generateBtn.setOnClickListener {
            val userId = (0..2).random().toString()
            viewModel.getUser(userId)
        }
        viewModel.user.observe(this, { t->
            (t.firstName + t.lastName).also { countText.text = it }
        })
    }

    override fun onPause() {
        super.onPause()
//        val edit = sp.edit()
//        edit.putInt("count_reserved", viewModel.counter.value ?: 0)
//        edit.apply()
    }


}