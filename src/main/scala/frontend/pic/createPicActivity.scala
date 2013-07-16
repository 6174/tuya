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
import android.provider.MediaStore

import java.io.FileNotFoundException
import java.io.File


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
    setOpenCameraEvent()
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
        // PopWindow.show(createPicActivity.this, R.layout.confirm_dialog, pic_body_layout)
        // val recorder = new  AudioRecorder(createPicActivity.this, pic_body_layout)
        // recorder.show()
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
        try{
          pic_content.setState(PicContent.STATE_CODE("can_add_text"))
        }catch{
          case e => Log.i("chxjia", e.getMessage().toString)
        }

        //change style of btn
      }
    })
    addInfoBtn.setOnClickListener(new View.OnClickListener(){
      def onClick(view: View) {
        Log.i("chxjia", "click add_info")
        pic_content.setState(PicContent.STATE_CODE("can_add_info"))
      }
    })
    addVoiceBtn.setOnClickListener(new View.OnClickListener(){
      def onClick(view: View) {
        Log.i("chxjia", "click add_voice")
        pic_content.setState(PicContent.STATE_CODE("can_add_voice"))
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
   *@DESC add open camera event hanlder
   */
  def setOpenCameraEvent() = {
    val btn = openCameraBtn
    btn.setOnClickListener(new View.OnClickListener(){
      def onClick(view: View){
        doTakePhoto()
      }
    })
  }

  private def doTakePhoto() {
    val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    val intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    val imageUri = Uri.fromFile(new File(android.os.Environment.getExternalStorageDirectory(), "com_cxj_image.jpg"))
    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
    startActivityForResult(intent, 100)
  }

  /**
   *@DESC: handles of action request this activity 
   *
   */
  override def onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    
    Log.i("chxjia", "ResultCode: " + resultCode.toString + " RequestCode:" + requestCode.toString)
    //requestCode 100 is for camera
    //requestCode 1 is for imageGallery
    val option = new BitmapFactory.Options()
    option.inPreferredConfig = Bitmap.Config.RGB_565
    option.inPurgeable = true
    option.inInputShareable = true
    option.inSampleSize = 2
    if(resultCode == -1){
      requestCode match{
        case RESULT_LOAD_IMAGE => getFromImageGallery()
        case 100 => getFromCamera()
      }
    }
    //from image gallery
    def getFromImageGallery(){
      val uri = data.getData()
      val cr = this.getContentResolver()
      Log.e(TAG, uri.toString())
      try {
        //use the most efficient way to read bitmap
        val bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri), null, option)
        // pic_content.bg_image.recycle()
        pic_content.bg_image = null
        pic_content.initWithBitmap(bitmap)

      } catch{
        case e => Log.e(TAG, e.getMessage(), e) 
      }
    }
    //from camera
    def getFromCamera(){
      val bitmap = BitmapFactory.decodeFile(android.os.Environment.getExternalStorageDirectory() + "/com_cxj_image.jpg", option)
      pic_content.bg_image = null
      pic_content.initWithBitmap(bitmap)
    }
  }
}
  // v.layout(mx - img.getWidth()/2,
  //    my - img.getHeight()/2, mx + img.getWidth()/2, my + img.getHeight()/2);    
