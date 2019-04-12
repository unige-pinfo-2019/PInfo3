package ch.unige.pinfo.classads.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER")
public class User implements Serializable {

	private static final long serialVersionUID = 1881800838175136618L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CLASSAD_ID")
	private long id;
	
	@Column(name="PRENOM")
	private String prenom;
	
	@Column(name="NOM")
	private String nom;
	
	@Column(name="AGE")
	private int age;
	
	@Column(name="TEL")
	private String tel;
	
	@Column(name="EMAIL")
	private String email;
	
	public User() {}
	
	public User(String prenom, String nom, int age, String email, String tel) {
		this.age = age;
		this.email = email;
		this.nom = nom;
		this.prenom = prenom;
		this.tel = tel;
	}
	
	@Override
	public String toString() {
		String NewLigne = System.getProperty("line.separator");
		String ret;
		ret = NewLigne + nom + " " + prenom + " (id = " + String.valueOf(id) + ")";
		ret += NewLigne + NewLigne + "Age : " + String.valueOf(age); 
		ret += NewLigne + NewLigne + "e-mail : " + email;
		ret += NewLigne + NewLigne + "tel : " + tel;
		return ret;
	}
	
	public String returnJSON() {
		return "{\"nom\":\""+this.nom+"\", \"prenom\":\"" + this.prenom + "\n, \"age\":\"" + this.age +"\", \"tel\":\"" + this.tel +"\", \"email\":\"" + this.email + "\"";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
