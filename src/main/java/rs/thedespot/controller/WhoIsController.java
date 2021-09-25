package rs.thedespot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.thedespot.model.LookupResponse;
import rs.thedespot.service.WhoIsException;
import rs.thedespot.service.WhoIsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/whois")
public class WhoIsController {

    private final WhoIsService whoIsService;

    @GetMapping
    public ResponseEntity<?> whois(@RequestParam(name = "domain_name") String domainName) {
        try {
            whoIsService.validateDomain(domainName);
        } catch (WhoIsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        LookupResponse response = whoIsService.resolveDomain(domainName);

        return ResponseEntity.ok(response);
    }

}
