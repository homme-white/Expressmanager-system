package LEMS.dataservice.informationdataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;

import LEMS.po.financepo.SalaryPO;
import LEMS.po.informationpo.AccountPO;
import LEMS.po.informationpo.DriverPO;
import LEMS.po.informationpo.InstitutionPO;
import LEMS.po.informationpo.VehiclePO;
import LEMS.po.userpo.UserPO;

/**
 *   更新Information信息数据层接口 2015年10月26日
 */
public interface InformationUpdateDataService extends Remote {
	public void updateDriver(DriverPO dpo) throws RemoteException;

	public void updateVehicle(VehiclePO vpo) throws RemoteException;

	public void updateInstitution(InstitutionPO ipo) throws RemoteException;

	public void updateStaff(UserPO upo) throws RemoteException;

	public void updateAccount(AccountPO apo) throws RemoteException;

	public void updateSalary(SalaryPO spo) throws RemoteException;
}
