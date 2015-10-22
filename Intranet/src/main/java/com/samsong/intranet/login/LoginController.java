package com.samsong.intranet.login;

import java.text.DateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.samsong.intranet.home.HomeService;
import com.samsong.intranet.mail.MailService;
import com.samsong.intranet.user.Employee;
import com.samsong.intranet.user.User;

/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController {
	@Autowired
	private LoginService service;
	@Autowired
	private HomeService homeService;
	@Autowired
	private MailService mailService;
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpServletRequest request) {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate );
		
		return "login";
	}
	
	@RequestMapping(value="/main")
	public String loginCheck(Model model, Authentication auth){
		System.out.println("login controller");
		User user = null;
		user = (User)auth.getPrincipal();
		
		service.insertLog(user.getEmpNo(),"LOGIN");		//사용자 로그인 처리
		model.addAttribute("user", Employee.userMapper(user));
		model.addAttribute("notice", homeService.getNoticeTop1());
		model.addAttribute("eduList", homeService.getEducationTop5());
		return "main";
	}
	
	@RequestMapping(value="/login")
	public String login()
	{
		return "login";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public @ResponseBody String signUp(
			@RequestParam(value="username") String username,
			@RequestParam(value="password") String password,
			@RequestParam(value="birth") String birth){
		String res = service.checkUser(username, birth);
		if(res.equals("not registered")){
			//사원 등록 처리
			service.insertUser(username, service.makePassword(password));
		}
		return res;
	}
	
	@RequestMapping(value="/findpw", method=RequestMethod.POST)
	public @ResponseBody String findpw(
			@RequestParam(value="username") String username,
			@RequestParam(value="birth") String birth
			){
		String res = service.checkUser(username, birth);
		//유저가 등록되어 있으면 메일 발송
		String tempText = service.makeTempPassword();
		if(res.equals("exist username")){
			service.updatePassword(username, service.makePassword(tempText));
			StringBuilder htmlText = new StringBuilder();
			htmlText.append("<!DOCTYPE html>");
			htmlText.append("<html>");
			htmlText.append("<head>");
			htmlText.append("<style>");
			htmlText.append("html{font-family: 나눔고딕;font-size: 15px;}");
			htmlText.append("strong{}");
			htmlText.append("div{");
			htmlText.append("border-radius: 4px;-moz-border-radius: 4px; -webkit-border-radius: 4px;");
			htmlText.append("background-image: linear-gradient(to bottom, #d9edf7 0px, #b9def0 100%);");
			htmlText.append("background-color: #d9edf7;");
			htmlText.append("padding:20px;");
			htmlText.append("border:1px solid #9acfea;");
			htmlText.append("width:300px;");
			htmlText.append("height:150px;");
			htmlText.append("}");
			htmlText.append("p{color:#31708f;}");
			htmlText.append("</style>");
			htmlText.append("</head>");
			htmlText.append("<body>");
			htmlText.append("<div>");
			//htmlText.append("<p>안녕하세요. <strong></strong> 님</p>");
			htmlText.append("<p>인트라넷 에서 임시 비밀번호를 알려드립니다. 개인정보에서 비밀번호를 수정하세요.</p>");
			htmlText.append("<p>ID : <strong>"+username+"</strong></p>");
			htmlText.append("<p>PW : <strong>"+tempText+"</strong></p>");
			htmlText.append("</div>");
			htmlText.append("</body>");
			htmlText.append("</html>");
			mailService.sendMailHTML("인트라넷 에서 임시 비밀번호를 알려드립니다.", htmlText.toString(), "intranet-system@samsong.co.kr", mailService.getEmailAddress(username));
		}
		return res;
	}
}
