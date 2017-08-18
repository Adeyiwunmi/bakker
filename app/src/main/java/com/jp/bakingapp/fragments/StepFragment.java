package com.jp.bakingapp.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.jp.bakingapp.R;
import com.jp.bakingapp.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.idVideo_view) SimpleExoPlayerView exoPlayerView;
    @BindView(R.id.idTextViewStepDesc) TextView stepDescTV;
    @BindView(R.id.idImageStepFrag) ImageView stepImage;
    @BindView(R.id.idButtonNextStep) Button btnNextStep;
    @BindView(R.id.idButtonPrevStep) Button btnPrevStep;
    public static final String KEY_STEP_VID_URL = "video_url";
    public static final String KEY_STEP_DESC = "desc";
    public static final String KEY_STEP_INDEX= "index";
    public  static  final  String KEY_STEP_THUMBNAIL_URL = "thumbnailUrl";
    private static final String PLAYBACK_POSITION = "playback pos";
    private static final String CURRENT_WINDOW_INDEX = "currentwindowindex";
    private static final String AUTOPLAY = "autoplay";
    private  int currentWindow = 0;
    private int step_index;
    private  static long playbackPosition = 0;
    private boolean playWhenReady = false;
    private SimpleExoPlayer player;
    private static final String TAG = "tag";

    private  int orientation;
    private String vid_url;
    private Context context;
    private static  final DefaultBandwidthMeter BANDWIDTH_METER= new DefaultBandwidthMeter();
    public StepFragment() {
        // Required empty public constructor
    }


    public static StepFragment newInstance(String step_video_url,
                                           String step_desc,
                                           String step_thumbnail_url,
                                           int step_index){
        Bundle bundle = new Bundle();
        bundle.putString(KEY_STEP_VID_URL,step_video_url);
        bundle.putString(KEY_STEP_DESC, step_desc);
        bundle.putString(KEY_STEP_THUMBNAIL_URL, step_thumbnail_url);
        bundle.putInt(KEY_STEP_INDEX, step_index);
        StepFragment stepFragment = new StepFragment();
        stepFragment.setArguments(bundle);
        return stepFragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
       vid_url = bundle.getString(KEY_STEP_VID_URL);
        String step_desc = bundle.getString(KEY_STEP_DESC);
        step_index=  bundle.getInt(KEY_STEP_INDEX);
        stepDescTV.setText(step_desc);
        checkAndLoadImage(bundle.getString(KEY_STEP_THUMBNAIL_URL));


        if (savedInstanceState != null) {
              initializePlayer(vid_url, savedInstanceState.getLong(PLAYBACK_POSITION),
                      savedInstanceState.getBoolean(AUTOPLAY),
                      savedInstanceState.getInt(CURRENT_WINDOW_INDEX));
           // Toast.makeText(getActivity(), "savedinstance was not null" , Toast.LENGTH_SHORT).show();
            //Toast.makeText(getActivity(), String.valueOf(savedInstanceState.getLong(PLAYBACK_POSITION)),Toast.LENGTH_SHORT).show();
        }   else {

            initializePlayer(vid_url);

        }

         btnNextStep.setOnClickListener(this);
        btnPrevStep.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }


public  void  initializePlayer(String video_url ){
if (player == null) {

//TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
    player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()),
            new DefaultTrackSelector(), new DefaultLoadControl());
    exoPlayerView.setPlayer(player);

    player.setPlayWhenReady(playWhenReady);
    player.seekTo(currentWindow, playbackPosition);


   //     player.seekTo(playbackPosition);

    Uri uri = Uri.parse(video_url);
    MediaSource mediaSource = buildMediaSource(uri);
    player.prepare(mediaSource);

}

}


    public  void  initializePlayer(String video_url, final long position, boolean play, int window ) {


//TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            exoPlayerView.setPlayer(player);

            player.setPlayWhenReady(play);
            player.seekTo(window,position);


            //     player.seekTo(playbackPosition);

            Uri uri = Uri.parse(video_url);
            MediaSource mediaSource = buildMediaSource(uri);
            player.prepare(mediaSource);

            Handler hanlder = new Handler();
            hanlder.postDelayed(new Runnable() {
                @Override
                public void run() {
                    player.seekTo(position);
                }
            }, 2000);
        }


    private MediaSource buildMediaSource(Uri uri){
        return  new ExtractorMediaSource(uri,new DefaultHttpDataSourceFactory("ua"),new DefaultExtractorsFactory(), null,null);

}

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23) && (vid_url != null) ){
           initializePlayer(vid_url);
        }

    }

    @SuppressLint("InlinedApi")
    private  void hideSystemUi(){
        exoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE|
        View.SYSTEM_UI_FLAG_FULLSCREEN
        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }
