package com.example.blogapp.repository;

import com.example.blogapp.entity.CaptchaCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaptchaRepo extends JpaRepository<CaptchaCode, Integer> {

    Optional<CaptchaCode> findCaptchaCodeByCode (String code);

    @Query(value = "delete from captcha_codes c where c.time < (NOW() + INTERVAL :interval HOUR)", nativeQuery = true)
    void deleteCaptcha(@Param("interval") long interval);
}
