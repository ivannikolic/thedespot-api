package rs.thedespot.lookup;

public class NicUkProvider extends LookupProvider {

    @Override
    protected String getLookupURL() {
        return "whois.nic.uk";
    }

}
