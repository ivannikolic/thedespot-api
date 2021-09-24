package rs.thedespot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/whois")
public class WhoIsController {

    @GetMapping
    public ResponseEntity<?> whois(@RequestParam String domain) {
        return ResponseEntity.ok("Hello," + domain);
    }

}
