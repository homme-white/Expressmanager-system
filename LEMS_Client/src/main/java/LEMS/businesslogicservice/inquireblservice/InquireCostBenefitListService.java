package LEMS.businesslogicservice.inquireblservice;

import LEMS.vo.inquirevo.CostBenefitListVO;

/**
 *  
 * 查询成本收益表接口
 */
public interface InquireCostBenefitListService {
	public CostBenefitListVO getCostBenefitList(String startTime,String endTime);
	
}
