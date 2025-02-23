package org.springai.flash.repository;

import org.springai.flash.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer> {

    Users findByUsername(String username);



}
