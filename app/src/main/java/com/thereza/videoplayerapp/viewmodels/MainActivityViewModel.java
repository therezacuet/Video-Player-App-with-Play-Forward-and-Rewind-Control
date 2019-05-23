package com.thereza.videoplayerapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thereza.videoplayerapp.models.PlayItem;
import com.thereza.videoplayerapp.repositories.PlayListRepo;

import java.util.ArrayList;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<ArrayList<PlayItem>> mPlayItems;
    private PlayListRepo mRepo;

    public void init(){
        if (mPlayItems != null)
            return;

        mRepo = PlayListRepo.getInstance();
        mPlayItems = mRepo.getPlayLists();
    }
    public LiveData<ArrayList<PlayItem>> getPlayItems(){
        return mPlayItems;
    }

}
