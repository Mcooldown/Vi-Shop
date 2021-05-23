import java.util.Random;
import java.util.Scanner;
import java.util.UUID;
import java.util.Vector;

public class Main implements Runnable {

	Scanner scan = new Scanner(System.in);
	Vector<Product> productList = new Vector<Product>();
	Vector<Ship> shipList = new Vector<Ship>();
	Vector<Ship> historyShip = new Vector<Ship>();
	Vector<Product> itemCart = new Vector<>();
	Random rand = new Random();
	int menu =-1;
	
	void cls(){
		for(int i=0; i<30;i++){
			System.out.println();
		}
	}

	private void buyItem(){
		
		String id= null;
		String confirm= null;
		String service = null;
		int pick = 0;
		int qty=0;
		boolean check = false;
		String address = null;
		double price = 0;
		double fee =0;
		double totalPrice;
		
		
		do{
			cls();
			for (int i = 0; i < productList.size(); i++) {
				System.out.println("No	: "+ (i+1));
				System.out.println("Name	: " + productList.get(i).getName());
				System.out.println("Price	: " + productList.get(i).getPrice());
				System.out.println("Stocks	: " + productList.get(i).getStock());
				System.out.println("");
			}

			do{
				System.out.print("Pick product to buy [1.."+ productList.size() + "]:");

				try {
					pick = scan.nextInt();
				} catch (Exception e) {
					pick = 0;
				}

			}while(pick< 1 || pick > productList.size());
			
			do{
				check= false;
				System.out.print("Input quantity [1.."+ productList.get(pick-1).getStock()+ "]:");
				
				try {
					qty = scan.nextInt();
				} catch (Exception e) {
					qty=0;
				}
				scan.nextLine();
				
				if(qty<1 || qty > productList.get(pick-1).getStock()){
					System.out.print("Insufficient product stock. Maximum purchase of this product is only " + productList.get(pick-1).getStock());
					scan.nextLine();
					check=true;
				}
			}while(check ==true);
			
			itemCart.add(new Product(productList.get(pick-1).getName(),productList.get(pick-1).getPrice(),qty));
			
			price+=(productList.get(pick-1).getPrice()*qty);
			
			do{
				System.out.print("Would you like to add more product into your cart? [Y/N]");
				confirm = scan.nextLine();
				confirm = confirm.trim();
				
			}while(!confirm.equals("Y") && !confirm.equals("N"));

			productList.get(pick-1).setStock(productList.get(pick-1).getStock()-qty);
			if(productList.get(pick-1).getStock()<=0)productList.remove(pick-1);
			
		}while(confirm.equals("Y"));
		
		do{			
			System.out.print("Input shipping address [must begin with 'Jl. '| Longer than 10 characters]:");
			address = scan.nextLine();
			address = address.trim();
		}while(!address.startsWith("Jl. ") || address.length()<=10);
		
		do{
			System.out.print("Input shipping service [ViCepat | VeDex]: ");
			service = scan.nextLine();
			service = service.trim();
			
		}while(!service.equalsIgnoreCase("ViCepat") && !service.equalsIgnoreCase("VeDex"));
		
		Ship temp;
		
		id= UUID.randomUUID().toString();
		if(service.equalsIgnoreCase("ViCepat")){
			temp = new ViCepat(id,address,itemCart,10);
		}else{
			temp = new VeDex(id,address,itemCart,20);
		}
		
		fee= temp.calculateFee(price);
		
		totalPrice = price+fee;
		System.out.println("");
		System.out.println("Product's Prices : Rp."+price);
		System.out.println("Shipping Fee: Rp."+fee);
		System.out.println("Grand Total: Rp." +totalPrice);
		System.out.println("");
		System.out.print("Press enter to continue...");
		scan.nextLine();
		
		temp.setTotalPrice(totalPrice);
		shipList.add(temp);
		itemCart.clear();
		
	}
	
	private void viewPurchaseHistory(){
		cls();
		System.out.println("Finished Orders");
		System.out.println("===================");
		
		for (int i = 0; i < historyShip.size(); i++) {
			System.out.println("");
			System.out.println("Shipping ID: " + historyShip.get(i).getId());
			System.out.println("Address: " +historyShip.get(i).getAddress());
			System.out.println("Total price: "+historyShip.get(i).getTotalPrice());
			
			System.out.println("Item(s):");
			for (int j = 0; j < historyShip.get(i).getItemList().size(); j++) {
				System.out.println("- " +historyShip.get(i).getItemList().get(j).getStock() + "pcs " +historyShip.get(i).getItemList().get(j).getName() );
			}
		}
	}
	
