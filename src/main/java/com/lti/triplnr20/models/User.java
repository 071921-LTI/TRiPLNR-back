package com.lti.triplnr20.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User {
	//This is the model setup for friend requests
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable = false)
	private int userId;
	@Column(unique = true, nullable = false)
	private String sub;
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	@Column
	private String profilePic;
	@Column
	private String bio;
	@Column(nullable = false)
	private String address;
	@ManyToMany @Column
	@JsonIgnoreProperties({"manager", "passengers"})
	private List<Trip> trips;
	@ManyToMany @Column
	@JsonIgnoreProperties({"trips", "friends"})
	private List<User> friends;
	
	
}
