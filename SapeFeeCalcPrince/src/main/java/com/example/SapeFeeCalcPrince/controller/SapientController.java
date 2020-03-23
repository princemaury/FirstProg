package com.example.SapeFeeCalcPrince.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.SapeFeeCalcPrince.dto.Invoice;
import com.example.SapeFeeCalcPrince.service.ProcessFee;

@RestController
public class SapientController {

@Autowired
ProcessFee processFee;

@PostMapping(value = "value")
public List<Invoice> calculateProcessingFee(@RequestParam("file") MultipartFile file) {
return processFee.calculateProcessingFees(file);
}
}