package com.bluesky.android_step

import com.bluesky.android_step.SocketStorage.myIp
import com.bluesky.android_step.SocketStorage.socket
import com.bluesky.android_step.SocketStorage.writeSocket


class Disconnect : Thread() {
    //클라이언트   접속   종료

    override fun run() {
            try {
                writeSocket.write(10)            //서버에게   접속   종료   명령   전송
                writeSocket.writeUTF(myIp)      //종료   요청   클라이언트   주소
                socket.close()
            } catch (e: Exception) {

            }
        }
    }