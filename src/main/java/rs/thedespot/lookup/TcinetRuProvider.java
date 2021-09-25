package rs.thedespot.lookup;

public class TcinetRuProvider extends LookupProvider {

    @Override
    protected String getLookupURL() {
        return "whois.tcinet.ru";
    }

}
