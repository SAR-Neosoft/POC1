package com.neosoft.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonFormat;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(max=65)
	private String name;
	
	@NotNull
	@Size(max=65)
	@Column(unique = true)
	private String surname;
	
	 
	@Temporal(TemporalType.DATE)
//	@JsonFormat(pattern="yyyy-MM-dd") 
	@Column(name = "dob")
	private Date dateOfBirth;
	
	@NotNull
	@Size(max=120)
	@Column(unique = true)
	private String email;
	
	@NotNull
	@Size(max=15)
	private String password;
	
	@Temporal(TemporalType.DATE) 
	@Column(name = "doj")
	private Date dateOfJoining;
	
	@NotNull
	@Size(max=100)
	private String country;
	
	@NotNull
	@Size(max=10)
	private String pincode;
	

    @Column(columnDefinition = "integer default 0")
	private int deleteStatus;


    
//	public User(@NotNull @Size(max = 65) String name, Date dateOfJoining, Date dateOfBirth,
//			@NotNull @Size(max = 120) String email, @NotNull @Size(max = 15) String password, @NotNull @Size(max = 65) String surname,
//			@NotNull @Size(max = 100) String country, @NotNull @Size(max = 10) String pincode, int deleteStatus) {
//		super();
//		this.name = name;
//		this.surname = surname;
//		this.dateOfBirth = dateOfBirth;
//		this.email = email;
//		this.password = password;
//		this.dateOfJoining = dateOfJoining;
//		this.country = country;
//		this.pincode = pincode;
//		this.deleteStatus = deleteStatus;
//	}
    
    
    
    

}


