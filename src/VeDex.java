import java.util.Vector;

public class VeDex extends Ship{

	public VeDex(String id, String address, Vector<Product> itemList, int shipTime) {
		super(id, address, itemList, shipTime);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double calculateFee(double price) {
		
		if(price >=150000) return 0;
		else return 10000;
	}

	

}
