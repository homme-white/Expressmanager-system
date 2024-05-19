package LEMS.presentation.orderui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import LEMS.businesslogic.orderbl.Receipt;
import LEMS.po.userpo.UserRole;
import LEMS.presentation.LoginUi;
import LEMS.presentation.MainFrame;
import LEMS.presentation.method.DateChooser;
import LEMS.presentation.method.Table;
import LEMS.presentation.ultraSwing.UltraButton;
import LEMS.presentation.ultraSwing.UltraComboBox;
import LEMS.presentation.ultraSwing.UltraTextField;
import LEMS.vo.ordervo.ArrivalVO;
import LEMS.vo.uservo.UserVO;
/**
 *  
 * 接收界面
 */
public class ReceiveUi extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private static final int LOCATION_LABEL_X=95;
	private static final int LOCATION_LABEL_Y=145;
	private static final int LOCATION_TEXT_X=205;
	private static final int LOCATION_TEXT_Y=150;
	private static final int BOUND_X=130;
	private static final int BOUND_Y=30;

	private MainFrame mainFrame;
	private UserVO user;
	private JLabel title;
	private UltraButton exit;
	private UltraButton OK;
	private UltraButton cancel;
	private UltraButton add;
	private UltraButton delete;
	private UltraButton update;
	private UltraButton finish;
	private JLabel labelDate;
	private JLabel labelId;
	private JLabel labelDeparture;
	private JLabel labelStatus;
	private JLabel labelTransferId;
	private UltraTextField textTransferId;
	private UltraTextField textId;

	private UltraTextField textDeparture;//departure
	private UltraComboBox comboBoxStatus;//status
	
	private DateChooser dc;
	private Table table;
	private JLabel userId;
	private JLabel userRole;
	
	private Font fnt1 = new Font("Courier", Font.BOLD, 26);//标题字体格式
	private Font fnt = new Font("Courier", Font.PLAIN, 15);//其余字体格式

	private Receipt receipt;
	
	/**
	 * 到达单值对象
	 */
	private ArrivalVO arrivalVO;
	
	/**
	 * 标记是否在修改状态（按下修改按钮）
	 */
	private boolean isUpdate = false;
	
	public ReceiveUi(final MainFrame mainFrame, UserVO userVO) {
		this.mainFrame = mainFrame;
		user=userVO;
		this.setLayout(null);
		this.setBounds(0, 0, MainFrame.JFRAME_WIDTH, MainFrame.JFRAME_HEIGHT);
		
		// 初始化
		this.init();
		// 初始化组件
		this.initComponents();
		// 设置输入框不可编辑
		this.setTextState(false);
		// 添加事件监听器
		this.addListener();

		arrivalVO = new ArrivalVO();
		receipt = new Receipt(userVO, arrivalVO);
	}

	/**
	 * 初始化
	 */
	private void init() {
	
		title = new JLabel("接收");
		exit = new UltraButton("返回");
		OK = new UltraButton("确定");
		cancel = new UltraButton("取消");
		add=new UltraButton("新增");
		delete=new UltraButton("删除");
		update=new UltraButton("修改");
		finish=new UltraButton("生成");
		labelDate = new JLabel("到达日期:");
		labelId = new JLabel("订单编号:");
		labelDeparture = new JLabel("出发地:");
		labelStatus = new JLabel("货物到达状态：");
		labelTransferId=new JLabel("中转单号：");
		textTransferId=new UltraTextField();
		textId = new UltraTextField();
		textDeparture = new UltraTextField();
		comboBoxStatus = new UltraComboBox();
		dc= new DateChooser(this,LOCATION_TEXT_X,LOCATION_TEXT_Y);
		userId = new JLabel("账号： "+user.getId());
		userId.setLocation(350, 81);
		userId.setSize(150, 25);
		userRole = new JLabel("身份： "+UserRole.transfer(user.getRole()));
		userRole.setLocation(515, 81);
		userRole.setSize(150, 25);
	}

	/**
	 * 初始化各组件
	 */
	@SuppressWarnings("unchecked")
	private void initComponents() {

		title.setBounds(450, 27, 230, 39);
		
		labelDate.setBounds(LOCATION_LABEL_X, LOCATION_LABEL_Y, BOUND_X, BOUND_Y);
		labelDeparture.setBounds(LOCATION_LABEL_X+7, LOCATION_LABEL_Y+60, BOUND_X, BOUND_Y);
		labelTransferId.setBounds(LOCATION_LABEL_X, LOCATION_LABEL_Y+120, BOUND_X, BOUND_Y);
		labelId.setBounds(LOCATION_LABEL_X, LOCATION_LABEL_Y+200, BOUND_X, BOUND_Y);
		labelStatus.setBounds(LOCATION_LABEL_X-15, LOCATION_LABEL_Y+260, BOUND_X, BOUND_Y);
		textTransferId.setBounds(LOCATION_TEXT_X, LOCATION_TEXT_Y+120, BOUND_X, BOUND_Y-5);
		textId.setBounds(LOCATION_TEXT_X, LOCATION_TEXT_Y+200, BOUND_X, BOUND_Y-5);
		textDeparture.setBounds(LOCATION_TEXT_X, LOCATION_TEXT_Y+60, BOUND_X, BOUND_Y-6);
		comboBoxStatus.setBounds(LOCATION_TEXT_X, LOCATION_TEXT_Y+260, BOUND_X, BOUND_Y-5);
		comboBoxStatus.addItem("完整");
		comboBoxStatus.addItem("损坏");
		comboBoxStatus.addItem("丢失");
		OK.setBounds(LOCATION_LABEL_X+15, LOCATION_LABEL_Y+330, BOUND_X-40, BOUND_Y+10);
		cancel.setBounds(LOCATION_LABEL_X+135, LOCATION_LABEL_Y+330, BOUND_X-40, BOUND_Y+10);
		exit.setBounds(80, 50, 100, 40);
		add.setBounds(150, 590, 120, 40);
		delete.setBounds(350, 590, 120,40);
		update.setBounds(550, 590, 120, 40);
		finish.setBounds(750, 590, 120, 40);

		title.setFont(fnt1);
		labelDate.setFont(fnt);
		labelId.setFont(fnt);
		labelDeparture.setFont(fnt);
		labelStatus.setFont(fnt);
		labelTransferId.setFont(fnt);
		
		this.add(title);
		this.add(labelDate);
		this.add(labelId);
		this.add(labelDeparture);
		this.add(labelStatus);
		this.add(labelTransferId);
		this.add(textTransferId);
		this.add(textId);
		this.add(textDeparture);
		this.add(comboBoxStatus);
		this.add(OK);
		this.add(cancel);
		this.add(exit);
		this.add(add);
		this.add(delete);
		this.add(update);
		this.add(finish);
		this.add(userId);
		this.add(userRole);
		
		String[] columnNames = { "序号","到达日期", "订单编号", "出发地","货物到达状态"};  
		int[] list={40,108,14,30,20,388,125,558,430};

	    table=new Table();
		add(table.drawTable(columnNames, list));
		
		JLayeredPane j=new JLayeredPane();
		j.setLayer(textId, 2, 0);
	    	
	}

	/**
	 * 设置输入框状态
	 * 
	 * @param state
	 *            输入框状态（是否可编辑）
	 */
	private void setTextState(boolean state) {
		textTransferId.setEditable(state);
		textId.setEditable(state);
		textDeparture.setEditable(state);
		comboBoxStatus.setEnabled(state);
		OK.setEnabled(state);
		cancel.setEnabled(state);
		dc.setEnabled(state);
	}

	/**
	 * 清空输入框
	 */
	private void empty() {
		textId.setText(null);
		textDeparture.setText(null);
		textTransferId.setText(null);
		comboBoxStatus.setSelectedIndex(0);
	}

	/**
	 * 为按钮添加事件监听器
	 */
	private void addListener() {
	
		add.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setTextState(true);
			}
		});
		delete.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				table.removeLine(table.getSelectedRow());
			}
		});
		update.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (table.getValueAt(table.getSelectedRow()) != null) {
					isUpdate = true;
					updateOperation();
				}
			}
		});
		finish.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				finishOperation();
			}
		});
		
		exit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				LoginUi loginUi=new LoginUi(mainFrame);
				mainFrame.setContentPane(loginUi);
			}
		});
		
		OK.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (!isEmpty() && isLegal()) {
					OKOperation();
					isUpdate = false;
				}
			}
		});
		cancel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				empty();
				setTextState(false);
			}
		});
	}

	public void paintComponent(Graphics g) {
		g.drawImage(MainFrame.background, 0, 0, this.getWidth(), this.getHeight(), null);
		g.drawRect(60, 125, 305, 190);  //输入框外框
		g.drawRect(60, 330, 305, 225);  //输入框外框
		this.repaint();
	}

	/**
	 * 确认按钮按下后的操作
	 */
	private void OKOperation() {
		//TODO 添加异常捕获
		try {
			receipt.addOrder(textId.getText());
			String[] values = {dc.getTime(), textId.getText(), textDeparture.getText(), comboBoxStatus.getSelectedItem() + ""};
			if (isUpdate) {
				table.setValueAt(table.getSelectedRow(), values);
			} else {
				table.setValueAt(table.numOfEmpty(), values);
			}
			
			textId.setText(null);
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(mainFrame, "请检查网络连接！", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * 完成按钮按下后的操作
	 */
	private void finishOperation() {
		arrivalVO.setDate(dc.getTime());
		arrivalVO.setDepature(textDeparture.getText());
		
		//生成到达单
		receipt.createArrivalNote();

		table.clean();
		this.setTextState(false);
		this.empty();
	}
	
	/**
	 * 修改按钮按下的操作
	 */
	private void updateOperation() {
		ArrayList<String> values = table.getValueAt(table.getSelectedRow());
		dc.setTime(values.get(1));
		textId.setText(values.get(2));
		textDeparture.setText(values.get(3));
		comboBoxStatus.setSelectedItem(values.get(4));
	}
	
	/**
	 * 判断输入是否为空 
	 */
	private boolean isEmpty() {
		if (textDeparture.getText() == null) {
			JOptionPane.showMessageDialog(mainFrame, "出发地为空！", "Error", JOptionPane.ERROR_MESSAGE);
			return true;
		}
		if (textId.getText() == null) {
			JOptionPane.showMessageDialog(mainFrame, "条形码为空！", "Error", JOptionPane.ERROR_MESSAGE);
			return true;
		}
		
		return false;
	}
	
	/**
	 * 判断输入是否合法
	 * 具体包括：条形码是否为10位数字、
	 * TODO 是否已存在
	 */
	private boolean isLegal() {
		//条形码全部为数字
		boolean isNumer = textId.getText().matches("\\d+");
		
		if (!isNumer || textId.getText().length() != 10) {
			JOptionPane.showMessageDialog(mainFrame, "条形码输入错误！", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} 
		if (!isUpdate && table.alreadyExisted(3, textId.getText()) != -1) {
			JOptionPane.showMessageDialog(mainFrame, "订单已存在！", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
}
