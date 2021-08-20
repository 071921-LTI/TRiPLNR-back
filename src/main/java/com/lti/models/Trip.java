package com.lti.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "trips")
public class Trip {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable = false, name = "trip_id")
	private int tripId;
	@OneToOne @JoinColumn(name = "destination")
	private Address destination;
	@OneToOne @JoinColumn(nullable = false, name = "origin")
	private Address origin;
	@Column(nullable = false, name = "trip_name")
	private String tripName;
	@ManyToOne @JoinColumn(nullable = false, name = "manager")
	private User manager;
	@OneToMany @JoinColumn(name = "stops")
	private List<Address> stops;
	@OneToMany @Column(nullable = false, name = "passengers")
	private List<User> passengers;
	public int getTripId() {
		return tripId;
	}
	public void setTripId(int tripId) {
		this.tripId = tripId;
	}
	public Address getDestination() {
		return destination;
	}
	public void setDestination(Address destination) {
		this.destination = destination;
	}
	public Address getOrigin() {
		return origin;
	}
	public void setOrigin(Address origin) {
		this.origin = origin;
	}
	public String getTripName() {
		return tripName;
	}
	public void setTripName(String tripName) {
		this.tripName = tripName;
	}
	public User getManager() {
		return manager;
	}
	public void setManager(User manager) {
		this.manager = manager;
	}
	public List<Address> getStops() {
		return stops;
	}
	public void setStops(List<Address> stops) {
		this.stops = stops;
	}
	public List<User> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<User> passengers) {
		this.passengers = passengers;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + ((manager == null) ? 0 : manager.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		result = prime * result + ((passengers == null) ? 0 : passengers.hashCode());
		result = prime * result + ((stops == null) ? 0 : stops.hashCode());
		result = prime * result + tripId;
		result = prime * result + ((tripName == null) ? 0 : tripName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trip other = (Trip) obj;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (manager == null) {
			if (other.manager != null)
				return false;
		} else if (!manager.equals(other.manager))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		if (passengers == null) {
			if (other.passengers != null)
				return false;
		} else if (!passengers.equals(other.passengers))
			return false;
		if (stops == null) {
			if (other.stops != null)
				return false;
		} else if (!stops.equals(other.stops))
			return false;
		if (tripId != other.tripId)
			return false;
		if (tripName == null) {
			if (other.tripName != null)
				return false;
		} else if (!tripName.equals(other.tripName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Trip [tripId=" + tripId + ", " + (destination != null ? "destination=" + destination + ", " : "")
				+ (origin != null ? "origin=" + origin + ", " : "")
				+ (tripName != null ? "tripName=" + tripName + ", " : "")
				+ (manager != null ? "manager=" + manager + ", " : "") + (stops != null ? "stops=" + stops + ", " : "")
				+ (passengers != null ? "passengers=" + passengers : "") + "]";
	}
	public Trip(int tripId, Address destination, Address origin, String tripName, User manager, List<Address> stops,
			List<User> passengers) {
		super();
		this.tripId = tripId;
		this.destination = destination;
		this.origin = origin;
		this.tripName = tripName;
		this.manager = manager;
		this.stops = stops;
		this.passengers = passengers;
	}
	public Trip(Address destination, Address origin, String tripName, User manager, List<User> passengers) {
		super();
		this.destination = destination;
		this.origin = origin;
		this.tripName = tripName;
		this.manager = manager;
		this.passengers = passengers;
	}
	public Trip(Address destination, Address origin, String tripName, User manager, List<Address> stops,
			List<User> passengers) {
		super();
		this.destination = destination;
		this.origin = origin;
		this.tripName = tripName;
		this.manager = manager;
		this.stops = stops;
		this.passengers = passengers;
	}
	public Trip(int tripId) {
		super();
		this.tripId = tripId;
	}
	
	
	
	
	
}
