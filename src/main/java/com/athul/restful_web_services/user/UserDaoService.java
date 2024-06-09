package com.athul.restful_web_services.user;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<>();
    private static int usersCount=0;

    static {
        users.add(new User(++usersCount,"Adam", LocalDate.now().minusYears(30)));
        users.add(new User(++usersCount,"Eve", LocalDate.now().minusYears(25)));
        users.add(new User(++usersCount,"Jim", LocalDate.now().minusYears(20)));

    }
    //find all user
    public List<User> findAll(){
        return users;
    }
    //save one user

    //find one user
    public User findOne(int id){
        Predicate<User> predicate = user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().orElse(null);
    }

    //delete one user
    public void deleteUserById (int id){
        Predicate<User> predicate = user -> user.getId().equals(id);
        users.removeIf(predicate);

    }

    //create a user
    public User createUser(@RequestBody User user){
        user.setId(++usersCount);
         users.add(user);
         return user;
    }
}
