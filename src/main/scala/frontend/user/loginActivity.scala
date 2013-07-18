package com.cxj

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.graphics.BitmapFactory
import android.view._
import android.widget._
import android.util.Log
import java.io.FileNotFoundException

import com.cxj.util._

class loginActivity extends Activity with TypedActivity {
  lazy val sign_in_btn = findView(TR.login_login_btn)
  lazy val userNameFiled = findView(TR.login_name_filed)
  lazy val userPasswordFiled = findView(TR.login_password_filed)

	/**
   *@method onCreate
   */
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(R.layout.login)
    Log.i("chxjia", "start loginActivity")
    // activity will shut down if there is no TR.textView
    // findView(TR.textview).setText("hello, world!")
    init()
    // val intent = getIntent()
    // val data = intent.getStringExtra("extra")
    // Log.i("chxjia", "startLoginActivity with extra data:" + data)
  }

  def init() = {
    setSignInEvent()
  }

  /**
   *@DESC sign in btn click event handler
   */
  def setSignInEvent() = {
    val btn = sign_in_btn
    btn.setOnClickListener(new View.OnClickListener(){
      def onClick(view: View) {
        Log.i("chxjia", "sign in")
        login()       
      }
    })
  }

  /**
   *@method login
   *@DESC  login event handler 
   */
  private def login(){
    val name = userNameFiled.getText().toString.trim
    val password = userPasswordFiled.getText().toString.trim
    if(name == "") {
      toast("username can't be empty")
      return 
    }
    if(password == ""){
      toast("password can't be empty")
      return
    }
    if(loginAjax(name, password)){
        val intent = new Intent()
        intent.putExtra("extra", "extra data")
        intent.setClass(loginActivity.this, classOf[homeActivity])
        startActivity(intent)
    }else{
      toast("something wrong")
    }
  } 


  /**
   *
   *@method loginAjax
   *@DESC login from ajax
   */
  private def loginAjax(name:String, password:String):Boolean = true 
  private def toast(info:String){
    Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
  }
  private def isLogined() = {}
}