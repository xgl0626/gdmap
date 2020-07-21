package com.example.gdmap.ui.fragment

import android.graphics.Color.red
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.example.gdmap.R
import com.example.gdmap.ui.Repository
import com.example.gdmap.ui.adapter.FragmentsAdapter
import com.example.gdmap.utils.ImmersedStatusbarUtils
import com.example.gdmap.utils.NetWorkUtils
import com.example.gdmap.utils.ToastUtils.showToast
import kotlinx.android.synthetic.main.activity_article_content.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_see.*

class SeeFragment :Fragment(),ViewPager.OnPageChangeListener
{
    private lateinit var fragmentsAdapter: FragmentsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_see,container,false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.activity?.let { ImmersedStatusbarUtils.initSetContentView(it,toolBar) }
        setFragment()
    }
      fun setFragment()
    {
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(ArticleFragment())
        fragmentList.add(VideoFragment())
        fragmentList.add(PlayFragment())
        fragmentsAdapter = FragmentsAdapter(fragmentList, activity!!.supportFragmentManager, 3)
        fragment_see_viewpager.adapter = fragmentsAdapter
        fragment_see_viewpager.addOnPageChangeListener(this)
        bt_nv_menu_fragment_see.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.it_article -> fragment_see_viewpager.currentItem = 0
                R.id.it_video -> fragment_see_viewpager.currentItem = 1
                R.id.it_play -> fragment_see_viewpager.currentItem = 2
                else -> "error".showToast()
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        bt_nv_menu_fragment_see.menu.getItem(position).isChecked = true
    }
}