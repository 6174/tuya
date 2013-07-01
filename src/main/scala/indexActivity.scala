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

import com.cxj._
import com.cxj.util._

class indexActivity extends Activity with TypedActivity {
	lazy val sign_in_btn = findView(TR.index_login_btn)
	lazy val sign_up_btn = findView(TR.index_regist_btn)	
	/**
 	*@method onCreate
	*/
	override def onCreate(bundle: Bundle) {
	  	super.onCreate(bundle)
		requestWindowFeature(Window.FEATURE_NO_TITLE)
	    setContentView(R.layout.index)
	    // activity will shut down if there is no TR.textView
	    // findView(TR.textview).setText("hello, world!")
	    Log.i("chxjia", "start IndexActivity")
	    init()
	}

	/**
	 *@DESC Init event handles
	 */
	def init() = {
		setSignInEvent()
	}

	def setSignInEvent() = {
		val btn = sign_in_btn
	    btn.setOnClickListener(new View.OnClickListener(){
	      def onClick(view: View) {
	        Log.i("chxjia", "sign in")
	        val intent = new Intent()
	        intent.putExtra("extra", "extra data")
	        intent.setClass(indexActivity.this, classOf[loginActivity])
	        startActivity(intent)
	      }
	    })
	}
}