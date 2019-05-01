package domain.model;

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
	@Column(name="USER_ID")
	private long id;
	
	@Column(name="FIRSTNAME")
	private String firstName;
	
	@Column(name="LASTNAME")
	private String lastName;
	
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
		this.lastName = nom;
		this.firstName = prenom;
		this.tel = tel;
	}
	
	@Override
	public String toString() {
		String newLine = System.getProperty("line.separator");
		String ret = newLine + lastName + " " + firstName + " (User id = " + id + ")";
		ret += newLine + newLine + "Age : " + age; 
		ret += newLine + newLine + "e-mail : " + email;
		ret += newLine + newLine + "tel : " + tel;
		return ret;
	}
	
	public String returnJSON() {
		return "{\"nom\":\""+this.lastName+"\", \"prenom\":\"" + this.firstName + "\n, \"age\":\"" + this.age +"\", \"tel\":\"" + this.tel +"\", \"email\":\"" + this.email + "\"";
	}

	public long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((tel == null) ? 0 : tel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		
		if (obj == null || getClass() != obj.getClass()) return false;
		
		User other = (User) obj;
		if (age != other.age) return false;
		
		if (email == null && other.email != null) return false;
		if (email != null && !email.equals(other.email)) return false;
		
		if (firstName == null && other.firstName != null) return false;
		if (firstName != null && !firstName.equals(other.firstName)) return false;
		
		if (id != other.id) return false;
		
		if (lastName == null && other.lastName != null) return false;
		if (lastName != null && !lastName.equals(other.lastName)) return false;
		
		if (tel == null && other.tel != null) return false;
		if (tel != null && !tel.equals(other.tel)) return false;
		
		return true;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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
