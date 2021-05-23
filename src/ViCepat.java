import java.util.Vector;

public class ViCepat extends Ship{

	public ViCepat(String id, String address, Vector<Product> itemList, int shipTime) {
		super(id, address, itemList, shipTime);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double calculateFee(double price) {
		return 12000;
	}

}
