package com.example.syntheticalproject.viewmodel.state

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.syntheticalproject.app.HttpRequestManger
import com.example.syntheticalproject.data.bean.ALiYunVideoBean
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.requestNoCheck
import me.hgj.jetpackmvvm.state.ResultState

class MainActivityViewModel(application: Application):BaseViewModel(application) {
    val allLiveData: MutableLiveData<ResultState<ALiYunVideoBean>> = MutableLiveData()

    fun getVideoPlay(){
        requestNoCheck({HttpRequestManger.instance.getVideoPlay()},allLiveData)
    }
}