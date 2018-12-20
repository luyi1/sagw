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
import com.ge.digital.gearbox.mapper.CustomMongoRepository;
import com.ge.digital.gearbox.mapper.MongoC2H2Repository;
import com.ge.digital.gearbox.mapper.MongoCcfRepository;
import com.ge.digital.gearbox.mapper.MongoCtgRepository;
import com.ge.digital.gearbox.mapper.MongoExCarRepository;
import com.ge.digital.gearbox.mapper.MongoInCarRepository;
import com.ge.digital.gearbox.mapper.MongoPreoxRepository;
import com.ge.digital.gearbox.mapper.MongoProductionProcRepository;
import com.ge.digital.gearbox.mapper.MongoTemperRepository;
import com.ge.digital.gearbox.mapper.MongoTunnelRepository;
import com.ge.digital.gearbox.mapper.MongoWashRepository;
import com.ge.digital.gearbox.mq.Customer;
import com.ge.digital.gearbox.util.SaveDateThread;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Controller
@CrossOrigin

public class HelloWorldRestController {
	private static final Logger logger = LoggerFactory.getLogger(HelloWorldRestController.class);

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	RedisTemplate<Object, Object> redisTemplate;
	@Autowired
	RedisConnectionFactory factory;

	@Autowired
	CustomMongoRepository customMongoRepository;

	@Autowired
	Customer customer;

	@Autowired
	MongoC2H2Repository mongoC2H2Repository;

	@Autowired
	MongoCcfRepository mongoCcfRepository;

	@Autowired
	MongoCtgRepository mongoCtgRepository;

	@Autowired
	MongoExCarRepository mongoExCarRepository;

	@Autowired
	MongoInCarRepository mongoInCarRepository;

	@Autowired
	MongoPreoxRepository mongoPreoxRepository;
	@Autowired
	MongoTemperRepository mongoTemperRepository;
	@Autowired
	MongoTunnelRepository mongoTunnelRepository;
	@Autowired
	MongoWashRepository mongoWashRepository;

