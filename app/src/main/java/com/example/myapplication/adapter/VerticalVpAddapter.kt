package com.example.syntheticalproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.utils.gsyvideo.GsyVideo
import com.nguyenhoanglam.imagepicker.helper.LogHelper
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import kotlinx.android.synthetic.main.layout_custom_progress_dialog_view.view.*

class VerticalVpAddapter(context: Context,list: MutableList<String>) : RecyclerView.Adapter<VerticalVpAddapter.VerticalVpViewHolder>() {
    var context = context
    var list = list

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VerticalVpAddapter.VerticalVpViewHolder {
        return VerticalVpViewHolder(LayoutInflater.from(context).inflate(R.layout.item_viewpager,parent,false))
    }

    override fun onBindViewHolder(holder: VerticalVpAddapter.VerticalVpViewHolder, position: Int) {
        val item_gsy = holder.itemView.findViewById<GsyVideo>(R.id.item_gsy)
        item_gsy.setUp(list[position],true,position.toString())
        item_gsy.isShowFullAnimation = true
        item_gsy.changeUiToPreparingShow()
        item_gsy.setIsTouchWiget(false)
        if (position==0){
            item_gsy.startPlayLogic()
        }

        /***
         * 状态监听
         */
        item_gsy.setGSYStateUiListener {
            LogHelper.instance?.e("setGSYStateUiListener------------"+it)
        }
    }

    override fun getItemCount(): Int = list.size

    class VerticalVpViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}