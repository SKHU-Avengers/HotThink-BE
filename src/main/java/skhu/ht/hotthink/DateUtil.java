package skhu.ht.hotthink;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class DateUtil {
    static Calendar cal;

    /*
        작성자: 홍민석
        작성일: 19-10-23
        내용: 인자로 받은 날짜를 인자로 받은 day동안
        연장시킨 값을 반환합니다.
     */
    public static Date addDate(Date date, int day){
        cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR,day);
        return date;
    }

    /*
        작성자: 홍민석
        작성일: 19-10-23
        내용: 날짜를 입력받아 지금 보다 해당 날짜가 나중이거나 같으면 true를 반환하고,
        그렇지 않으면 false를 반환합니다.
     */
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
