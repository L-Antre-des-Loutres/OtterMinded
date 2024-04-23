package com.example.otterminded

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.example.otterminded.ui.admin.SectionsPagerAdapter
import com.example.otterminded.databinding.AdminBinding

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: AdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = binding.fab

        fab.setOnClickListener { view ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}