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
@Table(name = "Sport")
@NamedQuery(name = "Sport.find", query = "SELECT s FROM Sport s")
@XmlRootElement
public class Sport implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "idSport")
	private int idSport;

@Column(name = "name")
private String name;

@Column(name = "perfectWeather")
private String perfectWeather;


public int getIdSport(){
	return idSport;
}
public void setIdSport(int idSport){
	this.idSport=idSport;
}
public String getName(){
	return name;
}
public void setName(String name){
	this.name=name;
}
public String getPerfectWeather(){
	return perfectWeather;
}
public void setPerfectWeather(String perfectWeather){
	this.perfectWeather=perfectWeather;}




//Database operations
	// get the Sport which id correspond to the given id as parameter, return a
	// Sport
	public static Sport getSportById(long sportId) {
		EntityManager em = StorageServiceSportDao.instance.createEntityManager();
		Sport s = em.find(Sport.class, (int) sportId);
		StorageServiceSportDao.instance.closeConnections(em);
		return s;
	}

	// get all theSports present in the db, return a list of Person
	public static List<Sport> getAll() {
		EntityManager em = StorageServiceSportDao.instance.createEntityManager();
		/*List<Sport> list = em.createNamedQuery("Sport.find", Sport.class).getResultList();
		*/
		try {
			Query q = em.createQuery(
					"SELECT s FROM Sport s ",
					Sport.class);
			
			
			List<Sport>list = q.getResultList();
			StorageServiceSportDao.instance.closeConnections(em);
			return list;
		} catch (Exception e) {
			System.out.println(
					"The database doesn't contain any Sport");

			StorageServiceSportDao.instance.closeConnections(em);
			return null;
		}
	}
	public static List<Sport> getSportByWeather(String weather) {
		EntityManager em = StorageServiceSportDao.instance.createEntityManager();
		String []parts= weather.split("\\s+");
		if (parts.length==2){
			weather=parts[1];
		}
		System.out.println(weather);
		try {
			Query q = em.createQuery(
					"SELECT s FROM Sport s WHERE s.perfectWeather LIKE :weather",
					Sport.class);
			q.setParameter("weather", weather);
			
			List<Sport>sports = q.getResultList();
			StorageServiceSportDao.instance.closeConnections(em);
			return sports;
		} catch (Exception e) {
			System.out.println(
					"The database doesn't contain a Sport with the perfect weather required");

			StorageServiceSportDao.instance.closeConnections(em);
			return null;
		}

	}

	// save a new Sport in the db
	public static Sport saveSport(Sport s) {
		EntityManager em = StorageServiceSportDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(s);
		tx.commit();
		StorageServiceSportDao.instance.closeConnections(em);
		return s;
	}



	// delete the Sport givean as input in the db
	public static void removeSport(Sport s) {
		EntityManager em = StorageServiceSportDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		s = em.merge(s);
		em.remove(s);
		tx.commit();
		StorageServiceSportDao.instance.closeConnections(em);
	}



}