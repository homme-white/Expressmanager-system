package LEMS.presentation.financeui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import LEMS.businesslogic.financebl.Cost;
import LEMS.presentation.LoginUi;
import LEMS.presentation.MainFrame;
import LEMS.presentation.method.DateChooser;
import LEMS.presentation.method.Table;
import LEMS.presentation.ultraSwing.UltraButton;
import LEMS.presentation.ultraSwing.UltraTextField;
import LEMS.vo.financevo.PayBillVO;
import LEMS.vo.uservo.UserVO;

/**
 *   成本管理界面 2015年11月25日
 */
public class CostUi extends JPanel {
	MainFrame mainFrame;

	private JLabel label[];
	private JLabel title;
	private JLabel name;
	private JLabel statue;
	private UltraTextField text[];
	private JTextArea textArea;
	private UltraButton button[];
	private Font font;
	private Table table;
	private UltraButton butOut;
	private DateChooser dc;
	private UserVO user;
	private Font fnt = new Font("Courier", Font.PLAIN, 15);//其余字体格式
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CostUi(final MainFrame mainFrame, UserVO uvo) {
		this.mainFrame = mainFrame;
		user = uvo;
		this.setLayout(null);
		this.setBounds(0, 0, MainFrame.JFRAME_WIDTH, MainFrame.JFRAME_HEIGHT);
		// 初始化
		this.init();
		// 初始化组件
		this.initComponents();
		// 设置输入框不可编辑
		this.setTestState(false);
		// 添加事件监听器
		this.addListener();
	}

	public void init() {
		label = new JLabel[6];
		title = new JLabel("成本管理");
		name = new JLabel("账号：" + user.getId());
		statue = new JLabel("身份：财务人员");
		text = new UltraTextField[5];
		textArea = new JTextArea();
		button = new UltraButton[5];
		font = new Font("Courier", Font.PLAIN, 26);
		button = new UltraButton[5];
		butOut = new UltraButton("返回");
		dc = new DateChooser(this, 304 - 50, 205 - 29);
	}

	public void initComponents() {
		int b = 120;// 整体左移量
		int change = 30;// 整体上移量

		title.setBounds(454, 26, 249, 45);
		title.setFont(font);
		name.setBounds(800, 25, 135, 28);
		statue.setBounds(800, 60, 183, 28);
		textArea.setBounds(304 - 50, 427 - change, 119, 90);
		butOut.setBounds(52, 36, 120, 40);
		this.add(butOut);

		for (int i = 0; i < 6; i++) {
			label[i] = new JLabel();
		}

		label[0].setText("* 付款账号：");
		label[1].setText("* 付款日期：");
		label[2].setText("* 付款金额：");
		label[3].setText(" 付款人：");
		label[4].setText(" 条目：");
		label[5].setText(" 备注：");
		
		for(int i = 0;i<6;i++){
			label[i].setFont(fnt);
		}

		int width = 60;// 间隔
		for (int i = 0; i < 5; i++) {
			label[i].setBounds(261 - b, 141 + width * i - change, 108, 32);
			this.add(label[i]);
		}
		label[5].setBounds(261 - b, 141 + width * 5 + 10 - change, 108, 32);
		this.add(label[5]);
		for (int i = 0; i < 5; i++) {
			text[i] = new UltraTextField();
			text[i].setBounds(374 - b, 144 + (width + 1) * i - change, 119, 25);
			if (i != 1)
				this.add(text[i]);
		}
		text[1] = dc.showDate;
		this.add(text[1]);

		button[0] = new UltraButton("确定");
		button[0].setBounds(276 - b, 554 - change, 99, 42);
		button[1] = new UltraButton("取消");
		button[1].setBounds(394 - b, 554 - change, 99, 42);
		button[2] = new UltraButton("新增");
		button[2].setBounds(349 - b, 642 - change, 109, 40);
		button[3] = new UltraButton("删除");
		button[3].setBounds(778 - b, 642 - change, 109, 40);

		for (int i = 0; i < 4; i++) {
			this.add(button[i]);
		}
		this.add(title);
		this.add(name);
		this.add(statue);
		this.add(textArea);

		String[] columnNames = { "付款账号", "付款日期", "付款金额" };
		int[] list = { 40, 158, 14, 30, 20, 523 - 70, 123 - change, 492, 489 };
		// list里面参数分别为需要的列数，每一列的宽度,设置第一行字体大小,设置第一行行宽,
		// * 剩下行的行宽,表格setbounds（list[5],list[6], list[7], list[8]）
		// *
		table = new Table();
		add(table.drawTable(columnNames, list));
	}

	private void setTestState(boolean state) {
		for (int i = 0; i < 6; i++) {
			label[i].setEnabled(state);
		}
		for (int i = 0; i < 5; i++) {
			text[i].setEnabled(state);
		}
		button[0].setEnabled(state);
		button[1].setEnabled(state);
		textArea.setEnabled(state);
	}

	private void empty() {
		text[0].setText(null);
		text[1].setText(null);
		text[2].setText(null);
		text[3].setText(null);
		text[4].setText(null);
		textArea.setText(null);
	}

	public void addListener() {
		// 确定按钮增加监听
		button[0].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (isComplete()) {
//					try {
						String text2 = text[2].getText();
						double t2 = Double.parseDouble(text2);
						if (t2 >= 0) {
							// TODO 获得第几行为空
							int i = table.numOfEmpty();
							// TODO 确定按钮的具体实现
							table.setValueAt(i, 0, text[0].getText());
							table.setValueAt(i, 1, text[1].getText());
							table.setValueAt(i, 2, text[2].getText());
							// 清空输入框
							empty();
							// 使输入框不可编辑
							setTestState(false);

							PayBillVO pay = new PayBillVO("",
									text[0].getText(), "", t2,
									text[3].getText(), text[0].getText(),
									text[4].getText(), textArea.getText());
							Cost cost = new Cost();
							cost.addCost(pay);
						} else {
							JOptionPane.showMessageDialog(CostUi.this,
									"请输入正确的付款金额");
						}

				} else {
					JOptionPane.showMessageDialog(CostUi.this, "请将必填信息填写完整");
				}
			}
		});

		// 取消按钮增加监听
		button[1].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				empty();
				setTestState(false);
			}
		});

		// 新建按钮增加监听
		button[2].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setTestState(true);
			}
		});

		// 删除按钮增加监听
		button[3].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int currentLine = table.table.getSelectedRow();
				if (currentLine == -1) {
					JOptionPane.showMessageDialog(CostUi.this, "请选择要删除的行!");
				} else {
					int i = table.numOfEmpty();
					//从数据库中删除信息
//					Cost cost = new Cost();
//					System.out.println("生成删除的cost");
//					cost.deleteCost(table.getValueAt(currentLine, 0).trim());
					
					for (int j = currentLine; j < i; j++) {
						table.setValueAt(j, 0, table.getValueAt(j + 1, 0));
						table.setValueAt(j, 1, table.getValueAt(j + 1, 1));
						table.setValueAt(j, 2, table.getValueAt(j + 1, 2));
					}
				}
			}
		});

		butOut.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mainFrame.setContentPane(new LoginUi(mainFrame));
			}
		});
	}

	public void paintComponent(Graphics g) {
		g.drawImage(MainFrame.background, 0, 0, this.getWidth(),
				this.getHeight(), null);
		g.draw3DRect(163 - 50, 124 - 30, 294, 489, false);
		this.repaint();
	}

	private boolean isComplete() {
		if (!text[0].getText().equals("") && !text[1].getText().equals("")
				&& !text[2].getText().equals("")) {
			return true;
		} else {
			return false;
		}
	}
}
