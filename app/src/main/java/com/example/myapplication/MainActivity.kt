package com.example.myapplication

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import androidx.lifecycle.Observer
import com.aliyun.player.IPlayer
import com.aliyun.player.bean.ErrorInfo
import com.aliyun.player.bean.InfoBean
import com.aliyun.player.nativeclass.TrackInfo
import com.aliyun.player.source.VidSts
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.utils.video.AliyunRenderView
import com.example.syntheticalproject.viewmodel.state.MainActivityViewModel
import com.nguyenhoanglam.imagepicker.helper.LogHelper
import kotlinx.android.synthetic.main.activity_main.*
import me.hgj.jetpackmvvm.base.activity.BaseVmDbActivity
import me.hgj.jetpackmvvm.ext.parseState

class MainActivity : BaseVmDbActivity<MainActivityViewModel, ActivityMainBinding>() {



    override fun layoutId(): Int =R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        //设置渲染View的类型，可选 SurfaceType.TEXTURE_VIEW 和 SurfaceType.SURFACE_VIEW
        main_ali.setSurfaceType(AliyunRenderView.SurfaceType.SURFACE_VIEW)
        main_ali.setAutoPlay(true)
        main_ali.isLoop = true

        mViewModel.run {
            allLiveData.observe(this@MainActivity, Observer {
                    resultState->
                parseState(resultState,{data->
                    LogHelper.instance?.e("mainvideo---------"+data.toString())
                    var aliyunVidSts =  VidSts();
                    aliyunVidSts.setVid(data.data.videoId);
                    aliyunVidSts.setAccessKeyId(data.data.accessKeyId);
                    aliyunVidSts.setAccessKeySecret(data.data.accessKeySecret);
                    aliyunVidSts.setSecurityToken(data.data.securityToken);
                    aliyunVidSts.setRegion("cn-shanghai");
                    //        设置播放源
                    main_ali.setDataSource(aliyunVidSts);
                    //        准备播放
                    main_ali.prepare();

                })
            })
        }
        main_seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
        //创建UrlSource播放
//        var urlSource = UrlSource()
//        urlSource.uri = "need_only_full_screen"
//        urlSource.title = "测试"
//        main_ali.setDataSource(urlSource)
//        main_ali.setAutoPlay(true);
//        main_ali.prepare()


//        main_ali.setDataSource(urlSource)
//        main_ali.prepare()


        main_bf.setOnClickListener {
            LogHelper.instance?.e("开始------------")
            main_ali.start()

            main_ali.start()
        }

        main_zt.setOnClickListener {
            LogHelper.instance?.e("暂停------------")
            main_ali.pause()

            main_ali.pause()
        }

        main_tz.setOnClickListener {
            LogHelper.instance?.e("停止------------")
            main_ali.stop()
            main_ali.stop()
        }


        main_ali.setOnCompletionListener {
            //播放完成
            LogHelper.instance?.e("播放完成--------")
        }

        main_ali.setOnErrorListener {
            //出错事件
            LogHelper.instance?.e("出错事件--------")
        }

        main_ali.setOnPreparedListener {
            //准备成功事件
            LogHelper.instance?.e("准备成功事件--------")
            LogHelper.instance?.e("播放总时长--------"+main_ali.duration)
            main_seek.max = main_ali.duration.toInt()/1000

        }

        main_ali.setOnVideoRenderedListener { l, l2 ->
            //视频分辨率变化回调
            LogHelper.instance?.e("视频分辨率变化回调--------")
        }

        main_ali.setOnRenderingStartListener {
            //首帧渲染显示事件
            LogHelper.instance?.e("首帧渲染显示事件--------")
        }

        main_ali.setOnInfoListener(object : IPlayer.OnInfoListener{
            override fun onInfo(p0: InfoBean?) {
                //其他信息的事件，type包括了：循环播放开始，缓冲位置，当前播放位置，自动播放开始等
                LogHelper.instance?.e("其他信息的事件，type包括了：循环播放开始，缓冲位置，当前播放位置，自动播放开始等--------")
                LogHelper.instance?.e("播放位置-----"+p0?.extraValue)
                main_seek.setProgress((p0?.extraValue?.div(1000))?.toInt()!!-50,true)
                main_time.text = (p0?.extraValue?.div(1000))?.toString()

            }

        })

        main_ali.setOnLoadingStatusListener(object : IPlayer.OnLoadingStatusListener{
            override fun onLoadingBegin() {
                //缓冲开始
                LogHelper.instance?.e("缓冲开始--------")
            }

            override fun onLoadingProgress(p0: Int, p1: Float) {
                //缓冲进度
                LogHelper.instance?.e("缓冲进度--------"+p0+"-----"+p1)
            }

            override fun onLoadingEnd() {
                //缓冲结束
                LogHelper.instance?.e("缓冲结束--------")
            }

        })

        main_ali.setOnSeekCompleteListener {
            //拖动结束
            LogHelper.instance?.e("拖动结束--------")
        }

        main_ali.setOnSubtitleDisplayListener(object : IPlayer.OnSubtitleDisplayListener{
            override fun onSubtitleExtAdded(p0: Int, p1: String?) {
                LogHelper.instance?.e(p1.toString())
            }

            override fun onSubtitleShow(p0: Int, p1: Long, p2: String?) {
                //显示弹幕
                LogHelper.instance?.e("显示弹幕--------")
            }

            override fun onSubtitleHide(p0: Int, p1: Long) {
                //隐藏弹幕
                LogHelper.instance?.e("隐藏弹幕--------")
            }

        })

        main_ali.setOnTrackChangedListener(object : IPlayer.OnTrackChangedListener{
            override fun onChangedSuccess(p0: TrackInfo?) {
                //切换音视频流或者清晰度成功
                LogHelper.instance?.e("切换音视频流或者清晰度成功--------")
            }

            override fun onChangedFail(p0: TrackInfo?, p1: ErrorInfo?) {
                //切换音视频流或者清晰度失败
                LogHelper.instance?.e("切换音视频流或者清晰度失败--------")
            }

        })


        main_ali.setOnStateChangedListener(object : IPlayer.OnStateChangedListener{
            override fun onStateChanged(p0: Int) {
                //播放器状态改变事件
                LogHelper.instance?.e("播放器状态改变事件--------")
            }

        })

        main_ali.setOnSnapShotListener(object : IPlayer.OnSnapShotListener{
            override fun onSnapShot(p0: Bitmap?, p1: Int, p2: Int) {
                //截图事件
                LogHelper.instance?.e("截图事件--------")
            }

        })
    }

    override fun showLoading(message: String) {

    }

    override fun onResume() {
        super.onResume()
        mViewModel.getVideoPlay()

    }
    override fun dismissLoading() {
    }

    override fun createObserver() {

    }


}