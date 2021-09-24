package rs.thedespot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.thedespot.service.WhoIsException;
import rs.thedespot.service.WhoIsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/whois")
public class WhoIsController {

    private final WhoIsService whoIsService;

    @GetMapping
    public ResponseEntity<?> whois(@RequestParam String domain) {
        try {
            whoIsService.validateDomain(domain);
        } catch (WhoIsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        String response = whoIsService.resolveDomain(domain);

        return ResponseEntity.ok(response);
    }

}
