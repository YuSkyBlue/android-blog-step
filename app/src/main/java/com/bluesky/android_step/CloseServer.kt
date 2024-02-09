package com.bluesky.android_step

import com.bluesky.android_step.SocketStorage.mHandler
import com.bluesky.android_step.SocketStorage.server
import com.bluesky.android_step.SocketStorage.serverClosed
import com.bluesky.android_step.SocketStorage.socket
import com.bluesky.android_step.SocketStorage.writeSocket


//서버   소켓   닫기
class CloseServer : Thread() {
    override fun run() {
        try {
            if (!socket.isClosed) {
                writeSocket.write(10)            //클라이언트에게   서버가   종료되었음을   알림
                writeSocket.close()
                socket.close()
            }
            server.close()
            serverClosed = true
            mHandler.obtainMessage(9, "서버가   닫혔습니다.").apply {
                sendToTarget()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            mHandler.obtainMessage(8).apply {
                sendToTarget()
            }
        }
    }
}
