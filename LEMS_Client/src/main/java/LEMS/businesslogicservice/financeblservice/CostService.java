package LEMS.businesslogicservice.financeblservice;

import LEMS.po.financepo.PayBillPO;
import LEMS.vo.financevo.CostProfitVO;
import LEMS.vo.financevo.PayBillVO;

/**
 *  
 *
 * 成本管理任务接口
 */
public interface CostService {
	
	/**
	 * 生成付款单
	 * 
	 * @param pay 付款信息
	 */
	public PayBillPO payBill(PayBillVO pay);
	
	/**
	 * 获得成本收益信息 
	 * 
	 * @param date 截止日期
	 * @return 成本收益信息
	 */
	public CostProfitVO costProfit(String date);

	/**
	 * 生成成本收益表
	 * 
	 * @param 成本收益信息
	 */
	public void costBenifitBill(CostProfitVO costProfitVO);
	
	/**
	 * 新增付款单
	 * @param pay 付款信息
	 */
	public void addCost(PayBillVO pay);
	
	/**
	 * 删除付款单
	 * @param 付款账号（ID）
	 */
	public void deleteCost(String id);
}
