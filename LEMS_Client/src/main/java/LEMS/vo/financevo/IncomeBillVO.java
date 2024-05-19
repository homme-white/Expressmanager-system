package LEMS.vo.financevo;

import LEMS.po.informationpo.AccountPO;

/**
 *  
 * 
 * 收款单值对象（结算管理、成本管理）
 */
public class IncomeBillVO {
	/**
	 * 收款日期
	 */
	private String date;
	/**
	 * 收款单位
	 */
	private String institution;
	/**
	 * 收款金额
	 */
	private double amount;
	/**
	 * 收款账户
	 */
	private String account;

	public IncomeBillVO(String date, String institution, double amount, String account) {
		this.date = date;
		this.institution = institution;
		this.account = account;
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public String getInstitution() {
		return institution;
	}

	public double getAmount() {
		return amount;
	}
	
	public String getAccount() {
		return account;
	}
}
