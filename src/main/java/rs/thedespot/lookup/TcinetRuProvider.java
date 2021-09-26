package rs.thedespot.lookup;

import rs.thedespot.model.DomainStatus;
import rs.thedespot.model.LookupResponse;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Stream;

public class TcinetRuProvider extends LookupProvider {

    @Override
    protected String getLookupURL() {
        return "whois.tcinet.ru";
    }

    @Override
    protected LookupResponse parseResponse(String response) {
        if (response.contains("No entries found for the selected source")) {
            return new LookupResponse().setDomainStatus(DomainStatus.NotRegistered);
        }

        String[] lines = response.split("\\n");

        ZonedDateTime createdDate = Stream.of(lines)
                .filter(l -> l.startsWith("created:"))
                .map(l -> l.replace("created:", "").replace(" ", ""))
                .map(ZonedDateTime::parse)
                .findFirst()
                .get();

        LocalDate freeDate = Stream.of(lines)
                .filter(l -> l.startsWith("free-date:"))
                .map(l -> l.replace("free-date:", "").replace(" ", ""))
                .map(LocalDate::parse)
                .findFirst()
                .get();

        return new LookupResponse()
                .setDomainStatus(DomainStatus.Active)
                .setRegistrationDate(createdDate)
                .setExpirationDate(freeDate.atStartOfDay(ZoneId.systemDefault()))
                ;
    }

}
