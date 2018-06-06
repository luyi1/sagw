package com.ge.digital.gearbox.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ge.digital.gearbox.entity.Users;
import com.ge.digital.gearbox.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
@Controller
@CrossOrigin

public class HelloWorldRestController {
	private static final Logger logger  =  LoggerFactory.getLogger(HelloWorldRestController.class);
    @Autowired
    UserService userService;  //Service which will do all data retrieval/manipulation work
    @Autowired
    RestTemplate restTemplate;
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
    	 ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://wip/user2/me12/", String.class, "Surgery");
    	    return responseEntity.getBody();
    }
    @RequestMapping(value = "/user2/me2/", method = RequestMethod.GET)
    @ResponseBody
    public List<Users> user2(Principal principal) {
    	 List<Users> users = userService.findAll();
         if(users.isEmpty()){
             return null;
         }
         return users;
    } 
    public String error() {
        return "error";
    }
    //-------------------Retrieve All Users--------------------------------------------------------

//    @PreAuthorize("hasRole('admin')")
    
    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    @ResponseBody
    public List<Users> listAllUsers() {
        List<Users> users = userService.findAll();
        if(users.isEmpty()){
            return null;
        }
        return users;
    }
  
    @RequestMapping(value = "/randomNum/", method = RequestMethod.GET)
    @ResponseBody
    public Double randomNum() {
    	logger.error("test");
        return Math.random()*100;
    }
    //-------------------Retrieve Single User--------------------------------------------------------
      
    /*@RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        System.out.println("Fetching User with id " + id);
        User user = userService.findById(id);
        if (user == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }*/
  
      
      
    //-------------------Create a User--------------------------------------------------------
      
    /*@RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating User " + user.getName());
  
        if (userService.isUserExist(user)) {
            System.out.println("A User with name " + user.getName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
  
        userService.saveUser(user);
  
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }*/
  
      
    //------------------- Update a User --------------------------------------------------------
      
    /*@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        System.out.println("Updating User " + id);
          
        User currentUser = userService.findById(id);
          
        if (currentUser==null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
  
        currentUser.setName(user.getName());
        currentUser.setAge(user.getAge());
        currentUser.setSalary(user.getSalary());
          
        userService.updateUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }*/
  
    //------------------- Delete a User --------------------------------------------------------
      
    /*@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting User with id " + id);
  
        User user = userService.findById(id);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
  
        userService.deleteUserById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }*/
  
      
    //------------------- Delete All Users --------------------------------------------------------
      
    /*@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteAllUsers() {
        System.out.println("Deleting All Users");
  
        userService.deleteAllUsers();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }*/
  
}