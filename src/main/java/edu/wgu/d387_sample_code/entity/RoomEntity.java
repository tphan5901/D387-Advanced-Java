package edu.wgu.d387_sample_code.entity;

import com.sun.istack.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name = "Room")
public class RoomEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private Integer roomNumber;

	@NotNull
	private String priceUSD;

	@NotNull
	private String price;

	@Transient // Avoid creating field in the database
	private double priceCAD;

	@Transient
	private double priceEUR;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<ReservationEntity> reservationEntityList;


	// Constructor
	public RoomEntity(Integer roomNumber, String priceUSD) {
		this.roomNumber = roomNumber;
		this.priceUSD = priceUSD;
		this.priceCAD = CurrencyConverter.convert("CAD", Double.parseDouble(priceUSD));
		this.priceEUR = CurrencyConverter.convert("EUR", Double.parseDouble(priceUSD));
	}

	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPriceUSD() {
		return priceUSD;
	}

	public void setPriceUSD(String priceUSD) {
		this.priceUSD = priceUSD;
		this.priceCAD = CurrencyConverter.convert("CAD", Double.parseDouble(priceUSD));
		this.priceEUR = CurrencyConverter.convert("EUR", Double.parseDouble(priceUSD));
	}

	public double getPriceCAD() {
		return priceCAD;
	}

	public void setPriceCAD(){
		this.priceCAD = CurrencyConverter.convert("CAD", Double.parseDouble(priceUSD));
    }

	public double getPriceEUR() {
		return priceEUR;
	}

	public List<ReservationEntity> getReservationEntityList() {
		return reservationEntityList;
	}

	public void setReservationEntityList(List<ReservationEntity> reservationEntityList) {
		this.reservationEntityList = reservationEntityList;
	}

	public void addReservationEntity(ReservationEntity reservationEntity) {
		if (null == reservationEntityList)
			reservationEntityList = new ArrayList<>();

		reservationEntityList.add(reservationEntity);
	}
}