package ucas.edu.android.productsstoreapplication;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddNew_ItemActivity extends AppCompatActivity {

    ImageButton btn_go_home ;
    ImageButton btn_image ;
    Button btn_addNewItem ;

    EditText et_productName;
    EditText et_productPrice;
    EditText et_productDescription ;

    RadioButton rb_cash ;
    RadioButton rb_installment ;

    Uri img_uri = Uri.parse("");

    Toast tst_btnAdd_pressed ;

    RadioGroup radioGroup ;

    ProjectDatabase db ;

    public static final int PAYING_BOTH = 0 ;
    public static final int PAYING_CASH = 1 ;
    public static final int PAYING_INSTALLMENT = 2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);


        btn_go_home = findViewById(R.id.btn_go_home) ;
        btn_image = findViewById(R.id.add_newItem_btn_image) ;
        btn_addNewItem = findViewById(R.id.add_newItem_btn_add) ;

        et_productName=findViewById(R.id.add_newItem_et_product_name) ;
        et_productPrice=findViewById(R.id.add_newItem_et_product_price) ;
        et_productDescription=findViewById(R.id.add_newItem_et_description) ;
        rb_cash=findViewById(R.id.add_newItem_radio_cash) ;
        rb_installment=findViewById(R.id.add_newItem_radio_installment) ;
        radioGroup = findViewById(R.id.add_newItem_radioGroup) ;


        tst_btnAdd_pressed = new Toast(getBaseContext()) ;

        db = new ProjectDatabase(getBaseContext()) ;


        if(ContextCompat.checkSelfPermission(getBaseContext() , Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions
                    (this , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE } , 10);}


        btn_go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_home = new Intent(getBaseContext() , MainActivity.class) ;
                startActivity(go_home);
            }
        });

        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent get_image = new Intent(Intent.ACTION_PICK) ;
                get_image.setType("image/png" );

                if(ContextCompat.checkSelfPermission(getBaseContext() , Manifest.permission.WRITE_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED){
                    startActivityForResult(get_image , 2); }
            }
        });

        btn_addNewItem.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View v) {
                String product_name = et_productName.getText().toString();
                String product_price = et_productPrice.getText().toString() ;
                String product_description = et_productDescription.getText().toString();
                String product_uri = String.valueOf(img_uri);
                String product_imgPath ="" ;

                if (
                        product_name.equals("") ||
                                product_price.equals("") ||
                                (!rb_cash.isChecked() && !rb_installment.isChecked())
                ){
                    toastTextChanhe_Show("الرجاء إدخال كامل البيانات ");
                    entering_erorr(et_productName);
                    entering_erorr(et_productPrice);

                    if (!rb_cash.isChecked() && !rb_installment.isChecked())
                        radioButton_is_unCheck();
                    et_productName.setText(product_name);
                    et_productPrice.setText(product_price);
                }
                else{
                    int paying_type =0;
                    if (rb_cash.isChecked()){ paying_type = PAYING_CASH ; }
                    else if(rb_installment.isChecked()){ paying_type = PAYING_INSTALLMENT ; }

                    if (!product_uri.equals("")){
                        String[] path_column = {MediaStore.Images.Media.DATA} ;
                        Cursor c = getContentResolver().query(Uri.parse(product_uri) , path_column , null , null , null);
                        if(c.moveToFirst()){
                            product_imgPath = c.getString(c.getColumnIndex(path_column[0])) ;
                            c.close();}}


                    Product_db_obj obj = new Product_db_obj(product_name , Double.parseDouble(product_price) , paying_type , product_imgPath , product_description) ;
                    long i =  db.insertProduct(obj) ;
                    if (i > -1){
                        toastTextChanhe_Show("تم حفظ المنتج بنجاح");
                        et_productPrice.setText("");
                        et_productName.setText("");
                        et_productDescription.setText("");
                        btn_image.setImageResource(R.drawable.ic_cloche);
                        rb_installment.setChecked(false);
                        rb_cash.setChecked(false);
                    }
                    else {
                        toastTextChanhe_Show("حدث خطأ في عملية حفظ المنتج");
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
            btn_image.setImageURI(img_uri);
        }
        else if (requestCode== 2 && resultCode == RESULT_CANCELED){
            Toast.makeText(getApplicationContext() , "No image selected" , Toast.LENGTH_SHORT).show() ;
        }
    }
    void toastTextChanhe_Show(String text){
        tst_btnAdd_pressed.cancel();
        tst_btnAdd_pressed = Toast.makeText(getBaseContext() ,text , Toast.LENGTH_LONG);
        tst_btnAdd_pressed.show();
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

    void radioButton_is_unCheck(){
        rb_cash.setBackgroundResource(R.drawable.check_red_background);
        rb_installment.setBackgroundResource(R.drawable.check_red_background);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rb_cash.setBackgroundResource(R.drawable.check_background);
                rb_installment.setBackgroundResource(R.drawable.check_background);
            }
        },5000) ;
    }
    void Image_EnteringErorr(){
        btn_image.setBackgroundResource(R.drawable.sitting_img_red_background);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                btn_image.setBackgroundResource(R.drawable.sitting_img_background);
            }
        },5000) ;
    }

}