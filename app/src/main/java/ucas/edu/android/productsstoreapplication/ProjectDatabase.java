package ucas.edu.android.productsstoreapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ProjectDatabase extends SQLiteOpenHelper {
    public static final String database_name = "ProjectDatabase" ;
    public static final int database_version = 1 ;

    public static final String PRODUCT_TABLE_NAME = "PRODUCT" ;
    public static final String PRODUCT_CLN_ID = "PRODUCT_ID" ;
    public static final String PRODUCT_CLN_NAME = "PRODUCT_NAME" ;
    public static final String PRODUCT_CLN_PRICE = "PRODUCT_PRICE" ;
    public static final String PRODUCT_CLN_URI = "PRODUCT_URI" ;
    public static final String PRODUCT_CLN_DESCRIPTION = "PRODUCT_DESCRIPTION" ;
    public static final String PRODUCT_CLN_PAYING_TYPE = "PRODUCT_PAYING_TYPE" ;


    public static final String SALES_TABLE_NAME = "SALES" ;
    public static final String SALES_CLN_ID = "SALE_ID" ;
    public static final String SALES_CLN_PRODUCT_NAME = "SALE_PRODUCT_NAME" ;
    public static final String SALES_CLN_PRICE = "SALE_TOTAL_PRICE" ;
    public static final String SALES_CLN_DATE_TIME = "SALE_DATE_TIME" ;
    public static final String SALES_CLN_USER_ID = "SALE_USER_ID" ;


    public ProjectDatabase(Context context){
        super(context , database_name , null , database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+PRODUCT_TABLE_NAME+"( "+PRODUCT_CLN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT " +
                " , " +PRODUCT_CLN_NAME + " TEXT NOT NULL ,  " +PRODUCT_CLN_PRICE + " REAL NOT NULL " +
                " , " +PRODUCT_CLN_URI + " TEXT , " +PRODUCT_CLN_DESCRIPTION + " TEXT  , " + PRODUCT_CLN_PAYING_TYPE + " INTEGER NOT NULL );");

        db.execSQL("CREATE TABLE "+SALES_TABLE_NAME+" ( " +SALES_CLN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
                " , " +SALES_CLN_PRODUCT_NAME + " TEXT NOT NULL , " +SALES_CLN_PRICE + " REAL NOT NULL ," +
                "  " +SALES_CLN_DATE_TIME + " TEXT NOT NULL , " +SALES_CLN_USER_ID + " INTEGER NOT NULL );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertProduct(Product_db_obj obj){
        SQLiteDatabase db = getWritableDatabase() ;
        ContentValues values = new ContentValues() ;
        values.put(PRODUCT_CLN_NAME , obj.getProduct_name());
        values.put(PRODUCT_CLN_PRICE , obj.getProduct_price()) ;
        values.put(PRODUCT_CLN_URI , obj.getProduct_uri());
        values.put(PRODUCT_CLN_DESCRIPTION , obj.getProduct_description());
        values.put(PRODUCT_CLN_PAYING_TYPE , obj.getProduct_paying_type());

        long had_insert =  db.insert(PRODUCT_TABLE_NAME , null ,values ) ;
        return had_insert  ;
    }

    public long insertSale(Sale_db_obj obj){
        SQLiteDatabase db = getWritableDatabase() ;
        ContentValues values = new ContentValues() ;
        values.put(SALES_CLN_PRODUCT_NAME , obj.getSale_product_name());
        values.put(SALES_CLN_PRICE , obj.getSale_price());
        values.put(SALES_CLN_DATE_TIME , obj.getSale_date_time());
        values.put(SALES_CLN_USER_ID , obj.getSale_user_id());

        long had_insert = db.insert(SALES_TABLE_NAME , null , values) ;
        return had_insert ;
    }

    public long deleteAllSalesForUser(int user_id){
        SQLiteDatabase db = getWritableDatabase() ;

        String[] value = {user_id+""} ;

        long i = db.delete(SALES_TABLE_NAME ," SALE_USER_ID = ? " , value) ;
        return i ;
    }

    public long getProductsCount(){
        SQLiteDatabase db = getReadableDatabase() ;
        return DatabaseUtils.queryNumEntries(db , PRODUCT_TABLE_NAME) ;
    }

    public long getSalesCount(){
        SQLiteDatabase db = getReadableDatabase() ;
        return DatabaseUtils.queryNumEntries(db , SALES_TABLE_NAME) ;
    }


    public ArrayList<Product_db_obj> getAllProducts(){
        ArrayList<Product_db_obj> products = new ArrayList<>() ;
        SQLiteDatabase db = getReadableDatabase() ;
        Cursor cr = db.rawQuery("SELECT * FROM "+PRODUCT_TABLE_NAME, null) ;

        if (cr.moveToFirst()){
            do {
                @SuppressLint("Range") String pro_name = cr.getString(cr.getColumnIndex(PRODUCT_CLN_NAME)) ;
                @SuppressLint("Range") Double pro_price = cr.getDouble(cr.getColumnIndex(PRODUCT_CLN_PRICE)) ;
                @SuppressLint("Range") String pro_uri = cr.getString(cr.getColumnIndex(PRODUCT_CLN_URI)) ;
                @SuppressLint("Range") String pro_description = cr.getString(cr.getColumnIndex(PRODUCT_CLN_DESCRIPTION)) ;
                @SuppressLint("Range") int pro_paying_type = cr.getInt(cr.getColumnIndex(PRODUCT_CLN_PAYING_TYPE)) ;

                Product_db_obj obj = new Product_db_obj(pro_name , pro_price , pro_paying_type , pro_uri  , pro_description) ;

                products.add(obj) ;
            }while (cr.moveToNext()) ;
            cr.close();
        }
        return products ;
    }

    public ArrayList<Product_db_obj> getProductsWhereLike(String pro_name_like , int paying_type){
        ArrayList<Product_db_obj> products = new ArrayList<>() ;
        SQLiteDatabase db = getReadableDatabase() ;

        String[] args = {"%"+pro_name_like+"%"} ;

        Cursor cr = null ;

        switch (paying_type) {

            case AddNew_ItemActivity.PAYING_BOTH :
                cr = db.rawQuery("SELECT * FROM " + PRODUCT_TABLE_NAME + " WHERE " + PRODUCT_CLN_NAME + " LIKE ? AND "
                        + PRODUCT_CLN_PAYING_TYPE + " in ( " + AddNew_ItemActivity.PAYING_CASH + " , " + AddNew_ItemActivity.PAYING_INSTALLMENT + " )", args);
                break;

            case AddNew_ItemActivity.PAYING_CASH :
                cr = db.rawQuery("SELECT * FROM " + PRODUCT_TABLE_NAME + " WHERE " + PRODUCT_CLN_NAME + " LIKE ? AND "
                        + PRODUCT_CLN_PAYING_TYPE + " in ( " + AddNew_ItemActivity.PAYING_CASH +"  )", args);
                break;

            case AddNew_ItemActivity.PAYING_INSTALLMENT :
                cr = db.rawQuery("SELECT * FROM " + PRODUCT_TABLE_NAME + " WHERE " + PRODUCT_CLN_NAME + " LIKE ? AND "
                        + PRODUCT_CLN_PAYING_TYPE + " in ( " + AddNew_ItemActivity.PAYING_INSTALLMENT +"  )", args);
                break;
        }

        if (cr.moveToFirst()){
            do {
                @SuppressLint("Range") String pro_name = cr.getString(cr.getColumnIndex(PRODUCT_CLN_NAME)) ;
                @SuppressLint("Range") Double pro_price = cr.getDouble(cr.getColumnIndex(PRODUCT_CLN_PRICE)) ;
                @SuppressLint("Range") String pro_uri = cr.getString(cr.getColumnIndex(PRODUCT_CLN_URI)) ;
                @SuppressLint("Range") String pro_description = cr.getString(cr.getColumnIndex(PRODUCT_CLN_DESCRIPTION)) ;
                @SuppressLint("Range") int pro_paying_type = cr.getInt(cr.getColumnIndex(PRODUCT_CLN_PAYING_TYPE)) ;

                Product_db_obj obj = new Product_db_obj(pro_name , pro_price , pro_paying_type , pro_uri  , pro_description) ;

                products.add(obj) ;
            }while (cr.moveToNext()) ;
            cr.close();
        }
        return products ;
    }



    public ArrayList<Sale_db_obj> getAllSalesForUser(int user_id , int order_by ){
        ArrayList<Sale_db_obj> sales = new ArrayList<>() ;
        SQLiteDatabase db = getReadableDatabase() ;

        String[] args = {user_id+""} ;

        Cursor cr = null ;

        switch (order_by){
            case All_salesActivity.ORDER_BY_NEWEST :
                cr = db.rawQuery("SELECT * FROM "+SALES_TABLE_NAME+" WHERE " + SALES_CLN_USER_ID + " = ?  ORDER BY "+ SALES_CLN_ID + " DESC ; ", args) ;
                break;

            case All_salesActivity.ORDER_BY_MOST_PRICE :
                cr = db.rawQuery("SELECT * FROM "+SALES_TABLE_NAME+" WHERE " + SALES_CLN_USER_ID + " = ?  ORDER BY "+ SALES_CLN_PRICE + " DESC ; ", args) ;
                break;

            case All_salesActivity.ORDER_BY_LOWEST_PRICE :
                cr = db.rawQuery("SELECT * FROM "+SALES_TABLE_NAME+" WHERE " + SALES_CLN_USER_ID + " = ?  ORDER BY "+ SALES_CLN_PRICE + " ASC ; ", args) ;
                break;

            case All_salesActivity.ORDER_BY_OLDEST :
                cr = db.rawQuery("SELECT * FROM "+SALES_TABLE_NAME+" WHERE " + SALES_CLN_USER_ID + " = ?  ORDER BY "+ SALES_CLN_ID + " ASC ; ", args) ;
                break;
        }

        if (cr.moveToFirst()){
            do {
                @SuppressLint("Range") String sale_pro_name = cr.getString(cr.getColumnIndex(SALES_CLN_PRODUCT_NAME)) ;
                @SuppressLint("Range") Double sale_price = cr.getDouble(cr.getColumnIndex(SALES_CLN_PRICE)) ;
                @SuppressLint("Range") String sale_date = cr.getString(cr.getColumnIndex(SALES_CLN_DATE_TIME)) ;
                @SuppressLint("Range") int sale_user_id = cr.getInt(cr.getColumnIndex(SALES_CLN_USER_ID)) ;

                Sale_db_obj obj = new Sale_db_obj(sale_pro_name , sale_price , sale_date , user_id) ;
                sales.add(obj) ;
            }while (cr.moveToNext()) ;
            cr.close();
        }
        return sales ;
    }

    public ArrayList<Sale_db_obj> getLastSaleForUser(int user_id ){
        ArrayList<Sale_db_obj> sales = new ArrayList<>() ;
        SQLiteDatabase db = getReadableDatabase() ;

        String[] args = {user_id+""} ;

        Cursor cr = cr = db.rawQuery("SELECT * FROM "+SALES_TABLE_NAME+" WHERE " + SALES_CLN_USER_ID + " = ?  ORDER BY "+ SALES_CLN_ID + " DESC ; ", args) ;


        if (cr.moveToFirst()){

            @SuppressLint("Range") String sale_pro_name = cr.getString(cr.getColumnIndex(SALES_CLN_PRODUCT_NAME)) ;
            @SuppressLint("Range") Double sale_price = cr.getDouble(cr.getColumnIndex(SALES_CLN_PRICE)) ;
            @SuppressLint("Range") String sale_date = cr.getString(cr.getColumnIndex(SALES_CLN_DATE_TIME)) ;
            @SuppressLint("Range") int sale_user_id = cr.getInt(cr.getColumnIndex(SALES_CLN_USER_ID)) ;

            Sale_db_obj obj = new Sale_db_obj(sale_pro_name , sale_price , sale_date , user_id) ;
            sales.add(obj) ;

            cr.close();
        }
        return sales ;
    }

    public double getSumOfSaleForUser(int user_id ){
        ArrayList<Sale_db_obj> sales = new ArrayList<>() ;
        SQLiteDatabase db = getReadableDatabase() ;

        String[] args = {user_id+""} ;

        Cursor cr = cr = db.rawQuery("SELECT sum("+SALES_CLN_PRICE+") FROM "+SALES_TABLE_NAME+" WHERE " + SALES_CLN_USER_ID + " = ? ; ", args) ;

        double sum_of_sales =0;

        if (cr.moveToFirst()){

//            String sale_pro_name = cr.getString(cr.getColumnIndex(SALES_CLN_PRODUCT_NAME)) ;
//            Double sale_price = cr.getDouble(cr.getColumnIndex(SALES_CLN_PRICE)) ;
//            String sale_date = cr.getString(cr.getColumnIndex(SALES_CLN_DATE_TIME)) ;
//            int sale_user_id = cr.getInt(cr.getColumnIndex(SALES_CLN_USER_ID)) ;
//
//            Sale_db_obj obj = new Sale_db_obj(sale_pro_name , sale_price , sale_date , user_id) ;
//            sales.add(obj) ;

            sum_of_sales = cr.getDouble(0) ;

            cr.close();
        }
        return sum_of_sales ;
    }






}