	@Autowired
	MongoProductionProcRepository mongoProductionProcRepository;
	
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
		SaveDateThread saveDateThread1 = new SaveDateThread(mongoC2H2Repository, 1,"c2h2");
		saveDateThread1.start();
		SaveDateThread saveDateThread2 = new SaveDateThread(mongoCcfRepository, 2,"ccf");
		saveDateThread2.start();
		SaveDateThread saveDateThread3 = new SaveDateThread(mongoCtgRepository, 3,"ctg");
		saveDateThread3.start();
		SaveDateThread saveDateThread4 = new SaveDateThread(mongoExCarRepository, 4,"excar");
		saveDateThread4.start();
		SaveDateThread saveDateThread5 = new SaveDateThread(mongoInCarRepository, 5,"in car");
		saveDateThread5.start();
		SaveDateThread saveDateThread6 = new SaveDateThread(mongoPreoxRepository, 6,"preox");
		saveDateThread6.start();
		SaveDateThread saveDateThread7 = new SaveDateThread(mongoTemperRepository, 7,"temper");
		saveDateThread7.start();
		SaveDateThread saveDateThread8 = new SaveDateThread(mongoTunnelRepository, 8,"tunnel");
		saveDateThread8.start();
		SaveDateThread saveDateThread9 = new SaveDateThread(mongoWashRepository, 9,"wash");
		saveDateThread9.start();
		SaveDateThread saveDateThread10 = new SaveDateThread(mongoProductionProcRepository, 10,"proc");
		saveDateThread10.start();
		SaveDateThread saveDateThread11 = new SaveDateThread(mongoC2H2Repository, 1,"c2h22");
		saveDateThread11.start();
		SaveDateThread saveDateThread12 = new SaveDateThread(mongoCcfRepository, 2,"ccf2");
		saveDateThread12.start();
		SaveDateThread saveDateThread13 = new SaveDateThread(mongoCtgRepository, 3,"ctg2");
		saveDateThread13.start();
		SaveDateThread saveDateThread14 = new SaveDateThread(mongoExCarRepository, 4,"excar2");
		saveDateThread14.start();
		SaveDateThread saveDateThread15 = new SaveDateThread(mongoInCarRepository, 5,"in car2");
		saveDateThread15.start();
		SaveDateThread saveDateThread16 = new SaveDateThread(mongoPreoxRepository, 6,"preox2");
		saveDateThread16.start();
		SaveDateThread saveDateThread17 = new SaveDateThread(mongoTemperRepository, 7,"temper2");
		saveDateThread17.start();
		SaveDateThread saveDateThread18 = new SaveDateThread(mongoTunnelRepository, 8,"tunnel2");
		saveDateThread18.start();
		SaveDateThread saveDateThread19 = new SaveDateThread(mongoWashRepository, 9,"wash2");
		saveDateThread19.start();
		SaveDateThread saveDateThread20 = new SaveDateThread(mongoProductionProcRepository, 10,"proc2");
		saveDateThread20.start();
		SaveDateThread saveDateThread31 = new SaveDateThread(mongoC2H2Repository, 1,"c2h23");
		saveDateThread31.start();
		SaveDateThread saveDateThread32 = new SaveDateThread(mongoCcfRepository, 2,"ccf3");
		saveDateThread32.start();
		SaveDateThread saveDateThread33 = new SaveDateThread(mongoCtgRepository, 3,"ctg3");
		saveDateThread33.start();
		SaveDateThread saveDateThread34 = new SaveDateThread(mongoExCarRepository, 4,"excar3");
		saveDateThread34.start();
		SaveDateThread saveDateThread35 = new SaveDateThread(mongoInCarRepository, 5,"in car3");
		saveDateThread35.start();
		SaveDateThread saveDateThread36 = new SaveDateThread(mongoPreoxRepository, 6,"preox3");
		saveDateThread36.start();
		SaveDateThread saveDateThread37 = new SaveDateThread(mongoTemperRepository, 7,"temper3");
		saveDateThread37.start();
		SaveDateThread saveDateThread38 = new SaveDateThread(mongoTunnelRepository, 8,"tunnel3");
		saveDateThread38.start();
		SaveDateThread saveDateThread39 = new SaveDateThread(mongoWashRepository, 9,"wash3");
		saveDateThread39.start();
		SaveDateThread saveDateThread40 = new SaveDateThread(mongoProductionProcRepository, 10,"proc3");
		saveDateThread40.start();
		SaveDateThread saveDateThread41 = new SaveDateThread(mongoC2H2Repository, 1,"c2h24");
		saveDateThread41.start();
		SaveDateThread saveDateThread42 = new SaveDateThread(mongoCcfRepository, 2,"ccf4");
		saveDateThread42.start();
		SaveDateThread saveDateThread43 = new SaveDateThread(mongoCtgRepository, 3,"ctg4");
		saveDateThread43.start();
		SaveDateThread saveDateThread44 = new SaveDateThread(mongoExCarRepository, 4,"excar4");
		saveDateThread44.start();
		SaveDateThread saveDateThread45 = new SaveDateThread(mongoInCarRepository, 5,"in car4");
		saveDateThread45.start();
		SaveDateThread saveDateThread46 = new SaveDateThread(mongoPreoxRepository, 6,"preox4");
		saveDateThread6.start();
		SaveDateThread saveDateThread47 = new SaveDateThread(mongoTemperRepository, 7,"temper4");
		saveDateThread47.start();
		SaveDateThread saveDateThread48 = new SaveDateThread(mongoTunnelRepository, 8,"tunnel4");
		saveDateThread48.start();
		SaveDateThread saveDateThread49 = new SaveDateThread(mongoWashRepository, 9,"wash4");
		saveDateThread49.start();
		SaveDateThread saveDateThread50 = new SaveDateThread(mongoProductionProcRepository, 10,"proc4");
		saveDateThread50.start();
		SaveDateThread saveDateThread51 = new SaveDateThread(mongoC2H2Repository, 1,"c2h25");
		saveDateThread51.start();
		SaveDateThread saveDateThread52 = new SaveDateThread(mongoCcfRepository, 2,"ccf5");
		saveDateThread52.start();
		SaveDateThread saveDateThread53 = new SaveDateThread(mongoCtgRepository, 3,"ctg5");
		saveDateThread53.start();
		SaveDateThread saveDateThread54 = new SaveDateThread(mongoExCarRepository, 4,"excar5");
		saveDateThread54.start();
		SaveDateThread saveDateThread55 = new SaveDateThread(mongoInCarRepository, 5,"in car5");
		saveDateThread55.start();
		SaveDateThread saveDateThread56 = new SaveDateThread(mongoPreoxRepository, 6,"preox5");
		saveDateThread56.start();
		SaveDateThread saveDateThread57 = new SaveDateThread(mongoTemperRepository, 7,"temper5");
		saveDateThread57.start();
		SaveDateThread saveDateThread58 = new SaveDateThread(mongoTunnelRepository, 8,"tunnel5");
		saveDateThread58.start();
		SaveDateThread saveDateThread59 = new SaveDateThread(mongoWashRepository, 9,"wash5");
		saveDateThread59.start();
		SaveDateThread saveDateThread60 = new SaveDateThread(mongoProductionProcRepository, 10,"proc5");
		saveDateThread60.start();
		SaveDateThread saveDateThread61 = new SaveDateThread(mongoC2H2Repository, 1,"c2h26");
		saveDateThread61.start();
		SaveDateThread saveDateThread62 = new SaveDateThread(mongoCcfRepository, 2,"ccf6");
		saveDateThread62.start();
		SaveDateThread saveDateThread63 = new SaveDateThread(mongoCtgRepository, 3,"ctg6");
		saveDateThread63.start();
		SaveDateThread saveDateThread64 = new SaveDateThread(mongoExCarRepository, 4,"excar6");
		saveDateThread64.start();
		SaveDateThread saveDateThread65 = new SaveDateThread(mongoInCarRepository, 5,"in car6");
		saveDateThread65.start();
		SaveDateThread saveDateThread66 = new SaveDateThread(mongoPreoxRepository, 6,"preox6");
		saveDateThread66.start();
		SaveDateThread saveDateThread67 = new SaveDateThread(mongoTemperRepository, 7,"temper6");
		saveDateThread67.start();
		SaveDateThread saveDateThread68 = new SaveDateThread(mongoTunnelRepository, 8,"tunnel6");
		saveDateThread68.start();
		SaveDateThread saveDateThread69 = new SaveDateThread(mongoWashRepository, 9,"wash6");
		saveDateThread69.start();
		SaveDateThread saveDateThread70 = new SaveDateThread(mongoProductionProcRepository, 10,"proc6");
		saveDateThread70.start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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