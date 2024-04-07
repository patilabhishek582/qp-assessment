package com.questionpro.testassignment.Controller;

import com.questionpro.testassignment.Model.GroceryDTO;
import com.questionpro.testassignment.Model.OrderDTO;
import com.questionpro.testassignment.Model.OrderResponse;
import com.questionpro.testassignment.Service.GroceryService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assignment")
public class GroceryController {

    @Autowired
    GroceryService groceryService;

    private static final Logger logger = LoggerFactory.getLogger(GroceryController.class);

    @GetMapping("/getGroceries")
    public ResponseEntity<Object> getGroceries(){
        List<GroceryDTO> groceries = groceryService.getGroceries();
        return new ResponseEntity<>(groceries,HttpStatus.OK);
    }

    @PostMapping("/AddGrocery")
    @Transactional
    public ResponseEntity<Object> addGrocery(@RequestBody GroceryDTO groceryDTO){
        groceryService.addGrocery(groceryDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/UpdateGrocery")
    @Transactional
    public ResponseEntity<Object> updateGrocery(@RequestBody GroceryDTO groceryDTO){
        if(groceryService.updateGrocery(groceryDTO))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/grocery/{groceryID}")
    public ResponseEntity<Object> deleteGrocery(@PathVariable String groceryID){
        logger.info("Request received to id {} ",groceryID);
        if(groceryService.deleteGrocery(groceryID))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @PostMapping("/order")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<OrderResponse> postOrders(@RequestBody OrderDTO order){
        return groceryService.orderItems(order);

    }
}
