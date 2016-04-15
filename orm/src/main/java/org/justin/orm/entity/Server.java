package org.justin.orm.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.justin.util.StringUtil;

@SuppressWarnings("hiding")
@org.hibernate.envers.Audited
@Entity
@Table(name="server")
public class Server extends AbstractEntity {
	@Column(name="name",unique=true,insertable=true,updatable=true,nullable=false,length=255)
	@Size(min=1,max=255)
    private String name;

    @OneToMany(mappedBy="server",cascade=CascadeType.PERSIST)
    private List<NetworkInterface> networkInterfaces = new ArrayList<NetworkInterface>();	// addresseses map to hostname(s)

    @OneToMany(mappedBy="server",cascade=CascadeType.PERSIST)
    private List<Application> applications = new ArrayList<Application>();

    public Server() {
        super();
    }

    public Server(final String name) {
    	super();
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
	public void setName(final String name) {
        this.name = name;
    }

    public List<Application> getApplications() {
        return this.applications;
    }
    public void setApplications(final List<Application> applications) {
        this.applications = applications;
    }

    public List<NetworkInterface> getNetworkInterfaces() {
        return this.networkInterfaces;
    }
    public void setNetworkInterfaces(final List<NetworkInterface> networkInterfaces) {
        this.networkInterfaces = networkInterfaces;
    }

    @Override
    public String toString() {
        return "Server [id=" + this.getId() + ", name=" + this.name + ", networkInterfaces={" + StringUtil.toString(this.networkInterfaces,",") + "}]";
    }
}