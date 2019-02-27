package cn.itcast.spring.ioc.spring.collection;

import java.util.List;
import java.util.Properties;
//注入集合
import java.util.Map;
public class Bean9 {
	private List<String> names;
	private Map<String, String> map;
	private Properties proper;
	
	public void setProper(Properties proper) {
		this.proper = proper;
	}
	public void setNames(List<String> names) {
		this.names = names;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public void fn(){
		for(String s:names){
			System.out.println(s);
		}
		System.out.println("---------------------");
		System.out.println(map);
		System.out.println("---------------------");
		System.out.println(proper);
	}
}
