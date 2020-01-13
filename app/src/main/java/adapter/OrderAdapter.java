package adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.dut.duttake_away.MakeCommentActivity;
import com.dut.duttake_away.R;

import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import db.tools.Tool;
import entity.Dish;
import entity.Order;
import entity.Store;
import utils.DateUtil;
import utils.LogUtil;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by 张浩天
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    private List<Order> OrderList;
    List<Order> orderlist=new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView orderStore;
        TextView state;
        TextView orderdish;
        TextView orderNumber;
        Button cancel;
        Button makecomment;

        public ViewHolder(View view){
            super(view);
            orderStore = view.findViewById(R.id.orderStore);
            state = view.findViewById(R.id.state);
            orderdish = view.findViewById(R.id.orderdish);
            orderNumber = view.findViewById(R.id.orderNumber);
            cancel=view.findViewById(R.id.cancel);
            makecomment=view.findViewById(R.id.makecomment);
        }
    }


    public OrderAdapter(List<Order> OrderList){
        this.OrderList = OrderList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num=holder.orderNumber.getText().toString();
                LogUtil.i("ID",num);
                Order currentorder=new Order();
                int position=0;
                for(;position<orderlist.size();position++ ){
                    if(orderlist.get(position).getOrderNumber().equals(num)){
                        orderlist.get(position).setState("取消");
                        currentorder=orderlist.get(position);
                        break;
                    }
                }
                Tool tool=new Tool();
                tool.update(currentorder); //将状态更新同步到后台
                notifyItemChanged(position);    //刷新当前item
            }
        });

        //监听点击评论事件
        holder.makecomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MakeCommentActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                intent.putExtra("storename",holder.orderStore.getText().toString());
                getApplicationContext().startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        Order order;
        order = OrderList.get(position);
        //LogUtil.i(String.valueOf(position),order.getOrderNumber());
        orderlist.add(order);
        holder.orderNumber.setText(order.getOrderNumber());
        List<Dish> m=order.getDishes();
        String dish_="";
        String dish_store="";
        for(Dish d:m){
            dish_+=d.getDishName()+"*"+d.getNumber()+" ";   //菜名和点的数量拼接在一起
            dish_store=d.getStoreName();
        }
        holder.orderStore.setText(dish_store);
        holder.orderdish.setText(dish_);
        String[] onumber=order.getOrderNumber().split("#");
        String starttime=onumber[0];    //获得订单创建时间
        Log.i("start time:",starttime);
        if(order.getState().equals("已完成")||order.getState().equals("取消")){
            holder.state.setText(order.getState());
            holder.cancel.setVisibility(View.GONE); //除了未完成状态以外，其他时候去掉取消按钮
        }
        else{
            //holder.state.setText("未完成");
            search(starttime,dish_store,holder,order);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount(){
        return OrderList.size();
    }

    //判断未完成订单是否已经经过了配送时间，经过了则修改状态为已完成
    private void search(final String createtime, String storename, final ViewHolder holder,final Order order) {
        BmobQuery<Store> query = new BmobQuery<>();
        query.addWhereEqualTo("storeName", storename);
        query.findObjects(new FindListener<Store>() {
            @Override
            public void done(List<Store> object, BmobException e) {
                if (e == null) {
                    if (object != null && object.size() > 0) {
                        int deliverytime=object.get(0).getDeliveryTime();
                        DateUtil dateUtil=new DateUtil();
                        if(dateUtil.isEnd(createtime,deliverytime)){
                            holder.state.setText("已完成");
                            order.setState("已完成");
                            Tool tool=new Tool();
                            tool.update(order); //更新状态到后台
                            notifyDataSetChanged();
                        }
                        else{
                            holder.state.setText("未完成");
                        }
                    }
                }
            }
        });
    }


}
