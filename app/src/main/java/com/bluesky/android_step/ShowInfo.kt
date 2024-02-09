package com.bluesky.android_step

import com.bluesky.android_step.SocketStorage.mHandler
import java.net.Inet4Address
import java.net.NetworkInterface

//자신의   IP주소를   표시
    class ShowInfo : Thread() {

        override fun run() {
            var ip = ""
            val en = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf = en.nextElement()
                val enumIpAddr = intf.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
                        ip = inetAddress.hostAddress as String
                    }
                }
            }

            if (ip == "") {
                mHandler.obtainMessage(19).apply {
                    sendToTarget()
                }
            } else {
                val msg = mHandler.obtainMessage()
                msg.what = 20
                msg.obj = ip
                mHandler.sendMessage(msg)
            }
        }
    }