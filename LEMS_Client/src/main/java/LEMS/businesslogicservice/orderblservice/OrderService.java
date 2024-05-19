package LEMS.businesslogicservice.orderblservice;

import LEMS.po.orderpo.Express;
import LEMS.po.orderpo.Packing;
import LEMS.vo.ordervo.CustomerVO;
import LEMS.vo.ordervo.GoodsVO;

/**
 *  
 * 
 * 新建订单接口
 */
public interface OrderService {
	/**
	 * 添加寄件人
	 * 
	 * @param sender 寄件人
	 */
	public void addSender(CustomerVO sender);
	/**
	 * 添加收件人
	 * 
	 * @param receiver
	 */
	public void addReceiver(CustomerVO receiver);
	/**
	 * 添加货物信息
	 * 
	 * @param goods 货物信息
	 */
	public void addGoodsInfo(GoodsVO goods);
	/**
	 * 选择快递类型
	 * 
	 * @param type 快递类型
	 */
	public void chooseExpress(Express type);
	/**
	 * 选择包装类型
	 * 
	 * @param type 包装类型
	 */
	public void choosePack(Packing type);
	/**
	 * 生成订单条形码
	 * 
	 * @return 订单条形码
	 */
	public String createID();
	/**
	 * 计算货物运费
	 * 
	 * @return 运费
	 */
	public double getMoney();
	/**
	 * 计算总运费
	 * 
	 * @return 总运费
	 */
	public double getTotal();
	/**
	 * 获得预估时间
	 * 
	 * @return 预估时间
	 */
	public String getTime();
	/**
	 * 结束订单管理任务
	 */
	//public void endOrder();
}
