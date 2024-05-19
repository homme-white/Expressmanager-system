package LEMS.businesslogicservice.orderblservice;

/**
 *  
 * 
 * 车辆装车管理任务接口
 */
public interface VehicleLoadService extends AddOrderService {

	/**
	 * 生成装车单
	 */
	public void createVehicleLoadNote();
}