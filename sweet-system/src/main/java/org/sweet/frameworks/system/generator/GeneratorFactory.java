/**
 * 
 */
package org.sweet.frameworks.system.generator;

import java.util.Map;
import java.util.Map.Entry;

/**
 * 生成器工厂(GeneratorFactory)
 * @filename:GeneratorFactory
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2017年2月27日
 * @modifyrecords:
 */
public final class GeneratorFactory {
	public static final String AUTO="auto";
	public static final String UUID="uuid";

	/**
	 * 数据动态生成
	 * @param data 源数据Map
	 * @param generatorMap 键生成配置Map
	 */
	public static void generate(Map<String,Object> data,Map<String,Object> generatorMap){
		if(null!=generatorMap&&generatorMap.size()>0){
			for(Entry<String,Object> ent:generatorMap.entrySet()){
				if(GeneratorFactory.AUTO.equals(ent.getValue())||GeneratorFactory.UUID.equals(ent.getValue())){
					/* UUID */
					new UUIDGenerator().generate(ent.getKey(),data);
				}
			}
		}
	}
}
