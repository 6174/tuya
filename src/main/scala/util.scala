package com.cxj

import android.app.Activity
import android.os.Bundle
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log

/**
 *@desc utils for computing 
 */
object util {
	lazy val metrics = new DisplayMetrics()
	//compute dip2px
	def dip2px(ctx: Context, dip:Double): Int = {
		val scale = ctx.getResources().getDisplayMetrics().density
		(dip * scale + 0.5f).toInt
	} 

	def px2dip(ctx: Context, px:Double): Int = {
		val scale = ctx.getResources().getDisplayMetrics().density
		(px / scale + 0.5f).toInt
	}

	def screenWidth(ctx: Context):Int = {
		val context = ctx.asInstanceOf[Activity]
 		context.getWindowManager().getDefaultDisplay().getMetrics(metrics)
 		metrics.widthPixels
	}

	def screenHeight(ctx: Context):Int = {
		val context = ctx.asInstanceOf[Activity]
 		context.getWindowManager().getDefaultDisplay().getMetrics(metrics)
		metrics.heightPixels		
	}
}


// public static int dip2px(Context context, float dipValue){ 
//     final float scale = context.getResources().getDisplayMetrics().density; 
//     return (int)(dipValue * scale + 0.5f); 
// } 
        
// public static int px2dip(Context context, float pxValue){ 
//     final float scale = context.getResources().getDisplayMetrics().density; 
//     return (int)(pxValue / scale + 0.5f); 
// } 