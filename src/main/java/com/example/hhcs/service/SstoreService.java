package com.example.hhcs.service;

import com.example.hhcs.domain.Sstore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface SstoreService {
    Sstore get();
    int getstatus();
    double getpurchase();
    String getopenid();
    int updatepur(double Purchase_price,String Purchase_people);
}
