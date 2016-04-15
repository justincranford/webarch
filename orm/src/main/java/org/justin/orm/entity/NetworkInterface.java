package org.justin.orm.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

/**
 * Minimum and maximum length examples:
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
@Table(name="netintf")
public class NetworkInterface extends AbstractEntity {
	@Column(name="ip",unique=true,insertable=true,updatable=true,nullable=false,length=45)
	@NotNull
	@Size(min=2,max=45)
    private String ipAddress;

	@Column(name="virt",unique=false,insertable=true,updatable=true,nullable=true)	// nice to have
	@Null
    private Boolean isVirtual;

	@Column(name="sub",unique=false,insertable=true,updatable=true,nullable=true,length=10)	// nice to have (TODO: research min and max lengths)
	@Null
	@Size(min=1,max=10)
    private String subnet;

	@Column(name="gw",unique=false,insertable=true,updatable=true,nullable=true,length=45)	// nice to have
	@Null
	@Size(min=1,max=45)
    private String gateway;

//	@OneToMany	// TODO Map multiple hostnames to single IP address
//	@Column(name="hostname",unique=false,insertable=true,updatable=true,nullable=false,length=255)
//	@Size(min=1,max=255)
//    private String hostname;

    @ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.PERSIST)
    private Server server;

    public NetworkInterface() {
        super();
    }
    public NetworkInterface(final String ipAddress, final Boolean isVirtual, final String subnet, final String gateway, final Server server) {
        this.ipAddress = ipAddress;
        this.isVirtual = isVirtual;
        this.subnet    = subnet;
        this.gateway   = gateway;
        this.server    = server;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }
	public void setIpAddress(final String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Boolean isVirtual() {
        return this.isVirtual;
    }
	public void setVirtual(final Boolean isVirtual) {
        this.isVirtual = isVirtual;
    }

    public String getSubnet() {
        return this.subnet;
    }
    public void setSubnet(final String subnet) {
        this.subnet = subnet;
    }

    public String getGateway() {
        return this.gateway;
    }
    public void setGateway(final String gateway) {
        this.gateway = gateway;
    }

    public Server getServer() {
        return this.server;
    }
    public void setServer(final Server server) {
        this.server = server;
    }

    @Override
    public String toString() {
        return "Server [id=" + this.getId() + ", ipAddress=" + this.ipAddress + ", virtual=" + this.isVirtual + ", subnet=" + this.subnet + ", gateway=" + this.gateway + ", server=" + this.server.getName() + "]";
    }
}