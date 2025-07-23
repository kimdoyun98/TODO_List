package com.example.todo_list.ui.home

import androidx.databinding.BindingAdapter
import com.google.android.material.tabs.TabLayout

object TabBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:tab_selected")
    fun tabSelectedListener(tabLayout: TabLayout, changedTab: (StatisticsTab) -> Unit){
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                changedTab(
                    when(tabLayout.selectedTabPosition){
                        StatisticsTab.WEEK.position -> StatisticsTab.WEEK
                        StatisticsTab.MONTH.position -> StatisticsTab.MONTH
                        StatisticsTab.HALF_OF_YEAR.position -> StatisticsTab.HALF_OF_YEAR
                        else -> StatisticsTab.YEAR
                    }
                )
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }
}
