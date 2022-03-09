package com.okra.widget.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.okra.android.activities.OkraMainActivity
import com.okra.widget.R


class MainActivity : AppCompatActivity() {

    val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ActivityResultCallback {
        if (it.resultCode == RESULT_OK && it.data != null){
            val okraResult  = it.data!!.getStringExtra(OkraMainActivity.OKRA_RESULT)
            //Successful operation, get the data and do whatever you want with it.
            Toast.makeText(this, okraResult, Toast.LENGTH_LONG).show()
        }
        else{
            val okraResult  = it.data!!.getStringExtra(OkraMainActivity.OKRA_RESULT)
            Toast.makeText(this, okraResult, Toast.LENGTH_LONG).show()
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val optionBtn = findViewById<Button>(R.id.btn)
        optionBtn.setOnClickListener {
            val intent = Intent(this, OptionsActivity::class.java)
            startActivity(intent)
        }

        val shortBtn = findViewById<Button>(R.id.btnShort)
        shortBtn.setOnClickListener {
            //With short-url
//            val okraOptionsShortUrl = CustomOkraOptions("uOxqP-u9n")
//            val intent = OkraMainActivity.newIntent(this, okraOptionsShortUrl)
//            activityResultLauncher.launch(intent)

            val intent = Intent(this, ShortUrlActivity::class.java)
            startActivity(intent)
        }
    }
}