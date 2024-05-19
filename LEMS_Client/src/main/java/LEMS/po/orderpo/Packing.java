package LEMS.po.orderpo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

/**
 *  
 * 
 * 货物包装类型
 */
public enum Packing implements Serializable {
	Carton, // 纸箱
	Wooden, // 木箱
	Bag, // 快递袋
	Other// 其它
	;
	
	private static HashMap<String, Packing> typeList = new HashMap<>();
	
	static {
		typeList.put("快递袋", Packing.Bag);
		typeList.put("纸箱", Packing.Carton);
		typeList.put("木箱", Packing.Wooden);
		typeList.put("其它", Packing.Other);
	}
	
	/**
	 * 将包装类型的中文转换为对应的包装类型
	 */
	public static Packing transfer(String type) {
		return typeList.get(type);
	}
	
	/**
	 * 将快递类型转换为对应的中文
	 */	
	public static String transfer(Packing type) {
		Set<Entry<String, Packing>> temp = typeList.entrySet();
		for (Entry<String, Packing> entry : temp) {
			if (entry.getValue() == type) {
				return entry.getKey();
			}
		}
		
		return null;
	}
}
