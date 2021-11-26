package com.ywcommon.common.kotlin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.ywcommon.common.R
import kotlinx.coroutines.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [KotlinCoroutinesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class KotlinCoroutinesFragment : Fragment(), View.OnClickListener {
    private lateinit var launchBtn : Button
    private lateinit var runBlockingBtn : Button
    private lateinit var asyncBtn : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kotlin_coroutines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        launchBtn = view.findViewById(R.id.btn1);
        runBlockingBtn = view.findViewById(R.id.btn2)
        asyncBtn = view.findViewById(R.id.btn3)
        launchBtn.setOnClickListener(this)
        runBlockingBtn.setOnClickListener(this)
        asyncBtn.setOnClickListener(this)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment KotlinCoroutinesFragment.
         */
        @JvmStatic
        fun newInstance() =
            KotlinCoroutinesFragment().apply {
                arguments = Bundle()
            }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            //不阻塞线程
            R.id.btn1 ->{
                GlobalScope.launch(Dispatchers.IO) {
                    delay(2000)
                    Log.d("yaowang", "协程执行结束 -- 线程id：${Thread.currentThread().id}")
                }
                Log.d("yaowang","主程序执行结束")
            }
            //阻塞线程
            R.id.btn2 ->{
                Log.d("yaowang", "主线程id：${Thread.currentThread().id}")
                runBlocking {
                    repeat(8){
                        Log.e("yaowang", "协程执行$it 线程id：${Thread.currentThread().id}")
                        delay(1000)
                    }
                }
                Log.d("yaowang", "主线程id：${Thread.currentThread().id}")
            }
            R.id.btn3 ->{
                GlobalScope.launch {
                    val result1 = GlobalScope.async {
                        getResult1()
                    }
                    val result2 = GlobalScope.async {
                        getResult2()
                    }
                    val result = result1.await() + result2.await()
                    Log.e("yaowang","result = $result")
                }

            }
        }

    }

    private suspend fun getResult1(): Int {
        delay(3000)
        return 1
    }

    private suspend fun getResult2(): Int {
        delay(4000)
        return 2
    }
}