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


object  Gesture{

	val detector = new GestureDetector(new GestureDetector.OnGestureListener(){

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
			logev("onDown")
			false
		}
	})

	def logev(t:String, ev:MotionEvent) = {
		Log.i("chxjia", t + ": " + ev.getX() + "," + ev.getY())
	}
	def logev(t:String) = {
		Log.i("chxjia", t)
	}
 // 66     @Override 
 // 67     public boolean onTouchEvent(MotionEvent event)  
 // 68     {  
 // 69         if(gestureDetector.onTouchEvent(event))  
 // 70             return true;  
 // 71         else 
 // 72             return false;  
 // 73     }  
}
