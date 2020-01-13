package db.tools;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dut.duttake_away.R;
import java.util.ArrayList;
import java.util.List;
import adapter.DishAdapter;
import adapter.OrderDishAdapter;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import entity.Dish;
import entity.Order;
import entity.ShoppingCart;
import entity.Store;
import entity.User;
import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by 陈贤鹏
 */

public class SearchShoppingCart {

    private List<Dish> orderDishList = new ArrayList<Dish>();

    private Order order = new Order();

    private  Tool tool = new Tool();

    private RecyclerView recyclerView;

    private OrderDishAdapter orderDishAdapter;

    private DishAdapter dishAdapter;

    private TextView order_total_price;

    private  Double totalPrice =0.0;

    private List<ShoppingCart> shoppingCartList = new ArrayList<ShoppingCart>();

    //查询购物车所有菜品并显示在核对订单界面
    public void SearchShopingCart(final View view, Store store, User user){
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_order_dish);
        order_total_price = (TextView) view.findViewById(R.id.order_totalPrice);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        String bql ="select * from ShoppingCart where storeName = ? and username = ?";
        BmobQuery<ShoppingCart> query=new BmobQuery<ShoppingCart>();
        query.setSQL(bql);
        query.setPreparedParams(new Object[]{store.getStoreName(),user.getUsername()});
        query.doSQLQuery(new SQLQueryListener<ShoppingCart>(){

            @Override
            public void done(BmobQueryResult<ShoppingCart> result, BmobException e) {
                if (e == null) {
                    List<ShoppingCart> list = (List<ShoppingCart>) result.getResults();
                    if (list != null && list.size() > 0) {
                        shoppingCartList = list;
                        orderDishList = list.get(0).getDishes();
                        for(Dish dish : orderDishList){
                            totalPrice += dish.getPrice()*dish.getNumber();
                        }
                        orderDishAdapter = new OrderDishAdapter(orderDishList);
                        recyclerView.setAdapter(orderDishAdapter);
                        order_total_price.setText("￥"+String.valueOf(totalPrice));
                    }
                }
            }
        });
    }

    //将购物车信息存放在订单中
    public void SearchShoppingCartToOrder(Store store, final User user){
        String bql ="select * from ShoppingCart where storeName = ? and username = ?";
        BmobQuery<ShoppingCart> query=new BmobQuery<ShoppingCart>();
        query.setSQL(bql);
        query.setPreparedParams(new Object[]{store.getStoreName(),user.getUsername()});
        query.doSQLQuery(new SQLQueryListener<ShoppingCart>(){

            @Override
            public void done(BmobQueryResult<ShoppingCart> result, BmobException e) {
                if (e == null) {
                    List<ShoppingCart> list = (List<ShoppingCart>) result.getResults();
                    if (list != null && list.size() > 0) {
                        shoppingCartList = list;
                        orderDishList = list.get(0).getDishes();
                        Double orderPrice = 0.0;
                        for (Dish dish : orderDishList){
                            orderPrice += dish.getPrice()*dish.getNumber();
                        }

                        //扣余额
                        if(user.getCash()>= orderPrice){
                            user.setCash(user.getCash()-orderPrice);
                            tool.update(user);
                            order.setDishes(orderDishList);
                            order.setName(user.getUsername());
                            order.setState("未完成");
                            order.save(new SaveListener<String>() {
                                @Override
                                public void done(String objectId,BmobException e) {
                                    if(e==null){
                                        order.setOrderNumber(order.getCreatedAt()+"#"+user.getUsername());
                                        tool.update(order);
                                        tool.delete(shoppingCartList.get(0));
                                    }
                                }
                            });

                        }else{
                            Toast.makeText(getApplicationContext(),"您的余额不足，无法下单!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}
