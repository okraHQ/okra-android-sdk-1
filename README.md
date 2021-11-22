# okra-android-sdk

## Setup
- As soon as the aar file is available on gradle. You will add this line to your gradle file.

  ```kotlin
     implementation 'com.okra.android:1.0.0'
  ```
  
## How to use
- Navigate to the OkraMainActivity using this line of code. First you have to initialize an `ActivityResultLauncher<Intent>`

  ```kotlin
    val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ActivityResultCallback {
        if (it.resultCode == RESULT_OK && it.data != null){
            val okraResult  = it.data!!.getStringExtra(OkraMainActivity.OKRA_RESULT)
            //Successful operation, get the data and do whatever you want with it.
            Toast.makeText(this, okraResult, Toast.LENGTH_SHORT).show()
        }
        else{
            val okraResult  = it.data!!.getStringExtra(OkraMainActivity.OKRA_RESULT)
            Toast.makeText(this, okraResult, Toast.LENGTH_SHORT).show()
        }
    })
  
  ```
  
  - Depending on the type of configuration you prefer. Either with Okra build option or Short url option
  
  ```kotlin
  //With option build
  val okraOptions = OkraOptions("key","token", listOf("auth","balance","identity","income", "transactions"),"dev","Kaysho")
  val intent = OkraMainActivity.newIntent(this, okraOptions)
  activityResultLauncher.launch(intent)
  ```
  ## OR
  
  ```kotlin
  //With short-url
  val okraOptionsShortUrl = OkraOptions("uOxqP-u9n")
  val intent = OkraMainActivity.newIntent(this, okraOptionsShortUrl)
  activityResultLauncher.launch(intent)
  
  ```
