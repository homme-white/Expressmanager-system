package LEMS.dataservice.inquiredataservice;

import java.rmi.RemoteException;

import LEMS.po.inquirepo.BusinessListPO;

/**
 * @author 赵君逸
 * 经营情况表数据层接口
 */
public interface BusinessListDataService {
	public BusinessListPO findBusinessList(String startTime,String endTime) throws RemoteException;
}
