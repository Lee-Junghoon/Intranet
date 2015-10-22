package com.samsong.intranet.home;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.samsong.intranet.user.Employee;
import com.samsong.intranet.user.User;
@Controller
public class HomeController {
	@Autowired
	private HomeService service;
	
	@RequestMapping(value="/home")
	public String home(Model model, Authentication auth){
		User user = (User)auth.getPrincipal();	
		System.out.println("HomeController >> /main");
		//공지사항 top 1, 교육자료 top 5 데이터 가져오기
		model.addAttribute("user", Employee.userMapper(user));
		model.addAttribute("notice", service.getNoticeTop1());
		model.addAttribute("eduList", service.getEducationTop5());
		return "/main";
	}
}