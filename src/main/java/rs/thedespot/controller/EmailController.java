package rs.thedespot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.thedespot.service.EmailService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<?> sendEmail(@RequestParam(name = "email_to") String emailTo,
                                       @RequestParam(name = "domain_name") String domainName) {
        emailService.sendEmail(emailTo, domainName);
        return ResponseEntity.ok().build();
    }


}
