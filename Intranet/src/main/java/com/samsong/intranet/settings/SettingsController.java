package com.samsong.intranet.settings;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.samsong.intranet.user.User;

@Controller
@RequestMapping("/settings")
public class SettingsController {
	private static final String SAMBA_SERVER_URL="smb://210.216.217.246/";
	private static final String SAMBA_USERNAME="administrator";
	private static final String SAMBA_PASSWORD="ip__admin";
	@Autowired
	private SettingsService service;
	
	@RequestMapping(value="/account")
	public String account(Model model, Authentication auth){
		User user = (User)auth.getPrincipal();
		
		
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("deptNo", user.getDeptNo());
		info.put("deptName", user.getDeptName());
		info.put("empNo", user.getEmpNo());		
		info.put("empName", user.getEmpName());
		info.put("email", user.getEmail());
		info.put("empCellNo", user.getEmpCellNo());
		info.put("rankName", user.getRankName());
		info.put("positionName", user.getPositionName());
		info.put("birthday", user.getBirthday().substring(0, 4)+"년 "+Integer.parseInt(user.getBirthday().substring(5, 7))+"월 "+user.getBirthday().substring(8, 10)+"일");
		info.put("gender", user.getGender());
		info.put("empPhoto", "/settings/thumbnail?path="+user.getEmpPhoto());
		model.addAttribute("user", info);
		
		return "/settings/account";
	}
	
	@RequestMapping(value="/thumbnail")
	public void setThumnail(@RequestParam(value="path") String path, HttpServletResponse response) throws Exception{

		// 로컬 주소를 samba 표현으로 변경.
		path = path.toLowerCase().replace("c:/", SAMBA_SERVER_URL);
		NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null,SAMBA_USERNAME,SAMBA_PASSWORD);
		//logger.info(path);
		SmbFile photo = new SmbFile(path,auth);
		
		if(!photo.exists()){
			photo = new SmbFile(SAMBA_SERVER_URL+"sawonimage/noname.gif",auth);
		}
		
		String[] tmp = path.split(".");
		String mimeType = tmp.length>0?"image/"+tmp[tmp.length-1]:null;
		
		if(mimeType!=null){
			response.setHeader("Content-Type", mimeType);
		}
		response.setHeader("Content-Length",String.valueOf(photo.length()));
		BufferedInputStream bis = new BufferedInputStream(photo.getInputStream());
		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
		
		byte[] buffer = new byte[1024];
		boolean eof = false;
		while(!eof){
			int read = bis.read(buffer);
			if(read==-1){
				eof = true;
			}
			else{
				bos.write(buffer,0,read);
			}
		}
		bos.flush();
		bis.close();
		bos.close();
	}
	
	@RequestMapping(value="/changePassword")
	public @ResponseBody String changePassword(Authentication auth, 
			@RequestParam(value="currentPassword") String currentPassword,
			@RequestParam(value="newPassword") String newPassword){
		User user = (User)auth.getPrincipal();
		
		if(service.changePassword(user.getUsername(),currentPassword, newPassword)){
			return "true";
		}else{
			return "false";
		}
	}
}
