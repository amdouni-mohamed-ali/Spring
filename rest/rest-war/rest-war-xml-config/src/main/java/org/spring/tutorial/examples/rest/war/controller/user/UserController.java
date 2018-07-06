package org.spring.tutorial.examples.rest.war.controller.user;

import org.spring.tutorial.examples.rest.war.domain.User;
import org.spring.tutorial.examples.rest.war.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// @RequestMapping("/users") == mapped in web xml file
class UserController {

    /*
     * use postman to test the app
     */

    @Autowired
    UserServiceImpl service;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> findAll() {

        return service.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)// return 200
    public User findOne(@PathVariable("id") Long id) {

        //TODO : http://localhost:8080/rest-war-xml-config/users will be catching by this method
        return service.findUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)//201
    //the body of this answer is empty
    public void createCustomer(@RequestBody User user /* mapping the body of the request to a User java object */) {

        service.addUser(user);
    }

    @PutMapping(path = "/{id}")//for the mapping we use the property path or value its the same thing
    @ResponseStatus(HttpStatus.NO_CONTENT)//204
    public void update(@PathVariable("id") Long id, @RequestBody User user) {

        service.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)//204
    public void delete(@PathVariable("id") Long id) {

        service.deleteUser(id);
    }
}