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
	lazy val dynamic_list = findView(TR.home_dynamic_state_list)
  lazy val home_content_layout = findView(TR.home__content_layout)
  lazy val contacts_content_layout = findView(TR.contacts_content_layout)
  lazy val bottom_nav_home_btn = findView(TR.bottom_nav_home_btn)
  lazy val bottom_nav_contacts_btn = findView(TR.bottom_nav_contacts_btn)
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
    testSimpleAdapter()
    setNavEvents()
  }

  def testArrayAdapter() = {
    Log.i("chxjia", "testArrayAdapter")
    val mdata = Array("1", "2", "3")
    val adapter = new ArrayAdapter[String](
        this, 
        android.R.layout.simple_expandable_list_item_1, 
        mdata)
    dynamic_list.setAdapter(adapter)
  }

  /**
   *@DESC set dynamic adapter
   */
  def testSimpleAdapter() = {
    val size = 20
    val data = new Array[Map[String, String]](size)
    // Map("title" -> "wowo", "info" -> "hahahhaha"),
    // Map("title" -> "haoba", "info" -> "so what"))
    for(i <- 0 until size) {
      data(i) = Map("title" -> "hha", "info" -> "today is a nice day")
    }
    val adapter = new TuyaDynamicListAdapter(this, data)
    dynamic_list.setAdapter(adapter)
  }

  /**
   *@DESC set navigation events 
   */
  def setNavEvents() = {
    //0 -> visible 4 -> invisible 8 -> gone
    //nav to home
    bottom_nav_home_btn.setOnClickListener(new View.OnClickListener() {
        def onClick(view: View)  {
          Log.i("chxjia", "nav to home")
          home_content_layout.setVisibility(View.VISIBLE)
          contacts_content_layout.setVisibility(View.GONE)
        }
    })
    //nav to contacts
    bottom_nav_contacts_btn.setOnClickListener(new View.OnClickListener() {
        def onClick(view: View)  {
          Log.i("chjxia", "nav to contacts")
          home_content_layout.setVisibility(View.GONE)
          contacts_content_layout.setVisibility(View.VISIBLE) 
        }
    })
  }
}

/**
 *@DESC ViewHolder  hold the view elements of dynamic list item
 */
class ViewHolder{
  var title:TextView = _
  var img: ImageView = _
  var info: TextView = _
} 
/**
 *@DESC adapter to the dynamic_list
 */
class TuyaDynamicListAdapter(
  val context:Context , 
  val data:Array[Map[String, String]] ) extends BaseAdapter {
  private  val inflater = LayoutInflater.from(context)
  private  val layout = R.layout.home_dynamic_state_list_item

  override def getCount():Int = data.size
  override def getItem(arg0: Int):Object = null
  override def getItemId(arg0: Int):Long = 0

  override def getView(position: Int, convertView: View, parent: ViewGroup ):View = {
    var holder:ViewHolder = null
    var view = convertView
    if(view == null) {
      holder = new ViewHolder()
      view = inflater.inflate(this.layout, null)
      holder.title = view.findViewById(R.id.title).asInstanceOf[TextView]
      holder.info = view.findViewById(R.id.info).asInstanceOf[TextView]
      view.setTag(holder)
    } else {
      holder = view.getTag().asInstanceOf[ViewHolder] 
    }
    holder.title.setText(data(position)("title"))
    holder.info.setText(data(position)("info"))
    view
  } 
}