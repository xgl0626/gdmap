package com.example.gdmap.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * @Author: xgl
 * @ClassName: SearchFragement
 * @Description:
 * @Date: 2020/10/4 14:02
 */
abstract class BaseFragment :Fragment()
{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
        initClick()
    }

    abstract fun initView()
    abstract fun initClick()
    abstract fun initData()
}