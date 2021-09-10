package com.example.syntheticalproject

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.utils.gsyvideo.ScrollCalculatorHelper
import com.example.syntheticalproject.adapter.VerticalVpAddapter
import com.example.syntheticalproject.utils.gsyvideo.DpTools
import com.nguyenhoanglam.imagepicker.helper.LogHelper
import kotlinx.android.synthetic.main.activity_cam.*


class camActivity : AppCompatActivity() {
    var adapter:VerticalVpAddapter?=null
    var a=false
    var list = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cam)
        initView()
    }

    private fun initView() {
        list.add("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4")
        list.add("http://vjs.zencdn.net/v/oceans.mp4")
        list.add("https://media.w3.org/2010/05/sintel/trailer.mp4")
        list.add("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4")
        initItem()
        cam_sm.setOnRefreshListener {
            list.clear()
            list.add("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4")
            list.add("http://vjs.zencdn.net/v/oceans.mp4")
            list.add("https://media.w3.org/2010/05/sintel/trailer.mp4")
            list.add("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4")
            cam_sm.finishRefresh()//刷新完成
        }

        cam_sm.setOnLoadMoreListener {
            var list1 = mutableListOf<String>()
            list1.add("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4")
            list1.add("http://vjs.zencdn.net/v/oceans.mp4")
            list1.add("https://media.w3.org/2010/05/sintel/trailer.mp4")
            list1.add("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4")
            list.addAll(list1)
            cam_sm.finishLoadMore()
        }
    }

    private fun initItem() {
        var linearLayoutManager =  LinearLayoutManager(this)
         adapter = VerticalVpAddapter(this,list)
        //获取屏幕宽高
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        //自定播放帮助类 限定范围为屏幕一半的上下偏移180   括号里不用在意 因为是一个item一个屏幕
        var scrollCalculatorHelper =  ScrollCalculatorHelper(R.id.item_gsy
        , dm.heightPixels / 2 - DpTools.dip2px(this, 180F)
        , dm.heightPixels / 2 + DpTools.dip2px(this, 180F))

        //让RecyclerView有ViewPager的翻页效果
        var pagerSnapHelper =  PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(cam_viewpager2)

        //设置LayoutManager和Adapter
        cam_viewpager2.setLayoutManager(linearLayoutManager)
        cam_viewpager2.setAdapter(adapter)

       cam_viewpager2.addOnScrollListener(object : RecyclerView.OnScrollListener() {
           //第一个可见视图,最后一个可见视图
           var firstVisibleItem = 0
           var lastVisibleItem = 0//第一个可见视图,最后一个可见视图

           override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
               super.onScrollStateChanged(recyclerView, newState)
                //如果newState的状态==RecyclerView.SCROLL_STATE_IDLE
               //播放对应的视频
               scrollCalculatorHelper.onScrollStateChanged(recyclerView, newState)
               LogHelper.instance?.e("onScrollStateChanged-------")
           }

           override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
               super.onScrolled(recyclerView, dx, dy)

               firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()
               lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
               LogHelper.instance?.e("有几个item"+firstVisibleItem + "    " + lastVisibleItem)
               //一屏幕显示一个item 所以固定1
               //实时获取设置 当前显示的GSYBaseVideoPlayer的下标
               scrollCalculatorHelper.onScroll(recyclerView, firstVisibleItem, lastVisibleItem, 1)
           }
       })
    }
}