package org.sussanacode.notificationapplication.mybank.entity

import com.google.gson.annotations.SerializedName

data class BankBalanceResponse(
    @SerializedName("status")
    val status : Int,

    @SerializedName("message")
    val message : String,

    @SerializedName("balance")
    val balance : Int
)
