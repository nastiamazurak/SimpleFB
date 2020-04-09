package com.edu.lnu.mongoDbPpoject.service;

import com.edu.lnu.mongoDbPpoject.exception.UserNotFoundByUsername;
import com.edu.lnu.mongoDbPpoject.exception.constant.ErrorMessage;
import com.edu.lnu.mongoDbPpoject.exception.IncorrectPasswordException;
import com.edu.lnu.mongoDbPpoject.model.User;
import com.edu.lnu.mongoDbPpoject.repository.RoleRepository;
import com.edu.lnu.mongoDbPpoject.repository.UserRepository;
import com.edu.lnu.mongoDbPpoject.security.AuthBody;
import com.edu.lnu.mongoDbPpoject.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User getUserInfo(String username) {
        return userRepository.findByNickName(username);
    }

    @Override
    public User updateUserInfo(User user, String username) {
        User newUserInfo = userRepository.findByNickName(username);
        newUserInfo.setName(user.getName());
        newUserInfo.setSurname(user.getSurname());
        newUserInfo.setBirthDate(user.getBirthDate());
        newUserInfo.setCity(user.getCity());
        newUserInfo.setCountry(user.getCountry());
        return userRepository.save(newUserInfo);
    }

    @Override
    public User findUser(String feature) {
        if (userRepository.findByName(feature) != null) {
            return userRepository.findByName(feature);
        } else if (userRepository.findBySurname(feature) != null) {
            return userRepository.findBySurname(feature);
        } else if (userRepository.findByNickName(feature) != null) {
            return userRepository.findByNickName(feature);
        } else {
            return null;
        }
    }

    @Override
    public User findUserByUserName(String username){
        return  userRepository.findByNickName(username);
    }

    @Override
    public boolean registerUser(User user) {
        ;
        user.setRole(roleRepository.findByRole("USER"));
        user.setName(user.getName());
        user.setSurname(user.getSurname());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return true;
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return principal.getUser();
    }

    @Override
    public boolean comparePasswordLogin(AuthBody body, PasswordEncoder passwordEncoder) {
        if(!passwordEncoder.matches(body.getPassword(), findUserByUserName(body.getNickName()).getPassword())){
            throw  new IncorrectPasswordException(ErrorMessage.INVALID_USERNAME_OR_PASSWORD);
        }
        return true;
    }

    @Override
    public boolean existsUserByUsername(String username) {
        if(!userRepository.existsByNickName(username)){
            throw  new UserNotFoundByUsername("User not found with username " + username);
        }else
            return true;
    }

}

