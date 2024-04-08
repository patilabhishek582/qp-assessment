package com.questionpro.testassignment.Controller;

import com.questionpro.testassignment.Entities.RefreshToken;
import com.questionpro.testassignment.Model.*;
import com.questionpro.testassignment.Service.GroceryService;
import com.questionpro.testassignment.Service.JwtService;
import com.questionpro.testassignment.Service.RefreshTokenService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assignment")
public class GroceryController {

    @Autowired
    GroceryService groceryService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    AuthenticationManager authenticationManager;

    private static final Logger logger = LoggerFactory.getLogger(GroceryController.class);

    @GetMapping("/getGroceries")
    public ResponseEntity<Object> getGroceries() {
        List<GroceryDTO> groceries = groceryService.getGroceries();
        return new ResponseEntity<>(groceries, HttpStatus.OK);
    }

    @PostMapping("/AddGrocery")
    @Transactional
    public ResponseEntity<Object> addGrocery(@RequestBody GroceryDTO groceryDTO) {
        groceryService.addGrocery(groceryDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/UpdateGrocery")
    @Transactional
    public ResponseEntity<Object> updateGrocery(@RequestBody GroceryDTO groceryDTO) {
        if (groceryService.updateGrocery(groceryDTO))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/grocery/{groceryID}")
    public ResponseEntity<Object> deleteGrocery(@PathVariable String groceryID) {
        logger.info("Request received to id {} ", groceryID);
        if (groceryService.deleteGrocery(groceryID))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @PostMapping("/order")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<OrderResponse> postOrders(@RequestBody OrderDTO order) {
        return groceryService.orderItems(order);

    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody AuthRequest authRequest) {
        logger.info("received login request : {}", authRequest.getUsername());
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getUsername());
            return JwtResponse.builder()
                    .accessToken(jwtService.generateToken(authRequest.getUsername()))
                    .token(refreshToken.getToken())
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found in db");
        }
    }

    @PostMapping("/refreshToken")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.generateToken(userInfo.getName());
                    return JwtResponse.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequest.getToken())
                            .build();
                }).orElseThrow(() -> new RuntimeException("Invalid refresh token"));
    }
}
