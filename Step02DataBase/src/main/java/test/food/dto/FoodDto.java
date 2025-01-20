package test.food.dto;

public class FoodDto {
	private int num;
	private String type;
	private String name;
	private int price;
	
	//디폴트생성자
	public FoodDto() {}

	public FoodDto(int num, String type, String name, int price) {
		super();
		this.num = num;
		this.type = type;
		this.name = name;
		this.price = price;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	
}
