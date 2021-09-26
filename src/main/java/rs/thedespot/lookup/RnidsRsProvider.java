package rs.thedespot.lookup;

import rs.thedespot.model.DomainStatus;
import rs.thedespot.model.LookupResponse;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class RnidsRsProvider extends LookupProvider {

    @Override
    protected String getLookupURL() {
        return "whois.rnids.rs";
    }

    @Override
    protected LookupResponse parseResponse(String response) {
        if (response.startsWith("%ERROR:103: Domain is not registered")) {
            return new LookupResponse()
                    .setDomainStatus(DomainStatus.NotRegistered);
        }
        if (response.contains("This domain is reserved")) {
            return new LookupResponse()
                    .setDomainStatus(DomainStatus.Reserved);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        String statusString = response.substring(response.indexOf("Domain status: ") + 15, response.indexOf(" https://www.rnids.rs/en/domain-name-status-codes"));

        String registrationDateString = response.substring(response.indexOf("Registration date: ") + 19, response.indexOf("\r\nModification date: "));
        ZonedDateTime registrationDate = LocalDateTime.parse(registrationDateString, formatter).atZone(ZoneId.of("CET"));

        String expirationDateString = response.substring(response.indexOf("Expiration date: ") + 17, response.indexOf("\r\nConfirmed: "));
        ZonedDateTime expirationDate = LocalDateTime.parse(expirationDateString, formatter).atZone(ZoneId.of("CET"));

        return new LookupResponse()
                .setDomainStatus(DomainStatus.valueOf(statusString))
                .setRegistrationDate(registrationDate)
                .setExpirationDate(expirationDate);
    }
}
