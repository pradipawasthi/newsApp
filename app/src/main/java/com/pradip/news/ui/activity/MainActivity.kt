package com.pradip.news.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.transaction
import com.pradip.news.R
import com.pradip.news.ui.fragment.ContentListFragment

/**
 * @author pradip; dated 31/03/19.
 *
 * Activity to host fragments.
 */
 class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        launchContentListFragment()
    }

    private fun launchContentListFragment() {
        supportFragmentManager
            .transaction {
                replace(R.id.fragmentContainer, ContentListFragment())
            }
    }
}
