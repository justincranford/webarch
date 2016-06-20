package org.justin.orm.entity;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

//import org.hibernate.envers.RevisionListener;

//CREATE TABLE SERVER (
//		ID INTEGER DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_B84C52A9_62A0_45D7_BB42_3055539AAA3F) NOT NULL,
//		VERNUM INTEGER NOT NULL,
//		VERTIME TIMESTAMP NOT NULL,
//		VERUSERID INTEGER NOT NULL,
//		NAME VARCHAR(255) NOT NULL
//	);

@org.hibernate.envers.Audited
@MappedSuperclass
@Table(indexes={@Index(name="veruserid_id_vernum",unique=false,columnList="veruserid,id,vernum")})
//@org.hibernate.envers.RevisionEntity(AbstractEntity.AbstractEntityRevisionListener.class)
public abstract class AbstractEntity {
	private static final AtomicInteger JUNK = new AtomicInteger(1);	// TODO remove

	@Column(name="id",unique=true,insertable=true,updatable=false,nullable=false)
	@NotNull
	@Min(value=0)
	@Id
//	@SequenceGenerator(name="seq",initialValue=1,allocationSize=100)
//	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="seq")	// SEQUENCE: global unique sequence with init value and increment size
//	@GeneratedValue(strategy=GenerationType.AUTO)						// AUTO: global unique sequence
	@GeneratedValue(strategy=GenerationType.IDENTITY)					// IDENTITY: type unique sequence
	private long id;

	@Column(name="vernum",unique=false,insertable=true,updatable=true,nullable=false)
	@Min(value=0)
	@Version
	private long auditRevision;

	@Column(name="veruserid",unique=false,insertable=true,updatable=true,nullable=false)
	@NotNull
	@Min(value=0)
	private long auditUserId;

	@Column(name="vertime",unique=false,insertable=true,updatable=true,nullable=false)
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date auditTimestamp;

	public AbstractEntity() {
    	// JPA calls child class constructors using reflection, and they call super()
	}

	public long getId() {
		return this.id;
	}

	public long getAuditRevision() {
		return this.auditRevision;
	}

	public long getAuditUserId() {
		return this.auditUserId;
	}

	public Date getAuditTimestamp() {
		return this.auditTimestamp;
	}

	@PrePersist	// create
	@PreUpdate	// update
	@PreRemove	// delete
	void preCommit() {
		this.auditTimestamp = new Date();
		this.auditUserId = AbstractEntity.JUNK.getAndIncrement();	// TODO remove
		System.out.println("DEBUG: Committing " + this.getClass().getSimpleName() + ", value=" + this.toString());	// TODO remove
	}

	@PostPersist
	@PostUpdate
	@PostRemove
	void postCommit() {
		System.out.println("DEBUG: Committed " + this.getClass().getSimpleName() + ", value=" + this.toString());
	}

	@PostLoad
	void postLoad() {
		System.out.println("DEBUG: Loaded " + this.getClass().getSimpleName() + ", value=" + this.toString());
	}

//	public class AbstractEntityRevisionListener implements RevisionListener {
//		@Override
//	    public void newRevision(Object revisionEntity) {
//	    	AbstractEntity abstractEntity = (AbstractEntity) revisionEntity;
//	        abstractEntity.auditUserId = Long.valueOf(AbstractEntity.JUNK.getAndIncrement());
//	    }
//	}
}