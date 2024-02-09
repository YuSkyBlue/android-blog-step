package com.bluesky.android_step

import com.bluesky.android_step.SocketStorage.cList
import com.bluesky.android_step.SocketStorage.mHandler
import com.bluesky.android_step.SocketStorage.writeSocket
import kotlin.properties.Delegates

//메시지   전송
class SendMessage : Thread() {
    private var state by Delegates.notNull<Int>()
    private lateinit var msg: String
    private lateinit var cname: String

    fun setMsg(s: Int, n: String, m: String) {
        state = s
        msg = m
        cname = n
    }

    override fun run() {

        if (cList.size > 0) {            //메시지를   전송하는   주체가   서버일   경우
            val cIter = cList.iterator()
            while (cIter.hasNext()) {
                val client = cIter.next()
                if (!client.isClosed()) client.sendMessage(state, cname, msg)
                else cIter.remove()
                mHandler.obtainMessage(9, "$cname>   $msg").apply {
                    sendToTarget()
                }
            }
        } else {
            try {
                writeSocket.writeInt(state)            //메시지   전송   명령   전송
                writeSocket.writeUTF(cname)            //클라이언트   이름
                writeSocket.writeUTF(msg)            //메시지   내용
            } catch (e: Exception) {
                e.printStackTrace()
                mHandler.obtainMessage(12).apply {
                    sendToTarget()
                }
            }
        }
    }
}