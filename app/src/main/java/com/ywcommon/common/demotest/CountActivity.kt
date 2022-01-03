package com.ywcommon.common.demotest

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.ywcommon.common.R
import kotlin.concurrent.thread

class CountActivity : AppCompatActivity() {

    private lateinit var viewModel: MyViewModel3
    private lateinit var toolbar : Toolbar
    private lateinit var plusBtn : Button
    private lateinit var clearBtn : Button
    private lateinit var generateBtn : Button
    private lateinit var addBtn : Button
    private lateinit var updateBtn : Button
    private lateinit var deleteBtn : Button
    private lateinit var queryBtn : Button
    private lateinit var countText : TextView
    private lateinit var sp : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count)
        toolbar = findViewById(R.id.toolbar_function)
        plusBtn = findViewById(R.id.plus_one_btn)
        clearBtn = findViewById(R.id.clear_btn)
        generateBtn = findViewById(R.id.generate)
        addBtn = findViewById(R.id.add_data)
        updateBtn = findViewById(R.id.update_data)
        deleteBtn = findViewById(R.id.delete_data)
        queryBtn = findViewById(R.id.query_data)
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
        val userDao = AppDataBase.getDataBase(this).userDao()
        val user1 = User("Tom", "Brady", 25)
        val user2 = User("Tom", "Hanks", 30)
        val user3 = User("Harry", "Boom", 35)
        val user4 = User("Harry", "Cook", 40)
        addBtn.setOnClickListener {
            thread {
                user1.id = userDao.insertUser(user1)
                user2.id = userDao.insertUser(user2)
                user3.id = userDao.insertUser(user3)
                user4.id = userDao.insertUser(user4)
                Log.d("yaowang","插入数据成功")
            }
        }
        updateBtn.setOnClickListener {
            thread {
                user1.age = 42
                userDao.updateUser(user1)
                Log.d("yaowang","更新数据成功")
            }
        }
        deleteBtn.setOnClickListener {
            thread {
                var deleteRow = userDao.deleteUserByLastName("Boom")
                Log.d("yaowang", "删除条目数为$deleteRow")
            }
        }
        queryBtn.setOnClickListener {
            thread {
                for (user in userDao.loadAllUsers()){
                    Log.d("yaowang",user.toString())
                }
            }

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