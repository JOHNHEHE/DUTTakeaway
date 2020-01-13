package db.tools;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import utils.LogUtil;

public class Tool {

    private boolean result = false;//判断操作是否成功
    final String TAG="增删改";
    //插入数据
    public void add(BmobObject object){
        object.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    result = true;
                    LogUtil.i(TAG,"添加成功啦");
                } else {
                    result = false;
                    LogUtil.i(TAG,"添加失败啦");
                }
            }
        });
    }

    //更新数据
    public void update(BmobObject object){
        object.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    result = true;
                    LogUtil.i(TAG,"更新成功啦");
                } else {
                    result = false;
                    LogUtil.i(TAG,"更新失败啦");
                }
            }
        });
    }

    //删除数据
    public void delete(BmobObject object){
        object.delete(object.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    result = true;
                    LogUtil.i(TAG,"删除成功啦");
                } else {
                    result = false;
                    LogUtil.i(TAG,"删除成功啦");
                }
            }
        });
    }

}