package com.dominic.readingcontact

import android.Manifest.permission.SEND_SMS
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.text.TextUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dominic.readingcontact.databinding.ActivityMainBinding
import com.dominic.readingcontact.databinding.ActivitySendMessageBinding
import java.util.jar.Manifest

class Send_Message : AppCompatActivity() {
    lateinit var binding:ActivitySendMessageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val contactMe =Contact.contactMe
        binding.sendMessage.isEnabled = false
        if (checkPermission(SEND_SMS)){
            binding.sendMessage.isEnabled = true
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(SEND_SMS),111)
        }
        binding.sendMessage.setOnClickListener {
            val message = binding.sendMessageText.text.toString()
            val number = binding.sendMessageNumber.text.toString()
            if (!TextUtils.isEmpty(message) && !TextUtils.isEmpty(number)){
                if (checkPermission(android.Manifest.permission.SEND_SMS)){
                    val smsManager = SmsManager.getDefault()
                    smsManager.sendTextMessage(number,null,message,null,null)
                }else{
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.sendMessageNumber.text = contactMe?.number
        binding.sendMessageName.text = contactMe?.name

    }
    private fun checkPermission(permission:String):Boolean{
        val checkPm:Int = ContextCompat.checkSelfPermission(this,permission)
        return (checkPm == PackageManager.PERMISSION_GRANTED)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){

            111 -> {
                if (grantResults.size > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    binding.sendMessage.isEnabled = true
                }
                return;
            }
        }

    }
}