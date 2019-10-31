package skhu.ht.hotthink;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class DateUtil {
    static Calendar cal;

    public static Date addDate(Date date, int day){
        cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR,day);
        return date;
    }

    public static boolean isValid(Date date){
        Calendar tmp;//now
        tmp = Calendar.getInstance();
        tmp.setTime(new Date());
        cal = Calendar.getInstance();
        cal.setTime(date);//expired date
        if(cal.compareTo(tmp) != -1){ //만료일이 지금보다 나중이거나 같으면
            return true;
        }
        return false;
    }

}
