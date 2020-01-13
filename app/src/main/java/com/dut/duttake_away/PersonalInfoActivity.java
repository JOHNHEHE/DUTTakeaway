package com.dut.duttake_away;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import entity.User;
import proxy.UserProxy;
import utils.LogUtil;

/**
 * Created by 张浩天
 */

public class PersonalInfoActivity extends AppCompatActivity {

    final  String TAG="MyInformation";
    User currentuser=this.getUser();
    private ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalinfo);
        picture=findViewById(R.id.imagePortrait);
        initialInfo();  //初始化用户信息

        Button order=findViewById(R.id.order);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.i(TAG,"前往订单页面");
                Intent i=new Intent(PersonalInfoActivity.this,OrderActivity.class);
                startActivity(i);
                overridePendingTransition(0, android.R.anim.fade_out);
                finish();
            }
        });
        Button home=findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.i(TAG,"前往主页面");
                Intent i=new Intent(PersonalInfoActivity.this,StoreActivity.class);
                startActivity(i);
                overridePendingTransition(0, android.R.anim.fade_out);
                finish();
            }
        });
        Button login =  findViewById(R.id.bnLogout);    //退出登录
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProxy userProxy=new UserProxy();
                userProxy.logout();
                LogUtil.i(TAG,"用户退出登录");
                Intent i=new Intent(PersonalInfoActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        Button changeLoc =  findViewById(R.id.bnManageLoc);    //修改地址
        changeLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.i(TAG,"用户选择修改地址");
                Intent i=new Intent(PersonalInfoActivity.this,ManageAddressActivity.class);
                startActivity(i);
            }
        });

        Button changePas =  findViewById(R.id.bnChangePassword);    //修改密码
        changePas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.i(TAG,"用户选择修改密码");
                Intent i=new Intent(PersonalInfoActivity.this,ChangePasswordActivity.class);
                startActivity(i);
            }
        });

        //选择头像
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.i(TAG,"用户选择修改头像");
                if(ContextCompat.checkSelfPermission(PersonalInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(PersonalInfoActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else{
                    openAlbum();
                }
            }
        });
    }

    protected User getUser(){
        UserProxy userProxy=new UserProxy();
        return userProxy.getCurrentUser();
    }

    //初始化用户信息
    public void initialInfo(){
        TextView currentUsername = findViewById(R.id.currentUsername);
        currentUsername.setText(currentuser.getName());
        TextView currentUserAdd = findViewById(R.id.currentUserAdd);
        //若是修改地址返回页面，因为后台更新有延迟，先直接从修改地址页面拿到修改的的地址
        if(getIntent().getStringExtra("address")!=null){
            currentUserAdd.setText(getIntent().getStringExtra("address"));
        }
        else{
            currentUserAdd.setText(currentuser.getAddress());
        }
        //如果之前本地保存有头像，就读取该头像，否则使用默认头像
        File file = new File(Environment.getExternalStorageDirectory().getPath()+ "/smallIcon", currentuser.getUsername() + ".jpg");
        if(file.exists()){
           displayImage(Environment.getExternalStorageDirectory().getAbsolutePath() + "/smallIcon/"+currentuser.getUsername() + ".jpg");
        }
        TextView currentUserPhone = findViewById(R.id.currentUserPhone);
        currentUserPhone.setText(currentuser.getMobilePhoneNumber());
        TextView currentUserMoney = findViewById(R.id.currentUserMoney);
        currentUserMoney.setText(currentuser.getCash().toString());
    }

    //发送打开相册请求
    private void openAlbum(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else{
                    Toast.makeText(this, "你拒绝申请权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestcode,int resultcode,Intent data){
        //处理如果用户不选择图片直接退出的情况
        if(data==null){
            return;
        }
        handleImageOnKitKat(data);
    }

    //对4.4以上系统处理图片
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        String imagePath=null;
        Uri uri=data.getData();
        String docID= DocumentsContract.getDocumentId(uri);
        if(DocumentsContract.isDocumentUri(this,uri)){
            assert uri != null;
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docID.split(":")[1];
                String selection= MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }
        }else {
            assert uri != null;
            if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docID));
                imagePath=getImagePath(contentUri,null);
            }else if("content".equalsIgnoreCase(uri.getScheme())){
                imagePath=getImagePath(uri,null);
            }else if("file".equalsIgnoreCase(uri.getScheme())){
                imagePath=getImagePath(uri,null);
            }
        }
        Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
        storeImage(bitmap);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection){
        String path=null;
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath){
        if(imagePath!=null){
            Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        }else{
            Toast.makeText(this,"failed to get image",Toast.LENGTH_SHORT).show();
        }
    }

    //将头像保存到本地
    private void storeImage(Bitmap photo){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String storage = Environment.getExternalStorageDirectory().getAbsolutePath();
            File dirFile = new File(storage + "/smallIcon");
            if (!dirFile.exists()) {
                if (!dirFile.mkdirs()) {
                    LogUtil.e("TAG", "文件夹创建失败");
                } else {
                    LogUtil.e("TAG", "文件夹创建成功");
                }
            }
            File file = new File(dirFile, currentuser.getUsername() + ".jpg");
            // 保存图片
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(file);
                photo.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*@Override
    protected  void onPause() {
        super.onPause();
        this.finish();
    }*/
}
