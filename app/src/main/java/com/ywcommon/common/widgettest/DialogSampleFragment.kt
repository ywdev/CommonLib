package com.ywcommon.common.widgettest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.ywcommon.common.R
import com.ywcommon.common.utillib.util.toast.ToastUtils
import com.ywcommon.common.widgetlib.popup.XPopup
import com.ywcommon.common.widgetlib.popup.core.BasePopupView
import com.ywcommon.common.widgetlib.popup.impl.LoadingPopupView
import com.ywcommon.common.widgetlib.popup.interfaces.OnCancelListener
import com.ywcommon.common.widgetlib.popup.interfaces.OnConfirmListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DialogSampleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DialogSampleFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var confirm_btn : Button? = null//显示confirm弹窗
    private var loading_btn : Button? = null//显示loading弹窗
    private var xPopup : BasePopupView? = null//popup基类

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog_sample, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        confirm_btn = view.findViewById(R.id.confirm_btn);
        loading_btn = view.findViewById(R.id.loading_btn);
        confirm_btn?.setOnClickListener(this)
        loading_btn?.setOnClickListener(this)


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DialogSampleFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                DialogSampleFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.confirm_btn ->{
                xPopup = XPopup.Builder(context)
                        .dismissOnBackPressed(true)
                        .dismissOnTouchOutside(false)
                        .isDestroyOnDismiss(true)
                        .asConfirm("提示","您正在错误的道路上越走越远",
                                "取消","确定", {
                            ToastUtils.show("您已确定")
                        }, {
                            ToastUtils.show("您已取消")
                        },false)
                xPopup?.show()
            }
            R.id.loading_btn ->{
                val loadingPopup : LoadingPopupView = XPopup.Builder(context)
                        .dismissOnBackPressed(false)
                        .asLoading("加载中")
                        .show() as LoadingPopupView
                loadingPopup.postDelayed({
                    loadingPopup.setTitle("加载长度变化")
                    loadingPopup.postDelayed({
                        loadingPopup.setTitle("hello")
                        loadingPopup.delayDismissWith(2000) {
                            ToastUtils.show("我消失了")
                        }
                    },2000)
                },2000)
            }
        }
    }
}