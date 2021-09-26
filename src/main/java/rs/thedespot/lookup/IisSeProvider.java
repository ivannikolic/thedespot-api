package rs.thedespot.lookup;

import rs.thedespot.model.DomainStatus;
import rs.thedespot.model.LookupResponse;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.stream.Stream;

public class IisSeProvider extends LookupProvider {

    @Override
    protected String getLookupURL() {
        return "whois.iis.se";
    }

    @Override
    protected LookupResponse parseResponse(String response) {
        if (response.contains("\" not found.")) {
            return new LookupResponse()
                    .setDomainStatus(DomainStatus.NotRegistered);
        }

        String[] lines = response.split("\\n");

        String state = Stream.of(lines)
                .filter(l -> l.startsWith("state:"))
                .map(l -> l.replace("state:", "").replace(" ", ""))
                .findFirst()
                .get();

        LocalDate createdDate = Stream.of(lines)
                .filter(l -> l.startsWith("expires:"))
                .map(l -> l.replace("expires:", "").replace(" ", ""))
                .map(LocalDate::parse)
                .findFirst()
                .get();

        LocalDate expirationDate = Stream.of(lines)
                .filter(l -> l.startsWith("created:"))
                .map(l -> l.replace("created:", "").replace(" ", ""))
                .map(LocalDate::parse)
                .findFirst()
                .get();

        return new LookupResponse()
                .setDomainStatus(DomainStatus.Active)
                .setRegistrationDate(createdDate.atStartOfDay(ZoneId.systemDefault()))
                .setExpirationDate(expirationDate.atStartOfDay(ZoneId.systemDefault()));
    }

}
