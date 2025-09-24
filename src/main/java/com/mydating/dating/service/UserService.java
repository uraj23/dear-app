package com.mydating.dating.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mydating.dating.dao.UserDao;
import com.mydating.dating.dto.MatchingUser;
import com.mydating.dating.entity.User;
import com.mydating.dating.exceptionclasses.InvalidUserIdException;
import com.mydating.dating.exceptionclasses.NoSuchRecordFoundException;
import com.mydating.dating.util.ResponseStructure;
import com.mydating.dating.util.UserGender;
import com.mydating.dating.util.UserSorting;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public ResponseEntity<?> saveUser(User user) {
		User savedUser = userDao.saveUser(user);
		return ResponseEntity.ok(ResponseStructure.<User>builder().status(HttpStatus.OK.value())
				.message("User Saved Successfully...!").body(savedUser).build());
	}

	public ResponseEntity<?> findAllMaleUsers() {
		List<User> maleUsers = userDao.findAllMaleUsers();
		if (maleUsers.isEmpty())
			throw new NoSuchRecordFoundException("No Male Users Present in the Database table...");

		return ResponseEntity.ok(ResponseStructure.<List<User>>builder().status(HttpStatus.OK.value())
				.message("Male Records found succesfully...").body(maleUsers).build());
	}

	public ResponseEntity<?> findAllFemaleUsers() {

		List<User> femaleUsers = userDao.findAllFemaleUsers();
		if (femaleUsers.isEmpty()) {
			throw new NoSuchRecordFoundException("No Female Users Present in the Database table...");
		}

		return ResponseEntity.ok(

				ResponseStructure.<List<User>>builder().status(HttpStatus.OK.value())
						.message("Female Records Found Succesfully...").body(femaleUsers).build()

		);

	}

	public ResponseEntity<?> findBestMatch(int id, int top) {

		Optional<User> optional = userDao.findUserById(id);

		if (optional.isEmpty()) {
			throw new InvalidUserIdException("Invalid User Id Unable to find Best Matches...");
		}

		User user = optional.get();

		List<User> users = null;

		if (user.getGender().equals(UserGender.MALE)) {
			users = userDao.findAllFemaleUsers();
		} else {
			users = userDao.findAllMaleUsers();
		}

		List<MatchingUser> matchingUsers = new ArrayList<>();

		for (User u : users) {
			MatchingUser mu = new MatchingUser();

			mu.setId(u.getId());
			mu.setName(u.getName());
			mu.setEmail(u.getEmail());
			mu.setPhone(u.getPhone());
			mu.setAge(u.getAge());
			mu.setIntrests(u.getIntrests());
			mu.setGender(u.getGender());

			mu.setAgeDiff(Math.abs(user.getAge() - u.getAge()));

			List<String> intrests1 = user.getIntrests();

			List<String> intrests2 = u.getIntrests();

			int mic = 0;

			for (String s : intrests1)
				if (intrests2.contains(s))
					mic++;
			mu.setMic(mic);
			matchingUsers.add(mu);
		}

		Comparator<MatchingUser> c = new UserSorting();

		Collections.sort(matchingUsers, c);

		List<MatchingUser> result = new ArrayList<>();

		for (MatchingUser mu : matchingUsers) {

			if (top == 0) {
				break;
			} else {
				result.add(mu);
				top--;
			}
		}
		return ResponseEntity.ok(ResponseStructure
				.<List<MatchingUser>>builder()
				.status(HttpStatus.OK.value())
				.message("All Matching Parterns Found")
				.body(result)
				.build());
	}

	public ResponseEntity<?> searchByName(String letters) {
		List<User> users = userDao.searchByName("%" + letters + "%");
		if (users.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No User Found with letters : " + letters);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(users);
		}
	}

	public ResponseEntity<?> searchByEmail(String letters) {
		List<User> users = userDao.searchByEmail("%" + letters + "%");
		if (users.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No User Found with letters : " + letters);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(users);
		}
	}

	public ResponseEntity<?> deleteUserById(int id) {
		   Optional<User> optional = userDao.findUserById(id);
		   if(optional.isEmpty()) {
			   throw new InvalidUserIdException("User with Id "+id+" Not Found");
		   }
		   
		  userDao.deleteUserById(id);
		return ResponseEntity.ok(
				ResponseStructure.<User>builder()
				.status(HttpStatus.OK.value())
				.message("User deleted with "+id+" succesfully..")
				.body(optional.get())
				.build()
				);
	}

	public ResponseEntity<?> getAllUsers() {
		List<User> users = userDao.findAllUsers();
		return ResponseEntity.ok(
				ResponseStructure.<List<User>>builder()
				.status(HttpStatus.OK.value())
				.message("All Users Found Succesfully...")
				.body(users)							
				.build()
				);
	}

	public ResponseEntity<?> getAllUserByAgeRange(int minAge, int maxAge) {
		List<User> users = userDao.findAllUserAgeRange(minAge, maxAge);
		if(users.isEmpty()) {
			throw new NoSuchRecordFoundException("No Users Exist in this range : min "+minAge+" max : "+maxAge);
		}		
		return ResponseEntity.ok(
				ResponseStructure.<List<User>>builder()
				.status(HttpStatus.FOUND.value())
				.message("In this Age range "+users.size()+" Users Found ...")
				.body(users)
				.build()			
				);
	}

	public ResponseEntity<?> findAllMaleUserByAgeRange(int minAge, int maxAge) {
		List<User> maleUsers = userDao.findAllMaleUsers(minAge,maxAge);	
		if(maleUsers.isEmpty()) {
			throw new NoSuchRecordFoundException("No male users found between ages "+minAge+" and "+maxAge);
		}		
		return ResponseEntity.ok(
				ResponseStructure.<List<User>>builder()
				.status(HttpStatus.FOUND.value())
				.message(maleUsers.size()+" male users found between ages "+minAge+" and "+maxAge)
				.body(maleUsers)
				.build()			
				);
	}

	public ResponseEntity<?> findAllFemaleUserByAgeRange(int minAge, int maxAge) {
  
		List<User> femaleUsers = userDao.findAllFemaleUsers(minAge,maxAge);
		if(femaleUsers.isEmpty()) {
			throw new NoSuchRecordFoundException("No Female users found between age "+minAge+" and "+maxAge);
		}	
		return ResponseEntity.ok(
				ResponseStructure.<List<User>> builder()
				.status(HttpStatus.FOUND.value())
				.message(femaleUsers.size()+" Female users found between ages "+minAge+" and "+maxAge)
				.body(femaleUsers)
				.build()
				
				);
	}

}
