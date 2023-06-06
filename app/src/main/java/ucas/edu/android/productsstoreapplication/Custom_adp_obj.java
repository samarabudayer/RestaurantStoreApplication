package ucas.edu.android.productsstoreapplication;

import android.net.Uri;

public class Custom_adp_obj {
    String name ;

    public Uri getImg_uri() {
        return img_uri;
    }

    public void setImg_uri(Uri img_uri) {
        this.img_uri = img_uri;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    String paying_type ;
    double price ;
    Uri img_uri ;

    public String getPaying_type() {
        return paying_type;
    }

    public void setPaying_type(String paying_type) {
        this.paying_type = paying_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
