import java.io.*;
import java.util.ArrayList;

public class Manager {
	private ArrayList<Product> productList; // ��ǰ �迭
	private ArrayList<User> userList; // �뿩 �迭
	private int revenue = 0; // ���� �Ѿ� ����
	
	// ������ 1. ������ �ƿ� ���� ���¸� ���� ������
	Manager (int maxProductCount, int maxUserCount) {
		productList = new ArrayList<Product>(maxProductCount); // ��ǰ �迭 ũ�� ����
		userList = new ArrayList<User>(maxUserCount); // �뿩 �迭 ũ�� ����
	}
	
	// ������ 2. ���� ������ �ִ� ���¿��� �����͸� �ε��ϱ� ���� ������
	Manager (int maxProductCount, int maxUserCount, ObjectInputStream dis) throws Exception 
	{ 
		productList = new ArrayList<Product>(maxProductCount); // ��ǰ �迭 ũ�� ����
		userList = new ArrayList<User>(maxUserCount); // �뿩 �迭 ũ�� ����
		
		// ������ �ε�
		try {
			Integer pCount = (Integer) dis.readObject(); // ����� ��ǰ ������ŭ �б�
			for (int i = 0; i < pCount.intValue(); i++) 
			{
				// ��ǰ ��ü�� �޾Ƽ� �迭�� �ֱ�
				productList.add((Product) dis.readObject());
			}
			Integer uCount = (Integer) dis.readObject(); // ����� ���� ����ŭ �б�
			for (int i = 0; i < uCount.intValue(); i++) 
			{
				// ���� ��ü�� �޾Ƽ� �迭�� �ֱ�
				userList.add((User) dis.readObject()); 
			}
			// ����� ����� �б�
			Integer r = (Integer) dis.readObject();
			revenue = r.intValue();
		}
		catch(FileNotFoundException fnfe){
			System.out.println("������ �������� �ʽ��ϴ�.");
		}
		catch(EOFException eofe){
		}
		catch(IOException ioe){
			System.out.println("������ ���� �� �����ϴ�.");
		}
		catch(ClassNotFoundException cnfe){
			System.out.println("�ش� Ŭ������ �������� �ʽ��ϴ�.");
		}
	}

	// ���� ����
	void saveObject(ObjectOutputStream oos) throws IOException {
		try {
			// ��ǰ �迭, ���� �迭�� ��ǰ ��, ���� ����ŭ ���Ͽ� ���
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
			// ���� ����
			oos.writeObject(Integer.valueOf(revenue));
		} catch (IOException e) {
			throw new IOException ("�߸��� ���� �����Դϴ�.");
		} 		
	}
	
	// �ڵ� �ߺ� �˻�(�������̵� �� equals ���)
	public void checkCode(Product p) throws Exception {
		// ��ǰ �ڵ尡 �̹� ������
		if (productList.indexOf(p) >= 0)
			// ���� �߻�
			throw new Exception("�߸��� ��ǰ ����Դϴ�.");
	}
	
	// ��ǰ �߰�
	public void add(Product p) throws Exception {
		try{
			checkCode(p); // �ڵ� �ߺ� �˻�
			productList.add(p); // ��ǰ �߰�
		}
		catch (Exception e) {
			throw new Exception ("�߸��� ��ǰ ����Դϴ�.");
		}
	}
	
	// ��ǰ ����
	public void delete(String productCode) throws Exception {
		try{
			int number = search(productCode); // ��ǰ �迭���� �˻��ϱ�
			productList.remove(number); // ��ǰ �迭���� �����ϱ�
		}
		catch (Exception e) {
			throw new Exception ("�������� �ʴ� ��ǰ�Դϴ�.");
		}
	}

	// ��ǰ ��ü �˻�(�������̵� �� equals ���)
	public int search(String productCode) throws Exception {	
		// �μ��� ���� ��ǰ �ڵ�� ���� �ڵ带 ���� ��ǰ�� �ִ��� ã�� 
		int index = productList.indexOf(new Product(productCode));
		// ��ǰ�� ������
		if (index >= 0)
			// �ε��� ��ȯ
			return index;
		// ��ǰ�� ������
		else
			// �ͼ��� �߻�
			throw new Exception("��ġ�ϴ� �ڵ带 ã�� �� �����ϴ�.");
	}
	
	
	// ��ǰ �迭 i��° ����
	public Product productAt(int i) {
		try {
			return productList.get(i); // ��ǰ �迭 i��° ��ǰ ��ü return
		} catch (IndexOutOfBoundsException iobe) {
			return null;
		}
	}
	
	// productCount ����� ��ȯ
	public int getProductCount() {
		return productList.size();
	}
	
	// ��� ����
	public void addStock(int index, int n) {
		Product p = productList.get(index); // �ش� �ε����� product ��ü
		p.setNumber(p.getNumber() + n); // ��ǰ ����Ʈ�� index ��° ��ǰ ��ü�� ��� n�� ����

	}
	
