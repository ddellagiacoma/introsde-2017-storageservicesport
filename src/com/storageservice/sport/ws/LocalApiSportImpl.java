package com.storageservice.sport.ws;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import com.storageservice.sport.model.Activity;
import com.storageservice.sport.model.Sport;



//Service Implementation

@WebService(endpointInterface = "com.storageservice.sport.ws.LocalApiSportModel", serviceName = "storageServiceSport")
public class LocalApiSportImpl implements LocalApiSportModel {
	
	@Override
	public List<Sport> getSportsList() {

		return Sport.getAll();
	}

	@Override
	public Sport getSport() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Sport> getSportsByWeather(String weather) {

		return Sport.getSportByWeather(weather);
	}
	@Override
	public Activity getActivityBySport(Sport sport, String access_token,String user_id,String refresh_token) {
		Activity activity = Activity.getActivityBySport(sport.getName());
		if (activity == null) {
			activity=new Activity();
			String url = "https://adapterservice.herokuapp.com/FitbitActivities?access_token="+access_token+"&user_id="+user_id+"&refresh_token="+refresh_token;
			try {
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();

				con.setRequestMethod("GET");
				con.setRequestProperty("Accept", "application/json");
				int responseCode = con.getResponseCode();

				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				JSONArray jarr = new JSONArray(response.toString());
				for (int i = 0; i < jarr.length(); i++) {
					JSONObject jobj = jarr.getJSONObject(i);
					if (jobj.getString("name").equals(sport.getName())) {
						activity.setName(jobj.getString("name"));
						activity.setAccessLevel(jobj.getString("accessLevel"));
						activity.setMets(jobj.getDouble("mets"));
						activity.setHasSpeed(jobj.getBoolean("hasSpeed"));
						activity.setIdActivity((jobj.getInt("id")));
						activity.setSport(sport);
						Activity.saveActivity(activity);
					}

				}

			} catch (Exception e) {
				System.out.println("error in getting activity request on FitBitAdapter " + e);
			}

		}
		return activity;
	}
	@Override
	public List<Activity> getFavouriteActivity( String access_token,String user_id,String refresh_token){
		String url = "https://adapterservice.herokuapp.com/FitbitFavoriteActivities?access_token="+access_token+"&user_id="+user_id+"&refresh_token="+refresh_token;
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("Accept", "application/json");
			
			int responseCode = con.getResponseCode();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			List <Activity>activities = new ArrayList();	
			JSONArray jarr = new JSONArray(response.toString());
			for (int i=0; i<jarr.length();i++){
				Activity activity= new Activity();
				JSONObject jobj = jarr.getJSONObject(i);
					activity.setIdActivity(jobj.getInt("activityId"));
					activity.setName(jobj.getString("name"));
					activity.setMets(jobj.getDouble("mets"));
					activities.add(activity);}
		
			return activities;		
		} catch (Exception e) {
			System.out.println("error in getting favourite activity request on FitBitAdapter " + e);
			return null;
		}
		
	}


		
	}

