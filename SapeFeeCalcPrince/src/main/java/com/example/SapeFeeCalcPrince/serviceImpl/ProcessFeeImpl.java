package com.example.SapeFeeCalcPrince.serviceImpl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.SapeFeeCalcPrince.Utils.CommonConstants;
import com.example.SapeFeeCalcPrince.dto.Client;
import com.example.SapeFeeCalcPrince.dto.Invoice;
import com.example.SapeFeeCalcPrince.service.ProcessFee;

@Service
public class ProcessFeeImpl implements ProcessFee {

	@Override
	public List<Invoice> calculateProcessingFees(MultipartFile file) {
		List<Invoice> transactionInvoice = new ArrayList<>();
		try {
			XSSFWorkbook excelBook = new XSSFWorkbook(file.getInputStream());
			XSSFSheet sheet = excelBook.getSheetAt(0);
			XSSFRow row;
			Client client = new Client();
			Integer rowNum = 1;
			Map<Client, Double> clientTransaction = new HashMap<>();
			while (sheet.getRow(rowNum) != null) {
				rowNum++;
			}
			for (int rowIndex = 1; rowIndex < rowNum; rowIndex++) {
				row = sheet.getRow(rowIndex);
				client = createClientObject(row);
				processTransaction(client, clientTransaction);
			}
			clientTransaction.keySet().forEach(cl -> {
				Invoice invoice = new Invoice(cl.getClientId(), cl.getTransactionDate(), cl.getTransactionType(),
						cl.isPriority(), clientTransaction.get(cl));
				transactionInvoice.add(invoice);
			});
		} catch (IOException ioException) {
			// catch an exception
		}
		return transactionInvoice;
	}

	private void processTransaction(Client client, Map<Client, Double> clientTransaction) {
		if (clientTransaction.containsKey(client)) {
			Double processingFee = clientTransaction.get(client) + 300.0;
			clientTransaction.put(client, processingFee);
		} else {
			calculateIntraDayFees(client, clientTransaction);
		}
	}

	private static void calculateIntraDayFees(Client client, Map<Client, Double> clientTransaction) {
		Double processingFee = checkPriority(client);
		switch (client.getTransactionType()) {
		case CommonConstants.BUY:
			if (checkForIntraDay(client, clientTransaction, CommonConstants.SELL)) {
				clientTransaction.put(client, processingFee + 10);
			} else {
				clientTransaction.put(client, processingFee);
			}
			break;
		case CommonConstants.SELL:
			if (checkForIntraDay(client, clientTransaction, CommonConstants.BUY)) {
				clientTransaction.put(client, processingFee + 10);
			} else {
				clientTransaction.put(client, processingFee);
			}
			break;
		case CommonConstants.DEPOSIT:
			if (checkForIntraDay(client, clientTransaction, CommonConstants.WITHDRAW)) {
				clientTransaction.put(client, processingFee + 10);
			} else {
				clientTransaction.put(client, processingFee);
			}
			break;
		case CommonConstants.WITHDRAW:
			if (checkForIntraDay(client, clientTransaction, CommonConstants.DEPOSIT)) {
				clientTransaction.put(client, processingFee + 10);
			} else {
				clientTransaction.put(client, processingFee);
			}
			break;
		default:
			break;
		}
	}

	private static boolean checkForIntraDay(Client client, Map<Client, Double> clientTransaction, String oppTransType) {
		Client cl = new Client(client.getClientId(), client.getTransactionDate(), oppTransType, client.isPriority(),
				client.getSecurityId());
		cl.setTransactionType(oppTransType);
		if (clientTransaction.containsKey(cl)) {
			clientTransaction.put(cl, clientTransaction.get(cl) + Double.valueOf(CommonConstants.INTRA_DAY_TRANS_FEE));
			return true;
		}
		return false;
	}

	private static Double checkPriority(Client client) {
		if (client.isPriority()) {
			return Double.valueOf(CommonConstants.PRIORITY_TRANSACTION_FEE) + client.getMarketValue();
		} else {
			if (client.getTransactionType().equals(CommonConstants.SELL)
					|| client.getTransactionType().equals(CommonConstants.SELL)) {
				return Double.valueOf(CommonConstants.NORMAL_TRANSACTION_SELL_FEE) + client.getMarketValue();
			} else {
				return Double.valueOf(CommonConstants.NORMAL_TRANSACTION_BUY_FEE) + client.getMarketValue();
			}
		}
	}

	private Client createClientObject(XSSFRow row) {
		Client client = new Client();
		client.setClientId(row.getCell(1).toString());
		client.setExtTransId(row.getCell(0).toString());
		client.setMarketValue(Double.valueOf(row.getCell(5).toString()));
		client.setPriority(getBoolean(row.getCell(6).toString()));
		client.setSecurityId(row.getCell(2).toString());
		client.setTransactionDate(convertStringToDate(row.getCell(4).toString()));
		client.setTransactionType(row.getCell(3).toString());
		return client;
	}

	private Date convertStringToDate(String transDate) {
		try {
			if (transDate.contains("/")) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date date = formatter.parse(transDate);
				return date;
			} else {
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
				Date date = formatter.parse(transDate);
				return date;
			}
		} catch (ParseException parseException) {

		}
		return null;
	}

	private boolean getBoolean(String priority) {
		if (priority.equals("Y")) {
			return true;
		}
		return false;
	}
}
