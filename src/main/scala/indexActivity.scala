package com.cxj

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.graphics.BitmapFactory
import android.view._
import android.support.v4.view._
import android.widget._
import android.util.Log
import java.io.FileNotFoundException

import com.cxj._
import com.cxj.util._

class indexActivity extends Activity with TypedActivity {
	lazy val view_pager = findView(TR.index_viewpager)
	lazy val sign_in_btn = findView(TR.index_login_btn)
	lazy val sign_up_btn = findView(TR.index_regist_btn)
	lazy val view_pages = new Array[View](3)	
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
		initViewPager()
	}
	/**
	 *@DESC set sign btn event handler
	 */
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

	/**
	 *@DESC  init view pager
	 */
	def initViewPager() = {
		//view = LayoutInflater.from(context).inflate(layout, null) 
		val inflater = getLayoutInflater()
		view_pages(0) = inflater.inflate(R.layout.index_viewpager_page1, null)
		view_pages(1) = inflater.inflate(R.layout.index_viewpager_page2, null)
		view_pages(2) = inflater.inflate(R.layout.index_viewpager_page3, null)

		view_pager.setAdapter(new MyviewpagerAdapter(view_pages))
		view_pager.setCurrentItem(0)
		// view_pager.setOnPageChangeListener(new MyOnPageChangeListener())
	}

	/**
	 *@Class
	 *@DESC view pager adapter
	 */
	class MyviewpagerAdapter(val pages:Array[View]) extends PagerAdapter {
		val len = pages.length
		override def destroyItem(v: View, arg1: Int, arg2: Object){
			val viewPager = v.asInstanceOf[ViewPager]
			viewPager.removeView(pages(arg1))
		}
		override def instantiateItem(v:View, arg1:Int):Object = {
			val viewPager = v.asInstanceOf[ViewPager]
			viewPager.addView(pages(arg1), 0)
			pages(arg1)
		}
		override def finishUpdate(arg0: View) = 0
		override def getCount():Int = len
		override def isViewFromObject(arg0:View, arg1:Object):Boolean = (arg0 == arg1)
		// override def restoreState(arg0: Parcelable, arg1: ClassLoader) = 0
		// override def saveState():Parcelable = null
	}

	/**
	 *@class
	 *@DESC on page change Listener
	 */

}