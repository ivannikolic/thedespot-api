package rs.thedespot.lookup;

import rs.thedespot.model.DomainStatus;
import rs.thedespot.model.LookupResponse;

import java.time.ZonedDateTime;

public class VerisignGrsProvider extends LookupProvider {

    @Override
    protected String getLookupURL() {
        return "whois.verisign-grs.com";
    }

    @Override
    protected LookupResponse parseResponse(String response) {
        System.out.println(response);

        if (response.startsWith("No match for ")) {
            return new LookupResponse()
                    .setDomainStatus(DomainStatus.NotRegistered);
        }
        String statusString = response.substring(response.indexOf("Domain status: ") + 15, response.indexOf(" https://icann.org/epp"));

        String registrationDateString = response.substring(response.indexOf("Creation Date: ") + 15, response.indexOf("\r\n   Registry Expiry Date: "));
        ZonedDateTime registrationDate = ZonedDateTime.parse(registrationDateString);

        String expirationDateString = response.substring(response.indexOf("Registry Expiry Date: ") + 22, response.indexOf("\r\n   Registrar: "));
        ZonedDateTime expirationDate = ZonedDateTime.parse(expirationDateString);

        return new LookupResponse()
                .setDomainStatus(DomainStatus.Active)
                .setRegistrationDate(registrationDate)
                .setExpirationDate(expirationDate);
    }

}
