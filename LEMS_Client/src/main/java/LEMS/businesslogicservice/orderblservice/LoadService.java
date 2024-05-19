package LEMS.businesslogicservice.orderblservice;

import java.rmi.RemoteException;

/**
 *  
 * 
 * 装运管理任务接口
 */
public interface LoadService extends AddOrderService {
	/**
	 * 生成装车单
	 * @throws RemoteException 
	 */
	public void createLoadNote() throws RemoteException;
}
