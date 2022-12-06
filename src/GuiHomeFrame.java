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
			// dis 객체를 매니저 생성자에 전달
			act = new Manager(100, 100, dis);
			// 파일 닫기
			dis.close();
		} catch (FileNotFoundException fnfe) { // 파일을 찾을 수 없을 때
			while (true)
			{
				int answer = JOptionPane.showConfirmDialog(null, "파일을 찾을 수 없습니다. 지난번에 파일을 저장하였습니까?", "메시지", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				
				// 대답이 YES면 프로그램 종료
				if (answer == 0)
				{ 
					JOptionPane.showMessageDialog(null, "파일을 찾을 수 없습니다. 시스템을 종료합니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
				// 대답이 NO면 파일 생성
				else if (answer == 1)
				{
					JOptionPane.showMessageDialog(null,"완전히 새로운 파일로 시작합니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
					act = new Manager(100, 100);
					break;
				}
			}
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "파일을 찾을 수 없습니다. 시스템을 종료합니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
		}
		
		setTitle("렌탈 서비스 프로그램");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		pane = createTabbedPane();
		c.add(pane, BorderLayout.CENTER);
		createMenu();
		setSize(680, 450);
	}
	
	// 탭
	private JTabbedPane createTabbedPane() {
		
		pane.addTab("메인", new MainTap());
		pane.addTab("체크인", new CheckIn());
		pane.addTab("체크아웃", new CheckOut());
		return pane;
	}

	// 메뉴바
	private void createMenu() {
		JMenu fileMenu = new JMenu("File");
		JMenuItem save = new JMenuItem("Save");
		fileMenu.add(save);
		JMenuBar mb = new JMenuBar();
		mb.add(fileMenu);
		setJMenuBar(mb);
		
		// 파일 저장
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fileSave();
					JOptionPane.showMessageDialog(null, "파일이 저장되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
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
			//JOptionPane.showMessageDialog(this, "파일이 저장되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
		} catch (FileNotFoundException fnfe) { // 데이터 출력 스트림의 생성자가 발생시키는 익셉션을 처리
			JOptionPane.showMessageDialog(this, "파일이 존재하지 않습니다.", "메시지", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) { // 파일 입출력 예외 처리
			JOptionPane.showMessageDialog(this, "잘못된 파일 저장입니다.", "메시지", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "잘못된 파일 저장입니다.", "메시지", JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				if (infoFile != null) // infoFile이 null이 아닌지 확인한 후, 스트림을 닫기
					infoFile.close();
			}
			catch (Exception e) {
			}
		}
	}
	
	/*************** 메인 *****************/
	
	class MainTap extends JPanel {
		
		private JPanel[] p = new JPanel[3];
		private JLabel mainLabel = new JLabel("Welcome to the rental shop!");
		private JButton mgrBtn = new JButton("관리자 모드");
		private JLabel pwLabel = new JLabel("password : ");
		private JTextField password = new JTextField(10);
		
		public MainTap() {
			setLayout(new BorderLayout());
			
			// 패널 생성
			for (int i = 0; i < p.length; i++) {
				p[i] = new JPanel();
			}
			
			mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
			mainLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 25));
			add(mainLabel, BorderLayout.CENTER);

			mgrBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(password.getText().equals("1111")) { // 입력한 관리자 비밀번호가 맞으면, 관리자 모드로 진입 
						dispose();
						new GuiManagerFrame();
					}
					else {
						JOptionPane.showMessageDialog(null, "관리자 비밀번호가 틀렸습니다.", "메시지", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			p[1].add(pwLabel);
			p[1].add(password);
			p[1].add(mgrBtn);
			add(p[1], BorderLayout.SOUTH);

		}
	}
	
	/*************** 체크인 *****************/
	
	public class CheckIn extends JPanel {
		
		private DefaultTableModel model;
		private JTable table = null;
		private String[] column =  {
			"상품명", "상품코드", "개수(재고)", "가격"
		};
		private String[] searchName = { "상품명", "상품코드" };
		private JTextField searchTf = new JTextField(10);
		private JComboBox<String> searchBox;
		private JButton viewButton = new JButton("조회");
		private JButton resetButton = new JButton("초기화");
		private JLabel name = new JLabel("이름");
		private JTextField nameTf = new JTextField(5);
		private JLabel phone = new JLabel("전화번호");
		private JTextField phoneTf = new JTextField(5);
		private JLabel returnDay = new JLabel("반납일");
		private SpinnerDateModel m;
		private JSpinner spinner;
		private JLabel code = new JLabel("상품코드 (최대 3개)");
		private JTextField codeTf_1 = new JTextField(3);
		private JTextField codeTf_2 = new JTextField(3);
		private JTextField codeTf_3 = new JTextField(3);
		private JButton rentalButton = new JButton("대여");
		private JPanel[] p = new JPanel[3];
		
		public CheckIn() {
			
			this.setBackground(Color.white);
			setLayout(new BorderLayout());
			
			// 패널 생성
			for (int i = 0; i < p.length; i++) {
				p[i] = new JPanel();
			}

			// 검색 선택 콤보박스 생성
			searchBox = new JComboBox<String>();
			for (int i = 0; i < searchName.length; i++)
				searchBox.addItem(searchName[i]);
			
			// "조회" 버튼에 액션리스너 추가
			viewButton.addActionListener(new findProductInfo());
			// "초기화" 버튼에 액션리스너 추가
			resetButton.addActionListener(new resetInfo());
			// "대여" 버튼에 액션리스너 추가
			rentalButton.addActionListener(new rentalInfo());

			// 반납일 날짜 선택
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
			
			// 테이블
			Object[][] ob = new Object[act.getProductCount()][4];
			for(int i = 0; i < act.getProductCount(); i++) {
				ob[i][0] = act.productAt(i).getName();
				ob[i][1] = act.productAt(i).getCode();
				ob[i][2] = act.productAt(i).getNumber();
				ob[i][3] = act.productAt(i).getPrice();
			}
			
			model = new DefaultTableModel(ob, column);
			table= new JTable(model);
				
			table.getColumn("상품명").setPreferredWidth(250);
			table.getColumn("상품코드").setPreferredWidth(250);
			table.getColumn("개수(재고)").setPreferredWidth(250);
			table.getColumn("가격").setPreferredWidth(250);
			table.setRowHeight(20);
			table.addMouseListener(new MyMouseListener()); // 테이블에 마우스 리스너 추가
			
			// 테이블 내용 가운데 정렬하기
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
		
		// 테이블 클릭 이벤트
		class MyMouseListener extends MouseAdapter {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				String code1 = codeTf_1.getText();
				String code2 = codeTf_2.getText();
				String code3 = codeTf_3.getText();
				if (e.getButton() == 1) { // 셀 클릭 시
					int row = table.getSelectedRow();
					try {
						String code = (String) model.getValueAt(row, 1); // 선택한 행의 상품 코드 값을 저장
						if(code1.equals("") && code2.equals("") && code3.equals("")) // 3개의 텍스트 필드에 모두 입력된 코드가 없으면
							codeTf_1.setText(code); // 1번 텍스트 필드에 입력
						else if(!code1.equals("") && !code2.equals("")) // 1번, 2번 텍스트 필드에 입력된 코드가 존재하면
							codeTf_3.setText(code); // 3번 텍스트 필드에 입력
						else if(!code1.equals("")) // 1번 텍스트 필드에 입력된 코드가 존재하면
							codeTf_2.setText(code); // 2번 텍스트 필드에 입력
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "테이블 선택 입력 오류 발생", "메시지", JOptionPane.ERROR_MESSAGE);
					}
				}
				 // 3개의 텍스트 필드에 모두 입력된 코드가 존재하는데, 셀 클릭 시 -> 텍스트 필드 초기화
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
	
		// 상품 정보 조회
		class findProductInfo implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				
				String target = searchTf.getText();
				
				if (searchBox.getSelectedItem().equals("상품명")) {
					
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
				
				else if (searchBox.getSelectedItem().equals("상품코드")) {
					
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
		
		// 테이블 초기화
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
						// 행 추가
						model.addRow(ob);
					}

				} catch (NullPointerException npe) {
					JOptionPane.showMessageDialog(null, "Error", "메시지", JOptionPane.ERROR_MESSAGE);
				}
			}	
		}
		
		// 대여
		class rentalInfo implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				
				String rDay = new SimpleDateFormat("yyyyMMdd").format(spinner.getValue());
				String today = new SimpleDateFormat("yyyyMMdd").format(getToday.getTime());
				
				if (Integer.parseInt(today) > Integer.parseInt(rDay)) {
					JOptionPane.showMessageDialog(null, "지정하신 반납일이 오늘 날짜보다 이전입니다.", "메시지", JOptionPane.ERROR_MESSAGE);
				}
				
				// 반납일이 오늘보다 이후일 때만 체크인 진행
				else {
					
					String name = nameTf.getText();
					String phone = phoneTf.getText();
					String returnDay = new SimpleDateFormat("yyyy-MM-dd").format(spinner.getValue());
					String rentalDay = new SimpleDateFormat("yyyy-MM-dd").format(getToday.getTime());
					String code1 = codeTf_1.getText();
					String code2 = codeTf_2.getText();
					String code3 = codeTf_3.getText();
					
					if(name.equals("")){
						JOptionPane.showMessageDialog(null, "이름을 입력해 주세요", "메시지", JOptionPane.INFORMATION_MESSAGE);
					} else if(phone.equals("")){
						JOptionPane.showMessageDialog(null, "전화번호를 입력해 주세요", "메시지", JOptionPane.INFORMATION_MESSAGE);
					} else if(code1.equals("") && code2.equals("") && code1.equals("")){
						JOptionPane.showMessageDialog(null, "상품코드를 입력해 주세요", "메시지", JOptionPane.INFORMATION_MESSAGE);
					}
				
					else {
						int check = JOptionPane.showConfirmDialog(null, "입력한 내용이 맞습니까?\n" + 
								"이름 : "+ name + "\n전화번호 : "+phone + "\n반납일 : " + returnDay + 
								"\n상품코드[1] : " + code1 + "\n상품코드[2] : " + code2 + "\n상품코드[3] : " + code3,
								"메시지", JOptionPane.INFORMATION_MESSAGE );
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
								JOptionPane.showMessageDialog(null, "대여가 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
								new resetInfo().actionPerformed(e);
								nameTf.setText(null);
								phoneTf.setText(null);
								codeTf_1.setText(null);
								codeTf_2.setText(null);
								codeTf_3.setText(null);
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(null, "잘못된 방법의 체크인입니다.", "메시지", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				}
			}
		}
	}
	
	
	/*************** 체크아웃 *****************/
	
	public class CheckOut extends JPanel {
		
		private JLabel phone = new JLabel("전화번호");
		private JTextField phoneTf = new JTextField(10);
		private JButton viewButton = new JButton("조회");
		private JLabel bottom_la = new JLabel("[전화번호] 입력 -> [조회] 버튼 클릭 -> [반납] 버튼 클릭 순서로 진행해주세요.");
		private JLabel pay = new JLabel("지불금액");
		private JLabel pay2 = new JLabel("(원)");
		private JTextField payTf = new JTextField(5);
		private JButton returnButton = new JButton("반납");
		private JPanel[] p = new JPanel[3];
		private DefaultTableModel model;
		private JTable table = null;
		private String[] column =  {
			"이름", "전화번호", "대여일", "반납일", "상품명[1]", "상품명[2]", "상품명[3]"
		};

		public CheckOut() {
			
			this.setBackground(Color.white);
			setLayout(new BorderLayout());
			
			// 패널 생성
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
			
			// "조회" 버튼에 액션리스너 추가
			viewButton.addActionListener(new findUserInfo());
			// "반납" 버튼에 액션리스너 추가
			returnButton.addActionListener(new returnInfo());
			
			model = new DefaultTableModel(column, 0);
			table = new JTable(model);
			table.setSize(660,387);
			
			// 테이블 내용 가운데 정렬하기
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
		
		// 유저 정보 조회
		class findUserInfo implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				
				int modelSize = model.getRowCount();
				for (int i = 0; i < modelSize; i++) {
					model.removeRow(0);
				}
				
				String target = phoneTf.getText();
				int index;
				try {
					// 일치하는 전화번호 확인
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
					JOptionPane.showMessageDialog(null, "일치하는 전화번호를 찾을 수 없습니다.", "메시지", JOptionPane.ERROR_MESSAGE);
				}
			}
		}	
		
		// 반납
		class returnInfo implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				String target = phoneTf.getText();
				int index;
				
				if (target.equals("")) {
					JOptionPane.showMessageDialog(null, "전화번호를 입력해 주세요.", "메시지", JOptionPane.ERROR_MESSAGE);
				}
				else {
					int check2 = JOptionPane.showConfirmDialog(null, "반납을 진행하시겠습니까?");
					if(check2 == 0){ 
						try {
							index = act.searchUser(target);
							act.checkOut(index);
							JOptionPane.showMessageDialog(null, "반납이 완료되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
							phoneTf.setText(null);
							int modelSize = model.getRowCount();
							for (int i = 0; i < modelSize; i++) {
								model.removeRow(0);
							}
							payTf.setText(null);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "잘못된 방법의 체크아웃입니다.", "메시지", JOptionPane.ERROR_MESSAGE);
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


