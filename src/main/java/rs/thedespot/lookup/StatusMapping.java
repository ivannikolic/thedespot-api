package rs.thedespot.lookup;

import rs.thedespot.model.DomainStatus;

public class StatusMapping {

    //https://www.icann.org/resources/pages/epp-status-codes-2014-06-16-en
    public static DomainStatus mapIcannStatus(String statusString) {
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
