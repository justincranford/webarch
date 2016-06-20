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
@Entity(name="identitier")
public class Identifier extends AbstractEntity {
	// User (w/ Credentials)
	// -> Other User (w/Credential)
	// -> Other User (w/Credential)

	// User -> CredentialAttribute (UserId,AttributeId,Type,Value,isIdentifierOrSecret)
	// User -> Credential          (UserId,CredentialId,AttributeId)	// UserId not explicitly necessary, but useful for referential integrity enforcement
	// User -> User                (UserId,ChildUserId)

//	// User -> UserIdentifier (UserIdentifierId,UserId,Type,Value)
//	// User -> UserSecret     (UserSecretId,UserId,Type,ProtectedValue)
//	// User -> UserCredential (UserCredentialId,UserId)
//	// UserCredential -> UserCredentialIdentifier (UserCredentialIdentifierId,UserCredentialId,UserId,UserIdentifierId)
//	// UserCredential -> UserCredentialSecret     (UserCredentialSecretId,    UserCredentialId,UserId,UserSecretId)

	// User -> Credential (UserId,Identifier1Name,Identifier1Value,Identifier2Name,Identifier2Value,Secret1Name,Secret1Value,Secret2Name,Secret2Value)

	// (authenticationdomain={local,AD,LDAP,TACAKS+},identifierrank,identifiername) => value
	// local,1,username,justin,unencrypted,unique
	// local,1,email,justin@something.com,unencrypted,unique
	// local,1,employeenumber,00001291234,unencrypted,unique
	// local,1,firstname,Justin,unencrypted,notunique
	// local,1,lastname,Cranford,unencrypted,notunique

	// ad,1,domainname,CPA,unencrypted,notunique
	// ad,2,username,jcranford,unencrypted,notunique
	// ad,3,DN,CN=Justin Cranford,OU=Engineering,DC=Ottawa,DC=CA,DC=COM,unencrypted,unique
	// ad,4,x509,<PUBLICCERT>,unencrypted,unique,unencrypted,unique

	// aws,1,username,justinc,unencrypted,unique
	@Column(name="identifierdomain",unique=false,insertable=true,updatable=true,nullable=false,length=255)
	@Size(min=1,max=255)
    private String identifierdomain = null;

	@Column(name="rank",unique=false,insertable=true,updatable=true,nullable=false)
	@Min(value=1)
	@Max(value=10)
    private int rank;

	@Column(name="identifiername",unique=false,insertable=true,updatable=true,nullable=false,length=255)
	@Size(min=1,max=255)
    private String identifiername = null;

	@Column(name="identifiervalue",unique=false,insertable=true,updatable=true,nullable=false,length=255)
	@Size(min=1,max=255)
    private String identifiervalue = null;

    @ManyToOne(cascade={},fetch=FetchType.LAZY,targetEntity=User.class,optional=false)
    private User parentUser = null;

    @OneToMany(cascade={},fetch=FetchType.EAGER,targetEntity=Identifier.class,mappedBy="parentUser",orphanRemoval=true)
    private List<Identifier> childIdentities = new ArrayList<>();

    // Server -> Application -> User -> Map(Attributes,Attributes)
    // Attributes (Identifier Data) <-ManyToMany-> Attributes (Credential Data)
    // Domain Authentication = List<Identifier> and List<Credential>

    @ManyToMany(cascade={},fetch=FetchType.LAZY,targetEntity=Group.class,mappedBy="childIdentities")
    private List<Group> parentGroups = new ArrayList<>();

    public Identifier() {
    	super();
    }

	public Identifier(final String name, final String type, final int rank, final User parentUser) {
    	super();
        this.identifiername = name;
        this.identifiervalue = type;
        this.rank = rank;
        this.parentUser = parentUser;
    }

	public Identifier(final String name, final String type, final int rank) {
        this.identifiername = name;
        this.identifiervalue = type;
        this.rank = rank;
    }

    public String getName() {
        return this.identifiername;
    }
    public void setName(final String name) {
        this.identifiername = name;
    }

    public String getType() {
        return this.identifiervalue;
    }
    public void setType(final String type) {
        this.identifiervalue = identifiername;
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
        return "User [id=" + this.getId() + ", name=" + this.identifiername + ", type=" + this.identifiervalue + ", rank=" + this.rank + (null==this.parentUser?"":", parentUser=" + this.parentUser.toString() + "]");
    }
}