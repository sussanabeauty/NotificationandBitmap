package org.sussanacode.notificationapplication

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.sussanacode.notificationapplication.databinding.ActivityImageViewBinding
import java.net.URL
import java.net.URLConnection

class ImageViewActivity : AppCompatActivity() {
    lateinit var  binding: ActivityImageViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnloadimage.setOnClickListener {
            loadImage()
        }
    }


    private fun loadImage() {
        val url = binding.etimageurl.text.toString()

        lifecycleScope.launch(IO){

            val inputstream = URL(url).openStream()
            val bitmap = BitmapFactory.decodeStream(inputstream)

            //switch to the main from IO
            withContext(Dispatchers.Main) {
                binding.ivimageview.setImageBitmap(bitmap)
            }

        }
    }


//    private fun loadImage() {
//        val url = binding.tvimageurl.text.toString()
//
//        lifecycleScope.launch(IO){
//           val urlConnection = URL(url).openConnection() as URLConnection
//            urlConnection.doInput = true
//
//            val inputStream = urlConnection.getInputStream()
//            val bitmap = BitmapFactory.decodeStream(inputStream)
//
//            withContext(Dispatchers.Main) {
//                binding.ivimageview.setImageBitmap(bitmap)
//            }
//
//        }
//    }
}