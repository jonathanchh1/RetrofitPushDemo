package com.emi.retrofitpushdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class MainActivity : AppCompatActivity() {

    private lateinit var network : NetworkProvider
    private var disposable = Disposables.empty()
    private var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        network = NetworkProvider(this)
        setContentView(R.layout.activity_main)
        profileData()
        fetchData()
    }


    private fun profileData(){
        val profile = Profile()
            profile.id = 0
            profile.city = "New York"
            profile.age = 22
            profile.email = "www.example.com"
            profile.name = "Ejimofor"
        saveData(profile)
    }


    private fun saveData(profile : Profile){
       disposable = network.networkProvider()!!.saveForm(id = profile.id,
            name = profile.name, email =  profile.email, city = profile.city,
            age =  profile.age)
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .cacheWithInitialCapacity(100)
           .cache()
           .doOnComplete{
              Log.d(MainActivity::class.java.simpleName, "successful")
           }
           .doOnNext {
               Log.d(MainActivity::class.java.simpleName, "$it")
               Toast.makeText(applicationContext, "$it", Toast.LENGTH_LONG).show()
           }
           .doOnError {
               Log.d(MainActivity::class.java.simpleName, "${it.printStackTrace()}")
           }
           .subscribe()
        compositeDisposable.add(disposable)

    }
    fun fetchData(){
        disposable = network.networkProvider()!!.getUserId(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                Log.d(MainActivity::class.java.simpleName, "got id")
            }
            .doOnNext {
                Log.d(MainActivity::class.java.simpleName, "$it getting profile by id")
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }


    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
        disposable.dispose()
    }
}
