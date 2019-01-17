package util;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Animal;
import model.Doctor;
import model.Appointment;

public class DatabaseUtil {

	public static EntityManagerFactory entityManagerFactory;
	public static EntityManager entityManager;



	// Connecting to the database
	public void setUp() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory("PetShopJPA");
		entityManager = entityManagerFactory.createEntityManager();
	}
	
	// adding an animal to the database
	public void addAnimal(int id, String name) {
		Animal animal = new Animal();
		animal.setIdAnimal(id);
		animal.setName(name);
		entityManager.getTransaction().begin();
		entityManager.persist(animal);
		entityManager.getTransaction().commit();
	}

	//displaying all animals inside the database
	public List<Animal> getAllAnimals() {
		List<Animal> results = entityManager.createNativeQuery("SELECT * FROM Animal", Animal.class).getResultList();
		return results;
	}

	//searching for an animal using the name and display it
	public void getAnimal(String name) {
		List<Animal> result = entityManager
				.createNativeQuery("SELECT * FROM Animal WHERE name='" + name + "'", Animal.class).getResultList();
		for (Animal animal : result) {
			System.out.println("Animal: " + animal.getName() + " has id " + animal.getIdAnimal());
		}

	}
	
	public Animal getAnim(String name) {
		List<Animal> result = entityManager.createNativeQuery("SELECT * FROM Animal WHERE name='" + name + "'", Animal.class).getResultList();
		int total = 0;
		for (Animal animal : result) {
			total++;
		}
		if(total == 0) {
			return null;
		}
		return result.get(0);

	}

	//updating an animal entry inside the database by
	// id. 1 changes the id and code 2 changes the name
	public void updateAnimal(int code, int id) {
		Scanner read = new Scanner(System.in);

		if (code == 1) {
			System.out.println("Enter the id: ");
			int idSet = read.nextInt();
			
			entityManager.getTransaction().begin();
			entityManager.createNativeQuery("UPDATE animal SET idAnimal=" + idSet + " WHERE idAnimal=" + id + "")
					.executeUpdate();
			entityManager.getTransaction().commit();
		}else
		if (code == 2) {
			System.out.println("Enter the name: ");
			String name = read.nextLine();
			
			entityManager.getTransaction().begin();
			entityManager.createNativeQuery("UPDATE animal SET name='" + name + "' WHERE idAnimal=" + id + "")
					.executeUpdate();
			entityManager.getTransaction().commit();
		}
		closeConnection();
	}

	//deleting an entry from the database by id
	public void deleteAnimal(int id) {
		System.out.println("Do you want to delete this entry with id: " + id + "? (y/n)");
		Scanner read = new Scanner(System.in);
		char name = read.next(".").charAt(0);
		
		if (name == 'y') {
			Animal animal = entityManager.find(Animal.class, id);
			entityManager.getTransaction().begin();
			entityManager.remove(animal);
			entityManager.getTransaction().commit();
		}
	}


	

	//Adding a doctor to the database
	public void addDr(int id, String name) {
		Doctor doctor = new Doctor();
		doctor.setIddoctor(id);
		doctor.setName(name);
		entityManager.getTransaction().begin();
		entityManager.persist(doctor);
		entityManager.getTransaction().commit();
	}

	//displaying all doctors inside the database
	public List<Doctor> getAllDrs() {
		List<Doctor> results = entityManager.createNativeQuery("SELECT * FROM doctor", Doctor.class).getResultList();
		return results;
	}

	//searching for a doctor using the name and displaying it
	public void getDr(String name) {
		List<Doctor> results = entityManager.createNativeQuery("SELECT * FROM doctor WHERE name='" + name + "'", Doctor.class).getResultList();
		for (Doctor doctor : results) {
			System.out.println("Employee: " + doctor.getName() + " has id " + doctor.getIddoctor());
				
		}

	}
	public Doctor getDoc(String name) {
		List<Doctor> results = entityManager.createNativeQuery("SELECT * FROM doctor WHERE name='" + name + "'", Doctor.class).getResultList();
		int total = 0;
		for (Doctor doctor : results) {
			total++;
		}
		if(total == 0) {
			return null;
		}
		return results.get(0);

	}

	//updating an doctor entry inside of the database using
	// the id. 1 changes the id and code 2 changes the name
	
	public void updateDr(int code, int id) {
		Scanner read = new Scanner(System.in);

		if (code == 1) {
			System.out.println("id: ");
			int idSet = read.nextInt();
			
			entityManager.getTransaction().begin();
			entityManager.createNativeQuery("UPDATE doctor SET idDoctor=" + idSet + " WHERE idDoctor=" + id + "").executeUpdate();
			entityManager.getTransaction().commit();
		}else
		if (code == 2) {
			System.out.println("Name: ");
			String name = read.nextLine();
			
			entityManager.getTransaction().begin();
			entityManager.createNativeQuery("UPDATE personalmedical SET name='" + name + "' WHERE idPersonalMedical=" + id + "").executeUpdate();
			entityManager.getTransaction().commit();
		}
		
		closeConnection();
	}

	//deleting an entry from the database by id
	public void deleteDr(int id) {
		System.out.println("Do you want to delete this entry with id: " + id + "? (y/n)");
		Scanner read = new Scanner(System.in);
		char name = read.next(".").charAt(0);
		
		if (name == 'y') {
			Doctor doctor = entityManager.find(Doctor.class, id);
			entityManager.getTransaction().begin();
			entityManager.remove(doctor);
			entityManager.getTransaction().commit();
		}
	}

	//adding appointments to the database
	public void addAppointment() throws ParseException {
		Appointment appointment = new Appointment();
		System.out.println("Add appointment id:");
		Scanner read = new Scanner(System.in);
		int id = read.nextInt();
		read.nextLine();
	    System.out.println("Add appointment doctor");
	    String doc = read.nextLine();
	    System.out.println("Add appointment animal");
	    String anim = read.nextLine();
	    appointment.setIdappointment(id);
	    appointment.setDoctor(getDoc(doc));
	    appointment.setAnimal(getAnim(anim));
	    if(appointment.getAnimal() == null) {
	    	System.out.println("Animal does not exist in database. Please make a request for it to be added.");
	    	return;
	    }else if(appointment.getDoctor() == null) {
	    	System.out.println("doctor does not exist in database.");
	    	return;
	    }
		entityManager.getTransaction().begin();
		entityManager.persist(appointment);
		entityManager.getTransaction().commit();
	}

	// This method is going to display all employees inside the database
	public List<Appointment> getAllAppointments() {
		List<Appointment> results = entityManager.createNativeQuery("SELECT * FROM appointment", Appointment.class).getResultList();
		return results;
	}

	//Searching for a doctor using the name and displaying it
	public void getAppointment(String name) {
		List<Appointment> results = entityManager.createNativeQuery("SELECT * FROM appointment WHERE name='" + name + "'", Appointment.class).getResultList();
		for (Appointment appointment : results) {
			System.out.println("Appointment with id:" + appointment.getIdappointment());
		}

	}

	//updating a doctor entry inside the database using
	// the id. 1 changes the id
	public void updateAppointment(int code, int id) throws Exception {
		Scanner read = new Scanner(System.in);

		if (code == 1) {
			System.out.println("Enter the id: ");
			int idSet = read.nextInt();
			
			entityManager.getTransaction().begin();
			entityManager.createNativeQuery("UPDATE appointment SET idappointment=" + idSet + " WHERE idappointment=" + id + "").executeUpdate();
			entityManager.getTransaction().commit();
			
		}
		closeConnection();
	}

	// This method is going to delete an entry from the database using the id
	public void deleteAppointment(int id) {
		System.out.println("Do you want to delete entry with id: " + id + "? (y/n)");
		Scanner read = new Scanner(System.in);
		char name = read.next(".").charAt(0);
		
		if (name == 'y') {
			Appointment appointment = entityManager.find(Appointment.class, id);
			entityManager.getTransaction().begin();
			entityManager.remove(appointment);
			entityManager.getTransaction().commit();
		}
	}

	
	//Closing the connection to the database
	public void closeConnection() {
		entityManager.close();
		entityManagerFactory.close();
	}

}
