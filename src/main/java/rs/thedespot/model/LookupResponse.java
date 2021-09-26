package rs.thedespot.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LookupResponse {

    private String domainName;

    private DomainStatus domainStatus;

    private ZonedDateTime registrationDate;

    private ZonedDateTime expirationDate;

    private String ipAddress;

}
