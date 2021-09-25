package rs.thedespot.lookup;

import rs.thedespot.model.DomainStatus;
import rs.thedespot.model.LookupResponse;

import java.time.ZonedDateTime;

public class RnidsRsProvider extends LookupProvider {

    @Override
    protected String getLookupURL() {
        return "whois.rnids.rs";
    }

    @Override
    protected LookupResponse parseResponse(String response) {
        return new LookupResponse()
                .setDomainStatus(DomainStatus.ACTIVE)
                .setRegistrationDate(ZonedDateTime.now().minusMonths(2))
                .setExpirationDate(ZonedDateTime.now().plusMonths(10));
    }
}
