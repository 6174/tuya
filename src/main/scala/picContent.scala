package com.cxj

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.content.Context
import android.net.Uri
import android.graphics._
import android.graphics.drawable._
import android.view._
import android.view.ViewGroup.LayoutParams
import android.widget._
import android.util.Log
import java.io.FileNotFoundException

import com.cxj.util._

class picContent(val context: Context,val wrapper:ViewGroup) {
	//create pic content
	val PIC = new LinearLayout(context)
	PIC.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT))
	PIC.setBackgroundColor(R.color.pic_default_bg_color)
	//add to the pic_content wrapper
	wrapper.addView(PIC)
	val w_height = wrapper.getHeight()
	val w_width = wrapper.getWidth()
	make_view_draggable(PIC)
	/**
	 *@DESC set pic image
	 */
	def initWithBitmap(bitmap:Bitmap) {
		Log.i("chxjia", "init picContent")
		val width = bitmap.getWidth()
		val height = bitmap.getHeight()
		Log.i("chxjia", width.toString() + " " + height.toString())
		val bd = new BitmapDrawable(bitmap)
		val image = bd.asInstanceOf[Drawable]
		PIC.setBackground(image)
		PIC.getLayoutParams().height = height*2
		PIC.getLayoutParams().width = width*2
		//this way will shut down activity 
		// PIC.setLayoutParams(new LayoutParams(width, height))
	}

	/**
	 *@DESC make view draggable
	 */
	private def make_view_draggable(view: View) {
	    view.setOnTouchListener(new View.OnTouchListener() {
	      var ox = 0
	      var oy = 0
	      def onTouch(v: View, event: MotionEvent): Boolean = {
	        //getRawX is according to the screen left-top corner
	        //getX is according to the widget left-top corner
	        val ex = event.getRawX().toInt
	        val ey = event.getRawY().toInt 
	        // Log.i("chxjia", "touch image" + event.getAction())
	        // Log.i("chxjia", Array(ex, ey, v.getWidth(), v.getHeight()).mkString(", "))
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