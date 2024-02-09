package com.bluesky.android_step

import com.bluesky.android_step.SocketStorage.ip
import com.bluesky.android_step.SocketStorage.mHandler
import com.bluesky.android_step.SocketStorage.myIp
import com.bluesky.android_step.SocketStorage.port
import com.bluesky.android_step.SocketStorage.readSocket
import com.bluesky.android_step.SocketStorage.socket
import com.bluesky.android_step.SocketStorage.writeSocket
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class Connect : Thread() {
    //클라이언트-서버   접속   시도
    override fun run() = try {
        socket = Socket(ip, port)
        writeSocket = DataOutputStream(socket.getOutputStream())
        readSocket = DataInputStream(socket.getInputStream())
        val b = readSocket.readInt()
        if (b == 1) {            //서버로부터   접속이   확인되었을   때
            mHandler.obtainMessage(11).apply {
                sendToTarget()
            }
            ClientSocket(myIp).start()
        } else {            //서버   접속에   성공하였으나   서버가   응답을   하지   않았을   때
            mHandler.obtainMessage(14).apply {
                sendToTarget()
            }
            socket.close()
        }
    } catch (e: Exception) {            //연결   실패
        val state = 1
        mHandler.obtainMessage(state).apply {
            sendToTarget()
        }
        socket.close()
    }
}