	private void viewShippingStatus(){
		for (int i = 0; i < shipList.size(); i++) {
			if(!shipList.get(i).getStatus().equals("Finished")){
				
				if(shipList.get(i) instanceof ViCepat){
					System.out.println("ViCepat");
				}else System.out.println("VeDex");
				System.out.println("---------------");
				System.out.println("Shipping ID: " +shipList.get(i).getId());
				System.out.println("Shipping Status: "+shipList.get(i).getStatus());
				if(shipList.get(i).getStatus().equals("Failed")){
					System.out.println("Sorry for the inconvenience, your packet can't be traced");
					System.out.println("Insurance fee will be sent directly to the according address");
				}
				System.out.println("");
			}
		}
	}
	
	private void mainMenu(String name){

		do{
			cls();
			if(!shipList.isEmpty()){
				viewShippingStatus();
			}else{
				System.out.println("No Purchase history");
				System.out.println("");
			}
			System.out.println("Hello, "+ name + "!");
			System.out.println("1.Shop at Vi");
			System.out.println("2.Refresh My Shipping Status");
			System.out.println("3.View Purchase History");
			System.out.println("4.Exit");

			do{
				System.out.print("Choose >>");
				try {
					menu = scan.nextInt();
				} catch (Exception e) {
					menu =-1;
				}
				scan.nextLine();
			}while(menu <1 || menu > 4);

			switch (menu) {
			case 1:
				if(productList.isEmpty()==false){
					buyItem();					
				}else{
					System.out.println("Sorry, all of our products are currently out of stock!");
					scan.nextLine();
				}
				break;
				
			case 3:
				if(historyShip.isEmpty()==false){
					viewPurchaseHistory();					
				}else{
					System.out.println("No History");
				}
				System.out.print("Press enter to continue...");
				scan.nextLine();
				break;
			}

		}while(menu != 4);

	}

	public Main() {

		String name = null;
		boolean check= false;
		Thread t = new Thread(this);
		t.start();

		productList.add(new Product("The Aubree Niacinamide Serum",99000, rand.nextInt(31)+20));
		productList.add(new Product("Tiff Body Cacao Coffee Scrub",150000, rand.nextInt(31)+20));
		productList.add(new Product("Kleveru Glass Skin Serum",143000, rand.nextInt(31)+20));
		productList.add(new Product("Sensatia Botanicals Unscented Facial-C Serum",180000, rand.nextInt(31)+20));
		productList.add(new Product("Mineral Botanicals Vanila Lip Scrub",55000, rand.nextInt(31)+20));
		productList.add(new Product("Think Hale Let's Mask",129000, rand.nextInt(31)+20));
		productList.add(new Product("Faith In Face Cica Jelly Mask",29000, rand.nextInt(31)+20));
		productList.add(new Product("Lacoco Swallow Facial Foam",165000, rand.nextInt(31)+20));
		productList.add(new Product("Everwhite Brightening Essence Serum",125000, rand.nextInt(31)+20));

		System.out.println("Welcome to Vi Shop! ^-^");
		System.out.println("");
		System.out.println("To create an order, please input your name");

		do{
			check = false;
			System.out.print("Name [Min. 3 characters | Must contain two words]: ");
			name = scan.nextLine();
			name = name.trim();

			String [] spaceCount = name.split(" ");
			if(spaceCount.length !=2){
				check= true;
				System.out.print("Must be two words");
				scan.nextLine();
			}
			else if(name.length()<3){
				System.out.print("Min. 3 characters");
				scan.nextLine();
				check=true;
			}

		}while(check == true);

		mainMenu(name);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (int i = 0; i < shipList.size(); i++) {
				shipList.get(i).deliver();
				if(shipList.get(i).isFinished()){
					
					if(shipList.get(i) instanceof VeDex){
						int random = rand.nextInt(10);
						if(random==0){
							shipList.get(i).setStatus("Failed");
						}
						else{
							historyShip.add(shipList.get(i));
							shipList.remove(i);
						}
					}
					else{
						historyShip.add(shipList.get(i));
						shipList.remove(i);
					}
					
					i--;
				}
			}
			
			if(menu==4)break;
		}
	}

}
