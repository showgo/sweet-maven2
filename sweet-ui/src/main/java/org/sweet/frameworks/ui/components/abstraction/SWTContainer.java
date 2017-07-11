/**
 * 
 */
package org.sweet.frameworks.ui.components.abstraction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import org.sweet.frameworks.ui.components.panel.bar.SWTBottomBar;
import org.sweet.frameworks.ui.components.panel.bar.SWTTopBar;

/**
 * 容器类组件(SWTContainer)
 * @filename:SWTContainer
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016年5月13日
 * @modifyrecords:
 */
public abstract class SWTContainer extends UIComponentBase {
	/**
	 * 设置组件的属性
	 * @param attribute
	 * @param attributeValue
	 */
	protected void setAttribute(String attribute,Object attributeValue){
		this.getAttributes().put(attribute,attributeValue);
	}

	/**
	 * 获取顶部按钮
	 * @return
	 */
	protected UIComponent getTopBar(){
		List<UIComponent> childrens=this.getChildren();
		for(UIComponent children:childrens){
			if(children instanceof SWTTopBar) {
				return children;
			}
		}
		return null;
	}

	/**
	 * 获取底部按钮
	 * @return
	 */
	protected UIComponent getBottomBar(){
		List<UIComponent> childrens=this.getChildren();
		for(UIComponent children:childrens){
			if(children instanceof SWTBottomBar) {
				return children;
			}
		}
		return null;
	}

	/**
	 * 收集顶部按钮和底部按钮
	 * @param id
	 * @param attributesMap
	 * @throws IOException
	 */
	protected List<UIComponent> collectBars(String id,Map<String,Object> attributesMap) throws IOException{
		List<UIComponent> bars=new ArrayList<UIComponent>();
		/* generate tbar */
		UIComponent tbar=this.getTopBar();
		if(null!=tbar) {
			Object buttonsId=id+"_top_buttons";
			attributesMap.put("tbar",buttonsId);
			tbar.getAttributes().put("id",buttonsId);
			bars.add(tbar);
		}
		/* generate bbar */
		UIComponent bbar=this.getBottomBar();
		if(null!=bbar) {
			Object buttonsId=id+"_bottom_buttons";
			attributesMap.put("bbar",buttonsId);
			bbar.getAttributes().put("id",buttonsId);
			bars.add(bbar);
		}
		return bars;
	}

	/**
	 * 收集面板按钮
	 * @param id
	 * @param attributesMap
	 * @throws IOException
	 */
	protected List<UIComponent> collectTools(String id,Map<String,Object> attributesMap) throws IOException{
		List<UIComponent> bars=new ArrayList<UIComponent>();
		/* generate tbar */
		UIComponent tbar=this.getTopBar();
		if(null!=tbar) {
			Object buttonsId=id+"_tool_buttons";
			attributesMap.put("tools",buttonsId);
			tbar.getAttributes().put("id",buttonsId);
			bars.add(tbar);
		}
		return bars;
	}

	/**
	 * 编码顶部按钮和底部按钮
	 * @param context
	 * @param bars
	 * @throws IOException
	 */
	protected void encodeBars(FacesContext context,List<UIComponent> bars) throws IOException{
		for(UIComponent bar:bars){
			bar.encodeAll(context);
		}
	}

	/**
	 * 编码孩子节点
	 * @param context
	 * @throws IOException
	 */
	protected void encodeChildrens(FacesContext context) throws IOException{
		List<UIComponent> childrens=this.getChildren();
		for(UIComponent children:childrens){
			if(!(children instanceof SWTTopBar)&&!(children instanceof SWTBottomBar)) {
				children.encodeAll(context);
			}
		}
	}
}