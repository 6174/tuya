
	/**
	 *@DESC make view draggable
	 */
	private def make_view_draggable(view: View) {
    view.setOnTouchListener(new View.OnTouchListener() {
      var pre_evx = 0
      var pre_evy = 0
      var cur_evx = 0
      var cur_evy = 0
      def onTouch(v: View, event: MotionEvent): Boolean = {
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

          //v.getLeft() postion relative to It's parent
          // Log.i("chxjia", v.getLeft())
          v.setX(deltaX + v.getX())
          v.setY(deltaY + v.getY())
        }
        def action_touchend = {
          // Log.i("chxjia", "touchend")
        }
        
        true
      }
    })
  }