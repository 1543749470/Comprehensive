package com.example.myapplication.utils.gsyvideo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import com.example.myapplication.R;
import com.nguyenhoanglam.imagepicker.helper.LogHelper;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import moe.codeest.enviews.ENDownloadView;
import moe.codeest.enviews.ENPlayView;

public class GsyVideo extends StandardGSYVideoPlayer {
    public GsyVideo(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public GsyVideo(Context context) {
        super(context);
    }

    public GsyVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    ENPlayView viewById= null;
    @Override
    protected void init(Context context) {
        super.init(context);
        viewById = findViewById(R.id.start);
        viewById.setVisibility(View.GONE);
        changeUiToPreparingShow();
    }

    @Override
    public int getLayoutId() {
        return R.layout.video_layout_standard;
    }

    /******************* 下方重载方法，在播放开始不显示底部进度和按键，不需要可屏蔽 ********************/

    protected boolean byStartedClick;

    @Override
    protected void onClickUiToggle(MotionEvent e) {
        if (mIfCurrentIsFullscreen && mLockCurScreen && mNeedLockFull) {
            setViewShowState(mLockScreen, VISIBLE);
            return;
        }
        byStartedClick = true;
        super.onClickUiToggle(e);

    }

    @Override
    public void changeUiToNormal() {
        super.changeUiToNormal();
        byStartedClick = false;
        setViewShowState(mTopContainer, VISIBLE);
        setViewShowState(mBottomContainer, INVISIBLE);
        setViewShowState(mStartButton, INVISIBLE);
        setViewShowState(mLoadingProgressBar, INVISIBLE);
        setViewShowState(mThumbImageViewLayout, VISIBLE);
        setViewShowState(mBottomProgressBar, INVISIBLE);
    }

    @Override
    public void changeUiToPreparingShow() {
        super.changeUiToPreparingShow();
        Debuger.printfLog("Sample changeUiToPreparingShow");
        setViewShowState(mBottomContainer, INVISIBLE);
        setViewShowState(mTopContainer, INVISIBLE);
        setViewShowState(mLoadingProgressBar, INVISIBLE);
        setViewShowState(mBackButton, INVISIBLE);
        setViewShowState(mTitleTextView, INVISIBLE);
        setViewShowState(mFullscreenButton, INVISIBLE);
        if (viewById!=null){
            viewById.setVisibility(View.GONE);
        }
    }

    @Override
    protected void changeUiToPlayingBufferingShow() {
        super.changeUiToPlayingBufferingShow();
        Debuger.printfLog("Sample changeUiToPlayingBufferingShow");
        if (!byStartedClick) {
            setViewShowState(mBottomContainer, INVISIBLE);
            setViewShowState(mStartButton, INVISIBLE);
        }
    }

    @Override
    public void changeUiToPlayingShow() {
        super.changeUiToPlayingShow();
        Debuger.printfLog("Sample changeUiToPlayingShow");
        if (!byStartedClick) {
            setViewShowState(mBottomContainer, INVISIBLE);
            setViewShowState(mStartButton, INVISIBLE);
        }
    }



    @Override
    public void startAfterPrepared() {
        super.startAfterPrepared();
        Debuger.printfLog("Sample startAfterPrepared");
        setViewShowState(mBottomContainer, INVISIBLE);
        setViewShowState(mStartButton, INVISIBLE);
        setViewShowState(mBottomProgressBar, VISIBLE);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        byStartedClick = true;
        super.onStartTrackingTouch(seekBar);
    }
}
