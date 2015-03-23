package com.samsong.intranet.user;

import java.util.HashMap;
import java.util.Map;

public class Employee {
	public Employee(){
		
	}
	
	public static Map<String, Object> userMapper(User user){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deptNo", user.getDeptNo());
		map.put("deptName", user.getDeptName());
		map.put("empNo", user.getEmpNo());		
		map.put("empName", user.getEmpName());
		map.put("email", user.getEmail());
		map.put("empCellNo", user.getEmpCellNo());
		map.put("empInPhoneNo", user.getEmpInPhoneNo());
		map.put("empPhoto", "/settings/thumbnail?path="+user.getEmpPhoto());
		map.put("rankName", user.getRankName());
		map.put("positionName", user.getPositionName());
		map.put("birthday", user.getBirthday());
		map.put("gender", user.getGender());
		return map;
	}
}
