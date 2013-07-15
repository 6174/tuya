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

class VoiceTip(context: Context, x:Int, y:Int, picContent:PicContent) extends Tip(context, x, y, picContent){
	var voice_record = ""
	var create_time = ""

	override val view = LayoutInflater.from(context).inflate(Tip.VOICE_LAYOUT, null)
	view.setLayoutParams(new LayoutParams(TIP_ONE_WIDTH, TIP_ONE_HEIGHT))
	view.setX(x)
	view.setY(y)

	lazy val tip_one = view.findViewById(R.id.tip_one)
	lazy val tip_close_one = view.findViewById(R.id.close_tip_one)
	lazy val layoutParams = view.getLayoutParams()

	
	//gesturedetector
	private val gestureDetector = new GestureDetector(new GestureDetector.OnGestureListener(){
		def onSingleTapUp(ev:MotionEvent):Boolean = {
			false
		}
		def onLongPress(ev:MotionEvent) = onLongPressHandler(ev)
		def onDown(ev:MotionEvent):Boolean = false
		def onFling(ev1: MotionEvent, ev2: MotionEvent, vx: Float, vy:Float):Boolean = false 
		def onScroll(ev1:MotionEvent, ev2:MotionEvent, disX:Float, disY:Float):Boolean = false
		def onShowPress(ev:MotionEvent) = logev("onShowPress")
		def logev(t:String, ev:MotionEvent) = Log.i("chxjia", t + ": " + ev.getX() + "," + ev.getY())
		def logev(t:String) = Log.i("chxjia", t)
	})
	private def init() = {
		tip_one.getLayoutParams.width = TIP_ONE_WIDTH
		tip_one.getLayoutParams.height = TIP_ONE_HEIGHT
		make_view_draggable(view, gestureDetector)
	}
	init()
}