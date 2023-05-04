package com.user.repository;

import com.user.entity.UserContactMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Transactional
public interface UserContactMapperRepository extends JpaRepository<UserContactMapper, Long> {

    public List<UserContactMapper> findAllByUserId(Long userId);

    public void deleteAllByUserId(Long userId);

}
