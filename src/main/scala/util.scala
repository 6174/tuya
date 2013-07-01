package com.cxj

import _root_.android.app.Activity
import _root_.android.os.Bundle
import _root_.android.content.Context

/**
 *@desc utils for computing 
 */
object util {
	//compute dip2px
	def dip2px(ctx: Context, dip:Double): Int = {
		val scale = ctx.getResources().getDisplayMetrics().density
		(dip * scale + 0.5f).toInt
	} 

	def px2dip(ctx: Context, px:Double): Int = {
		val scale = ctx.getResources().getDisplayMetrics().density
		(px / scale + 0.5f).toInt
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