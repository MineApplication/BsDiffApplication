package com.body.bsdiffapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.body.bsdiffapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
        binding.sampleText.text = BsPatchUtils.stringFromJNI()
    }

    /**
     * A native method that is implemented by the 'bsdiffapplication' native library,
     * which is packaged with this application.
     */
//    external fun stringFromJNI(): String
//
//    //组装差分包
//    external fun patch(oldApk: String?, newApk: String?, patchFile: String?): Int
//
//    //获取差分包
//    external fun diff(oldApk: String?, newApk: String?, dissFile: String?): Int
//
//    companion object {
//        // Used to load the 'bsdiffapplication' library on application startup.
//        init {
//            System.loadLibrary("bsdiffapplication")
//        }
//    }
}