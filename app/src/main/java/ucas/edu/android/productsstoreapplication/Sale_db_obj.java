package ucas.edu.android.productsstoreapplication;

public class Sale_db_obj {
    String sale_product_name ;
    double sale_price ;
    String sale_date_time ;
    int sale_user_id ;

    public Sale_db_obj(String sale_product_name, double sale_price, String sale_date_time, int sale_user_id) {
        this.sale_product_name = sale_product_name;
        this.sale_price = sale_price;
        this.sale_date_time = sale_date_time;
        this.sale_user_id = sale_user_id;
    }

    public void setSale_product_name(String sale_product_name) {
        this.sale_product_name = sale_product_name;
    }

    public void setSale_price(double sale_price) {
        this.sale_price = sale_price;
    }

    public void setSale_date_time(String sale_date_time) {
        this.sale_date_time = sale_date_time;
    }

    public void setSale_user_id(int sale_user_id) {
        this.sale_user_id = sale_user_id;
    }

    public String getSale_product_name() {
        return sale_product_name;
    }

    public double getSale_price() {
        return sale_price;
    }

    public String getSale_date_time() {
        return sale_date_time;
    }

    public int getSale_user_id() {
        return sale_user_id;
    }
}
