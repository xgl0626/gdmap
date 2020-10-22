package com.example.gdmap.ui.fragment

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.gdmap.R
import com.example.gdmap.base.BaseFragment
import com.example.gdmap.bean.UserInfo
import com.example.gdmap.config.TokenConfig
import com.example.gdmap.config.TokenConfig.BASE_URL
import com.example.gdmap.ui.activity.*
import com.example.gdmap.ui.viewmodel.MyViewModel
import com.example.gdmap.ui.viewmodel.SetDataViewModel
import com.example.gdmap.utils.*
import kotlinx.android.synthetic.main.fragment_me.*
import top.limuyang2.photolibrary.LPhotoHelper


class MyFragment : BaseFragment(), View.OnClickListener {
    companion object {
        const val TAKE_PHOTO = 1
    }

    var avatar: String = ""
    var qq: String = ""
    var tel: String = ""
    var dcp: String = ""
    var Myname: String = ""
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MyViewModel::class.java)
    }
    private val viewModel2  by lazy { ViewModelProviders.of(this).get(SetDataViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_me, container, false)
    }

    override fun initView() {
        viewModel.getUserInfo(TokenConfig.token.token)
        viewModel.MyInfoResultValue.observe(this, Observer {
            if (it != null) {
                tv_fragment_me_user_data.text = it.nickname
                tv_fragment_me_user_name.text = it.description
                Log.d("mydata", it.toString())
                if (it.avatar.isNotEmpty()) {
                    Log.d("zt",it.avatar)
                    Glide.with(iv_fragment_me_user_avator).load(BASE_URL+it.avatar)
                        .into(iv_fragment_me_user_avator)
                } else {
                    Glide.with(iv_fragment_me_user_avator).load(R.mipmap.acticity_login_name)
                        .into(iv_fragment_me_user_avator)
                }
                avatar = it.avatar
                qq = it.qq
                tel = it.tel
                dcp = it.description
                Myname = it.nickname
            }
        })
        bt_fragment_me_data.setOnClickListener(this)
        bt_fragment_me_question.setOnClickListener(this)
        bt_fragment_me_set.setOnClickListener(this)
        bt_fragment_collect.setOnClickListener(this)
        iv_fragment_me_user_avator.setOnClickListener(this)
        AddIconImage.setImageViewToButton(R.mipmap.fragment_me_bt_day, bt_fragment_collect, 0)
        AddIconImage.setImageViewToButton(R.mipmap.fragment_me_bt_set, bt_fragment_me_set, 0)
        AddIconImage.setImageViewToButton(
            R.mipmap.fragment_me_bt_question,
            bt_fragment_me_question,
            0
        )
        AddIconImage.setImageViewToButton(R.mipmap.acticity_login_name, bt_fragment_me_data, 0)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getUserInfo(TokenConfig.token.token)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun initClick() {
        tv_back_login.setOnClickListener {
            changeToActivity(LoginActivity())
            activity?.finish()
        }
    }

    override fun initData() {
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.bt_fragment_me_set -> changeToActivity(ProductActivity())
            R.id.bt_fragment_me_data -> changeToActivity2(SetDataActivity())
            R.id.bt_fragment_collect -> changeToActivity(CollectActivity())
            R.id.bt_fragment_me_question -> changeToActivity(TipsActivity())
            R.id.iv_fragment_me_user_avator -> {
                ImageSelectutils.selectImageFromAlbum(1, this)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                TAKE_PHOTO -> {
                    val selectedPhotos = LPhotoHelper.getSelectedPhotos(data).map {
                        it.toString()
                    }
                    val imageListUri =
                        ArrayList((LPhotoHelper.getSelectedPhotos(data))).map {
                            it.toString() }
                    val imageListAbsolutePath = ArrayList<String>()
                    imageListUri.forEach { imageListAbsolutePath.add(it) }

                    Glide.with(iv_fragment_me_user_avator).load(selectedPhotos[0])
                        .into(iv_fragment_me_user_avator)
                    val user=UserInfo(dcp,Myname,qq,tel)
                    viewModel2.setImageList(imageListAbsolutePath)
                    viewModel2.updateInfo(user)
                    avatar = selectedPhotos[0]
                }
            }
        }
    }

    private fun changeToActivity(activity: Activity) {
        val intent = Intent(this.activity, activity::class.java)
        startActivity(intent)
    }

    private fun changeToActivity2(activity: Activity) {
        val intent = Intent(this.activity, activity::class.java)
        intent.putExtra("avatar", avatar)
        intent.putExtra("qq", qq)
        intent.putExtra("tel", tel)
        intent.putExtra("dcp", dcp)
        intent.putExtra("name", Myname)
        startActivity(intent)
    }
}