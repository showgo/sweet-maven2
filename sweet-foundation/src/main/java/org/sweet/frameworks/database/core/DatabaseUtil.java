/**
 * 
 */
package org.sweet.frameworks.database.core;

import org.sweet.frameworks.database.envir.DBEnvironment;
import org.sweet.frameworks.database.envir.DBEnvironments;
import org.sweet.frameworks.foundation.util.string.StringUtil;

/**
 * 数据库实用程序(DatabaseUtil)
 * @filename:DatabaseUtil
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-26
 */
public final class DatabaseUtil {
	private static ThreadLocal<DatabaseSession> session=null;

	/**
	 * 获取会话
	 * @return
	 */
	public static DatabaseSession getSession(){
		return getSession(DBEnvironments.getDefaultEnvironment());
	}

	/**
	 * 获取会话
	 * @param dasName
	 * @return
	 */
	public static DatabaseSession getSession(String dasName){
		return StringUtil.isNotEmpty(dasName) ? getSession(DBEnvironments.getEnvironment(dasName)) : null;
	}

	/**
	 * 获取会话
	 * @param dbEnvir 数据库环境
	 * @return
	 */
	private static DatabaseSession getSession(final DBEnvironment dbEnvir){
		if(null!=session){
			session.get();
		}
		session=new ThreadLocal<DatabaseSession>() {
			protected DatabaseSession initialValue(){
				return null!=dbEnvir ? new DatabaseSession(dbEnvir) : null;
			}
		};
		return session.get();
	}
}
