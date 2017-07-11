package org.sweet.frameworks.system.service.datasource;

import org.sweet.frameworks.ui.components.panel.tree.TreeModel;

public abstract interface ITreeDataSource extends IDataSource {
	/**
	 * 设置树的模型
	 * @param model
	 */
	public void setModel(TreeModel model);

	/**
	 * 获得树的模型
	 * @return
	 */
	public TreeModel getModel();
}
