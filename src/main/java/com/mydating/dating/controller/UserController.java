package com.mydating.dating.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mydating.dating.entity.User;
import com.mydating.dating.service.UserService;

@RestController
public class UserController {
	@Autowired
	UserService userService;

	//To save user Data
	@PostMapping("/users")
	public ResponseEntity<?> saveUser(@RequestBody User user) {
		return userService.saveUser(user);
	}
	

	//Filter only Male Users only
	@GetMapping("/users/gender/male")
	public ResponseEntity<?> findAllMaleUsers(){
		return userService.findAllMaleUsers();
	}
	
	//Filter only Female users only
	@GetMapping("/users/gender/female")
	public ResponseEntity<?> findAllFemaleUsers(){
		return userService.findAllFemaleUsers();
	}
	
	//Finding best Match for based on id and top best Match
	@GetMapping("/users/best-match/{id}/{top}")
	public ResponseEntity<?> findBestMatch(@PathVariable int id,@PathVariable int top){
		return userService.findBestMatch(id,top);
	}
	
	//Search User based on name letters
	@GetMapping("/users/search/name/{letters}")
	public ResponseEntity<?> searchByName(@PathVariable String letters){
		return userService.searchByName(letters);
	}
	
	//Search User based on email letters
	@GetMapping("/users/search/email/{letters}")
	public ResponseEntity<?> searchByEmail(@PathVariable String letters){
		return userService.searchByEmail(letters);
	}
	
	
	//Fetch User Based on id
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable int id){	
		return userService.deleteUserById(id);
	}
	
	//Fetch All Male And Female Users
	@GetMapping("/users/all")
	public ResponseEntity<?> fetchAllUsers(){	
		return userService.getAllUsers();
	}
	
	
	//  Filter Users by Age Range
	@GetMapping("/users/age/{min}/{max}")
	public ResponseEntity<?> findUserByAgeRange(@PathVariable("min") int minAge , @PathVariable("max") int maxAge){	
		return userService.getAllUserByAgeRange(minAge,maxAge);
	}
	
	//Filter All Male Users By  Age Range  (users/male/age?minAge=20&&maxAge=30)
	@GetMapping("/users/male/age")
	public ResponseEntity<?> findMaleUsersByAge(@RequestParam int minAge , @RequestParam int maxAge){
		return userService.findAllMaleUserByAgeRange(minAge,maxAge);
	}
	
	//Filter All Female Users By Age Range   (users/female/age?minAge=21&&maxAge=30)
	@GetMapping("/users/female/age")
	public ResponseEntity<?> findFemaleUserByAge(@RequestParam int minAge,@RequestParam int maxAge){	
		return userService.findAllFemaleUserByAgeRange(minAge,maxAge);
	}

}
