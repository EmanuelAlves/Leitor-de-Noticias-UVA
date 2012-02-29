package com.emanuel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


import android.os.AsyncTask;
import android.util.Log;




public class HttpAsyncTask extends AsyncTask<String, Void, String>
{
	
	String codigo = null;

	@Override
	protected String doInBackground(String... url_link) {
		
		try{
			
			URL url = new URL("http://www.uvanet.br/inicial.php");
	        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        String line;
	        
	        while ((line = reader.readLine()) != null) {
	        	codigo += line;
	        }
	        reader.close();
	        	        
	        return codigo;
		}
		catch (Exception e) {
			// TODO: handle exception
			Log.e("Erro na ASYNCTASK", "erro: "+e.getMessage());
			return null;			
		}
	}	
		
	
}