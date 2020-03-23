package com.example.SapeFeeCalcPrince.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.SapeFeeCalcPrince.dto.Invoice;

public interface ProcessFee {

public List<Invoice> calculateProcessingFees(MultipartFile file);
}
