package com.example.capstonemovie

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.capstoneMovie.R
import com.example.capstoneMovie.databinding.ActivityMainBinding
import com.example.capstonemovie.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.apply {
            title = getString(com.example.core.R.string.app_name)
        }
        setupViewPager()
        setupNavigationBar()

    }

    private fun setupNavigationBar() {
        binding.navigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            var itemSelected = 0
            when (item.itemId) {
                R.id.menu_home -> {
                    itemSelected = 0
                    supportActionBar!!.show()
                    supportActionBar!!.title = "Now Playing"
                }

                R.id.search -> {
                    itemSelected = 1
                    supportActionBar!!.hide()
                    supportActionBar!!.title = "Search Movie"
                }

                R.id.favourite -> {
                    itemSelected = 2
                    supportActionBar!!.show()
                    supportActionBar!!.title = "Favorite Movie"
                }
            }
            binding.viewPager.setCurrentItem(itemSelected)
            true
        }
    }

    private fun setupViewPager() {
        val menuSize: Int = binding.navigationView.menu.size()

        val tabAdapter = TabAdapter(supportFragmentManager, menuSize)

        binding.viewPager.setAdapter(tabAdapter)
        binding.viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
//                selectedTab = position
//                fragmentCommunicators.get(position).fragmentOnStart()
//                fragmentCommunicators.get(tabPosition).fragmentOnStop()
                binding.navigationView.menu.getItem(position).setChecked(true)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }
}