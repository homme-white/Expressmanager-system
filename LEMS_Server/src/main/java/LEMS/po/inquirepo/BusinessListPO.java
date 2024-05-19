package LEMS.po.inquirepo;

import java.io.Serializable;
import java.util.ArrayList;

import LEMS.po.financepo.DocumentPO;
import LEMS.po.financepo.IncomeBillPO;
import LEMS.po.financepo.PayBillPO;

/**
 * @author 赵君逸
 * 经营情况表持久化对象
 */
@SuppressWarnings("serial")
public class BusinessListPO extends DocumentPO implements Serializable{
	
	/**
	 * 开始日期
	 */
	String startTime;
	/**
	 * 结束日期
	 */
	String endTime;
	/**
	 * 付款单信息
	 */
	ArrayList<PayBillPO> pay;
	/**
	 * 收款单信息
	 */
	ArrayList<IncomeBillPO> income;
	
	public BusinessListPO(String s,String e,ArrayList<PayBillPO> ap,ArrayList<IncomeBillPO> ai){
		startTime=s;
		endTime=e;
		pay=ap;
		income=ai;
	}
	public String getStartTime(){
		return startTime;
	}
	public String getEndTime(){
		return endTime;
	}
	public ArrayList<PayBillPO> getPayBill(){
		return pay;
	}
	public ArrayList<IncomeBillPO> getIncomeBill(){
		return income;
	}
}
