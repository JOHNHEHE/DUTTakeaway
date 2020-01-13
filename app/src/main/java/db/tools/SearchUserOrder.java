package db.tools;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import adapter.OrderAdapter;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import entity.Order;

/**
 * Created by 张浩天
 */

public class SearchUserOrder {

    RecyclerView recyclerView;
    OrderAdapter orderAdapter;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public OrderAdapter getOrderAdapter() {
        return orderAdapter;
    }

    public void setOrderAdapter(OrderAdapter orderAdapter) {
        this.orderAdapter = orderAdapter;
    }

    public void search(String username) {
        BmobQuery<Order> query = new BmobQuery<>();
        query.addWhereEqualTo("username", username);
        query.findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> object, BmobException e) {
                if (e == null) {
                    if (object != null && object.size() > 0) {
                        orderAdapter = new OrderAdapter(object);
                        recyclerView.setAdapter(orderAdapter);
                    }
                }
            }
        });
    }
}
