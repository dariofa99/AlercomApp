package com.alercom.app.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alercom.app.ui.data_protection.DataProtectionContentFragment


class ViewPageAdapter(private  val fr:FragmentActivity) : FragmentStateAdapter(fr) {

    companion object {
        private const val ARG_OBJECT = "object"
    }

    override fun getItemCount(): Int {
       return 2
    }

    override fun createFragment(position: Int): Fragment {

        val fragment = DataProtectionContentFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_OBJECT,position + 1)
        }

        return  fragment
    }
}