/**
 * 商品類別
 * @author yuhsuan
 *
 */
public class Product {

	private String item;
	private int price;
	private int number;
	private String unit;
	private String note;
	
	private double unit_price; 
	
	
	
	public Product() {
		
		this.item = "";
		this.price = 0;
		this.number = 0;
		this.unit = "";
		this.note = "";
		
		this.unit_price = 0;
		
	}
	
	/**
	 * 自行輸入的建構子
	 * @param item 名稱
	 * @param price 價錢
	 * @param number 數量
	 * @param unit 單位
	 * @param note 備註
	 * @param unit_price 單位價錢
	 */
	public Product(String item,int price,int number,String unit,String note) {
		
		this.item = item;
		this.price = price;
		this.number = number;
		this.unit = unit;
		this.note = note;
		
		this.unit_price = (double)price/(double)number;
		
	}
	
	public String Item() {
		return item;
	}
	
	public int Price() {
		return price;
	}
	
	public int Number() {
		return number;
	}
	
	public String Unit() {
		return unit;
	}
	
	public String Note() {
		return note;
	}
	
	public double Unit_Price() {
		return unit_price;
	}
	
}
