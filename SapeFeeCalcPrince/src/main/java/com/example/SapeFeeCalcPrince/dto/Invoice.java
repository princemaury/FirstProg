package com.example.SapeFeeCalcPrince.dto;

import java.util.Date;

public class Invoice {

private String clientId;
private Date transactionDate;
private String transactionType;
private boolean priorityFlag;
private Double processingFee;

public Invoice(String clientId, Date transactionDate, String transactionType, boolean priorityFlag,
Double processingFee) {
this.clientId = clientId;
this.transactionDate = transactionDate;
this.transactionType = transactionType;
this.priorityFlag = priorityFlag;
this.processingFee = processingFee;
}

public String getClientId() {
return clientId;
}

public void setClientId(String clientId) {
this.clientId = clientId;
}

public Date getTransactionDate() {
return transactionDate;
}

public void setTransactionDate(Date transactionDate) {
this.transactionDate = transactionDate;
}

public String getTransactionType() {
return transactionType;
}

public void setTransactionType(String transactionType) {
this.transactionType = transactionType;
}

public boolean isPriorityFlag() {
return priorityFlag;
}

public void setPriorityFlag(boolean priorityFlag) {
this.priorityFlag = priorityFlag;
}

public Double getProcessingFee() {
return processingFee;
}

public void setProcessingFee(Double processingFee) {
this.processingFee = processingFee;
}

}