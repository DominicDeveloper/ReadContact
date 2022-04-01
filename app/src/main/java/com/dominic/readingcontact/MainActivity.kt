package com.dominic.readingcontact

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.dominic.readingcontact.Adapters.MyAdapter
import com.dominic.readingcontact.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.item_bootom.view.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var myAdapter: MyAdapter
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val contactList =  ArrayList<ContactMe>()

        val contacts = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null,null)
        while (contacts!!.moveToNext()){
            val name = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val obj = ContactMe()
            obj.name = name
            obj.number = number

            val photo_uri = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
            if (photo_uri != null)
            {
                obj.photo = MediaStore.Images.Media.getBitmap(contentResolver,Uri.parse(photo_uri))

            }
            contactList.add(obj)

        }
        myAdapter =MyAdapter(this,contactList,object :MyAdapter.RvClick{
            override fun onClick(contactMe: ContactMe,cardView: CardView) {
                val bootomdialog = BottomSheetDialog(this@MainActivity)
                val view = LayoutInflater.from(this@MainActivity).inflate(R.layout.item_bootom,null,false)
                bootomdialog.setContentView(view)
                view.itembottom_ct_image.setImageBitmap(contactMe.photo)
                bootomdialog.show()
                view.itembottom_ct_number.text = contactMe.number
                view.itembottom_ct_call.setOnClickListener {
                    if (!TextUtils.isEmpty(contactMe.number)){
                        val dial = "tel:" + contactMe.number
                        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(dial)))
                    }else{
                        Toast.makeText(this@MainActivity, "Number error!", Toast.LENGTH_SHORT).show()
                    }
                }
                view.itembottom_ct_message.setOnClickListener {
                    val intent = Intent(this@MainActivity,Send_Message::class.java)
                    Contact.contactMe = contactMe
                    startActivity(intent)
                }
            }
        })

        binding.contactList.adapter = myAdapter
    }
}