package com.fincheck.fincheckapijava.security;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.fincheck.fincheckapijava.repository.UsersRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UsersRepository usersRepo;
    
    @Override
    public UserDetails loadUserByUsername(String email) {
        return usersRepo.findByEmail(email).get();
    }

    public UserDetails loadById(UUID id) {
        return usersRepo.findById(id).get();
    }
    
}
