package com.thereza.videoplayerapp.repositories;

import androidx.lifecycle.MutableLiveData;

import com.thereza.videoplayerapp.models.PlayItem;

import java.util.ArrayList;

public class PlayListRepo {

    private static PlayListRepo mInstance;
    private ArrayList<PlayItem> mDataSet = new ArrayList<>();

    public static PlayListRepo getInstance(){
        if (mInstance == null){
            mInstance = new PlayListRepo();
        }
        return mInstance;
    }

    public MutableLiveData<ArrayList<PlayItem>> getPlayLists(){

        setPlayListItem();
        MutableLiveData<ArrayList<PlayItem>> data = new MutableLiveData<>();
        data.setValue(mDataSet);
        return data;
    }

    public void setPlayListItem(){
        mDataSet.add(
                new PlayItem("Big Buck Bunny","http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
        );
        mDataSet.add(
                new PlayItem("Elephant Dream","http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4")
        );
        mDataSet.add(
                new PlayItem("For Bigger Blazes","http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4")
        );
        mDataSet.add(
                new PlayItem("For Bigger Escape","http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4")
        );
        mDataSet.add(
                new PlayItem("For Bigger Fun","http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4")
        );
        mDataSet.add(
                new PlayItem("For Bigger Joyrides","http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4")
        );
        mDataSet.add(
                new PlayItem("For Bigger Meltdowns","http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4")
        );
        mDataSet.add(
                new PlayItem("Sintel","http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4")
        );
        mDataSet.add(
                new PlayItem("Subaru Outback On Street","http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4")
        );
        mDataSet.add(
                new PlayItem("Tears of Steel","http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4")
        );
        mDataSet.add(
                new PlayItem("Volkswagen GTI Review","http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4")
        );

    }
}
