package rs.thedespot.lookup;

public class IisSeProvider extends LookupProvider {

    @Override
    protected String getLookupURL() {
        return "whois.iis.se";
    }

}
