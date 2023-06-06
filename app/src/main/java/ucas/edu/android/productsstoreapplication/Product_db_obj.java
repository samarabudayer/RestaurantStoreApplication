package ucas.edu.android.productsstoreapplication;

import java.io.Serializable;

public class Product_db_obj implements Serializable {

   // int product_id = 0 ;
    String product_name ;
    double product_price ;
    String product_uri ;
    String product_description ;
    int product_paying_type ;

    public static final int TYPE_CASH = 1 ;
    public static final int TYPE_INSTALLMENT = 2 ;

    public Product_db_obj(String product_name, double product_price, int product_paying_type) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_paying_type = product_paying_type;
        product_uri = null ;
        product_description = null ;
    }

    public Product_db_obj(String product_name, double product_price, int product_paying_type , String product_uri , String product_description) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_paying_type = product_paying_type;
        this.product_uri = product_uri ;
        this.product_description = product_description ;
    }

    public String getProduct_name() {
        return product_name;
    }

    public double getProduct_price() {
        return product_price;
    }

    public String getProduct_uri() {
        return product_uri;
    }

    public String getProduct_description() {
        return product_description;
    }

    public int getProduct_paying_type() {
        return product_paying_type;
    }

    public static int getTypeCash() {
        return TYPE_CASH;
    }

    public static int getTypeInstallment() {
        return TYPE_INSTALLMENT;
    }


    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public void setProduct_uri(String product_uri) {
        this.product_uri = product_uri;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public void setProduct_paying_type(int product_paying_type) {
        this.product_paying_type = product_paying_type;
    }
}
