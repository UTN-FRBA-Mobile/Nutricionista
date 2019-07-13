package com.utn.nutricionista.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.utn.nutricionista.R
import com.utn.nutricionista.adapters.HomeDietCollectionPagerAdapter

class HomeDietCollectionFragment : Fragment() {
    // When requested, this adapter returns a HomeDieFragment,
    // representing an object in the collection.
    private lateinit var homeDietCollectionPagerAdapter: HomeDietCollectionPagerAdapter
    private lateinit var viewPager: ViewPager

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_diet_collection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        homeDietCollectionPagerAdapter = HomeDietCollectionPagerAdapter(childFragmentManager)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = homeDietCollectionPagerAdapter
    }
}