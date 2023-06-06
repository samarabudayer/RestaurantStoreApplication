package ucas.edu.android.productsstoreapplication;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;

public class Create_accountActivity extends AppCompatActivity {

    ArrayList<String> days_29 =new ArrayList<>() ;
    ArrayList<String> days_30 =new ArrayList<>() ;
    ArrayList<String> days_31 =new ArrayList<>() ;

    Spinner sp_day ;
    Spinner sp_month ;
    Spinner sp_year ;

    ImageButton btn_account_img ;

    ArrayList<String> yearsList = new ArrayList<>() ;
    ArrayList<String> monthList = new ArrayList<>() ;

    Uri img_uri = Uri.parse("");

    EditText et_fullname;
    EditText et_email;
    EditText et_username ;
    EditText et_rePassword;
    EditText et_password;
    EditText et_title;
    EditText et_country;
    EditText et_phone ;

    Button btn_create ;

    RadioButton rb_male ;
    RadioButton rb_female ;

    CheckBox cb_isAdmin ;

    Boolean username_is_used ;

    String user_img_uri = "" ;

    public static final String USERS_PREFERANCES = "UsersPreferences1" ;
    public static final String USERS_NUM = "UsersNum" ;
    public static final String ADDED_USER_USERNAME = "added_user_username" ;
    public static final String ADDED_USER_PASSWORD = "added_user_password" ;

    public static final int USERS_MALE = 1 ;
    public static final int USERS_FEMALE = 2 ;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        sp_day =findViewById(R.id.create_account_sp_day) ;
        sp_month=findViewById(R.id.create_account_sp_month) ;
        sp_year=findViewById(R.id.create_account_sp_year) ;
        btn_account_img = findViewById(R.id.create_account_img) ;

        et_fullname=findViewById(R.id.create_account_et_fullname) ;
        et_email=findViewById(R.id.create_account_et_email) ;
        et_username=findViewById(R.id.create_account_et_username) ;
        et_rePassword=findViewById(R.id.create_account_et_rePassword) ;
        et_password=findViewById(R.id.create_account_et_password) ;
        et_title=findViewById(R.id.create_account_et_title) ;
        et_country=findViewById(R.id.create_account_et_country) ;
        et_phone=findViewById(R.id.create_account_et_phone) ;

        btn_create =findViewById(R.id.create_account_btn_create) ;
        rb_male=findViewById(R.id.create_account_rb_male) ;
        rb_female=findViewById(R.id.create_account_rb_female) ;

        cb_isAdmin = findViewById(R.id.create_account_cb_isAdmin) ;

        SharedPreferences UsersPreferences = getSharedPreferences(USERS_PREFERANCES , MODE_PRIVATE) ;
        SharedPreferences.Editor UsersEditor  = UsersPreferences.edit() ;

        LocalDate today = LocalDate.now() ;

        yearsList.add("السنة") ;

        for (int i = today.getYear() ; i>=1920 ; i-- ){
            yearsList.add(i+"");
        }

        days_29.add("اليوم") ;
        for (int i = 1 ; i<=29 ; i++)
            days_29.add(i+"");

        days_30.add("اليوم") ;
        for (int i = 1 ; i<=30 ; i++)
            days_30.add(i+"");

        days_31.add("اليوم") ;
        for (int i = 1 ; i<=31 ; i++)
            days_31.add(i+"");

        monthList.add("الشهر") ;
        for (int i = 1 ; i<=12 ; i++)
            monthList.add(i+"");

        SpinnerAdapter adapter_29 = new SpinnerAdapter(days_29);
        SpinnerAdapter adapter_30 = new SpinnerAdapter(days_30);
        SpinnerAdapter adapter_31 = new SpinnerAdapter(days_31);

        sp_day.setAdapter(adapter_31);

        SpinnerAdapter yearAdapter = new SpinnerAdapter(yearsList) ;

        sp_year.setAdapter(yearAdapter);

        SpinnerAdapter monthAdapter = new SpinnerAdapter(monthList) ;
        sp_month.setAdapter(monthAdapter);

        sp_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selected_postion ;

