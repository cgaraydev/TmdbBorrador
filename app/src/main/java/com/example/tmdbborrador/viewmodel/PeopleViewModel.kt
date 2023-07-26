package com.example.tmdbborrador.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tmdbborrador.data.model.People
import com.example.tmdbborrador.data.model.PeopleImageList
import com.example.tmdbborrador.data.model.PeopleMovieCreditsList
import com.example.tmdbborrador.data.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PeopleViewModel : ViewModel() {

    private var peopleDetailLiveData = MutableLiveData<People>()
    private var peopleImageLiveData = MutableLiveData<PeopleImageList>()
    private var peopleMovieCreditsLiveData = MutableLiveData<PeopleMovieCreditsList>()

    fun getPeopleDetail(id: Int) {
        RetrofitInstance.api.getPeopleDetail(id.toString()).enqueue(object : Callback<People> {
            override fun onResponse(call: Call<People>, response: Response<People>) {
                if (response.isSuccessful) {
                    peopleDetailLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<People>, t: Throwable) {
                Log.d("peopleViewModel", t.message.toString())
            }
        })
    }

    fun getPeopleImages(id: Int) {
        RetrofitInstance.api.getPeopleImages(id.toString())
            .enqueue(object : Callback<PeopleImageList> {
                override fun onResponse(
                    call: Call<PeopleImageList>,
                    response: Response<PeopleImageList>
                ) {
                    if (response.isSuccessful) {
                        peopleImageLiveData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<PeopleImageList>, t: Throwable) {
                    Log.d("peopleViewModel", t.message.toString())
                }
            })
    }

    fun getPeopleMovieCredits(id: Int) {
        RetrofitInstance.api.getPeopleMovieCredits(id.toString())
            .enqueue(object : Callback<PeopleMovieCreditsList> {
                override fun onResponse(
                    call: Call<PeopleMovieCreditsList>,
                    response: Response<PeopleMovieCreditsList>
                ) {
                    if (response.isSuccessful) {
                        peopleMovieCreditsLiveData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<PeopleMovieCreditsList>, t: Throwable) {
                    Log.d("peopleViewModel", t.message.toString())
                }
            })
    }

    fun observePeopleDetailLiveData(): LiveData<People> {
        return peopleDetailLiveData
    }

    fun observePeopleImageLiveData(): LiveData<PeopleImageList> {
        return peopleImageLiveData
    }

    fun observePeopleMovieCreditsLiveData(): LiveData<PeopleMovieCreditsList>{
        return peopleMovieCreditsLiveData
    }

}