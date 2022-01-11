package com.neosoft;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neosoft.controller.UserController;
import com.neosoft.model.User;
import com.neosoft.repository.UserRepository;

//@SpringBootTest
@WebMvcTest(UserController.class)
class Poc1ApplicationTests {

//	@Autowired
//	private UserRepository userRepository;
//	
//	@Autowired
//	private UserController uc;

	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper mapper;

	@MockBean
	private UserRepository userRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void register_Success() throws Exception {
		String d = "31/12/1998";
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(d);
//    		User u = new User("India",date1,date1,"Abc15@gmail.com","abc","Aa@1","12345","Aa15",0); 

		User u = User.builder()
				.id((long) 111)
				.country("India")
				.dateOfBirth(date1)
				.dateOfJoining(date1)
				.email("Abc15@gmail.com")
				.name("abc")
				.password("Aa@1")
				.pincode("12345")
				.surname("Aa15")
				.deleteStatus(0)
				.build();

		Mockito.when(userRepository.save(u)).thenReturn(u);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/add")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(u));

		mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()));
	}
	
	
	@Test
	public void register_Fail_DueTo_InValid_Surname_Email_Password() throws Exception {
		String d = "31/12/1998";
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(d);
//    		User u = new User("India",date1,date1,"Abc15@gmail.com","abc","Aa@1","12345","Aa15",0); 

		User u = User.builder()
				.id((long) 111)
				.country("India")
				.dateOfBirth(date1)
				.dateOfJoining(date1)
				.email("Abc15@gmail.com")
				.name("abc")
				.password("Aa@1")
				.pincode("12345")
				.surname("Aa")
				.deleteStatus(0)
				.build();

		Mockito.when(userRepository.save(u)).thenReturn(u);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.post("/add")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(u));

		mockMvc.perform(mockRequest)
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$", notNullValue()));
	}
	
	
	@Test
	public void register_Fail_Due_Email_Already_Exist() throws Exception {
		String d = "31/12/1998";
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(d);
//    		User u = new User("India",date1,date1,"Abc15@gmail.com","abc","Aa@1","12345","Aa15",0); 

		User u = User.builder()
				.id((long) 111)
				.country("India")
				.dateOfBirth(date1)
				.dateOfJoining(date1)
				.email("Abc15@gmail.com")
				.name("abc")
				.password("Aa@1")
				.pincode("12345")
				.surname("Aa1")
				.deleteStatus(0)
				.build();

		Mockito.when(userRepository.existsByEmail(u.getEmail())).thenReturn(true);
		
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.post("/add")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(u));

		mockMvc.perform(mockRequest)
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$", notNullValue()));
	}
	
	@Test
	public void register_Fail_DueTo_Surname_Already_Taken() throws Exception {
		String d = "31/12/1998";
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(d);

		User u = User.builder()
				.id((long) 111)
				.country("India")
				.dateOfBirth(date1)
				.dateOfJoining(date1)
				.email("Abc15@gmail.com")
				.name("abc")
				.password("Aa@1")
				.pincode("12345")
				.surname("Aa1")
				.deleteStatus(0)
				.build();


		Mockito.when(userRepository.existsBySurname(u.getSurname())).thenReturn(true);
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.post("/add")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(u));

		mockMvc.perform(mockRequest)
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$", notNullValue()));
	}
	
	@Test
	public void findUserById_Success() throws Exception {
		String d = "31/12/1998";
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(d);

		User u = User.builder()
				.id((long) 111)
				.country("India")
				.dateOfBirth(date1)
				.dateOfJoining(date1)
				.email("Abc15@gmail.com")
				.name("abc")
				.password("Aa@1")
				.pincode("12345")
				.surname("Aa1")
				.deleteStatus(0)
				.build();


		Mockito.when(userRepository.findById(u.getId())).thenReturn(Optional.of(u));
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.get("/getBy/111")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(u));

		mockMvc.perform(mockRequest)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()));
	}
	
    @Test
    public void updateUserRecord_success() throws Exception {
    	String d = "31/12/1998";
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(d); 

		User updatedRecord = User.builder()
				.id((long) 111)
				.country("India")
				.dateOfBirth(date1)
				.dateOfJoining(date1)
				.email("Abc16@gmail.com")
				.name("abcd")
				.password("Aa@16")
				.pincode("12345")
				.surname("Aa16")
				.deleteStatus(0)
				.build();

        Mockito.when(userRepository.findById(updatedRecord.getId()))
        .thenReturn(Optional.of(updatedRecord));
        
        Mockito.when(userRepository.save(updatedRecord)).thenReturn(updatedRecord);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/user/111")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedRecord));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
                
    }
    
    
    
    @Test
    public void updateUserRecord_Fail() throws Exception {
    	String d = "31/12/1998";
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(d); 

		User updatedRecord = User.builder()
				.id((long) 1)
				.country("India")
				.dateOfBirth(date1)
				.dateOfJoining(date1)
				.email("Abc16@gmail.com")
				.name("abcd")
				.password("Aa@16")
				.pincode("12345")
				.surname("Aa16")
				.deleteStatus(0)
				.build();

        Mockito.when(userRepository.findById(updatedRecord.getId()))
        .thenReturn(Optional.of(updatedRecord));
        
        Mockito.when(userRepository.save(updatedRecord)).thenReturn(updatedRecord);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/user/111")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedRecord));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", notNullValue()));
                
    }
	
    @Test
    public void getUserByNameOrSurnameOrPincode_success() throws Exception {
    	
    	String d = "31/12/1998";
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(d); 

		User u = User.builder()
				.id((long) 1)
				.country("India")
				.dateOfBirth(date1)
				.dateOfJoining(date1)
				.email("Abc16@gmail.com")
				.name("abcd")
				.password("Aa@16")
				.pincode("12345")
				.surname("Aa16")
				.deleteStatus(0)
				.build();
    	
        Mockito.when(userRepository.findByNameOrSurnameOrPincode(u.getName(), u.getSurname(), u.getPincode()))
        				.thenReturn(Arrays.asList(u));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/or/abcd/Aa16/12345")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }
    
    @Test
    public void getSortedUsers_success() throws Exception {
    	
    	String d = "31/12/1998";
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(d); 

		User u = User.builder()
				.id((long) 111)
				.country("India")
				.dateOfBirth(date1)
				.dateOfJoining(date1)
				.email("Abc16@gmail.com")
				.name("abcd")
				.password("Aa@16")
				.pincode("12345")
				.surname("Aa16")
				.deleteStatus(0)
				.build();
    	
        Mockito.when(userRepository.findAllByOrderByDateOfJoiningAscDateOfBirthAsc())
        				.thenReturn(Arrays.asList(u));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/idOrderbyDobAndDoj")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }
    
    
	@Test
	public void findByDeleteStatus_Success() throws Exception {
		String d = "31/12/1998";
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(d);

		User u = User.builder()
				.id((long) 111)
				.country("India")
				.dateOfBirth(date1)
				.dateOfJoining(date1)
				.email("Abc15@gmail.com")
				.name("abc")
				.password("Aa@1")
				.pincode("12345")
				.surname("Aa1")
				.deleteStatus(0)
				.build();


		Mockito.when(userRepository.findById(u.getId())).thenReturn(Optional.of(u));
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.get("/byDeleteStatus")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(u));

		mockMvc.perform(mockRequest)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()));
	}
	
	
	
    @Test
    public void softDeleteUser_success() throws Exception {
    	String d = "31/12/1998";
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(d); 

		User updatedRecord = User.builder()
				.id((long) 111)
				.country("India")
				.dateOfBirth(date1)
				.dateOfJoining(date1)
				.email("Abc16@gmail.com")
				.name("abcd")
				.password("Aa@16")
				.pincode("12345")
				.surname("Aa16")
				.deleteStatus(0)
				.build();

        Mockito.when(userRepository.softDeleteUser(1,updatedRecord.getId()))
        .thenReturn(1);
        
       

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/softDelete/111")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedRecord));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
                
    }
    
    @Test
    public void deleteUserById_success() throws Exception {
        
    	String d = "31/12/1998";
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(d); 

		User u = User.builder()
				.id((long) 111)
				.country("India")
				.dateOfBirth(date1)
				.dateOfJoining(date1)
				.email("Abc16@gmail.com")
				.name("abcd")
				.password("Aa@16")
				.pincode("12345")
				.surname("Aa16")
				.deleteStatus(0)
				.build();
    	
//    	Mockito.when(userRepository.deleteById(u.getId()))
//        .thenReturn();
    	
    	
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/deleteByid/111")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());              
    }
	
	

//------------------------------------------------------------------
//	@Test
//	void testGet() throws Exception {
//		String d = "31/12/1998";
//		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(d);
////		System.out.println(userRepository.updateUser("india", date1, date1,
////				"abcd@gmail.com", "abcd", "Abcd@1", "11111", "Abcd1", (long) 2));
//		assertEquals(1, userRepository.updateUser("india", date1, date1,
//				"abcd@gmail.com", "abcd", "Abcd@1", "11111", "Abcd1", (long) 2));
//	}
//	
//	
//	@Test 
//	public void testReg() throws Exception { 
//		User u = new User(); 
//		String d = "31/12/1998";
//		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(d);
////		u.setId(null);
//		u.setCountry("India");
//		u.setDateOfBirth(date1);
//		u.setDateOfJoining(date1);
//		u.setEmail("Abc14@gmail.com");
//		u.setName("abc");
//		u.setPassword("Aa@1");
//		u.setPincode("12345");
//		u.setSurname("Aa14");
//		u.setDeleteStatus(0);
//		//System.out.println(uc.addUser(u));
//		assertEquals(1,uc.addUser(u));
//		}
	// ----------------------------------------------------------------------------------------------
}
