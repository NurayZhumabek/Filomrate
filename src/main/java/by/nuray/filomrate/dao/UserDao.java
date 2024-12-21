package by.nuray.filomrate.dao;

import by.nuray.filomrate.models.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserDao {

    private static int COUNTER ;

    private List<User> userList;

    {
        userList = new ArrayList<>();
        userList.add(new User(++COUNTER,"test@gmail.com","firstUser","user",
                LocalDate.of(2000,4,12)));
    }

    public List<User> getAllUsers() {
        return userList;
    }

    public Optional<User> getUserById(int id) {
        return userList.stream().filter(user -> user.getId() == id).findFirst();
    }

    public void addUser(User user) {
        user.setId(++COUNTER);
        userList.add(user);
    }

    public void updateUser(User user,int id) {
        Optional<User> oldUser = getUserById(id);

        oldUser.get().setEmail(user.getEmail());
        oldUser.get().setName(user.getName());
        oldUser.get().setLogin(user.getLogin());
        oldUser.get().setBirthday(user.getBirthday());

    }

    public Optional<User> getUserByEmail(String email) {
        return userList.stream().filter(user -> user.getEmail().equals(email)).findAny();
    }

}
