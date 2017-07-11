package org.sweet.frameworks.database.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.sweet.frameworks.database.envir.DBEnvironment;
import org.sweet.frameworks.database.envir.DBEnvironments;
import org.sweet.frameworks.foundation.util.debug.Debug;

/**
 * 数据库配置工厂(DatabaseConfigFactory)
 * @filename:DatabaseConfigFactory
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:
 * @modifyrecords:
 */
public final class DatabaseConfigFactory {
	private static Map<String,SqlSessionFactory> sessionFactory=new ConcurrentHashMap<String,SqlSessionFactory>();

	/**
	 * 加载配置
	 * @param configXML
	 */
	public static void loadConfigurations(final String configXML){
		try{
			/* 构建SqlSessionFactory */
			InputStream inputStream=Resources.getResourceAsStream(configXML);
			SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
			for(Map.Entry<String,DBEnvironment> entry:DBEnvironments.getEnvironments().entrySet()){
				SqlSessionFactory sqlSessionFactory=null;
				try{
					sqlSessionFactory=builder.build(inputStream,entry.getKey());
				}catch(Exception e){
					Debug.error(DatabaseConfigFactory.class,"Create SqlSessionFactory failure: environment -> "+entry.getKey());
				}
				if(null!=sqlSessionFactory) {
					sessionFactory.put(entry.getKey(),sqlSessionFactory);
				}
			}
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}

	/**
	 * 返回会话工厂
	 * @param dasName
	 * @return
	 */
	public static SqlSessionFactory getSessionFactory(String dasName){
		if(null!=sessionFactory) {
			return sessionFactory.get(dasName);
		}
		return null;
	}
}
