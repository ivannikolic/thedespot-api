package rs.thedespot.lookup;

import rs.thedespot.model.DomainStatus;
import rs.thedespot.model.LookupResponse;

import java.time.ZonedDateTime;

public class VerisignGrsProvider extends LookupProvider {

    @Override
    protected String getLookupURL() {
        return "whois.verisign-grs.com";
    }

    @Override
    protected LookupResponse parseResponse(String response) {
        System.out.println(response);

        if (response.startsWith("No match for ")) {
            return new LookupResponse()
                    .setDomainStatus(DomainStatus.NotRegistered);
        }
        String statusString = response.substring(response.indexOf("Domain Status: ") + 15, response.indexOf(" https://icann.org/epp"));

        String registrationDateString = response.substring(response.indexOf("Creation Date: ") + 15, response.indexOf("\r\n   Registry Expiry Date: "));
        ZonedDateTime registrationDate = ZonedDateTime.parse(registrationDateString);

        String expirationDateString = response.substring(response.indexOf("Registry Expiry Date: ") + 22, response.indexOf("\r\n   Registrar: "));
        ZonedDateTime expirationDate = ZonedDateTime.parse(expirationDateString);

        return new LookupResponse()
                .setDomainStatus(mapStatus(statusString))
                .setRegistrationDate(registrationDate)
                .setExpirationDate(expirationDate);
    }

    //https://www.icann.org/resources/pages/epp-status-codes-2014-06-16-en
    private DomainStatus mapStatus(String statusString) {
        switch (statusString) {
            case "addPeriod":
            case "ok":
                return DomainStatus.Active;
            case "autoRenewPeriod":
                return DomainStatus.AutoRenewPeriod;
            case "inactive":
                return DomainStatus.Inactive;
            case "pendingCreate":
                return DomainStatus.PendingCreate;
            case "pendingDelete":
                return DomainStatus.PendingDelete;
            case "pendingRenew":
                return DomainStatus.PendingRenew;
            case "pendingRestore":
                return DomainStatus.PendingRestore;
            case "pendingTransfer":
                return DomainStatus.PendingTransfer;
            case "pendingUpdate":
                return DomainStatus.PendingUpdate;
            case "redemptionPeriod":
                return DomainStatus.RedemptionPeriod;
            case "renewPeriod":
                return DomainStatus.RenewPeriod;
            case "serverDeleteProhibited":
                return DomainStatus.ServerDeleteProhibited;
            case "serverHold":
                return DomainStatus.ServerHold;
            case "serverRenewProhibited":
                return DomainStatus.ServerRenewProhibited;
            case "serverTransferProhibited":
                return DomainStatus.ServerTransferProhibited;
            case "serverUpdateProhibited":
                return DomainStatus.ServerUpdateProhibited;
            case "transferPeriod":
                return DomainStatus.TransferPeriod;
            case "clientDeleteProhibited":
                return DomainStatus.ClientDeleteProhibited;
            case "clientHold":
                return DomainStatus.ClientHold;
            case "clientRenewProhibited":
                return DomainStatus.ClientRenewProhibited;
            case "clientTransferProhibited":
                return DomainStatus.ClientTransferProhibited;
            case "clientUpdateProhibited":
                return DomainStatus.ClientUpdateProhibited;
        }
        return DomainStatus.Active;
    }


}
