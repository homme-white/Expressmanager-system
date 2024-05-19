package LEMS.presentation.financeui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import LEMS.businesslogic.financebl.Price;
import LEMS.businesslogic.orderbl.Distance;
import LEMS.po.orderpo.City;
import LEMS.po.orderpo.Express;
import LEMS.po.orderpo.Packing;
import LEMS.po.storepo.TransportType;
import LEMS.po.userpo.UserRole;
import LEMS.presentation.LoginUi;
import LEMS.presentation.MainFrame;
import LEMS.presentation.method.Table;
import LEMS.presentation.ultraSwing.UltraButton;
import LEMS.presentation.ultraSwing.UltraComboBox;
import LEMS.vo.uservo.UserVO;
import javax.swing.DefaultComboBoxModel;

/**
 *   制定价格界面 2015年12月4日
 */
public class SettingPriceUi extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserVO user;

	private MainFrame mainFrame;
	private JLabel title = new JLabel("制定常量");
	private Table table;
	private UltraButton butModify = new UltraButton("修改");

	private UltraButton butOut = new UltraButton("返回");
	private Font font = new Font("Courier", Font.PLAIN, 26);

	private JLabel name;
	private JLabel statue;
	JLabel labelPrice = new JLabel("价格：");
	JLabel labelDistance = new JLabel("距离：");

	private UltraComboBox comboxPrice = new UltraComboBox();
	private UltraComboBox comboxDistance = new UltraComboBox();
	
	/**
	 * 标记当前状态 
	 * 		价格/距离
	 */
	private boolean pricing = true;
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SettingPriceUi(final MainFrame mainFrame, UserVO uvo) {
		this.setBounds(0, 0, MainFrame.JFRAME_WIDTH, MainFrame.JFRAME_HEIGHT);
		this.setLayout(null);
		this.mainFrame = mainFrame;
		user = uvo;
		name = new JLabel("账号：  " + user.getId());
		statue = new JLabel("身份： " + UserRole.transfer(user.getRole()));
		title.setBounds(456, 26, 249, 45);
		title.setFont(font);
		butOut.setBounds(60, 41, 120, 40);
		butModify.setBounds(860, 41, 120, 40);

		labelPrice.setBounds(265, 150, 70, 30);
		labelDistance.setBounds(565, 150, 70, 30);
		comboxPrice.setModel(new DefaultComboBoxModel(new String[] {"快递价格", "包装价格", "运输价格"}));
		comboxPrice.setBounds(320, 150, 120, 30);
		comboxDistance.setModel(new DefaultComboBoxModel(new String[] {"显示全部", "北京", "上海", "南京", "广州"}));
		comboxDistance.setBounds(630, 150, 120, 30);

		name.setBounds(355, 75, 135, 28);
		statue.setBounds(528, 75, 183, 28);

		this.add(title);
		this.add(butOut);
		this.add(butModify);
		this.add(labelPrice);
		this.add(labelDistance);
		this.add(comboxPrice);
		this.add(comboxDistance);
		this.add(name);
		this.add(statue);
		this.initComponents();
		this.addListener();
		
		this.showPrice();
	}

	public void initComponents() {
		String[] columnNames1 = { "项目", "数值" };
		int[] list1 = { 120, 392, 20, 30, 20, 102, 265, 801, 360 };
		// list里面参数分别为需要的列数，每一列的宽度,设置第一行字体大小,设置第一行行宽,
		// * 剩下行的行宽,表格setbounds（list[5],list[6], list[7], list[8]）
		table = new Table();
		add(table.drawTable(columnNames1, list1));
		// 将每一列的默认宽度设置为
		table.table.setRowHeight(40);
	}

	private void addListener() {
		comboxPrice.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					//清空表格
					table.clean();
					
					if (e.getSource() == comboxPrice) {
						showPrice();
						pricing = true;
					}
				}
			}
		});
		
		comboxDistance.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					//清空表格
					table.clean();
					
					if (e.getSource() == comboxDistance) {
						showDistance();
						pricing = false;
					}
				}
			}
		});
		
		butOut.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mainFrame.setContentPane(new LoginUi(mainFrame));
			}
		});

		butModify.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedRow() != -1) {
					modifyOperation();
				}
			}
		});

	}

	public void paintComponent(Graphics g) {
		g.drawImage(MainFrame.background, 0, 0, this.getWidth(), this.getHeight(), null);
		this.repaint();
	}

	/**
	 * 在界面中显示距离数据
	 */
	private void showDistance() {
		if (comboxDistance.getSelectedItem() == "显示全部") {
			this.showAllCity();
		} else {
			this.showCityDistance(comboxDistance.getSelectedItem() + "");
		}
	}
	
	private void showAllCity() {
		Distance cityDistance = new Distance();
		double distance = 0;
		int line = 0;
		String departure = "", destination = "";

		table.clean();
		
		// 同一城市间距离
		distance = cityDistance.getDistance(City.cityList.get(0), City.cityList.get(0));
		table.setValueAt(line, 0, "城市各营业厅");
		table.setValueAt(line, 1, distance + "  KM");

		for (int i = 0; i < City.cityList.size(); i++) {
			departure = City.cityList.get(i);
			for (int j = i + 1; j < City.cityList.size(); j++) {
				destination = City.cityList.get(j);
				line = table.numOfEmpty();

				distance = cityDistance.getDistance(departure, destination);
				table.setValueAt(line, 0, departure + " --- " + destination);
				table.setValueAt(line, 1, distance + "  KM");
			}
		}
	}
	
	/**
	 * 显示目标城市与其他城市间的距离
	 */
	private void showCityDistance(String city) {
		int line = 0;
		double distance = 0.0;
		
		for (String destination : City.cityList) {
			if (city.equals(destination)) {
				continue;
			}
			line = table.numOfEmpty();
			distance = new Distance().getDistance(city, destination);
			table.setValueAt(line, 0, city + " --- " + destination);
			table.setValueAt(line, 1, distance + "  KM");
		}
	}

	/**
	 * 在界面中显示价格数据
	 */
	private void showPrice() {
		if (comboxPrice.getSelectedItem() == "快递价格") {
			this.showExpressPrice();
		} else if (comboxPrice.getSelectedItem() == "包装价格"){
			this.showPackagePrice();
		} else if (comboxPrice.getSelectedItem() == "运输价格") {
			this.showLoadPrice();
		}
	}

	private void showExpressPrice() {
		int line = 0;
		Price price = new Price();
		
		for (Express e : Express.values()) {
			line = table.numOfEmpty();
			table.setValueAt(line, 0, Express.transfer(e));
			table.setValueAt(line, 1, price.getPrice(e) + " 元");
		}
	}
	
	private void showPackagePrice() {
		int line = 0;
		Price price = new Price();
		
		for (Packing p : Packing.values()) {
			line = table.numOfEmpty();
			table.setValueAt(line, 0, Packing.transfer(p));
			table.setValueAt(line, 1, price.getPrice(p) + " 元");
		}
	}
	
	private void showLoadPrice() {
		int line = 0;
		Price price = new Price();
		
		for (TransportType type	: TransportType.values()) {
			if (type == TransportType.YetToKnow) {
				continue;
			}
			line = table.numOfEmpty();
			table.setValueAt(line, 0, TransportType.Transfer(type));
			table.setValueAt(line, 1, price.getPrice(type) + " 元每公里每吨");
		}
	}

	/**
	 * 修改按钮按下的操作
	 */
	private void modifyOperation() {
		String input = JOptionPane.showInputDialog(mainFrame, "输入", "请输入", JOptionPane.INFORMATION_MESSAGE);
		double value = 0.0;
		try {
			value = Double.parseDouble(input);
			
			if (value <= 0.0) {
				JOptionPane.showMessageDialog(mainFrame, "请输入正确数值！", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (pricing) {
				// “制定价格”选项
				this.modifyPrice(value);
			} else {
				// “制定距离”选项
				this.modifyDistance(value);
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(mainFrame, "请输入正确数值！", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * 修改距离
	 */
	private void modifyDistance(double value) {
		String city = table.getValueAt(table.getSelectedRow(), 0);
		
		Distance distance = new Distance();
		
		if (city.startsWith("城市")) {
			distance.setDistance("北京市", "北京市", value);
			return;
		} 
		
		String city1 = city.substring(0, 2);
		String city2 = city.substring(city.length() - 2);
		
		distance.setDistance(city1, city2, value);
		
		table.setValueAt(table.getSelectedRow(), 1, value + "  KM");
	}

	/**
	 * 修改价格
	 */
	private void modifyPrice(double value) {
		String item = table.getValueAt(table.getSelectedRow(), 0);
		Price price = new Price();
		
		TransportType transport = TransportType.transfer(item);
		if (transport != null) {
			table.setValueAt(table.getSelectedRow(), 1, value + " 元每公里每吨");
			return;
		}
		Express express = Express.transfer(item);
		if (express != null) {
			price.pricing(express, value);
		}
		Packing packing = Packing.transfer(item);
		if (packing != null) {
			price.pricing(packing, value);
		}

		table.setValueAt(table.getSelectedRow(), 1, value + " 元");
	}
}
