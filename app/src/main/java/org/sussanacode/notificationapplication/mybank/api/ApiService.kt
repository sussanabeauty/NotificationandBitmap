package org.sussanacode.notificationapplication.mybank.api


import org.sussanacode.notificationapplication.mybank.entity.BankBalanceResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {


    @GET("get_balance_hdfcbank.php")
    suspend fun getHdfcBalance(@Query("acno") acno: Int):Response<BankBalanceResponse>

    @GET("get_balance_axisbank.php")
    suspend fun getAxisBalance(@Query("acno") acno: Int):Response<BankBalanceResponse>

}