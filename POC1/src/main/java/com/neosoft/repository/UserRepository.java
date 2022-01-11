package com.neosoft.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.neosoft.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
	
	 Boolean existsBySurname(String username);
	    Boolean existsByEmail(String email);
	    
	    List<User> findByNameOrSurnameOrPincode(String name,String surname,String pincode);
	    List<User> findAllByOrderByDateOfJoiningAscDateOfBirthAsc();
	    //List<User> findAllByOrderByDateOfJoiningDesc();
	    
	    @Transactional
		@Modifying
		@Query("UPDATE User u SET u.country=:country, u.dateOfBirth=:dob, u.dateOfJoining=:doj, u.email=:email, u.name=:name, u.password=:password, u.pincode=:pincode, u.surname=:surname  WHERE u.id=:id")
		int updateUser(@Param("country") String country, @Param("dob") java.util.Date date,@Param("doj") java.util.Date date2,@Param("email") String email,@Param("name") String name,@Param("password") String password,@Param("pincode") String pincode,@Param("surname") String surname, @Param("id") Long id);
	

	   
	    
	    @Transactional
	  		@Modifying
	  		@Query("UPDATE User u SET u.deleteStatus=:stat  WHERE u.id=:id")
	  		int softDeleteUser(@Param("stat") int stat, @Param("id") Long id);
	    
	    List<User> findByDeleteStatus(int stat);
}
