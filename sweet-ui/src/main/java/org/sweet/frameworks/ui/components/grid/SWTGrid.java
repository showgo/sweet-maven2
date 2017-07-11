package org.sweet.frameworks.ui.components.grid;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.sweet.frameworks.foundation.util.json.JSONUtil;
import org.sweet.frameworks.ui.components.Component;
import org.sweet.frameworks.ui.components.abstraction.SWTContainer;
import org.sweet.frameworks.ui.components.util.ComponentUtil;
import org.sweet.frameworks.ui.exception.DatasourceException;
import org.sweet.frameworks.ui.exception.RequiredAttributeException;
import org.sweet.frameworks.ui.resources.URLParameter;

/**
 * 数据表格SWTGrid
 * @filename:SWTGrid
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class SWTGrid extends SWTContainer {
	public static final String COMPONENT_FAMILY="SWTGrid";

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
			/* define panel */
			String id=compo.getId();
			out.startElement("div",this);
			out.writeAttribute("id",id,null);
			out.writeAttribute("xtype",compo.getXtype(),null);
			out.writeAttribute("border","false",null);
			out.writeAttribute("class","easyui-panel",null);
			out.writeAttribute("data-options","region: '"+attributesMap.get("location")+"', split: "+attributesMap.get("split"),null);
			out.writeAttribute("style",ComponentUtil.getStyle(attributesMap),null);
			/* collect bars */
			this.collectBars(id,attributesMap);
			if(null!=attributesMap.get("content")){
				Map<String,Object> contentMap=JSONUtil.toMap(attributesMap.get("content").toString());
				out.startElement("div",this);
				out.writeAttribute("border","false",null);
				if(contentMap.containsKey("align")){
					out.writeAttribute("class",contentMap.get("align")+" easyui-panel",null);
				}else{
					out.writeAttribute("class","easyui-panel",null);
				}
				out.writeText(contentMap.get("content"),null);
				out.endElement("div");
			}else{
				/* generate children */
				/* generate tbar */
				UIComponent tbar=this.getTopBar();
				if(null!=tbar){
					tbar.encodeAll(context);
				}
				/* generate grid row */
				for(UIComponent children:this.getChildren()){
					if(children instanceof SWTGridRow){
						out.startElement("table",this);
						out.writeAttribute("id",id+"_table",null);
						out.writeAttribute("class",compo.getStyleClass(),null);
						out.writeAttribute("fit","true",null);
						/* Add extrasMap to generate extra data-options */
						/* Add property to extrasMap */
						Map<String,Object> extrasMap=new HashMap<String,Object>();
						extrasMap.put("fitColumns",attributesMap.get("fitColumns"));
						extrasMap.put("rownumbers",attributesMap.get("rownumbers"));
						extrasMap.put("singleSelect",attributesMap.get("singleSelect"));
						extrasMap.put("striped",attributesMap.get("striped"));
						extrasMap.put("pagination",attributesMap.get("pagination"));
						extrasMap.put("pageList",attributesMap.get("pageList"));
						extrasMap.put("pageSize",attributesMap.get("pageSize"));
						/* Add events to extrasMap */
						extrasMap.put("onClickRow","function(rowIndex,row){"+attributesMap.get("onClick")+"(row,rowIndex);}");
						Map<String,Object> sourceMap=null!=attributesMap.get("source") ? JSONUtil.toMap(attributesMap.get("source").toString()) : new HashMap<String,Object>();
						if(null!=sourceMap&&sourceMap.size()>0){
							int count=0;
							StringBuilder urlBuffer=new StringBuilder();
							/* 修改表格数据源,增加其他数据源支持 */
							if(sourceMap.containsKey(URLParameter.SQLID_LIT_)&&!sourceMap.containsKey(URLParameter.SERVICE_)){
								urlBuffer.append("./servlet/grid.do?");
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
							extrasMap.put("url","'"+urlBuffer.toString()+"'");
						}
						out.writeAttribute("data-options",ComponentUtil.getDataOptions(attributesMap,extrasMap),null);
						children.encodeAll(context);
						out.endElement("table");
					}
				}
				/* generate bbar */
				UIComponent bbar=this.getBottomBar();
				if(null!=bbar){
					bbar.encodeAll(context);
				}
			}
			out.endElement("div");
		}catch(RequiredAttributeException e){
			e.printStackTrace();
		}catch(DatasourceException e){
			e.printStackTrace();
		}
	}
}
