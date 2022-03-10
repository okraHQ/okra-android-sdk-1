package com.okra.widget.activities

import android.app.AlertDialog
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
            AlertDialog.Builder(this)
                .setTitle("Successful")
                .setMessage(okraResult)
                .setNegativeButton("No", null)
                .setPositiveButton("Yes") { dialogInterface, which ->
                    //onClose("closed")
                }
                .create().show()
        } else {
            val okraResult = it.data!!.getStringExtra(OkraMainActivity.OKRA_RESULT)
            AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(okraResult)
                .setNegativeButton("No", null)
                .setPositiveButton("Yes") { dialogInterface, which ->
                    //onClose("closed")
                }
                .create().show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_short_url)


        val shorturl = findViewById<EditText>(R.id.short_url)
        val optionBtn = findViewById<Button>(R.id.btn)
        optionBtn.setOnClickListener {
            if (!shorturl.text.isEmpty()){
                val okraOptions = OkraOptions.ShortUrlBuilder(shorturl.text.toString())
                    .build()

                val intent = OkraMainActivity.newIntent(this, okraOptions)
                activityResultLauncher.launch(intent)
            }
        }

    }

}