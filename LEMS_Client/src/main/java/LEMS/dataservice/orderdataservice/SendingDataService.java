package LEMS.dataservice.orderdataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import LEMS.po.orderpo.DeliveryNotePO;

public interface SendingDataService extends Remote {

	
	public DeliveryNotePO find(String id) throws RemoteException;
	
	public void insert(DeliveryNotePO deliveryNotePO) throws RemoteException;
	
	public void update(DeliveryNotePO deliveryNotePO) throws RemoteException;
	
	public void delete(String id) throws RemoteException;
	
	/**
	 * 在数据库中查找指定机构最新的记录，并直接生成ID
	 * @param date 日期
	 * @param institution 机构
	 * 
	 * @return 记录ID
	 */
	public String createID(String institution, String date) throws RemoteException;
	
	/**
	 * 在数据库中查找所有“待审批”的单据
	 */
	public ArrayList<DeliveryNotePO> findAll() throws RemoteException;
}