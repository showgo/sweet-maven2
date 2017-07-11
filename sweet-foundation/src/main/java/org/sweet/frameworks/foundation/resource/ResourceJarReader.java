/**
 * 
 */
package org.sweet.frameworks.foundation.resource;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.apache.commons.io.IOUtils;

/**
 * JAR资源Reader(ResourceJarReader)
 * @filename:ResourceJarReader
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-26
 * @modifyrecords:
 */
public final class ResourceJarReader {
	/**
	 * 解压
	 * @param jar
	 * @param dir
	 */
	public static void extractTo(String jar,String dir){
		try{
			ClassLoader loader=Thread.currentThread().getContextClassLoader();
			JarFile jarFile=new JarFile(new File(jar));
			Enumeration<JarEntry> entries=jarFile.entries();
			while(entries.hasMoreElements()){
				JarEntry jarEntry=entries.nextElement();
				if(jarEntry.isDirectory()) {
					continue;
				}else{
					extractTo(jarEntry.getName(),dir,loader);
				}
			}
			jarFile.close();
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}

	/**
	 * 解压
	 * @param resourceName
	 * @param dir
	 * @param loader
	 * @throws IOException
	 */
	private static void extractTo(String resourceName,String dir,ClassLoader loader) throws IOException{
		File fout=new File(dir+File.separator+resourceName);
		if(!fout.getParentFile().exists()) {
			fout.getParentFile().mkdirs();
		}
		InputStream ins=loader.getResourceAsStream(resourceName);
		BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(fout));
		IOUtils.copy(ins,bos);
		IOUtils.closeQuietly(ins);
		IOUtils.closeQuietly(bos);
	}
}
