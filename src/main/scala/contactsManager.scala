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
// import android.provider.ContactsContract.ContactsColumns._
// import android.provider.ContactsContract.RawContactsCloumns._
// import android.provider.ContactsContract.RawContactsCloumns._
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

/**
 *@Desc get contacts list
 */
class contactsManager(val context: Context) {
    private val PHONES_PROJECTION = Array(
    	"display_name", 
    	"data1", 
        "photo_id",
        "contact_id")
   
    private val PHONES_DISPLAY_NAME_INDEX = 0
    private val PHONES_NUMBER_INDEX = 1
    private val PHONES_PHOTO_ID_INDEX = 2
    private val PHONES_CONTACT_ID_INDEX = 3
    val mContactsName = new ArrayList[String]()
    val mContactsNumber = new ArrayList[String]()
    val mContactsPhonto = new ArrayList[Bitmap]()
    // setPhoneContacts(context)
    // private def setPhoneContacts(context: Context) = {
    // 	val resolver = context.getContentResolver()
    // 	val phoneCursor =  resolver.query(Phone.CONTENT_URI,PHONES_PROJECTION, null, null, null)
    // 	if (phoneCursor != null) {
    // 		while (phoneCursor.moveToNext()) {
    // 			val phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX)
    // 			if (!TextUtils.isEmpty(phoneNumber)) {
    // 				val contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX)
    // 				val contactid = phoneCursor.getString(PHONES_CONTACT_ID_INDEX)
    // 				val photoid = phoneCursor.getString(PHONES_PHOTO_ID_INDEX)
    // 				var contactPhoto:Bitmap = _
    // 				//photo > 0 means there is an user photo
    // 				if(photoid > 0) {
    // 					val url = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);
		  //  				val input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, url);
		  //   			contactPhoto = BitmapFactory.decodeStream(input);
    // 				} else {
    // 					contactPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.contact_photo)
    // 				}
			 //    	mContactsName.add(contactName)
				// 	mContactsNumber.add(phoneNumber)
				// 	mContactsPhonto.add(contactPhoto)
    // 			}
    // 		}
    // 		phoneCursor.close()
    // 	}
    // }
}