package by.nuray.filomrate.util;

import by.nuray.filomrate.dao.UserDao;
import by.nuray.filomrate.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class UserValidator implements Validator {


    private final UserDao userDao;

     @Autowired
    public UserValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
         User user = (User) target;

         if (userDao.getUserByEmail(user.getEmail()).isPresent()) {
             errors.rejectValue("email", null, "This email address is already in use");
         }

    }
}