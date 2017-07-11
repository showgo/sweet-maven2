package org.sweet.frameworks.system.generator;

import java.util.Map;

/**
 * IGenerator
 * @filename:IGenerator
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2017年2月27日
 * @modifyrecords:
 */
public interface IGenerator {
	/**
	 * 数据动态生成
	 * @param key 目标键
	 * @param data 源数据Map
	 */
	public void generate(String key,Map<String,Object> data);
}
