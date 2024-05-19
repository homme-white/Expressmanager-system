package LEMS.dataservice.financedataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;

import LEMS.po.financepo.PayBillPO;

/**
 *  
 * 新建付款单信息数据层接口
 * 2015年12月8日
 */
public interface FinanceInsertDataService extends Remote{
	public void insertCost(PayBillPO payPO) throws RemoteException;
}
