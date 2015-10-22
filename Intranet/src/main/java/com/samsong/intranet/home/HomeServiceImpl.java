package com.samsong.intranet.home;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HomeServiceImpl implements HomeService{
	
	@Autowired
	private HomeServiceDAO dao;

	@Override
	public Map<String, Object> getNoticeTop1() {
		return dao.getNoticeTop1();
	}

	@Override
	public List<Map<String, Object>> getEducationTop5() {
		List<Map<String, Object>> list = dao.getEducationTop5();
		for(int i = 0; i < list.size(); i++){
			list.get(i).put("empPhoto", dao.getEmpPhoto(list.get(i).get("empNo").toString()));
		}
		return list;
	}
}
