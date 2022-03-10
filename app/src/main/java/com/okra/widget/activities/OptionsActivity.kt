package com.okra.widget.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.okra.android.activities.OkraMainActivity
import com.okra.android.models.Charge
import com.okra.android.models.OkraOptions
import com.okra.widget.R

class OptionsActivity : AppCompatActivity() {
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
        }
        else
        {
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
        setContentView(R.layout.activity_options)

        val key = findViewById<EditText>(R.id.key)
        val token = findViewById<EditText>(R.id.token)
        val env = findViewById<EditText>(R.id.env)
        val name = findViewById<EditText>(R.id.name)
        val optionBtn = findViewById<Button>(R.id.btn)
        optionBtn.setOnClickListener {
            if (!key.text.isEmpty() && !token.text.isEmpty() && !env.text.isEmpty() && !name.text.isEmpty()){
                val okraOptions = OkraOptions.OptionsBuilder(key.text.toString(),token.text.toString(),env.text.toString(),name.text.toString(),arrayListOf("auth","balance","identity","income","transactions"))
                    //Add additional properties here.
                    .appId("")
                    .color("")
                    .connectMessage("")
                    .currency("")
                    .isCorporate(true)
                    .charge(Charge("hello", "yes","Helpppppp","NGN"))
                    .build()
                val intent = OkraMainActivity.newIntent(this, okraOptions)
                activityResultLauncher.launch(intent)
            }
        }
    }
}