package org.sweet.frameworks.ui.components.resources;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributeException;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagException;
import javax.servlet.http.HttpServletRequest;

/**
 * 页面包含SWTIncludeLoader
 * @filename:SWTIncludeLoader
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
public class SWTIncludeLoader extends TagHandlerImpl {
	private TagAttribute library=null;
	private TagAttribute href=null;

	public SWTIncludeLoader(TagConfig config){
		super(config);
		TagAttribute attr=getAttribute("href");
		if(null==attr) {
			throw new TagException(this.tag,SWTIncludeLoader.class.getName()+": attribute \"href\" is required");
		}
		this.href=attr;
		this.library=getAttribute("library");
	}

	public void apply(FaceletContext ctx,UIComponent parent) throws IOException{
		String path=this.href.getValue(ctx);
		if((null==path)||(path.length()==0)) {
			return;
		}
		try{
			HttpServletRequest request=(HttpServletRequest)ctx.getFacesContext().getExternalContext().getRequest();
			String relationPath=PageResourceResolver.getRelationPath(request);
			if(null!=this.library) {
				path=relationPath+PageResourceResolver.getResourceBasePath()+this.library.getValue()+PageResourceResolver.SEPARATOR+path;
			}else{
				path=relationPath+path;
			}
			this.nextHandler.apply(ctx,null);
			ctx.includeFacelet(parent,path);
		}catch(IOException e){
			throw new TagAttributeException(this.tag,this.href,SWTIncludeLoader.class.getName()+": Message: "+e.getMessage());
		}finally{
		}
	}
}
