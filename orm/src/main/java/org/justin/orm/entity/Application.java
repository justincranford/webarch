package org.justin.orm.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@SuppressWarnings("hiding")
@org.hibernate.envers.Audited
@Entity
@Table(name="app")
public class Application extends AbstractEntity {
	@Column(name="name",unique=false,insertable=true,updatable=true,nullable=false,length=255)
	@Size(min=1,max=255)
    private String name;

	@Column(name="type",unique=false,insertable=true,updatable=true,nullable=false,length=255)
	@Size(min=1,max=255)
    private String type;

    @ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.DETACH)
    private Server server;

    @OneToMany(mappedBy="application",cascade=CascadeType.PERSIST)
    private List<User> users = new ArrayList<>();

    public Application() {
    	super();
    }

	public Application(final String name, final String type, final Server server) {
    	super();
        this.name = name;
        this.type = type;
        this.server = server;
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

    public Server getServer() {
        return this.server;
    }
    public void setServer(final Server server) {
        this.server = server;
    }

    public List<User> getUsers() {
        return this.users;
    }
    public void setUsers(final List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Application [id=" + this.getId() + ", name=" + this.name + ", type=" + this.type + (null==this.server?"":", server=" + this.server.getName() + "]");
    }
}