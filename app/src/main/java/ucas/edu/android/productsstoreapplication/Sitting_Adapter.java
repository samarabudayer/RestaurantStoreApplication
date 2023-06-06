package ucas.edu.android.productsstoreapplication;



import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Sitting_Adapter extends RecyclerView.Adapter<Sitting_Adapter.Sitting_viewhalder> {

    ArrayList<Sitting_objects> data ;
    ViewGroup viewGroup ;
    int position ;
    Intent intent ;
    View view ;
    int last_position = -1 ;

    public Sitting_Adapter(ArrayList<Sitting_objects> data){
        this.data = data ;
    }

    @NonNull
    @Override
    public Sitting_viewhalder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sitting_custom_adapter  , null ) ;
        Sitting_viewhalder viewhalder = new Sitting_viewhalder(view) ;
        viewGroup = parent ;
        return viewhalder;
    }

    @Override
    public void onBindViewHolder(@NonNull Sitting_viewhalder holder, int position) {
        if (holder.getAdapterPosition()>last_position && position%2==0 ){
            Animation animation = AnimationUtils.loadAnimation(viewGroup.getContext() , R.anim.animation) ;
            ((Sitting_viewhalder)holder).itemView .startAnimation(animation);
            last_position = holder.getAdapterPosition() ;
        }
        else if (holder.getAdapterPosition()>last_position && position%2!=0 ){
            Animation animation = AnimationUtils.loadAnimation(viewGroup.getContext() , R.anim.animation2) ;
            ((Sitting_viewhalder)holder).itemView .startAnimation(animation);
            last_position = holder.getAdapterPosition() ;
        }
        Sitting_objects obj = data.get(position) ;
        holder.textView.setText(obj.sittin_type);
        holder.imageView.setImageResource(obj.img_res);

        this.position = position ;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Sitting_viewhalder extends RecyclerView.ViewHolder{

        TextView textView ;
        ImageView imageView ;

        public Sitting_viewhalder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.sittig_recycler_tv1) ;
            imageView = itemView.findViewById(R.id.sittig_recycler_img1) ;
            view = itemView ;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition() ;
                    intent = new Intent() ;
                    switch (position){
                        case 0 :
                            intent.setClass(viewGroup.getContext() , All_salesActivity.class) ;
                            break;
                        case 1 :
                            intent.setClass(viewGroup.getContext() , Last_saleActivity.class) ;
                            break;
                        case 2 :
                            intent.setClass(viewGroup.getContext() , ChangePasswordActivity.class) ;
                            break;
                        case 3 :
                            intent.setClass(viewGroup.getContext() , DeleteAllSalesActivity.class) ;
                            break;
                        case 4 :
                            intent.setClass(viewGroup.getContext() , AddNew_ItemActivity.class) ;
                            break;
                    }
                    viewGroup.getContext().startActivity(intent);
                }
            });
        }
    }

    public int getPosition() {
        return position;
    }

    public Intent getIntent() {
        return intent;
    }

    public View getView() {
        return view;
    }
}
