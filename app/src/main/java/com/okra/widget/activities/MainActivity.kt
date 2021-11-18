package com.okra.widget.activities

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
            Toast.makeText(this, okraResult, Toast.LENGTH_SHORT).show()
        }
        else{
            val okraResult  = it.data!!.getStringExtra(OkraMainActivity.OKRA_RESULT)
            Toast.makeText(this, okraResult, Toast.LENGTH_SHORT).show()
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val b = findViewById<Button>(R.id.btn)
        b.setOnClickListener {
            //With option build
            val dummyOkraObject = DummyOkraObject("key","token", listOf("auth","balance","identity","income", "transactions"),"dev","Kaysho","android" )
            val okraObject = object {
                val key ="key"
                val token = "token"
                val products= listOf("auth","balance","identity","income", "transactions")
                val env = "dev"
                val name = "Kay"
                val source = "android"
            }

            //With short-url
            val shortUrlObject = DummyShortUrlObject("uOxqP-u9n")

            val intent = OkraMainActivity.newIntent(this, dummyOkraObject)
            activityResultLauncher.launch(intent)
        }
    }
}