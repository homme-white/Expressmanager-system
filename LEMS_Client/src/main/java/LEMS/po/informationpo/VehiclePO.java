package LEMS.po.informationpo;

import java.io.Serializable;

/**
 *   车辆信息持久化对象 2015年10月25日
 */
@SuppressWarnings("serial")
public class VehiclePO implements Serializable {
	/*
	 * 车辆编号
	 */
	String id;
	/*
	 * 车牌号
	 */
	String plateNumber;
	/*
	 * 服役时间
	 */
	String workTime;
	/*
	 * 车辆图片
	 */
	String image;

	public VehiclePO(String id, String plateNumber, String workTime, String image) {
		this.id = id;
		this.plateNumber = plateNumber;
		this.workTime = workTime;
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public String getWorkTime() {
		return workTime;
	}

	public String getImage() {
		return image;
	}

}
