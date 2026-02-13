package com.alpha.EatExpress.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private double price;
    private int unit;
    private String type;
    private double rating;
    private byte image;
    private boolean available;
    private int serves;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public byte getImage() {
		return image;
	}

	public void setImage(byte image) {
		this.image = image;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getServes() {
		return serves;
	}

	public void setServes(int serves) {
		this.serves = serves;
	}

	

	

	public Item(int id, String name, double price, int unit, String type, double rating, byte image, boolean available,
			int serves) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.unit = unit;
		this.type = type;
		this.rating = rating;
		this.image = image;
		this.available = available;
		this.serves = serves;
	
	}

	public Item() {
		super();
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", price=" + price + ", unit=" + unit + ", type=" + type
				+ ", rating=" + rating + ", image=" + image + ", available=" + available + ", serves=" + serves + "]";
	}
	
}
   

