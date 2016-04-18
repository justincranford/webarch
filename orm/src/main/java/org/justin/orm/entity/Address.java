package org.justin.orm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * IP address and hostname/FQDN pair, plus flags for static/dynamic and virtual/physical for both.
 * 
 * Examples:
 *     Address address = new Address("127.0.0.1", "localhost.localdomain", Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, null);
 *     Address address = new Address("192.168.101.37", "laptop", Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, null);		// Fixed hostname, DHCP address
 *     Address address = new Address("192.168.250.100", "mailserver", Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, null);	// Static IP and DNS reservations
 *     Address address = new Address("192.168.250.201", "stunintf1", Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, null);	// STUN server physical address, non-virtual hostname
 *     Address address = new Address("192.168.250.202", "stunintf2", Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, null);	// STUN server virtual address, virtual hostname
 *     Address address = new Address("10.20.30.45", "vip", Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, null);				// Static virtual address (VIP/DVIP)
 *     Address address = new Address("10.20.30.56", "virtual", Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, null);			// Dynamic virtual address (DVIP/DVIPA)
 * 
 * Minimum and maximum length examples for IPv4, IPv6, and IPv4-mapped IPv6 addresses:
 * 
 * IPv4: min(length(A.B.C.D))=7
 * IPv6: min(length(::))=2 (Shorthand for 0000:0000:0000:0000:0000:0000:0000:0000, aka the IPv6 unspecified address, similar to IPv4 0.0.0.0)
 * IPv4: max(length(ABC.ABC.ABC.ABC))
 * IPv6: max(length(ABCD:ABCD:ABCD:ABCD:ABCD:ABCD:ABCD:ABCD))=39
 * IPv4-mapped IPv6: max(length(0000:0000:0000:0000:0000:ffff:###.###.###.###))=30+15=45 (Longhand for ::ffff:###.###.###.###)
 * 
 * @author justin.cranford
 */
@SuppressWarnings("hiding")
@org.hibernate.envers.Audited
@Entity
@Table(name="address")
// TODO IP and Hostname multi-field validation and uniqueness, so both cannot be NULL at the same time, only one or none
public class Address extends AbstractEntity {
	@Column(name="ip",unique=true,insertable=true,updatable=true,nullable=false,length=45)
	@Size(min=2,max=45)
    private String ipAddress = null;

	@Column(name="hn",unique=false,insertable=true,updatable=true,nullable=true,length=255)
	@Size(min=1,max=255)
    private String hostname = null;

	@Column(name="ipstatic",unique=false,insertable=true,updatable=true,nullable=true)
    private Boolean isIpAddressStatic = Boolean.TRUE;	// TRUE: Static, FALSE: Dynamic (DHCP), NULL: Unknown

	@Column(name="hnstatic",unique=false,insertable=true,updatable=true,nullable=true)
    private Boolean isHostnameStatic = Boolean.TRUE;	// TRUE: Static, FALSE: Dynamic (?), NULL: Unknown

	@Column(name="ipvirtual",unique=false,insertable=true,updatable=true,nullable=true)
    private Boolean isIpAddressVirtual = Boolean.FALSE;	// TRUE: Virtual, FALSE: Physical, NULL: Unknown

	@Column(name="hnvirtual",unique=false,insertable=true,updatable=true,nullable=true)
    private Boolean isHostnameVirtual = Boolean.FALSE;	// TRUE: Virtual, FALSE: Physical, NULL: Unknown

    @ManyToOne(fetch=FetchType.EAGER,optional=false,targetEntity=Server.class)
    private Server server;

    public Address() {
        super();
    }
    public Address(final String ipAddress, final String hostname, final Boolean isIpAddressStatic, final Boolean isHostnameStatic, final Boolean isIpAddressVirtual, final Boolean isHostnameVirtual, final Server server) {
        this.ipAddress          = ipAddress;
        this.hostname           = hostname;
        this.isIpAddressStatic  = isIpAddressStatic;
        this.isHostnameStatic   = isHostnameStatic;
        this.isIpAddressVirtual = isIpAddressVirtual;
        this.isHostnameVirtual  = isHostnameVirtual;
        this.server             = server;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }
	public void setIpAddress(final String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getHostname() {
        return this.hostname;
    }
	public void setHostname(final String hostname) {
        this.hostname = hostname;
    }

    /**
     * TRUE: Static (Reserved), FALSE: Dynamic (DHCP), NULL: Unknown
     */
    public Boolean isIpAddressStatic() {
        return this.isIpAddressStatic;
    }
	public void setIpAddressStatic(final Boolean isIpAddressStatic) {
        this.isIpAddressStatic = isIpAddressStatic;
    }

    public Boolean isHostnameStatic() {
        return this.isHostnameStatic;
    }
	public void setHostnameStatic(final Boolean isHostnameStatic) {
        this.isHostnameStatic = isHostnameStatic;
    }

    /**
     * TRUE: Virtual (127.0.0.1,VIP,etc), FALSE: Physical, NULL: Unknown
     */
    public Boolean isIpAddressVirtual() {
        return this.isIpAddressVirtual;
    }
	public void setIpAddressVirtual(final Boolean isIpAddressVirtual) {
        this.isIpAddressVirtual = isIpAddressVirtual;
    }

    public Boolean isHostnameVirtual() {
        return this.isHostnameVirtual;
    }
	public void setHostnameVirtual(final Boolean isHostnameVirtual) {
        this.isHostnameVirtual = isHostnameVirtual;
    }

    public Server getServer() {
        return this.server;
    }
    public void setServer(final Server server) {
        this.server = server;
    }

    @Override
    public String toString() {
        return "Server [id=" + this.getId() + ", ipAddress=" + this.ipAddress + ", hostname=" + this.hostname + ", ipstatic=" + this.isIpAddressStatic + ", hostnamestatic=" + this.isHostnameStatic + ", ipvirtual=" + this.isIpAddressVirtual + ", hostnamevirtual=" + this.isHostnameVirtual + ", server=" + this.server.getName() + "}]";
    }
}