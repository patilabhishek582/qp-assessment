package com.questionpro.testassignment.Repository;

import com.questionpro.testassignment.Entities.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken,Integer> {
    Optional<RefreshToken> findByToken(String token);
}
