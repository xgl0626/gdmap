package com.example.gdmap.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.gdmap.MainActivity
import com.example.gdmap.R
import com.example.gdmap.base.BaseActivity
import com.example.gdmap.bean.DoubleStatusDao
import com.example.gdmap.ui.viewmodel.LoginOrRegisterViewModel
import com.example.gdmap.utils.AddIconImage
import com.example.gdmap.utils.MyApplication
import com.example.gdmap.utils.Toast
import com.example.gdmap.utils.setOnSingleClickListener
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(LoginOrRegisterViewModel::class.java)
    }
    private var rememberPassword: SharedPreferences = MyApplication.context.getSharedPreferences(
        "remember",
        MODE_PRIVATE
    )

    companion object {
        const val PERMISSION = 5000
    }

    private var name: String? = null
    private var psw1: String? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!DoubleStatusDao.getStatus(PERMISSION)) {
            PermissionX.init(this)
                .permissions(
                    Manifest.permission.WAKE_LOCK,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.INTERNET,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                .request { allGranted, grantedList, deniedList ->
                    if (allGranted) {
                        DoubleStatusDao.saveStatus(PERMISSION, true)
                        Toast.toast("授权成功")
                    } else {
                        Toast.toast("未授权")
                        showDialog()
                    }
                }
        }

    }

    override fun getViewLayout(): Int {
        return R.layout.activity_login
    }

    override fun initData() {
        et_activity_login_username.setText(rememberPassword.getString("name", ""))
        et_activity_login_psw.setText(rememberPassword.getString("psw", ""))
        viewModel.loginOrRegisterResult.observe(this, Observer {
            if (it == 200) {
                changeToActivity(MainActivity())
                finish()
            }
        })
    }

    //弹出提示框
    fun showDialog() {
        val dialog = AlertDialog.Builder(this)
            .setMessage("是否去设置权限？")
            .setPositiveButton("是") { dialog, which ->
                dialog.dismiss()
                goToAppSetting()
            }
            .setNegativeButton(
                "否"
            ) { dialog, p1 -> dialog?.dismiss() }
            .setCancelable(false)
            .show()
    }

    private fun goToAppSetting() {
        val intent = Intent()
        intent.action = ACTION_APPLICATION_DETAILS_SETTINGS;
        val uri = Uri.fromParts("package", packageName, null);
        intent.data = uri
        startActivity(intent)
    }

    override fun initView() {

        AddIconImage.setImageViewToEditText(
            R.mipmap.acticity_login_name,
            et_activity_login_username,
            0
        )
        AddIconImage.setImageViewToEditText(R.mipmap.activity_login_psw, et_activity_login_psw, 0)

    }

    override fun initClick() {
        bt_activity_login_login.setOnSingleClickListener {
            name = et_activity_login_username.text.toString()
            psw1 = et_activity_login_psw.text.toString()
            if (name != "" && psw1 != "")
                viewModel.login(name.toString(), psw1.toString())
            else
                Toast.toast("用户名或者密码不能为空")
        }
        tv_activity_login_register.setOnSingleClickListener {
            changeToActivity(RegisterActivity())
            finish()
        }
    }


    private fun changeToActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }
}