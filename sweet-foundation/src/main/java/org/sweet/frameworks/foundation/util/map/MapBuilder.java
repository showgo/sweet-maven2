package org.sweet.frameworks.foundation.util.map;

/**
 * 参数Map
 * @Filename: MapBuilder
 * @Company:
 * @Author: wugz
 * @Create: 2017年7月27日
 * @Version: 1.0.0
 * @ModifyRecords:
 */
public class MapBuilder {
	/**
	 * 构造函数
	 */
	private MapBuilder(){
	}

	/**
	 * 设置参数
	 * @param key
	 * @param value
	 * @return
	 */
	public static MapBuilderHelper put(String key,Object value){
		MapBuilderHelper helper=new MapBuilderHelper();
		helper.put(key,value);
		return helper;
	}
}