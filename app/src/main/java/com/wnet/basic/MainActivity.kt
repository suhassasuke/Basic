package com.wnet.basic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.wnet.basic.adapter.ViewPagerAdapter
import com.wnet.basic.data.Data
import com.wnet.basic.utils.HorizontalMarginItemDecoration
import com.wnet.basic.utils.SimplePageTransformer
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var viewPagerAdapter: ViewPagerAdapter

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewPagerAdapter = ViewPagerAdapter(ArrayList(), this)

        initializeViewPager()

        viewModel.loadData()

        viewModel.upgradeResult().observe(this, Observer {
            updateViewPager(it)
        })

        restartViewPager.setOnClickListener {
            viewModel.loadData()
            viewpager.setCurrentItem(0, true)
        }
    }

    fun updateViewPager(list: List<Data>) {
        viewpager.offscreenPageLimit = list.size
        viewPagerAdapter.addupdates(list)
        viewPagerAdapter.notifyDataSetChanged()
        //show dot indicator only when the (list.size > 2)
        if (list.size > 1) {
            dots_indicator.visibility = View.VISIBLE
        } else {
            dots_indicator.visibility = View.INVISIBLE
        }
    }

    private fun initializeViewPager() {
        viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewpager.adapter = viewPagerAdapter
        dots_indicator.setViewPager2(viewpager)

        viewpager.setPageTransformer(SimplePageTransformer())

        val itemDecoration = HorizontalMarginItemDecoration(
            baseContext,
            R.dimen.viewpager_current_item_horizontal_margin
        )
        viewpager.addItemDecoration(itemDecoration)
    }

}