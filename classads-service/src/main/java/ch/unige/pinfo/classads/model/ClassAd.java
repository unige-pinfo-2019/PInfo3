package ch.unige.pinfo.classads.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="CLASSAD")
public class ClassAd implements Serializable{

	private static final long serialVersionUID = 5725261213022717645L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CLASSAD_ID")
	private long id;
	
	@Column(name="TITRE")
	private String titre;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="PRIX")
	private float prix;
	
	
	
	public ClassAd() {}
	
	public ClassAd(String titre, String description, float prix) {
		super();
		this.titre = titre;
		this.description = description;
		this.prix = prix;
	}
	
	@Override
	public String toString() {
		String NewLigne = System.getProperty("line.separator");
		String ret;
		ret = NewLigne + titre + " (id = " + String.valueOf(id) + ")"+ NewLigne + NewLigne + description + NewLigne + NewLigne + "Prix : " + String.valueOf(prix);
		return ret;
	}
	
	public String returnJSON() {
		return "{\"titre\":\""+this.titre+"\", \"description\":\"" + this.description + "\n, \"prix\":\"" + this.prix + "\"";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrix() {
		return prix;
	}

	public void setPrix(float prix) {
		this.prix = prix;
	}
	


}
