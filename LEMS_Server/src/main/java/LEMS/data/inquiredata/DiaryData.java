package LEMS.data.inquiredata;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import LEMS.data.Connect;
import LEMS.dataservice.inquiredataservice.DiaryDataService;
import LEMS.po.inquirepo.DiaryPO;
/**
 * @author 赵君逸
 * DiaryDataService接口的实现
 */
@SuppressWarnings("serial")
public class DiaryData extends UnicastRemoteObject implements DiaryDataService {

	public DiaryData() throws RemoteException {
		super();
	}

	//从数据库中读出当天的日志信息
	public ArrayList<DiaryPO> findDiary(String date) throws RemoteException{
		ArrayList<DiaryPO> uu = new ArrayList<DiaryPO>();
		DiaryPO dpo;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		String sql = "SELECT date,operation FROM diary";
		try {
			Class.forName(Connect.DBDRIVER);
			conn = DriverManager.getConnection(Connect.DBURL, Connect.DBUSER, Connect.DBPASS);
			pstmt = conn.prepareStatement(sql);
			result = pstmt.executeQuery();
			while (result.next()) {
				if (result.getString(1).equals(date)) {					
					dpo = new DiaryPO(result.getString(1),
							result.getString(2));
					uu.add(dpo);
				}
			}
			result.close();
			pstmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return uu;
	}
	
	//记录日志（对系统的主要操作）
	public void recordDiary(DiaryPO dpo) throws RemoteException{
		Connection conn=null;
		PreparedStatement pstmt = null; 
		String sql="INSERT INTO diary(date,operation) VALUES (?,?) ";
		try {
			Class.forName(Connect.DBDRIVER);
			conn = DriverManager.getConnection(Connect.DBURL, Connect.DBUSER, Connect.DBPASS);
			pstmt = conn.prepareStatement(sql) ;
			pstmt.setString(1,dpo.getDate());
			pstmt.setString(2,dpo.getOperation());			
			pstmt.executeUpdate();
			pstmt.close() ;
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
