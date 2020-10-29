package com.example.gdmap.ui.viewmodel

import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gdmap.bean.UserInfo
import com.example.gdmap.config.TokenConfig
import com.example.gdmap.network.ServiceCreator
import com.example.gdmap.network.UpdateInfo
import com.example.gdmap.utils.MyApplication
import com.example.gdmap.utils.Toast
import com.example.gdmap.utils.getFilePathFromContentUri
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File

/**
 *@Date 2020/10/11
 *@Time 15:25
 *@author SpreadWater
 *@description
 */
class SetDataViewModel : ViewModel() {
    val updateService = ServiceCreator.create(UpdateInfo::class.java)
    val imageUrls = MutableLiveData<ArrayList<String>>()

    fun setImageList(imageList: ArrayList<String>) {
        imageUrls.value = imageList
    }

    fun updateInfo(user: UserInfo) {
        val token2 = "Bearer " + TokenConfig.token.token
        val builder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("qq", user.qq)
            .addFormDataPart("tel", user.tel)
            .addFormDataPart("description", user.description)
            .addFormDataPart("nickname", user.nickname)
        if (!imageUrls.value.isNullOrEmpty()) {
            val files = imageUrls.value!!.asSequence()
                .map {
                    File(
                        getFilePathFromContentUri(
                            it.toUri(),
                            MyApplication.context.contentResolver
                        )
                    )
                }
                .toList()
            files.forEachIndexed { index, file ->
                val suffix = file.name.substring(file.name.lastIndexOf(".") + 1)
                val mediaType = MediaType.parse("image/$suffix")
                val imageBody = RequestBody.create(mediaType, file)
                val name = "photo" + (index + 1)
                builder.addFormDataPart(name, file.name, imageBody)
            }
        }
        updateService.updateInfo(token2, builder.build().parts())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.status == 10000) {
                    Toast.toast("更新成功！")
                } else {
                    if (it.data.message.isNotEmpty()) {
                        Log.d("zt", it.data.message)
                    }
                    Log.d("zt", "null")
                    Toast.toast("更新失败!")
                }
            }
    }
}