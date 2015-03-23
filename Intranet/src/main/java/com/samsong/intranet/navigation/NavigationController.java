package com.samsong.intranet.navigation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.samsong.intranet.user.Employee;
import com.samsong.intranet.user.User;

@Controller
public class NavigationController {
	@Autowired
	public NavigationService service;
	
	@RequestMapping("/notice")
	public String notice(Model model, Authentication auth,
			@RequestParam(value="menu") String selectedMenu){
		User user = (User)auth.getPrincipal();	
		System.out.println("NavigationController >> /notice");
		System.out.println("param : " + selectedMenu);
		model.addAttribute("user", Employee.userMapper(user));
		model.addAttribute("selectedMenu", "notice");
		return "/navigation/notice";
	}
	@RequestMapping("/employees")
	public String employees(Model model, Authentication auth,
			@RequestParam(value="menu") String selectedMenu){
		User user = (User)auth.getPrincipal();	
		System.out.println("NavigationController >> /employees");
		System.out.println("param : " + selectedMenu);
		model.addAttribute("user", Employee.userMapper(user));
		//사원목록을 가져옴
		model.addAttribute("employees", service.getEmployees());
		model.addAttribute("selectedMenu", "employees");
		return "/navigation/employees";
	}
	@RequestMapping("/employees-detail")
	public String employeesDetail(Model model, Authentication auth,
			@RequestParam(value="empNo") String empNo){
		User user = (User)auth.getPrincipal();	
		System.out.println("NavigationController >> /employees-detail");
		model.addAttribute("user", Employee.userMapper(user));
		model.addAttribute("info", service.getEmpInfo(empNo));
		model.addAttribute("selectedMenu", "employees");
		return "/navigation/employees-detail";
	}
	
}
