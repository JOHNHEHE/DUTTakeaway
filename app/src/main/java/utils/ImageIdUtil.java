package utils;
/**
 * Created by 张浩天
 */

public class ImageIdUtil {

    public String getImageID(String storename){
        if(storename.equals("百碗香排骨米饭")){
            return "store_bdpkrrrw";
        }
        else if(storename.equals("乐而美汉堡")){
            return "store_s8nzyyyk";
        }
        else if(storename.equals("升和私房菜")){
            return "store_bqe5999m";
        }
        else if(storename.equals("大叔私房茶")){
            return "store_cmi8sssm";
        }
        else if(storename.equals("金饭村")){
            return "store_wzaa000s";
        }
        else if(storename.equals("法式红酒牛排饭")){
            return "store_qez1999i";
        }
        else if(storename.equals("西爷龙虾")){
            return "store_17f9a57ec3";
        }
        else if(storename.equals("缘味鲜")){
            return "store_6bbda42d9a";
        }
        else if(storename.equals("肌肉兄弟轻食")){
            return "store_3fded7e655";
        }
        else if(storename.equals("米谷家寿司料理")){
            return "store_tldd999p";
        }
        return null;
    }
}
