package by.nuray.filomrate.controllers;


import by.nuray.filomrate.dao.UserDao;
import by.nuray.filomrate.models.User;
import by.nuray.filomrate.util.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserDao userDao;
    private final UserValidator userValidator;


    @Autowired
    public UserController(UserDao userDao, UserValidator userValidator) {
        this.userDao = userDao;
        this.userValidator = userValidator;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user,
                                          BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {

            StringBuilder errors = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errors.append(error.getDefaultMessage()).append("\n");
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errors.toString());

        }
        userDao.addUser(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }


    @PutMapping("users")
    public ResponseEntity<User> updateUser(@RequestBody @Valid User user,
                                           BindingResult bindingResult) {

        if (user.getId() ==0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errors.append(error.getDefaultMessage()).append("\n");
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errors.toString());
        }

        Optional<User> toBeUpdated = userDao.getUserById(user.getId());
        if (toBeUpdated.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        userDao.updateUser(user, user.getId());
        return new ResponseEntity( HttpStatus.OK);
    }
}
