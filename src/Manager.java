import java.io.*;
import java.util.ArrayList;

public class Manager {
	private ArrayList<Product> productList; // 상품 배열
	private ArrayList<User> userList; // 대여 배열
	private int revenue = 0; // 매출 총액 변수
	
	// 생성자 1. 파일이 아예 없는 상태를 위한 생성자
	Manager (int maxProductCount, int maxUserCount) {
		productList = new ArrayList<Product>(maxProductCount); // 상품 배열 크기 설정
		userList = new ArrayList<User>(maxUserCount); // 대여 배열 크기 설정
	}
	
	// 생성자 2. 기존 파일이 있는 상태에서 데이터를 로드하기 위한 생성자
	Manager (int maxProductCount, int maxUserCount, ObjectInputStream dis) throws Exception 
	{ 
		productList = new ArrayList<Product>(maxProductCount); // 상품 배열 크기 설정
		userList = new ArrayList<User>(maxUserCount); // 대여 배열 크기 설정
		
		// 데이터 로드
		try {
			Integer pCount = (Integer) dis.readObject(); // 저장된 상품 개수만큼 읽기
			for (int i = 0; i < pCount.intValue(); i++) 
			{
				// 상품 객체를 받아서 배열에 넣기
				productList.add((Product) dis.readObject());
			}
			Integer uCount = (Integer) dis.readObject(); // 저장된 유저 수만큼 읽기
			for (int i = 0; i < uCount.intValue(); i++) 
			{
				// 유저 객체를 받아서 배열에 넣기
				userList.add((User) dis.readObject()); 
			}
			// 저장된 매출액 읽기
			Integer r = (Integer) dis.readObject();
			revenue = r.intValue();
		}
		catch(FileNotFoundException fnfe){
			System.out.println("파일이 존재하지 않습니다.");
		}
		catch(EOFException eofe){
		}
		catch(IOException ioe){
			System.out.println("파일을 읽을 수 없습니다.");
		}
		catch(ClassNotFoundException cnfe){
			System.out.println("해당 클래스가 존재하지 않습니다.");
		}
	}

	// 파일 저장
	void saveObject(ObjectOutputStream oos) throws IOException {
		try {
			// 상품 배열, 유저 배열을 상품 수, 유저 수만큼 파일에 기록
			Integer pCount = Integer.valueOf(productList.size());
			oos.writeObject(pCount);
			for (int i = 0; i < pCount; i++)
			{
				oos.writeObject(productList.get(i)); 
			}
			Integer uCount = Integer.valueOf(userList.size());
			oos.writeObject(uCount);
			for (int i = 0; i < uCount; i++)
			{
				oos.writeObject(userList.get(i));
			}
			// 매출 저장
			oos.writeObject(Integer.valueOf(revenue));
		} catch (IOException e) {
			throw new IOException ("잘못된 파일 저장입니다.");
		} 		
	}
	
	// 코드 중복 검색(오버라이딩 한 equals 사용)
	public void checkCode(Product p) throws Exception {
		// 상품 코드가 이미 있으면
		if (productList.indexOf(p) >= 0)
			// 예외 발생
			throw new Exception("잘못된 상품 등록입니다.");
	}
	
	// 상품 추가
	public void add(Product p) throws Exception {
		try{
			checkCode(p); // 코드 중복 검색
			productList.add(p); // 상품 추가
		}
		catch (Exception e) {
			throw new Exception ("잘못된 상품 등록입니다.");
		}
	}
	
	// 상품 삭제
	public void delete(String productCode) throws Exception {
		try{
			int number = search(productCode); // 상품 배열에서 검색하기
			productList.remove(number); // 상품 배열에서 삭제하기
		}
		catch (Exception e) {
			throw new Exception ("존재하지 않는 상품입니다.");
		}
	}

	// 상품 객체 검색(오버라이딩 한 equals 사용)
	public int search(String productCode) throws Exception {	
		// 인수로 받은 상품 코드와 같은 코드를 가진 상품이 있는지 찾기 
		int index = productList.indexOf(new Product(productCode));
		// 상품이 있으면
		if (index >= 0)
			// 인덱스 반환
			return index;
		// 상품이 없으면
		else
			// 익셉션 발생
			throw new Exception("일치하는 코드를 찾을 수 없습니다.");
	}
	
	
	// 상품 배열 i번째 리턴
	public Product productAt(int i) {
		try {
			return productList.get(i); // 상품 배열 i번째 상품 객체 return
		} catch (IndexOutOfBoundsException iobe) {
			return null;
		}
	}
	
	// productCount 사이즈값 반환
	public int getProductCount() {
		return productList.size();
	}
	
