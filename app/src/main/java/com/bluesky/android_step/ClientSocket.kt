package com.bluesky.android_step


import com.bluesky.android_step.SocketStorage.mHandler
import com.bluesky.android_step.SocketStorage.readSocket
import com.bluesky.android_step.SocketStorage.socket
import java.net.SocketException

//클라이언트-서버   통신   개시
class ClientSocket(private val addr: String) : Thread() {

    override fun run() {
        try {
            while (true) {
                val ac = readSocket.readInt()
                val cname = readSocket.readUTF()

                if (ac == 3) {
                    readSocket.readUTF()
                    if (addr != cname) {
                        mHandler.obtainMessage(9, "$cname   님이   입장하였습니다.").apply {
                            sendToTarget()
                        }
                    } else {
                        mHandler.obtainMessage(9, "채팅방에   입장하였습니다.").apply {
                            sendToTarget()
                        }
                    }
                } else if (ac == 2) {            //서버로부터   메시지   수신   명령을   받았을   때
                    val bac = readSocket.readUTF()
                    val input = bac.toString()
                    val recvInput = input.trim()

                    val clientName = cname.toString().trim()

                    val msg = mHandler.obtainMessage()
                    msg.what = 9
                    msg.obj = "$clientName>   $recvInput"
                    mHandler.sendMessage(msg)
                } else if (ac == 4) {
                    readSocket.readUTF()
                    if (addr != cname) {
                        mHandler.obtainMessage(9, "$cname   님이   퇴장하였습니다.").apply {
                            sendToTarget()
                        }
                    }

                } else if (ac == 10) {            //서버로부터   접속   종료   명령을   받았을   때
                    mHandler.obtainMessage(18).apply {
                        sendToTarget()
                    }
                    mHandler.obtainMessage(9, "서버에서   연결을   끊었습니다.").apply {
                        sendToTarget()
                    }
                    socket.close()
                    break
                }
            }
        } catch (e: SocketException) {            //소켓이   닫혔을   때
            mHandler.obtainMessage(15).apply {
                sendToTarget()
            }
            mHandler.obtainMessage(9, "채팅방을   나갔습니다.").apply {
                sendToTarget()
            }
        }
    }
}