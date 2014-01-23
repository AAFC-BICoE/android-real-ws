package ca.gc.agr.mbb.androidmockws;

import java.util.Random;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util{
    static final Random r = new Random();

    public static final String makeRandomString(final int length, final boolean insertSpaces){
	if(length < 0){
	    return "Error: makeRandomString < 0";
	}

	StringBuilder sb = new StringBuilder(length);
	for(int i=0; i<length; i++){
	    if(insertSpaces && i > 3 && r.nextInt(100) < 18){
		sb.append(" ");
	    }else{
		sb.append((char) (r.nextInt(26) + 'a'));
	    }
	}
	return sb.toString();
    }

    public static final String makeRandomString(final int length){
	return makeRandomString(length, false);
    }

    public static String randomDate(){
	return randomDate(0);
    }

    public static String randomDate(final int yearOffset){
	return randomDate(yearOffset, 0);
	}

    public static String randomDate(final int yearOffset, final float probablilityNull){
	if(r.nextInt(100) < probablilityNull){
	    return null;
	}

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Calendar cal = Calendar.getInstance();
	cal.set(2010-yearOffset +r.nextInt(3), 
		r.nextInt(11), 
		r.nextInt(30), 

		r.nextInt(23), 
		r.nextInt(60));
		
	return dateFormat.format(cal.getTime());
    }

    public static String randomDate(final float probablilityNull){
	return randomDate(0, probablilityNull);
    }

}
