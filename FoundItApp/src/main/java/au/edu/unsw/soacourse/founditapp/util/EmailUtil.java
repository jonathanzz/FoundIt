package au.edu.unsw.soacourse.founditapp.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EmailUtil {

	public static void sendAlert(String result, String e) {
		SimpleEmail email = new SimpleEmail();
		String myUserName = "zhuqihui90@163.com";
		String Password = "Zqh199065";

		// String sendTo = Email;
		StringBuffer sb = new StringBuffer();
		sb.append("Dear " + e + "\n");
		sb.append("The mail is from FoundIt, this is the detail of the jobs from your job alert.\n\n");
		sb.append(result + "\n\n");
		sb.append("Thanks for using our website.");
		try {
			email.setHostName("smtp.163.com");

			email.setAuthentication(myUserName, Password);
			email.setSSLOnConnect(true);
			email.setFrom(myUserName);
			email.setSubject("Register Confirmation");

			String s = sb.toString();
			email.setMsg(s);
			email.addTo(e);
			email.send();
		} catch (EmailException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

	}
}
