package com.questionpro.testassignment.Repository;

import com.questionpro.testassignment.Entities.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order,String> {
}
