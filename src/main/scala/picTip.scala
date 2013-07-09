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

object Tip {
	val TEXT_LAYOUT = R.layout.text_tip
}
class Tip(val context: Context, val x:Int, val y:Int){

}
class TextTip(context: Context, x:Int, y:Int ) extends Tip(context, x, y){
	val TIP_HEIGHT = dip2px(context, 60)
	val TIP_WIDTH = dip2px(context, 60)
	var content = ""
	var create_time = ""
	var state = 1
	lazy val view = LayoutInflater.from(context).inflate(Tip.TEXT_LAYOUT, null) 
	view.setLayoutParams(new LayoutParams(TIP_WIDTH, TIP_HEIGHT))
	view.setX(x)
	view.setY(y)

	init()
	//init PicTextTip
	private def init() = {
		addClickEvent()
	}

	//click the the 
	def addClickEvent() = {
		view.setOnClickListener(new View.OnClickListener() {
	   		def onClick(v: View){
	   			Log.i("chxjia", "click tip")
	   			val tip_one = view.findViewById(R.id.tip_one)
	   			tip_one.setBackgroundColor(R.color.translucent_red)

	   			val tip_close = view.findViewById(R.id.close_tip_one)
	   			val tip_two = view.findViewById(R.id.tip_two)

	   			tip_one.setVisibility(View.GONE)
	   			tip_two.setVisibility(View.VISIBLE) 
	   		}
		})
	}

}
// class VoiceTip extends Tip{}
