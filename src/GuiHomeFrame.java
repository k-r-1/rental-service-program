import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class GuiHomeFrame extends JFrame {
	
	Manager act;
	JTabbedPane pane = new JTabbedPane();
	ObjectInputStream dis;
	Calendar getToday = Calendar.getInstance();
	
	public GuiHomeFrame() {
		
		try {
			dis = new ObjectInputStream(new FileInputStream("info.dat"));
			// dis ��ü�� �Ŵ��� �����ڿ� ����
			act = new Manager(100, 100, dis);
			// ���� �ݱ�
			dis.close();
		} catch (FileNotFoundException fnfe) { // ������ ã�� �� ���� ��
			while (true)
			{
				int answer = JOptionPane.showConfirmDialog(null, "������ ã�� �� �����ϴ�. �������� ������ �����Ͽ����ϱ�?", "�޽���", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				
				// ����� YES�� ���α׷� ����
				if (answer == 0)
				{ 
					JOptionPane.showMessageDialog(null, "������ ã�� �� �����ϴ�. �ý����� �����մϴ�.", "�޽���", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
				// ����� NO�� ���� ����
				else if (answer == 1)
				{
					JOptionPane.showMessageDialog(null,"������ ���ο� ���Ϸ� �����մϴ�.", "�޽���", JOptionPane.INFORMATION_MESSAGE);
					act = new Manager(100, 100);
					break;
				}
			}
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "������ ã�� �� �����ϴ�. �ý����� �����մϴ�.", "�޽���", JOptionPane.INFORMATION_MESSAGE);
		}
		
		setTitle("��Ż ���� ���α׷�");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		pane = createTabbedPane();
		c.add(pane, BorderLayout.CENTER);
		createMenu();
		setSize(680, 450);
	}
	
	// ��
	private JTabbedPane createTabbedPane() {
		
		pane.addTab("����", new MainTap());
		pane.addTab("üũ��", new CheckIn());
		pane.addTab("üũ�ƿ�", new CheckOut());
		return pane;
	}

	// �޴���
	private void createMenu() {
		JMenu fileMenu = new JMenu("File");
		JMenuItem save = new JMenuItem("Save");
		fileMenu.add(save);
		JMenuBar mb = new JMenuBar();
		mb.add(fileMenu);
		setJMenuBar(mb);
		
		// ���� ����
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fileSave();
					JOptionPane.showMessageDialog(null, "������ ����Ǿ����ϴ�.", "�޽���", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e1) {
				
				}
			}
		});			
	}
	
	public void fileSave(){
		
		ObjectOutputStream infoFile = null;
		try {

			infoFile = new ObjectOutputStream(new FileOutputStream("info.dat"));
			act.saveObject(infoFile);
			//JOptionPane.showMessageDialog(this, "������ ����Ǿ����ϴ�.", "�޽���", JOptionPane.INFORMATION_MESSAGE);
		} catch (FileNotFoundException fnfe) { // ������ ��� ��Ʈ���� �����ڰ� �߻���Ű�� �ͼ����� ó��
			JOptionPane.showMessageDialog(this, "������ �������� �ʽ��ϴ�.", "�޽���", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) { // ���� ����� ���� ó��
			JOptionPane.showMessageDialog(this, "�߸��� ���� �����Դϴ�.", "�޽���", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "�߸��� ���� �����Դϴ�.", "�޽���", JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				if (infoFile != null) // infoFile�� null�� �ƴ��� Ȯ���� ��, ��Ʈ���� �ݱ�
					infoFile.close();
			}
			catch (Exception e) {
			}
		}
	}
	
	/*************** ���� *****************/
	
	class MainTap extends JPanel {
		
		private JPanel[] p = new JPanel[3];
		private JLabel mainLabel = new JLabel("Welcome to the rental shop!");
		private JButton mgrBtn = new JButton("������ ���");
		private JLabel pwLabel = new JLabel("password : ");
		private JTextField password = new JTextField(10);
		
		public MainTap() {
			setLayout(new BorderLayout());
			
			// �г� ����
			for (int i = 0; i < p.length; i++) {
				p[i] = new JPanel();
			}
			
			mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
			mainLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 25));
			add(mainLabel, BorderLayout.CENTER);

			mgrBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(password.getText().equals("1111")) { // �Է��� ������ ��й�ȣ�� ������, ������ ���� ���� 
						dispose();
						new GuiManagerFrame();
					}
					else {
						JOptionPane.showMessageDialog(null, "������ ��й�ȣ�� Ʋ�Ƚ��ϴ�.", "�޽���", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			p[1].add(pwLabel);
			p[1].add(password);
			p[1].add(mgrBtn);
			add(p[1], BorderLayout.SOUTH);

		}
	}
	
	/*************** üũ�� *****************/
	
	public class CheckIn extends JPanel {
		
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
		private JLabel name = new JLabel("�̸�");
		private JTextField nameTf = new JTextField(5);
		private JLabel phone = new JLabel("��ȭ��ȣ");
		private JTextField phoneTf = new JTextField(5);
		private JLabel returnDay = new JLabel("�ݳ���");
		private SpinnerDateModel m;
		private JSpinner spinner;
		private JLabel code = new JLabel("��ǰ�ڵ� (�ִ� 3��)");
		private JTextField codeTf_1 = new JTextField(3);
		private JTextField codeTf_2 = new JTextField(3);
		private JTextField codeTf_3 = new JTextField(3);
		private JButton rentalButton = new JButton("�뿩");
		private JPanel[] p = new JPanel[3];
		
		public CheckIn() {
			
			this.setBackground(Color.white);
			setLayout(new BorderLayout());
			
			// �г� ����
			for (int i = 0; i < p.length; i++) {
				p[i] = new JPanel();
			}

			// �˻� ���� �޺��ڽ� ����
			searchBox = new JComboBox<String>();
			for (int i = 0; i < searchName.length; i++)
				searchBox.addItem(searchName[i]);
			
			// "��ȸ" ��ư�� �׼Ǹ����� �߰�
			viewButton.addActionListener(new findProductInfo());
			// "�ʱ�ȭ" ��ư�� �׼Ǹ����� �߰�
			resetButton.addActionListener(new resetInfo());
			// "�뿩" ��ư�� �׼Ǹ����� �߰�
			rentalButton.addActionListener(new rentalInfo());

			// �ݳ��� ��¥ ����
			m = new SpinnerDateModel();
		    spinner = new JSpinner(m);
		    JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "yyyy-MM-dd");
		    spinner.setEditor(editor);
		    spinner.setPreferredSize(new Dimension(90, 25));
					
			p[0].add(searchBox);
			p[0].add(searchTf);
			p[0].add(viewButton);
			p[0].add(resetButton);
			p[2].add(name);
			p[2].add(nameTf);
			p[2].add(phone);
			p[2].add(phoneTf);
			p[2].add(returnDay);
			p[2].add(spinner);
			p[2].add(code);
			p[2].add(codeTf_1);
			p[2].add(codeTf_2);
			p[2].add(codeTf_3);
			p[2].add(rentalButton);
			add(p[0], BorderLayout.NORTH);
			add(p[1], BorderLayout.CENTER);
			add(p[2], BorderLayout.SOUTH);
			
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
				String code1 = codeTf_1.getText();
				String code2 = codeTf_2.getText();
				String code3 = codeTf_3.getText();
				if (e.getButton() == 1) { // �� Ŭ�� ��
					int row = table.getSelectedRow();
					try {
						String code = (String) model.getValueAt(row, 1); // ������ ���� ��ǰ �ڵ� ���� ����
						if(code1.equals("") && code2.equals("") && code3.equals("")) // 3���� �ؽ�Ʈ �ʵ忡 ��� �Էµ� �ڵ尡 ������
							codeTf_1.setText(code); // 1�� �ؽ�Ʈ �ʵ忡 �Է�
						else if(!code1.equals("") && !code2.equals("")) // 1��, 2�� �ؽ�Ʈ �ʵ忡 �Էµ� �ڵ尡 �����ϸ�
							codeTf_3.setText(code); // 3�� �ؽ�Ʈ �ʵ忡 �Է�
						else if(!code1.equals("")) // 1�� �ؽ�Ʈ �ʵ忡 �Էµ� �ڵ尡 �����ϸ�
							codeTf_2.setText(code); // 2�� �ؽ�Ʈ �ʵ忡 �Է�
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "���̺� ���� �Է� ���� �߻�", "�޽���", JOptionPane.ERROR_MESSAGE);
					}
				}
				 // 3���� �ؽ�Ʈ �ʵ忡 ��� �Էµ� �ڵ尡 �����ϴµ�, �� Ŭ�� �� -> �ؽ�Ʈ �ʵ� �ʱ�ȭ
				if (!code1.equals("") && !code2.equals("") && !code3.equals("")) {
					if (e.getButton() == 1) {
						codeTf_1.setText(null);
						codeTf_2.setText(null);
						codeTf_3.setText(null);
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
		
		// �뿩
		class rentalInfo implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				
				String rDay = new SimpleDateFormat("yyyyMMdd").format(spinner.getValue());
				String today = new SimpleDateFormat("yyyyMMdd").format(getToday.getTime());
				
				if (Integer.parseInt(today) > Integer.parseInt(rDay)) {
					JOptionPane.showMessageDialog(null, "�����Ͻ� �ݳ����� ���� ��¥���� �����Դϴ�.", "�޽���", JOptionPane.ERROR_MESSAGE);
				}
				
				// �ݳ����� ���ú��� ������ ���� üũ�� ����
				else {
					
					String name = nameTf.getText();
					String phone = phoneTf.getText();
					String returnDay = new SimpleDateFormat("yyyy-MM-dd").format(spinner.getValue());
					String rentalDay = new SimpleDateFormat("yyyy-MM-dd").format(getToday.getTime());
					String code1 = codeTf_1.getText();
					String code2 = codeTf_2.getText();
					String code3 = codeTf_3.getText();
					
					if(name.equals("")){
						JOptionPane.showMessageDialog(null, "�̸��� �Է��� �ּ���", "�޽���", JOptionPane.INFORMATION_MESSAGE);
					} else if(phone.equals("")){
						JOptionPane.showMessageDialog(null, "��ȭ��ȣ�� �Է��� �ּ���", "�޽���", JOptionPane.INFORMATION_MESSAGE);
					} else if(code1.equals("") && code2.equals("") && code1.equals("")){
						JOptionPane.showMessageDialog(null, "��ǰ�ڵ带 �Է��� �ּ���", "�޽���", JOptionPane.INFORMATION_MESSAGE);
					}
				
					else {
						int check = JOptionPane.showConfirmDialog(null, "�Է��� ������ �½��ϱ�?\n" + 
								"�̸� : "+ name + "\n��ȭ��ȣ : "+phone + "\n�ݳ��� : " + returnDay + 
								"\n��ǰ�ڵ�[1] : " + code1 + "\n��ǰ�ڵ�[2] : " + code2 + "\n��ǰ�ڵ�[3] : " + code3,
								"�޽���", JOptionPane.INFORMATION_MESSAGE );
						if(check == 0) {
							try {
								User u = new User(name, phone, rentalDay, returnDay);
								if(!code1.equals("")) {
									int index1 = act.search(code1);
									int amount1 = act.productAt(index1).getPrice();
									u.addProduct(code1, amount1);
								}
								if(!code2.equals("")) {
									int index2 = act.search(code2);
									int amount2 = act.productAt(index2).getPrice();
									u.addProduct(code2, amount2);
								}
								if(!code3.equals("")) {
									int index3 = act.search(code3);
									int amount3 = act.productAt(index3).getPrice();
									u.addProduct(code3, amount3);
								}
								act.checkIn(u);
								JOptionPane.showMessageDialog(null, "�뿩�� �Ϸ�Ǿ����ϴ�.", "�޽���", JOptionPane.INFORMATION_MESSAGE);
								new resetInfo().actionPerformed(e);
								nameTf.setText(null);
								phoneTf.setText(null);
								codeTf_1.setText(null);
								codeTf_2.setText(null);
								codeTf_3.setText(null);
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(null, "�߸��� ����� üũ���Դϴ�.", "�޽���", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				}
			}
		}
	}
	
	
	/*************** üũ�ƿ� *****************/
	
	public class CheckOut extends JPanel {
		
		private JLabel phone = new JLabel("��ȭ��ȣ");
		private JTextField phoneTf = new JTextField(10);
		private JButton viewButton = new JButton("��ȸ");
		private JLabel bottom_la = new JLabel("[��ȭ��ȣ] �Է� -> [��ȸ] ��ư Ŭ�� -> [�ݳ�] ��ư Ŭ�� ������ �������ּ���.");
		private JLabel pay = new JLabel("���ұݾ�");
		private JLabel pay2 = new JLabel("(��)");
		private JTextField payTf = new JTextField(5);
		private JButton returnButton = new JButton("�ݳ�");
		private JPanel[] p = new JPanel[3];
		private DefaultTableModel model;
		private JTable table = null;
		private String[] column =  {
			"�̸�", "��ȭ��ȣ", "�뿩��", "�ݳ���", "��ǰ��[1]", "��ǰ��[2]", "��ǰ��[3]"
		};

		public CheckOut() {
			
			this.setBackground(Color.white);
			setLayout(new BorderLayout());
			
			// �г� ����
			for (int i = 0; i < p.length; i++) {
				p[i] = new JPanel();
			}
			
			p[0].add(phone);
			p[0].add(phoneTf);
			p[0].add(pay);
			p[0].add(payTf); payTf.setEditable(false);
			p[0].add(pay2);
			p[0].add(viewButton);
			p[0].add(returnButton);
			p[2].add(bottom_la);
			add(p[0], BorderLayout.NORTH);
			add(p[1], BorderLayout.CENTER);
			add(p[2], BorderLayout.SOUTH);
			
			// "��ȸ" ��ư�� �׼Ǹ����� �߰�
			viewButton.addActionListener(new findUserInfo());
			// "�ݳ�" ��ư�� �׼Ǹ����� �߰�
			returnButton.addActionListener(new returnInfo());
			
			model = new DefaultTableModel(column, 0);
			table = new JTable(model);
			table.setSize(660,387);
			
			// ���̺� ���� ��� �����ϱ�
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); 
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			TableColumnModel tcm = table.getColumnModel();
			for(int i=0; i<tcm.getColumnCount(); i++){
				tcm.getColumn(i).setCellRenderer(dtcr);
			}
			
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setSize(660, 356);	
			scrollPane.setLocation(12, 10);
			scrollPane.setPreferredSize(new Dimension(600, 41));
			p[1].add(scrollPane, BorderLayout.CENTER);
			p[1].setVisible(true);
		
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
						int idx = act.search(act.userAt(index).codeAt(k));
						ob[4+k] = act.productAt(idx).getName();
						//ob[4+k] = act.userAt(index).codeAt(k);
					}
					model.addRow(ob);
					
					int money = act.userAt(index).pay();
					payTf.setText(String.valueOf(money));
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "��ġ�ϴ� ��ȭ��ȣ�� ã�� �� �����ϴ�.", "�޽���", JOptionPane.ERROR_MESSAGE);
				}
			}
		}	
		
		// �ݳ�
		class returnInfo implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				String target = phoneTf.getText();
				int index;
				
				if (target.equals("")) {
					JOptionPane.showMessageDialog(null, "��ȭ��ȣ�� �Է��� �ּ���.", "�޽���", JOptionPane.ERROR_MESSAGE);
				}
				else {
					int check2 = JOptionPane.showConfirmDialog(null, "�ݳ��� �����Ͻðڽ��ϱ�?");
					if(check2 == 0){ 
						try {
							index = act.searchUser(target);
							act.checkOut(index);
							JOptionPane.showMessageDialog(null, "�ݳ��� �Ϸ�Ǿ����ϴ�.", "�޽���", JOptionPane.INFORMATION_MESSAGE);
							phoneTf.setText(null);
							int modelSize = model.getRowCount();
							for (int i = 0; i < modelSize; i++) {
								model.removeRow(0);
							}
							payTf.setText(null);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "�߸��� ����� üũ�ƿ��Դϴ�.", "�޽���", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		}	
	}

	static public void main(String[] arg) {
		GuiHomeFrame frame = new GuiHomeFrame();
		frame.setVisible(true);
	}
}


