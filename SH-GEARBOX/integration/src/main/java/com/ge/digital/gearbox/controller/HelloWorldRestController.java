package com.ge.digital.gearbox.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ge.digital.gearbox.common.response.NormalResponse;
import com.ge.digital.gearbox.redis.RedisService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Controller
@CrossOrigin

public class HelloWorldRestController {
	private static final Logger logger = LoggerFactory.getLogger(HelloWorldRestController.class);

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	RedisService redisService;
	@Autowired
	RedisTemplate<Object, Object> redisTemplate;
	@Autowired
	RedisConnectionFactory factory;

	@RequestMapping(value = "/user/me", method = RequestMethod.GET)
	@ResponseBody
	public Principal user(Principal principal) {
		System.out.println(principal);
		return principal;
	}

	@HystrixCommand(fallbackMethod = "error")
	@RequestMapping(value = "/testHystrix/", method = RequestMethod.GET)
	@ResponseBody
	public Object testHystrix() {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://wip/user2/me2/", String.class,
				"Surgery");

		return responseEntity.getBody();
	}

	public String error() {
		return "error";
	}

	@HystrixCommand(fallbackMethod = "error")
	@RequestMapping(value = "/randomNum/", method = RequestMethod.GET)
	@ResponseBody
	public Object randomNum() {

		// logger.info("test");
		// redisService.setStr("123", "55554");
		// redisService.addForList("123", "tewrw");
		// List<Object> objects = redisTemplate.opsForList().range("123", 0,
		// 51);
		// for(Object object:objects)
		// {
		//// System.out.println(object);
		// }
		NormalResponse rsp = new NormalResponse();
		rsp.setBody(Math.random() * 100);
		// rsp.setErrorCode("0000");
		return rsp;
	}
	// -------------------Retrieve Single
	// User--------------------------------------------------------

	/*
	 * @RequestMapping(value = "/user/{id}", method = RequestMethod.GET,
	 * produces =
	 * {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	 * public ResponseEntity<User> getUser(@PathVariable("id") long id) {
	 * System.out.println("Fetching User with id " + id); User user =
	 * userService.findById(id); if (user == null) {
	 * System.out.println("User with id " + id + " not found"); return new
	 * ResponseEntity<User>(HttpStatus.NOT_FOUND); } return new
	 * ResponseEntity<User>(user, HttpStatus.OK); }
	 */

	// -------------------Create a
	// User--------------------------------------------------------

	/*
	 * @RequestMapping(value = "/user/", method = RequestMethod.POST) public
	 * ResponseEntity<Void> createUser(@RequestBody User user,
	 * UriComponentsBuilder ucBuilder) { System.out.println("Creating User " +
	 * user.getName());
	 * 
	 * if (userService.isUserExist(user)) {
	 * System.out.println("A User with name " + user.getName() +
	 * " already exist"); return new ResponseEntity<Void>(HttpStatus.CONFLICT);
	 * }
	 * 
	 * userService.saveUser(user);
	 * 
	 * HttpHeaders headers = new HttpHeaders();
	 * headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.
	 * getId()).toUri()); return new ResponseEntity<Void>(headers,
	 * HttpStatus.CREATED); }
	 */

	// ------------------- Update a User
	// --------------------------------------------------------

	/*
	 * @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT) public
	 * ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody
	 * User user) { System.out.println("Updating User " + id);
	 * 
	 * User currentUser = userService.findById(id);
	 * 
	 * if (currentUser==null) { System.out.println("User with id " + id +
	 * " not found"); return new ResponseEntity<User>(HttpStatus.NOT_FOUND); }
	 * 
	 * currentUser.setName(user.getName()); currentUser.setAge(user.getAge());
	 * currentUser.setSalary(user.getSalary());
	 * 
	 * userService.updateUser(currentUser); return new
	 * ResponseEntity<User>(currentUser, HttpStatus.OK); }
	 */

	// ------------------- Delete a User
	// --------------------------------------------------------

	/*
	 * @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	 * public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
	 * System.out.println("Fetching & Deleting User with id " + id);
	 * 
	 * User user = userService.findById(id); if (user == null) {
	 * System.out.println("Unable to delete. User with id " + id +
	 * " not found"); return new ResponseEntity<User>(HttpStatus.NOT_FOUND); }
	 * 
	 * userService.deleteUserById(id); return new
	 * ResponseEntity<User>(HttpStatus.NO_CONTENT); }
	 */

	// ------------------- Delete All Users
	// --------------------------------------------------------

	/*
	 * @RequestMapping(value = "/user/", method = RequestMethod.DELETE) public
	 * ResponseEntity<User> deleteAllUsers() {
	 * System.out.println("Deleting All Users");
	 * 
	 * userService.deleteAllUsers(); return new
	 * ResponseEntity<User>(HttpStatus.NO_CONTENT); }
	 */

}