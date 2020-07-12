package com.vardges.javabeltexam.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.vardges.javabeltexam.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	List<User> findAll();
    User findByEmail(String email);
}
