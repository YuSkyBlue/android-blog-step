package com.bluesky.android_step

//서버에 접속한 클라이언트에게 메시지 전파
class Broadcast(
        private val cList: MutableList<Client>,
        private val state: Int,
        private val cname: String,
        private val msg: String
    ) : Thread() {

        override fun run() {
            if (cList.size > 0) {
                val cIter = cList.iterator()
                while (cIter.hasNext()) {
                    val client = cIter.next()
                    if (!client.isClosed()) {
                        client.sendMessage(state, cname, msg)
                    } else cIter.remove()
                }
            }
        }
    }