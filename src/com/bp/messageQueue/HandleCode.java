package com.bp.messageQueue;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author current_bp
 * @time 20160427
 *
 */
public class HandleCode {

	
	/**
	 * 调用该类的方法,此处可以使用json格式
	 * @param info
	 * @return
	 */
	public String invokeMethod(String info){
		return invokeMethod( "com.bp.messageQueueService", info);
	}
	public String invokeMethod(String path,String info){
		List infos = parseInfo(info);
		System.out.println("infos:"+infos);
		String className = infos.get(0).toString();
		String functionName  = infos.get(1).toString();
		String params = infos.get(2).toString();
		Class c;
		Object result = null;
		Object instance = null;
		
		
		try {
			instance = Class.forName(path+"."+className).newInstance();
			c = instance.getClass();
			//方法名称不能重复
			Method m1 = getMethod(functionName,Arrays.asList(c.getMethods()));
			System.out.println("method:Name:"+m1.getName());
			result = m1.invoke(instance, getParams(m1,params));
			
		} catch (Exception  e) {
			e.printStackTrace();
		}
		
		return result.toString();
	}
	
	/**
	 * 根据方法的对应类型的参数获得所需的参数数组
	 * @param m1
	 * @param params
	 * @return
	 */
	private Object[] getParams(Method m1, String params) {
		System.out.println("m1.getName:"+m1.getName());
		//TODO 这个项目有问题
		List a = new ArrayList<>();
		List<String> paramsList = Arrays.asList(params.split(","));
		List<Class<?>> params1 = Arrays.asList(m1.getParameterTypes());
		
		if(paramsList.size() != params1.size()){
			throw new RuntimeException("输入参数值于方法中参数个数不一致！");
		}
		
		
		for(int i=0;i<params1.size();i++){
			String type = m1.getParameterTypes()[i].getName();
			if(type.equals("int") || type.equals("java.lang.Integer")){
				a.add(i,new Integer(paramsList.get(i).toString()));
			}else if(type.equals("float") || type.equals("java.lang.Float")){
				a.add(i,new Float(paramsList.get(i).toString()));
			}else if(type.equals("double") || type.equals("java.lang.Double")){
				a.add(i,new Double(paramsList.get(i).toString()));
			}else{
				a.add(i,new String(paramsList.get(i).toString()));
			}
		}
		return a.toArray();
	}
	/**
	 * 根据方法名称获得对应的方法
	 * @return
	 */
	public Method getMethod(String methodName,List l){
		Method m = null;
		//
		if(null != methodName && !"".equals(methodName)){
			for(int i=0;i<l.size();i++){
				if(((Method)l.get(i)).getName().equals(methodName)){
					m = (Method)l.get(i);
				}
			}
		}
		return m;
	}

	/**
	 * 解析字符串，生成：类，方法，值
	 * @param info
	 * @return
	 */
	private List parseInfo(String info) {
		List result = new ArrayList();
		String[] infos = info.split(":");
		for(int i=0;i<infos.length;i++){
			result.add(infos[i]);
		}
		
		return result;
	}
	
	
	public static void main(String[] args) {
		HandleCode cn = new HandleCode();
		
		System.out.println(cn.invokeMethod("ssss"));
	}
}
