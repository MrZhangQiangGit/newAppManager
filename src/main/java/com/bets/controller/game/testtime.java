package com.bets.controller.game;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class testtime {
	public static void  main(String[] args){

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int d = 0;
		if(cal.get(Calendar.DAY_OF_WEEK)==1){
			d = -6;
		}else{
			d = 2-cal.get(Calendar.DAY_OF_WEEK);
		}
		cal.add(Calendar.DAY_OF_WEEK, d);
		//所在周开始日期 本周一
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
		cal.add(Calendar.DAY_OF_WEEK, 6);
		//所在周结束日期 本周日
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
		//上周日
		cal.add(Calendar.DAY_OF_WEEK, -7);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
		//上周一
		cal.add(Calendar.DAY_OF_WEEK, -6);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
	}
}
