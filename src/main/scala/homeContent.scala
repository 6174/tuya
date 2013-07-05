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

import com.cxj.util._

/**
 *@DESC Home Content , include home views and 
 *
 */
class HomeContent(val context:Context){
  //home_content views 
  val ctx = context.asInstanceOf[homeActivity]

  var isShow = true
  lazy val dynamic_list = ctx.findView(TR.home_dynamic_state_list)
  lazy val content = ctx.findView(TR.home__content_layout)
  lazy val bottom_nav_home_btn = ctx.findView(TR.bottom_nav_home_btn)
  lazy val bottom_nav_contacts_btn = ctx.findView(TR.bottom_nav_contacts_btn)

  /**
   *@DESC init method
   */
  def init() = {
   	setTuyaDynamicListAdapter()
  }

  /**
   *@DESC show or hide a content
   */
  def toggle()= {
    if (isShow){
      content.setVisibility(View.GONE)
      isShow = false
    }else {
      content.setVisibility(View.VISIBLE)
      isShow = true
    }

  }

  def testArrayAdapter() = {
	  Log.i("chxjia", "testArrayAdapter")
    val mdata = Array("1", "2", "3")
    val adapter = new ArrayAdapter[String](
	        ctx, 
	        android.R.layout.simple_expandable_list_item_1, 
	        mdata)
	  dynamic_list.setAdapter(adapter)
	}
	/**
	 *@DESC set dynamic adapter
	 */
	def setTuyaDynamicListAdapter() = {
	  val size = 20
	  val data = new Array[Map[String, String]](size)
	  for(i <- 0 until size) {
	    data(i) = Map("title" -> "hha", "info" -> "today is a nice day")
	  }
	  val adapter = new TuyaDynamicListAdapter(ctx, data)
	  dynamic_list.setAdapter(adapter)
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
	      holder.title.setOnClickListener(new View.OnClickListener() {
		        def onClick(view: View)  {
		          Log.i("chxjia", "nav to pictalk activity")
		          val intent = new Intent()
			        intent.putExtra("extra", "extra data")
			        intent.setClass(context, classOf[picTalkActivity])
			        context.startActivity(intent)
		        }
		    })
	    } else {
	      holder = view.getTag().asInstanceOf[ViewHolder] 
	    }
	    holder.title.setText(data(position)("title"))
	    holder.info.setText(data(position)("info"))
	    view
	  } 
	}
}