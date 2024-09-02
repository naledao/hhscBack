package com.example.hhcs.controller;

import com.example.hhcs.domain.ImageS;
import com.example.hhcs.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageService imageService;
    @GetMapping("/imageS")
    private ImageS get(){
        return imageService.get(1);
    }
}
