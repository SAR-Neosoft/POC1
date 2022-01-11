package com.neosoft.controller;


import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.neosoft.model.User;
import com.neosoft.repository.UserRepository;





@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/add")
	public ResponseEntity<?> addUser(@RequestBody User user) {
		System.out.println(user);
		
		 if(userRepository.existsBySurname(user.getSurname())){
	            return new ResponseEntity<>("Surname is already taken!", HttpStatus.BAD_REQUEST);
	        }

	        // add check for email exists in DB
	        if(userRepository.existsByEmail(user.getEmail())){
	            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
	        }
	       
		
	    String emailRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
	    String passRegex="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()]).{4,15}$";
	    String surnameRegex="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{3,15}$";
	    
	      boolean emailValid =user.getEmail().matches(emailRegex);
	      boolean passValid=user.getPassword().matches(passRegex);
	      boolean surnameValid=user.getSurname().matches(surnameRegex);
	     
	      
		if(emailValid && passValid && surnameValid) {
		userRepository.save(user);
		return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
		}else
			return new ResponseEntity<>("Please Check Email and Password", HttpStatus.BAD_REQUEST);
		
	}
	
	@PutMapping("/user/{id}")
	public ResponseEntity<?> updateUser(@RequestBody User user ,@PathVariable Long id) {
		System.out.println("hhhhhhhhhhh"+userRepository.findById(id).isPresent());
		if(userRepository.findById(id).isPresent()) {
		userRepository.updateUser(user.getCountry(), user.getDateOfBirth(), user.getDateOfJoining(), user.getEmail(), user.getName(), user.getPassword(), user.getPincode(), user.getSurname(),id);
		return new ResponseEntity<>("User Updated successfully", HttpStatus.OK);
		}else 
			return new ResponseEntity<>("User Not exist", HttpStatus.BAD_REQUEST);
		
	}
	

	
	@GetMapping("/or/{name}/{surname}/{pincode}")
	public List<User> getUserByNameOrSurnameOrPincode(@PathVariable String name,
			@PathVariable String surname,@PathVariable String pincode) {
		return userRepository.findByNameOrSurnameOrPincode(name, surname,pincode);
	}
	
	@GetMapping("/idOrderbyDobAndDoj")
	public List<User> getSortedUsers() {
		return userRepository.findAllByOrderByDateOfJoiningAscDateOfBirthAsc();
		
	}
	
	@DeleteMapping("/deleteByid/{id}")
	public  ResponseEntity<?> deleteUserById(@PathVariable Long id){
		userRepository.deleteById(id);
		return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
	}

	@PutMapping("/softDelete/{id}")
	public ResponseEntity<?> softDeleteUser(@PathVariable Long id) {
		userRepository.softDeleteUser(1, id);
		System.out.println( " Deleted Successfully (Soft Delete)"+ HttpStatus.OK);
		return new ResponseEntity<>(" Deleted Successfully (Soft Delete)", HttpStatus.OK);
	
	}
	
	@GetMapping("/getBy/{id}")
	public Optional<User> getUsersById(@PathVariable Long id) {
		return userRepository.findById(id);
		
	}
	
	@GetMapping("/byDeleteStatus")
	public List<User> getByDeleteStatus() {
		return userRepository.findByDeleteStatus(0);
		
	}
	
}
