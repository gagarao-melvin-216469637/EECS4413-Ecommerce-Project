package demo;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
public class Item {

	/**
	 * @param bid
	 * @param name
	 * @param description
	 * @param type
	 * @param brand
	 * @param quantity
	 * @param price
	 */
	
	private @Id @GeneratedValue Long id;
	private String bid;
	private String name;
	private String description;
	private String type;
	private String brand;
	private int quantity;
	private int price;

	public Item() {
	}

	public Item(String bid, String name, String description, String type, String brand, int quantity, int price) {

		this.bid = bid;
		this.name = name;
		this.description = description;
		this.type = type;
		this.brand = brand;
		this.quantity = quantity;
		this.price = price;
	}

	/**
	 * @return the bid
	 */
	public String getBid() {
		return bid;
	}
	
	 public Long getId() {
		    return this.id;
		  }
	 
	 public void setId(Long id) {
		 this.id = id;
	 }

	/**
	 * @param bid the bid to set
	 */
	public void setBid(String bid) {
		this.bid = bid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}



	@Override
	public int hashCode() {
		return Objects.hash(bid, brand, description, id, name, price, quantity, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(bid, other.bid) && Objects.equals(brand, other.brand)
				&& Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && price == other.price && quantity == other.quantity
				&& Objects.equals(type, other.type);
	}
	
	@Override
	public String toString() {

		return "Item{\"bid\"=\""+this.bid+"\",\"brand\"=\""+this.brand+"\",\"description\"=\""+this.description+"\","
				+ "\"name\"=\""+this.name+"\",\"price\"="+this.price+","
						+ "\"quantity\"="+this.quantity+",\"type\"=\""+this.type+"\"}";
		
		
		
	  }
	
	
	
}
	
