package LEMS.data.informationdata;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import LEMS.dataservice.informationdataservice.InformationUpdateDataService;
import LEMS.po.financepo.SalaryPO;
import LEMS.po.informationpo.AccountPO;
import LEMS.po.informationpo.DriverPO;
import LEMS.po.informationpo.InstitutionPO;
import LEMS.po.informationpo.VehiclePO;
import LEMS.po.userpo.UserPO;

/**
 * @author 闫圣夫
 * InformationUpdate包数据
 * 2015年10月26日
 */
@SuppressWarnings("serial")
public class InformationUpdateData extends UnicastRemoteObject implements InformationUpdateDataService{
	public InformationUpdateData() throws RemoteException {
		super();
	}
	
	//更新一项司机信息
	public void updateDriver(DriverPO dpo) throws RemoteException{
		InformationDeleteData delete=new InformationDeleteData();
		delete.deleteDriver(dpo.getId());
		InformationInsertData insert=new InformationInsertData();
		insert.insert(dpo);
	}
	
	//更新一项车辆信息
	public void updateVehicle(VehiclePO vpo) throws RemoteException{
		InformationDeleteData delete=new InformationDeleteData();
		delete.deleteVehicle(vpo.getId());
		InformationInsertData insert=new InformationInsertData();
		insert.insert(vpo);
	}
	
	//更新一项机构信息
	public void updateInstitution(InstitutionPO ipo) throws RemoteException{
		InformationDeleteData delete=new InformationDeleteData();
		delete.deleteInstitution(ipo.getID());
		InformationInsertData insert=new InformationInsertData();
		insert.insert(ipo);
	}
	
	//更新一项人员信息
	public void updateStaff(UserPO upo) throws RemoteException{
		InformationDeleteData delete=new InformationDeleteData();
		delete.deleteStaff(upo.getId());
		InformationInsertData insert=new InformationInsertData();
		insert.insert(upo);
	}
	
	//更新一项账户信息
	public void updateAccount(AccountPO apo) throws RemoteException{
		InformationDeleteData delete=new InformationDeleteData();
		delete.deleteAccount(apo.getId());
		InformationInsertData insert=new InformationInsertData();
		insert.insert(apo);
	}

	//更新一项人员薪水信息
	public void updateSalary(SalaryPO spo) throws RemoteException {
		InformationDeleteData delete=new InformationDeleteData();
		delete.deleteSalary(spo.getId());
		InformationInsertData insert=new InformationInsertData();
		insert.insert(spo);
	}
	
}
