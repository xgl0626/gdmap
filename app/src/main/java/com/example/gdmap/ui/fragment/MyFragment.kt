package com.example.gdmap.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.gdmap.R
import com.example.gdmap.ui.activity.QuestionActivity
import com.example.gdmap.ui.activity.SetDataActivity
import com.example.gdmap.ui.activity.SignActivity
import com.example.gdmap.ui.activity.SystemActivity
import com.example.gdmap.utils.AvatarUtils
import com.example.gdmap.utils.ImmersedStatusbarUtils
import kotlinx.android.synthetic.main.fragment_me.*
import kotlinx.android.synthetic.main.fragment_see.*

class MyFragment : Fragment(),View.OnClickListener{
    private var saveData: SharedPreferences?=null
    private var name:String?=null
    private var content:String?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_me,container,false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        this.activity?.let { ImmersedStatusbarUtils.initSetContentView(it,tb_fragment_me) }
        initView()
        super.onActivityCreated(savedInstanceState)
    }
    private fun initView() {
        saveData=context?.getSharedPreferences("userdata",MODE_PRIVATE)
        bt_fragment_me_data.setOnClickListener(this)
        bt_fragment_me_question.setOnClickListener(this)
        bt_fragment_me_set.setOnClickListener(this)
        bt_fragment_me_day.setOnClickListener(this)
        iv_fragment_me_user_avator.setOnClickListener(this)
        setImageViewAndButton(R.mipmap.fragment_me_bt_day,bt_fragment_me_day,2)
        setImageViewAndButton(R.mipmap.fragment_me_bt_set,bt_fragment_me_set,2)
        setImageViewAndButton(R.mipmap.fragment_me_bt_question,bt_fragment_me_question,2)
        setImageViewAndButton(R.mipmap.acticity_login_name,bt_fragment_me_data,2)
    }

    private fun setImageViewAndButton(drawable: Int, view: TextView, id: Int) {
        val drawable: Drawable =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                resources.getDrawable(drawable, null)
            } else {
                TODO("VERSION.SDK_INT < LOLLIPOP")
            }
        drawable.setBounds(0, 0, 80, 80)
        when (id) {
            1 -> view.setCompoundDrawables(null, drawable, null, null)
            2 -> view.setCompoundDrawables(drawable, null, null, null)
        }
    }

    override fun onClick(view: View?) {
        when(view?.id)
        {
            R.id.bt_fragment_me_data->changeToActivity2(SetDataActivity())
            R.id.bt_fragment_me_set->changeToActivity(SystemActivity())
            R.id.bt_fragment_me_day->changeToActivity(SignActivity())
            R.id.bt_fragment_me_question->changeToActivity(QuestionActivity())
            R.id.iv_fragment_me_user_avator->{
                AvatarUtils.choicePhoto(context as Activity)
        }
        }
    }
    private fun changeToActivity(activity: Activity) {
        val intent = Intent(this.activity, activity::class.java)
        startActivity(intent)
    }
    private fun changeToActivity2(activity: Activity) {
        val intent = Intent(this.activity, activity::class.java)
        startActivity(intent)
    }
    override fun onStart() {
        tv_fragment_me_user_data.text = saveData?.getString("data","")
        tv_fragment_me_user_name.text = saveData?.getString("name","")
        super.onStart()
    }
}