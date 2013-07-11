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
import java.util.ArrayList

import com.cxj.util._

/**
 *@DESC companion object of class picContent
 */
object PicContent{
	val STATE_CODE = Map(
		"idle" -> 0, 
		"add_text" -> 1, 
		"add_voice" -> 2, 
		"add_info" -> 3, 
		"uneditable" -> 4, 
		"drag_pic" -> 5
		)
}
/**
 *@DESC PicContent is a class handling tuya pic editing methods  
 */
class PicContent(val context: Context,val wrapper:ViewGroup) {
	//create pic content
  // val ACTIVITY = context.asInstanceOf[createPicActivity]
  val picNotesArr = new ArrayList[View]()
	val PIC = new AbsoluteLayout(context)

	PIC.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT))
	PIC.setBackgroundColor(R.color.pic_default_bg_color)

	val w_height = wrapper.getHeight()
	val w_width = wrapper.getWidth()
	//add to the pic_content wrapper
	wrapper.addView(PIC)
	// make_view_draggable(PIC)
	init_pic_motion_event_delegater()

	//state_code
	var state = PicContent.STATE_CODE("idle") 
	
	/**
	 *@DESC set pic image
	 */
	def initWithBitmap(bitmap:Bitmap) {
		Log.i("chxjia", "init picContent")
		Log.i("chxjia", state.toString())
		val width = bitmap.getWidth()
		val height = bitmap.getHeight()
		Log.i("chxjia", width.toString() + " " + height.toString())
		val bd = new BitmapDrawable(bitmap)
		val image = bd.asInstanceOf[Drawable]
		PIC.getLayoutParams().height = (height*1.5).toInt
		PIC.getLayoutParams().width = (width*1.5).toInt
		PIC.setBackground(image)
		//this way will shut down activity 
		// PIC.setLayoutParams(new LayoutParams(width, height))
	}

	/**
	 *@DESC change the state of PIC 
	 *		 handle Pick click event according to state 
	 */
	def setState(state_code: Int) = state = state_code 
	
	/**
	 *@DESC PIC_event_delegator handle all pic events of the pic
	 * 			include the drag drop and the click events
	 */
	private def init_pic_motion_event_delegater() {
		PIC.setOnTouchListener(new View.OnTouchListener{
		 	// val pre_event:MotionEvent = _
		 	// val cur_event:MotionEvent = _
		 	var pre_evx = 0
      var pre_evy = 0
      var cur_evx = 0
      var cur_evy = 0
    	val gestureDetector = new GestureDetector(new GestureDetector.OnGestureListener(){
				//on_down
				def onDown(ev:MotionEvent):Boolean = {
					logev("mouse down", ev)
					false
				}
				//onFling
				def onFling(ev1: MotionEvent, ev2: MotionEvent, vx: Float, vy:Float):Boolean = {
					logev("Fling")
					false
				}
				//onLongPress
				def onLongPress(ev:MotionEvent) = logev("onLongPress")
				//onScroll
				def onScroll(ev1:MotionEvent, ev2:MotionEvent, disX:Float, disY:Float):Boolean = {
					logev("onscroll")
					false
				}
				//onShowPress
				def onShowPress(ev:MotionEvent) = logev("onShowPress")

				//onSingleTapUp
				def onSingleTapUp(ev:MotionEvent):Boolean = {
					handleOnSingleTapUp(ev)
					false
				}
				def logev(t:String, ev:MotionEvent) = {
					Log.i("chxjia", t + ": " + ev.getX() + "," + ev.getY())
				}
				def logev(t:String) = {
					Log.i("chxjia", t)
				}
			})
			//touch event
    	def onTouch(v:View, event:MotionEvent):Boolean = {
    		if(gestureDetector.onTouchEvent(event)) {
    			Log.i("chxjia", "onSingleTapUp PIC")
    			return true
    		}
    		dragView(v, event)
    	}
    	//handleOnSingleTapUp
    	def handleOnSingleTapUp(event:MotionEvent) = {
			  def add_tip(x:Int, y:Int ) = {
			  	Log.i("chxjia", "add tip at " + x.toString + ", " + y.toString)
			  	val btn = new Button(context)
			  	btn.setText("TIP")
			    btn.setX(x)
			    btn.setY(y)
			  	PIC.addView(btn)
			  }
			  val x = event.getX().toInt
			  val y = event.getY().toInt
			  // add_tip(x, y)
			  val tip = new TextTip(context, x, y)
			  PIC.addView(tip.view)
    	}
    	//dragView
    	def dragView(v:View, event:MotionEvent):Boolean = {
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

	/**
	 *@DESC add text handler
	 */
	def addText(x:Int, y:Int) {

	}

	/**
	 *@DESC add voice handler
	 */
	def addInfo(x:Int, y:Int) {

	}
	/*
	 *@DESC add textNote event
	 */
	def initTextNoteEvents(textNote: View){

	} 
	/**
	 *@DESC add voice Note event
	 */
	def initVoiceNoteEvents(voiceNote: View){

	}
}


 