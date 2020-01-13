package db.tools;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.StoreAdapter;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import entity.Store;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by 陈贤鹏
 */

public class SearchStore {

    private List<Store> storeList = new ArrayList<Store>();

    private StoreAdapter storeAdapter;


    //查询所有店家
    public void SearchAllStore(final RecyclerView recyclerView){

        String bql ="select * from Store";
        BmobQuery<Store> query=new BmobQuery<Store>();
        query.setSQL(bql);
        query.doSQLQuery(new SQLQueryListener<Store>(){

            @Override
            public void done(BmobQueryResult<Store> result, BmobException e) {
                if (e == null) {
                    List<Store> list = (List<Store>) result.getResults();
                    if (list != null && list.size() > 0) {
                        storeList = list;
                        storeAdapter = new StoreAdapter(storeList);
                        recyclerView.setAdapter(storeAdapter);
                    }
                }
            }
        });
    }

    //查询部分店家
    public void SearchStore(final RecyclerView recyclerView, TextView textView, String string){
        List<Store> stores = new ArrayList<Store>();
        for (Store store : storeList){
            if (store.getStoreName().contains(string)){
                stores.add(store);
            }
        }
        if (stores.isEmpty()){
            textView.setVisibility(View.VISIBLE);
        }else {
            textView.setVisibility(View.GONE);
        }
        storeAdapter = new StoreAdapter(stores);
        recyclerView.setAdapter(storeAdapter);
    }
}
