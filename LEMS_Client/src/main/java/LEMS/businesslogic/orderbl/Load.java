package LEMS.businesslogic.orderbl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import LEMS.businesslogic.utility.Approvalable;
import LEMS.businesslogic.utility.RMIConnect;
import LEMS.businesslogicservice.orderblservice.LoadService;
import LEMS.dataservice.factory.DatabaseFactory;
import LEMS.dataservice.factory.OrderFactory;
import LEMS.dataservice.orderdataservice.LoadDataService;
import LEMS.po.financepo.DocumentState;
import LEMS.po.orderpo.LoadNotePO;
import LEMS.po.orderpo.OrderPO;
import LEMS.vo.ordervo.LoadVO;
import LEMS.vo.uservo.UserVO;

/**
 *  
 * 
 * 装运管理任务
 * 
 * 目的地中转中心业务员负责出库、装车，并在系统中录入装车单
 * 发往各个营业厅
 */
public class Load extends AddOrder implements LoadService, Approvalable<LoadNotePO> {
	/**
	 * 订单列表
	 */
	protected ArrayList<OrderPO> orders;
	
	private LoadVO loadVO;
	
	private UserVO user;
	
	/**
	 * 汽车2元每公里每吨
	 */
	private final int PRICE = 2;
	
	public Load(){};
	
	public Load(LoadVO loadVO, UserVO user) {
		this.loadVO = loadVO;
		this.user = user;
		//新建订单列表
		orders = new ArrayList<OrderPO>();
	}
	
	public void addOrder(String id) throws RemoteException {
		orders.add(findOrder(id));
	}
	
	public String getName(String id) throws RemoteException {
		return findOrder(id).getName();
	}
	
	public double getWeight(String id) throws RemoteException {
		return findOrder(id).getWeight();
	}

	public void createLoadNote() throws RemoteException {
		LoadNotePO loadNote = new LoadNotePO();
		loadNote.setId(this.createID());
		loadNote.setState(DocumentState.waiting);
		loadNote.setDate(loadVO.getDate());
		loadNote.setDeparture(user.getInstitution().getID());
		loadNote.setDestination(loadVO.getDestination());
		loadNote.setSuperVision(loadVO.getSuperVision());
		loadNote.setSuperCargo(loadVO.getSuperCargo());
		loadNote.setOrders(orders);
		loadNote.setPassage(this.calculatePassage());
		
		this.getDataService().insert(loadNote);
	}
	
	private String createID() {
		String id = "";
		
		try {
			id = this.getDataService().createID(user.getInstitution().getID(), loadVO.getDate());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return id;
	}
	
	private double calculatePassage() {
		//货物总重（单位kg）
		double weight = sumWeight(orders);
		double ditance = new Distance().getDistance(user.getInstitution().getLocation(), loadVO.getDestination());
		return PRICE * ditance * weight / 1000;
	}
	
	@Override
	public void approval(String id, DocumentState state) {
		try {
			this.getDataService().find(id).setState(state);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<LoadNotePO> findAll() throws RemoteException {
		return this.getDataService().findAll();
	}
	
	private LoadDataService getDataService() {
		LoadDataService dataService = null;
		
		try {
			DatabaseFactory databaseFactory = (DatabaseFactory) Naming.lookup(RMIConnect.RMI);
			OrderFactory orderFactory = databaseFactory.getOrderFactory();
			dataService = orderFactory.getLoadData();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		return dataService;
	}
}
