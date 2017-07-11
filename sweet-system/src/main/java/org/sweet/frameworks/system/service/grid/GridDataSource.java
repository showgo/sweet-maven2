package org.sweet.frameworks.system.service.grid;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.sweet.frameworks.system.service.PagedQueryService;
import org.sweet.frameworks.system.service.datasource.IDataSource;
import org.sweet.frameworks.system.service.grid.pagination.Pagination;

public class GridDataSource extends PagedQueryService implements IDataSource {
	public GridDataSource(HttpServletRequest request){
		super(request);
	}

	public Object getData(String id,Map<String,Object> parameter) throws SQLException{
		int pageNumb=Integer.valueOf(parameter.get(Pagination.PAGE_NUMB).toString()).intValue();
		int pageSize=Integer.valueOf(parameter.get(Pagination.PAGE_SIZE).toString()).intValue();
		int fromIndex=(pageNumb-1)*pageSize;
		Map<String,Object> map=this.pagedQuery(id,parameter,fromIndex,pageSize);
		if(null!=map&&map.size()>0){
			return map;
		}
		return null;
	}
}
