package com.thereza.videoplayerapp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.thereza.videoplayerapp.R;
import com.thereza.videoplayerapp.utils.NetworkUtil;
import com.thereza.videoplayerapp.utils.SnackbarFactory;
import com.thereza.videoplayerapp.utils.ToastFactory;
import com.thereza.videoplayerapp.viewmodels.MainActivityViewModel;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class MainActivity extends AppCompatActivity {

    private Activity mActivity;
    private Context mContext;
    private MediaController mController;
    private int mCurrentPosition = 0;
    private static final String PLAYBACK_TIME = "play_time";
    private MainActivityViewModel mMainActivityViewModel;
    private int mPosition;
    private boolean isFullScreen,isTitleShows;

    @Bind(R.id.videoView)
    public VideoView mVideoView;
    @Bind(R.id.pb_buffer)
    public ProgressBar pbBuffer;
    @Bind(R.id.iv_fullscreen)
    public ImageView ivFullScreen;
    @Bind(R.id.rl_titlebar)
    RelativeLayout rlTitleBar;
    @Bind(R.id.rl_parent)
    RelativeLayout rl_parent;
    @Bind(R.id.tv_title)
    public TextView tvVideoTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }
        initVariable();
        initView();
    }

    private void initVariable() {

        mActivity = MainActivity.this;
        mContext = getApplicationContext();
        mController = new MediaController(mActivity);
        mPosition = 0;
        mMainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mMainActivityViewModel.init();
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mController.setAnchorView(mVideoView);
        mController.setPrevNextListeners(onClickListenerNext, onClickListenerPrevious);
        mVideoView.setMediaController(mController);

    }

    private View.OnClickListener onClickListenerNext = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!mMainActivityViewModel.getPlayItems().getValue().isEmpty()) {
                if (mPosition < mMainActivityViewModel.getPlayItems().getValue().size()-1) {
                    mPosition = mPosition + 1;
                }
                else {
                    mPosition = 0;
                }
                mVideoView.stopPlayback();
                initializePlayer(mPosition);
            }
        }
    };
    private View.OnClickListener onClickListenerPrevious = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mPosition != 0) {
                mPosition = mPosition - 1;
            }
            else {
                mPosition = mMainActivityViewModel.getPlayItems().getValue().size()-1;
            }
            mVideoView.stopPlayback();
            initializePlayer(mPosition);
        }
    };

    @OnClick(R.id.iv_fullscreen)
    public void fullScreen(){

        if (isFullScreen) {
            ivFullScreen.setImageResource(R.drawable.ic_fullscreen);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            isFullScreen = false;
        }
        else {
            ivFullScreen.setImageResource(R.drawable.ic_fullscreen_exit);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            isFullScreen = true;
        }
    }

    @OnTouch(R.id.rl_parent)
    public boolean showHideTitle(View v, MotionEvent motionEvent){
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            if (isTitleShows){
                showVideoTitle();
                isTitleShows = false;
            }
            else {
                hideVideoTitle();
                isTitleShows = true;
            }
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        initializePlayer(mPosition);
        if (!NetworkUtil.isNetworkConnected(mContext)){
            ToastFactory.createCustomToast(mActivity);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mVideoView.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current playback position (in milliseconds) to the
        // instance state bundle.
        outState.putInt(PLAYBACK_TIME, mVideoView.getCurrentPosition());
    }

    private void initializePlayer(int position) {
        ToastFactory.createCenterToast(mActivity);
        //showProgressBar();
        // Buffer and decode the video sample.
        if (!mMainActivityViewModel.getPlayItems().getValue().isEmpty()) {
            mVideoView.setVideoURI(Uri.parse(mMainActivityViewModel.getPlayItems().getValue().get(position).getVideoPath()));
            tvVideoTitle.setText(mMainActivityViewModel.getPlayItems().getValue().get(position).getTitle());
        }
        // Listener for onPrepared() event (runs after the media is prepared).
        mVideoView.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        //hideProgressBar();
                        mController.show(0);
                        // Restore saved position, if available.
                        if (mCurrentPosition > 0) {
                            mVideoView.seekTo(mCurrentPosition);
                        } else {
                            // Skipping to 1 shows the first frame of the video.
                            mVideoView.seekTo(1);
                        }
                        // Start playing!
                        mVideoView.start();
                    }
                });

        // Listener for onCompletion() event (runs after media has finished
        // playing).
        mVideoView.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {

                        if (mPosition < mMainActivityViewModel.getPlayItems().getValue().size()) {
                            mPosition = mPosition + 1;
                            mVideoView.stopPlayback();
                            mVideoView.setVideoURI(Uri.parse(mMainActivityViewModel.getPlayItems().getValue().get(mPosition).getVideoPath()));
                            mVideoView.start();
                        }

                    }
                });
    }

    // Release all media-related resources. In a more complicated app this
    // might involve unregistering listeners or releasing audio focus.
    private void releasePlayer() {
        mVideoView.stopPlayback();
    }

    private void showProgressBar(){
        pbBuffer.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar(){
        pbBuffer.setVisibility(View.GONE);
    }

    private void showVideoTitle(){
        rlTitleBar.setVisibility(View.VISIBLE);
    }
    private void hideVideoTitle(){
        rlTitleBar.setVisibility(View.GONE);
    }
}
