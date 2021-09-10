package com.example.syntheticalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.viewmodel.TablayoutActivity
import com.nguyenhoanglam.imagepicker.helper.ToastHelper
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        home_spbf.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        home_dsp.setOnClickListener {
            startActivity(Intent(this,camActivity::class.java))
        }
        home_tab.setOnClickListener {
            startActivity(Intent(this,TablayoutActivity::class.java))
        }
        main_att.setOnClickListener {
            ToastHelper.show(this,"点击---")
        }
    }
}