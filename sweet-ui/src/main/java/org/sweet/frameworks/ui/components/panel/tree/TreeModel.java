package org.sweet.frameworks.ui.components.panel.tree;

import java.util.HashMap;
import java.util.Map;

/**
 * 树模型TreeModel
 * @filename:TreeModel
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016年10月2日
 * @modifyrecords:
 */
public final class TreeModel {
	private String treeId=null;
	private String treeSync=null;
	private String treeChecked=null;
	private String treeCascadeChecked=null;
	private String treeOnlyLeafChecked=null;
	private String id=null;
	private String text=null;
	private String parentId=null;
	private String action=null;
	private String nodeCls=null;
	private String seque=null;
	private String isExpand=null;
	private String isChecked=null;

	/**
	 * 构造函数
	 */
	public TreeModel(){
		this(new HashMap<String,Object>());
	}

	/**
	 * 构造函数
	 * @param model
	 */
	public TreeModel(Map<String,Object> model){
		Map<String,Object> treeModel=new HashMap<String,Object>();
		if(null!=model&&model.size()>0){
			treeModel.putAll(model);
		}
		this.treeId=null!=treeModel.get(TreeFields.TREE_ID) ? treeModel.get(TreeFields.TREE_ID).toString() : TreeFields.TREE_ID;
		this.treeSync=null!=treeModel.get(TreeFields.TREE_SYNC) ? treeModel.get(TreeFields.TREE_SYNC).toString() : TreeFields.TREE_SYNC;
		this.treeChecked=null!=treeModel.get(TreeFields.TREE_CHECKED) ? treeModel.get(TreeFields.TREE_CHECKED).toString() : TreeFields.TREE_CHECKED;
		this.treeCascadeChecked=null!=treeModel.get(TreeFields.TREE_CASCADE_CHECKED) ? treeModel.get(TreeFields.TREE_CASCADE_CHECKED).toString() : TreeFields.TREE_CASCADE_CHECKED;
		this.treeOnlyLeafChecked=null!=treeModel.get(TreeFields.TREE_ONLYLEAF_CHECKED) ? treeModel.get(TreeFields.TREE_ONLYLEAF_CHECKED).toString() : TreeFields.TREE_ONLYLEAF_CHECKED;
		this.id=null!=treeModel.get(TreeFields.ID) ? treeModel.get(TreeFields.ID).toString() : TreeFields.ID;
		this.text=null!=treeModel.get(TreeFields.TEXT) ? treeModel.get(TreeFields.TEXT).toString() : TreeFields.TEXT;
		this.parentId=null!=treeModel.get(TreeFields.PARENT_ID) ? treeModel.get(TreeFields.PARENT_ID).toString() : TreeFields.PARENT_ID;
		this.action=null!=treeModel.get(TreeFields.ACTION) ? treeModel.get(TreeFields.ACTION).toString() : TreeFields.ACTION;
		this.nodeCls=null!=treeModel.get(TreeFields.NODE_CLS) ? treeModel.get(TreeFields.NODE_CLS).toString() : TreeFields.NODE_CLS;
		this.seque=null!=treeModel.get(TreeFields.SEQUE) ? treeModel.get(TreeFields.SEQUE).toString() : TreeFields.SEQUE;
		this.isExpand=null!=treeModel.get(TreeFields.IS_EXPAND) ? treeModel.get(TreeFields.IS_EXPAND).toString() : TreeFields.IS_EXPAND;
		this.isChecked=null!=treeModel.get(TreeFields.IS_CHECKED) ? treeModel.get(TreeFields.IS_CHECKED).toString() : TreeFields.IS_CHECKED;
	}

	public String getTreeId(){
		return treeId;
	}

	public String getTreeSync(){
		return treeSync;
	}

	public String getTreeChecked(){
		return treeChecked;
	}

	public String getTreeCascadeChecked(){
		return treeCascadeChecked;
	}

	public String getTreeOnlyLeafChecked(){
		return treeOnlyLeafChecked;
	}

	public String getId(){
		return id;
	}

	public String getText(){
		return text;
	}

	public String getParentId(){
		return parentId;
	}

	public String getAction(){
		return action;
	}

	public String getNodeCls(){
		return nodeCls;
	}

	public String getSeque(){
		return seque;
	}

	public String isExpand(){
		return isExpand;
	}

	public String isChecked(){
		return isChecked;
	}

	public Map<String,Object> getDefaultModel(){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put(TreeFields.TREE_ID,TreeFields.TREE_ID);
		map.put(TreeFields.TREE_SYNC,TreeFields.TREE_SYNC);
		map.put(TreeFields.TREE_CHECKED,TreeFields.TREE_CHECKED);
		map.put(TreeFields.TREE_CASCADE_CHECKED,TreeFields.TREE_CASCADE_CHECKED);
		map.put(TreeFields.TREE_ONLYLEAF_CHECKED,TreeFields.TREE_ONLYLEAF_CHECKED);
		map.put(TreeFields.ID,TreeFields.ID);
		map.put(TreeFields.TEXT,TreeFields.TEXT);
		map.put(TreeFields.PARENT_ID,TreeFields.PARENT_ID);
		map.put(TreeFields.ACTION,TreeFields.ACTION);
		map.put(TreeFields.NODE_CLS,TreeFields.NODE_CLS);
		map.put(TreeFields.SEQUE,TreeFields.SEQUE);
		map.put(TreeFields.IS_EXPAND,TreeFields.IS_EXPAND);
		map.put(TreeFields.IS_CHECKED,TreeFields.IS_CHECKED);
		return map;
	}

	public Map<String,Object> getTreeModelAsMap(){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put(TreeFields.TREE_ID,this.getTreeId());
		map.put(TreeFields.TREE_SYNC,this.getTreeSync());
		map.put(TreeFields.TREE_CHECKED,this.getTreeChecked());
		map.put(TreeFields.TREE_CASCADE_CHECKED,this.getTreeCascadeChecked());
		map.put(TreeFields.TREE_ONLYLEAF_CHECKED,this.getTreeOnlyLeafChecked());
		map.put(TreeFields.ID,this.getId());
		map.put(TreeFields.TEXT,this.getText());
		map.put(TreeFields.PARENT_ID,this.getParentId());
		map.put(TreeFields.ACTION,this.getAction());
		map.put(TreeFields.NODE_CLS,this.getNodeCls());
		map.put(TreeFields.SEQUE,this.getSeque());
		map.put(TreeFields.IS_EXPAND,this.isExpand());
		map.put(TreeFields.IS_CHECKED,this.isChecked());
		return map;
	}

	/**
	 * 树的标准字段
	 */
	public static class TreeFields {
		public static final String TREE_ID="tree_id_";
		public static final String TREE_ROOT="tree_root_";
		public static final String TREE_NODE_PREFIX="tree_node_prefix_";
		public static final String TREE_SYNC="tree_sync_";
		public static final String TREE_CHECKED="tree_checked_";
		public static final String TREE_CASCADE_CHECKED="tree_cascade_checked_";
		public static final String TREE_ONLYLEAF_CHECKED="tree_onlyleaf_checked_";
		public static final String ID="id";
		public static final String TEXT="text";
		public static final String PARENT_ID="parent_id";
		public static final String ACTION="action";
		public static final String NODE_CLS="node_cls";
		public static final String SEQUE="seque";
		public static final String IS_EXPAND="is_expand";
		public static final String IS_CHECKED="is_checked";
	}
}
