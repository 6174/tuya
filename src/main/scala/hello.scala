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
class hello extends Activity with TypedActivity {

  val RESULT_LOAD_IMAGE = 1
  val TAG = "chxjia"
  val RESULT_OK = 1
  lazy val pic_body_layout = findView(TR.pic_body_layout)
  lazy val openImageGallaryBtn = findView(TR.openImageGallaryBtn)
  lazy val sendBtn = findView(TR.sendBtn)
  lazy val pic_content:picContent = new picContent(this, pic_body_layout) 
  /**
   *@method onCreate
   */
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(R.layout.main)
    findView(TR.textview).setText("hello, world!")
    init()
  }

  /**
   *@DESC init UI listeners, DATAs
   */
  def init() = {
    Log.i(TAG, "startInit")
    setOpenImageGallaryEvent()
    setSendEvent()
  }
  
  /**
   *@DESC set send event
   */
  def setSendEvent() = {
    val btn = sendBtn
    btn.setOnClickListener(new View.OnClickListener(){
      def onClick(view: View) {
        Log.i("chxjia", "click sendBtb")
        PopWindow.show(hello.this, R.layout.confirm_dialog, pic_body_layout)
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
    
    Log.i("chxjia", resultCode.toString)

    if (requestCode == RESULT_LOAD_IMAGE ) {
      val uri = data.getData()
      val cr = this.getContentResolver()
      Log.e(TAG, uri.toString())
      try {
        val bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri))
        pic_content.initWithBitmap(bitmap)
      } catch{
        case e => Log.e(TAG, e.getMessage(), e) 
      }
    }
  }
  /**
   *@DESC test add btn dynamicly
   */ 
  def test_add_btn_dynamic() = {
  	val btn = new Button(this)
  	btn.setText("dynamic add btn")
    btn.setText(px2dip(this, 40.5).toString)
    btn.setX(100)
    btn.setY(100)
  	pic_body_layout.addView(btn)
  }
}
// img.setOnTouchListener(new OnTouchListener(){               
//         private int mx, my;             
//         public boolean onTouch(View v, MotionEvent event) {    
//             switch(event.getAction()) {                 
//             case MotionEvent.ACTION_MOVE:    
//                 mx = (int)(event.getRawX());    
//                 my = (int)(event.getRawY() - 50);    
                    
//                 v.layout(mx - img.getWidth()/2,
//                  my - img.getHeight()/2, mx + img.getWidth()/2, my + img.getHeight()/2);    
//                 break;    
//             }    
//             return true;    
//         }});    

// public void onCreate(Bundle savedInstanceState) { 
//     super.onCreate(savedInstanceState); 
//     setContentView(R.layout.main); 
     
//     Button button = (Button)findViewById(R.id.b01); 
//     button.setText("选择图片"); 
//     button.setOnClickListener(new Button.OnClickListener(){ 
//         @Override 
//         public void onClick(View v) { 
//             Intent intent = new Intent(); 
//             /* 开启Pictures画面Type设定为image */ 
//             intent.setType("image/*"); 
//             /* 使用Intent.ACTION_GET_CONTENT这个Action */ 
//             intent.setAction(Intent.ACTION_GET_CONTENT);  
//             /* 取得相片后返回本画面 */ 
//             startActivityForResult(intent, 1); 
//         } 
         
//     }); 
// } 
// @Override 
// protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
//     if (resultCode == RESULT_OK) { 
//         Uri uri = data.getData(); 
//         Log.e("uri", uri.toString()); 
//         ContentResolver cr = this.getContentResolver(); 
//         try { 
//             Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri)); 
//             ImageView imageView = (ImageView) findViewById(R.id.iv01); 
//             /* 将Bitmap设定到ImageView */ 
//             imageView.setImageBitmap(bitmap); 
//         } catch (FileNotFoundException e) { 
//             Log.e("Exception", e.getMessage(),e); 
//         } 
//     } 
//     super.onActivityResult(requestCode, resultCode, data); 
// } 
 
//In java    
//Button button = (Button)findViewById(R.id.my_button);
// AbsoluteLayout.LayoutParams absParams = 
//     (AbsoluteLayout.LayoutParams)button.getLayoutParams();
// absParams.x = myNewX;
// absParams.y = myNewY;
// button.setLayoutParams(absParams);
//Button myButton = (Button) findViewById(R.id.<id of the button in the layout xml file>);
// myButton.setX(<x value>);
// myButton.setY(<y value>);
