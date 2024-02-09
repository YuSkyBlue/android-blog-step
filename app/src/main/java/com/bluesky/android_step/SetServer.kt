package com.bluesky.android_step


import com.bluesky.android_step.SocketStorage.cList
import com.bluesky.android_step.SocketStorage.mHandler
import com.bluesky.android_step.SocketStorage.port
import com.bluesky.android_step.SocketStorage.server
import com.bluesky.android_step.SocketStorage.serverClosed
import com.bluesky.android_step.SocketStorage.socket
import java.net.BindException
import java.net.ServerSocket
import java.net.SocketException

//서버   통신   개시
    class SetServer : Thread() {

        override fun run() {
            try {
                server = ServerSocket(port)            //포트   개방
                mHandler.obtainMessage(2, "").apply {
                    sendToTarget()
                }
                mHandler.obtainMessage(9, "서버가   열렸습니다.").apply {
                    sendToTarget()
                }

                while (true) {
                    socket = server.accept()            //클라이언트가   접속할   때   까지   대기
                    val client = Client(socket)            //접속한   Client의   socket을   저장
                    cList.add(client)            //접속   client   socket   리스트   추가
                    client.start()            //접속한   클라이언트   전용   socket   thread   실행
                }

            } catch (e: BindException) {            //이미   개방된   포트를   개방하려   시도하였을때
                mHandler.obtainMessage(5).apply {
                    sendToTarget()
                }
            } catch (e: SocketException) {            //소켓이   닫혔을   때
                mHandler.obtainMessage(7).apply {
                    sendToTarget()
                }
            } catch (e: Exception) {
                if (!serverClosed) {
                    mHandler.obtainMessage(6).apply {
                        sendToTarget()
                    }
                } else {
                    serverClosed = false
                }
            }
        }
    }