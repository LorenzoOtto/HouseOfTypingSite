package houseoftyping.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {

	private static Properties props = new Properties();
	private static ResourceBundle rb = ResourceBundle.getBundle("mail");

	/**
	 * Utility method to send simple HTML email
	 * 
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	public static void sendWelcomeMail(String toEmail, ArrayList<String> names, String firstName)
			throws MessagingException {
		try {
			MimeMessage msg = new MimeMessage(getMailSession());
			msg.setFrom(new InternetAddress(rb.getString("mail.username"), "Info House of Typing"));

			String nameMessage = "";
			for (String name : names) {
				nameMessage += "<tr> <td width='30%' style='width: 30.0%; background: #01ACE4; padding: 3.75pt 3.75pt 3.75pt 3.75pt'> <p class='MsoNormal'> <span style='color: white'>Voor- en achternaam</span> </p> </td> <td style='padding: 3.75pt 3.75pt 3.75pt 3.75pt'> <p class='MsoNormal'> <span style='color: #0186B1'>"
						+ name + "</span> </p> </td></tr>";
			}
			msg.setSubject("Uw inschrijving is ontvangen!");

			String message = "<p class='MsoNormal' align='center' style='text-align: center'> <a href='https://www.thehouseoftyping.nl/' rel='noopener noreferrer'><span style='text-decoration: none'><img border='0' width='100' height='100' id='_x0000_i1025' src='https://www.thehouseoftyping.nl/mail/assets/logo-eco-house-of-typing.png' alt='The House of Typing'></span></a> </p> <div align='center'> <table class='MsoNormalTable' width='600' style='width: 450.0pt; border-collapse: collapse'> <tbody> <tr> <td style='background: white; padding: 15.0pt 15.0pt 15.0pt 15.0pt'> <p style='line-height: 15.0pt; mso-line-height-rule: exactly'> <span style='font-size: 11.5pt; font-family: & amp; amp; quot; Arial & amp; quot; ,& amp; amp; quot; sans-serif & amp; quot;; color: #333333'>Hallo "
					+ firstName
					+ ",</span> </p> <p style='line-height: 15.0pt; mso-line-height-rule: exactly'> <span style='font-size: 11.5pt; font-family: & amp; amp; quot; Arial & amp; quot; ,& amp; amp; quot; sans-serif & amp; quot;; color: #333333'>Bedankt voor je inschrijving via onze website! <br> Je ontvangt van ons een welkomsmail met daarin de inlogcodes!  </span> </p> <h2 style='margin: 0cm; margin-bottom: .0001pt; line-height: 15.0pt; mso-line-height-rule: exactly'> <span style='font-size: 13.0pt; font-family: & amp; amp; quot; Arial & amp; quot; ,& amp; amp; quot; sans-serif & amp; quot;; color: #0186B1'>Ingeschreven deelnemers</span> </h2> <table class='MsoNormalTable' border='0' cellspacing='0' cellpadding='0' align='left' width='100%' style='width: 100.0%; border-collapse: collapse; margin-left: -2.25pt; margin-right: -2.25pt'> <tbody>"
					+ nameMessage
					+ " </tbody> </table> <p style='line-height: 15.0pt; mso-line-height-rule: exactly'> <span style='font-size: 11.5pt; font-family: & amp; amp; quot; Arial & amp; quot; ,& amp; amp; quot; sans-serif & amp; quot;; color: #333333'>Mocht je nog vragen hebben, dan beantwoorden wij deze uiteraard graag,</span> </p> <p style='line-height: 15.0pt; mso-line-height-rule: exactly'> <span style='font-size: 11.5pt; font-family: & amp; amp; quot; Arial & amp; quot; ,& amp; amp; quot; sans-serif & amp; quot;; color: #333333'>Vriendelijke groet, <br> </span><b><span style='font-size: 11.5pt; font-family: & amp; amp; quot; Arial & amp; quot; ,& amp; amp; quot; sans-serif & amp; quot;; color: #01ACE4'>The House of Typing</span></b><span style='font-size: 11.5pt; font-family: & amp; amp; quot; Arial & amp; quot; ,& amp; amp; quot; sans-serif & amp; quot;; color: #333333'></span> </p> <p style='margin-top: 7.5pt; line-height: 15.0pt; mso-line-height-rule: exactly'> <b><span style='font-size: 11.5pt; font-family: & amp; amp; quot; Arial & amp; quot; ,& amp; amp; quot; sans-serif & amp; quot;; color: #01ACE4'>T.</span></b><span style='font-size: 11.5pt; font-family: & amp; amp; quot; Arial & amp; quot; ,& amp; amp; quot; sans-serif & amp; quot;; color: #333333'> 0165 - 31 88 32<br> </span><b><span style='font-size: 11.5pt; font-family: & amp; amp; quot; Arial & amp; quot; ,& amp; amp; quot; sans-serif & amp; quot;; color: #01ACE4'>E.</span></b><span style='font-size: 11.5pt; font-family: & amp; amp; quot; Arial & amp; quot; ,& amp; amp; quot; sans-serif & amp; quot;; color: #333333'> <a href='mailto:info@econet.nl' onclick='return false;'>info@econet.nl</a> </span> </p> </td> </tr> </tbody> </table> </div>";
			msg.setText(message, "utf-8", "html");

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			Transport.send(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sendRegistrationsToSchool(String toEmail, ArrayList<String> names, int courseSize)
			throws MessagingException {
		try {
			MimeMessage msg = new MimeMessage(getMailSession());
			msg.setFrom(new InternetAddress(rb.getString("mail.username"), "Info House of Typing"));

			String nameMessage = "<tr> <td width='30%' style='width: 30.0%; background: #01ACE4; padding: 3.75pt 3.75pt 3.75pt 3.75pt'> <p class='MsoNormal'> <span style='color: white'>Voor- en achternaam</span> </p> </td> <td style='padding: 3.75pt 3.75pt 3.75pt 3.75pt'> <p class='MsoNormal'> <span style='color: #0186B1'>"
					+ names.get(0) + "</span> </p> </td></tr>";
			msg.setSubject("Wij hebben een inschrijving voor de typetraining op uw school via onze site ontvangen.");

			String message = "<p class='MsoNormal' align='center' style='text-align: center'> <a href='https://www.thehouseoftyping.nl/' rel='noopener noreferrer'><span style='text-decoration: none'><img border='0' width='100' height='100' id='_x0000_i1025' src='https://www.thehouseoftyping.nl/mail/assets/logo-eco-house-of-typing.png' alt='The House of Typing'></span></a> </p> <div align='center'> <table class='MsoNormalTable' width='600' style='width: 450.0pt; border-collapse: collapse'> <tbody> <tr> <td style='background: white; padding: 15.0pt 15.0pt 15.0pt 15.0pt'> <p style='line-height: 15.0pt; mso-line-height-rule: exactly'> <span style='font-size: 11.5pt; font-family: & amp; amp; quot; Arial & amp; quot; ,& amp; amp; quot; sans-serif & amp; quot;; color: #333333'>L.S.</span> </p> <p style='line-height: 15.0pt; mso-line-height-rule: exactly'> <span style='font-size: 11.5pt; font-family: & amp; amp; quot; Arial & amp; quot; ,& amp; amp; quot; sans-serif & amp; quot;; color: #333333'>Onderstaand treft u een inschrijving aan voor de typetraining op uw school. De leerling(en) heeft zich zojuist via onze site ingeschreven.</span> </p> <h2 style='margin: 0cm; margin-bottom: .0001pt; line-height: 15.0pt; mso-line-height-rule: exactly'> <span style='font-size: 13.0pt; font-family: & amp; amp; quot; Arial & amp; quot; ,& amp; amp; quot; sans-serif & amp; quot;; color: #0186B1'>Ingeschreven deelnemers</span> </h2> <table class='MsoNormalTable' border='0' cellspacing='0' cellpadding='0' align='left' width='100%' style='width: 100.0%; border-collapse: collapse; margin-left: -2.25pt; margin-right: -2.25pt'> <tbody>"
					+ nameMessage
					+ " </tbody> </table> <p style='line-height: 15.0pt; mso-line-height-rule: exactly'> <span style='font-size: 11.5pt; font-family: & amp; amp; quot; Arial & amp; quot; ,& amp; amp; quot; sans-serif & amp; quot;; color: #333333'></span> </p> <br> <p style='line-height: 15.0pt; mso-line-height-rule: exactly'> <span style='font-size: 11.5pt; font-family: Arial, sans-serif; color: #333333'>Met deze inschrijving bestaat de cursusgroep nu uit "
					+ ++courseSize
					+ " persoon/personen. <br> </span> </p> <p style='line-height: 15.0pt; mso-line-height-rule: exactly'> <span style='font-size: 11.5pt; font-family: & amp; amp; quot; Arial & amp; quot; ,& amp; amp; quot; sans-serif & amp; quot;; color: #333333'>Vriendelijke groet, <br> </span><b><span style='font-size: 11.5pt; font-family: & amp; amp; quot; Arial & amp; quot; ,& amp; amp; quot; sans-serif & amp; quot;; color: #01ACE4'>The House of Typing</span></b><span style='font-size: 11.5pt; font-family: & amp; amp; quot; Arial & amp; quot; ,& amp; amp; quot; sans-serif & amp; quot;; color: #333333'></span> </p> <p style='margin-top: 7.5pt; line-height: 15.0pt; mso-line-height-rule: exactly'> <b><span style='font-size: 11.5pt; font-family: & amp; amp; quot; Arial & amp; quot; ,& amp; amp; quot; sans-serif & amp; quot;; color: #01ACE4'>T.</span></b><span style='font-size: 11.5pt; font-family: & amp; amp; quot; Arial & amp; quot; ,& amp; amp; quot; sans-serif & amp; quot;; color: #333333'> 0165 - 31 88 32<br> </span><b><span style='font-size: 11.5pt; font-family: & amp; amp; quot; Arial & amp; quot; ,& amp; amp; quot; sans-serif & amp; quot;; color: #01ACE4'>E.</span></b><span style='font-size: 11.5pt; font-family: & amp; amp; quot; Arial & amp; quot; ,& amp; amp; quot; sans-serif & amp; quot;; color: #333333'> <a href='mailto:info@econet.nl' onclick='return false;'>info@econet.nl</a> </span> </p> </td> </tr> </tbody> </table> </div>";
			msg.setText(message, "utf-8", "html");

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			Transport.send(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sendWaitingListToSchool(String toEmail, ArrayList<String> names, String schoolName,
			int courseSize) throws MessagingException {
		try {
			MimeMessage msg = new MimeMessage(getMailSession());
			msg.setFrom(new InternetAddress(rb.getString("mail.username"), "Info House of Typing"));

			String nameMessage = "<tr> <td width='30%' style='width: 30.0%; background: #01ACE4; padding: 3.75pt 3.75pt 3.75pt 3.75pt'> <p class='MsoNormal'> <span style='color: white'>Voor- en achternaam</span> </p> </td> <td style='padding: 3.75pt 3.75pt 3.75pt 3.75pt'> <p class='MsoNormal'> <span style='color: #0186B1'>"
					+ names.get(0) + "</span> </p> </td></tr>";
			String schoolMessage = "<tr> <td width='30%' style='width: 30.0%; background: #01ACE4; padding: 3.75pt 3.75pt 3.75pt 3.75pt'> <p class='MsoNormal'> <span style='color: white'>Voor de training van</span> </p> </td> <td style='padding: 3.75pt 3.75pt 3.75pt 3.75pt'> <p class='MsoNormal'> <span style='color: #0186B1'>"
					+ schoolName + "</span> </p> </td></tr>";

			msg.setSubject("Nieuwe inschrijving op de wachtlijst voor de training op uw school.");

			String message = "<p class='MsoNormal' align='center' style='text-align: center'> <a href='https://www.thehouseoftyping.nl/' rel='noopener noreferrer'><span style='text-decoration: none'><img border='0' width='100' height='100' id='_x0000_i1025' src='https://www.thehouseoftyping.nl/mail/assets/logo-eco-house-of-typing.png' alt='The House of Typing'></span></a> </p><div align='center'> <table class='MsoNormalTable' width='600' style='width: 450.0pt; border-collapse: collapse'> <tbody> <tr> <td style='background: white; padding: 15.0pt 15.0pt 15.0pt 15.0pt'> <p style='line-height: 15.0pt; mso-line-height-rule: exactly'> <span style='font-size: 11.5pt; font-family: Arial, sans-serif; color: #333333'>L.S.</span> </p> <p style='line-height: 15.0pt; mso-line-height-rule: exactly'> <span style='font-size: 11.5pt; font-family: Arial, sans-serif; color: #333333'>Onderstaand treft u een inschrijving aan voor de typetraining op uw school. Het maximale aantal inschrijvingen voor de cursusgroep is echter bereikt. De hier onder genoemde leerling(en), die zich zojuist heeft proberen in te schrijven, is op de wachtlijst gezet.</span> </p> <h2 style='margin: 0cm; margin-bottom: .0001pt; line-height: 15.0pt; mso-line-height-rule: exactly'> <span style='font-size: 13.0pt; font-family: Arial, sans-serif; color: #0186B1'>De volgende leerling</span> </h2> <table class='MsoNormalTable' border='0' cellspacing='0' cellpadding='0' align='left' width='100%' style='width: 100.0%; border-collapse: collapse; margin-left: -2.25pt; margin-right: -2.25pt'> <tbody>"
					+ nameMessage
					+ " </tbody> </table> <table class='MsoNormalTable' border='0' cellspacing='0' cellpadding='0' align='left' width='100%' style='width: 100.0%; border-collapse: collapse; margin-left: -2.25pt; margin-right: -2.25pt'> <tbody>"
					+ schoolMessage
					+ " </tbody> </table> <br> <br> <p style='line-height: 15.0pt; mso-line-height-rule: exactly'><span style='font-size: 13pt; font-family: Arial, sans-serif; color: #333333'> Is op de wachtlijst gezet en heeft daar van ons een bevestiging over ontvangen.</span> </p> <br> <p style='line-height: 15.0pt; mso-line-height-rule: exactly'> <span style='font-size: 11.5pt; font-family: Arial, sans-serif; color: #333333'>Met deze inschrijving bestaat de wachtlijst nu uit "
					+ courseSize
					+ " persoon/personen. <br> </span> </p><p style='line-height: 15.0pt; mso-line-height-rule: exactly'> <span style='font-size: 11.5pt; font-family: Arial, sans-serif; color: #333333'>Vriendelijke groet, <br> </span><b><span style='font-size: 11.5pt; font-family: Arial, sans-serif; color: #01ACE4'>The House of Typing</span></b><span style='font-size: 11.5pt; font-family: Arial, sans-serif; color: #333333'></span> </p> <p style='margin-top: 7.5pt; line-height: 15.0pt; mso-line-height-rule: exactly'> <b><span style='font-size: 11.5pt; font-family: Arial, sans-serif; color: #01ACE4'>T.</span></b><span style='font-size: 11.5pt; font-family: Arial, sans-serif; color: #333333'> 0165 - 31 88 32<br> </span><b><span style='font-size: 11.5pt; font-family: Arial, sans-serif; color: #01ACE4'>E.</span></b><span style='font-size: 11.5pt; font-family: Arial, sans-serif; color: #333333'> <a href='mailto:info@econet.nl' onclick='return false;'>info@econet.nl</a> </span> </p> </td> </tr> </tbody> </table> </div>";
			msg.setText(message, "utf-8", "html");

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			Transport.send(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sendWaitingListMail(String toEmail, ArrayList<String> names, String firstName)
			throws MessagingException {
		try {
			MimeMessage msg = new MimeMessage(getMailSession());
			msg.setFrom(new InternetAddress(rb.getString("mail.username"), "Info House of Typing"));

			String nameMessage = "<tr> <td width='30%' style='width: 30.0%; background: #01ACE4; padding: 3.75pt 3.75pt 3.75pt 3.75pt'> <p class='MsoNormal'> <span style='color: white'>Voor- en achternaam</span> </p> </td> <td style='padding: 3.75pt 3.75pt 3.75pt 3.75pt'> <p class='MsoNormal'> <span style='color: #0186B1'>"
					+ names.get(0) + "</span> </p> </td></tr>";

			String extraStudentsMessage = "";
			if (names.size() > 1) {
				extraStudentsMessage = "Naast jou heeft ook een familielid van jou aangegeven dat zij/hij de cursus wil gaan doen. Zij/hij doet de cursus thuis en zal dan ook gewoon een mail gaan krijgen met de inlogcodes zodat begonnen kan worden met de training.";
			}

			msg.setSubject("Je bent op de wachtlijst geplaatst!");

			String message = "<p class='MsoNormal' align='center' style='text-align: center'> <a href='https://www.thehouseoftyping.nl/' rel='noopener noreferrer'><span style='text-decoration: none'><img border='0' width='100' height='100' id='_x0000_i1025' src='https://www.thehouseoftyping.nl/mail/assets/logo-eco-house-of-typing.png' alt='The House of Typing'></span></a> </p><div align='center'> <table class='MsoNormalTable' width='600' style='width: 450.0pt; border-collapse: collapse'> <tbody> <tr> <td style='background: white; padding: 15.0pt 15.0pt 15.0pt 15.0pt'> <p style='line-height: 15.0pt; mso-line-height-rule: exactly'> <span style='font-size: 11.5pt; font-family: Arial, sans-serif; color: #333333'>Hallo "
					+ firstName
					+ "</span> </p> <p style='line-height: 15.0pt; mso-line-height-rule: exactly'> <span style='font-size: 11.5pt; font-family: Arial, sans-serif; color: #333333'>Wat fijn dat je hebt aangegeven te willen leren typen. Helaas is de groep waarvoor jij je wilde inschrijven al vol. Je bent op de wachtlijst geplaatst en we hopen dat er straks toch nog een cursusplaats beschikbaar komt zodat je alsnog mee kunt doen. Als dat gebeurt nemen we meteen contact met jou op. </span> </p> <h2 style='margin: 0cm; margin-bottom: .0001pt; line-height: 15.0pt; mso-line-height-rule: exactly'> <span style='font-size: 13.0pt; font-family: Arial, sans-serif; color: #0186B1'>Op de wachtlijst staat</span> </h2> <table class='MsoNormalTable' border='0' cellspacing='0' cellpadding='0' align='left' width='100%' style='width: 100.0%; border-collapse: collapse; margin-left: -2.25pt; margin-right: -2.25pt'> <tbody>"
					+ nameMessage
					+ " </tbody> </table> <p style='line-height: 15.0pt; mso-line-height-rule: exactly'><span style='font-size: 11.5pt; font-family: Arial, sans-serif; color: #333333'>"
					+ extraStudentsMessage
					+ "</span></p> <p style='line-height: 15.0pt; mso-line-height-rule: exactly'> <span style='font-size: 11.5pt; font-family: Arial, sans-serif; color: #333333'>Mocht je nog vragen hebben, dan beantwoorden wij deze uiteraard graag,</span> </p> <p style='line-height: 15.0pt; mso-line-height-rule: exactly'> <span style='font-size: 11.5pt; font-family: Arial, sans-serif; color: #333333'>Vriendelijke groet, <br> </span><b><span style='font-size: 11.5pt; font-family: Arial, sans-serif; color: #01ACE4'>The House of Typing</span></b><span style='font-size: 11.5pt; font-family: Arial, sans-serif; color: #333333'></span> </p> <p style='margin-top: 7.5pt; line-height: 15.0pt; mso-line-height-rule: exactly'> <b><span style='font-size: 11.5pt; font-family: Arial, sans-serif; color: #01ACE4'>T.</span></b><span style='font-size: 11.5pt; font-family: Arial, sans-serif; color: #333333'> 0165 - 31 88 32<br> </span><b><span style='font-size: 11.5pt; font-family: Arial, sans-serif; color: #01ACE4'>E.</span></b><span style='font-size: 11.5pt; font-family: Arial, sans-serif; color: #333333'> <a href='mailto:info@econet.nl' onclick='return false;'>info@econet.nl</a> </span> </p> </td> </tr> </tbody> </table> </div>";
			msg.setText(message, "utf-8", "html");

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			Transport.send(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Session getMailSession() throws IOException {
		final String fromEmail = rb.getString("mail.username"); // requires valid mail
		final String password = rb.getString("mail.password"); // correct password for mail
		props.put("mail.smtp.host", rb.getString("mail.smtp.host")); // SMTP Host
		props.put("mail.smtp.port", rb.getString("mail.smtp.port")); // TLS Port
		props.put("mail.smtp.auth", rb.getString("mail.smtp.auth")); // enable authentication
//		props.put("mail.smtp.starttls.enable", rb.getString("mail.smtp.starttls.enable")); // enable STARTTLS
		props.put("mail.smtp.socketFactory.port", rb.getString("mail.smtp.socketFactory.port"));
		props.put("mail.smtp.socketFactory.class", rb.getString("mail.smtp.socketFactory.class"));
		// create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		Session session = Session.getInstance(props, auth);
		return session;
	}

}