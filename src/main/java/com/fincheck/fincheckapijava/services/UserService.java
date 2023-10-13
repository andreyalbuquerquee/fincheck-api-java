package com.fincheck.fincheckapijava.services;

import com.fincheck.fincheckapijava.model.User;
import com.fincheck.fincheckapijava.repository.UsersRepository;
import com.fincheck.fincheckapijava.security.JWTService;
import com.fincheck.fincheckapijava.shared.dtos.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UsersRepository usersRepo;

    @Autowired
    JWTService jwtService;

    public Object getUserById(String accessToken) {
        UUID currentUserId = jwtService.activeUserId(accessToken.substring(7)).get();
        User user = usersRepo.findById(currentUserId).get();
        return new UserDto(user.getName(), user.getEmail());
    }

}
