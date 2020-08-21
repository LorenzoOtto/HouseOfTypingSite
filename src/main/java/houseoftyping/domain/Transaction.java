package houseoftyping.domain;

import java.util.Optional;

import org.apache.log4j.BasicConfigurator;

import be.feelio.mollie.Client;
import be.feelio.mollie.ClientBuilder;
import be.feelio.mollie.data.common.Amount;
import be.feelio.mollie.data.common.Locale;
import be.feelio.mollie.data.payment.PaymentMethod;
import be.feelio.mollie.data.payment.PaymentRequest;
import be.feelio.mollie.data.payment.PaymentResponse;
import be.feelio.mollie.exception.MollieException;

public class Transaction {

	private Client client;
	public Transaction() {
		BasicConfigurator.configure();
		this.client = new ClientBuilder()
                .withApiKey("live_6kxgeafMR6HtqFyp4byxxf5gCftMht")
                .build();
	}
	
	public PaymentResponse getPaymentById(String id) {
		try {
			return client.payments().getPayment(id);
		} catch (MollieException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * value = how much they pay
	 * currency = currency of money, always EUR.
	 * description = what they see on their bank statement when paid
	 * redirectionURL = where they go after payment successful = www.inschrijventypecursus.nl/inschrijving-voltooid
	 * locale = language they pay in, always NL
	 * method = type of payment (Paypal, Creditcard, Ideal) always ideal.
	 * issuer = the bank user wants to pay with (Only for IDEAL) (ING, Rabobank, etc.)
	 * 
	 */
	public PaymentResponse createPayment(String value, String issuer) {
		PaymentRequest pr = new PaymentRequest();
		Amount amount = new Amount("EUR", value);
		pr.setAmount(amount);
		pr.setDescription("Typecursus The House Of Typing");
		pr.setRedirectUrl(Optional.ofNullable("https://inschrijventypecursus.nl/"));
//		pr.setRedirectUrl(Optional.ofNullable("http://localhost:8080/inschrijventypecursus/"));
		pr.setLocale(Optional.ofNullable(Locale.nl_NL));
		pr.setMethod(Optional.ofNullable(PaymentMethod.IDEAL));
		pr.setIssuer(Optional.ofNullable(issuer));
		try {
			return client.payments().createPayment(pr);
		} catch (MollieException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void close() {
		BasicConfigurator.resetConfiguration();
	}
}
