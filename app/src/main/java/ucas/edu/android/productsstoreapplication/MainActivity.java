package ucas.edu.android.productsstoreapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView main_recyclerView ;
    EditText et_search ;
    ImageButton main_btn_sitting ;

    RadioButton rb_in_installments ;
    RadioButton rb_cash ;

    int back_pressed_times = 0 ;

    Toast back_toast ;

    ProjectDatabase db ;

    ArrayList<Product_db_obj> data ;
    Main_adapter my_adapter ;

    Toast tst_radio_pressed ;

    public static MediaPlayer mp ;

    SharedPreferences DefultPrefernce ;


    public MainActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_recyclerView = findViewById(R.id.main_recyclerView) ;
        et_search = findViewById(R.id.main_edittext_search) ;
        main_btn_sitting = findViewById(R.id.main_btn_sitting) ;
        rb_cash = findViewById(R.id.main_radio_cash) ;
        rb_in_installments = findViewById(R.id.main_radio_in_installments) ;

        data = new ArrayList<>() ;

        db = new ProjectDatabase(getBaseContext()) ;

        back_toast = new Toast(getBaseContext()) ;

        tst_radio_pressed = new Toast(getBaseContext()) ;

        DefultPrefernce = PreferenceManager.getDefaultSharedPreferences(this) ;

        mp = MediaPlayer.create(getBaseContext() , R.raw.background_music) ;
        mp.setLooping(true);

        sound_player();


        data = db.getAllProducts() ;
        my_adapter = new Main_adapter(data);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this , 2) ;
        main_recyclerView.setHasFixedSize(true);
        main_recyclerView.setLayoutManager(layoutManager);
        main_recyclerView.setAdapter(my_adapter);


        main_btn_sitting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goSitting = new Intent(getBaseContext() , SittingActivity.class) ;
                startActivity(goSitting);
                finish();
            }
        });


        et_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String searched_product = et_search.getText().toString().trim() ;
                int paying_type = AddNew_ItemActivity.PAYING_BOTH ;
                if (rb_cash.isChecked())
                { paying_type = AddNew_ItemActivity.PAYING_CASH ; }
                else if (rb_in_installments.isChecked())
                { paying_type = AddNew_ItemActivity.PAYING_INSTALLMENT ; }

                data = db.getProductsWhereLike(searched_product , paying_type) ;
                my_adapter.setData(data);
                return false ;
            }
        });

        rb_cash.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                rb_cash.setChecked(false);
                rb_in_installments.setChecked(false);
                String searched_product = et_search.getText().toString().trim() ;
                data = db.getProductsWhereLike(searched_product , AddNew_ItemActivity.PAYING_BOTH) ;
                my_adapter.setData(data);
                return true;
            }
        });

        rb_in_installments.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                rb_in_installments.setChecked(false);
                rb_cash.setChecked(false);
                String searched_product = et_search.getText().toString().trim() ;
                data = db.getProductsWhereLike(searched_product , AddNew_ItemActivity.PAYING_BOTH) ;
                my_adapter.setData(data);
                return true;
            }
        });

        rb_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searched_product = et_search.getText().toString().trim() ;
                int paying_type = AddNew_ItemActivity.PAYING_CASH ;
                data = db.getProductsWhereLike(searched_product , paying_type) ;
                my_adapter.setData(data);
                toastTextChanhe_Show("لعرض كل الأصناف مرة أخرى : \n اضغط مطولاً على نفس الزر");
            }
        });

        rb_in_installments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searched_product = et_search.getText().toString().trim() ;
                int paying_type = AddNew_ItemActivity.PAYING_INSTALLMENT ;
                data = db.getProductsWhereLike(searched_product , paying_type) ;
                my_adapter.setData(data);
                toastTextChanhe_Show("لعرض كل الأصناف مرة أخرى : \n اضغط مطولاً على نفس الزر");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        if (back_pressed_times>0){
            back_toast.cancel();
            finish();
        }
        else {
            back_toast.cancel();
            back_toast = Toast.makeText(this, "اضغط على زر الرجوع مرة أخرى لإغلاق البرنامج", Toast.LENGTH_SHORT);
            back_toast.show();
        }
        back_pressed_times++ ;
    }

    void toastTextChanhe_Show(String text){
        tst_radio_pressed.cancel();
        tst_radio_pressed = Toast.makeText(getBaseContext() ,text , Toast.LENGTH_LONG);
        tst_radio_pressed.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        back_pressed_times = 0 ;
    }

    @Override
    protected void onPause() {
        super.onPause();
        tst_radio_pressed.cancel();
        sound_player();
    }

    public void sound_player(){

        mp.setVolume(0.25f,0.25f);
        if (DefultPrefernce.getBoolean(SittingActivity.APP_SOUNDS_KEY , false)){
            mp.start();
        }
        else{
            mp.pause();
        }
    }
}

