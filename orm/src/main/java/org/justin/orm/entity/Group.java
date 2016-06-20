package org.justin.orm.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.justin.util.StringUtil;

/**
 * TODO: Add dynamic child object relationships separate from static child object relationships.
 * 
 * @author justin.cranford
 */
@SuppressWarnings("hiding")
@org.hibernate.envers.Audited
@Entity
@Table(name="server")
public class Group extends AbstractEntity {
	@Column(name="name",unique=true,insertable=true,updatable=true,nullable=false,length=255)
	@Size(min=1,max=255)
    private String name;

    @ManyToMany(cascade={},fetch=FetchType.LAZY,targetEntity=Server.class)
    @JoinTable(name="map_group2server",joinColumns=@JoinColumn(name="groupid", referencedColumnName="id"),inverseJoinColumns=@JoinColumn(name="serverid", referencedColumnName="id"))
    private List<Server> childServers = new ArrayList<>();

    @ManyToMany(cascade={},fetch=FetchType.LAZY,targetEntity=Application.class)
    @JoinTable(name="map_group2application",joinColumns=@JoinColumn(name="groupid", referencedColumnName="id"),inverseJoinColumns=@JoinColumn(name="applicationid", referencedColumnName="id"))
    private List<Application> childApplications = new ArrayList<>();

    @ManyToMany(cascade={},fetch=FetchType.LAZY,targetEntity=User.class)
    @JoinTable(name="map_group2user",joinColumns=@JoinColumn(name="groupid", referencedColumnName="id"),inverseJoinColumns=@JoinColumn(name="userid", referencedColumnName="id"))
    private List<User> childUsers = new ArrayList<>();

    @ManyToMany(cascade={},fetch=FetchType.LAZY,targetEntity=Identity.class)
    @JoinTable(name="map_group2identity",joinColumns=@JoinColumn(name="groupid", referencedColumnName="id"),inverseJoinColumns=@JoinColumn(name="identityid", referencedColumnName="id"))
    private List<Identity> childIdentities = new ArrayList<>();

    // Recursion: This group is contained in a parent group (ex: Directory Services like Active Directory, LDAP)
    @ManyToOne(cascade={},fetch=FetchType.LAZY,targetEntity=Group.class,optional=true)
    private Group parentGroup = null;

    // Recursion: This group contains 0-N child groups (ex: Directory Services like Active Directory, LDAP)
    @OneToMany(cascade={},fetch=FetchType.LAZY,targetEntity=Group.class,mappedBy="parentGroup",orphanRemoval=false)
    private List<Group> childGroups = new ArrayList<>();

    // One or more applications can be a Federated Identity Manager for one or more groups (of servers or applications, but not users?).
    @ManyToMany(cascade={},fetch=FetchType.LAZY,targetEntity=Application.class,mappedBy="federatedGroups")
    private List<Application> federatingApplications = new ArrayList<>();

    public Group() {
        super();
    }

    public Group(final String name) {
    	super();
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
	public void setName(final String name) {
        this.name = name;
    }

    public List<Server> getChildServers() {
        return this.childServers;
    }
    public void setChildServers(final List<Server> childServers) {
        this.childServers = childServers;
    }

    public List<Application> getChildApplications() {
        return this.childApplications;
    }
    public void setChildApplications(final List<Application> childApplications) {
        this.childApplications = childApplications;
    }

    public List<User> getChildUsers() {
        return this.childUsers;
    }
    public void setChildUsers(final List<User> childUsers) {
        this.childUsers = childUsers;
    }

    public List<Identity> getChildIdentities() {
        return this.childIdentities;
    }
    public void setChildIdentities(final List<Identity> childIdentities) {
        this.childIdentities = childIdentities;
    }

    @Override
    public String toString() {
        return "Group [id=" + this.getId() + ", name=" + this.name + ", childServers={" + StringUtil.toString(this.childServers,",") + "}]";
    }
}