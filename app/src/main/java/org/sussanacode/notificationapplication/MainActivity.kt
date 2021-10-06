package org.sussanacode.notificationapplication


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.sussanacode.notificationapplication.databinding.ActivityMainBinding
import org.sussanacode.notificationapplication.mybank.MyBankActivity
import java.net.URL
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpEvents()
    }

    private fun setUpEvents() {
        binding.btncreatesimplenotification.setOnClickListener {
            createSimpleNotification()
        }


        binding.btnmoreinfo.setOnClickListener {
            createMoreInfoNotification()
        }

        binding.btngetimage.setOnClickListener {
            startActivity(Intent(baseContext, ImageViewActivity::class.java))
        }

        binding.btnbankapp.setOnClickListener {
            startActivity(Intent(baseContext, MyBankActivity::class.java))
        }
    }


    private fun createMoreInfoNotification() {

        val url = "https://psmobitech.com/clinic/dp.png"

        MainScope().launch(Dispatchers.IO) {
            val inputstream = URL(url).openStream()
            val bitmap = BitmapFactory.decodeStream(inputstream)

            if (bitmap == null) {
                Toast.makeText(baseContext, "Failed to show image", Toast.LENGTH_LONG).show()
                return@launch
            }


            val id = Random.nextInt(1, 10000)
            val pIntent = PendingIntent.getActivity(baseContext, id,
                Intent(baseContext, InformationActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            val notification = NotificationCompat.Builder(baseContext, "CheapTicket")
                .setContentTitle("Get the most affordable tickets")
                .setContentText("Click here to get latest ticket offer")
                .setSmallIcon(R.drawable.flight_attendant)
                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                .setAutoCancel(true)
                .setContentIntent(pIntent)
                .build()

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notifChannel = NotificationChannel(
                    "CheapTicket",
                    "More Info",
                    NotificationManager.IMPORTANCE_HIGH
                )
                notificationManager.createNotificationChannel(notifChannel)
            }

            notificationManager.notify(id, notification)
        }
    }

    private fun createSimpleNotification() {
        val notificationID = Random.nextInt(1, 10000)

        val infoIntent = Intent(baseContext, InformationActivity::class.java)
        val notificationAction = PendingIntent.getActivity(baseContext, notificationID, infoIntent, PendingIntent.FLAG_UPDATE_CURRENT)




        val notification = NotificationCompat.Builder(this, "SimpleNotification")
            .setContentTitle("Hey there! this is the best Travel offer you can ever get")
            //.setContentText("Get 50% off on your first ticket booking on us")
            .setStyle(NotificationCompat.BigTextStyle().bigText("Get 50% off on your first ticket booking on us. Find out more on our page"))
            .setSmallIcon(R.drawable.img).setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_travel))
            .setAutoCancel(true)
            .setContentIntent(notificationAction)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel("SimpleNotification", "Simple Text Alert", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)


//            val channelticketOffers = NotificationChannel("Ticket Offer", "Cheap Flight Ticket", NotificationManager.IMPORTANCE_HIGH)
//            notificationManager.createNotificationChannel(channelticketOffers)
//
//
//            val channelflightDetails = NotificationChannel("Flight Details", "Flight Alert", NotificationManager.IMPORTANCE_HIGH)
//            notificationManager.createNotificationChannel(channelflightDetails)

        }

        notificationManager.notify(notificationID, notification)



    }
}