	// ��� ����
	public void subStock(int index, int n) throws Exception {
		
		Product p = productList.get(index); // �ش� �ε����� product ��ü
		if (p.getNumber() < n ) {
			throw new Exception("�߸��� ����� üũ���Դϴ�."); // n�� ������ ���� exception �߻�
		}
		else
			p.setNumber(p.getNumber() - n); // ��ǰ ����Ʈ�� index ��° ��ǰ ��ü�� ��� n�� �̻��� ��� ��� n�� ����
	}
	
	// ��ȭ��ȣ �ߺ� �˻�(�������̵� �� equals ���)
	public void checkPhone(User u) throws Exception {
		// ��ȭ��ȣ�� �̹� ������
		if (userList.indexOf(u) >= 0)
			// ���� �߻�
			throw new Exception("�߸��� ����� üũ���Դϴ�.");
	}

	// ��� �������� �뿩 ���� ����
	public void subStock(User u) throws Exception {
		// ��� �������� �뿩 ���� �����ϱ�
		for(int i = 0; i < u.getRentalCount(); i++)
		{
			String code = u.codeAt(i); // �ش� User ��ü�� i��° �뿩 ��ǰ �ڵ�
			int searchNum;
			try {
				searchNum = search(code); // productList���� �ش� �ڵ��� �ε��� ��ȣ �˻�
			} 
			catch (Exception e) {
				throw new Exception("�߸��� ����� üũ���Դϴ�.");
			}
			Product p = productList.get(searchNum); //�ش� �ε����� product ��ü
			p.subNumber(); // �뿩�� �������� Ȯ�� �� ������ (��� 1�� ����)
		}
	}
	
	// �뿩 �迭�� ���� �߰�
	public void addUser(User u) {
		userList.add(u);
	}

	// üũ��
	public void checkIn(User u) throws Exception {
		try {
			checkPhone(u); // ��ȭ��ȣ �ߺ� �˻�
			subStock(u); // ��� �������� �뿩 ���� ����
			addUser(u); // �뿩 �迭�� �뿩 ���� �ֱ�
		}
		catch(Exception e) {
			throw new Exception("�߸��� ����� üũ���Դϴ�.");
		}
	}
	
	// userCount ����� ��ȯ
	public int getUserCount() {
		return userList.size();
	}

	
	// �뿩 �迭 i��° ����
	public User userAt(int i) 
	{
		try {
			return userList.get(i); // �뿩 �迭 i��° ���� ��ü return
		} catch (IndexOutOfBoundsException iobe) {
			return null;
		}
	}
	
	// ��ġ�ϴ� ȸ����ȣ �˻�(�������̵� �� equals ���)
	public int searchUser(String phone) throws Exception {
		// �μ��� ���� ��ȭ��ȣ�� ���� ��ȭ��ȣ�� ���� ��ǰ�� �ִ��� ã�� 
		int index = userList.indexOf(new User(phone));
		// ��ǰ�� ������
		if (index >= 0)
			// �ε��� ��ȯ
			return index;
		// ��ǰ�� ������
		else
			// �ͼ��� �߻�
			throw new Exception("��ġ�ϴ� �ڵ带 ã�� �� �����ϴ�.");
	}

	// ��ǰ ��� �ٽ� �߰�
	public void addStock(int index) throws Exception {
		User u = userAt(index);
		for(int i = 0; i < u.getRentalCount(); i++) {
			String code = u.codeAt(i); // �ش� User ��ü�� i��° �뿩 ��ǰ �ڵ�
			try {
				int number = search(code); // productList���� �ش� �ڵ��� �ε��� ��ȣ �˻�
				productAt(number).addNumber(); //�ش� �ε����� product ��ü�� ��� �߰��ϱ�
			}
			catch (Exception e) {
				throw new Exception ("�߸��� ����� üũ�ƿ��Դϴ�.");
			}
		}
	}
	
	// �뿩 �迭�� ���� ����
	public void subUser(int number) {
		userList.remove(number);
	}
	
	// üũ�ƿ�
	public void checkOut(int index) throws Exception{
		try{
			addStock(index);// ��ǰ ��� �ٽ� �߰��ϱ�
			int money = userList.get(index).pay();// �ݾ� ��ȯ�ޱ�
			subUser(index); // userList���� ����, �迭 �����ϱ�
			revenue += money; // ���⿡ �߰��ϱ�
		}
		catch(Exception e) {
			throw new Exception ("�߸��� ����� üũ�ƿ��Դϴ�.");
		}
	}
	
	// ���� ���� ��ȯ
	public int getRevenue() {
		return revenue;
	}

	// ��ǰ �迭 ��ȯ
	public ArrayList<Product> getProductList() {
		return productList;
	}
	
	// ���� �迭 ��ȯ
	public ArrayList<User> getUserList() {
		return userList;
	}
	
	

}