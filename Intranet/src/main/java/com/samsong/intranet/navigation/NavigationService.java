package com.samsong.intranet.navigation;

import java.util.*;

public interface NavigationService {

	public List<Map<String, Object>> getEmployees();

	public Map<String,Object> getEmpInfo(String empNo);

}
