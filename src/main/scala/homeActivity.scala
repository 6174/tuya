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

// import java.util.ArrayList
// import java.util.HashMap
// import java.util.List
// import java.util.Map

import java.io.FileNotFoundException

import com.cxj.util._

class homeActivity extends Activity with TypedActivity {

  val homeContent = new HomeContent(this)
  val contactsContent = new ContactsContent(this)
  val settingContent = new SettingContent(this)

  /**
   *@method onCreate
   */
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(R.layout.home)
    Log.i("chxjia", "start homeActivity")
    // activity will shut down if there is no TR.textView
    // findView(TR.textview).setText("hello, world!")
    init()
    // val intent = getIntent()
    // val data = intent.getStringExtra("extra")
    // Log.i("chxjia", "startLoginActivity with extra data:" + data)
  }

  def init() = {    
    setNavEvents()
    homeContent.init()
    contactsContent.init()
    settingContent.init()
  }

  /**
   *@DESC set navigation events 
   */
  def setNavEvents() = {
    //0 -> visible 4 -> invisible 8 -> gone
    //nav to home
    homeContent.bottom_nav_home_btn.setOnClickListener(new View.OnClickListener() {
        def onClick(view: View)  {
          Log.i("chxjia", "nav to home")
          homeContent.show()
          contactsContent.hide()
          settingContent.hide()
        }
    })
    //nav to contacts
    homeContent.bottom_nav_contacts_btn.setOnClickListener(new View.OnClickListener() {
        def onClick(view: View)  {
          Log.i("chjxia", "nav to contacts")
          contactsContent.show()
          homeContent.hide()
          settingContent.hide()
        }
    })
    //nav to settings
    homeContent.bottom_nav_setting_btn.setOnClickListener(new View.OnClickListener(){
      def onClick(view: View) {
        Log.i("chxjia", "nav to settings")
        settingContent.show()
        homeContent.hide()
        contactsContent.hide()
      }
      })
  }

}


