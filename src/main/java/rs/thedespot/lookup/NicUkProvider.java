package rs.thedespot.lookup;

import rs.thedespot.model.DomainStatus;
import rs.thedespot.model.LookupResponse;

public class NicUkProvider extends LookupProvider {

    @Override
    protected String getLookupURL() {
        return "whois.nic.uk";
    }

    @Override
    protected LookupResponse parseResponse(String response) {
        if (response.contains("No entries found")) {
            return new LookupResponse().setDomainStatus(DomainStatus.NotRegistered);
        }
        return super.parseResponse(response);
    }

}
