package com.wnet.basic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.biz2.nowfloats.boost.updates.data.remote.ApiInterface
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wnet.basic.data.CardInfoModel
import com.wnet.basic.data.Data
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class MainActivityViewModel: ViewModel() {

    var updatesResult: MutableLiveData<List<Data>> = MutableLiveData()

    var ApiService = Retrofit.Builder()
        .baseUrl("https://git.io")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ApiInterface::class.java)

    fun upgradeResult(): LiveData<List<Data>> {
        return updatesResult
    }

    fun loadData(){
        CompositeDisposable().add(
            ApiService.GetCardInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val response = it.replace("/","")
                    val obj: CardInfoModel = Gson().fromJson(response, object : TypeToken<CardInfoModel?>() {}.type)
                    updatesResult.postValue(obj.data)
                },{
                    it.printStackTrace()
                })
        )
    }
}