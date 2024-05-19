package LEMS.data.orderdata;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import LEMS.data.Connect;
import LEMS.dataservice.orderdataservice.OrderDataService;
import LEMS.po.orderpo.Express;
import LEMS.po.orderpo.OrderPO;
import LEMS.po.orderpo.Packing;

/**
 * @author 包文辉
 * 
 * Order包数据
 */
public class OrderData extends UnicastRemoteObject implements OrderDataService {
	private static final long serialVersionUID = 1L;

	private Connect connect;
	
	/**
	 * ID长度
	 */
	private static final int ID_LENGTH = 10;
	
	public OrderData() throws RemoteException {
		super();
		
		connect = new Connect();
	}
	
	public OrderPO find(String id) throws RemoteException {
		String sql = "SELECT * FROM dingdan WHERE id = " + id;

		ResultSet result = connect.getResultSet(sql);

		OrderPO orderPO = new OrderPO();
		try {
			result.next();
			orderPO.setId(id);
			//设置寄件人信息
			orderPO.setSenderName(result.getString(2));
			orderPO.setSenderPhone(result.getString(3));
			orderPO.setSenderAddress(result.getString(4));
			//设置收件人信息
			orderPO.setReceiverName(result.getString(5));
			orderPO.setReceiverPhone(result.getString(6));
			orderPO.setReceiverAddress(result.getString(7));
			//设置货物信息
			orderPO.setName(result.getString(8));
			orderPO.setExpressType(Express.valueOf(result.getString(9)));
			orderPO.setPackageType(Packing.valueOf(result.getString(10)));
			orderPO.setAmount(result.getDouble(11));
			orderPO.setQuantity(result.getInt(12));
			orderPO.setWeight(result.getDouble(13));
			orderPO.setVolumn(result.getDouble(14));
			orderPO.setTime(result.getString(15));
			//设置实际收件人
			orderPO.setReceiver(result.getString(16));
			orderPO.setCollector(result.getString(17));
			orderPO.setDeliver(result.getString(18));
			
			connect.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return orderPO;
	}

	public void insert(OrderPO orderPO) throws RemoteException {
		String sql = "INSERT INTO dingdan VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement pstmt = connect.getPreparedStatement(sql);
		
		try {
			pstmt.setString(1, orderPO.getId());
			pstmt.setString(2, orderPO.getSenderName());
			pstmt.setString(3, orderPO.getSenderPhone());
			pstmt.setString(4, orderPO.getSenderAddress());
			
			pstmt.setString(5, orderPO.getReceiverName());
			pstmt.setString(6, orderPO.getReceiverPhone());
			pstmt.setString(7, orderPO.getReceiverAddress());
			
			pstmt.setString(8, orderPO.getName());
			pstmt.setString(9,  orderPO.getExpressType()+"");
			pstmt.setString(10,  orderPO.getPackageType()+"");
			pstmt.setDouble(11, orderPO.getAmount());
			pstmt.setInt(12, orderPO.getQuantity());
			pstmt.setDouble(13, orderPO.getWeight());
			pstmt.setDouble(14, orderPO.getVolumn());
			pstmt.setString(15, orderPO.getTime());
			//实际收件人
			pstmt.setString(16, orderPO.getReceiver());
			pstmt.setString(17, orderPO.getCollector());
			pstmt.setString(18, orderPO.getDeliver());
			
			pstmt.executeUpdate();
			
			connect.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(OrderPO po) throws RemoteException {
		String sql = "DELETE FROM dingdan WHERE id = " + po.getId();
		
		PreparedStatement pstmt = connect.getPreparedStatement(sql);
		
		try {
			pstmt.executeUpdate();
			
			connect.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(OrderPO po) throws RemoteException {
		this.delete(po);
		this.insert(po);
	}
	
	@Override
	public ArrayList<OrderPO> findAll(String collector, String date) throws RemoteException {
		//为记录收款单任务服务
		ArrayList<OrderPO> orders = new ArrayList<OrderPO>();
		String sql = "SELECT * FROM dingdan";

		ResultSet result = connect.getResultSet(sql);
		
		try {
			while (result.next()) {
				if (result.getString(15).startsWith(date) && result.getString(17).equals(collector)) {
					OrderPO order = this.find(result.getString(1));
					orders.add(order);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return orders;
	}

	@Override
	public String createID(String date) throws RemoteException {
		String id = new CreateID().createID("dingdan", ID_LENGTH, date);
		
		return id;
	}

	@Override
	public String getTime(String departure, String destination, Express type) throws RemoteException{
		String time = "";
		
		String sql = "SELECT * FROM dingdan";
		
		ResultSet result = connect.getResultSet(sql);
		
		try {
			while (result.next()) {
				String temp1 = result.getString(4).substring(0, 3);
				String temp2 = result.getString(7).substring(0, 3);
				departure = departure.substring(0, 3);
				destination = destination.substring(0, 3);
				
				if (temp1.equals(departure) && temp2.equals(destination)) {
					if (result.getString(9).equals(type + "")) {
						time = result.getString(15);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return time;
	}
}
