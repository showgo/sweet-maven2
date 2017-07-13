package org.sweet.frameworks.ui.components.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.sweet.frameworks.foundation.message.Messages;
import org.sweet.frameworks.foundation.util.map.MapUtil;
import org.sweet.frameworks.ui.components.Component;
import org.sweet.frameworks.ui.components.UIComponentsReader;

/**
 * ComponentUtil
 * @filename:ComponentUtil
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:
 * @modifyrecords:
 */
public final class ComponentUtil extends UIComponentsReader {
	private static final String SWEET_ID_PREFIX="org_sweet_";
	private static final String SWEET_VAR_PREFIX="__swtui__";
	private static final String UNIQUE_ID_PREFIX="j_id";
	protected static final String NL=" ";

	/**
	 * 根据Faces上下文和组件名称获取组件实例
	 * @param context
	 * @param component
	 * @return
	 */
	public static Component getComponent(FacesContext context,UIComponent component){
		String componentName=component.getFamily();
		return new Component(context,component,mapper.get(componentName));
	}

	/**
	 * 重载getId()方法
	 */
	public static String getId(UIComponent component){
		Map<String,Object> attributes=component.getAttributes();
		if(attributes.containsKey("id")&&!component.getId().equals(attributes.get("id"))) {
			return attributes.get("id").toString();
		}
		String id=component.getId();
		id=id.startsWith(UNIQUE_ID_PREFIX) ? id.replaceAll(UNIQUE_ID_PREFIX,SWEET_ID_PREFIX) : id;
		return id;
	}

	public static String autoId(String xtype){
		String uuidString=uuid();
		return SWEET_ID_PREFIX+((null!=xtype&&!"".equals(xtype)) ? (xtype+"_"+uuidString) : uuidString);
	}

	/**
	 * 随机Id
	 * @return
	 */
	public static String uuid(){
		UUID uuid=UUID.randomUUID();
		return uuid.toString().replaceAll("-","").replaceAll("_","");
	}

	/**
	 * 通过环境上下文获得参数集
	 * @param context
	 * @return
	 */
	public static Map<String,Object> getParameters(FacesContext context){
		HttpServletRequest request=(HttpServletRequest)context.getExternalContext().getRequest();
		return MapUtil.getParameterMap(request);
	}

	/**
	 * 获得参数值
	 * @param request
	 * @param paramName
	 * @return
	 */
	public static Object getParameter(HttpServletRequest request,String paramName){
		Map<String,Object> map=MapUtil.getParameterMap(request);
		return map.get(paramName);
	}

	/**
	 * 获得应用基础路径
	 * @param request
	 * @return
	 */
	public static String getBasePath(HttpServletRequest request){
		StringBuilder path=new StringBuilder();
		path.append(request.getScheme());
		path.append("://");
		path.append(request.getServerName());
		path.append(":");
		path.append(request.getServerPort());
		path.append(request.getContextPath());
		path.append("/");
		return path.toString();
	}

	/**
	 * 获得变量名称
	 * @param id
	 * @return
	 */
	public static String getVariable(String id){
		return SWEET_VAR_PREFIX+id;
	}

