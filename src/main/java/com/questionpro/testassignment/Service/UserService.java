package com.questionpro.testassignment.Service;

import com.questionpro.testassignment.Entities.UserInfo;
import com.questionpro.testassignment.Repository.UserInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserInfoRepository userInfoRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public String addUser(UserInfo userInfo) {
        logger.info("request received to add user {} ",userInfo.getName());
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        return "user added to system ";
    }
}
