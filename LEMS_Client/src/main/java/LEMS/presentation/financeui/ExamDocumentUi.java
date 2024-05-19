package LEMS.presentation.financeui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import LEMS.businesslogic.financebl.Approval;
import LEMS.po.financepo.DocumentPO;
import LEMS.po.financepo.DocumentState;
import LEMS.po.userpo.UserRole;
import LEMS.presentation.LoginUi;
import LEMS.presentation.MainFrame;
import LEMS.presentation.method.Table;
import LEMS.presentation.ultraSwing.UltraButton;
import LEMS.presentation.ultraSwing.UltraComboBox;
import LEMS.vo.uservo.UserVO;

/**
 *   审批单据界面 2015年12月4日
 */
public class ExamDocumentUi extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	MainFrame mainFrame;
	private UltraComboBox box;
	private Font font;
	private JLabel title;
	private JLabel date;
	private JLabel name;
	private JLabel statue;
	private UltraButton btnFind;
	private UltraButton btnOut;
	private UltraButton btnAccepted;
	private UltraButton btnUnaccepted;
	private UltraButton allPass;

	private Table table;
	private UserVO user;
	private Font fnt1 = new Font("Courier", Font.PLAIN, 26);
	private Font fnt = new Font("Courier", Font.PLAIN, 15);//其余字体格式
	
	private Approval approval;
	
	/**
	 * Create the panel.
	 */
	public ExamDocumentUi(final MainFrame mainFrame, UserVO uvo) {
		this.mainFrame = mainFrame;
		user = uvo;
		this.setLayout(null);
		this.setBounds(0, 0, MainFrame.JFRAME_WIDTH, MainFrame.JFRAME_HEIGHT);

		this.init();
		this.initComponent();
		this.addListener();
		
		approval = new Approval("派件单");
	}

	private void init() {
		date = new JLabel("单据类型：");
		title = new JLabel("审批单据");
		title.setFont(fnt1);
		name = new JLabel("账号：  " + user.getId());
		statue = new JLabel("身份： " + UserRole.transfer(user.getRole()));
		btnFind = new UltraButton("查找");
		box = new UltraComboBox();
		
		font = new Font("Courier", Font.PLAIN, 26);
		btnOut = new UltraButton("返回");
		btnAccepted = new UltraButton("通过");
		btnUnaccepted = new UltraButton("不通过");
		allPass = new UltraButton("全部通过");
	}

	@SuppressWarnings("unchecked")
	private void initComponent() {
		date.setBounds(289, 97, 80, 25);
		title.setBounds(444, 26, 249, 45);
		title.setFont(font);
		name.setBounds(800,25, 389, 62);
		statue.setBounds(800,60, 514, 62);
		btnFind.setBounds(588, 92, 120, 40);
		box.setBounds(403, 97, 160, 25);
		box.addItem("派件单");
		box.addItem("中转单");
		box.addItem("装车单");
		box.addItem("装运单");
		box.addItem("付款单");
		box.addItem("收款单");
		btnOut.setBounds(52, 36, 120, 40);
		int b = 120;// 整体左移量
		int change = 30;// 整体上移量
		btnAccepted.setBounds(292 - b, 642 - change, 109, 40);
		btnUnaccepted.setBounds(558 - b, 642 - change, 109, 40);
		allPass.setBounds(823 - b, 642 - change, 153, 40);

		//设置字体
		date.setFont(fnt);
		
		this.add(date);
		this.add(title);
		this.add(name);
		this.add(statue);
		this.add(btnFind);
		this.add(box);
		this.add(btnOut);
		this.add(btnAccepted);
		this.add(btnUnaccepted);
		this.add(allPass);

		String[] columnNames = { "序号","ID","日期","单据状态" };
		int[] list = { 40, 153, 14, 30, 20, 190, 152, 630, 440 };
		table = new Table();
		add(table.drawTable(columnNames, list));
	}

	private void addListener() {
		box.addItemListener(new DocumentListener(box));
		
		btnFind.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				table.clean();
				findOperation();
			}
		});

		btnOut.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mainFrame.setContentPane(new LoginUi(mainFrame));
			}
		});
		
		btnAccepted.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedRow() != -1) {
					acceptOperation(table.getSelectedRow());
				}
			}
		});
		
		btnUnaccepted.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedRow() != -1) {
					unAcceptOperation();
				}
			}
		});
		
		allPass.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				allAcceptOperation();
			}
		});
	}

	public void paintComponent(Graphics g) {
		g.drawImage(MainFrame.background, 0, 0, this.getWidth(), this.getHeight(), null);
		this.repaint();
	}
	
	private void findOperation() {
		try {
			ArrayList<DocumentPO> list = approval.findAll();
			String[] values = new String[3];
			
			for (DocumentPO document : list) {
				values[0] = document.getId();
				values[1] = document.getDate();
				values[2] = DocumentState.transfer(document.getState());
				
				table.setValueAt(table.numOfEmpty(), values);
			}
			
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(mainFrame, "请检查网络连接！", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void acceptOperation(int line) {
		String id = table.getValueAt(line, 1);

		approval.accepted(id);
		
		table.setValueAt(line, 3, DocumentState.transfer(DocumentState.accepted));
	}
	
	private void unAcceptOperation() {
		ArrayList<String>values = table.getValueAt(table.getSelectedRow());
		String id = values.get(0);
		
		approval.unaccepted(id);
		
		table.setValueAt(table.getSelectedRow(), 3, DocumentState.transfer(DocumentState.unaccepted));
	}
	
	private void allAcceptOperation() {
		for (int i = 0; i < table.numOfEmpty(); i++) {
			this.acceptOperation(i);
		}
	}
	
	class DocumentListener implements ItemListener {
		private UltraComboBox comboBox;
		
		public DocumentListener(UltraComboBox comboBox) {
			this.comboBox = comboBox;
		}
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
	            if(e.getSource() == comboBox) {
	            	approval = new Approval(comboBox.getSelectedItem() + "");
	            } 
	        }
		}
	}
}
