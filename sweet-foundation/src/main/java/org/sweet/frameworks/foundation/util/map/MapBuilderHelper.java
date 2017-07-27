package org.sweet.frameworks.foundation.util.map;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MapBuilderHelper implements Serializable {
	private static final long serialVersionUID=-8735282053051286625L;
	private Map<String,Object> map=new HashMap<String,Object>();

	/**
	 * 设置参数
	 * @param key
	 * @param value
	 * @return
	 */
	public MapBuilderHelper put(String key,Object value){
		this.map.put(key,value);
		return this;
	}

	/**
	 * 创建Map
	 * @return
	 */
	public Map<String,Object> build(){
		return this.map;
	}

	/**
	 * 创建Json
	 * @return
	 */
	public String toJson(){
		if(this.map.size()>0){
			StringBuilder builder=new StringBuilder();
			builder.append("{");
			for(Map.Entry<String,Object> entry:this.map.entrySet()){
				builder.append("\""+entry.getKey()+"\"").append(": ").append("\""+entry.getValue()+"\"");
				builder.append(", ");
			}
			return new StringBuilder(builder.subSequence(0,builder.lastIndexOf(","))).append("}").toString();
		}
		return "{}";
	}
}