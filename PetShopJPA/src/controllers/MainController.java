package controllers;

import model.Animal;
import model.Doctor;
import model.Appointment;
import util.DatabaseUtil;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController implements Initializable{
	
	//-----------------Animal------------------------
	@FXML private TableView<Animal> animalTable;
	@FXML private TableColumn<Animal, Integer> idAnimal;
	@FXML private TableColumn<Animal, String> nameAnimal;
	@FXML private TextField fieldidanimal;
	@FXML private TextField fieldnameanimal;
	//-----------------Doctor------------------------
	@FXML private TableView<Doctor> angajatiTable;
	@FXML private TableColumn<Doctor, Integer> idDoctor;
	@FXML private TableColumn<Doctor, String> nameDoctor;
	@FXML private TextField fieldidDoctor;
	@FXML private TextField fieldnameDoctor;
	//-----------------Appointments------------------------
	@FXML private ListView listAppointment;
	@FXML private Label nameAppointment;
	@FXML private Label doctorAppointment;
	@FXML private Label animalAppointment;

	
	public ObservableList<Animal> anima;
	public ObservableList<Doctor> doctors;
	public ObservableList<Appointment> appointments;
	
	
	//Populate table with animals
	public void populateAnimalTable(){
		try {
		DatabaseUtil db = new DatabaseUtil();
		db.setUp();
		List<Animal> animals = (List <Animal>) db.getAllAnimals();	
		anima = FXCollections.observableArrayList(animals);
		db.closeConnection();
		}catch(Exception e) {
			System.out.println("Error");
		}
		
	}
	
	//Add an animal
	public void addAnimal() {
		try {
			DatabaseUtil db = new DatabaseUtil();
			db.setUp();
			int id = Integer.parseInt(fieldidanimal.getText());
			String name = fieldnameanimal.getText();
			db.addAnimal(id,name);
			db.closeConnection();
			populateAnimalTable();
			animalTable.setItems(anima);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//Populate table for doctors
	public void populateAngajatiTable(){
		try {
		DatabaseUtil db = new DatabaseUtil();
		db.setUp();
		List<Doctor> employ = (List <Doctor>) db.getAllDrs();	
		doctors = FXCollections.observableArrayList(employ);
		db.closeConnection();
		}catch(Exception e) {
			System.out.println("Error");
		}
		
	}
	
	//Add employee to database
	public void addEmployee() {
		try {
			DatabaseUtil db = new DatabaseUtil();
			db.setUp();
			int id = Integer.parseInt(fieldidDoctor.getText());
			String name = fieldnameDoctor.getText();
			db.addDr(id,name);
			db.closeConnection();
			populateAngajatiTable();
			angajatiTable.setItems(doctors);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void populateAppointmentList() {
		try {
			DatabaseUtil db = new DatabaseUtil();
			db.setUp();
			List<Appointment> appoint = (List <Appointment>) db.getAllAppointments();	
			appointments = FXCollections.observableArrayList(appoint);
			db.closeConnection();
			}catch(Exception e) {
				System.out.println("Error");
			}
			
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		// TODO Auto-generated method stub
		populateAnimalTable();
		populateAngajatiTable();
		populateAppointmentList();
		
		idAnimal.setCellValueFactory(new PropertyValueFactory<Animal, Integer>("idAnimal"));
		nameAnimal.setCellValueFactory(new PropertyValueFactory<Animal, String>("name"));
		animalTable.setItems(anima);
		
		idDoctor.setCellValueFactory(new PropertyValueFactory<Doctor, Integer>("idPersonalMedical"));
		nameDoctor.setCellValueFactory(new PropertyValueFactory<Doctor, String>("name"));
		
		listAppointment.setItems(appointments);
		
	}

}
