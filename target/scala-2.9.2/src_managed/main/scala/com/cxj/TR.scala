package com.cxj
import _root_.android.app.{Activity, Dialog}
import _root_.android.view.View

case class TypedResource[T](id: Int)
case class TypedLayout(id: Int)

object TR {
  val login_login_btn = TypedResource[android.widget.TextView](R.id.login_login_btn)
  val index_body_layout = TypedResource[android.widget.AbsoluteLayout](R.id.index_body_layout)
  val left_holder1 = TypedResource[android.view.View](R.id.left_holder1)
  val sendBtn = TypedResource[android.widget.TextView](R.id.sendBtn)
  val home_content_layout = TypedResource[android.widget.LinearLayout](R.id.home_content_layout)
  val textviewasd = TypedResource[android.widget.TextView](R.id.textviewasd)
  val right_holder = TypedResource[android.view.View](R.id.right_holder)
  val btn_add_text = TypedResource[android.widget.TextView](R.id.btn_add_text)
  val home_top_layout = TypedResource[android.widget.LinearLayout](R.id.home_top_layout)
  val login_password_filed = TypedResource[android.widget.EditText](R.id.login_password_filed)
  val list_layout = TypedResource[android.widget.LinearLayout](R.id.list_layout)
  val index_login_btn = TypedResource[android.widget.TextView](R.id.index_login_btn)
  val home_bottom_layout = TypedResource[android.widget.LinearLayout](R.id.home_bottom_layout)
  val layout_top = TypedResource[android.widget.AbsoluteLayout](R.id.layout_top)
  val openImageGallaryBtn = TypedResource[android.widget.TextView](R.id.openImageGallaryBtn)
  val btn_add_voice = TypedResource[android.widget.TextView](R.id.btn_add_voice)
  val login_forget_password_btn = TypedResource[android.widget.TextView](R.id.login_forget_password_btn)
  val btn_open_camera = TypedResource[android.widget.TextView](R.id.btn_open_camera)
  val btn_add_info = TypedResource[android.widget.TextView](R.id.btn_add_info)
  val pic_body_layout = TypedResource[android.widget.AbsoluteLayout](R.id.pic_body_layout)
  val login_logo = TypedResource[android.widget.ImageView](R.id.login_logo)
  val login_name_filed = TypedResource[android.widget.EditText](R.id.login_name_filed)
  val index_regist_btn = TypedResource[android.widget.TextView](R.id.index_regist_btn)
  val textview = TypedResource[android.widget.TextView](R.id.textview)
  val home_bottom_icon_home = TypedResource[android.widget.ImageView](R.id.home_bottom_icon_home)
  val home_bottom_text_home = TypedResource[android.widget.TextView](R.id.home_bottom_text_home)
  val title = TypedResource[android.widget.TextView](R.id.title)
  val home_dynamic_state_list = TypedResource[android.widget.ListView](R.id.home_dynamic_state_list)
  val login_form_layout = TypedResource[android.widget.LinearLayout](R.id.login_form_layout)
  val login_signup_btn = TypedResource[android.widget.TextView](R.id.login_signup_btn)
 object layout {
  val main = TypedLayout(R.layout.main)
 val confirm_dialog = TypedLayout(R.layout.confirm_dialog)
 val home = TypedLayout(R.layout.home)
 val login = TypedLayout(R.layout.login)
 val index = TypedLayout(R.layout.index)
 val home_dynamic_state_list_item = TypedLayout(R.layout.home_dynamic_state_list_item)
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
