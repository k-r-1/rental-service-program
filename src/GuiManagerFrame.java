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
		
		setTitle("렌탈 서비스 프로그램(관리자 모드)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		pane = createTabbedPane();
		c.add(pane, BorderLayout.CENTER);
		setSize(980, 750);
		setVisible(true);
		
	}
	
	// 탭
	private JTabbedPane createTabbedPane() {
		
		pane.addTab("상품 관리", new ProductTap());
		pane.addTab("체크인 정보", new CheckInInfo());
		return pane;
	}
	
	/*************** 상품 관리 *****************/
	
	class ProductTap extends JPanel {
		
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
		private TitledBorder title;
		private JPanel[] p = new JPanel[7];
		private JLabel pName = new JLabel("상품명");
		private JTextField pNameTf = new JTextField(5);
		private JLabel pCode = new JLabel("코드");
		private JTextField pCodeTf = new JTextField(5);
		private JLabel pNum = new JLabel("개수");
		private JTextField pNumTf = new JTextField(5);
		private JLabel pPrice = new JLabel("가격");
		private JTextField pPriceTf = new JTextField(5);
		private JButton addBtn = new JButton("등록");
		private JLabel pdelCode = new JLabel("코드");
		private JTextField pdelCodeTf = new JTextField(5);
		private JButton delBtn = new JButton("삭제");
		private JLabel pEditCode = new JLabel("코드");
		private JTextField pEditCodeTf = new JTextField(5);
		private JLabel pEditNum = new JLabel("개수");
		private JTextField pEditNumTf = new JTextField(5);
		private JButton upBtn = new JButton("추가");
		private JButton subBtn = new JButton("감소");
		private JLabel revenue = new JLabel("(원)");
		private JTextField revenueTf = new JTextField(5);
		
		public ProductTap() {
			
			this.setBackground(Color.white);
			setLayout(new BorderLayout());
			
			// 패널 생성
			for (int i = 0; i < p.length; i++) {
				p[i] = new JPanel();
			}
			
			title = BorderFactory.createTitledBorder("상품등록");
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
			
			title = BorderFactory.createTitledBorder("상품삭제");
			p[2].setBorder(title);
			p[2].add(pdelCode);
			p[2].add(pdelCodeTf);
			p[2].add(delBtn);
			
			title = BorderFactory.createTitledBorder("재고수정");
			p[3].setBorder(title);
			p[3].add(pEditCode);
			p[3].add(pEditCodeTf);
			p[3].add(pEditNum);
			p[3].add(pEditNumTf);
			p[3].add(upBtn);
			p[3].add(subBtn);
			
			// 검색 선택 콤보박스 생성
			searchBox = new JComboBox<String>();
			for (int i = 0; i < searchName.length; i++)
				searchBox.addItem(searchName[i]);
			
			// "조회" 버튼에 액션리스너 추가
			viewButton.addActionListener(new findProductInfo());
			// "초기화" 버튼에 액션리스너 추가
			resetButton.addActionListener(new resetInfo());
			// "상품 등록" 버튼에 액션리스너 추가
			addBtn.addActionListener(new addInfo());
			// "상품 삭제" 버튼에 액션리스너 추가
			delBtn.addActionListener(new delInfo());
			// "재고 추가" 버튼에 액션리스너 추가
			upBtn.addActionListener(new upInfo());
			// "재고 감소" 버튼에 액션리스너 추가
			subBtn.addActionListener(new subInfo());
		
			p[4].add(searchBox);
			p[4].add(searchTf);
			p[4].add(viewButton);
			p[4].add(resetButton);
			
			// 매출 가져오기
			revenueTf.setEditable(false);
			revenueTf.setText(String.valueOf(act.getRevenue()));
			
			title = BorderFactory.createTitledBorder("매출");
			p[6].setBorder(title);
			p[6].add(revenueTf);
			p[6].add(revenue);
			
			p[0].add(p[1]);
			p[0].add(p[2]);
			p[0].add(p[3]);
			p[4].add(p[6]);
			
			add(p[4], BorderLayout.NORTH);
			add(p[0], BorderLayout.SOUTH);
			
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
				if (e.getButton() == 1) { // 셀 클릭 시
					int row = table.getSelectedRow();
					try {
						String code = (String) model.getValueAt(row, 1); // 선택한 행의 상품 코드 값을 저장
						pdelCodeTf.setText(code); // 상품삭제 텍스트 필드 값 수정
						pEditCodeTf.setText(code); // 재고수정 텍스트 필드 값 수정
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "테이블 선택 입력 오류 발생", "메시지", JOptionPane.ERROR_MESSAGE);
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
		
		// 상품 등록
		class addInfo implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				
				String pName = pNameTf.getText();
				String pCode = pCodeTf.getText();
				String pNum = pNumTf.getText();
				String pPrice = pPriceTf.getText();
				
				if(pCode.equals("")){
					JOptionPane.showMessageDialog(null, "상품 코드를 입력해 주세요", "메시지", JOptionPane.ERROR_MESSAGE);
					} else if(pName.equals("")){
						JOptionPane.showMessageDialog(null, "상품명을 입력해 주세요", "메시지", JOptionPane.ERROR_MESSAGE);
					} else if(pNum.equals("")){
						JOptionPane.showMessageDialog(null, "상품 개수를 입력해 주세요", "메시지", JOptionPane.ERROR_MESSAGE);
					} else if(pPrice.equals("")){
						JOptionPane.showMessageDialog(null, "상품 가격을 입력해 주세요", "메시지", JOptionPane.ERROR_MESSAGE);
	
					} else if(!integerOrNot(pNum)){
							JOptionPane.showMessageDialog(null, "상품 개수는 문자를 입력할 수 없습니다.", "메시지", JOptionPane.ERROR_MESSAGE);
					} else if(!integerOrNot(pPrice)){
							JOptionPane.showMessageDialog(null, "상품 가격은 문자를 입력할 수 없습니다.", "메시지", JOptionPane.ERROR_MESSAGE);
					} else {
						for(int i=0; i<act.getProductCount(); i++){
							if(pCode.equals(act.productAt(i).getCode())){
								JOptionPane.showMessageDialog(null, "동일한 상품 코드가 있습니다.", "메시지", JOptionPane.ERROR_MESSAGE);
								break;
							}
						}
					
					int check = JOptionPane.showConfirmDialog(null, "입력한 내용이 맞습니까?\n" + 
							"상품명 : "+ pName + "\n코드 : "+pCode + "\n개수 : " + pNum + 
							"\n가격 : " + pPrice,
							"메시지", JOptionPane.INFORMATION_MESSAGE );
					if(check == 0){
						Product p = new Product();
						p.setName(pName);
						p.setCode(pCode);
						p.setNumber(Integer.parseInt(pNum));
						p.setPrice(Integer.parseInt(pPrice));
						try {
							act.add(p);
							JOptionPane.showMessageDialog(null, "상품이 등록되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
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
								// 행 추가
								model.addRow(ob);
							} catch (NullPointerException npe) {
								JOptionPane.showMessageDialog(null, "Error", "메시지", JOptionPane.ERROR_MESSAGE);
							}
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "잘못된 상품 등록입니다.", "메시지", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		}
		
		// 상품 삭제
		class delInfo implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				
				String pCode = pdelCodeTf.getText();
				int index;
				try {
					index = act.search(pCode);
					Product p = act.getProductList().get(index);
					act.delete(p.getCode());
					JOptionPane.showMessageDialog(null, "상품이 삭제되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
					pdelCodeTf.setText(null);
					// 모델에서 삭제
					for (int i = 0; i < model.getRowCount(); i++) {
						if (model.getValueAt(i, 1).equals(pCode)) {
							model.removeRow(i);
							break;
						}
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "존재하지 않는 상품입니다.", "메시지", JOptionPane.ERROR_MESSAGE);;
				}
			}
		}
		
		// 재고 추가
		class upInfo implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				
				String pCode = pEditCodeTf.getText();
				String pNum = pEditNumTf.getText();
				int index;
				
				if(pCode.equals("")){
					JOptionPane.showMessageDialog(null, "상품 코드를 입력해 주세요", "메시지", JOptionPane.ERROR_MESSAGE);
				}else if(pNum.equals("")){
					JOptionPane.showMessageDialog(null, "상품 개수를 입력해 주세요", "메시지", JOptionPane.ERROR_MESSAGE);
				}else if(!integerOrNot(pNum)){
					JOptionPane.showMessageDialog(null, "상품 개수는 문자를 입력할 수 없습니다.", "메시지", JOptionPane.ERROR_MESSAGE);
				}
				
				else {
					try {
						index = act.search(pCode);
						act.addStock(index, Integer.parseInt(pNum));
						JOptionPane.showMessageDialog(null, "재고가 추가되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
						pEditCodeTf.setText(null);
						pEditNumTf.setText(null);
						// 모델에서 재고 증가
						for (int i = 0; i < model.getRowCount(); i++) {
							if (model.getValueAt(i, 1).equals(pCode)) {
								model.setValueAt(act.productAt(index).getNumber(), i, 2);
								break;
							}
						}
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "잘못된 재고 수정입니다.", "메시지", JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}
		}
		
		// 재고 감소
		class subInfo implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				
				String pCode = pEditCodeTf.getText();
				String pNum = pEditNumTf.getText();
				int index;
				
				if(pCode.equals("")){
					JOptionPane.showMessageDialog(null, "상품 코드를 입력해 주세요", "메시지", JOptionPane.ERROR_MESSAGE);
				}else if(pNum.equals("")){
					JOptionPane.showMessageDialog(null, "상품 개수를 입력해 주세요", "메시지", JOptionPane.ERROR_MESSAGE);
				}else if(!integerOrNot(pNum)){
					JOptionPane.showMessageDialog(null, "상품 개수는 문자를 입력할 수 없습니다.", "메시지", JOptionPane.ERROR_MESSAGE);
				}
				
				else {
					try {
						index = act.search(pCode);
						act.subStock(index, Integer.parseInt(pNum));
						JOptionPane.showMessageDialog(null, "재고가 감소되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
						pEditCodeTf.setText(null);
						pEditNumTf.setText(null);
						// 모델에서 재고 감소
						for (int i = 0; i < model.getRowCount(); i++) {
							if (model.getValueAt(i, 1).equals(pCode)) {
								model.setValueAt(act.productAt(index).getNumber(), i, 2);
								break;
							}
						}
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "잘못된 재고 수정입니다.", "메시지", JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}
		}
		
		// 입력값이 숫자인지 문자인지 판별 
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
	
	/*************** 체크인 정보 *****************/
	
	class CheckInInfo extends JPanel {
		
		private DefaultTableModel model;
		private JTable table = null;
		private String[] column =  {
			"이름", "전화번호", "대여일", "반납일", "대여코드[1]", "대여코드[2]", "대여코드[3]"
		};
		
		private JLabel phone = new JLabel("전화번호");
		private JTextField phoneTf = new JTextField(10);
		private JButton viewButton = new JButton("조회");
		private JButton resetButton = new JButton("초기화");
		private JPanel[] p = new JPanel[3];
		
		public CheckInInfo() {
			
			this.setBackground(Color.white);
			setLayout(new BorderLayout());
			
			// 패널 생성
			for (int i = 0; i < p.length; i++) {
				p[i] = new JPanel();
			}
			
			p[0].add(phone);
			p[0].add(phoneTf);
			p[0].add(viewButton);
			p[0].add(resetButton);
			add(p[0], BorderLayout.NORTH);
			
			// "조회" 버튼에 액션리스너 추가
			viewButton.addActionListener(new findUserInfo());
			// "초기화" 버튼에 액션리스너 추가
			resetButton.addActionListener(new resetInfo());
			
			// 테이블
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
				
			table.getColumn("이름").setPreferredWidth(250);
			table.getColumn("전화번호").setPreferredWidth(250);
			table.getColumn("대여일").setPreferredWidth(250);
			table.getColumn("반납일").setPreferredWidth(250);
			table.getColumn("대여코드[1]").setPreferredWidth(250);
			table.getColumn("대여코드[2]").setPreferredWidth(250);
			table.getColumn("대여코드[3]").setPreferredWidth(250);
			table.setRowHeight(20);
			
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
						ob[4+k] = act.userAt(index).codeAt(k);
					}
					model.addRow(ob);
			
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "일치하는 전화번호를 찾을 수 없습니다.", "메시지", JOptionPane.ERROR_MESSAGE);
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
					JOptionPane.showMessageDialog(null, "Error", "메시지", JOptionPane.ERROR_MESSAGE);
				}
			}	
		}	
	}
}
