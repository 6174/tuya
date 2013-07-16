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

class TextEditor(val context:Context, anchor:View){
	var text = ""
	lazy val container = LayoutInflater.from(context).inflate(R.layout.text_tip_popupwindow, null) 
	lazy val editText = container.findViewById(R.id.tip_textarea).asInstanceOf[EditText]
	lazy val okBtn = container.findViewById(R.id.ok_button)
	var popUpWin = {
		val win = new PopupWindow(container, -1, -1, true)
		win.setFocusable(true)
		win.setOutsideTouchable(true)
		win
	}

	private def setEventHandlers() = {
		okBtn.setOnClickListener(new View.OnClickListener(){
			def onClick(view: View){
				save()
				dismiss()
			}
		})
	}

	def onSave() = {}

	private def save(){
		text = editText.getText().toString
		onSave()
	}

	def setText(str:String){
		text = str
		editText.setText(str)
	}

	def dismiss(){
		popUpWin.dismiss()
		popUpWin = null
	}

	def show(){
		setEventHandlers()
		popUpWin.showAtLocation(anchor, Gravity.CENTER, 0, 0)
	}

}