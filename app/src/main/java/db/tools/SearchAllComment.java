package db.tools;

import android.support.v7.widget.RecyclerView;
import java.util.List;
import adapter.CommentAdapter;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import entity.Comment;

/**
 * Created by 张浩天
 */

public class SearchAllComment {
    /*public void search(final RecyclerView recyclerView){
        String bql ="select * from Comment";
        BmobQuery<Comment> query=new BmobQuery<Comment>();
        query.include("user,store");
        query.setSQL(bql);
        query.doSQLQuery(new SQLQueryListener<Comment>(){
            @Override
            public void done(BmobQueryResult<Comment> result, BmobException e) {
                if (e == null) {
                    List<Comment> list = (List<Comment>) result.getResults();
                    if (list != null && list.size() > 0) {
                        CommentAdapter commentAdapter = new CommentAdapter(list);
                        recyclerView.setAdapter(commentAdapter);
                    }
                }
            }
        });

    }*/

    public void search(final RecyclerView recyclerView) {
        BmobQuery<Comment> query = new BmobQuery<>();
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> object, BmobException e) {
                if (e == null) {
                    if (object != null && object.size() > 0) {
                        CommentAdapter commentAdapter = new CommentAdapter(object);
                        recyclerView.setAdapter(commentAdapter);
                    }
                }
            }
        });
    }
}


