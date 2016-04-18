package org.justin.orm.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.justin.util.StringUtil;

/**
 * @author justin.cranford
 */
@SuppressWarnings("hiding")
@org.hibernate.envers.Audited
@Entity
@Table(name="server")
public class Server extends AbstractEntity {
	@Column(name="name",unique=true,insertable=true,updatable=true,nullable=false,length=255)
    @NotNull
	@Size(min=1,max=255)
    private String name;

    @OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER,targetEntity=Address.class,mappedBy="server",orphanRemoval=true)
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(cascade={},fetch=FetchType.LAZY,targetEntity=Application.class,mappedBy="parentServer",orphanRemoval=false)
    private List<Application> childApplications = new ArrayList<>();

    @ManyToMany(cascade={},fetch=FetchType.LAZY,targetEntity=Group.class,mappedBy="childServers")
    private List<Group> parentGroups = new ArrayList<>();

    @ManyToOne(cascade={},fetch=FetchType.LAZY,targetEntity=Server.class,optional=true)
    private Server parentApplication = null;

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

    public List<Application> getChildApplications() {
        return this.childApplications;
    }
    public void setChildApplications(final List<Application> childApplicationss) {
        this.childApplications = childApplicationss;
    }

    public List<Address> getAddresses() {
        return this.addresses;
    }
    public void setAddresses(final List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Group> getParentGroups() {
        return this.parentGroups;
    }
    public void setParentGroups(final List<Group> parentGroups) {
        this.parentGroups = parentGroups;
    }

    @Override
    public String toString() {
        return "Server [id=" + this.getId() + ", name=" + this.name + ", addresses={" + StringUtil.toString(this.addresses,",") + "}]";
    }
}