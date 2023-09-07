package com.fincheck.fincheckapijava.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fincheck.fincheckapijava.model.User;

public interface UsersRepository extends JpaRepository<User, UUID>{
    Optional<User> findByEmail(String email);
}
