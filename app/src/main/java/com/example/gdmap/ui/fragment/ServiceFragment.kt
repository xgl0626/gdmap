package com.example.gdmap.ui.fragment

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gdmap.R
import com.example.gdmap.base.BaseFragment
import com.example.gdmap.ui.activity.LoadDisasterActivity
import com.example.gdmap.ui.activity.SearchActivity
import com.example.gdmap.ui.activity.WriteQuestionActivity
import com.example.gdmap.ui.adapter.QuestionItemAdapter
import com.example.gdmap.ui.viewmodel.QuestionViewModel
import com.example.gdmap.utils.AddIconImage
import com.example.gdmap.utils.LogUtils
import com.example.gdmap.utils.setOnSingleClickListener
import kotlinx.android.synthetic.main.fragment_service.*

/**
 * @Author: xgl
 * @ClassName: ServiceFragment
 * @Description:
 * @Date: 2020/10/3 10:05
 */
class ServiceFragment : BaseFragment() {
    private var dialog: ProgressDialog? = null
    private var messageItemAdapter: QuestionItemAdapter? = null
    private val viewModel by lazy { ViewModelProviders.of(this).get(QuestionViewModel::class.java) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_service, container, false)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getQuestionListData()
    }

    override fun initClick() {
        et_fragment_home_search.setOnSingleClickListener {
            changeToActivity(SearchActivity())
        }
        tv_question.setOnSingleClickListener {
            changeToActivity(WriteQuestionActivity())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.inflateMenu(R.menu.service_fragment_menu)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initView() {
        AddIconImage.setImageViewToEditText(R.drawable.ic_search, et_fragment_home_search, 1)
        dialog = ProgressDialog(context)
        dialog?.setMessage("加载数据中...")
        dialog?.show()
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        messageItemAdapter = this.context?.let { QuestionItemAdapter(it) }
        recyclerView.adapter = messageItemAdapter

    }

    override fun initData() {
        srl_main.setOnRefreshListener {
            viewModel.getQuestionListData()
            srl_main.isRefreshing = false
        }
        viewModel.getQuestionListData()
        viewModel.commentData.observe(viewLifecycleOwner, Observer {
            messageItemAdapter?.addData(it)
            LogUtils.log_d<String>(it.toString())
        })
        dialog?.dismiss()
    }

    private fun changeToActivity(activity: Activity) {
        val intent = Intent(this.activity, activity::class.java)
        this.activity?.startActivity(intent)
    }

}