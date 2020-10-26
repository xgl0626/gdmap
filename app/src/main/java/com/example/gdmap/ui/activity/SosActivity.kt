package com.example.gdmap.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.activity_sos.*
import kotlinx.android.synthetic.main.activity_sos.toolbar

/**
 * @Author: xgl
 * @ClassName: SosActivity
 * @Description:
 * @Date: 2020/10/26 15:39
 */
class SosActivity : BaseActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun initView() {
        setSupportActionBar(toolbar)
        et_sos_content.hint = "这里进行具体的位置描述，我们会将你的经纬度和具体描述位置上传至后台告诉相关部门"
    }

    override fun initClick() {
        bt_send_sos.setOnClickListener {
            finish()
        }
    }

    override fun initData() {
    }

    override fun getViewLayout(): Int {
        return R.layout.activity_sos
    }

}