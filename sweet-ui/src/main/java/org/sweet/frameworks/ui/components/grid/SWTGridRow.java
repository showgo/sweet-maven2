package org.sweet.frameworks.ui.components.grid;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.sweet.frameworks.ui.components.abstraction.SWTControl;

/**
 * 行模式SWTGridRow
 * @filename:SWTGridRow
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class SWTGridRow extends SWTControl {
	public static final String COMPONENT_FAMILY="SWTGridRow";

	@Override
	public String getFamily(){
		return COMPONENT_FAMILY;
	}

	/**
	 * 
	 */
	public void encodeAll(FacesContext context) throws IOException{
		ResponseWriter out=context.getResponseWriter();
		/* generate columns */
		out.startElement("thead",this);
		out.startElement("tr",this);
		for(UIComponent column:this.getChildren()){
			if(column instanceof SWTGridColumn) {
				column.encodeAll(context);
			}
		}
		out.endElement("tr");
		out.endElement("thead");
	}
}
