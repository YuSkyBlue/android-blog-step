package com.bluesky.android_step

import androidx.appcompat.app.AppCompatActivity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import com.bluesky.android_step.SocketStorage.cList
import com.bluesky.android_step.SocketStorage.cManager
import com.bluesky.android_step.SocketStorage.ip
import com.bluesky.android_step.SocketStorage.mHandler
import com.bluesky.android_step.SocketStorage.myIp
import com.bluesky.android_step.SocketStorage.port
import com.bluesky.android_step.SocketStorage.server
import com.bluesky.android_step.SocketStorage.socket
import com.bluesky.android_step.SocketStorage.writeSocket

import com.bluesky.android_step.databinding.ActivityMainBinding // Import the generated binding class

import java.net.*
import kotlin.properties.Delegates

// REFERNCE  https://elecs.tistory.com/411
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding // Declare a binding variable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // Initialize the binding
        setContentView(binding.root)

        cManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        server.close()
        socket.close()

        binding.buttonConnect.setOnClickListener {
            //클라이언트   ->   서버   접속
            val ipInput = binding.etIp.text.toString()
            val portInput = binding.etPort.text.toString()
            myIp = binding.etName.text.toString()

            when{
                ipInput.isEmpty() -> {
                    Toast.makeText(this@MainActivity, "IP 주소를 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
                portInput.isEmpty() -> {
                    Toast.makeText(this@MainActivity, "PORT 번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
                portInput.toIntOrNull() !in 0..65535 -> {
                    Toast.makeText(this@MainActivity, "PORT 번호는 0부터 65535까지만 가능합니다.", Toast.LENGTH_SHORT).show()
                }
                !socket.isClosed -> {
                    Toast.makeText(this@MainActivity, "$ipInput 에 이미 연결되어 있습니다.", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    ip = ipInput
                    port = portInput.toInt()
                    Connect().start()
                }
            }
        }

        binding.buttonDisconnect.setOnClickListener {
            //클라이언트   ->   서버   접속   끊기
            if (!socket.isClosed) {
                Disconnect().start()
            } else {
                Toast.makeText(this@MainActivity, "서버와   연결이   되어있지   않습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonSetserver.setOnClickListener {
            // 서버 포트 열기
            if (binding.etPort.text.isNullOrEmpty()) {
                Toast.makeText(this@MainActivity, "Please enter the PORT number.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val cport = binding.etPort.text.toString().toInt()
            when {
                cport < 0 || cport > 65535 -> {
                    Toast.makeText(this@MainActivity, "PORT numbers can only be from 0 to 65535.", Toast.LENGTH_SHORT).show()
                }
                server.isClosed -> {
                    port = cport
                    SetServer().start()
                }
                else -> {
                    val tstr = "$port Port number is open."
                    Toast.makeText(this@MainActivity, tstr, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.buttonCloseserver.setOnClickListener {            //서버   포트   닫기
            if (!server.isClosed) {
                CloseServer().start()
            } else {
                mHandler.obtainMessage(17).apply {
                    sendToTarget()
                }
            }
        }

        binding.buttonClear.setOnClickListener {            //채팅방   내용   지우기
            binding.textStatus.text = ""
        }

        binding.buttonMsg.setOnClickListener {            //상대에게   메시지   전송
            if (socket.isClosed) {
                Toast.makeText(this@MainActivity, "연결이   되어있지   않습니다.", Toast.LENGTH_SHORT).show()
            } else {
                val mThread = SendMessage()
                mThread.setMsg(2, binding.etName.text.toString(), binding.etMsg.text.toString())
                mThread.start()
            }
        }

        mHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                handleMessageMessage(msg)
            }
        }

        ShowInfo().start()  //자신의   IP주소   확인
    }

    private fun handleMessageMessage(msg: Message) {
        val context = this@MainActivity

        val showToast: (String) -> Unit = { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        when (msg.what) {
            1 -> showToast("The IP address is incorrect or the port on the server is not open.")
            2 -> showToast("Server port $port is ready.")
            3 -> showToast(msg.obj.toString())
            4 -> showToast("The connection has been closed.")
            5 -> showToast("The port is already in use.")
            6 -> showToast("Server preparation failed.")
            7 -> showToast("The server has terminated.")
            8 -> showToast("The server failed to close normally.")
            9 -> {
                val newText = (binding.textStatus.text as String) + (msg.obj as String) + "\n"
                binding.textStatus.text = newText
            }
            11 -> showToast("You have connected to the server.")
            12 -> showToast("Message delivery failed.")
            13 -> showToast("${msg.obj as String} Connected with client.")
            14 -> showToast("No response from the server.")
            15 -> showToast("Terminating the connection to the server.")
            16 -> showToast("${msg.obj as String} Terminating the connection with the client.")
            17 -> showToast("The port is already closed.")
            18 -> showToast("The connection to the server has been lost.")
            19 -> showToast("The Internet is not connected. Please connect and try again.")
            20 -> {
                binding.etName.setText(msg.obj as String)
                myIp = msg.obj as String
            }
        }
    }

}
