package com.example.SapeFeeCalcPrince.dto;

import java.util.Date;

public class Client {

	private String extTransId;
	private String clientId;
	private Date transactionDate;
	private String transactionType;
	private boolean priorityFlag;
	private String securityId;
	private Double marketValue;

	public String getExtTransId() {
		return extTransId;
	}

	public void setExtTransId(String extTransId) {
		this.extTransId = extTransId;
	}

	public Client() {

	}

	public Client(String clientId, Date transactionDate, String transactionType, boolean priority, String securityId) {
		this.clientId = clientId;
		this.transactionDate = transactionDate;
		this.transactionType = transactionType;
		this.priorityFlag = priority;
		this.securityId = securityId;
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

	public boolean isPriority() {
		return priorityFlag;
	}

	public void setPriority(boolean priority) {
		this.priorityFlag = priority;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public Double getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(Double marketValue) {
		this.marketValue = marketValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result + (priorityFlag ? 1231 : 1237);
		result = prime * result + ((securityId == null) ? 0 : securityId.hashCode());
		result = prime * result + ((transactionDate == null) ? 0 : transactionDate.hashCode());
		result = prime * result + ((transactionType == null) ? 0 : transactionType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId))
			return false;
		if (priorityFlag != other.priorityFlag)
			return false;
		if (securityId == null) {
			if (other.securityId != null)
				return false;
		} else if (!securityId.equals(other.securityId))
			return false;
		if (transactionDate == null) {
			if (other.transactionDate != null)
				return false;
		} else if (!transactionDate.equals(other.transactionDate))
			return false;
		if (transactionType == null) {
			if (other.transactionType != null)
				return false;
		} else if (!transactionType.equals(other.transactionType))
			return false;
		return true;
	}

}