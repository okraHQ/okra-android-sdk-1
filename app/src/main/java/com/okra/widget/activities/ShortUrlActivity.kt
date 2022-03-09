package com.okra.widget.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.okra.android.activities.OkraMainActivity
import com.okra.android.models.OkraOptions
import com.okra.widget.R

class ShortUrlActivity : AppCompatActivity() {
    val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK && it.data != null) {
            val okraResult = it.data!!.getStringExtra(OkraMainActivity.OKRA_RESULT)
            //Successful operation, get the data and do whatever you want with it.
            Toast.makeText(this, okraResult, Toast.LENGTH_SHORT).show()
        } else {
            val okraResult = it.data!!.getStringExtra(OkraMainActivity.OKRA_RESULT)
            Toast.makeText(this, okraResult, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_short_url)


        val shorturl = findViewById<EditText>(R.id.short_url)
        val optionBtn = findViewById<Button>(R.id.btn)
        optionBtn.setOnClickListener {
            if (!shorturl.text.isEmpty()){
                val okraOptions = OkraOptions.Builder()
                    .shortUrl(shorturl.text.toString())
                    .build()

                val intent = OkraMainActivity.newIntent(this, okraOptions)
                activityResultLauncher.launch(intent)
            }
        }

    }

}