package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 张浩天
 */

public class DateUtil {

    public static Date transferDateTime(String dateStr) {
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date=null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String transferDateToString(Date date){
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateStr = sdf.format(date);
        return dateStr;
    }

    //判断是否更新订单状态为已完成
    public boolean isEnd(String sendtime,int deliverytime){
        Date date = this.transferDateTime(sendtime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, deliverytime);
        Date sendDate=calendar.getTime();
        Date nowDate = new Date(System.currentTimeMillis());
        if(nowDate.compareTo(sendDate)==1){
            return true;
        }
        return false;
    }
}
