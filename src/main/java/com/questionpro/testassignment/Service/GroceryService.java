package com.questionpro.testassignment.Service;

import com.questionpro.testassignment.Entities.Grocery;
import com.questionpro.testassignment.Entities.Order;
import com.questionpro.testassignment.Model.GroceryDTO;
import com.questionpro.testassignment.Model.OrderDTO;
import com.questionpro.testassignment.Model.OrderResponse;
import com.questionpro.testassignment.Repository.GroceryRepository;
import com.questionpro.testassignment.Repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroceryService {

    @Autowired
    GroceryRepository groceryRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<GroceryDTO> getGroceries(){
        List<Grocery> groceries = (List<Grocery>) groceryRepository.findAll();
        return groceries.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public void addGrocery(GroceryDTO groceryDTO){
        Grocery grocery = convertToEntity(groceryDTO);
        groceryRepository.save(grocery);
    }

    public boolean updateGrocery(GroceryDTO groceryDTO){
        if(groceryRepository.findById(groceryDTO.getGroceryId()).isPresent()){
            Grocery grocery = convertToEntity(groceryDTO);
            groceryRepository.save(grocery);
            return true;
        }
        else{
            return false;
        }
    }

    private Grocery convertToEntity(GroceryDTO groceryDTO) {
        return modelMapper.map(groceryDTO,Grocery.class);
    }

    private GroceryDTO convertToDto(Grocery grocery) {
        return modelMapper.map(grocery, GroceryDTO.class);
    }

    public boolean deleteGrocery(String groceryID) {
        if(groceryRepository.findById(groceryID).isPresent()){
            groceryRepository.deleteById(groceryID);
            return true;
        }
        else{
            return false;
        }
    }

    public ResponseEntity<OrderResponse> orderItems(OrderDTO order) {
        List<String> groceries = order.getGroceryName();
        double total = 0;
        for(int i = 0;i<groceries.size() ;i++){
            Optional<Grocery> groceryOpt = groceryRepository.findByName(groceries.get(i));
            if(groceryOpt.isPresent()){
               Grocery grocery = groceryOpt.get();
               total += grocery.getPrice() * order.getQuantityPerItem().get(i);
            }
            else {
                throw new RuntimeException("Given Item is not available");
            }
        }
        //save order to order repo
        Order orderEntity = convertOrderToEntity(order);
        orderRepository.save(orderEntity);


        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setUserID(order.getUserID());
        orderResponse.setOrderID(orderEntity.getOrderID());
        orderResponse.setTotalPrice(total);

        return new ResponseEntity<>(orderResponse,HttpStatus.CREATED);
    }

    private Order convertOrderToEntity(OrderDTO orderDTO) {
        return modelMapper.map(orderDTO,Order.class);
    }
}
