package com.example.UserAuthenticationService.repos;

import com.example.UserAuthenticationService.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {

}
