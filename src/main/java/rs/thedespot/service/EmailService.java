package rs.thedespot.service;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Emailv31;
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
        MailjetRequest request;
        MailjetResponse response;
        request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "ivan.nikolic.dev@gmail.com")
                                        .put("Name", "RNIDS"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", emailTo)
                                                .put("Name", "User")))
                                .put(Emailv31.Message.TEMPLATEID, 3201654)
                                .put(Emailv31.Message.TEMPLATELANGUAGE, true)
                                .put(Emailv31.Message.SUBJECT, "Domen {{var:domain_name}} je sada slobodan")
                                .put(Emailv31.Message.VARIABLES, new JSONObject()
                                        .put("domain_name", domainName))));
        response = mailjetClient.post(request);
        System.out.println(response.getStatus());
        System.out.println(response.getData());
    }

}
