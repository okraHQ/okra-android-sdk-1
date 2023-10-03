package com.okra.widget.activities

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.okra.android.activities.OkraMainActivity
import com.okra.android.models.Charge
import com.okra.android.models.OkraOptions
import com.okra.widget.R

class OptionsActivity : AppCompatActivity() {
    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK && it.data != null) {
            val okraResult = it.data!!.getStringExtra(OkraMainActivity.OKRA_RESULT)
            //Successful operation, get the data and do whatever you want with it.
            AlertDialog.Builder(this)
                .setTitle("Successful")
                .setMessage(okraResult)
                .setNegativeButton("No", null)
                .setPositiveButton("Yes") { _, _ ->
                    //onClose("closed")
                }
                .create().show()
        } else {
            val okraResult = it.data!!.getStringExtra(OkraMainActivity.OKRA_RESULT)
            AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(okraResult)
                .setNegativeButton("No", null)
                .setPositiveButton("Yes") { _, _ ->
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
            if (key.text.isNotEmpty() && token.text.isNotEmpty() && env.text.isNotEmpty() && name.text.isNotEmpty()) {
                val okraOptions = OkraOptions.OptionsBuilder(
                    key.text.toString(),
                    token.text.toString(),
                    env.text.toString(),
                    name.text.toString(),
                    arrayListOf("auth", "balance", "identity", "income", "transactions")
                )
                    //Add additional properties here.
                    .appId("")
                    .color("")
                    .connectMessage("")
                    .currency("")
                    .isCorporate(false)
                    .charge(Charge("hello", "yes", "Helpppppp", "NGN"))
                    .build()
                val intent = OkraMainActivity.newIntent(this, okraOptions)
                activityResultLauncher.launch(intent)
            }
        }
    }
}