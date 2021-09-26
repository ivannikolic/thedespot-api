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
            String rawData = whois.query(domainName);
            LookupResponse response = parseResponse(rawData);
            response.setDomainName(domainName);
            response.setWhoIsProvider(lookUpUrl);
            response.setWhoIsRawData(rawData);
            return response;
        } finally {
            whois.disconnect();
        }
    }

    //TODO make abstract when everything is implemented
    protected LookupResponse parseResponse(String response) {
        return new LookupResponse()
                .setDomainStatus(DomainStatus.Active)
                .setRegistrationDate(ZonedDateTime.now().minusMonths(14))
                .setExpirationDate(ZonedDateTime.now().plusMonths(10));
    }

    protected abstract String getLookupURL();

}
