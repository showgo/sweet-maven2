package org.sweet.frameworks.database.sql;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.sweet.frameworks.database.core.DatabaseSession;

/**
 * SQL
 * @filename:SQL
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public abstract class SQL {
	protected DatabaseSession session=null;

	public SQL(){
	}

	/**
	 * 执行语句
	 * @param id 命名sql的全名
	 * @param parameter 用于动态解析sql的参数
	 * @return
	 * @throws SQLException
	 */
	protected int execute(String id,Object parameter) throws SQLException{
		return this.session.update(id,parameter);
	}

	/**
	 * 执行sql语句
	 * @param sql sql语句
	 * @param parameter 参数
	 * @return 执行结果标志(或标志数组)
	 * @throws SQLException
	 */
	protected int executeSQL(String sql,Object parameter) throws SQLException{
		return this.session.updateSQL(sql,parameter);
	}

	/**
	 * 执行查询语句
	 * @param id 命名sql的全名O
	 * @param parameter 用于动态解析sql的参数
	 * @return
	 * @throws SQLException
	 */
	protected List<Map<String,Object>> query(String id,Object parameter) throws SQLException{
		return this.session.query(id,parameter);
	}

	/**
	 * 获得单条结果集
	 * @param id 命名sql的全名
	 * @param parameter 用于动态解析sql的参数
	 * @return
	 * @throws SQLException
	 */
	protected Map<String,Object> queryMap(String id,Object parameter) throws SQLException{
		List<Map<String,Object>> list=this.session.query(id,parameter);
		if(null!=list&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 执行查询语句
	 * @param sql sql语句
	 * @param parameter 参数
	 * @return 查询结果集
	 * @throws SQLException
	 */
	protected List<Map<String,Object>> querySQL(String sql,Object parameter) throws SQLException{
		return this.session.querySQL(sql,parameter);
	}

	/**
	 * 内部调用的方法,不建议外部调用
	 * @param session
	 * @throws SQLException
	 */
	public int[] execute(DatabaseSession session) throws SQLException{
		this.session=session;
		if(this.statements()){
			return new int[]{1};
		}
		return new int[]{0};
	}

	/**
	 * 定义可运行的语句序列(内部使用,不建议外部调用)
	 * @return
	 * @throws SQLException
	 */
	protected abstract boolean statements() throws SQLException;
}
