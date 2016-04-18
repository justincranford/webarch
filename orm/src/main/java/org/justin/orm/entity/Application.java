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

/**
 * TODO: The federatedGroups only specified data permissions, move to a policy object to contain data permissions with operation permissions?
 * 
 * Parent/child relations:
 * 
 *       Group -> Server -> Application
 *       Group -> Application -> Server
 *       Server -> Application
 *       Application -> Server
 *       Application -> Application
 * 
 * Federated identity manager relationships:
 * 
 *       Application(s) -> Group(s) -> Server -> Application
 *       Application(s) -> Group(s) -> Application -> Server
 * 
 * ASSUMPTION: Authentication => Application Obj + Child User Obj
 * ASSUMPTION: Authentication => Application Obj + Federared User Obj
 * 
 * Server always contains one application representing the operating system, and some applications.
 * 
 * Server -> ESX OS (IaaS) -> Server -> Windows OS -> Local User
 * Server -> ESX OS (IaaS) -> Server -> Windows OS -> SSH
 * Server -> ESX OS (IaaS) -> Server -> Windows OS -> Tomcat (SaaS)
 * Server -> ESX OS (IaaS) -> Server -> Windows OS -> SQL Server (SaaS) -> Database User
 * Server -> ESX OS (IaaS) -> Server -> Windows OS -> IIS Server (PaaS) -> .Net Hosted Application (SaaS)
 * 
 * Server -> Linux OS -> VMware Player -> Server -> Windows OS -> Local User
 * Server -> Linux OS -> Docker (PaaS) -> Micro Application
 * 
 * Server -> vCenter OS -> Users
 * Server -> vCenter OS -> Server -> Windows -> Active Directory -> Users
 * Server -> vCenter OS -> Server -> ESX OS  -> Users
 * Server -> vCenter OS -> Server -> ESX OS -> Windows
 * 
 * Application has local users, and can link to an application container federated users?
 * 
 * TODO: Server -> Application -> Server
 * TODO: Server -> Application -> Application (Container) -> Application (Authentication)
 * TODO: Server -> Application -> Application (Authentication) -> Users
 * 
 * @author justin.cranford
 */
@SuppressWarnings("hiding")
@org.hibernate.envers.Audited
@Entity
@Table(name="application")
public class Application extends AbstractEntity {
	@Column(name="name",unique=false,insertable=true,updatable=true,nullable=false,length=255)
	@Size(min=1,max=255)
    private String name = null;

	@Column(name="type",unique=false,insertable=true,updatable=true,nullable=false,length=255)
	@Size(min=1,max=255)
    private String type = null;

	// TODO: One and only one of parentServer and parentApplication must be NotNull
    @ManyToOne(cascade={},fetch=FetchType.LAZY,targetEntity=Server.class,optional=true)
    private Server parentServer = null;

	// TODO: One and only one of parentServer and parentApplication must be NotNull
    @ManyToOne(cascade={},fetch=FetchType.LAZY,targetEntity=Application.class,optional=true)
    private Application parentApplication = null;

    @OneToMany(cascade={},fetch=FetchType.LAZY,targetEntity=User.class,mappedBy="parentApplication",orphanRemoval=false)
    private List<User> childUsers = new ArrayList<>();

    // Recursion: This application is contained in a parent application (ex: PaaS containers like IIS Server, Docker, JBoss)
    @ManyToMany(cascade={},fetch=FetchType.LAZY,targetEntity=Group.class,mappedBy="childApplications")
    private List<Group> parentGroups = new ArrayList<>();

    // Recursion: This application contains 0-N child applications (ex: PaaS containers like IIS Server, Docker, JBoss)
    @OneToMany(cascade={},fetch=FetchType.LAZY,targetEntity=Application.class,mappedBy="parentApplication",orphanRemoval=false)
    private List<Application> childApplications = new ArrayList<>();

    @OneToMany(cascade={},fetch=FetchType.LAZY,targetEntity=Server.class,mappedBy="parentApplication",orphanRemoval=false)
    private List<Server> childServers = new ArrayList<>();

    // One or more applications can be a Federated Identity Manager for one or more groups (of servers or applications, but not users?).
    @ManyToMany(cascade={},fetch=FetchType.LAZY,targetEntity=Group.class)
    @JoinTable(name="map_application2group",joinColumns=@JoinColumn(name="applicationid", referencedColumnName="id"),inverseJoinColumns=@JoinColumn(name="groupid", referencedColumnName="id"))
    private List<Group> federatedGroups = new ArrayList<>();

    public Application() {
    	super();
    }

	public Application(final String name, final String type, final Server parentServer) {
    	super();
        this.name = name;
        this.type = type;
        this.parentServer = parentServer;
    }

    public Application(final String name, final String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }
    public void setName(final String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }
    public void setType(final String type) {
        this.type = type;
    }

    public Server getParentServer() {
        return this.parentServer;
    }
    public void setParentServer(final Server parentServer) {
        this.parentServer = parentServer;
    }

    public List<User> getChildUsers() {
        return this.childUsers;
    }
    public void setChildUsers(final List<User> childUsers) {
        this.childUsers = childUsers;
    }

    public List<Group> getParentGroups() {
        return this.parentGroups;
    }
    public void setParentGroups(final List<Group> parentGroups) {
        this.parentGroups = parentGroups;
    }

    public List<Application> getChildApplications() {
        return this.childApplications;
    }
    public void setChildApplications(final List<Application> childApplications) {
        this.childApplications = childApplications;
    }

    @Override
    public String toString() {
        return "Application [id=" + this.getId() + ", name=" + this.name + ", type=" + this.type + (null==this.parentServer?"":", parentServer=" + this.parentServer.getName() + "]");
    }
}