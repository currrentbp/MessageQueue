package com.bp.messageQueueService;

import java.util.Map;
import java.util.TreeMap;

/**
 * 这是一个抢购活动的例子，
 * 在这个类中的方法不能重名，
 * 这个类中的方法尽量简短，减少单线程排队所造成的时间耗。
 * 
 * @author current_bp
 * @time 20160427
 *
 */
public class CountNum {
	
	private static int count=0;
	private static Map<String ,Integer> activityCount = new TreeMap<String,Integer>();
	
	public synchronized String addCount(String activityId,int num){
		this.count = this.count+ num;
		System.out.println("addCount count:"+count);
		
		return ""+count;
	}
	
	public synchronized String multCount(String activityId,int num){
		this.count = this.count - num;
		System.out.println("multCount count:"+count);
		
		return ""+count;
	}
	
	
	
	

}
