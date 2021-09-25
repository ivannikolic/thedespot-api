package rs.thedespot.service;

import org.springframework.stereotype.Service;
import rs.thedespot.lookup.IisSeProvider;
import rs.thedespot.lookup.LookupProvider;
import rs.thedespot.lookup.MarnetMkProvider;
import rs.thedespot.lookup.NicComProvider;
import rs.thedespot.lookup.NicUkProvider;
import rs.thedespot.lookup.PublicInterestRegistryNetProvider;
import rs.thedespot.lookup.RnidsRsProvider;
import rs.thedespot.lookup.TcinetRuProvider;
import rs.thedespot.lookup.VerisignGrsProvider;
import rs.thedespot.model.LookupResponse;

import java.util.HashMap;
import java.util.Map;

@Service
public class WhoIsService {

    private static final Map<String, LookupProvider> DOMAIN_LOOKUP_URL_MAPPING = new HashMap<>() {
        {
            put(".rs", new RnidsRsProvider());
            put(".срб", new RnidsRsProvider());

            put(".ru", new TcinetRuProvider());
            put(".рф", new TcinetRuProvider());

            put(".mk", new MarnetMkProvider());
            put(".мкд", new MarnetMkProvider());

            put(".org", new PublicInterestRegistryNetProvider());
            put(".орг", new PublicInterestRegistryNetProvider());

            put(".com", new VerisignGrsProvider());

            put(".ком", new NicComProvider());

            put(".net", new VerisignGrsProvider());

            put(".uk", new NicUkProvider());

            put(".se", new IisSeProvider());
        }
    };

    public LookupResponse resolveDomain(String domainName) {
        LookupProvider lookupProvider = DOMAIN_LOOKUP_URL_MAPPING.get(extractExtension(domainName));

        return lookupProvider.lookup(domainName);
    }

    public void validateDomain(String domain) throws WhoIsException {
        String extension = extractExtension(domain);

        if (!DOMAIN_LOOKUP_URL_MAPPING.containsKey(extension)) {
            throw new WhoIsException("Domain that ends with " + extension + " can not be resolved");
        }
    }

    private String extractExtension(String domain) throws WhoIsException {
        if (!domain.contains(".")) {
            throw new WhoIsException("Domain needs to contain a dot (.)");
        }
        return domain.substring(domain.lastIndexOf("."));
    }

}
