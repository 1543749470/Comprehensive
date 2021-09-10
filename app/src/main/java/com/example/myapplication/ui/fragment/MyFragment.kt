package com.example.myapplication.ui.fragment

import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.MyfragmentBinding
import com.example.syntheticalproject.viewmodel.state.MainActivityViewModel
import com.fatoan.android.xnwapp.app.base.BaseFragment

class MyFragment: BaseFragment<MainActivityViewModel, MyfragmentBinding>() {
    override fun layoutId(): Int = R.layout.myfragment

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun lazyLoadData() {
    }
}