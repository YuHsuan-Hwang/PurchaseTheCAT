import java.util.ArrayList;
/**
 * 一個種類的商品，及商品類表
 * @author yuhsuan
 *
 */
public class ProductClass {
	
	private String item_class;
	private ArrayList<Product> product_list;
	
	public ProductClass(String item_class,Product pro) {
		this.item_class = item_class;
		product_list = new ArrayList<Product>();
		product_list.add(pro);
	}
	
	public String ItemClass() {
		return this.item_class;
	}
	
	public int ShowProductListSize() {
		return this.product_list.size();
	}
	
	public Product GetProductListElement(int i) {
		return this.product_list.get(i);
	}
	
	public void Add(Product pro) {
		int flag = 0;
		for (int i=0; i<product_list.size();i++) {
			if (pro.Unit_Price()<=product_list.get(i).Unit_Price()){
				product_list.add(i,pro);
				flag = 1;
				break;
			}
		}
		if (flag==0) {
			product_list.add(pro);
		}
	}
	
	public void Remove(int i) {
		product_list.remove(i);
	}

	public String PrintProductListElement(int i) {
		Product pro = this.product_list.get(i);
		String str = pro.Item()+","+pro.Price()+","+pro.Number()+","+pro.Unit()+","+pro.Note();
		return str;
	}
	

}
