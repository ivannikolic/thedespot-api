package rs.thedespot.service;

import org.springframework.stereotype.Service;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.NSRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.Type;
import rs.thedespot.model.DnsInfo;

import java.net.InetAddress;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DnsService {

    public DnsInfo resolveAddress(String domainName) {
        var nameServers = List.<String>of();
        try {
            Record[] records = new Lookup(domainName, Type.NS).run();
            nameServers = Stream.of(records).map(r -> (NSRecord) r)
                    .map(NSRecord::getTarget)
                    .map(r -> r.toString(true))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String hostAddress = null;
        try {
            InetAddress address = InetAddress.getByName(domainName);
            hostAddress = address.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new DnsInfo().setHostAddress(hostAddress).setNameServers(nameServers);
    }
}
