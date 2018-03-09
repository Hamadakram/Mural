package com.irozon.muralsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.irozon.mural.extension.placeholder
import com.irozon.mural.extension.source
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivImage.placeholder = resources.getDrawable(R.drawable.placeholder)
        ivImage.source = "http://blog.europeana.eu/wp-content/uploads/2017/01/2063604_DEN_280_010.jpeg"

    }
}