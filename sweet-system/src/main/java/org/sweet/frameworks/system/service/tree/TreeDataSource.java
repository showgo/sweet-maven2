package org.sweet.frameworks.system.service.tree;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.sweet.frameworks.database.dao.service.Service;
import org.sweet.frameworks.system.service.datasource.ITreeDataSource;
import org.sweet.frameworks.ui.components.panel.tree.TreeModel;

public class TreeDataSource extends Service implements ITreeDataSource {
	protected TreeModel model=null;

	public TreeDataSource(HttpServletRequest request){
		super(request);
	}

	public void setModel(TreeModel model){
		this.model=model;
	}

	public TreeModel getModel(){
		return model;
	}

	public Object getData(String id,Map<String,Object> parameter) throws SQLException{
		List<Map<String,Object>> list=this.query(id,parameter);
		if(null!=list) {
			Map<String,List<Map<String,Object>>> group=this.groupedBy(list,this.model.getParentId());
			Object parentId=parameter.containsKey(TreeModel.TreeFields.TREE_ID) ? parameter.get(TreeModel.TreeFields.TREE_ID) : null;
			return this.getNodes(group,parentId,parameter);
		}
		return null;
	}

	/**
	 * 获得节点
	 * @param group
	 * @param parentId
	 * @param parameter
	 * @return
	 */
	protected List<Map<String,Object>> getNodes(Map<String,List<Map<String,Object>>> group,Object parentId,Map<String,Object> parameter){
		List<Map<String,Object>> nodes=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> list=null!=parentId ? group.get(parentId.toString()) : group.get(TreeModel.TreeFields.TREE_ROOT);
		if(null!=list) {
			for(Map<String,Object> child:list){
				Map<String,Object> node=new HashMap<String,Object>();
				node.put("id",child.get(this.model.getId()));
				node.put("text",child.get(this.model.getText()));
				node.put("checked","1".equals(child.get(this.model.isChecked())) ? true : false);
				node.put("attributes",child);
				List<Map<String,Object>> childNodes=this.getNodes(group,child.get(this.model.getId()),parameter);
				if(null!=childNodes&&childNodes.size()>0) {
					node.put("state","1".equals(null!=child.get(this.model.isExpand()) ? child.get(this.model.isExpand()).toString() : "0") ? "open" : "closed");
					if("true".equals(parameter.get(this.model.getTreeSync()))) {
						node.put("children",childNodes);
					}
					node.put("isLeaf",false);
				}else{
					node.put("isLeaf",true);
				}
				nodes.add(node);
			}
		}
		return nodes;
	}

	/**
	 * 数据分组
	 * @param data 数据
	 * @param field 分组字段
	 * @return
	 */
	protected Map<String,List<Map<String,Object>>> groupedBy(List<Map<String,Object>> data,String field){
		Map<String,List<Map<String,Object>>> group=new HashMap<String,List<Map<String,Object>>>();
		for(Map<String,Object> dat:data){
			Object parentId=dat.get(field);
			if(null!=parentId) {
				if(!group.containsKey(parentId)) {
					group.put(parentId.toString(),new ArrayList<Map<String,Object>>());
				}
				group.get(parentId.toString()).add(dat);
			}else{
				if(!group.containsKey(TreeModel.TreeFields.TREE_ROOT)) {
					group.put(TreeModel.TreeFields.TREE_ROOT,new ArrayList<Map<String,Object>>());
				}
				group.get(TreeModel.TreeFields.TREE_ROOT).add(dat);
			}
		}
		return group;
	}
}
