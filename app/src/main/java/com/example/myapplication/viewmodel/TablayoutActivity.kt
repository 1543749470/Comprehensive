package com.example.myapplication.viewmodel

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.example.myapplication.Adapter
import com.example.myapplication.R
import com.example.myapplication.ui.fragment.MyFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_tablayout.*
import org.w3c.dom.Text

class TablayoutActivity : AppCompatActivity() {
    var fragmentlist = mutableListOf<Fragment>()
    var titlefragmentlist = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tablayout)
        initData()
    }

    private fun initData() {
            fragmentlist.clear()
            titlefragmentlist.clear()
            fragmentlist.add(MyFragment())
            fragmentlist.add(MyFragment())
            fragmentlist.add(MyFragment())
            fragmentlist.add(MyFragment())
            fragmentlist.add(MyFragment())
            fragmentlist.add(MyFragment())
            titlefragmentlist.add("测试"+1)
            titlefragmentlist.add("测试"+2)
            titlefragmentlist.add("测试"+3)
            titlefragmentlist.add("测试"+4)
            titlefragmentlist.add("测试"+5)
            titlefragmentlist.add("测试"+6)


        viewpage.adapter = Adapter(supportFragmentManager,fragmentlist,titlefragmentlist)
        tablayout.setupWithViewPager(viewpage)
        getView()

        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
                setStatus(tab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                setStatus(tab)
            }

        })
    }

    fun getView(){
        for (index in 0..tablayout.tabCount-1){
            var inflate = LayoutInflater.from(this).inflate(R.layout.item, null)
            inflate.findViewById<TextView>(R.id.tabname).text = titlefragmentlist[index]
            tablayout.getTabAt(index)?.setCustomView(inflate)
        }

        tablayout.getTabAt(0)?.getCustomView()?.findViewById<TextView>(R.id.tabname)?.setTextColor(
            Color.RED)
        tablayout.getTabAt(0)?.getCustomView()?.findViewById<LinearLayout>(R.id.iv)?.setVisibility(
            View.VISIBLE)

    }

    fun setStatus(tab: TabLayout.Tab?){
        for (index in 0..tablayout.tabCount-1){
            if (index == tab?.position){
                tablayout.getTabAt(index)?.getCustomView()?.findViewById<TextView>(R.id.tabname)?.setTextColor(
                    Color.RED)
                tablayout.getTabAt(index)?.getCustomView()?.findViewById<LinearLayout>(R.id.iv)?.setVisibility(
                    View.VISIBLE)
            }else{
                tablayout.getTabAt(index)?.getCustomView()?.findViewById<TextView>(R.id.tabname)?.setTextColor(
                    Color.BLACK)
                tablayout.getTabAt(index)?.getCustomView()?.findViewById<LinearLayout>(R.id.iv)?.setVisibility(
                    View.INVISIBLE)
            }
        }
    }

}