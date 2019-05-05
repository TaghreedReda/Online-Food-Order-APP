import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; 

interface  product
{
	
public void add(Customer c);
public void setInfo(String name,int id,int p );
public int getID();
public void setQuantity(int q);
public void getInfo ();

}

class Product_A implements product 
{
	private List<Customer> customers = new ArrayList<Customer>();
	public int quantity;
    public String P_name; 
    public int P_id,price;
    
    @Override
	public void add(Customer c) {
		customers.add(c);
	}
    @Override
    public void setQuantity(int q) {
    	this.quantity=q;
    	if(q >0 ) {
    		for(Customer c :customers){
    			c.notifyMe(P_name);			
    		}
    		}
    }
    @Override
	public void setInfo(String name,int id,int p) {		
		this. P_name=name;
		this. P_id=id;
		this.price=p;
	}
    @Override
    public int getID() {
    	return (this.P_id);
    }
    @Override
	public void getInfo () {
		System.out.println("Product ID : "+this. P_id);
		System.out.println("Product Name : "+this. P_name);
		System.out.println("Product Price : "+this.price);
		if (this.quantity >0) {
			System.out.println("Product is Available Now");
		}
		else
			System.out.println("Product isn't Available Now");
	}
}

interface Customer
{
	public void notifyMe(String s );
	public int getID();
	public void setID(int id);
	public void showNotifications();
}

class Customer_A implements Customer 
{
	public int id;
	private List<String> notifications = new ArrayList<String>();
	
	@Override
	public void notifyMe(String s ) {
	      notifications.add("Item : "+s+" Is available now");	
	}

	@Override
	public void setID(int id) {
		this.id=id;
	}
	@Override
	public int getID() {return id;}
	@Override
	public void showNotifications() {
		if (notifications.isEmpty()) {
			System.out.println("no notifications");
		}
		else 
		for (String s : notifications) {
			System.out.println(s);
		}
	}
}

interface Vendor
{
	
	public void ListProducts();
	public void SetProductQuantity(int id,int q); // set quantity by id
	public void AddProduct (String name,int id,int p ,int q);
	public void NotifyCustomer(int id , Customer c);
}
class Vendor_A implements Vendor 
{
	public List<product> VendorProducts = new ArrayList<product>();
	@Override
	public void AddProduct (String name,int id,int price , int quantity ) 
	{
		product p = new Product_A();
		p.setQuantity(quantity ); 
		p.setInfo(name, id, price);
		VendorProducts.add(p);
	}
	@Override
	public void ListProducts() 
	{
		for (product pro :VendorProducts) {
			pro.getInfo();
		}
	}
	@Override
	public void SetProductQuantity(int id ,int q)
	{
		for (product pro :VendorProducts) {
			if (pro.getID()==id) {
				pro.setQuantity(q); 
			}
		}
	}	
	@Override
	public void NotifyCustomer(int id,Customer c) 
	{
		for (product pro :VendorProducts) {
			if (pro.getID()==id) {
				pro.add(c);
			}
	}
}
}
interface Portal
{
	public void ShowProducts(Vendor v);
}

class VendorAdapter implements Portal 
{
	Vendor adaptee_v=new Vendor_A();
	public void ShowProducts(Vendor v) {
		this.adaptee_v = v;
		adaptee_v.ListProducts();
	}
}

public class online_portal
{
 static List<Customer> customers = new ArrayList<Customer>();
 static Vendor  vendor=new Vendor_A();
	 public static void main(String[] args)
	 {		 
		 vendor .AddProduct("Shakoush", 0, 10, 0);
		 vendor .AddProduct("Notebook", 1, 11, 2);
		 System.out.println("Enter 0 for new Customer and ID for Current Customers");
		 Scanner sc = new Scanner(System.in); 
		 int c_id= sc.nextInt();
		 int count=1;
		 if(c_id==0) 
		 {
			 Customer c=new Customer_A();		 
			 c.setID(count);
			 customers.add(c);
			 System.out.println("Welcome , Your ID is "+count);
			 count++;
		 }
		 Portal p= new VendorAdapter();
		 p.ShowProducts(vendor);
		 
		 System.out.println("If you want to be notified when a specific item is available Press y then Enter It's ID");
		
		 System.out.println("If you aren't interested in notifications Press n");
		 
		 char u = sc.next().charAt(0);
		
		 if(u=='y')
		 {
			 int id=sc.nextInt();
			 System.out.println("Enter your ID");
			 int b=sc.nextInt();
			 for (Customer c:customers) {
				 if (c.getID()==b) {
					 vendor.NotifyCustomer(id,c);
				 }
			 }	 
		 }
		 vendor.SetProductQuantity(0, 5);
		 
		System.out.println("Enter your ID to check Your Notifications");
			 int ans= sc.nextInt();	
		   
			 for(Customer customer : customers) 
			 {
				 if (customer.getID()==ans) {
					 customer.showNotifications();
				 }
			 }			 
				 
			 }
	 }

