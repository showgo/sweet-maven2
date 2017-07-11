package org.sweet.frameworks.ui.components;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.sweet.frameworks.ui.components.util.ComponentUtil;
import org.sweet.frameworks.ui.exception.RequiredAttributeException;

/**
 * 组件实体类
 * @filename:
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:
 * @modifyrecords:
 */
public final class Component implements IComponent {
	private FacesContext context=null;
	private String componentName=null;
	private UIComponent component=null;
	private Map<String,List<Attribute>> componentCfg=null;
	private String attributeId=null;
	private String attributeXtype=null;
	private String attributeClass=null;
	private Map<String,Attribute> pro=new HashMap<String,Attribute>();
	private Map<String,Attribute> pub=new HashMap<String,Attribute>();

	/**
	 * 构造函数
	 * @param context
	 * @param component
	 * @param componentCfg
	 */
	public Component(FacesContext context,UIComponent component,Map<String,List<Attribute>> componentCfg){
		/* context, component, componentCfg */
		this.context=context;
		this.component=component;
		this.componentCfg=componentCfg;
		/* component name */
		List<Attribute> nameList=componentCfg.get("component-name");
		if(null!=nameList&&nameList.size()>0) {
			this.componentName=nameList.get(0).getValue().toString();
		}
		/* protected: 使用缺省值设置 */
		List<Attribute> pro=componentCfg.get("component-protected");
		if(null!=pro&&pro.size()>0) {
			for(Attribute attr:pro){
				Attribute at=Attribute.wrap(attr);
				if("id".equalsIgnoreCase(at.getName())) {
					if(Generator.AUTO.equals(at.getGenerator())) {
						at.setValue(ComponentUtil.autoId(this.getXtype(pro)));
					}else if(Generator.CUSTOM.equals(at.getGenerator())) {
						at.setValue(ComponentUtil.getId(component));
					}else{
						at.setValue(null!=at.getDefaultValue() ? at.getDefaultValue().toString() : ComponentUtil.uuid());
					}
					this.attributeId=at.getValue().toString();
				}else if("xtype".equalsIgnoreCase(at.getName())) {
					at.setValue(at.getDefaultValue());
					this.attributeXtype=at.getValue().toString();
				}else if("class".equalsIgnoreCase(at.getName())) {
					at.setValue(at.getDefaultValue());
					this.attributeClass=at.getValue().toString();
				}else{
					at.setValue(at.getDefaultValue());
				}
				this.pro.put(at.getName(),at);
			}
		}
		/* public: 使用实际值 */
		List<Attribute> pub=componentCfg.get("component-public");
		if(null!=pub&&pub.size()>0) {
			getValues(component,pub,this.pub);
		}
	}

	/**
	 * 组件校验
	 * @return
	 * @throws RequiredAttributeException
	 */
	public boolean validate() throws RequiredAttributeException{
		Map<String,Attribute> requiredAttributes=getRequired();
		Map<String,Object> componentAttributes=getRuntimeAttributes(component,this);
		if(null!=requiredAttributes&&requiredAttributes.size()>0) {
			for(Map.Entry<String,Attribute> entry:requiredAttributes.entrySet()){
				String attr=entry.getKey();
				if(!componentAttributes.containsKey(attr)) {
					throw new RequiredAttributeException(this.getComponentName()+": attribute \""+attr+"\" is required");
				}else
					if(componentAttributes.containsKey(attr)&&(null==componentAttributes.get(attr)||"".equals(componentAttributes.get(attr)))) {
					throw new RequiredAttributeException(this.getComponentName()+": attribute \""+attr+"\" is null");
				}
			}
		}
		return true;
	}

	/**
	 * 获得必须字段
	 * @return
	 */
	private Map<String,Attribute> getRequired(){
		Map<String,Attribute> requiredMap=new HashMap<String,Attribute>();
		for(Map.Entry<String,Attribute> entry:this.getProtectedAttributes().entrySet()){
			if(entry.getValue().isRequired()) {
				requiredMap.put(entry.getKey(),entry.getValue());
			}
		}
		for(Map.Entry<String,Attribute> entry:this.getPublicAttributes().entrySet()){
			if(entry.getValue().isRequired()) {
				requiredMap.put(entry.getKey(),entry.getValue());
			}
		}
		return requiredMap;
	}

	/**
	 * 获得组件属性的实际值(现值)
	 * @param component
	 * @param attributes
	 * @param savedAttributes
	 */
	private void getValues(UIComponent component,List<Attribute> attributes,Map<String,Attribute> savedAttributes){
		Map<String,Object> map=component.getAttributes();
		for(Attribute attribute:attributes){
			Attribute attr=Attribute.wrap(attribute);
			if(map.containsKey(attr.getName())) {
				attr.setValue(map.get(attr.getName()));
			}else{
				attr.setValue(attr.getDefaultValue());
			}
			savedAttributes.put(attr.getName(),attr);
		}
	}

	public FacesContext getContext(){
		return this.context;
	}

	public Map<String,List<Attribute>> getComponentCfg(){
		return this.componentCfg;
	}

	public String getComponentName(){
		return this.componentName;
	}

	public String getId(){
		return null!=this.attributeId ? this.attributeId.toString() : null;
	}

	public String getXtype(){
		return null!=this.attributeXtype ? this.attributeXtype.toString() : null;
	}

	private String getXtype(List<Attribute> attributeList){
		for(Attribute attribute:attributeList){
			if("xtype".equalsIgnoreCase(attribute.getName())) {
				return null!=attribute.getDefaultValue() ? attribute.getDefaultValue().toString() : null;
			}
		}
		return null;
	}

	public String getStyleClass(){
		return null!=this.attributeClass ? this.attributeClass.toString() : null;
	}

	public Map<String,Attribute> getProtectedAttributes(){
		return pro;
	}

	public Map<String,Object> getProtectedAttributesMap(){
		Map<String,Object> map=new HashMap<String,Object>();
		for(Map.Entry<String,Attribute> entry:this.pro.entrySet()){
			map.put(entry.getKey(),entry.getValue().getValue());
		}
		return map;
	}

	public Map<String,Attribute> getPublicAttributes(){
		return pub;
	}

	public Map<String,Object> getPublicAttributesMap(){
		Map<String,Object> map=new HashMap<String,Object>();
		for(Map.Entry<String,Attribute> entry:this.pub.entrySet()){
			map.put(entry.getKey(),entry.getValue().getValue());
		}
		return map;
	}

	public Map<String,Attribute> getAttributes(){
		Map<String,Attribute> map=new HashMap<String,Attribute>();
		if(null!=this.pro&&pro.size()>0) {
			map.putAll(this.pro);
		}
		if(null!=this.pub&&pub.size()>0) {
			map.putAll(this.pub);
		}
		return map;
	}

	public Map<String,Object> getAttributesMap(){
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Object> proMap=this.getProtectedAttributesMap();
		if(null!=proMap&&proMap.size()>0) {
			map.putAll(proMap);
		}
		Map<String,Object> pubMap=this.getPublicAttributesMap();
		if(null!=pubMap&&pubMap.size()>0) {
			map.putAll(pubMap);
		}
		return map;
	}

	/**
	 * 获得运行时组件属性
	 * @param component
	 * @param componentWrapper
	 * @return
	 */
	private Map<String,Object> getRuntimeAttributes(UIComponent component,Component componentWrapper){
		Map<String,Object> componentAttributes=new HashMap<String,Object>();
		componentAttributes.putAll(component.getAttributes());
		componentAttributes.put("id",componentWrapper.getId());
		componentAttributes.put("xtype",componentWrapper.getXtype());
		return componentAttributes;
	}
}
