package com.storageservice.sport.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.storageservice.sport.dao.StorageServiceSportDao;

@Entity
@Table(name = "Activity")
@NamedQuery(name = "Activity.findAll", query = "SELECT a FROM Activity a")
@XmlRootElement
public class Activity implements Serializable{
	@Id
	@GeneratedValue
	@Column(name="idActivity")
	private long idActivity;
	@Column(name="accessLevel")
private String accessLevel;
	@Column(name="mets")
private double mets;
	@Column(name="hasSpeed")
private boolean hasSpeed;
	@Column(name="name")
private String name;

	@OneToOne
	@JoinColumn(name = "idSport", referencedColumnName = "idSport", insertable = true, updatable = true)
	private Sport sport;


public long getIdActivity(){
	return idActivity;
}
public void setIdActivity(long l){
	this.idActivity=idActivity;
}

public String getAccessLevel(){
	return accessLevel;
}
public void setAccessLevel(String accessLevel){
	this.accessLevel=accessLevel;
}
public String getName(){
	return name;
}
public void setName(String name){
	this.name=name;
}
public double getMets(){
	return mets;
}
public void setMets(double mets){
	this.mets=mets;
}
public boolean getHasSpeed(){
	return hasSpeed;
}
public void setHasSpeed(boolean hasSpeed){
	this.hasSpeed=hasSpeed;
}
public Sport getSport(){
	return sport;
}
public void setSport(Sport sport){
	this.sport=sport;
}




//Database operations
	// get the SportCalories which id correspond to the given id as parameter, return a
	// SportCalories
	public static Activity getActivityById(int activityId) {
		EntityManager em = StorageServiceSportDao.instance.createEntityManager();
		Activity s = em.find(Activity.class, (int) activityId);
		StorageServiceSportDao.instance.closeConnections(em);
		return s;
	}

	// get all the SportCalories present in the db, return a list of Person
	public static List<Activity> getAll() {
		EntityManager em = StorageServiceSportDao.instance.createEntityManager();
		List<Activity> list = em.createNamedQuery("Activity.findAll", Activity.class).getResultList();
		StorageServiceSportDao.instance.closeConnections(em);
		return list;
	}

	// save a new SportCalories in the db
	public static Activity saveActivity(Activity s) {
		EntityManager em = StorageServiceSportDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(s);
		tx.commit();
		StorageServiceSportDao.instance.closeConnections(em);
		return s;
	}



	// delete the SportCalories givean as input in the db
	public static void removeActivity(Activity s) {
		EntityManager em = StorageServiceSportDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		s = em.merge(s);
		em.remove(s);
		tx.commit();
		StorageServiceSportDao.instance.closeConnections(em);
	}
	
	public static Activity getActivityBySport(String name) {
		EntityManager em = StorageServiceSportDao.instance.createEntityManager();
		try {
			Query q = em.createQuery(
					"SELECT a FROM Activity a WHERE a.name=:name",
					Sport.class);
			q.setParameter("name", name);
			
			Activity activity = (Activity) q.getSingleResult();
			StorageServiceSportDao.instance.closeConnections(em);
			return activity;
		} catch (Exception e) {
			System.out.println(
					"The database doesn't contain an activity with the sport required");

			StorageServiceSportDao.instance.closeConnections(em);
			return null;
		}

	}
	



}
