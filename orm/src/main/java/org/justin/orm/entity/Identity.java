package org.justin.orm.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@SuppressWarnings("hiding")
@org.hibernate.envers.Audited
@Entity(name="identity")
public class Identity extends AbstractEntity {
	@Column(name="name",unique=false,insertable=true,updatable=true,nullable=false,length=255)
	@Size(min=1,max=255)
    private String name = null;

	@Column(name="type",unique=false,insertable=true,updatable=true,nullable=false,length=255)
	@Size(min=1,max=255)
    private String type = null;

	@Column(name="rank",unique=false,insertable=true,updatable=true,nullable=false)
	@Min(value=1)
	@Max(value=10)
    private int rank;

	@Column(name="identifier1",unique=false,insertable=true,updatable=true,nullable=false,length=255)
	@Size(min=1,max=255)
    private String identifier1 = null;

	@Column(name="identifier2",unique=false,insertable=true,updatable=true,nullable=false,length=255)
	@Size(min=1,max=255)
    private String identifier2 = null;

	@Column(name="identifier3",unique=false,insertable=true,updatable=true,nullable=false,length=255)
	@Size(min=1,max=255)
    private String identifier3 = null;

	@Column(name="identifier4",unique=false,insertable=true,updatable=true,nullable=false,length=255)
	@Size(min=1,max=255)
    private String identifier4 = null;

	@Column(name="identifier5",unique=false,insertable=true,updatable=true,nullable=false,length=255)
	@Size(min=1,max=255)
    private String identifier5 = null;

	@Column(name="identifier6",unique=false,insertable=true,updatable=true,nullable=false,length=255)
	@Size(min=1,max=255)
    private String identifier6 = null;

    @ManyToOne(cascade={},fetch=FetchType.LAZY,targetEntity=User.class,optional=false)
    private User parentUser = null;

    @OneToMany(cascade={},fetch=FetchType.EAGER,targetEntity=Identity.class,mappedBy="parentUser",orphanRemoval=true)
    private List<Identity> childIdentities = new ArrayList<>();

    // Server -> Application -> User -> Map(Attributes,Attributes)
    // Attributes (Identity Data) <-ManyToMany-> Attributes (Credential Data)
    // Domain Authentication = List<Identifier> and List<Credential>

    @ManyToMany(cascade={},fetch=FetchType.LAZY,targetEntity=Group.class,mappedBy="childIdentities")
    private List<Group> parentGroups = new ArrayList<>();

    public Identity() {
    	super();
    }

	public Identity(final String name, final String type, final int rank, final User parentUser) {
    	super();
        this.name = name;
        this.type = type;
        this.rank = rank;
        this.parentUser = parentUser;
    }

	public Identity(final String name, final String type, final int rank) {
        this.name = name;
        this.type = type;
        this.rank = rank;
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
        this.type = name;
    }

    public int getRank() {
        return this.rank;
    }
    public void setRank(final int rank) {
        this.rank = rank;
    }

    public User getParentUser() {
        return this.parentUser;
    }
    public void setParentUser(final User parentUser) {
        this.parentUser = parentUser;
    }

    public List<Group> getParentGroups() {
        return this.parentGroups;
    }
    public void setParentGroups(final List<Group> parentGroups) {
        this.parentGroups = parentGroups;
    }

    @Override
    public String toString() {
        return "User [id=" + this.getId() + ", name=" + this.name + ", type=" + this.type + ", rank=" + this.rank + (null==this.parentUser?"":", parentUser=" + this.parentUser.toString() + "]");
    }
}