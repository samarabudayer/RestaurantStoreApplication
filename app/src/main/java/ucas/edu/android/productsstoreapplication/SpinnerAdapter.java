package ucas.edu.android.productsstoreapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SpinnerAdapter extends BaseAdapter {

    ArrayList<String> data;
    public SpinnerAdapter ( ArrayList<String> data){
        this.data = data ;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position);
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_adapter , null , false);
        }

        TextView textView1 = convertView.findViewById(R.id.spinner_text1) ;
        textView1.setText(data.get(position));

        return convertView;
    }
}
