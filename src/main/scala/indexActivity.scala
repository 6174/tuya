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

class indexActivity extends Activity with TypedActivity {
	/**
   *@method onCreate
   */
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(R.layout.index)
    // activity will shut down if there is no TR.textView
    // findView(TR.textview).setText("hello, world!")
    // init()
  }
}