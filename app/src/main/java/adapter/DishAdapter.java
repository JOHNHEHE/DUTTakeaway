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
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import db.tools.Tool;
import entity.Dish;
import entity.ShoppingCart;
import proxy.UserProxy;
import utils.ImageIdUtil;

/**
 * Created by 陈贤鹏
 */

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.ViewHolder>{

    private View ActivityView;

    private List<Dish> dishList;

    private Tool tool = new Tool();

    private TextView shop_cartNumber;

    private TextView total_price;

    private TextView no_shop;

    private  TextView how_money_to_delivery;

    private  TextView go_to_check;

    private Integer shopCartNum = 0; //当前点菜数

    private Double totalPrice = 0.0;// 所点菜品总价

    private List<Dish> orderDish = new ArrayList<Dish>();

    private ShoppingCart shoppingCart = new ShoppingCart();

    public Integer getShopCartNum() {
        return shopCartNum;
    }

    public void setShopCartNum(Integer shopCartNum) {
        this.shopCartNum = shopCartNum;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        View dishView;
        ImageView dishImage;
        TextView dishName;
        TextView saleCount;
        TextView dishPrice;
        ImageView dishAdd;
        TextView dishNumber;
        ImageView dishMinus;

        public ViewHolder(View view){
            super(view);
            dishView = view;
            dishImage = (ImageView) view.findViewById(R.id.dish_image);
            dishName = (TextView)view.findViewById(R.id.dish_name);
            saleCount = (TextView) view.findViewById(R.id.dish_sale_count);
            dishPrice = (TextView) view.findViewById(R.id.dish_price);
            dishAdd = (ImageView) view.findViewById(R.id.dish_add);
            dishNumber= (TextView) view.findViewById(R.id.dish_num);
            dishMinus = (ImageView) view.findViewById(R.id.dish_minus);
        }
    }

    public List<Dish> getDishList() {
        return dishList;
    }

    public DishAdapter( View view, List<Dish> dishList) {
        this.ActivityView = view;
        this.dishList = dishList;
        for (Dish dish : dishList){
            if (dish.getNumber() > 0){
                orderDish.add(dish);
            }
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        shop_cartNumber = (TextView) ActivityView.findViewById(R.id.shopCartNum);
        total_price = (TextView) ActivityView.findViewById(R.id.totalPrice);
        no_shop = (TextView) ActivityView.findViewById(R.id.noShop);
        how_money_to_delivery = (TextView) ActivityView.findViewById(R.id.howMoneyToDelivery);
        go_to_check = (TextView) ActivityView.findViewById(R.id.goTOCheckOut);

        if(shopCartNum > 0){
            shop_cartNumber.setVisibility(View.VISIBLE);
            shop_cartNumber.setText(String.valueOf(shopCartNum));
            total_price.setVisibility(View.VISIBLE);
            total_price.setText("￥"+String.valueOf(totalPrice));
            no_shop.setVisibility(View.GONE);
            if(totalPrice >= 10){
                how_money_to_delivery.setVisibility(View.GONE);
                go_to_check.setVisibility(View.VISIBLE);
            }
        }


        //加菜点击监听
        holder.dishAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                Dish dish = dishList.get(position);
                if (orderDish.contains(dish)){
                    dish.setNumber(dish.getNumber()+1);
                }else{
                    dish.setNumber(dish.getNumber()+1);
                    orderDish.add(dish);
                }

                shopCartNum += 1;
                totalPrice += dish.getPrice();
                if(dish.getNumber() > 0){
                    holder.dishMinus.setVisibility(View.VISIBLE);
                    holder.dishNumber.setVisibility(View.VISIBLE);
                    holder.dishNumber.setText(String.valueOf(dish.getNumber()));
                }
                if (shopCartNum >= 1){
                    no_shop.setVisibility(View.GONE);
                    total_price.setVisibility(View.VISIBLE);
                    shop_cartNumber.setVisibility(View.VISIBLE);
                    shop_cartNumber.setText(String.valueOf(shopCartNum));
                    total_price.setText("￥"+String.valueOf(totalPrice));
                }
                if (totalPrice >= 10){
                    go_to_check.setVisibility(View.VISIBLE);
                    how_money_to_delivery.setVisibility(View.GONE);
                }

                //将点单数据存在购物车里
                shoppingCart.setStoreName(dishList.get(0).getStoreName());
                shoppingCart.setDishes(orderDish);
                UserProxy userProxy=new UserProxy();
                shoppingCart.setName(userProxy.getCurrentUser().getUsername());//获得当前用户
                if(shopCartNum == 1){
                    tool.add(shoppingCart);
                }else{
                    String bql ="select * from ShoppingCart where storeName = ? and username = ?";
                    BmobQuery<ShoppingCart> query=new BmobQuery<ShoppingCart>();
                    query.setSQL(bql);
                    query.setPreparedParams(new Object[]{shoppingCart.getStoreName(), shoppingCart.getName()});
                    query.doSQLQuery(new SQLQueryListener<ShoppingCart>(){

                        @Override
                        public void done(BmobQueryResult<ShoppingCart> result, BmobException e) {
                            if (e == null) {
                                List<ShoppingCart> list = (List<ShoppingCart>) result.getResults();
                                if (list != null && list.size() > 0) {
                                    shoppingCart.setObjectId(list.get(0).getObjectId());
                                    tool.update(shoppingCart);
                                }
                            }
                        }
                    });
                }
            }
        });

        //减菜点击监听
        holder.dishMinus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                Dish dish = dishList.get(position);
                if (orderDish.contains(dish) && dish.getNumber()>1){
                    dish.setNumber(dish.getNumber()-1);
                }else if (orderDish.contains(dish) && dish.getNumber() == 1){
                    dish.setNumber(dish.getNumber()-1);
                    orderDish.remove(dish);
                }
                shopCartNum -= 1;
                totalPrice -= dish.getPrice();
                if(dish.getNumber() == 0){
                    holder.dishMinus.setVisibility(View.INVISIBLE);
                    holder.dishNumber.setVisibility(View.INVISIBLE);
                }else{
                    holder.dishNumber.setText(String.valueOf(dish.getNumber()));
                }
                if (shopCartNum == 0){
                    shop_cartNumber.setVisibility(View.GONE);
                    no_shop.setVisibility(View.VISIBLE);
                    total_price.setVisibility(View.GONE);
                }else{
                    shop_cartNumber.setText(String.valueOf(shopCartNum));
                    total_price.setText("￥"+String.valueOf(totalPrice));
                }
                if (totalPrice < 10){
                    go_to_check.setVisibility(View.GONE);
                    how_money_to_delivery.setVisibility(View.VISIBLE);
                }
                //更新购物车
                shoppingCart.setStoreName(dishList.get(0).getStoreName());
                shoppingCart.setDishes(orderDish);
                UserProxy userProxy=new UserProxy();
                shoppingCart.setName(userProxy.getCurrentUser().getUsername());//获得当前用户
                if(shopCartNum > 0){
                    String bql ="select * from ShoppingCart where storeName = ? and username = ?";
                    BmobQuery<ShoppingCart> query=new BmobQuery<ShoppingCart>();
                    query.setSQL(bql);
                    query.setPreparedParams(new Object[]{shoppingCart.getStoreName(), shoppingCart.getName()});
                    query.doSQLQuery(new SQLQueryListener<ShoppingCart>(){

                        @Override
                        public void done(BmobQueryResult<ShoppingCart> result, BmobException e) {
                            if (e == null) {
                                List<ShoppingCart> list = (List<ShoppingCart>) result.getResults();
                                if (list != null && list.size() > 0) {
                                    shoppingCart.setObjectId(list.get(0).getObjectId());
                                    tool.update(shoppingCart);
                                }
                            }
                        }
                    });

                }else{

                    String bql ="select * from ShoppingCart where storeName = ? and username = ?";
                    BmobQuery<ShoppingCart> query=new BmobQuery<ShoppingCart>();
                    query.setSQL(bql);
                    query.setPreparedParams(new Object[]{shoppingCart.getStoreName(), shoppingCart.getName()});
                    query.doSQLQuery(new SQLQueryListener<ShoppingCart>(){

                        @Override
                        public void done(BmobQueryResult<ShoppingCart> result, BmobException e) {
                            if (e == null) {
                                List<ShoppingCart> list = (List<ShoppingCart>) result.getResults();
                                if (list != null && list.size() > 0) {
                                    shoppingCart.setObjectId(list.get(0).getObjectId());
                                    tool.delete(shoppingCart);
                                }
                            }
                        }
                    });
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){

        Dish dish = dishList.get(position);
        ImageIdUtil imageIdUtil=new ImageIdUtil();
        int imgID=0;
        try {
            imgID = R.mipmap.class.getField(imageIdUtil.getImageID(dish.getStoreName())).getInt(new R.mipmap());
            holder.dishImage.setImageResource(imgID);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        holder.dishName.setText(dish.getDishName());
        holder.saleCount.setText("每月"+String.valueOf(dish.getSalesCount())+"单");
        holder.dishPrice.setText("￥"+String.valueOf(dish.getPrice()));
        if (dish.getNumber() > 0){
            holder.dishMinus.setVisibility(View.VISIBLE);
            holder.dishNumber.setText(String.valueOf(dish.getNumber()));
            holder.dishNumber.setVisibility(View.VISIBLE);
            shopCartNum += dish.getNumber();
            totalPrice += dish.getPrice()*dish.getNumber();
        }

    }

    @Override
    public int getItemCount(){
        return dishList.size();
    }
}
