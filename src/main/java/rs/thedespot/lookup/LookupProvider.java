package rs.thedespot.lookup;

import lombok.SneakyThrows;
import org.apache.commons.net.whois.WhoisClient;
import rs.thedespot.model.DomainStatus;
import rs.thedespot.model.LookupResponse;

import java.time.ZonedDateTime;

public abstract class LookupProvider {

    @SneakyThrows
    public LookupResponse lookup(String domainName) {
        WhoisClient whois = new WhoisClient();
        try {
            String lookUpUrl = getLookupURL();
            whois.connect(lookUpUrl);
            LookupResponse response = parseResponse(whois.query(domainName));
            response.setDomainName(domainName);
            return response;
        } finally {
            whois.disconnect();
        }
    }

    //TODO make abstract when everything is implemented
    protected LookupResponse parseResponse(String response) {
        return new LookupResponse()
                .setDomainStatus(DomainStatus.ACTIVE)
                .setRegistrationDate(ZonedDateTime.now().minusMonths(2))
                .setExpirationDate(ZonedDateTime.now().plusMonths(10));
    }

    protected abstract String getLookupURL();

}