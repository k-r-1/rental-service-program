
public class Product implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String productName; // ��ǰ �̸�
	private String productCode; // ��ǰ �ڵ�
	private int productNumber; // ��ǰ ����
	private int price; // ��ǰ ����
	
	// �μ� �ִ� ������ 
	Product(String productName, String productCode, int productNumber, int price)
	{
		this.productName = productName;
		this.productCode = productCode;
		this.productNumber = productNumber;
		this.price = price;
	}
	
	// �μ� �ִ� ������ (��ǰ �ڵ常 ����)
	Product(String productCode)
	{
		this.productCode = productCode;
	}
	
	// �μ� ���� �� ������ 
	Product() {}
	
	// equals �Լ� ������
	@Override
	public boolean equals(Object o) {
		// o�� Product ��ü�� �ƴϸ�
		if (!(o instanceof Product))
			// false ��ȯ
			return false;
		Product p = (Product) o;
		// ��ǰ �ڵ尡 ������ true, �ٸ��� false ��ȯ
		return p.getCode().equals(this.getCode());
	}
	
	// ��ǰ �̸� ��ȯ
	public String getName()
	{
		return productName;
	}
	
	public void setName(String productName) {
		this.productName = productName;
	}
	
	// ��ǰ �ڵ� ��ȯ
	public String getCode()
	{
		return productCode;
	}
	
	public void setCode(String productCode) {
		this.productCode = productCode;
	}
	
	// ��ǰ ���� ��ȯ
	public int getNumber()
	{
		return productNumber;
	}
	
	public void setNumber(int pNumber) {
		this.productNumber = pNumber;
	}
	
	// ��ǰ ���� �߰�
	public void addNumber()
	{
		productNumber++;
	}
	
	// ��ǰ �뿩 �������� Ȯ�� �� ��ǰ ���� 1�� ����
	public void subNumber() throws Exception{
		if(productNumber < 1) // ��� ������ 1���� ���� ��� 
			throw new Exception("�߸��� ����� üũ���Դϴ�."); // �ͼ��� �߻�
		else // ��� ���� ���ڰ� 1�̻��� ���
			productNumber--; // ��� �� 1�� ����
	}
	
	// ��ǰ ���� ��ȯ
	public int getPrice()
	{
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	// ��� �˻� �Լ� (��� ������ true, �ƴϸ� false ��ȯ)
	public boolean isEmpty()
	{
		if(productNumber > 0)
			return true;
		else
			return false;
	}
	
	// ���Ͽ� ��ǰ ���� ����ϱ�
		/*public void saveProduct(DataOutputStream dos) throws Exception
		{			
			try {
				// ��ǰ ������ ���
				dos.writeUTF(productCode);
				dos.writeUTF(productName);
				dos.writeInt(productNumber);
				dos.writeInt(price);
			} catch (Exception e) {
				throw new Exception("���Ͽ� �� �� �����ϴ�.");
			}
		}*/
		
		// ���Ͽ��� ��ǰ �о����
		/*public Product readProduct(DataInputStream dis) throws Exception
		{
			try {
				// ��ǰ �����͸� ���Ͽ� ����ߴ� ������� �о����
		        productCode = dis.readUTF();
		        productName = dis.readUTF();
		        productNumber = dis.readInt();
		        price = dis.readInt();
			} catch (Exception e) {
				throw new Exception("������ ���� �� �����ϴ�.");
			}
			
	        // ��ǰ ��ü�� �����Ͽ� ��ȯ
	        return this;
		}*/
}