	// 재고 증가
	public void addStock(int index, int n) {
		Product p = productList.get(index); // 해당 인덱스의 product 객체
		p.setNumber(p.getNumber() + n); // 상품 리스트의 index 번째 상품 객체의 재고를 n개 증가

	}
	
	// 재고 감소
	public void subStock(int index, int n) throws Exception {
		
		Product p = productList.get(index); // 해당 인덱스의 product 객체
		if (p.getNumber() < n ) {
			throw new Exception("잘못된 방법의 체크인입니다."); // n개 이하의 재고면 exception 발생
		}
		else
			p.setNumber(p.getNumber() - n); // 상품 리스트의 index 번째 상품 객체의 재고가 n개 이상일 경우 재고를 n개 감소
	}
	
	// 전화번호 중복 검색(오버라이딩 한 equals 사용)
	public void checkPhone(User u) throws Exception {
		// 전화번호가 이미 있으면
		if (userList.indexOf(u) >= 0)
			// 예외 발생
			throw new Exception("잘못된 방법의 체크인입니다.");
	}

	// 재고 개수에서 대여 개수 제외
	public void subStock(User u) throws Exception {
		// 재고 개수에서 대여 개수 제외하기
		for(int i = 0; i < u.getRentalCount(); i++)
		{
			String code = u.codeAt(i); // 해당 User 객체의 i번째 대여 물품 코드
			int searchNum;
			try {
				searchNum = search(code); // productList에서 해당 코드의 인덱스 번호 검색
			} 
			catch (Exception e) {
				throw new Exception("잘못된 방법의 체크인입니다.");
			}
			Product p = productList.get(searchNum); //해당 인덱스의 product 객체
			p.subNumber(); // 대여가 가능한지 확인 후 빌리기 (재고 1개 삭제)
		}
	}
	
	// 대여 배열에 원소 추가
	public void addUser(User u) {
		userList.add(u);
	}

	// 체크인
	public void checkIn(User u) throws Exception {
		try {
			checkPhone(u); // 전화번호 중복 검색
			subStock(u); // 재고 개수에서 대여 개수 제외
			addUser(u); // 대여 배열에 대여 정보 넣기
		}
		catch(Exception e) {
			throw new Exception("잘못된 방법의 체크인입니다.");
		}
	}
	
	// userCount 사이즈값 반환
	public int getUserCount() {
		return userList.size();
	}

	
	// 대여 배열 i번째 리턴
	public User userAt(int i) 
	{
		try {
			return userList.get(i); // 대여 배열 i번째 유저 객체 return
		} catch (IndexOutOfBoundsException iobe) {
			return null;
		}
	}
	
	// 일치하는 회원번호 검색(오버라이딩 한 equals 사용)
	public int searchUser(String phone) throws Exception {
		// 인수로 받은 전화번호와 같은 전화번호를 가진 상품이 있는지 찾기 
		int index = userList.indexOf(new User(phone));
		// 상품이 있으면
		if (index >= 0)
			// 인덱스 반환
			return index;
		// 상품이 없으면
		else
			// 익셉션 발생
			throw new Exception("일치하는 코드를 찾을 수 없습니다.");
	}

	// 상품 재고 다시 추가
	public void addStock(int index) throws Exception {
		User u = userAt(index);
		for(int i = 0; i < u.getRentalCount(); i++) {
			String code = u.codeAt(i); // 해당 User 객체의 i번째 대여 물품 코드
			try {
				int number = search(code); // productList에서 해당 코드의 인덱스 번호 검색
				productAt(number).addNumber(); //해당 인덱스의 product 객체의 재고 추가하기
			}
			catch (Exception e) {
				throw new Exception ("잘못된 방법의 체크아웃입니다.");
			}
		}
	}
	
	// 대여 배열에 원소 삭제
	public void subUser(int number) {
		userList.remove(number);
	}
	
	// 체크아웃
	public void checkOut(int index) throws Exception{
		try{
			addStock(index);// 상품 재고 다시 추가하기
			int money = userList.get(index).pay();// 금액 반환받기
			subUser(index); // userList에서 삭제, 배열 정리하기
			revenue += money; // 매출에 추가하기
		}
		catch(Exception e) {
			throw new Exception ("잘못된 방법의 체크아웃입니다.");
		}
	}
	
	// 일일 매출 반환
	public int getRevenue() {
		return revenue;
	}

	// 상품 배열 반환
	public ArrayList<Product> getProductList() {
		return productList;
	}
	
	// 유저 배열 반환
	public ArrayList<User> getUserList() {
		return userList;
	}
	
	

}