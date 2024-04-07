package com.questionpro.testassignment.Repository;

import com.questionpro.testassignment.Entities.Grocery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroceryRepository extends CrudRepository<Grocery,String> {

    Optional<Grocery> findByName(String name);
}
