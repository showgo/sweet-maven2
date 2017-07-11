package org.sweet.frameworks.system.loader;

import org.sweet.frameworks.database.core.DatabaseConfigFactory;
import org.sweet.frameworks.database.envir.DBEnvironments;
import org.sweet.frameworks.foundation.util.debug.Debug;

/**
 * (DatabaseConfigLoader)
 * @filename:DatabaseConfigLoader
 * @filedescription:
 * @version:1.0.0O
 * @author:wugz
 * @finisheddate:2017年3月13日
 * @modifyrecords:
 */
public final class DatabaseConfigLoader {
	public static void loadConfigurations(){
		DBEnvironments.loadEnvironments(SystemResourceConfig.DEFAULT_MYBATIS_CONFIG_XML);
		DatabaseConfigFactory.loadConfigurations(SystemResourceConfig.DEFAULT_MYBATIS_CONFIG_XML);
		Debug.info(DatabaseConfigLoader.class,"[Load database configuration(s) completed]");
	}
}
