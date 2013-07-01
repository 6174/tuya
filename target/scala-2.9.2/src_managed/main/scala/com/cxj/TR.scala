package com.cxj
import _root_.android.app.{Activity, Dialog}
import _root_.android.view.View

case class TypedResource[T](id: Int)
case class TypedLayout(id: Int)

object TR {
  val index_body_layout = TypedResource[android.widget.AbsoluteLayout](R.id.index_body_layout)
  val sendBtn = TypedResource[android.widget.TextView](R.id.sendBtn)
  val textviewasd = TypedResource[android.widget.TextView](R.id.textviewasd)
  val btn_add_text = TypedResource[android.widget.TextView](R.id.btn_add_text)
  val index_login_btn = TypedResource[android.widget.TextView](R.id.index_login_btn)
  val layout_top = TypedResource[android.widget.AbsoluteLayout](R.id.layout_top)
  val openImageGallaryBtn = TypedResource[android.widget.TextView](R.id.openImageGallaryBtn)
  val btn_add_voice = TypedResource[android.widget.TextView](R.id.btn_add_voice)
  val btn_open_camera = TypedResource[android.widget.TextView](R.id.btn_open_camera)
  val btn_add_info = TypedResource[android.widget.TextView](R.id.btn_add_info)
  val pic_body_layout = TypedResource[android.widget.AbsoluteLayout](R.id.pic_body_layout)
  val index_regist_btn = TypedResource[android.widget.TextView](R.id.index_regist_btn)
  val textview = TypedResource[android.widget.TextView](R.id.textview)
 object layout {
  val main = TypedLayout(R.layout.main)
 val confirm_dialog = TypedLayout(R.layout.confirm_dialog)
 val index = TypedLayout(R.layout.index)
 }
}
trait TypedViewHolder {
  def findViewById( id: Int ): View
  def findView[T](tr: TypedResource[T]) = findViewById(tr.id).asInstanceOf[T]
}
trait TypedView extends View with TypedViewHolder
trait TypedActivityHolder extends TypedViewHolder
trait TypedActivity extends Activity with TypedActivityHolder
trait TypedDialog extends Dialog with TypedViewHolder
object TypedResource {
  implicit def layout2int(l: TypedLayout) = l.id
  implicit def view2typed(v: View) = new TypedViewHolder { 
    def findViewById( id: Int ) = v.findViewById( id )
  }
  implicit def activity2typed(a: Activity) = new TypedViewHolder { 
    def findViewById( id: Int ) = a.findViewById( id )
  }
  implicit def dialog2typed(d: Dialog) = new TypedViewHolder { 
    def findViewById( id: Int ) = d.findViewById( id )
  }
}
