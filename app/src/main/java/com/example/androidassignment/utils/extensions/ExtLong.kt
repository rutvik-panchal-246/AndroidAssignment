package com.example.androidassignment.utils.extensions

fun Long.formatToTwoDigit(): String{
    return String.format("%02d",this)
}