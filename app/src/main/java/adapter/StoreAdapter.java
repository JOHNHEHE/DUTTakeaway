package adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.dut.duttake_away.DishActivity;
import com.dut.duttake_away.R;
import com.google.gson.Gson;
import java.util.List;
import entity.Store;
import utils.ImageIdUtil;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by 陈贤鹏
 */

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder>{

    private List<Store> storeList;



    static class ViewHolder extends RecyclerView.ViewHolder{

        View storeView;
        ImageView storePhoto;
        TextView storeName;
        TextView deliveryTime;
        TextView salesCount;
        TextView phoneNumber;

        public ViewHolder(View view){
            super(view);
            storeView = view;
            storePhoto = (ImageView) view.findViewById(R.id.store_photo);
            storeName = (TextView)view.findViewById(R.id.store_name);
            deliveryTime = (TextView) view.findViewById(R.id.store_time);
            salesCount = (TextView) view.findViewById(R.id.store_count);
            phoneNumber = (TextView) view.findViewById(R.id.store_phone);
        }
    }

    public StoreAdapter( List<Store> storelist){
        this.storeList = storelist;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        //RecyclerView点击监听
        holder.storeView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                Store store = storeList.get(position);
                Toast.makeText(v.getContext(), "欢迎光顾"+store.getStoreName(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), DishActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                intent.putExtra("store",new Gson().toJson(store));
                getApplicationContext().startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){

        Store store = storeList.get(position);
        ImageIdUtil imageIdUtil=new ImageIdUtil();
        int imgID=0;
        try {
            imgID = R.mipmap.class.getField(imageIdUtil.getImageID(store.getStoreName())).getInt(new R.mipmap());
            holder.storePhoto.setImageResource(imgID);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        //imgViewID = R.id.class.getField( "imageView" + i ).getInt(new R.id());
        holder.storeName.setText(store.getStoreName());
        holder.deliveryTime.setText(String.valueOf(store.getDeliveryTime()));
        holder.salesCount.setText(String.valueOf(store.getSalesCount()));
        holder.phoneNumber.setText(store.getPhoneNumber());


    }

    @Override
    public int getItemCount(){
        return storeList.size();
    }

}
