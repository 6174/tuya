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

import android.media.MediaRecorder
// import android.media.MediaRecorder.AudioEncoder
import android.media.MediaPlayer
import android.app.AlertDialog
import android.os.Handler
import android.os.Message

import java.io.FileNotFoundException
import java.io.IOException
import java.io.File
import java.util.ArrayList
import java.util.Timer
import java.util.TimerTask
import java.util.Date

import com.cxj.util._
/**
 *@DESC audio recoder
 */
class AudioRecorder(val context:Context, anchor:View, audio_path:String) {
	//Attrs
	var mr:MediaRecorder = null
	var mp:MediaPlayer = null
	var path = ""
	var startPlay = false
	var startRecord = false

	var startRecordTime:Date = null
	var startPlayTime:Date = null

	var trushed = false

	//UI elements
	lazy val container = LayoutInflater.from(context).inflate(R.layout.audio_recorder, null) 
	lazy val playBtn = container.findViewById(R.id.play_btn).asInstanceOf[Button]
	lazy val recordBtn = container.findViewById(R.id.record_btn).asInstanceOf[Button]
	lazy val trushBtn = container.findViewById(R.id.trush_btn).asInstanceOf[Button]
	lazy val dismissBtn = container.findViewById(R.id.dismiss_btn).asInstanceOf[Button]
	lazy val audioTimeText = container.findViewById(R.id.audio_time).asInstanceOf[TextView]
	lazy val playProgressText = container.findViewById(R.id.play_progress).asInstanceOf[TextView]
	var popUpWin = {
		val win = new PopupWindow(container, -1, -1, true)
		win.setFocusable(true)
		win.setOutsideTouchable(true)
		win
	}

	//UI thread handler
	val audioRecorderHandler = new Handler(){
		override def handleMessage(msg: Message){
			Log.i("chxjia","handler")
			def getDiffDateString(start:Date, end:Date):String = {
				val startTime = start.getTime()
				val endTime = end.getTime()
				var deltaTime = endTime - startTime

				def fill(num:Double):String = {
					if(num >= 10) num.toInt.toString
					else "0" + num.toInt.toString
				}
				val hours = fill(deltaTime / (3600*1000))
				deltaTime %= (3600*1000)

				val minutes = fill(deltaTime / 60000)
				deltaTime %= 60000

				val seconds = fill(deltaTime / 1000)
				deltaTime %= 1000
				val mseconds = fill(deltaTime)

				hours + ":" + minutes + ":" + seconds
				// + ":" + mseconds
			}
			msg.what match{
				case 1 => audioTimeText.setText(getDiffDateString(startRecordTime, new Date()))
				case 2 => Log.i("chxjia", "2")
				case _ => false
			}
			super.handleMessage(msg)
		}
	}

	var recordTimer:Timer = null

	//setup events mediarecorder
	private def setupMr() = {
		try {
			setPath()
			mr = new MediaRecorder()
			mr.setAudioSource(1)//MediaRecorder.AudioSource.MIC)
			mr.setOutputFormat(1)//MediaRecorder.OutputFormat.THREE_GPP)
			mr.setOutputFile(path)
			mr.setAudioEncoder(1)//MediaRecorder.AudioEncoder.AMR_NB)
		}catch{
			case e => {
					Log.i("chxjia", e.getMessage().toString)
					error("something wrong")
				}
		}
	}

	//set event handlers
	private def setEventHandlers() = {
		recordBtn.setOnClickListener(new View.OnClickListener(){
			def onClick(view: View){
				Log.i("chxjia", "click record Btn")
				onRecord()		
				trushed = false
			}
		})
		playBtn.setOnClickListener(new View.OnClickListener(){
			def onClick(view: View){
				Log.i("chxjia", "click play Btn")
				onPlay()		
			}
		})
		dismissBtn.setOnClickListener(new View.OnClickListener() {
     		def onClick(view: View)  {
     			if(!trushed) onSave(path)
     			popUpWin.dismiss()
     			popUpWin = null
     		}
     	})
     	trushBtn.setOnClickListener(new View.OnClickListener(){
     		def onClick(view: View) {
     			onTrush()
     			trushed = true
     			setDefaultVoiceText()
     			recordBtn.setEnabled(true)
     			playBtn.setEnabled(false)
     		}	
 		})
	}
	//setpath
	private def setPath() = {
		Log.i("chxjia", path)
		val state = android.os.Environment.getExternalStorageState()
		if(state != android.os.Environment.MEDIA_MOUNTED){
			throw new IOException("chxjia: no external storage statecode:" + state.toString)
		}
		val dir = new File(path).getParentFile()
		if(!dir.exists() && !dir.mkdirs()){
			throw new IOException("chxjia: can't create file")
		}
	}

	private def onRecord(){
		if(startRecord){
			stopRecording()
		}else{
			startRecording()
		}
		startRecord = !startRecord
	}
	
	//record
	private def startRecording() {
		try{
			mr.prepare()
		}catch{
			case e => Log.i("chxjia", e.getMessage().toString)
		}
		mr.start()
		recordTimer = new Timer()
		startRecordTime = new Date()
		val recordTask = new TimerTask(){
			def run(){
				val message = new Message()
				message.what = 1
				audioRecorderHandler.sendMessage(message)
			}
		}
		recordTimer.schedule(recordTask, 0, 1000/60)
		recordBtn.setText("stop")
	}
	//stop
	private def stopRecording() {
		mr.stop()
		recordBtn.setText("Record")
		recordBtn.setEnabled(false)
		playBtn.setEnabled(true)
		mr.release()
		recordTimer.cancel()
		recordTimer = null
	}
    private def onPlay() {  
        if (startPlay) {  
            stopPlaying() 
        } else {  
            startPlaying()  
        }  
        startPlay = !startPlay
    }  
    private def startPlaying() {  
        mp = new MediaPlayer();  
        try {  
            mp.setDataSource(path) 
            mp.prepare()
            mp.start()
            playBtn.setText("stop")
        } catch{  
            case e => Log.e("chxjia", "prepare() failed")  
        }  
    }  
    private def stopPlaying() {  
        mp.release() 
        mp = null
        playBtn.setText("play")
    } 

    def setDefaultVoiceText(){
    	audioTimeText.setText("00:00:00")
    }
    def setProgressTime() = {}
    def onSave(path:String) = {}
    def onTrush() = {}
	//error
	private def error(str:String) = {
		val builder = new AlertDialog.Builder(context)
		builder.setTitle("Error").setMessage(str).setPositiveButton("sure", null).show()
	}

	def show(){
		init()
		setupMr()
		setEventHandlers()
		popUpWin.showAtLocation(anchor, Gravity.CENTER, 0, 0)
	}

	def init(){
		path = audio_path
		Log.i("chxjia", "initAudioPath:" + path + ";")
		if(path != ""){
			recordBtn.setEnabled(false)
			playBtn.setEnabled(true)
		}else{
			path = audio_path
			if(path == ""){
				val time = (new Date()).getTime()
				path = "/"+ guid() + time +".mp3"
				path = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + path
			}
			playBtn.setEnabled(false)
			recordBtn.setEnabled(true)
		}
	}
}