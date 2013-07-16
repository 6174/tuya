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

import collection.mutable.HashMap
import java.io.FileNotFoundException
import java.util.ArrayList

import com.cxj.util._

/**
 *@DESC companion object of class picContent
 */
object PicContent{
	val STATE_CODE = Map(
		"idle" -> 0, 
		"can_add_text" -> 1, 
		"can_add_voice" -> 2, 
		"can_add_info" -> 3, 
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
  	val picTips = new HashMap[String, Tip]()
	val PIC = new AbsoluteLayout(context)

	PIC.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT))
	PIC.setBackgroundColor(R.color.pic_default_bg_color)

	lazy val swidth = screenWidth(context)
	lazy val sheight = screenHeight(context)
	var picwidth = PIC.getWidth()
	var picheight = PIC.getHeight()
	val w_height = wrapper.getHeight()
	val w_width = wrapper.getWidth()

	var bg_image:Drawable = null

	//add to the pic_content wrapper
	wrapper.addView(PIC)

	//state_code
	var state = PicContent.STATE_CODE("idle") 
	
	/**
	 *@DESC set pic image
	 *@Method initWithBitmao
	 *@param {Bitmap} bitmap 
	 */
	def initWithBitmap(bitmap:Bitmap) {
		Log.i("chxjia", "init picContent")
		val width = bitmap.getWidth()
		val height = bitmap.getHeight()
		Log.i("chxjia", width.toString() + " " + height.toString())

		val bd = new BitmapDrawable(bitmap)
		bg_image = bd.asInstanceOf[Drawable]

		picheight = (height*2.5).toInt
		picwidth = (width*2.5).toInt
		PIC.getLayoutParams().height = picheight 
		PIC.getLayoutParams().width = picwidth 
		PIC.setBackground(bg_image)

		//center picture
		var pos_x = (swidth - picwidth)/2 
	    var pos_y = (sheight - picheight)/2
	    PIC.setX(pos_x)
	    PIC.setY(pos_y)
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
				def onDown(ev:MotionEvent):Boolean = false
				def onFling(ev1: MotionEvent, ev2: MotionEvent, vx: Float, vy:Float):Boolean = false
				def onLongPress(ev:MotionEvent) = logev("onLongPress")
				def onScroll(ev1:MotionEvent, ev2:MotionEvent, disX:Float, disY:Float):Boolean = false
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
    		if(state != PicContent.STATE_CODE("uneditable")){
					val x = event.getX().toInt
				 	val y = event.getY().toInt
				 	state match{
				 		case  1 => addText(x, y)
				 		case  2 => addVoice(x, y)
				 		case  3 => false
				 		case _ => false
				 	}
    		}
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

	          var pos_x = deltaX + v.getX()
	          var pos_y = deltaY + v.getY()

	          //v.getLeft() postion relative to It's parent
	          // Log.i("chxjia", v.getLeft())
	          //bounding box
	          if(pos_x >= 0) pos_x = 0
	          if(pos_y >= 0) pos_y = 0

	          if(pos_x + picwidth <= swidth) pos_x = swidth - picwidth 
	          if(pos_y + picheight <= sheight) pos_y = sheight - picheight 

	          if(picwidth <= swidth) pos_x = {
	          	//center
	          	(swidth - picwidth)/2
	          }
	          if(picheight <= sheight) pos_y = {
	          	//center
	          	(sheight - picheight)/2
	          }

	          v.setX(pos_x)
	          v.setY(pos_y)
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
		// add_tip(x, y)
		Log.i("chxjia", "add text Tip" + " tip_size:" + picTips.size.toString)
		val tip = new TextTip(context, x, y, this)
		picTips += (tip.id -> tip)
	  PIC.addView(tip.view)
	}

	/**
	 *@DESC add voice handler
	 */
	def addVoice(x:Int, y:Int) {
		Log.i("chxjia", "add voice tip" + "tip_size" + picTips.size.toString)
		val tip = new VoiceTip(context, x, y, this)
		picTips += (tip.id -> tip)
		PIC.addView(tip.view)
	}
	
	/**
	 *@DESC add voice handler
	 */
	def addInfo(x:Int, y:Int) {

	}

	/**
	 *@DESC remove Tip
	 */
	def removeTip(tip:Tip) = {
		PIC.removeView(tip.view)
		picTips -= tip.id
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

	init_pic_motion_event_delegater()
}


 