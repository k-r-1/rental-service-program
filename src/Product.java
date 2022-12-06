
public class Product implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String productName; // 상품 이름
	private String productCode; // 상품 코드
	private int productNumber; // 상품 개수
	private int price; // 상품 가격
	
	// 인수 있는 생성자 
	Product(String productName, String productCode, int productNumber, int price)
	{
		this.productName = productName;
		this.productCode = productCode;
		this.productNumber = productNumber;
		this.price = price;
	}
	
	// 인수 있는 생성자 (상품 코드만 대입)
	Product(String productCode)
	{
		this.productCode = productCode;
	}
	
	// 인수 없는 빈 생성자 
	Product() {}
	
	// equals 함수 재정의
	@Override
	public boolean equals(Object o) {
		// o가 Product 객체가 아니면
		if (!(o instanceof Product))
			// false 반환
			return false;
		Product p = (Product) o;
		// 상품 코드가 같으면 true, 다르면 false 반환
		return p.getCode().equals(this.getCode());
	}
	
	// 상품 이름 반환
	public String getName()
	{
		return productName;
	}
	
	public void setName(String productName) {
		this.productName = productName;
	}
	
	// 상품 코드 반환
	public String getCode()
	{
		return productCode;
	}
	
	public void setCode(String productCode) {
		this.productCode = productCode;
	}
	
	// 상품 개수 반환
	public int getNumber()
	{
		return productNumber;
	}
	
	public void setNumber(int pNumber) {
		this.productNumber = pNumber;
	}
	
	// 상품 개수 추가
	public void addNumber()
	{
		productNumber++;
	}
	
	// 상품 대여 가능한지 확인 후 상품 개수 1개 삭제
	public void subNumber() throws Exception{
		if(productNumber < 1) // 재고 개수가 1보다 작을 경우 
			throw new Exception("잘못된 방법의 체크인입니다."); // 익셉션 발생
		else // 재고 물건 숫자가 1이상일 경우
			productNumber--; // 재고 수 1개 감소
	}
	
	// 상품 가격 반환
	public int getPrice()
	{
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	// 재고 검색 함수 (재고가 있으면 true, 아니면 false 반환)
	public boolean isEmpty()
	{
		if(productNumber > 0)
			return true;
		else
			return false;
	}
	
	// 파일에 상품 정보 기록하기
		/*public void saveProduct(DataOutputStream dos) throws Exception
		{			
			try {
				// 상품 데이터 기록
				dos.writeUTF(productCode);
				dos.writeUTF(productName);
				dos.writeInt(productNumber);
				dos.writeInt(price);
			} catch (Exception e) {
				throw new Exception("파일에 쓸 수 없습니다.");
			}
		}*/
		
		// 파일에서 상품 읽어오기
		/*public Product readProduct(DataInputStream dis) throws Exception
		{
			try {
				// 상품 데이터를 파일에 출력했던 순서대로 읽어들임
		        productCode = dis.readUTF();
		        productName = dis.readUTF();
		        productNumber = dis.readInt();
		        price = dis.readInt();
			} catch (Exception e) {
				throw new Exception("파일을 읽을 수 없습니다.");
			}
			
	        // 상품 객체를 생성하여 반환
	        return this;
		}*/
}