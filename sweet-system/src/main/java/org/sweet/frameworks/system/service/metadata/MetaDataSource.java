package org.sweet.frameworks.system.service.metadata;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.sweet.frameworks.database.dao.service.ServiceForMetaData;
import org.sweet.frameworks.foundation.util.map.MapUtil;
import org.sweet.frameworks.system.service.datasource.IDataSource;
import org.sweet.frameworks.system.service.grid.pagination.Pagination;

public class MetaDataSource extends ServiceForMetaData implements IDataSource {
	private HttpServletRequest request;

	public MetaDataSource(HttpServletRequest request){
		this.request=request;
	}

	public Object getData(String id,Map<String,Object> parameter) throws SQLException{
		Map<String,Object> data=MapUtil.getParameterMap(request);
		String schema=null!=data.get("table_schema") ? data.get("table_schema").toString() : null;
		String table=null!=data.get("table_name") ? data.get("table_name").toString() : null;
		String type=null!=data.get("type") ? data.get("type").toString() : null;
		List<Map<String,Object>> list=null;
		if("table".equals(type)){
			list=this.getTable(schema,table);
		}else if("view".equals(type)){
			list=this.getView(schema,table);
		}else if("column".equals(type)){
			list=this.getColumns(schema,table);
		}
		if(null!=list){
			int pageNumb=Integer.valueOf(parameter.get(Pagination.PAGE_NUMB).toString()).intValue();
			int pageSize=Integer.valueOf(parameter.get(Pagination.PAGE_SIZE).toString()).intValue();
			int fromIndex=(pageNumb-1)*pageSize;
			int toIndex=(pageSize>0&&(pageNumb*pageSize)<list.size()) ? (pageNumb*pageSize) : (list.size());
			Map<String,Object> map=new HashMap<String,Object>();
			map.put(Pagination.PAGE_TOTAL,list.size());
			map.put(Pagination.PAGE_ROWS,list.subList(fromIndex,toIndex));
			return map;
		}
		return null;
	}
}
