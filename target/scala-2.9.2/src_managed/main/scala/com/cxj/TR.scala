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
  val pic_card_title_layout = TypedResource[android.widget.RelativeLayout](R.id.pic_card_title_layout)
  val home_content_layout = TypedResource[android.widget.LinearLayout](R.id.home_content_layout)
  val pic_user_name = TypedResource[android.widget.TextView](R.id.pic_user_name)
  val pictalk_bottom_reply_btn = TypedResource[android.widget.TextView](R.id.pictalk_bottom_reply_btn)
  val textviewasd = TypedResource[android.widget.TextView](R.id.textviewasd)
  val info_holder_layout = TypedResource[android.widget.LinearLayout](R.id.info_holder_layout)
  val right_holder = TypedResource[android.view.View](R.id.right_holder)
  val btn_add_text = TypedResource[android.widget.TextView](R.id.btn_add_text)
  val home_top_layout = TypedResource[android.widget.LinearLayout](R.id.home_top_layout)
  val login_password_filed = TypedResource[android.widget.EditText](R.id.login_password_filed)
  val pictalk_body_layout = TypedResource[android.widget.LinearLayout](R.id.pictalk_body_layout)
  val contacts_contact_list = TypedResource[android.widget.ListView](R.id.contacts_contact_list)
  val btn_open_gallary = TypedResource[android.widget.TextView](R.id.btn_open_gallary)
  val home__content_layout = TypedResource[android.widget.RelativeLayout](R.id.home__content_layout)
  val list_layout = TypedResource[android.widget.RelativeLayout](R.id.list_layout)
  val img = TypedResource[android.widget.ImageView](R.id.img)
  val index_login_btn = TypedResource[android.widget.TextView](R.id.index_login_btn)
  val close_tip_one = TypedResource[android.widget.TextView](R.id.close_tip_one)
  val home_bottom_layout = TypedResource[android.widget.LinearLayout](R.id.home_bottom_layout)
  val layout_top = TypedResource[android.widget.AbsoluteLayout](R.id.layout_top)
  val btn_add_voice = TypedResource[android.widget.TextView](R.id.btn_add_voice)
  val login_forget_password_btn = TypedResource[android.widget.TextView](R.id.login_forget_password_btn)
  val info = TypedResource[android.widget.TextView](R.id.info)
  val img_holder_layout = TypedResource[android.widget.LinearLayout](R.id.img_holder_layout)
  val pictalk_bottom_layout = TypedResource[android.widget.LinearLayout](R.id.pictalk_bottom_layout)
  val bottom_nav_contacts_btn = TypedResource[android.widget.RelativeLayout](R.id.bottom_nav_contacts_btn)
  val btn_open_camera = TypedResource[android.widget.TextView](R.id.btn_open_camera)
  val close_tip_two = TypedResource[android.widget.TextView](R.id.close_tip_two)
  val bottom_nav_home_btn = TypedResource[android.widget.RelativeLayout](R.id.bottom_nav_home_btn)
  val tip_two_content = TypedResource[android.widget.TextView](R.id.tip_two_content)
  val contacts_content_layout = TypedResource[android.widget.RelativeLayout](R.id.contacts_content_layout)
  val pictalk_list = TypedResource[android.widget.ListView](R.id.pictalk_list)
  val tip_two = TypedResource[android.widget.RelativeLayout](R.id.tip_two)
  val btn_add_info = TypedResource[android.widget.TextView](R.id.btn_add_info)
  val pic_body_layout = TypedResource[android.widget.AbsoluteLayout](R.id.pic_body_layout)
  val pic_card_info = TypedResource[android.widget.TextView](R.id.pic_card_info)
  val login_logo = TypedResource[android.widget.ImageView](R.id.login_logo)
  val tip_one = TypedResource[android.widget.AbsoluteLayout](R.id.tip_one)
  val login_name_filed = TypedResource[android.widget.EditText](R.id.login_name_filed)
  val index_regist_btn = TypedResource[android.widget.TextView](R.id.index_regist_btn)
  val textview = TypedResource[android.widget.TextView](R.id.textview)
  val home_bottom_icon_home = TypedResource[android.widget.ImageView](R.id.home_bottom_icon_home)
  val home_bottom_text_home = TypedResource[android.widget.TextView](R.id.home_bottom_text_home)
  val title = TypedResource[android.widget.TextView](R.id.title)
  val pic_user_img = TypedResource[android.widget.ImageView](R.id.pic_user_img)
  val pic_card_info_time = TypedResource[android.widget.TextView](R.id.pic_card_info_time)
  val home_dynamic_state_list = TypedResource[android.widget.ListView](R.id.home_dynamic_state_list)
  val login_form_layout = TypedResource[android.widget.LinearLayout](R.id.login_form_layout)
  val login_signup_btn = TypedResource[android.widget.TextView](R.id.login_signup_btn)
  val pictalk_top_bar_layout = TypedResource[android.widget.RelativeLayout](R.id.pictalk_top_bar_layout)
 object layout {
  val bottom_bar = TypedLayout(R.layout.bottom_bar)
 val main = TypedLayout(R.layout.main)
 val home_content = TypedLayout(R.layout.home_content)
 val confirm_dialog = TypedLayout(R.layout.confirm_dialog)
 val home = TypedLayout(R.layout.home)
 val login = TypedLayout(R.layout.login)
 val pic_card = TypedLayout(R.layout.pic_card)
 val pictalk = TypedLayout(R.layout.pictalk)
 val contacts_list_item = TypedLayout(R.layout.contacts_list_item)
 val text_tip = TypedLayout(R.layout.text_tip)
 val contacts_content = TypedLayout(R.layout.contacts_content)
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
