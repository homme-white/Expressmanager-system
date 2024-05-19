package LEMS.businesslogic.orderbl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;

import LEMS.businesslogic.financebl.Price;
import LEMS.businesslogic.inquirebl.inquirelogisticsinfo.InquireLogisticsInfo;
import LEMS.businesslogic.utility.Formate;
import LEMS.businesslogic.utility.RMIConnect;
import LEMS.businesslogicservice.orderblservice.OrderService;
import LEMS.dataservice.factory.DatabaseFactory;
import LEMS.dataservice.factory.OrderFactory;
import LEMS.dataservice.orderdataservice.OrderDataService;
import LEMS.po.orderpo.Express;
import LEMS.po.orderpo.OrderPO;
import LEMS.po.orderpo.Packing;
import LEMS.vo.inquirevo.LogisticsInfoVO;
import LEMS.vo.ordervo.CustomerVO;
import LEMS.vo.ordervo.GoodsVO;
import LEMS.vo.ordervo.OrderVO;
import LEMS.vo.uservo.UserVO;

/**
 *  
 * 
 * 新建订单任务
 */
public class Order implements OrderService {

	/**
	 * 订单值对象
	 */
	private OrderVO order;
	
	private UserVO user;
	
	public Order(OrderVO order, UserVO user) {
		this.user = user;
		this.order = order;
	}
	
	public void addSender(CustomerVO sender) {
		order.setSender(sender);
	}

	public void addReceiver(CustomerVO receiver) {
		order.setReceiver(receiver);
	}

	public void addGoodsInfo(String name, String quantity, String weight, String length, String width, String height) {
		GoodsVO goods = new GoodsVO();
		//体积
		double volumn = Double.parseDouble(length) * Double.parseDouble(width) * Double.parseDouble(height);
		//重量
		double w = Double.parseDouble(weight);
		w = ((volumn / 5000) > w) ? (volumn / 5000) : w;
		
		goods.setName(name);
		goods.setQuantity(Integer.parseInt(quantity));
		goods.setVolumn(volumn);
		goods.setWeight(w);
		
		order.setGoodsInfo(goods);
	}
	
	public void addGoodsInfo(GoodsVO goods) {
		order.setGoodsInfo(goods);
	}

	public void chooseExpress(Express type) {
		order.setExpressType(type);
	}

	public void choosePack(Packing type) {
		order.setPackingType(type);
	}
	
	public String createID() {
		String id = null;
		
		try {
			id = this.getDataService().createID(user.getInstitution().getID());
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return id;
	}

	public double getMoney() {
		//获得距离
		double distance = new Distance().getDistance(order.getSender().getAddress().substring(0, 2), 
													 order.getReceiver().getAddress().substring(0, 2));
		//获得单价
		double temp = new Price().getPrice(order.getExpressType());
		
		double money = distance / 1000 * temp * order.getGoodsInfo().getWeight(); 
		
		money = Double.parseDouble(Formate.DECIMAL_FORMAT.format(money));
		
		return money;
	}

	public double getTotal() {
		double total = new Price().getPrice(order.getPackingType()) + getMoney();
		
		total = Double.parseDouble(Formate.DECIMAL_FORMAT.format(total));
		
		order.setAmount(total);
		
		return total;
	}

	public String getTime() {
		String time = null;
		try {
			time = this.getDataService().getTime(order.getSender().getAddress(), 
										order.getReceiver().getAddress(), order.getExpressType());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return time;
	}

	public void endOrder(String id) {
		OrderPO orderPO = new OrderPO();
		CustomerVO sender = order.getSender();
		CustomerVO receiver = order.getReceiver();
		GoodsVO goodsVO = order.getGoodsInfo();
		orderPO.setId(id);
		orderPO.setSenderName(sender.getName());
		orderPO.setSenderAddress(sender.getAddress());
		orderPO.setSenderPhone(sender.getPhone());
		orderPO.setReceiverName(receiver.getName());
		orderPO.setReceiverAddress(receiver.getAddress());
		orderPO.setReceiverPhone(receiver.getPhone());
		orderPO.setName(goodsVO.getName());
		orderPO.setQuantity(goodsVO.getQuantity());
		orderPO.setWeight(goodsVO.getWeight());
		orderPO.setVolumn(goodsVO.getVolumn());
		orderPO.setExpressType(order.getExpressType());
		orderPO.setPackageType(order.getPackingType());
		orderPO.setAmount(this.getTotal());
		orderPO.setTime(Formate.DATE_FORMAT.format(new Date()));
		orderPO.setReceiver("");
		orderPO.setDeliver("");
		orderPO.setCollector(user.getId());
		
		//写入数据
		try {
			this.getDataService().insert(orderPO);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		//生成物流信息
		this.createLogistics();
	}
	
	/**
	 * 生成物流信息
	 */
	private void createLogistics() {
		LogisticsInfoVO logistics = new LogisticsInfoVO();
		
		logistics.setId(this.createID());
		logistics.setTrace("待发货");
		logistics.setInstitution(user.getInstitution().getID());
		
		new InquireLogisticsInfo().createLogistics(logistics);
	}
	
	private OrderDataService getDataService() {
		OrderDataService orderDataService = null;
		
		try {
			//获得数据库的引用
			DatabaseFactory databaseFactory = (DatabaseFactory) Naming.lookup(RMIConnect.RMI);
			OrderFactory orderFactory = databaseFactory.getOrderFactory();
			orderDataService = orderFactory.getOrderData();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		return orderDataService;
	}
}