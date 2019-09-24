package com.personal.seckill.service;

import com.personal.seckill.dao.UserDao;
import com.personal.seckill.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User getUserById(int id){

        return userDao.getUserById(id);
    }

    @Transactional
    public boolean txTest() {

        User user1 = new User();
        user1.setId(2);
        user1.setName("testName2");
        System.out.println(user1.getId()+user1.getName());
        userDao.addUser(user1);



        User user2 = new User();
        user2.setId(1);
        user2.setName("testName1");
        System.out.println(user2.getId()+user2.getName());
        userDao.addUser(user2);



        return true;
    }
}
