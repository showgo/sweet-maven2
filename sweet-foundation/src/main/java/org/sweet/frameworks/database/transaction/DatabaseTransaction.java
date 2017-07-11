package org.sweet.frameworks.database.transaction;

import java.sql.SQLException;
import org.sweet.frameworks.database.core.DatabaseSession;
import org.sweet.frameworks.database.core.DatabaseUtil;

/**
 * 事务管理(统一事务管理)
 * @filename:DatabaseTransaction
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public abstract class DatabaseTransaction {
	private String dasName=null;

	/**
	 * 构造函数
	 * @param dasName
	 */
	public DatabaseTransaction(String dasName){
		this.dasName=dasName;
	}

	/**
	 * (统一)事务处理
	 * @throws SQLException
	 */
	public int[] doTransaction() throws SQLException{
		int[] updated=null;
		DatabaseSession session=null;
		try{
			session=DatabaseUtil.getSession(dasName);
			updated=this.doTransaction(session);
			session.commit();
		}catch(SQLException excep){
			session.rollback();
			throw excep;
		}finally{
			if(null!=session) {
				session.close();
			}
		}
		return updated;
	}

	/**
	 * 用户要执行的操作系列
	 * @param session
	 * @throws SQLException
	 */
	protected abstract int[] doTransaction(DatabaseSession session) throws SQLException;
}
