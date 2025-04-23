package com.example.blueymoney

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.blueymoney.fragments.BalanceFragment
import com.example.blueymoney.fragments.GraphicFragment
import com.example.blueymoney.fragments.ListsFragment

class FragmentPageAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
):FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                BalanceFragment()
            }
            1 -> {
                GraphicFragment()
            }
            2 -> {
                ListsFragment()
            }
            else -> {
                BalanceFragment()
            }
        }

    }


}