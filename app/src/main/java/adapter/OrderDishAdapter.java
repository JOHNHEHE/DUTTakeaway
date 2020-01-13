package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.dut.duttake_away.R;
import java.util.ArrayList;
import java.util.List;
import entity.Dish;
import entity.Store;
import utils.ImageIdUtil;

/**
 * Created by 陈贤鹏
 */

public class OrderDishAdapter extends RecyclerView.Adapter<OrderDishAdapter.ViewHolder> {

    private List<Dish> orderDishList = new ArrayList<Dish>();



    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView order_dish_image;
        TextView order_dish_name;
        TextView order_dish_number;
        TextView order_dish_total_price;

        public ViewHolder(View view){
            super(view);
            order_dish_image = (ImageView) view.findViewById(R.id.order_dish_image);
            order_dish_name = (TextView)view.findViewById(R.id.order_dish_name);
            order_dish_number = (TextView) view.findViewById(R.id.order_dish_number);
            order_dish_total_price = (TextView) view.findViewById(R.id.order_dish_total_price);
        }
    }

    public OrderDishAdapter( List<Dish> orderDishList){
        this.orderDishList = orderDishList;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_dish_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){

        Dish dish = orderDishList.get(position);
        ImageIdUtil imageIdUtil=new ImageIdUtil();
        int imgID=0;
        try {
            imgID = R.mipmap.class.getField(imageIdUtil.getImageID(dish.getStoreName())).getInt(new R.mipmap());
            holder.order_dish_image.setImageResource(imgID);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        holder.order_dish_name.setText(dish.getDishName());
        holder.order_dish_number.setText("×"+String.valueOf(dish.getNumber()));
        holder.order_dish_total_price.setText("￥"+String.valueOf(dish.getNumber()* dish.getPrice()));


    }

    @Override
    public int getItemCount(){
        return orderDishList.size();
    }
}
