package LEMS.businesslogicservice.inquireblservice;

import LEMS.vo.inquirevo.DiaryVO;

/**
 *  
 * 查询日志接口
 */
public interface InquireDiaryService {
	public DiaryVO getDiary(String date);
}
