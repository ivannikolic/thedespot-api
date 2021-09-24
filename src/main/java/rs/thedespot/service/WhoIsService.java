package rs.thedespot.service;

import lombok.SneakyThrows;
import org.apache.commons.net.whois.WhoisClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WhoIsService {

    private static final Map<String, String> DOMAIN_LOOKUP_URL_MAPPING = new HashMap<>() {
        {
            put(".rs", "whois.rnids.rs");
            put(".срб", "whois.rnids.rs");

            put(".ru", "whois.tcinet.ru");
            put(".рф", "whois.tcinet.ru");

            put(".mk", "whois.marnet.mk");
            put(".мкд", "whois.marnet.mk");

            put(".org", "whois.publicinterestregistry.net");
            put(".орг", "whois.publicinterestregistry.net");

            put(".com", "whois.verisign-grs.com");

            put(".ком", "whois.nic.ком");

            put(".net", "whois.verisign-grs.com");

            put(".uk", "whois.nic.uk");

            put(".se", "whois.iis.se");
        }
    };

    @SneakyThrows
    public String resolveDomain(String domain) {
        WhoisClient whois = new WhoisClient();
        try {
            String lookUpUrl = DOMAIN_LOOKUP_URL_MAPPING.get(extractExtension(domain));
            whois.connect(lookUpUrl);
            return whois.query(domain);
        } finally {
            whois.disconnect();
        }
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
