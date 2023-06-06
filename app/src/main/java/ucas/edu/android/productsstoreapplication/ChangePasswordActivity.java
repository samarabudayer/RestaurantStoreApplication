package ucas.edu.android.productsstoreapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {

    ImageButton btn_go_home ;
    EditText et_oldPassword;
    EditText et_newPassword;
    EditText et_reNewPassword;

    CheckBox cb_areYouSure ;
    Button btn_change ;

    Toast tst_btnChange_pressed ;
    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        btn_go_home = findViewById(R.id.btn_go_home) ;
        et_oldPassword =findViewById(R.id.change_password_oldpassword);
        et_newPassword=findViewById(R.id.change_password_newpassword);
        et_reNewPassword=findViewById(R.id.change_password_reNewpassword);
        cb_areYouSure = findViewById(R.id.change_password_checkbox) ;
        btn_change = findViewById(R.id.change_password_btn_change) ;

        SharedPreferences UsersPreferences = getSharedPreferences(Create_accountActivity.USERS_PREFERANCES , MODE_PRIVATE) ;
        SharedPreferences.Editor UsersEditor  = UsersPreferences.edit() ;

        tst_btnChange_pressed = new Toast(getBaseContext()) ;

        btn_go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_home = new Intent(getBaseContext() , MainActivity.class) ;
                startActivity(go_home);
            }
        });


        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entered_oldPass = et_oldPassword.getText().toString() ;
                String entered_newPass = et_newPassword.getText().toString() ;
                String entered_reNewPass = et_reNewPassword.getText().toString() ;
                int signed_userId = UsersPreferences.getInt(Sign_inActivity.SIGNED_USER_ID , 0) ;

                String user_oldPass = UsersPreferences.getString("u" + signed_userId + "_password" , "") ;

                cb_areYouSure.setBackgroundResource(R.drawable.check_background);


                if (entered_oldPass.equals("") ||
                        entered_newPass.equals("") ||
                        entered_reNewPass.equals("")
                ){
                    toastTextChanhe_Show("قم بإدخال كل البيانات من فضلك");

                    entering_erorr(et_reNewPassword);
                    entering_erorr(et_newPassword);
                    entering_erorr(et_oldPassword);
                    check_box_is_unCheck();

                    et_oldPassword.setText(entered_oldPass);
                    et_newPassword.setText(entered_newPass);
                    et_reNewPassword.setText(entered_reNewPass);
                }
                else{
                    if (cb_areYouSure.isChecked()){
                        if (entered_oldPass.equals(user_oldPass) && !entered_oldPass.equals(entered_newPass)){
                            if (entered_newPass.equals(entered_reNewPass)){
                                UsersEditor.putString("u" + signed_userId + "_password" , entered_newPass) ;
                                UsersEditor.apply();

                                et_oldPassword.setText("");
                                et_newPassword.setText("");
                                et_reNewPassword.setText("");
                                cb_areYouSure.setChecked(false);

                                toastTextChanhe_Show("تم تغيير كلمة المرور بنجاح");
                            }
                            else{
                                toastTextChanhe_Show("قم بإعادة إدخال كلمة المرور بشكل صحيح");
                                entering_erorr(et_reNewPassword);
                            }
                        }
                        else if(!entered_oldPass.equals(user_oldPass)){
                            toastTextChanhe_Show("كلمة المرور القديمة التي أدخلتها غير صحيحة");
                            entering_erorr(et_oldPassword);
                        }
                        else if(entered_oldPass.equals(entered_newPass)){
                            toastTextChanhe_Show("كلمة المرور الجديدة لا يمكن \n  أن تكون نفس كلمة المرور القديمة");
                            entering_erorr(et_newPassword);
                            entering_erorr(et_reNewPassword);
                        }
                    }
                    else{
                        toastTextChanhe_Show("اضغط على مربع ال ✔ من فضلك");
                        check_box_is_unCheck();
                        cb_areYouSure.setChecked(true);
                    }
                }
            }
        });
    }

    // دوال خاصة
    void toastTextChanhe_Show(String text){
        tst_btnChange_pressed.cancel();
        tst_btnChange_pressed = Toast.makeText(getBaseContext() ,text , Toast.LENGTH_LONG);
        tst_btnChange_pressed.show();
    }

    void entering_erorr(EditText editText){
        editText.setText("");
        editText.setHintTextColor(Color.parseColor("#aaff0000"));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                editText.setHintTextColor(Color.parseColor("#9C9C9C"));
            }
        },5000) ;
    }

    void check_box_is_unCheck(){
        cb_areYouSure.setBackgroundResource(R.drawable.check_red_background);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cb_areYouSure.setBackgroundResource(R.drawable.check_background);
            }
        },5000) ;
    }
}