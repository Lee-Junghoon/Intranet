package com.samsong.intranet.navigation;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NavigationServiceImpl implements NavigationService{
	@Autowired
	private NavigationServiceDAO dao;

	@Override
	public List<Map<String, Object>> getEmployees() {
		return dao.getEmployees();
	}

	@Override
	public Map<String, Object> getEmpInfo(String empNo) {
		return dao.getEmpInfo(empNo);
	}
}
