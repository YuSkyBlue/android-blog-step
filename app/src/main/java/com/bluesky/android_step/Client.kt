package com.bluesky.android_step

import com.bluesky.android_step.SocketStorage.cList
import com.bluesky.android_step.SocketStorage.mHandler
import com.bluesky.android_step.SocketStorage.socket
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetSocketAddress
import java.net.Socket

//서버에   접속한   클라이언트   소켓   제어
    class Client(socket: Socket) : Thread() {
        private lateinit var clientName: String
        private lateinit var clientAddr: String
        private lateinit var cWriteSocket: DataOutputStream
        private val cSocket: Socket = socket

        override fun run() {
            cWriteSocket = DataOutputStream(cSocket.getOutputStream())
            val cReadSocket = DataInputStream(cSocket.getInputStream())

            cWriteSocket.writeInt(1)            //클라이언트에게   서버의   소켓   생성을   알림
            val socketAddr = socket.remoteSocketAddress as InetSocketAddress
            clientAddr = socketAddr.address.hostAddress as String

            mHandler.obtainMessage(13, clientAddr).apply {
                sendToTarget()
            }
            mHandler.obtainMessage(9, clientAddr + "님이   입장하였습니다.").apply {
                sendToTarget()
            }
            Broadcast(cList, 3, clientAddr, "입장").start()
            while (true) {
                val ac = cReadSocket.read()
                clientName = cReadSocket.readUTF().toString()
                if (ac == 10) {            //클라이언트로부터   소켓   종료   명령   수신
                    mHandler.obtainMessage(16, clientName).apply {
                        sendToTarget()
                    }
                    mHandler.obtainMessage(9, "$clientName   님이   퇴장하였습니다.").apply {
                        sendToTarget()
                    }
                    Broadcast(cList, 4, clientName, "퇴장").start()
                    break
                } else if (ac == 2) {            //클라이언트로부터   메시지   전송   명령   수신
                    val bac = cReadSocket.readUTF()
                    val input = bac.toString()
                    val recvInput = input.trim()

                    val msg = mHandler.obtainMessage()
                    msg.what = 9
                    msg.obj = "$clientName>   $recvInput"
                    mHandler.sendMessage(msg)            //핸들러에게   클라이언트로   전달받은   메시지   전송

                    Broadcast(cList, 2, clientName, recvInput).start()
                }
            }
            cWriteSocket.close()
            cSocket.close()
        }

        fun isClosed(): Boolean {
            return cSocket.isClosed
        }

        fun sendMessage(state: Int, cname: String, msg: String) {
            try {
                cWriteSocket.writeInt(state)            //메시지   전송   명령   전송
                cWriteSocket.writeUTF(cname)            //클라이언트   이름
                cWriteSocket.writeUTF(msg)            //메시지   내용
            } catch (e: Exception) {
                e.printStackTrace()
                mHandler.obtainMessage(12).apply {
                    sendToTarget()
                }
            }
        }
    }
