package org.spring.tutorial.rest.boot.controllers;

import org.spring.tutorial.rest.boot.models.User;
import org.spring.tutorial.rest.boot.services.UserService;
import org.spring.tutorial.rest.boot.util.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
/*
 * @RestController : First of all, we are using Spring 4′s new @RestController annotation.
 * This annotation eliminates the need of annotating each method with @ResponseBody. Under the hood,
 * @RestController is itself annotated with @ResponseBody, and can be considered as combination of @Controller and @ResponseBody.
 *
 * @ResponseBody : If a method is annotated with @ResponseBody, Spring will bind the return value to outgoing HTTP response body.
 * While doing that, Spring will [behind the scenes] use HTTP Message converters to convert the return value to HTTP response body
 * [serialize the object to response body], based on Content-Type present in request HTTP header. As already mentioned, in Spring 4,
 * you may stop using this annotation.
 *
 * based on the header of the HTTP request spring will determine if the response will be xml or json
 * if the header contains application/xml or text/xml the response will be xml although the response will be json
 */
@RequestMapping("/api")
public class RestApiController {


    @Autowired
    //Service which will do all data retrieval/manipulation work
    private UserService userService;

    /**
     * Retrieve All Users
     * @return ResponseEntity
     */
    @RequestMapping(value = {"/user", "/user/"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    //we can use also the new annotations @GetMapping, @PostMapping ... (we gonna use them in an another example)
    //also we can use simple java objects as return type (like User , List<User>, Map<Long,User> ...) and eliminates the ResponseEntity
    public ResponseEntity<?> listAllUsers() {
        /*
         * ResponseEntity is a real deal. It represents the entire HTTP response. Good thing about it is that you can control anything that goes into it.
         * You can specify status code, headers, and body. It comes with several constructors to carry the information you want to sent in HTTP Response.
         */
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);    //204
            // You many decide to return HttpStatus.NOT_FOUND	//404
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Retrieve Single User
     * @param id
     * @return ResponseEntity
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") int id) {
        /*
         * @PathVariable This annotation indicates that a method parameter should be bound to a URI template variable [the one in '{}'].
         */
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity(new CustomErrorType("User with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    /**
     * Create a User
     * @param user
     * @param ucBuilder
     * @return ResponseEntity
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {

        if (userService.isUserExist(user)) {
            return new ResponseEntity(new CustomErrorType("Creation failed. A User with the same id already exist."), HttpStatus.CONFLICT);
        }
        userService.saveUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
        /*
         * this piece of code above will provide the url to the new created user. this url will be in the headers.
         * check the HTTP response header to get the uri of the last created resource.
         */
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }


    /**
     * Update a User
     * @param id
     * @param user
     * @return ResponseEntity
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") int id, @RequestBody User user) {
        /*
         * @RequestBody : If a method parameter is annotated with @RequestBody, Spring will bind the incoming HTTP request body(for the URL mentioned
         * in @RequestMapping for that method) to that parameter. While doing that, Spring will [behind the scenes] use HTTP Message converters to
         * convert the HTTP request body into domain object [deserialize request body to domain object], based on ACCEPT or Content-Type header present in request.
         */
        User lastUser = userService.findById(id);

        if (lastUser == null) {
            return new ResponseEntity(new CustomErrorType("Unable to update. User with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }
        User newUser = new User();
        newUser.setLogin(user.getLogin());
        newUser.setPassword(user.getPassword());
        newUser.setId(user.getId());
        userService.updateUser(id,newUser);
        return new ResponseEntity<User>(newUser, HttpStatus.OK);
    }

    /**
     * Delete a User
     * @param id
     * @return ResponseEntity
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {

        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }
        userService.deleteUserById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    /**
     * Delete All Users
     * @return ResponseEntity
     */
    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAllUsers() {

        userService.deleteAllUsers();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
}
