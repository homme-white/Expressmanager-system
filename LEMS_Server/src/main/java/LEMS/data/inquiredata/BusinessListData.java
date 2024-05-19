package LEMS.data.inquiredata;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import LEMS.dataservice.inquiredataservice.BusinessListDataService;
import LEMS.po.inquirepo.BusinessListPO;

/**
 * @author 赵君逸
 * BusinessListDataService的实现
 */
@SuppressWarnings("serial")
public class BusinessListData extends UnicastRemoteObject implements BusinessListDataService {

	public BusinessListData() throws RemoteException {
		super();
	}

	public BusinessListPO findBusinessList(String startTime, String endTime) throws RemoteException {
		return null;
	}
}
