package org.sweet.frameworks.ui.components.panel.tree;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.sweet.frameworks.foundation.util.json.JSONUtil;
import org.sweet.frameworks.ui.components.Component;
import org.sweet.frameworks.ui.components.abstraction.SWTContainer;
import org.sweet.frameworks.ui.components.util.ComponentUtil;
import org.sweet.frameworks.ui.components.util.TreeUtil;
import org.sweet.frameworks.ui.exception.DatasourceException;
import org.sweet.frameworks.ui.exception.RequiredAttributeException;
import org.sweet.frameworks.ui.resources.URLParameter;

/**
 * 普通tree(SWTTreePanel)
 * @filename:SWTTreePanel
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-15
 * @modifyrecords:
 */
public class SWTTreePanel extends SWTContainer {
	public static final String COMPONENT_FAMILY="SWTTreePanel";

	@Override
	public String getFamily(){
		return COMPONENT_FAMILY;
	}

	public void encodeAll(FacesContext context) throws IOException{
		try{
			/* validate */
			Component compo=ComponentUtil.getComponent(context,this);
			compo.validate();
			this.setAttribute("id",compo.getId());
			this.setAttribute("xtype",compo.getXtype());
			/* generate me */
			Map<String,Object> attributesMap=compo.getPublicAttributesMap();
			ResponseWriter out=context.getResponseWriter();
			out.startElement("div",this);
			out.writeAttribute("id",compo.getId(),null);
			out.writeAttribute("xtype",compo.getXtype(),null);
			out.writeAttribute("border",attributesMap.get("border"),null);
			out.writeAttribute("class","easyui-panel",null);
			out.writeAttribute("style",ComponentUtil.getStyle(attributesMap),null);
			out.writeAttribute("data-options",ComponentUtil.getDataOptions(attributesMap),null);
			if("center".equals(attributesMap.get("location"))){
				out.writeAttribute("fit","true",null);
			}
			/* 处理树的根节点 */
			if(null!=attributesMap.get("rootText")){
				out.startElement("span",this);
				out.writeAttribute("class","sweet-treepanel-root",null);
				out.writeText(attributesMap.get("rootText"),null);
				out.endElement("span");
			}
			String panelId=compo.getId();
			out.startElement("ul",this);
			out.writeAttribute("id",panelId+"_tree",null);
			out.writeAttribute("class",compo.getStyleClass(),null);
			/* data-options */
			StringBuilder dataOptions=new StringBuilder();
			dataOptions.append("method: 'get'").append(", ");
			dataOptions.append("animate: true").append(", ");
			dataOptions.append("lines: "+attributesMap.get("lines")).append(", ");
			dataOptions.append("checkbox: "+attributesMap.get("checked")).append(", ");
			dataOptions.append("cascadeCheck: "+attributesMap.get("cascadeChecked")).append(", ");
			dataOptions.append("onlyLeafCheck: "+attributesMap.get("onlyLeafChecked")).append(", ");
			/* events */
			if(null!=attributesMap.get("onClick")){
				dataOptions.append("onClick: function(node){"+attributesMap.get("onClick")+"(node);}, ");
			}
			if(null!=attributesMap.get("onDblClick")){
				dataOptions.append("onDblClick: function(node){$(this).tree('toggle',node.target);"+attributesMap.get("onDblClick")+"(node);}, ");
			}else{
				dataOptions.append("onDblClick: function(node){$(this).tree('toggle',node.target);}, ");
			}
			if(null!=attributesMap.get("onContextMenu")){
				dataOptions.append("onContextMenu: function(e,node){e.preventDefault();"+attributesMap.get("onContextMenu")+"(node);}, ");
			}
			/* 生成菜单项 */
			if(null!=attributesMap.get("source")){
				StringBuilder urlBuffer=new StringBuilder();
				Map<String,Object> sourceMap=JSONUtil.toMap(attributesMap.get("source").toString());
				sourceMap.put(TreeModel.TreeFields.TREE_SYNC,attributesMap.get("sync"));
				sourceMap.put(TreeModel.TreeFields.TREE_CHECKED,attributesMap.get("checked"));
				sourceMap.put(TreeModel.TreeFields.TREE_CASCADE_CHECKED,attributesMap.get("cascadeChecked"));
				sourceMap.put(TreeModel.TreeFields.TREE_ONLYLEAF_CHECKED,attributesMap.get("onlyLeafChecked"));
				if(null!=sourceMap&&sourceMap.size()>0){
					int count=0;
					/* 修改表格数据源,增加其他数据源支持 */
					if(sourceMap.containsKey(URLParameter.SQLID_LIT_)&&!sourceMap.containsKey(URLParameter.SERVICE_)){
						urlBuffer.append("./servlet/tree.do?");
					}else if(!sourceMap.containsKey(URLParameter.SQLID_LIT_)&&sourceMap.containsKey(URLParameter.SERVICE_)){
						urlBuffer.append(sourceMap.get(URLParameter.SERVICE_)).append("?");
						sourceMap.remove(URLParameter.SERVICE_);
					}else{
						throw new DatasourceException("Attribute 'source' is error configured.");
					}
					for(Map.Entry<String,Object> entry:sourceMap.entrySet()){
						if(count>0){
							urlBuffer.append("&");
						}
						urlBuffer.append(entry.getKey()).append("=").append(entry.getValue());
						count++;
					}
				}
				dataOptions.append("url: ").append("'").append(urlBuffer).append("'");
				out.writeAttribute("data-options",dataOptions.toString(),null);
			}else{
				out.writeAttribute("data-options",dataOptions.toString(),null);
				for(UIComponent component:TreeUtil.getItems(this)){
					component.encodeAll(context);
				}
			}
			out.endElement("ul");
			out.endElement("div");
			/* add on ready event */
			/* end tree */
		}catch(RequiredAttributeException e){
			e.printStackTrace();
		}catch(DatasourceException e){
			e.printStackTrace();
		}
	}
}
