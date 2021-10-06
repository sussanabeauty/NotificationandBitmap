package org.sussanacode.notificationapplication.mybank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import org.sussanacode.notificationapplication.databinding.ActivityMyBankBinding
import org.sussanacode.notificationapplication.mybank.api.ApiClient

class MyBankActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyBankBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyBankBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnloadbalance.setOnClickListener{
            loadBalance()
        }
    }



    private fun loadBalance() {
        val hdfcAcno = binding.ethfcBanckacct.text.toString().toInt()
        val axisAcno = binding.etaxisBanckacct.text.toString().toInt()



        lifecycleScope.launch(IO) {


            val hdfcOperation: Deferred<Int> = lifecycleScope.async(IO) {
                val hdfcResponse = ApiClient.apiService.getHdfcBalance(hdfcAcno)
                hdfcResponse.body()?.balance ?: 0
            }


            val axisOperation: Deferred<Int> = lifecycleScope.async(IO) {

                val axisResponse = ApiClient.apiService.getAxisBalance(axisAcno)
                axisResponse.body()?.balance ?: 0
            }

            //calculate and display total balance only both the coroutines has finish =ed task and generated result

            val axisBal = axisOperation.await() // await() will block the current thread, so it not good idea to implement awiat() in withContext(Main)
            val hdfcBal = hdfcOperation.await()

            val totalBalance = axisBal + hdfcBal

            withContext(Main){
                binding.tvtotalbalance.text = "Total Balance of $axisBal + $hdfcBal  is $totalBalance"
            }

        }

    }

//    private fun loadBalance() {
//        val hdfcAcno = binding.ethfcBanckacct.text.toString().toInt()
//        val axisAcno = binding.etaxisBanckacct.text.toString().toInt()
//
//
//
//
////        lifecycleScope.launch(IO){
////            val hdfcResponse = ApiClient.apiService.getHdfcBalance(hdfcAcno)
////            val axisResponse = ApiClient.apiService.getAxisBalance(axisAcno)
////
////
////            withContext(Dispatchers.Main){
////                val hdfcBalance = hdfcResponse.body()?.balance?:0
////                val axisBalance = axisResponse.body()?.balance?:0
////
////                val totBalance = hdfcBalance + axisBalance
////
////                binding.tvtotalbalance.text = "Total balance is $totBalance"
////
////
////            }
////        }
//
//    }
}