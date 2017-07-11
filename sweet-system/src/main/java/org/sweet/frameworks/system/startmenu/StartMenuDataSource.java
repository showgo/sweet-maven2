package org.sweet.frameworks.system.startmenu;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.sweet.frameworks.system.service.datasource.ITreeDataSource;
import org.sweet.frameworks.system.service.tree.TreeDataSource;
import org.sweet.frameworks.ui.components.panel.tree.TreeModel;

public class StartMenuDataSource extends TreeDataSource implements ITreeDataSource {
	public StartMenuDataSource(HttpServletRequest request){
		super(request);
	}

	public Object getData(String id,Map<String,Object> parameter) throws SQLException{
		List<Map<String,Object>> list=this.query(id,parameter);
		if(null!=list){
			/* 修改function_id和parent_id */
			for(Map<String,Object> map:list){
				if(null!=map.get(this.model.getParentId())&&!"".equals(map.get(this.model.getParentId()))){
					map.put(this.model.getParentId(),TreeModel.TreeFields.TREE_NODE_PREFIX+map.get(this.model.getParentId()).toString());
				}
				map.put(this.model.getId(),TreeModel.TreeFields.TREE_NODE_PREFIX+map.get(this.model.getId()));
			}
			Map<String,List<Map<String,Object>>> group=this.groupedBy(list,this.model.getParentId());
			Object parentId=parameter.containsKey(TreeModel.TreeFields.TREE_ID) ? parameter.get(TreeModel.TreeFields.TREE_ID) : null;
			return this.getNodes(group,parentId,parameter);
		}
		return null;
	}
}
