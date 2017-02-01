package com.storageservice.sport.ws;


import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import com.storageservice.sport.model.Activity;
import com.storageservice.sport.model.Sport;



//service definition
@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) 
public interface LocalApiSportModel {
	  @WebMethod(operationName="getSportsList")
	    @WebResult(name="sports") 
	    public List<Sport> getSportsList();
	    
	    @WebMethod(operationName="getSport")
	    @WebResult(name="sport") 
	    public Sport getSport();
	    
	    @WebMethod(operationName="getSportsByWeather")
	    @WebResult(name="sportsbyWeather") 
	    public List<Sport> getSportsByWeather(@WebParam(name="weather")String weather );
   
	    @WebMethod(operationName="getActivityBySport")
	    @WebResult(name="activity") 
	    public Activity getActivityBySport(@WebParam(name="sport")Sport sport,@WebParam(name="access_token")String access_token,@WebParam(name="user_id")String user_id,@WebParam(name="refresh_token")String refresh_token);
	    
	    @WebMethod(operationName="getFavouriteActivity")
	    @WebResult(name="favouriteActivity")  
	    public List<Activity> getFavouriteActivity(@WebParam(name="access_token")String access_token,@WebParam(name="user_id")String user_id,@WebParam(name="refresh_token")String refresh_token);
	    
  
}