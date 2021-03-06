package org.justin.orm.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

@SuppressWarnings("hiding")
@org.hibernate.envers.Audited
@Entity(name="user")
public class User extends AbstractEntity {
	@Column(name="firstname",unique=false,insertable=true,updatable=true,nullable=false,length=255)
	@Size(min=1,max=255)
    private String firstName = null;

	@Column(name="lastname",unique=false,insertable=true,updatable=true,nullable=false,length=255)
	@Size(min=1,max=255)
    private String lastName = null;

	@Column(name="email",unique=true,insertable=true,updatable=true,nullable=false,length=320)
	@Size(min=1,max=320)
    private String email = null;

    @ManyToOne(cascade={},fetch=FetchType.LAZY,targetEntity=Application.class,optional=true)
    private Application parentApplication = null;

    @ManyToMany(cascade={},fetch=FetchType.LAZY,targetEntity=Group.class,mappedBy="childUsers")
    private List<Group> parentGroups = new ArrayList<>();

    public User() {
    	super();
    }

	public User(final String firstName, final String lastName, final Application parentApplication) {
    	super();
        this.firstName = firstName;
        this.parentApplication = parentApplication;
        this.lastName = lastName;
    }

	public User(final String firstName, final String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(final String email) {
        this.email = email;
    }

    public Application getParentApplication() {
        return this.parentApplication;
    }
    public void setParentApplication(final Application parentApplication) {
        this.parentApplication = parentApplication;
    }

    public List<Group> getParentGroups() {
        return this.parentGroups;
    }
    public void setParentGroups(final List<Group> parentGroups) {
        this.parentGroups = parentGroups;
    }

    @Override
    public String toString() {
        return "User [id=" + this.getId() + ", firstName=" + this.firstName + ", lastName=" + this.lastName + (null==this.parentApplication?"":", parentApplication=" + this.parentApplication.getName() + "]");
    }
}