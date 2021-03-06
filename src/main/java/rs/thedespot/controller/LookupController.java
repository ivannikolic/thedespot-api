package rs.thedespot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.thedespot.model.DnsInfo;
import rs.thedespot.model.DomainStatus;
import rs.thedespot.model.LookupResponse;
import rs.thedespot.service.DnsService;
import rs.thedespot.service.WhoIsException;
import rs.thedespot.service.WhoIsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LookupController {

    private final WhoIsService whoIsService;
    private final DnsService dnsService;

    @GetMapping
    @RequestMapping({"/whois", "/lookup"})
    public ResponseEntity<?> lookup(@RequestParam(name = "domain_name") String domainName) {
        try {
            whoIsService.validateDomain(domainName);
        } catch (WhoIsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        LookupResponse response = whoIsService.resolveDomain(domainName);
        if (response.getDomainStatus() != DomainStatus.NotRegistered) {
            response.setDnsInfo(dnsService.resolveAddress(domainName));
        } else {
            response.setDnsInfo(new DnsInfo().setHostAddress(null).setNameServers(List.of()));
        }

        return ResponseEntity.ok(response);
    }

}
