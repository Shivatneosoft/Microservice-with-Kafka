package com.user.repository;

import com.user.ResponseBean.ResponseBean;
import com.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUserNameAndUserGender(String userName, String gender);

    public Optional<User> findByUserName(String userName);
}
