package org.sweet.frameworks.system.service.datasource;

import java.sql.SQLException;
import java.util.Map;

public abstract interface IDataSource {
	/**
	 * 获得数据
	 * @param id sql语句配置id
	 * @param parameter sql参数
	 * @return
	 * @throws SQLException
	 */
	public Object getData(String id,Map<String,Object> parameter) throws SQLException;
}