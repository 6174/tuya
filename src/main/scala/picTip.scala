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
}
class TextTip(context: Context, x:Int, y:Int, picContent:PicContent) extends Tip(context, x, y, picContent){
	var content = ""
	var create_time = ""

	//UI elements
	override val view = LayoutInflater.from(context).inflate(Tip.TEXT_LAYOUT, null) 
	view.setLayoutParams(new LayoutParams(TIP_ONE_WIDTH, TIP_ONE_HEIGHT))
	view.setX(x)
	view.setY(y)

	lazy val tip_one = view.findViewById(R.id.tip_one)
	lazy val tip_close_one = view.findViewById(R.id.close_tip_one)
	lazy val tip_two = view.findViewById(R.id.tip_two)
	lazy val tip_close_two = view.findViewById(R.id.close_tip_two)
	lazy val layoutParams = view.getLayoutParams()

	//methods
	init()

	//init PicTextTip
	private def init() = {
		addClickEvent()
		tip_one.getLayoutParams.width = TIP_ONE_WIDTH
		tip_one.getLayoutParams.height = TIP_ONE_HEIGHT
		tip_two.getLayoutParams.width = TIP_TWO_WIDTH
		tip_two.getLayoutParams.height = TIP_TWO_HEIGHT
		make_view_draggable(view)
	}

	//click the the 
	def addClickEvent() = {
		//state two to state one
 		tip_close_two.setOnClickListener(new View.OnClickListener(){
			def onClick(v: View){
				Log.i("chxjia", "close state two")
				state_one()
			}
		})
	}

	//change_to_state_two
	def state_two() = {
		if(state != Tip.STATE_REMOVABLE){
			state = Tip.STATE_TIP_TWO
			tip_one.setVisibility(View.GONE)
			layoutParams.width = TIP_TWO_WIDTH
			layoutParams.height = TIP_TWO_HEIGHT
			tip_two.setVisibility(View.VISIBLE) 
		}
	}

	//change_to_state_one
	def state_one() = {
		state = Tip.STATE_TIP_ONE
		tip_two.setVisibility(View.GONE)
		layoutParams.width = TIP_ONE_WIDTH
		layoutParams.height = TIP_ONE_HEIGHT
		tip_one.setVisibility(View.VISIBLE) 
	}

	//show popUpWindow 
	def state_three() = {
		if(state != Tip.STATE_REMOVABLE){
			val tip_three = LayoutInflater.from(context).inflate(R.layout.text_tip_popupwindow, null) 
			PopWindow.show(context, tip_three, view, null)
		}
	}

	//gesturedetector
	val gestureDetector = new GestureDetector(new GestureDetector.OnGestureListener(){
		//onSingleTapUp
		def onSingleTapUp(ev:MotionEvent):Boolean = {
   			state match {
   				case Tip.STATE_TIP_ONE => state_two()
   				//state_two_show pop_up_window
   				case Tip.STATE_TIP_TWO => state_three()
   				//state_remoable
   				case Tip.STATE_REMOVABLE => true
   				case _ => 
   			}
			false
		}
		//onLongPress, change state to removable
		def onLongPress(ev:MotionEvent) = {
			logev("longpressed")
			if(state == Tip.STATE_TIP_ONE){
				logev("removable")
				val container = LayoutInflater.from(context).inflate(R.layout.empty_popup_window, null).asInstanceOf[AbsoluteLayout]
				//record the pos current
				val pre_x = view.getX()
				val pre_y = view.getY()

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
		     			picContent.removeTip(TextTip.this)
		     			win.dismiss()
		     			win = null
		     		}	
	     		})
		        // PopWindow.show(context, container, picContent.PIC, dismissHandler) //
			} 
			false
		}
		//trait methods
		def onDown(ev:MotionEvent):Boolean = false
		def onFling(ev1: MotionEvent, ev2: MotionEvent, vx: Float, vy:Float):Boolean = false 
		def onScroll(ev1:MotionEvent, ev2:MotionEvent, disX:Float, disY:Float):Boolean = false
		def onShowPress(ev:MotionEvent) = logev("onShowPress")
		def logev(t:String, ev:MotionEvent) = Log.i("chxjia", t + ": " + ev.getX() + "," + ev.getY())
		def logev(t:String) = Log.i("chxjia", t)
	})
	/**
	 *@DESC make view draggable
	 */
	private def make_view_draggable(view: View) {
	    view.setOnTouchListener(new View.OnTouchListener() {
	      var pre_evx = 0
	      var pre_evy = 0
	      var cur_evx = 0
	      var cur_evy = 0
	      def onTouch(v: View, event: MotionEvent): Boolean = {
	      	if(gestureDetector.onTouchEvent(event)) {
    			Log.i("chxjia", "onSingleTapUp PIC")
    			return true
    		}
    		// if (state != Tip.STATE_TIP_ON) return false
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
	          // Log.i("chxjia", "touchStart")
	          pre_evy = ey 
	          pre_evx = ex 
	          cur_evy = ey 
	          cur_evx = ex
	        }
	        def action_touchmove = {
	          // Log.i("chxjia", "touchMove")
	          val deltaX = cur_evx - pre_evx 
	          val deltaY = cur_evy - pre_evy
	          pre_evy = cur_evy
	          pre_evx = cur_evx

	          cur_evx = ex 
	          cur_evy = ey

	          //v.getLeft() postion relative to It's parent
	          // Log.i("chxjia", v.getLeft())
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
}
// class VoiceTip extends Tip{}
