package rs.thedespot.lookup;

public class VerisignGrsProvider extends LookupProvider {

    @Override
    protected String getLookupURL() {
        return "whois.verisign-grs.com";
    }

}
