package com.example.noteApp.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.noteApp.NoteApp
import com.example.noteApp.models.Note
import com.example.noteApp.repository.database.NoteDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
class MainViewModelTest:TestCase() {
    private lateinit var viewModel: MainViewModel
    private lateinit var db:NoteDatabase
    @Before
    public override fun setUp() {

        val app=NoteApp()
        db= Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NoteDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        app.database=db
        viewModel= MainViewModel(app)

    }

    @Test
    fun test() {
        val note = Note("Title", "test", byteArrayOf(), "12.05.1222")


        viewModel.insert(note)
       viewModel.getNotes()
      val result=  viewModel.noteList.await().find {
           it==note
       }
        assert(result!=null)
    }

  private fun <T> LiveData<T>.await():T{
        var data: T?=null
        val latch=CountDownLatch(1)
        val observer=object:Observer<T>{

            override fun onChanged(t: T) {
               data=t
                this@await.removeObserver(this)
                latch.countDown()
            }

        }

        Observable.just(observer)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe {
                this.observeForever(it)
            }

        try {
            if (!latch.await(2,TimeUnit.SECONDS)) {
                throw TimeoutException("Too long")
            }
        } finally {
            Observable.just(observer)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    this.removeObserver(it)
                }

        }
        return data as T
    }
}