package org.sweet.frameworks.system.generator;

import java.util.Map;

import org.sweet.frameworks.foundation.util.string.StringUtil;

/**
 * UUIDGenerator
 * @filename:UUIDGenerator
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2017年2月27日
 * @modifyrecords:
 */
public class UUIDGenerator implements IGenerator {
	public void generate(String key,Map<String,Object> data){
		if(null!=data){
			data.put(key,StringUtil.uuid());
		}
	}
}
