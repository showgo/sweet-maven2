/**
 * 
 */
package org.sweet.frameworks.foundation.resource;

import java.io.File;

/**
 * ResourcePathUtil
 * @filename:ResourcePathUtil
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016年7月11日
 * @modifyrecords:
 */
public final class ResourcePathUtil {
	private static final String WEB_ROOT="/WEB-INF/classes/";

	/**
	 * 获得相对路径
	 * @param parent
	 * @param child
	 * @return
	 */
	public static String getRelativePath(String parent,String child){
		parent=parent.replace(File.separatorChar,'/');
		child=child.replace(File.separatorChar,'/');
		String relativePath=getPathAsChild(parent,child);
		return relativePath==null ? "" : relativePath;
	}

	/**
	 * 获得资源相对路劲
	 * @param filePath 文件对象
	 * @return 文件相对路径
	 */
	public static String getRelativePath(File file){
		String path=file.getAbsolutePath().replace(File.separator,"/");
		return path.substring(path.indexOf(WEB_ROOT)+WEB_ROOT.length());
	}

	/**
	 * Former pathIsChild.
	 * @param path
	 * @param pathChild
	 * @return
	 */
	private static String getPathAsChild(String path,String pathChild){
		if(path==null||pathChild==null){
			return null;
		}
		if(pathChild.compareTo(path)==0){
			return null;
		}
		if(path.length()==0&&!pathChild.startsWith("/")){
			return pathChild;
		}
		// if(!path.endsWith("/")){
		// path=path+"/";
		// }
		if(pathChild.startsWith(path)){
			return pathChild.substring(path.length());
		}
		return null;
	}
}
