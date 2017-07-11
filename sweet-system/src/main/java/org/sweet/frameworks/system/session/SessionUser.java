/**
 * 
 */
package org.sweet.frameworks.system.session;

import java.util.Map;

/**
 * 会话用户(SessionUser)
 * @filename:
 * @filedescription:
 * @copyright:版权所有(C)2009-2050
 * @company:成都淞幸科技有限责任公司
 * @summary:
 * @othersummary:
 * @finisheddate:
 * @modifyrecords:
 * @version:1.0.0
 * @date:wugz/2017年3月21日
 */
public final class SessionUser {
	private String sessionId=null;
	private String userId=null;
	private String userName=null;
	private String account=null;
	private String userType=null;
	private String orgId=null;
	private String employeeId=null;
	private boolean valid=false;

	/**
	 * 构造函数
	 * @param sessionId
	 * @param user
	 */
	public SessionUser(String sessionId,Map<String,Object> user){
		this.sessionId=sessionId;
		this.userId=null!=user.get("user_id") ? user.get("user_id").toString() : null;
		this.userName=null!=user.get("user_name") ? user.get("user_name").toString() : null;
		this.account=null!=user.get("account") ? user.get("account").toString() : null;
		this.userType=null!=user.get("user_type") ? user.get("user_type").toString() : null;
		this.orgId=null!=user.get("org_id") ? user.get("org_id").toString() : null;
		this.employeeId=null!=user.get("employee_id") ? user.get("employee_id").toString() : null;
		this.valid=null!=user.get("is_valid")&&"Y".equalsIgnoreCase(user.get("is_valid").toString()) ? true : false;
	}

	public String getSessionId(){
		return sessionId;
	}

	public String getUserId(){
		return userId;
	}

	public String getUserName(){
		return userName;
	}

	public String getAccount(){
		return account;
	}

	public String getUserType(){
		return userType;
	}

	public String getOrgId(){
		return orgId;
	}

	public String getEmployeeId(){
		return employeeId;
	}

	public boolean isValid(){
		return valid;
	}
}
