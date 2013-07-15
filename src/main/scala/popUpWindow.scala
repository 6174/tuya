package com.cxj

import android.view._
import android.widget._
import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.content.Context
import android.view.WindowManager.LayoutParams
import android.graphics.BitmapFactory
import android.util.Log

import com.cxj.util._

object PopWindow {
	def show(context: Context, layout: Int, anchor: View) {
		Log.i("chxjia", "show pop window")
		val popview = LayoutInflater.from(context).inflate(layout, null)
		//initial content, and size
		var win = new PopupWindow(popview, 
			// LayoutParams.FILL_PARENT, 
			// LayoutParams.FILL_PARENT,
			-1, -1, true)
		// val full_width = context.getWindowManager.getWidth()
		// val full_height = context.getWindowManager.getHeight()
		// val dw = new Color
		// win.setBackground("#12121212")
		win.setFocusable(true)
		win.setOutsideTouchable(true)
		win.showAtLocation(anchor, Gravity.CENTER, 0, 0)
		popview.setOnClickListener(new View.OnClickListener() {
     		def onClick(view: View)  {
     			win.dismiss()
     			win = null
     		}
     	})
	}
	/**
	 *@DESC show a popwindow with a view
	 */
	def show(context: Context, view: View, anchor: View, handler: () => Unit) {
		Log.i("chxjia", "show text tip")
		var win = new PopupWindow(view, -1, -1, true)
		win.setFocusable(true)
		win.setOutsideTouchable(true)
		win.showAtLocation(anchor, Gravity.CENTER, 0, 0)
		view.setOnClickListener(new View.OnClickListener() {
     		def onClick(view: View)  {
     			handler()
     			win.dismiss()
     			win = null
     		}
     	})
	}
} 