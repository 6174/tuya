
package com.cxj

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.content.Context
import android.net.Uri
import android.graphics.BitmapFactory
import android.view._
import android.support.v4.view._
import android.widget._
import android.util.Log
import java.io.FileNotFoundException

import com.cxj._
import com.cxj.util._

import br.com.dina.ui.widget.UITableView
import br.com.dina.ui.widget.UITableView.ClickListener


class SettingContent(val context:Context) {

	val ctx = context.asInstanceOf[homeActivity]
	lazy val content = ctx.findView(TR.setting_content)
	lazy val tableView = ctx.findView(TR.setting_tableView)
	// lazy val tableView = content.findViewById(R.id.setting_tableView)
	// val tableView = ctx.findViewById(R.id.setting_tableView).asInstanceOf[]
	var isShow = false
	// init()

	def init() {
		createList()	    
	  tableView.commit()
	}

	 /**
   *@DESC show or hide a content
   */
  def toggle()= {
    if (isShow){
      content.setVisibility(View.GONE)
      isShow = false
    }else {
      content.setVisibility(View.VISIBLE)
      isShow = true
    }
  }

 	def show() = {
 		if (!isShow){
 			content.setVisibility(View.VISIBLE)
 			isShow = true
 		}
 	}

 	def hide() = {
 		if(isShow){
 			content.setVisibility(View.GONE)
 			isShow = false
 		}
 	}


	private def createList() {
		val listener = new CustomClickListener()
  	tableView.setClickListener(listener)
  	tableView.addBasicItem("Example 1", "Summary text 1")
  	tableView.addBasicItem("Example 2", "Summary text 2")
  	tableView.addBasicItem("Example 3", "Summary text 3")
  	tableView.addBasicItem("Example 4", "Summary text 4")
  	tableView.addBasicItem("Example 5", "Summary text 5")
  	tableView.addBasicItem("Example 6", "Summary text 6")
  	tableView.addBasicItem("Example 7", "Summary text 7")
  	tableView.addBasicItem("Example 8", "Summary text 8") 
  	tableView.addBasicItem("Example 9", "Summary text 9")
  	tableView.addBasicItem("Example 10", "Summary text 10")
  	tableView.addBasicItem("Example 11")
	}
	
	private class CustomClickListener extends ClickListener {
		def onClick(index:Int) {
			Toast.makeText(context, "item clicked: " + index, Toast.LENGTH_SHORT).show();
		}
  }
}