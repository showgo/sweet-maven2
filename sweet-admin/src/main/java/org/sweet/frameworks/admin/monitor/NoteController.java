package org.sweet.frameworks.admin.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.sweet.frameworks.foundation.annotation.controller.RestController;
import org.sweet.frameworks.foundation.annotation.controller.RestMapping;
import org.sweet.frameworks.foundation.util.map.MapUtil;

@RestController(value="/note",allowValidated=false,apiService="公告管理",apiModule="Test")
public class NoteController {
	@RestMapping(value="/read/list",apiFunction="发布公告")
	public Object getResult(Map params){
		List<String> list=new ArrayList<String>();
		list.add("\u6570\u636e11");
		list.add("\u6570\u636e22");
		list.add("\u6570\u636e33");
		return list;
	}
}