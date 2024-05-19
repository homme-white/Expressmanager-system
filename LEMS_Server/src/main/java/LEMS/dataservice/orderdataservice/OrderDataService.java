package LEMS.dataservice.orderdataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import LEMS.po.orderpo.Express;
import LEMS.po.orderpo.OrderPO;

/**
 * @author 包文辉
 *
 * Order包数据接口
 */
public interface OrderDataService extends Remote {
	
	/**
	 * 按ID进行查找返回对应的OrderPO结果
	 */
	public OrderPO find(String id) throws RemoteException;
	
	/**
	 * 在数据库中插入一个po记录
	 */
	public void insert(OrderPO po) throws RemoteException;
	
	/**
	 * 在数据库中删除一个po
	 */
	public void delete(OrderPO po) throws RemoteException;
	
	/**
	 * 在数据库中更新一个po
	 */
	public void update(OrderPO po) throws RemoteException;
	
	/**
	 * 根据揽件员及日期获得所有订单
	 * 
	 * @param collector 揽件员ID
	 * @param date 日期
	 * @return 所有订单，需要订单金额信息
	 */
	public ArrayList<OrderPO> findAll(String collector, String id) throws RemoteException;
	
	/**
	 * 在数据库中查找指定机构最新的记录，并直接生成ID
	 * @param institution 机构编号
	 * @return 记录ID
	 */
	public String createID(String institution) throws RemoteException;
	
	/**
	 * 根据已有快件在出发地和到达地之间送达的平均时间，预计时间
	 * 如果没有历史数据，为0
	 * 
	 * @param departure 出发地
	 * @param destination 到达地
	 * @param type 快递类型
	 * @return 预估时间
	 */
	public String getTime(String departure, String destination, Express type) throws RemoteException;
}
