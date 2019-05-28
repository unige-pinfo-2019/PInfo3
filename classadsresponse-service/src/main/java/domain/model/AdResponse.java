package domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.JsonObject;


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
	private LocalDateTime time;
	
	@Column(name = "FLAG")
	private boolean flag;
	
	
	/***** Constructors *****/
	
	public AdResponse() {}
	
	public AdResponse(long aid, long uid, String rep, boolean flg) {
		super();
		this.adID = aid;
		this.userID = uid;
		this.response = rep;
		this.time = LocalDateTime.now();
		this.flag = flg = true;
		
	}
	
	/***** Manipulation *****/
	/* Returns attributes and their default values in a json format */
	public static JsonObject getAttributes() {
		JsonObject json = new JsonObject();
		json.addProperty(adIDField, 0);
		json.addProperty(userIDField, 0);
		json.addProperty(responseField, "");
		json.addProperty(timeField, "");
		json.addProperty(flagField, true);
		return json;
	}
	
	
	/***** Getters and setters *****/

	public static String getIdField() {
		return idField;
	}

	public static void setIdField(String idField) {
		AdResponse.idField = idField;
	}

	public static String getAdIDField() {
		return adIDField;
	}

	public static void setAdIDField(String adIDField) {
		AdResponse.adIDField = adIDField;
	}

	public static String getUserIDField() {
		return userIDField;
	}

	public static void setUserIDField(String userIDField) {
		AdResponse.userIDField = userIDField;
	}

	public static String getResponseField() {
		return responseField;
	}

	public static void setResponseField(String responseField) {
		AdResponse.responseField = responseField;
	}

	public static String getTimeField() {
		return timeField;
	}

	public static void setTimeField(String timeField) {
		AdResponse.timeField = timeField;
	}

	public static String getFlagField() {
		return flagField;
	}

	public static void setFlagField(String flagField) {
		AdResponse.flagField = flagField;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAdID() {
		return adID;
	}

	public void setAdID(long adID) {
		this.adID = adID;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
