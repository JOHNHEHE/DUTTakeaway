package com.dut.duttake_away;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import utils.LogUtil;

/**
 * Created by 张浩天
 */

public class ChangePasswordActivity extends AppCompatActivity {

    final  String TAG="ChangePassword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Button changePas =  findViewById(R.id.bnUpdatePassword);
        changePas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.i(TAG,"用户确认修改密码");
                EditText oldPasswordText=findViewById(R.id.oldPasswordText);
                String oldPassword=oldPasswordText.getText().toString();
                LogUtil.i(TAG,"旧密码"+oldPassword);
                EditText newPasswordText=findViewById(R.id.newPasswordText);
                String newPassword=newPasswordText.getText().toString();
                if(newPassword.equals("")||newPassword.trim().isEmpty()){
                    Toast.makeText(ChangePasswordActivity.this, "密码不能为空哦", Toast.LENGTH_SHORT).show();
                }
                else{
                    LogUtil.i(TAG,"新密码"+newPassword);
                    updatePassword(oldPassword,newPassword);
                }
            }
        });
    }

    //修改密码
    public void updatePassword(String oldPassword,String newPassword){
        BmobUser.updateCurrentUserPassword(oldPassword, newPassword, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    LogUtil.i(TAG,"修改密码成功");
                    Toast.makeText(ChangePasswordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(ChangePasswordActivity.this,PersonalInfoActivity.class);
                    startActivity(i);
                } else {
                    LogUtil.i(TAG,"修改密码失败");
                    Toast.makeText(ChangePasswordActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
