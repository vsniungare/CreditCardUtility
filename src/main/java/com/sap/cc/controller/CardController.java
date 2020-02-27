package com.sap.cc.controller;

import com.sap.cc.dto.CardDto;
import com.sap.cc.model.CreditCard;
import com.sap.cc.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping(value = "/add")
    public ResponseEntity<Void> addCreditCard(@RequestBody CardDto cardDto) {
        cardService.processNewCard(cardDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAllCards")
    public List<CreditCard> getAllCreditCards(){
        return cardService.getAllCards();
    }

}
