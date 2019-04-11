package com.vimal.infiniteimagelist.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.vimal.infiniteimagelist.Injection
import com.vimal.infiniteimagelist.R
import kotlinx.android.synthetic.main.activity_main.*

class ImagesListActivity : AppCompatActivity() {

    private lateinit var viewModel: ImagesListViewModel
    private val adapter = ImageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get the viewmodel
        viewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(this))
            .get(ImagesListViewModel::class.java)

        /*// add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        list.addItemDecoration(decoration)*/
        viewModel.fetchImages()
        initAdapter()
    }

    private fun initAdapter(){
        list.adapter = adapter
        viewModel.photos.observe(this, Observer {
            Log.d("Activity", "list: ${it?.size}")
            showEmptyList(it?.size == 0)
            adapter.submitList(it)
        })
        viewModel.networkErrors.observe(this, Observer<String> {
            Toast.makeText(this, "\uD83D\uDE28 Wooops $it", Toast.LENGTH_LONG).show()
        })
    }

    private fun showEmptyList(show : Boolean){
        if(show){
            emptyList.visibility = View.VISIBLE
            list.visibility = View.GONE
        } else {
            emptyList.visibility = View.GONE
            list.visibility = View.VISIBLE
        }
    }
}
