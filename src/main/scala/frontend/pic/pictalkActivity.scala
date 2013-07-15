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

class picTalkActivity extends Activity with TypedActivity {
  
  lazy val pictalk_list = findView(TR.pictalk_list)
  lazy val reply_btn = findView(TR.pictalk_bottom_reply_btn)
  /**
   *@method onCreate
   */
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    setContentView(R.layout.pictalk)
    Log.i("chxjia", "start pictalckActivity")
    // activity will shut down if there is no TR.textView
    // findView(TR.textview).setText("hello, world!")
    init()
    // val intent = getIntent()
    // val data = intent.getStringExtra("extra")
    // Log.i("chxjia", "startLoginActivity with extra data:" + data)
  }

  def init() = {
    initReplyEvent()
  }

  /**
   *@DESC reply btn clicked handler
   */
  def initReplyEvent() = {
    val btn = reply_btn
    btn.setOnClickListener(new View.OnClickListener(){
      def onClick(view: View) {
        Log.i("chxjia", "sign in")
        val intent = new Intent()
        intent.putExtra("extra", "extra data")
        intent.setClass(picTalkActivity.this, classOf[createPicActivity])
        startActivity(intent)
      }
    })
  }

  /**
   *@DESC contact_view_holder
   */
  class picCardViewHolder {
  	var userLogo: ImageView = _
  	var userName: TextView = _
  	var picCardView: ViewGroup = _
  }
  /**
   *@DESC contactsListAdapter
   */
  class picTalkListAdapter(
    val context: Context
    ) extends BaseAdapter {
    private  val inflater = LayoutInflater.from(context)
    private  val card_layout= R.layout.pic_card

    override def getCount():Int =  1
    override def getItem(arg0: Int):Object = null
    override def getItemId(arg0: Int):Long = 0
    override def getView(position: Int, convertView: View, parent: ViewGroup ):View = {
      var holder:picCardViewHolder = null
      val view = convertView
      view
    } 
  }

}