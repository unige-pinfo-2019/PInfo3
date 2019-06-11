package domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="ADRESPONSE")
public class AdResponse implements Serializable {

	private static final long serialVersionUID = 1951233445258493142L;
	
	private static String idField = "id";
	private static String adIDField = "adID";
	private static String userIDField = "userID";
	private static String responseField = "response";
	private static String timeField = "time";
	private static String flagField = "flag";
	
	@Id
	// @SequenceGenerator(name = "ADRESPONSE_SEQ", sequenceName = "ADRESPONSE_SEQ")
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ADRESPONSE_ID")
	private long id;
	
	@Column(name="AD_ID")
	private long adID;
	
	@Column(name="USER_ID")
	private long userID;
	
	@Column(name="RESPONSE")
	private String response;
	
	@Column(name = "RESPONSE_TIME")
	private LocalDateTime time = LocalDateTime.now();
	
	@Column(name = "FLAG")
	private boolean flag;
	
	
	/***** Constructors *****/
	
	public AdResponse() {}
	
	public AdResponse(long aid, long uid, String rep, boolean flg) {
		super();
		this.adID = aid;
		this.userID = uid;
		this.response = rep;
		this.flag = flg;
		this.time = LocalDateTime.now();
		
	}
	
	/***** Manipulation *****/


	public static String getAdIDField() {
		return adIDField;
	}

	public static String getUserIDField() {
		return userIDField;
	}

	public static String getTimeField() {
		return timeField;
	}

	public static String getIdField() {
		return idField;
	}

	public static String getResponseField() {
		return responseField;
	}

	public static String getFlagField() {
		return flagField;
	}

	
	

	
}
