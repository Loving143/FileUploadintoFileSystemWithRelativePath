package com.crud.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name="Course")
public class Employee {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
private int id; 

	private String firstName;
	private String lastName;
	private String image;
	private String signature;
	
	



}
