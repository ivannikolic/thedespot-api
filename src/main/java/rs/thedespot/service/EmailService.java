package rs.thedespot.service;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Emailv31;
import com.mailjet.client.transactional.SendContact;
import com.mailjet.client.transactional.SendEmailsRequest;
import com.mailjet.client.transactional.TransactionalEmail;
import com.mailjet.client.transactional.response.SendEmailsResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final MailjetClient mailjetClient;

    @SneakyThrows
    public void sendEmail(String emailTo, String domainName) {
        TransactionalEmail message1 = TransactionalEmail
                .builder()
                .to(new SendContact(emailTo, "User"))
                .from(new SendContact("ivan.nikolic.dev@gmail.com", "RNIDS"))
                .htmlPart("Domen " + domainName + " koji ste zapamtili uskoro istice. Pozurite da ga rezervisete")
                .subject("Domen " + domainName + " uskoro istice")
//                .header("test-header-key", "test-value")
                .build();

        SendEmailsRequest request = SendEmailsRequest
                .builder()
                .message(message1)
                .build();

        SendEmailsResponse response = request.sendWith(mailjetClient);
        System.out.println(response);
    }

}
