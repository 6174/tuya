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
import android.util.DisplayMetrics
import collection.mutable.HashMap

import java.io.FileNotFoundException
import java.util.ArrayList

import com.cxj.util._

object Tip {
	val TEXT_LAYOUT = R.layout.text_tip
	val VOICE_LAYOUT = R.layout.voice_tip
	val STATE_REMOVABLE = -1
	val STATE_TIP_ONE = 1
	val STATE_TIP_TWO = 2
}

class Tip(val context: Context, val x:Int, val y:Int, val picContent: PicContent){
	val id = guid()
	val view:View = null
	var state = Tip.STATE_TIP_ONE
	//constants
	val TIP_ONE_HEIGHT = dip2px(context, 60)
	val TIP_ONE_WIDTH = dip2px(context, 60)
	val TIP_TWO_HEIGHT = dip2px(context, 55)
	val TIP_TWO_WIDTH = dip2px(context, 200)
	/**
	 *@DESC make view draggable
	 */
	def make_view_draggable(view: View, gestureDetector:GestureDetector) {
	    view.setOnTouchListener(new View.OnTouchListener() {
	      var pre_evx = 0
	      var pre_evy = 0
	      var cur_evx = 0
	      var cur_evy = 0
	      def onTouch(v: View, event: MotionEvent): Boolean = {
	    	try{
		      	if(gestureDetector.onTouchEvent(event)) {
	    			Log.i("chxjia", "onSingleTapUp PIC")
	    			return true
	    		}
	    	}catch{
	    		case e => Log.i("chxjia", e.getMessage().toString)
	    		case _ => false
	    	}
	    	
	        val ex = event.getRawX().toInt
	        val ey = event.getRawY().toInt 
	        event.getAction() match {
	          case MotionEvent.ACTION_DOWN => action_touchstart
	          case MotionEvent.ACTION_MOVE => action_touchmove
	          case MotionEvent.ACTION_UP => action_touchend
	          case _ => true
	        }

	        def action_touchstart = {
	          pre_evy = ey 
	          pre_evx = ex 
	          cur_evy = ey 
	          cur_evx = ex
	        }
	        def action_touchmove = {
	          val deltaX = cur_evx - pre_evx 
	          val deltaY = cur_evy - pre_evy
	          pre_evy = cur_evy
	          pre_evx = cur_evx

	          cur_evx = ex 
	          cur_evy = ey
	          v.setX(deltaX + v.getX())
	          v.setY(deltaY + v.getY())
	        }
	        def action_touchend = {
	          // Log.i("chxjia", "touchend")
	        }
	        
	        true
	      }
	    })
	}
	//onLongPress, change state to removable
	def onLongPressHandler(ev:MotionEvent) = {
		// logev("longpressed")
		if(state == Tip.STATE_TIP_ONE){
			// logev("removable")
			val container = LayoutInflater.from(context).inflate(R.layout.empty_popup_window, null).asInstanceOf[AbsoluteLayout]
			//record the pos current
			val pre_x = view.getX()
			val pre_y = view.getY()
			val tip_close_one = view.findViewById(R.id.close_tip_one)

			//get location to the screen
			val location = new Array[Int](2)
			view.getLocationOnScreen(location)
			view.setX(location(0))
			view.setY(location(1))

			// container.addView(view)
			// view should removed first that can be added to a new viewgroup
			picContent.PIC.removeView(view)
			picContent.state = PicContent.STATE_CODE("uneditable")
			container.addView(view)
			tip_close_one.setVisibility(View.VISIBLE)

			//called when dismissed the popup window
			def dismissHandler() = {
				Log.i("chxjia", "call dismissHandler")
				container.removeView(view)
				tip_close_one.setVisibility(View.GONE)
				picContent.PIC.addView(view)

				view.setX(pre_x)
				view.setY(pre_y)

				picContent.state = PicContent.STATE_CODE("idle")
			}

			var win = new PopupWindow(container, -1, -1, true)
			win.setFocusable(true)
			win.setOutsideTouchable(true)
			win.showAtLocation(picContent.PIC, Gravity.CENTER, 0, 0)
			//container click handler
			container.setOnClickListener(new View.OnClickListener() {
	     		def onClick(view: View)  {
	     			dismissHandler()
	     			win.dismiss()
	     			win = null
	     		}
	     	})
	     	//close_event
	     	tip_close_one.setOnClickListener(new View.OnClickListener(){
	     		def onClick(view: View) {
	     			Log.i("chxjia", "click close one")
	     			container.removeView(view)
	     			picContent.state =PicContent.STATE_CODE("idle") 
	     			picContent.removeTip(Tip.this)
	     			win.dismiss()
	     			win = null
	     		}	
     		})
	        // PopWindow.show(context, container, picContent.PIC, dismissHandler) //
		} 
		false
	}

}