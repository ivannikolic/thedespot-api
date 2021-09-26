package rs.thedespot.service;

import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class DnsService {

    public String resolveAddress(String domainName) {
        try {
            InetAddress address = InetAddress.getByName(domainName);
            return address.getHostAddress();
        } catch (UnknownHostException e) {
            return null;
        }
    }
}
