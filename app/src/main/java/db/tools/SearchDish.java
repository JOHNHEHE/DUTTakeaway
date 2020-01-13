package db.tools;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.dut.duttake_away.R;
import java.util.ArrayList;
import java.util.List;
import adapter.DishAdapter;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import entity.Dish;
import entity.ShoppingCart;
import entity.Store;
import entity.User;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by 陈贤鹏
 */

public class SearchDish {

    List<Dish> dishList = new ArrayList<Dish>();

    private DishAdapter dishAdapter;

    private  RecyclerView recyclerView;

    //查询某家店所有菜品并将购物车所有信息加载到界面上
    public void SearchAllDish(final View view,final Store store,final User user){

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_dish);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        String bql ="select * from Dish where storeName = ?";
        BmobQuery<Dish> query=new BmobQuery<Dish>();
        query.setSQL(bql);
        query.setPreparedParams(new Object[]{store.getStoreName()});
        query.doSQLQuery(new SQLQueryListener<Dish>(){

            @Override
            public void done(BmobQueryResult<Dish> result, BmobException e) {
                if (e == null) {
                    List<Dish> list = (List<Dish>) result.getResults();
                    if (list != null && list.size() > 0) {
                        dishList = list;   //获得店家所有菜品

                        //加载购物车内容
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
                                        List<Dish> orderDishes = new ArrayList<Dish>();
                                        orderDishes = list.get(0).getDishes();
                                        for(Dish order_dish : orderDishes){
                                            for (Dish dish : dishList){
                                                if (order_dish.getObjectId().equals(dish.getObjectId())){
                                                    dish.setNumber(order_dish.getNumber());
                                                }
                                            }
                                        }
                                    }
                                    dishAdapter = new DishAdapter(view, dishList);
                                    recyclerView.setAdapter(dishAdapter);
                                }
                            }
                        });

                    }
                }
            }
        });
    }
}
