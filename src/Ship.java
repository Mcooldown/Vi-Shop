import java.util.Vector;

public abstract class Ship {

	private String id;
	private String address;
	private double totalPrice;
	private String status;
	private int timeCurrent;
	private int shipTime;
	
	private Vector<Product> itemList = new Vector<>();
	
	public abstract double calculateFee(double price);
	
	public void deliver(){
		timeCurrent++;
	}
	public boolean isFinished(){
		return timeCurrent>= shipTime;
	}

	public Ship(String id, String address, Vector<Product> itemList, int shipTime) {
		super();
		this.id = id;
		this.address = address;
		this.itemList = (Vector<Product>) itemList.clone();
		this.timeCurrent = 0;
		this.shipTime = shipTime;
		this.status = "On Going";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTimeCurrent() {
		return timeCurrent;
	}

	public void setTimeCurrent(int timeCurrent) {
		this.timeCurrent = timeCurrent;
	}

	public int getShipTime() {
		return shipTime;
	}

	public void setShipTime(int shipTime) {
		this.shipTime = shipTime;
	}

	public Vector<Product> getItemList() {
		return itemList;
	}

	public void setItemList(Vector<Product> itemList) {
		this.itemList = (Vector<Product>) itemList.clone();
	}

	
}
