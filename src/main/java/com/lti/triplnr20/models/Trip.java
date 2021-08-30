package com.lti.triplnr20.models;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "trips")
public class Trip {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable = false)
	private int tripId;
	@Column(name = "destination")
	private String destination;
	@Column(nullable = false)
	private String origin;
	@Column(nullable = false)
	private String tripName;
	@ManyToOne @JoinColumn(nullable = false, name = "manager")
	private User manager;
	@ElementCollection @Column
	private List<String> stops;
	@OneToMany @JoinColumn
	private List<User> passengers;
	@Column(nullable = false)
	private Timestamp startTime;
	@Column
	private Timestamp endTime;
	
}