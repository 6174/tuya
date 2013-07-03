package com.cxj

import java.io.InputStream
import java.util.ArrayList

import android.app.ListActivity
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle

import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.provider.ContactsContract.CommonDataKinds.Photo
import android.text.TextUtils
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.AdapterView.OnItemClickListener


import android.util.Log
/**
 *@Desc get contacts list
 */
class ContactsManager(val context: Context) {
    private val PHONES_PROJECTION = Array("display_name", "data1", "photo_id", "contact_id")
   
    private val PHONES_DISPLAY_NAME_INDEX = 0
    private val PHONES_NUMBER_INDEX = 1
    private val PHONES_PHOTO_ID_INDEX = 2
    private val PHONES_CONTACT_ID_INDEX = 3
    val mContactsName = new ArrayList[String]()
    val mContactsNumber = new ArrayList[String]()
    val mContactsPhonto = new ArrayList[Bitmap]()
    setPhoneContacts(context)
    private def setPhoneContacts(context: Context) = {
        Log.i("chxjia", "setPhoneContacts")
    	val resolver = context.getContentResolver()
        //something wrong here , Can't get the cursor
        try {
            // Log.i("chxjia", "in try")
            val phoneCursor =  resolver.query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null)
            // Log.i("chxjia", "pass")
            if (phoneCursor != null) {
                // Log.i("chxjia", "get phoneCursor")
                while (phoneCursor.moveToNext()) {
                    Log.i("chxjia", "move TO next cursor")
                    val phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX)
                    Log.i("chxjia", "Phone:" + phoneNumber + "; " + phoneCursor.toString)

                    if (!TextUtils.isEmpty(phoneNumber)) {
                        val contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX)
                        val contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX)
                        val photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX)
                        var contactPhoto:Bitmap = null
                        // //photo > 0 means there is an user photo
                        if(photoid > 0) {
                            val url = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid.toLong);
                            val input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, url);
                            contactPhoto = BitmapFactory.decodeStream(input);
                        } else {
                            contactPhoto = BitmapFactory.decodeResource(context.getResources(), R.drawable.contact_photo)
                        }
                        mContactsName.add(contactName)
                        mContactsNumber.add(phoneNumber)
                        mContactsPhonto.add(contactPhoto)
                    }
                }
                phoneCursor.close()
            }
        } catch{
            case ex:Exception => throw(ex)
            case _ => Log.i("chxjia", "someting wrong when get the contacts")
        }
    }
    // testIt()
    // private def testIt() = {
    //     for(i <- 0 until mContactsName.size()) {
    //         Log.i("chxjia", mContactsName.get(i))
    //     }
    // }
}