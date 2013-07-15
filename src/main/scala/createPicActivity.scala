package com.cxj

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.view._
import android.widget._
import android.util.Log
import java.io.FileNotFoundException


import com.cxj.util._
class createPicActivity extends Activity with TypedActivity {

  val RESULT_LOAD_IMAGE = 1
  val TAG = "chxjia"
  val RESULT_OK = 1

  //UI elements
  lazy val pic_body_layout = findView(TR.pic_body_layout)
  lazy val openImageGallaryBtn = findView(TR.btn_open_gallary)
  lazy val addTextBtn = findView(TR.btn_add_text)
  lazy val addVoiceBtn = findView(TR.btn_add_voice)
  lazy val addInfoBtn = findView(TR.btn_add_info)
  lazy val openCameraBtn = findView(TR.btn_open_camera)
  lazy val sendBtn = findView(TR.sendBtn)

  lazy val pic_content = new PicContent(this, pic_body_layout) 
  
  /**
   *@method onCreate
   */
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(R.layout.main)
    // findView(TR.textview).setText("hello, world!")
    init()
  }

  /**
   *@DESC init UI listeners, DATAs
   */
  def init() = {
    Log.i(TAG, "startInit")
    setOpenImageGallaryEvent()
    setSendEvent()
    setToolClickEvent()
  }
  
  /**
   *@DESC set send event
   */
  def setSendEvent() = {
    val btn = sendBtn
    btn.setOnClickListener(new View.OnClickListener(){
      def onClick(view: View) {
        Log.i("chxjia", "click sendBtb")
        PopWindow.show(createPicActivity.this, R.layout.confirm_dialog, pic_body_layout)
      }
    })
  }

  /**
   *@DESC set tools events
   */
  def setToolClickEvent() = {
    addTextBtn.setOnClickListener(new View.OnClickListener(){
      def onClick(view: View) {
        Log.i("chxjia", "click add_text")
        pic_content.setState(PicContent.STATE_CODE("add_text"))
        //change style of btn
      }
    })
    addInfoBtn.setOnClickListener(new View.OnClickListener(){
      def onClick(view: View) {
        Log.i("chxjia", "click add_info")
        pic_content.setState(PicContent.STATE_CODE("add_info"))
      }
    })
    addVoiceBtn.setOnClickListener(new View.OnClickListener(){
      def onClick(view: View) {
        Log.i("chxjia", "click add_voice")
        pic_content.setState(PicContent.STATE_CODE("add_voice"))
      }
    })

  }

  /**
   *@DESC add background Image method
   */
  def setOpenImageGallaryEvent() = {
    val btn = openImageGallaryBtn
    btn.setOnClickListener(new View.OnClickListener() {
      def onClick(view: View)  {
        // startActivity(new Intent(HomeActivity.this, classOf[NextActivity]))
        val intent = new Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent, 1)
      }
    })
  }


  /**
   *@DESC: handles of action request this activity 
   *
   */
  override def onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    
    Log.i("chxjia", "imageResultCode: " + resultCode.toString)
    if (requestCode == RESULT_LOAD_IMAGE ) {
      val uri = data.getData()
      val cr = this.getContentResolver()
      Log.e(TAG, uri.toString())
      try {
        //use the most efficient way to read bitmap
        val option = new BitmapFactory.Options()
        option.inPreferredConfig = Bitmap.Config.RGB_565
        option.inPurgeable = true
        option.inInputShareable = true
        option.inSampleSize = 2
        val bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri), null, option)
        // pic_content.bg_image.recycle()
        pic_content.bg_image = null
        pic_content.initWithBitmap(bitmap)

      } catch{
        case e => Log.e(TAG, e.getMessage(), e) 
      }
    }
  }
}
  // v.layout(mx - img.getWidth()/2,
  //    my - img.getHeight()/2, mx + img.getWidth()/2, my + img.getHeight()/2);    
