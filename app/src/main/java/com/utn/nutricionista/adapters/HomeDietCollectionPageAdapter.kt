package com.utn.nutricionista.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.utn.nutricionista.fragments.HomeDietFragment

private const val ARG_OBJECT = "object"

class HomeDietCollectionPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int  = 100

    override fun getItem(i: Int): Fragment {
        val fragment = HomeDietFragment()
        fragment.arguments = Bundle().apply {
            putString(ARG_OBJECT, (i + 1).toString())
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return "OBJECT ${(position + 1)}"
    }
}