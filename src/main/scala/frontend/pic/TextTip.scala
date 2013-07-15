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

class TextTip(context: Context, x:Int, y:Int, picContent:PicContent) extends Tip(context, x, y, picContent){
	//attr fileds
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
		def onLongPress(ev:MotionEvent) = onLongPressHandler(ev)
		//trait methods
		def onDown(ev:MotionEvent):Boolean = false
		def onFling(ev1: MotionEvent, ev2: MotionEvent, vx: Float, vy:Float):Boolean = false 
		def onScroll(ev1:MotionEvent, ev2:MotionEvent, disX:Float, disY:Float):Boolean = false
		def onShowPress(ev:MotionEvent) = logev("onShowPress")
		def logev(t:String, ev:MotionEvent) = Log.i("chxjia", t + ": " + ev.getX() + "," + ev.getY())
		def logev(t:String) = Log.i("chxjia", t)
	})

	//init PicTextTip
	private def init() = {
		addClickEvent()
		tip_one.getLayoutParams.width = TIP_ONE_WIDTH
		tip_one.getLayoutParams.height = TIP_ONE_HEIGHT
		tip_two.getLayoutParams.width = TIP_TWO_WIDTH
		tip_two.getLayoutParams.height = TIP_TWO_HEIGHT
		make_view_draggable(view, gestureDetector)
	}
	init()

}
// class VoiceTip extends Tip{}
