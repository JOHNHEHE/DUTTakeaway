package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.dut.duttake_away.R;

import java.util.List;
import entity.Comment;

/**
 * Created by 张浩天
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    private List<Comment> commentList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView comment;
        TextView commentUser;
        TextView commentStore;
        TextView time;

        public ViewHolder(View view){
            super(view);
            comment = view.findViewById(R.id.comment);
            commentUser = view.findViewById(R.id.commentUser);
            commentStore = view.findViewById(R.id.commentStore);
            time = view.findViewById(R.id.time);
        }
    }

    public CommentAdapter(List<Comment> commentList){
        this.commentList = commentList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Comment comment = commentList.get(position);
        holder.comment.setText(comment.getMessage());
        holder.commentStore.setText(comment.getStoreName());
        holder.commentUser.setText(comment.getName());
        holder.time.setText(comment.getCreatedAt());
    }

    @Override
    public int getItemCount(){
        return commentList.size();
    }

}
