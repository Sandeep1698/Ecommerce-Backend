package com.deere.ecommerce.service;

import com.deere.ecommerce.entity.User;
import com.deere.ecommerce.exception.UserException;

public interface UserService  {
    public User findUserById(Long userId) throws UserException;
    public User findUserProfileByJwt(String jwt) throws UserException;
}
