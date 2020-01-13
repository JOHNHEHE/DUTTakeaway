package utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 张浩天
 */

public class NetworkUtil {

    final String TAG="网络连接";

    public boolean isConnectedNetwork(){
        Runtime runtime = Runtime.getRuntime();
        try {
            Process p = runtime.exec("ping -p 1 -w 1 www.baidu.com");
            int ret = p.waitFor();
            if(ret==0){
                LogUtil.i(TAG,"已经连接网络");
                return true;
            }
            else{
                LogUtil.i(TAG,"未连接网络");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean isOnline() {
        URL url;
        try {
            url = new URL("https://www.baidu.com");
            InputStream stream = url.openStream();
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
