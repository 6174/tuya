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
// import android.view.View;
// import android.widget.AdapterView.OnItemSelectedListener;
// import android.widget.ArrayAdapter;
// import android.widget.Spinner;
// import android.widget.Toast;
class hello extends Activity with TypedActivity {

  val RESULT_LOAD_IMAGE = 1
  val TAG = "chxjia"
  val RESULT_OK = 1
  lazy val body_layout = findView(TR.pic_body_layout)
  lazy val openImageGallaryBtn = findView(TR.openImageGallaryBtn)
  lazy val sendBtn = findView(TR.sendBtn)
  /**
   *@method onCreate
   */
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
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

  def test_add_btn_dynamic() = {
  	val btn = new Button(this)
  	btn.setText("dynamic add btn")
    btn.setText(px2dip(this, 40.5).toString)
    btn.setX(100)
    btn.setY(100)
  	body_layout.addView(btn)
  }

  def addParams(view: View, paramsMap: Map[String, String]) = {
    val lparams = view.getLayoutParams()
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

  def setSendEvent() = {
    val btn = sendBtn
    btn.setOnClickListener(new View.OnClickListener(){
      def onClick(view: View) {
        Log.i("chxjia", "click sendBtb")
        PopWindow.show(hello.this, R.layout.confirm_dialog, body_layout)
      }
    })
  }

  /**
   *@DESC: handles of action request this activity 
   *
   *here is request
   */
  override def onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    
    Log.i("chxjia", resultCode.toString)

    if (requestCode == RESULT_LOAD_IMAGE ) {
      val uri = data.getData()
      val cr = this.getContentResolver()
      Log.e(TAG, uri.toString())
      try {
        val bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri))
        val imageView = new ImageView(this)
        imageView.setImageBitmap(bitmap)
        
        imageView.setX(10)
        imageView.setY(10)
        make_view_draggable(imageView)
        body_layout.addView(imageView)
      } catch{
        case e => Log.e(TAG, e.getMessage(), e) 
      }
    }
  }

  def make_view_draggable(view: View) {
    view.setOnTouchListener(new View.OnTouchListener() {
      var ox = 0
      var oy = 0
      def onTouch(v: View, event: MotionEvent): Boolean = {

        //getRawX is according to the screen left-top corner
        //getX is according to the widget left-top corner
        val ex = event.getRawX().toInt
        val ey = event.getRawY().toInt 
        Log.i("chxjia", "touch image" + event.getAction())
        Log.i("chxjia", Array(ex, ey, v.getWidth(), v.getHeight()).mkString(", "))

        //when there is no matched case , the activity will shut down
        event.getAction() match {
          case MotionEvent.ACTION_DOWN => action_touchstart
          case MotionEvent.ACTION_MOVE => action_touchmove
          case MotionEvent.ACTION_UP => action_touchend
          case _ => true
        }

        def action_touchstart = {
          Log.i("chxjia", "touchStart")
          //v.getLeft() postion relative to It's parent
          ox = ex - v.getLeft()
          oy = ey - v.getTop()
        }
        def action_touchmove = {
          Log.i("chxjia", "touchMove")
          v.setX(ex - ox)
          v.setY(ey - oy)
        }
        def action_touchend = {
          Log.i("chxjia", "touchend")
        }
        
        true
      }
    })
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
