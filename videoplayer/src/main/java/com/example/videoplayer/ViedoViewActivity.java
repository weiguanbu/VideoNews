package com.example.videoplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import butterknife.ButterKnife;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by wagaranai on 2016/8/9.
 */

public class ViedoViewActivity extends AppCompatActivity {
    private static final String KEY_VIDEO_PATH = "KEY_VIDEO_PATH";

    //开启当前activity，传入视频播放路径
    public static void open(Context context, String videoPath) {
        Intent intent = new Intent(context, ViedoViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置窗口背景色
        getWindow().setBackgroundDrawableResource(android.R.color.black);
        //设置当前内容视图
//                setContentView(R.layout.);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PowerManager pm= (PowerManager) getSystemService(POWER_SERVICE);
//        if (pm.isScreenOn()){
//            videoView.setVideoPath(getIntent().getStringExtra());
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.stopPlayback();
    }

    @Override
    public void onContentChanged() {

        //初始化视图
        initBufferViews();
        //初始化videoview
        initVideoView();
        ButterKnife.bind(this);
    }

    private void initBufferViews() {
//        tvBufferInfo=findViewById()
//        ivLoading=findViewById()
//        tvBufferInfo
    }

    private VideoView videoView;
    private MediaPlayer mediaPlayer;
    private ImageView ivLoading;
    private TextView tvBufferInfo;
   private int downloadSpeed;
   private int bufferPercent;
    private void initVideoView() {
        //        videoView=findViewById()
        //控制(暂停，播放，快进等)
        videoView.setMediaController(new MediaController(this));
        videoView.setKeepScreenOn(true);
        videoView.requestFocus();
        //资源准备监听处理
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer=mp;
                mediaPlayer.setBufferSize(512*1024);
            }
        });
        //缓冲信息监听
        videoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {

            }
        });
        //状态信息监听
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what){
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START://开始缓冲
                        if (videoView.isPlaying()){
                            videoView.pause();
                        }
                        showBufferViews();
                    break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END://结束缓冲
                        videoView.start();
                        hideBufferViews();
                        break;
                    case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED://缓冲下载速率
                        downloadSpeed=extra;
                        updateBufferViewInfo();
                        break;
                }
                return true;
            }
        });

    }

    private void hideBufferViews() {
        tvBufferInfo.setVisibility(View.INVISIBLE);
        ivLoading.setVisibility(View.INVISIBLE);
    }

    private void showBufferViews() {
        tvBufferInfo.setVisibility(View.VISIBLE);
        ivLoading.setVisibility(View.VISIBLE);
        downloadSpeed=0;
        bufferPercent=0;
    }

    private void updateBufferViewInfo() {
        String info=String.format(Locale.CHINA,"%d%%","%dkb/s",bufferPercent,downloadSpeed);
    }
}
