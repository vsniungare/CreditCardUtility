package com.sap.cc.repository;

import com.sap.cc.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<CreditCard, Long> {
}
