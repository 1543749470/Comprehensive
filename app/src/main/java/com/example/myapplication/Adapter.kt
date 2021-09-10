package com.example.myapplication

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class Adapter(fm:FragmentManager,
              list:MutableList<Fragment>,title:MutableList<String>): FragmentPagerAdapter(fm) {
    var fragmentlist = list
    var titlelist = title



    override fun getItem(position: Int): Fragment {
        return fragmentlist[position]
    }

    override fun getCount(): Int {
        return   fragmentlist.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titlelist[position]
    }
}