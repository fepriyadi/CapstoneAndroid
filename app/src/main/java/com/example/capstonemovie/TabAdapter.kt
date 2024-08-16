package com.example.capstonemovie

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.capstonemovie.home.HomeFragment
import com.example.capstonemovie.search.SearchFragment

class TabAdapter(fragmentManager: FragmentManager?, private val pageCount: Int) :
    FragmentStatePagerAdapter(fragmentManager!!) {

    override fun getItem(position: Int): Fragment {
        val fragmentFav = Class.forName("com.example.favourite.FavouriteFragment").newInstance()

//        if (fragment is Fragment) {
//            fragmentManager
//                ?.beginTransaction()
//                ?.replace(R.id.fragmentHolder, fragment, "dynamic_fragment")
//                ?.addToBackStack("dynamic_fragment")
//                ?.commit()
//        }
        when (position) {
            0 -> return HomeFragment()
            1 -> return SearchFragment()
            2 -> return fragmentFav as Fragment
        }
        return HomeFragment()
    }

    override fun getCount(): Int {
        return pageCount
    }


}