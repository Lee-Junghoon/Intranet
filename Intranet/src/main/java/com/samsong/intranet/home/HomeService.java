package com.samsong.intranet.home;

import java.util.*;

public interface HomeService {

	Map<String, Object> getNoticeTop1(); //공지사항 top 1

	List<Map<String, Object>> getEducationTop5();
}