                switch (position) {
                    case 2:
                        selected_postion = sp_day.getSelectedItemPosition() ;
                        sp_day.setAdapter(adapter_29);
                        if (selected_postion<29)
                            sp_day.setSelection(selected_postion);
                        else
                            sp_day.setSelection(29);
                        break;
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        selected_postion = sp_day.getSelectedItemPosition() ;
                        sp_day.setAdapter(adapter_31);
                        sp_day.setSelection(selected_postion);
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        selected_postion = sp_day.getSelectedItemPosition() ;
                        sp_day.setAdapter(adapter_30);
                        if (selected_postion<30)
                            sp_day.setSelection(selected_postion);
                        else
                            sp_day.setSelection(30);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        if(ContextCompat.checkSelfPermission(getBaseContext() , Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions
                    (Create_accountActivity.this , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE } , 10);}


        btn_account_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent get_image = new Intent(Intent.ACTION_PICK) ;
                get_image.setType("image/png" );
                if(ContextCompat.checkSelfPermission(getBaseContext() , Manifest.permission.WRITE_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED){
                    startActivityForResult(get_image , 2);}
            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {

            String user_fullname ;
            String user_email ;
            String user_username ;
            String user_password ;
            String user_repassword ;
            String user_country ;
            String user_title ;
            String user_phone ;
            String user_birth ;
            Boolean user_isAdmin ;
            int user_gender ;


            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                user_fullname = et_fullname.getText().toString() ;
                user_email =  et_email.getText().toString();
                user_username = et_username.getText().toString() ;
                user_password =  et_password.getText().toString();
                user_repassword = et_rePassword.getText().toString() ;
                user_country =  et_country.getText().toString() ;
                user_title = et_title.getText().toString() ;
                user_phone = et_phone.getText().toString() ;
                user_birth = sp_day.getAdapter().getItem(sp_day.getSelectedItemPosition())
                        +"/"+sp_month.getAdapter().getItem(sp_month.getSelectedItemPosition())
                        +"/"+sp_year.getAdapter().getItem(sp_year.getSelectedItemPosition()) ;

                if (rb_male.isChecked())
                {user_gender = USERS_MALE;}
                else if (rb_female.isChecked())
                {user_gender  = USERS_FEMALE;}

                user_isAdmin = cb_isAdmin.isChecked() ;

                if (
                        user_fullname.equals("")    ||
                                user_username.equals("")    ||
                                user_password.equals("")    ||
                                user_repassword.equals("")  ||
                                sp_day.getSelectedItemPosition() == 0   ||
                                sp_month.getSelectedItemPosition() == 0 ||
                                sp_year.getSelectedItemPosition() == 0  ||














                                (!rb_female.isChecked()  && !rb_male.isChecked())
                ){
                    Toast.makeText
                            (Create_accountActivity.this,  "الرجاء التأكد من إدخال كل البيانات" , Toast.LENGTH_LONG).show();
                }
                else{
                    if (!user_password.equals(user_repassword)){
                        Toast.makeText(Create_accountActivity.this, "قم بإعادة إدخال كلمة المرور بشكل صحيح", Toast.LENGTH_LONG).show();
                        et_rePassword.setText("");
                        et_rePassword.setHintTextColor(Color.parseColor("#aaff0000"));
                    }
                    else {
                        et_rePassword.setHintTextColor(Color.parseColor("#9C9C9C"));
                        et_username.setHintTextColor(Color.parseColor("#9C9C9C"));

                        username_is_used = false ;

                        for (int i = 0 ; i<=UsersPreferences.getInt(USERS_NUM , 0) ; i++){
                            if
                            (UsersPreferences.getString("u"+ i +"_username"  , "No_User").equals(user_username))
                            {username_is_used = true ;}
                        }
                        if (!username_is_used) {

                            UsersEditor.putInt(USERS_NUM, UsersPreferences.getInt(USERS_NUM, 0) + 1);
                            UsersEditor.apply();

                            UsersEditor.putInt("u" + UsersPreferences.getInt(USERS_NUM, 0) + "_id", UsersPreferences.getInt(USERS_NUM, 0));
                            UsersEditor.putString("u" + UsersPreferences.getInt(USERS_NUM, 0) + "_fullname", user_fullname);
                            UsersEditor.putString("u" + UsersPreferences.getInt(USERS_NUM, 0) + "_email", user_email);
                            UsersEditor.putString("u" + UsersPreferences.getInt(USERS_NUM, 0) + "_username", user_username);
                            UsersEditor.putString("u" + UsersPreferences.getInt(USERS_NUM, 0) + "_password", user_password);
                            UsersEditor.putString("u" + UsersPreferences.getInt(USERS_NUM, 0) + "_country", user_country);
                            UsersEditor.putString("u" + UsersPreferences.getInt(USERS_NUM, 0) + "_title", user_title);
                            UsersEditor.putString("u" + UsersPreferences.getInt(USERS_NUM, 0) + "_birth", user_birth);
                            UsersEditor.putString("u" + UsersPreferences.getInt(USERS_NUM, 0) + "_phone", user_phone);
                            UsersEditor.putInt("u" + UsersPreferences.getInt(USERS_NUM, 0) + "_gender", user_gender);
                            UsersEditor.putBoolean("u" + UsersPreferences.getInt(USERS_NUM, 0) + "_isAdmin", user_isAdmin);

                            if (!user_img_uri.equals("")) {
                                String[] file_path_cln = {MediaStore.Images.Media.DATA};
                                Cursor cursor = getContentResolver().query(img_uri, file_path_cln, null, null , null);
                                if (cursor.moveToFirst()){
                                    @SuppressLint("Range") String imgPath = cursor.getString(cursor.getColumnIndex(file_path_cln[0])) ;
                                    cursor.close();
                                    UsersEditor.putString("u" + UsersPreferences.getInt(USERS_NUM, 0) + "_imgUri", imgPath);
                                }
                            }
                            else {
                                UsersEditor.putString("u" + UsersPreferences.getInt(USERS_NUM, 0) + "_imgUri", user_img_uri);
                            }

                            UsersEditor.apply();


                            Toast.makeText
                                    (Create_accountActivity.this, "أهلاً بك : " + UsersPreferences.getString("u" + UsersPreferences.getInt(USERS_NUM, 0) + "_fullname", "No_User"), Toast.LENGTH_SHORT).show();

                            Intent goSign_in = new Intent(getBaseContext() , Sign_inActivity.class) ;

                            goSign_in.putExtra(ADDED_USER_USERNAME , user_username) ;
                            goSign_in.putExtra(ADDED_USER_PASSWORD , user_password) ;

                            startActivity(goSign_in);
                        }
                        else {
                            Toast.makeText(Create_accountActivity.this, "اسم المستخدم الذي أدخلته غير متاح حاليا", Toast.LENGTH_LONG).show();
                            et_username.setText("");
                            et_username.setHintTextColor(Color.parseColor("#aaff0000"));
                        }
                    }
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== 2 && resultCode == RESULT_OK){
            img_uri = data.getData() ;
            user_img_uri = String.valueOf(img_uri) ;
            btn_account_img.setImageURI(img_uri);
        }
        else if (requestCode== 2 && resultCode == RESULT_CANCELED){
            Toast.makeText(getApplicationContext() , "No image selected" , Toast.LENGTH_SHORT).show() ;
        }
    }
}
