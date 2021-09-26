package rs.thedespot.lookup;

import rs.thedespot.model.DomainStatus;
import rs.thedespot.model.LookupResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class MarnetMkProvider extends LookupProvider {

    @Override
    protected String getLookupURL() {
        return "whois.marnet.mk";
    }

    @Override
    protected LookupResponse parseResponse(String response) {
        System.out.println(response);

        if (response.contains("No entries found")) {
            return new LookupResponse().setDomainStatus(DomainStatus.NotRegistered);
        }

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd.MM.yyyyHH:mm:ss");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        String[] lines = response.split("\\r\\n");

        ZonedDateTime createdDate = Stream.of(lines)
                .filter(l -> l.startsWith("registered:"))
                .map(l -> l.replace("registered:", "").replace(" ", ""))
                .map(d -> LocalDateTime.parse(d, formatter1).atZone(ZoneId.of("CET")))
                .findFirst()
                .get();

        ZonedDateTime expirationDate = Stream.of(lines)
                .filter(l -> l.startsWith("expire:"))
                .map(l -> l.replace("expire:", "").replace(" ", ""))
                .map(d -> LocalDate.parse(d, formatter2).atStartOfDay(ZoneId.of("CET")))
                .findFirst()
                .get();

        //registered:   11.02.2010 13:00:00
        //changed:      19.03.2019 11:34:38
        //expire:       11.02.2022

        return new LookupResponse()
                .setDomainStatus(DomainStatus.Active)
                .setRegistrationDate(createdDate)
                .setExpirationDate(expirationDate)
                ;
    }

}
