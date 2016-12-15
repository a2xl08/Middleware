package model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.*;

@Entity
@Table(name = "EVENTS")

@NamedQueries( {

@NamedQuery(name = "All", query ="SELECT * FROM EVENTS"),

@NamedQuery(name = "EventByName", query = "SELECT * FROM EVENTS WHERE NAME= :name"),

})


public class Event {

	@Id
	@Column(name="NAME")
	private String nom;
	
	@Column(name="CATEGORIE")
	private String categorie;
	
	@Column(name="DATE")
	private String date;
	
	@Column(name="ARTIST")
	private String artiste;
	
	@Column(name="PLACES")
	private ArrayList<ArrayList<Boolean>> places;
	
	public Event(String categorie, String date) {
		super();
		this.categorie = categorie;
		this.date = date;
		this.places = new ArrayList<ArrayList<Boolean>>();
		this.places.add(new ArrayList<Boolean>(25));
		this.places.add(new ArrayList<Boolean>(45));
		this.places.add(new ArrayList<Boolean>(100));
		this.places.add(new ArrayList<Boolean>(500));
		for (int i=0; i<4; i++){
			for (int k=0; k<this.places.get(i).size(); k++){
				this.places.get(i).set(k, true);
			}
		}
	}
	
	public String getNom() {
		return nom;
	}

	public String getArtiste() {
		return artiste;
	}

	public String getCategorie() {
		return categorie;
	}

	public String getDate() {
		return date;
	}
	
	public ArrayList<ArrayList<Boolean>> getPlaces(){
		return this.places;
	}
	
	public boolean[] isComplete(){
		boolean[] tab = {true, true, true, true, true};
		for (int i=0; i<4; i++){
			for (boolean bool : places.get(i)){
				if (bool){
					tab[i+1]=false;
				}
			}
		}
		tab[0]=(tab[1] && tab[2] && tab[3] && tab[4]);
		return tab;
	}
	
	public double regularprice(){
		double prix=0;
		switch(categorie){
		case "C1":
			prix=5;
			break;
		case "C2":
			prix=10;
			break;
		case "C3":
			prix=20;
			break;
		case "C4":
			prix=50;
			break;
		}
		return prix;
	}
			
	public double price(String place){
		double prix=0;
		switch(categorie){
		case "C1":
			prix=5;
			break;
		case "C2":
			prix=10;
			break;
		case "C3":
			prix=20;
			break;
		case "C4":
			prix=50;
			break;
		}
		
		String catplace = place.split(" ")[0];
		
		switch(catplace){
		case "A":
			return prix*3;
		case "B":
			return prix*2.5;
		case "C":
			return prix*2;
		case "D":
			return prix;
		}
		return prix;
	}
	
	public void reservation(String place){
		String[] tab = place.split(" ");
		int index=0;
		Integer numero = Integer.parseInt(tab[1]);
		switch(tab[0]){
		case "B" : 
			index = 1;
			break;
		case "C":
			index = 2;
			break;
		case "D":
			index = 3;
			break;	
		}
		if (places.get(index).get(numero)){
			places.get(index).set(numero, false);
			System.out.println("Votre place a bien été réservée");
			System.out.println("Nous vous avons prélevé "+price(place)+"€");
		} else {
			System.out.println("Cette place n'est pas disponible.");
		}
		return;
	}
	
	
	
	
	
	
}
