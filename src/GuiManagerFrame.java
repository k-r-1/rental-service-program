import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class GuiManagerFrame extends GuiHomeFrame {
	
	private JTabbedPane pane = new JTabbedPane();
	
	public GuiManagerFrame(){
		
		setTitle("��Ż ���� ���α׷�(������ ���)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		pane = createTabbedPane();
		c.add(pane, BorderLayout.CENTER);
		setSize(980, 750);
		setVisible(true);
		
	}
	
	// ��
	private JTabbedPane createTabbedPane() {
		
		pane.addTab("��ǰ ����", new ProductTap());
		pane.addTab("üũ�� ����", new CheckInInfo());
		return pane;
	}
	
	/*************** ��ǰ ���� *****************/
	
	class ProductTap extends JPanel {
		
		private DefaultTableModel model;
		private JTable table = null;
		private String[] column =  {
			"��ǰ��", "��ǰ�ڵ�", "����(���)", "����"
		};
		private String[] searchName = { "��ǰ��", "��ǰ�ڵ�" };
		private JTextField searchTf = new JTextField(10);
		private JComboBox<String> searchBox;
		private JButton viewButton = new JButton("��ȸ");
		private JButton resetButton = new JButton("�ʱ�ȭ");
		private TitledBorder title;
		private JPanel[] p = new JPanel[7];
		private JLabel pName = new JLabel("��ǰ��");
		private JTextField pNameTf = new JTextField(5);
		private JLabel pCode = new JLabel("�ڵ�");
		private JTextField pCodeTf = new JTextField(5);
		private JLabel pNum = new JLabel("����");
		private JTextField pNumTf = new JTextField(5);
		private JLabel pPrice = new JLabel("����");
		private JTextField pPriceTf = new JTextField(5);
		private JButton addBtn = new JButton("���");
		private JLabel pdelCode = new JLabel("�ڵ�");
		private JTextField pdelCodeTf = new JTextField(5);
		private JButton delBtn = new JButton("����");
		private JLabel pEditCode = new JLabel("�ڵ�");
		private JTextField pEditCodeTf = new JTextField(5);
		private JLabel pEditNum = new JLabel("����");
		private JTextField pEditNumTf = new JTextField(5);
		private JButton upBtn = new JButton("�߰�");
		private JButton subBtn = new JButton("����");
		private JLabel revenue = new JLabel("(��)");
		private JTextField revenueTf = new JTextField(5);
		
		public ProductTap() {
			
			this.setBackground(Color.white);
			setLayout(new BorderLayout());
			
			// �г� ����
			for (int i = 0; i < p.length; i++) {
				p[i] = new JPanel();
			}
			
			title = BorderFactory.createTitledBorder("��ǰ���");
			p[1].setBorder(title);
			p[1].add(pName);
			p[1].add(pNameTf);
			p[1].add(pCode);
			p[1].add(pCodeTf);
			p[1].add(pNum);
			p[1].add(pNumTf);
			p[1].add(pPrice);
			p[1].add(pPriceTf);
			p[1].add(addBtn);
			
			title = BorderFactory.createTitledBorder("��ǰ����");
			p[2].setBorder(title);
			p[2].add(pdelCode);
			p[2].add(pdelCodeTf);
			p[2].add(delBtn);
			
			title = BorderFactory.createTitledBorder("������");
			p[3].setBorder(title);
			p[3].add(pEditCode);
			p[3].add(pEditCodeTf);
			p[3].add(pEditNum);
			p[3].add(pEditNumTf);
			p[3].add(upBtn);
			p[3].add(subBtn);
			
			// �˻� ���� �޺��ڽ� ����
			searchBox = new JComboBox<String>();
			for (int i = 0; i < searchName.length; i++)
				searchBox.addItem(searchName[i]);
			
			// "��ȸ" ��ư�� �׼Ǹ����� �߰�
			viewButton.addActionListener(new findProductInfo());
			// "�ʱ�ȭ" ��ư�� �׼Ǹ����� �߰�
			resetButton.addActionListener(new resetInfo());
			// "��ǰ ���" ��ư�� �׼Ǹ����� �߰�
			addBtn.addActionListener(new addInfo());
			// "��ǰ ����" ��ư�� �׼Ǹ����� �߰�
			delBtn.addActionListener(new delInfo());
			// "��� �߰�" ��ư�� �׼Ǹ����� �߰�
			upBtn.addActionListener(new upInfo());
			// "��� ����" ��ư�� �׼Ǹ����� �߰�
			subBtn.addActionListener(new subInfo());
		
			p[4].add(searchBox);
			p[4].add(searchTf);
			p[4].add(viewButton);
			p[4].add(resetButton);
			
			// ���� ��������
			revenueTf.setEditable(false);
			revenueTf.setText(String.valueOf(act.getRevenue()));
			
			title = BorderFactory.createTitledBorder("����");
			p[6].setBorder(title);
			p[6].add(revenueTf);
			p[6].add(revenue);
			
			p[0].add(p[1]);
			p[0].add(p[2]);
			p[0].add(p[3]);
			p[4].add(p[6]);
			
			add(p[4], BorderLayout.NORTH);
			add(p[0], BorderLayout.SOUTH);
			
			// ���̺�
			Object[][] ob = new Object[act.getProductCount()][4];
			for(int i = 0; i < act.getProductCount(); i++) {
				ob[i][0] = act.productAt(i).getName();
				ob[i][1] = act.productAt(i).getCode();
				ob[i][2] = act.productAt(i).getNumber();
				ob[i][3] = act.productAt(i).getPrice();
			}
			
			model = new DefaultTableModel(ob, column);
			table= new JTable(model);
				
			table.getColumn("��ǰ��").setPreferredWidth(250);
			table.getColumn("��ǰ�ڵ�").setPreferredWidth(250);
			table.getColumn("����(���)").setPreferredWidth(250);
			table.getColumn("����").setPreferredWidth(250);
			table.setRowHeight(20);
			table.addMouseListener(new MyMouseListener()); // ���̺� ���콺 ������ �߰�
			
			// ���̺� ���� ��� �����ϱ�
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); 
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			TableColumnModel tcm = table.getColumnModel();
			for(int i=0; i<tcm.getColumnCount(); i++){
				tcm.getColumn(i).setCellRenderer(dtcr);
			}
			
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setSize(660,387);
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setSize(660, 356);	
			scrollPane.setLocation(12, 10);
			scrollPane.setPreferredSize(new Dimension(500, 203));
			add(scrollPane, BorderLayout.CENTER);
			setVisible(true);
			
		}
		
		// ���̺� Ŭ�� �̺�Ʈ
		class MyMouseListener extends MouseAdapter {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 1) { // �� Ŭ�� ��
					int row = table.getSelectedRow();
					try {
						String code = (String) model.getValueAt(row, 1); // ������ ���� ��ǰ �ڵ� ���� ����
						pdelCodeTf.setText(code); // ��ǰ���� �ؽ�Ʈ �ʵ� �� ����
						pEditCodeTf.setText(code); // ������ �ؽ�Ʈ �ʵ� �� ����
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "���̺� ���� �Է� ���� �߻�", "�޽���", JOptionPane.ERROR_MESSAGE);
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		}
		
		// ��ǰ ���� ��ȸ
		class findProductInfo implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				
				String target = searchTf.getText();
				
				if (searchBox.getSelectedItem().equals("��ǰ��")) {
					
					int rowCnt = model.getRowCount();
					
					for (int i = 0; i < rowCnt; i++) {
						if (((String) model.getValueAt(0, 0)).equals(target)) {
	
							Object ob[] = new Object[4];
							ob[0] = (Object) model.getValueAt(0, 0);
							ob[1] = (Object) model.getValueAt(0, 1);
							ob[2] = (Object) model.getValueAt(0, 2);
							ob[3] = (Object) model.getValueAt(0, 3);
							
							model.addRow(ob); 
						}
						model.removeRow(0);
					}
				}
				
				else if (searchBox.getSelectedItem().equals("��ǰ�ڵ�")) {
					
					int rowCnt = model.getRowCount();
					
					for (int i = 0; i < rowCnt; i++) {
						if (((String) model.getValueAt(0, 1)).equals(target)) {
	
							Object ob[] = new Object[4];
							ob[0] = (Object) model.getValueAt(0, 0);
							ob[1] = (Object) model.getValueAt(0, 1);
							ob[2] = (Object) model.getValueAt(0, 2);
							ob[3] = (Object) model.getValueAt(0, 3);
							
							model.addRow(ob);
						}
						model.removeRow(0);
					}
				}
			}
		}
		
		// ���̺� �ʱ�ȭ
		class resetInfo implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				
				int modelSize = model.getRowCount();
				for (int i = 0; i < modelSize; i++) {
					model.removeRow(0);
				}
				
				try {
					for (int i = 0; i < act.getProductCount(); i++) {
						Object ob[] = new Object[4];
						ob[0] = act.productAt(i).getName();
						ob[1] = act.productAt(i).getCode();
						ob[2] = act.productAt(i).getNumber();
						ob[3] = act.productAt(i).getPrice();
						// �� �߰�
						model.addRow(ob);
					}
	
				} catch (NullPointerException npe) {
					JOptionPane.showMessageDialog(null, "Error", "�޽���", JOptionPane.ERROR_MESSAGE);
				}
			}	
		}
		
		// ��ǰ ���
		class addInfo implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				
				String pName = pNameTf.getText();
				String pCode = pCodeTf.getText();
				String pNum = pNumTf.getText();
				String pPrice = pPriceTf.getText();
				
				if(pCode.equals("")){
					JOptionPane.showMessageDialog(null, "��ǰ �ڵ带 �Է��� �ּ���", "�޽���", JOptionPane.ERROR_MESSAGE);
					} else if(pName.equals("")){
						JOptionPane.showMessageDialog(null, "��ǰ���� �Է��� �ּ���", "�޽���", JOptionPane.ERROR_MESSAGE);
					} else if(pNum.equals("")){
						JOptionPane.showMessageDialog(null, "��ǰ ������ �Է��� �ּ���", "�޽���", JOptionPane.ERROR_MESSAGE);
					} else if(pPrice.equals("")){
						JOptionPane.showMessageDialog(null, "��ǰ ������ �Է��� �ּ���", "�޽���", JOptionPane.ERROR_MESSAGE);
	
					} else if(!integerOrNot(pNum)){
							JOptionPane.showMessageDialog(null, "��ǰ ������ ���ڸ� �Է��� �� �����ϴ�.", "�޽���", JOptionPane.ERROR_MESSAGE);
					} else if(!integerOrNot(pPrice)){
							JOptionPane.showMessageDialog(null, "��ǰ ������ ���ڸ� �Է��� �� �����ϴ�.", "�޽���", JOptionPane.ERROR_MESSAGE);
					} else {
						for(int i=0; i<act.getProductCount(); i++){
							if(pCode.equals(act.productAt(i).getCode())){
								JOptionPane.showMessageDialog(null, "������ ��ǰ �ڵ尡 �ֽ��ϴ�.", "�޽���", JOptionPane.ERROR_MESSAGE);
								break;
							}
						}
					
					int check = JOptionPane.showConfirmDialog(null, "�Է��� ������ �½��ϱ�?\n" + 
							"��ǰ�� : "+ pName + "\n�ڵ� : "+pCode + "\n���� : " + pNum + 
							"\n���� : " + pPrice,
							"�޽���", JOptionPane.INFORMATION_MESSAGE );
					if(check == 0){
						Product p = new Product();
						p.setName(pName);
						p.setCode(pCode);
						p.setNumber(Integer.parseInt(pNum));
						p.setPrice(Integer.parseInt(pPrice));
						try {
							act.add(p);
							JOptionPane.showMessageDialog(null, "��ǰ�� ��ϵǾ����ϴ�.", "�޽���", JOptionPane.INFORMATION_MESSAGE);
							pNameTf.setText(null);
							pCodeTf.setText(null);
							pNumTf.setText(null);
							pPriceTf.setText(null);
							try {
								Object ob[] = new Object[4];
								ob[0] = p.getName();
								ob[1] = p.getCode();
								ob[2] = p.getNumber();
								ob[3] = p.getPrice();
								// �� �߰�
								model.addRow(ob);
							} catch (NullPointerException npe) {
								JOptionPane.showMessageDialog(null, "Error", "�޽���", JOptionPane.ERROR_MESSAGE);
							}
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "�߸��� ��ǰ ����Դϴ�.", "�޽���", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		}
		
		// ��ǰ ����
		class delInfo implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				
				String pCode = pdelCodeTf.getText();
				int index;
				try {
					index = act.search(pCode);
					Product p = act.getProductList().get(index);
					act.delete(p.getCode());
					JOptionPane.showMessageDialog(null, "��ǰ�� �����Ǿ����ϴ�.", "�޽���", JOptionPane.INFORMATION_MESSAGE);
					pdelCodeTf.setText(null);
					// �𵨿��� ����
					for (int i = 0; i < model.getRowCount(); i++) {
						if (model.getValueAt(i, 1).equals(pCode)) {
							model.removeRow(i);
							break;
						}
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "�������� �ʴ� ��ǰ�Դϴ�.", "�޽���", JOptionPane.ERROR_MESSAGE);;
				}
			}
		}
		
		// ��� �߰�
		class upInfo implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				
				String pCode = pEditCodeTf.getText();
				String pNum = pEditNumTf.getText();
				int index;
				
				if(pCode.equals("")){
					JOptionPane.showMessageDialog(null, "��ǰ �ڵ带 �Է��� �ּ���", "�޽���", JOptionPane.ERROR_MESSAGE);
				}else if(pNum.equals("")){
					JOptionPane.showMessageDialog(null, "��ǰ ������ �Է��� �ּ���", "�޽���", JOptionPane.ERROR_MESSAGE);
				}else if(!integerOrNot(pNum)){
					JOptionPane.showMessageDialog(null, "��ǰ ������ ���ڸ� �Է��� �� �����ϴ�.", "�޽���", JOptionPane.ERROR_MESSAGE);
				}
				
				else {
					try {
						index = act.search(pCode);
						act.addStock(index, Integer.parseInt(pNum));
						JOptionPane.showMessageDialog(null, "��� �߰��Ǿ����ϴ�.", "�޽���", JOptionPane.INFORMATION_MESSAGE);
						pEditCodeTf.setText(null);
						pEditNumTf.setText(null);
						// �𵨿��� ��� ����
						for (int i = 0; i < model.getRowCount(); i++) {
							if (model.getValueAt(i, 1).equals(pCode)) {
								model.setValueAt(act.productAt(index).getNumber(), i, 2);
								break;
							}
						}
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "�߸��� ��� �����Դϴ�.", "�޽���", JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}
		}
		
		// ��� ����
		class subInfo implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				
				String pCode = pEditCodeTf.getText();
				String pNum = pEditNumTf.getText();
				int index;
				
				if(pCode.equals("")){
					JOptionPane.showMessageDialog(null, "��ǰ �ڵ带 �Է��� �ּ���", "�޽���", JOptionPane.ERROR_MESSAGE);
				}else if(pNum.equals("")){
					JOptionPane.showMessageDialog(null, "��ǰ ������ �Է��� �ּ���", "�޽���", JOptionPane.ERROR_MESSAGE);
				}else if(!integerOrNot(pNum)){
					JOptionPane.showMessageDialog(null, "��ǰ ������ ���ڸ� �Է��� �� �����ϴ�.", "�޽���", JOptionPane.ERROR_MESSAGE);
				}
				
				else {
					try {
						index = act.search(pCode);
						act.subStock(index, Integer.parseInt(pNum));
						JOptionPane.showMessageDialog(null, "��� ���ҵǾ����ϴ�.", "�޽���", JOptionPane.INFORMATION_MESSAGE);
						pEditCodeTf.setText(null);
						pEditNumTf.setText(null);
						// �𵨿��� ��� ����
						for (int i = 0; i < model.getRowCount(); i++) {
							if (model.getValueAt(i, 1).equals(pCode)) {
								model.setValueAt(act.productAt(index).getNumber(), i, 2);
								break;
							}
						}
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "�߸��� ��� �����Դϴ�.", "�޽���", JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}
		}
		
		// �Է°��� �������� �������� �Ǻ� 
		public boolean integerOrNot(String strData){ 
			char[] charData = strData.toCharArray();
			boolean check=true;
			while(check){
				for(int i=0; i<charData.length; i++){		
					if(!Character.isDigit(charData[i])){
							check = !check;
							break;
					}
				}
				break;	
			}
			return check;
		}
	}
	
	/*************** üũ�� ���� *****************/
	
	class CheckInInfo extends JPanel {
		
		private DefaultTableModel model;
		private JTable table = null;
		private String[] column =  {
			"�̸�", "��ȭ��ȣ", "�뿩��", "�ݳ���", "�뿩�ڵ�[1]", "�뿩�ڵ�[2]", "�뿩�ڵ�[3]"
		};
		
		private JLabel phone = new JLabel("��ȭ��ȣ");
		private JTextField phoneTf = new JTextField(10);
		private JButton viewButton = new JButton("��ȸ");
		private JButton resetButton = new JButton("�ʱ�ȭ");
		private JPanel[] p = new JPanel[3];
		
		public CheckInInfo() {
			
			this.setBackground(Color.white);
			setLayout(new BorderLayout());
			
			// �г� ����
			for (int i = 0; i < p.length; i++) {
				p[i] = new JPanel();
			}
			
			p[0].add(phone);
			p[0].add(phoneTf);
			p[0].add(viewButton);
			p[0].add(resetButton);
			add(p[0], BorderLayout.NORTH);
			
			// "��ȸ" ��ư�� �׼Ǹ����� �߰�
			viewButton.addActionListener(new findUserInfo());
			// "�ʱ�ȭ" ��ư�� �׼Ǹ����� �߰�
			resetButton.addActionListener(new resetInfo());
			
			// ���̺�
			Object[][] ob = new Object[act.getUserCount()][7];
			for(int i = 0; i < act.getUserCount(); i++) {
				ob[i][0] = act.userAt(i).getName();
				ob[i][1] = act.userAt(i).getPhone();
				ob[i][2] = act.userAt(i).getRentalDay();
				ob[i][3] = act.userAt(i).getReturnDay();
				
				for(int k = 0; k < act.userAt(i).getRentalCount(); k++)
				{
					ob[i][4+k] = act.userAt(i).codeAt(k);
				}
			}
			
			model = new DefaultTableModel(ob, column);
			table= new JTable(model);
				
			table.getColumn("�̸�").setPreferredWidth(250);
			table.getColumn("��ȭ��ȣ").setPreferredWidth(250);
			table.getColumn("�뿩��").setPreferredWidth(250);
			table.getColumn("�ݳ���").setPreferredWidth(250);
			table.getColumn("�뿩�ڵ�[1]").setPreferredWidth(250);
			table.getColumn("�뿩�ڵ�[2]").setPreferredWidth(250);
			table.getColumn("�뿩�ڵ�[3]").setPreferredWidth(250);
			table.setRowHeight(20);
			
			// ���̺� ���� ��� �����ϱ�
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); 
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			TableColumnModel tcm = table.getColumnModel();
			for(int i=0; i<tcm.getColumnCount(); i++){
				tcm.getColumn(i).setCellRenderer(dtcr);
			}
			
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setSize(660,387);
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setSize(660, 356);	
			scrollPane.setLocation(12, 10);
			scrollPane.setPreferredSize(new Dimension(500, 203));
			add(scrollPane, BorderLayout.CENTER);
			setVisible(true);
			
		}
		
		// ���� ���� ��ȸ
		class findUserInfo implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				
				int modelSize = model.getRowCount();
				for (int i = 0; i < modelSize; i++) {
					model.removeRow(0);
				}
				
				String target = phoneTf.getText();
				int index;
				try {
					// ��ġ�ϴ� ��ȭ��ȣ Ȯ��
					index = act.searchUser(target);
					Object[] ob = new Object[7];
					ob[0] = act.userAt(index).getName();
					ob[1] = act.userAt(index).getPhone();
					ob[2] = act.userAt(index).getRentalDay();
					ob[3] = act.userAt(index).getReturnDay();
					
					for(int k = 0; k < act.userAt(index).getRentalCount(); k++)
					{
						ob[4+k] = act.userAt(index).codeAt(k);
					}
					model.addRow(ob);
			
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "��ġ�ϴ� ��ȭ��ȣ�� ã�� �� �����ϴ�.", "�޽���", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
		// ���̺� �ʱ�ȭ
		class resetInfo implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				
				int modelSize = model.getRowCount();
				for (int i = 0; i < modelSize; i++) {
					model.removeRow(0);
				}
				
				try {
					for (int i = 0; i < act.getUserCount(); i++) {
						Object ob[] = new Object[7];
						ob[0] = act.userAt(i).getName();
						ob[1] = act.userAt(i).getPhone();
						ob[2] = act.userAt(i).getRentalDay();
						ob[3] = act.userAt(i).getReturnDay();
						
						for(int k = 0; k < act.userAt(i).getRentalCount(); k++)
						{
							ob[4+k] = act.userAt(i).codeAt(k);
						}
						
						model.addRow(ob);
					}

				} catch (NullPointerException npe) {
					JOptionPane.showMessageDialog(null, "Error", "�޽���", JOptionPane.ERROR_MESSAGE);
				}
			}	
		}	
	}
}
