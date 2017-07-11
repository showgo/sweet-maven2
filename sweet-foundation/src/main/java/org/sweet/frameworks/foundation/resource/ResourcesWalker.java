package org.sweet.frameworks.foundation.resource;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.apache.commons.io.DirectoryWalker;

/**
 * 系统资源遍历(ResourcesWalker)
 * @filename:ResourcesWalker
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-25
 * @modifyrecords:
 */
public final class ResourcesWalker extends DirectoryWalker<String> {
	private String start=null;
	private ResourcesFilter filter=null;

	public ResourcesWalker(ResourcesFilter filter){
		this.filter=filter;
	}

	/**
	 * 文件处理
	 */
	protected void handleFile(File file,int depth,Collection<String> results){
		String path=file.getAbsolutePath();
		if(null!=this.filter&&this.filter.accept(this.start,path)) {
			if(path.endsWith(".jar")) {
				try{
					JarFile jarFile=new JarFile(file);
					Enumeration<JarEntry> entrys=jarFile.entries();
					while(entrys.hasMoreElements()){
						JarEntry jarEntry=entrys.nextElement();
						String entryName=jarEntry.getName();
						/* 从jar包中获取满足条件的资源 */
						if(null!=this.filter&&this.filter.accept(null,entryName)) {
							results.add(entryName);
						}
					}
					jarFile.close();
				}catch(IOException ex){
					ex.printStackTrace();
				}
			}else{
				String rel=ResourcePathUtil.getRelativePath(this.start,path);
				results.add(rel);
			}
		}
	}

	/**
	 * 开始遍历
	 * @param startDirectory
	 * @param results
	 * @throws IOException
	 */
	public void walks(String startDirectory,Collection<String> results) throws IOException{
		this.start=startDirectory;
		this.walk(new File(startDirectory),results);
	}
}
