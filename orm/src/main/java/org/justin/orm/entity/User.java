package org.justin.orm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

@SuppressWarnings("hiding")
@org.hibernate.envers.Audited
@Entity(name="user")
public class User extends AbstractEntity {
	@Column(name="firstname",unique=false,insertable=true,updatable=true,nullable=false,length=255)
	@Size(min=1,max=255)
    private String firstName;

	@Column(name="lastname",unique=false,insertable=true,updatable=true,nullable=false,length=255)
	@Size(min=1,max=255)
    private String lastName;

	@Column(name="email",unique=true,insertable=true,updatable=true,nullable=false,length=320)
	@Size(min=1,max=320)
    private String email;

    @ManyToOne
    private Application application;

    public User() {
    	super();
    }

	public User(final String firstName, final String lastName, final Application application) {
    	super();
        this.firstName = firstName;
        this.application = application;
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

    public Application getApplication() {
        return this.application;
    }
    public void setApplication(final Application application) {
        this.application = application;
    }

    @Override
    public String toString() {
        return "User [id=" + this.getId() + ", firstName=" + this.firstName + ", lastName=" + this.lastName + (null==this.application?"":", application=" + this.application.getName() + "]");
    }
}