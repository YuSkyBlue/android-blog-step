package com.bluesky.android_step

import android.net.ConnectivityManager
import android.os.Handler
import android.os.Looper
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.ServerSocket
import java.net.Socket

object SocketStorage {
    var socket = Socket()
    var server = ServerSocket()
    lateinit var writeSocket: DataOutputStream
    lateinit var readSocket: DataInputStream
    lateinit var cManager: ConnectivityManager
    lateinit var myIp: String

    var ip = "192.168.0.1"
    var port = 2222

    //var   mHandler   =   Handler()                  ->   API30부터   Deprecated됨.   Looper를   직접   명시해야함
    var mHandler = Handler(Looper.getMainLooper())
    var serverClosed = true

    var cList = mutableListOf<Client>()
}