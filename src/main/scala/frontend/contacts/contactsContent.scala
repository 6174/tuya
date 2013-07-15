package com.cxj

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.content.Context
import android.net.Uri
import android.graphics.BitmapFactory
import android.view._
import android.widget._
import android.util.Log

import java.io.FileNotFoundException
import com.cxj.util._
/**
 *@DESC Home Content , include home views and 
 *
 */
class ContactsContent(context: Context){
  val ctx = context.asInstanceOf[homeActivity]

  var isShow = false
  lazy val content = ctx.findView(TR.contacts_content_layout)
  lazy val contact_list = ctx.findView(TR.contacts_contact_list)
  
  /**
   *@DESC init events and other components of this activity
   */
  def init() = {
    setContactsListAdapter()
  }
  
  /**
   *@DESC show or hide a content
   */
  def toggle()= {
    if (isShow){
      content.setVisibility(View.GONE)
      isShow = false
    }else {
      content.setVisibility(View.VISIBLE)
      isShow = true
    }

  }

  def show() = {
    if (!isShow){
      content.setVisibility(View.VISIBLE)
      isShow = true
    }
  }

  def hide() = {
    if(isShow){
      content.setVisibility(View.GONE)
      isShow = false
    }
  }
  /**
   *@DESC set contacts list adapter
   */
  def setContactsListAdapter() = {
    val contactsManager = new ContactsManager(ctx)
    val adapter = new ContactsListAdapter(ctx, contactsManager)
    contact_list.setAdapter(adapter)
  }
  
  /**
   *@DESC contact_view_holder
   */
  class ContactViewHolder {
    var title:TextView = _
    var img: ImageView = _
    var info: TextView = _
  }
  /**
   *@DESC contactsListAdapter
   */
  class ContactsListAdapter(
    val context: Context,
    val contactsManager: ContactsManager ) extends BaseAdapter {
    private  val inflater = LayoutInflater.from(context)
    private  val layout = R.layout.contacts_list_item

    override def getCount():Int = contactsManager.mContactsName.size()
    override def getItem(arg0: Int):Object = null
    override def getItemId(arg0: Int):Long = 0

    override def getView(position: Int, convertView: View, parent: ViewGroup ):View = {
      var holder:ContactViewHolder = null
      var view = convertView
      if(view == null) {
        holder = new ContactViewHolder()
        view = inflater.inflate(this.layout, null)
        holder.title = view.findViewById(R.id.title).asInstanceOf[TextView]
        holder.info = view.findViewById(R.id.info).asInstanceOf[TextView]
        view.setTag(holder)
      } else {
        holder = view.getTag().asInstanceOf[ContactViewHolder] 
      }
      holder.title.setText(contactsManager.mContactsName.get(position))
      holder.info.setText(contactsManager.mContactsNumber.get(position))
      view
    } 
  }
}