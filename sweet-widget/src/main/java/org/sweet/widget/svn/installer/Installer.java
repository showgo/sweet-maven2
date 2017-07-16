package org.sweet.widget.svn.installer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.IOUtils;
import org.sweet.widget.commander.Commander;

/**
 * Installer
 * @filename:Installer
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2017年7月16日
 * @modifyrecords:
 */
public class Installer extends DirectoryWalker<Map<String,Object>> {
	private File path;

	/**
	 * 构造函数
	 * @param path jar包搜索路径
	 * @throws Exception
	 */
	public Installer(String path) throws Exception{
		if(null!=path&&!"".equals(path)){
			File tmp=new File(path);
			if(!tmp.isDirectory()){
				throw new Exception("\""+path+"\" is not a directory...");
			}
			this.path=tmp;
		}else{
			throw new Exception("\""+path+"\" is not a directory...");
		}
	}

	/**
	 * 加载配置文件
	 * @param entityStream 配置文件
	 */
	protected Map<String,Object> getProperties(InputStream entityStream){
		Map<String,Object> propertiesMap=new HashMap<String,Object>();
		try{
			if(null!=entityStream){
				Properties properties=new Properties();
				properties.load(entityStream);
				for(Map.Entry<Object,Object> entry:properties.entrySet()){
					propertiesMap.put(entry.getKey().toString(),entry.getValue());
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			IOUtils.closeQuietly(entityStream);
		}
		return propertiesMap;
	}

	protected void handleFile(File file,int depth,Collection<Map<String,Object>> results){
		String path=file.getAbsolutePath();
		if(path.endsWith(".jar")){
			JarFile jarFile=null;
			try{
				jarFile=new JarFile(file);
				Enumeration<JarEntry> entrys=jarFile.entries();
				while(entrys.hasMoreElements()){
					JarEntry jarEntry=entrys.nextElement();
					/* 从jar包中获取满足条件的资源 */
					if(!jarEntry.isDirectory()&&jarEntry.getName().endsWith("pom.properties")){
						InputStream entityStream=jarFile.getInputStream(jarEntry);
						/* 获取属性 */
						Map<String,Object> map=this.getProperties(entityStream);
						/* 生成新的属性file,script */
						map.put("packaging","jar");
						map.put("file",path);
						Object groupId=map.containsKey("groupId") ? map.get("groupId") : null;
						Object artifactId=map.containsKey("artifactId") ? map.get("artifactId") : null;
						Object version=map.containsKey("version") ? map.get("version") : null;
						if(null!=groupId&&null!=artifactId&&null!=version){
							StringBuilder builder=new StringBuilder();
							builder.append("mvn install:install-file ");
							builder.append("-Dfile=").append(map.get("file")).append(" ");
							builder.append("-DgroupId=").append(map.get("groupId")).append(" ");
							builder.append("-DartifactId=").append(map.get("artifactId")).append(" ");
							builder.append("-Dversion=").append(map.get("version")).append(" ");
							builder.append("-Dpackaging=").append(map.get("packaging"));
							map.put("script",builder.toString());
							/* 添加 */
							results.add(map);
						}
					}
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}finally{
				if(null!=jarFile){
					try{
						jarFile.close();
					}catch(IOException e){
						e.printStackTrace();
					}
				}
			}
		}
	}

	protected List<Map<String,Object>> walk() throws IOException{
		List<Map<String,Object>> results=new ArrayList<Map<String,Object>>();
		this.walk(this.path,results);
		return results;
	}

	public void install() throws IOException{
		List<Map<String,Object>> jars=this.walk();
		Commander commander=Commander.getCommander();
		for(Map<String,Object> jar:jars){
			String script=jar.get("script").toString();
			commander.command(script);
		}
		commander.run();
	}
}