	/**
	 * 获得数据选项集
	 * @param map
	 * @param extras
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getDataOptions(Map<String,Object> map,Object...extras){
		StringBuilder buffer=new StringBuilder();
		if(null!=map&&map.size()>0) {
			for(Map.Entry<String,Object> entry:map.entrySet()){
				/* options */
				if("modal".equals(entry.getKey())&&null!=entry.getValue()) {
					buffer.append("modal: ").append(entry.getValue()).append(", ");
				}else if("title".equals(entry.getKey())&&null!=entry.getValue()) {
					buffer.append("title: ").append("'").append(entry.getValue()).append("'").append(", ");
				}else if("width".equals(entry.getKey())&&null!=entry.getValue()) {
					buffer.append("width: ").append(entry.getValue()).append(", ");
				}else if("height".equals(entry.getKey())&&null!=entry.getValue()) {
					buffer.append("height: ").append(entry.getValue()).append(", ");
				}else if("icons".equals(entry.getKey())&&null!=entry.getValue()) {
					buffer.append("iconCls: ").append("'").append(entry.getValue()).append("'").append(", ");
				}else if("buttonIcon".equals(entry.getKey())&&null!=entry.getValue()) {
					String handler="";
					if(null!=map.get("handler")) {
						handler=map.get("handler").toString();
						if(handler.contains("(")&&handler.contains(")")) {
							handler.replaceAll(")",", e)");
							handler=handler.contains(";") ? handler : (handler+";");
						}else{
							handler+="(e);";
						}
					}
					buffer.append("icons: ").append("[{iconCls: ").append("'").append(entry.getValue()).append("'").append(", handler: ").append("function(e){"+handler+"}").append("}], ");
				}else if("buttonText".equals(entry.getKey())&&null!=entry.getValue()) {
					buffer.append("buttonText: ").append("'").append(entry.getValue()).append("'").append(", ");
				}else if("split".equals(entry.getKey())&&null!=entry.getValue()) {
					buffer.append("split: ").append(entry.getValue()).append(", ");
				}else if("collapsed".equals(entry.getKey())&&null!=entry.getValue()) {
					buffer.append("collapsed: ").append(entry.getValue()).append(", ");
				}else if("minimized".equals(entry.getKey())&&null!=entry.getValue()) {
					buffer.append("minimized: ").append(entry.getValue()).append(", ");
				}else if("maximized".equals(entry.getKey())&&null!=entry.getValue()) {
					buffer.append("maximized: ").append(entry.getValue()).append(", ");
				}else if("autoOpen".equals(entry.getKey())&&null!=entry.getValue()) {
					buffer.append("closed: ").append(!Boolean.valueOf(entry.getValue().toString()).booleanValue()).append(", ");
				}else if("collapsible".equals(entry.getKey())&&null!=entry.getValue()) {
					buffer.append("collapsible: ").append(entry.getValue()).append(", ");
				}else if("minimizable".equals(entry.getKey())&&null!=entry.getValue()) {
					buffer.append("minimizable: ").append(entry.getValue()).append(", ");
				}else if("maximizable".equals(entry.getKey())&&null!=entry.getValue()) {
					buffer.append("maximizable: ").append(entry.getValue()).append(", ");
				}else if("closable".equals(entry.getKey())&&null!=entry.getValue()) {
					buffer.append("closable: ").append(entry.getValue()).append(", ");
				}else if("location".equals(entry.getKey())&&null!=entry.getValue()) {
					buffer.append("region: ").append("'").append(entry.getValue()).append("'").append(", ");
				}else if("tools".equals(entry.getKey())&&null!=entry.getValue()) {
					buffer.append("tools: ").append("'#").append(entry.getValue()).append("'").append(", ");
				}else if("tbar".equals(entry.getKey())&&null!=entry.getValue()) {
					buffer.append("toolbar: ").append("'#").append(entry.getValue()).append("'").append(", ");
				}else if("bbar".equals(entry.getKey())&&null!=entry.getValue()) {
					buffer.append("buttons: ").append("'#").append(entry.getValue()).append("'").append(", ");
				}else if("emptyText".equals(entry.getKey())&&null!=entry.getValue()) {
					Object prompt="auto".equals(entry.getValue()) ? (null!=map.get("label") ? (Messages.getDefault("emptyTextTip").toString()+map.get("label")) : "") : entry.getValue();
					buffer.append("prompt: ").append("'").append(prompt).append("'").append(", ");
				}else if("required".equals(entry.getKey())&&null!=entry.getValue()) {
					buffer.append("required: ").append(entry.getValue()).append(", ");
				}else if("readOnly".equals(entry.getKey())&&null!=entry.getValue()) {
					buffer.append("editable: ").append(!Boolean.parseBoolean(entry.getValue().toString())).append(", ");
				}
			}
		}
		if(null!=extras) {
			for(Object obj:extras){
				if(obj instanceof Map) {
					for(Map.Entry<String,Object> entry:((Map<String,Object>)obj).entrySet()){
						buffer.append(entry.getKey()+": ").append(entry.getValue()).append(", ");
					}
				}else{
					buffer.append(obj).append(", ");
				}
			}
		}
		if(buffer.length()>0) {
			return buffer.substring(0,buffer.lastIndexOf(","));
		}
		return buffer.toString();
	}

	/**
	 * 获得style字串
	 * @param map
	 * @return
	 */
	public static String getStyle(Map<String,Object> map){
		return getStyle0(map).toString();
	}

	/**
	 * 获得对齐样式
	 * @param map
	 * @return
	 */
	public static String getAlignStyle(Map<String,Object> map){
		Map<String,Object> heightMap=new HashMap<String,Object>();
		if(null!=map.get("height")) {
			heightMap.put("height",map.get("height"));
			map.remove("height");
		}
		StringBuilder buffer=getStyle0(map);
		if(null!=heightMap.get("height")) {
			if(null!=heightMap.get("height")&&heightMap.get("height").toString().indexOf("%")==-1) {
				int height=Integer.valueOf(heightMap.get("height").toString()).intValue();
				height-=2;
				buffer.append("height: ").append(height).append("px;");
				buffer.append("line-height: ").append(height).append("px;");
			}else{
				buffer.append(";");
			}
		}
		return buffer.toString();
	}

	/**
	 * 获得style字串
	 * @param map
	 * @return
	 */
	private static StringBuilder getStyle0(Map<String,Object> map){
		StringBuilder buffer=new StringBuilder();
		if(null!=map&&map.size()>0) {
			if(null!=map.get("width")) {
				buffer.append("width: ").append(map.get("width"));
				if(null!=map.get("width")&&map.get("width").toString().indexOf("%")==-1) {
					buffer.append("px;");
				}else{
					buffer.append(";");
				}
			}
			if(null!=map.get("height")) {
				buffer.append("height: ").append(map.get("height"));
				if(null!=map.get("height")&&map.get("height").toString().indexOf("%")==-1) {
					buffer.append("px;");
				}else{
					buffer.append(";");
				}
			}
			if(null!=map.get("margins")) {
				String[] margins=map.get("margins").toString().split(" ");
				StringBuilder marginsBuffer=new StringBuilder();
				for(String margin:margins){
					if(!margin.endsWith("px")) {
						marginsBuffer.append(margin).append("px").append(" ");
					}else{
						marginsBuffer.append(margin).append(" ");
					}
				}
				buffer.append("margin: ").append(marginsBuffer).append(";");
			}
			if(null!=map.get("paddings")) {
				String[] paddings=map.get("paddings").toString().split(" ");
				StringBuilder paddingsBuffer=new StringBuilder();
				for(String padding:paddings){
					if(!padding.endsWith("px")) {
						paddingsBuffer.append(padding).append("px").append(" ");
					}else{
						paddingsBuffer.append(padding).append(" ");
					}
				}
				buffer.append("padding: ").append(paddingsBuffer).append(";");
			}
			if(null!=map.get("background")) {
				buffer.append("background: ").append(map.get("background"));
			}
		}
		return buffer;
	}

	/**
	 * 获得表单定义label默认宽度
	 * @param component
	 * @return
	 */
	public static String getDefaultLabelWidth(UIComponent component){
		UIComponent compo=component;
		while(null!=compo){
			if(null!=compo.getAttributes().get("labelWidth")) {
				return "width: "+Integer.parseInt(compo.getAttributes().get("labelWidth").toString())+"px;";
			}else{
				compo=compo.getParent();
			}
		}
		return "width: 75px;";
	}

	/**
	 * 保存非空键值对
	 * @param map
	 * @param key
	 * @param value
	 * @return
	 */
	public static Object beyondNull(Map<String,Object> map,String key,String value){
		if(null!=map&&null!=key&&!"".equals(key)&&null!=value&&!"".equals(value)) {
			return map.put(key,value);
		}
		return map;
	}
}
