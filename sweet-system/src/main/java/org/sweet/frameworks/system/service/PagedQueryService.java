package org.sweet.frameworks.system.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.sweet.frameworks.database.dao.DAO;
import org.sweet.frameworks.database.dao.DAOFactory;
import org.sweet.frameworks.system.service.grid.pagination.Pagination;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 分页查询
 * @Filename: PagedQueryService
 * @Company:
 * @Author: wugz
 * @Create: 2017年7月10日
 * @Version: 1.0.0
 * @ModifyRecords:
 */
public class PagedQueryService {
	private DAO dao=DAOFactory.getDAO();

	public PagedQueryService(HttpServletRequest request){
	}

	/**
	 * 数据库分页查询
	 * @param id 命名sql的全名
	 * @param parameter 用于动态解析sql的参数
	 * @param startIndex 开始获取的行(startIndex=0,1,2...)
	 * @param rows 获取行数
	 * @return
	 * @throws SQLException
	 */
	public Map<String,Object> pagedQuery(String id,Object parameter,int startIndex,int rows) throws SQLException{
		Map<String,Object> data=new HashMap<String,Object>();
		Page<Map<String,Object>> page=PageHelper.offsetPage(startIndex,rows);
		this.dao.query(id,parameter);
		data.put(Pagination.PAGE_TOTAL,page.getTotal());
		data.put(Pagination.PAGE_ROWS,page.getResult());
		return data;
	}
}