/**

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23){
            //playbackPosition = player.getCurrentPosition();
            releasePlayer();
        }

    }
*/
    private void releasePlayer(){
        if (player != null){
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.stop();
            player.release();
            player = null;
        }

    }

    /**
     * @Override
    public void onStop() {
    super.onStop();
    if (Util.SDK_INT > 23){
    releasePlayer();
    }
    }

     */


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
         /*
        * A simple configuration change such as screen rotation will destroy this activity
        * so we'll save the player state here in a bundle (that we can later access in onCreate) before everything is lost
        * NOTE: we cannot save player state in onDestroy like we did in onPause and onStop
        * the reason being our activity will be recreated from scratch and we would have lost all members (e.g. variables, objects) of this activity
        */


            outState.putLong(PLAYBACK_POSITION, getPlayerInstance().getCurrentPosition());
            outState.putInt(CURRENT_WINDOW_INDEX, getPlayerInstance().getCurrentWindowIndex());
            outState.putBoolean(AUTOPLAY, getPlayerInstance().getPlayWhenReady());

    }

private  void  checkAndLoadImage(String stepImageUrl){
    if (!TextUtils.isEmpty(stepImageUrl)){
        exoPlayerView.setVisibility(View.INVISIBLE);
        stepImage.setVisibility(View.VISIBLE);
        Glide.with(getActivity().getApplicationContext()).load(stepImageUrl).into(stepImage);
    }

}

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.idButtonNextStep:
                nextButtonClick();
                break;
            case R.id.idButtonPrevStep:
                prevButtonClick();
                break;
            default:
                break;
        }
    }

    public void nextButtonClick(){
        ArrayList<Step> stepArrayList = RecipeDetailFragment.stepArrayList;
        int nextPosition = step_index +1;
        int arraysize = stepArrayList.size();
        if (nextPosition <= arraysize){
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            Step nextStep = stepArrayList.get(nextPosition);
            StepFragment nextStepFragment = StepFragment.newInstance(nextStep.getVideoUrl(),
                    nextStep.getDescription(),nextStep.getThumbnailUrl(), nextPosition);

            int frameLayaoutId;
            if (isTablet()){
               frameLayaoutId = R.id.idcontainerStepFragLarge;
            }  else {
                frameLayaoutId = R.id.idContainer_Step_activity;
            }
           fragmentTransaction.replace(frameLayaoutId, nextStepFragment);
            fragmentTransaction.commit();
        }
        else {
            Toast.makeText(getContext(), "No Videos", Toast.LENGTH_SHORT).show();
        }
    }

    boolean isTablet(){
        context = getActivity().getBaseContext();
        orientation = context.getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE ||
                context.getResources().getConfiguration().smallestScreenWidthDp == 600) {
            return true;

        } else {
            return false;
        }

    }



    public void prevButtonClick(){
        ArrayList<Step> stepArrayList = RecipeDetailFragment.stepArrayList;
        int prevPosition = step_index -1;

        if (prevPosition >= 0){
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            Step nextStep = stepArrayList.get(prevPosition);
            StepFragment prevStepFragment = StepFragment.newInstance(nextStep.getVideoUrl(),
                    nextStep.getDescription(),nextStep.getThumbnailUrl(), prevPosition);

            int frameLayaoutId;
            if (isTablet()){
                frameLayaoutId = R.id.idcontainerStepFragLarge;
            }  else {
                frameLayaoutId = R.id.idContainer_Step_activity;
            }
            fragmentTransaction.replace(frameLayaoutId, prevStepFragment);
            fragmentTransaction.commit();
        }  else {
            Toast.makeText(getContext(), "" +
                    "No Videos", Toast.LENGTH_SHORT).show();

        }
    }

private SimpleExoPlayer getPlayerInstance(){
    return StepFragment.this.player;

}


}
