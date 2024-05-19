package LEMS.dataservice.inquiredataservice;

import java.rmi.RemoteException;

import LEMS.po.inquirepo.CostBenefitListPO;

/**
 *  
 * 成本收益表数据层接口
 */
public interface CostBenefitListDataService {
	public CostBenefitListPO findCostBenefitList(String startTime,String endTime) throws RemoteException;
}
