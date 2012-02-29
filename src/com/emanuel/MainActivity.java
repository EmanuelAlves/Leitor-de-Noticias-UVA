package com.emanuel;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ListView;



public class MainActivity extends ListActivity implements Runnable{
	
	public ListView list = null;
	ArrayList<Noticia> noticias = null;
	NoticiaAdapter adapter = null;
	private ProgressDialog barra = null;
	HttpAsyncTask page = null;
	private Message msg = null;
	
		
	@Override 
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		page = (HttpAsyncTask) new HttpAsyncTask().execute();
		
		list = getListView();
		
		barra = ProgressDialog.show(this, "Aguarde", "Carregando as Notícias...");
		
		Thread thread = new Thread(this);
		thread.start();
		
	}
	
	
	//método pega o código e tranforma em uma lista de objetos noticias
	//lista já declarada no inicio da classe
	public void trabalharCodigo(String codigo_pag)
	{	
	
		int ini = codigo_pag.indexOf("id=\"amplia_texto\">") + 18;
		int fim = codigo_pag.indexOf("id=\"box_direito\">");
		
		codigo_pag = codigo_pag.substring(ini, fim);
		
		Noticia noticia = null;
		noticias = new ArrayList<Noticia>();
			
		Pattern patternLink = Pattern.compile("href=\"(.*?)</a>");
			
		Matcher m = patternLink.matcher(codigo_pag);
			
		String aux = null;
			
		int i;		
			
		while(m.find())
		{
			noticia = new Noticia();
				
			aux = m.group(1);
				
			noticia.setLink(aux.substring(0,aux.indexOf("\"")));
				
			i = aux.indexOf("\">") + 2;
				
			noticia.setTitulo(aux.substring(i));
				
			noticias.add(noticia);				
		}				
	}
	
	
	//refresh
	private void refresh()
	{
		Intent refresh = new Intent(this, MainActivity.class);
		startActivity(refresh);
		this.finish();
	}
	
	
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
         
        //abre o navegar com o endereço da noticia
        String url = "http://www.uvanet.br/";
        url += noticias.get(position).getLink();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);        
    }
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	   MenuItem sobre = menu.add(0,1,0,"Sobre");
	   sobre.setIcon(R.drawable.ic_sobre);
	   
	   MenuItem atualizar = menu.add(0,2,0,"Atualizar");
	   atualizar.setIcon(R.drawable.ic_atualizar);
	   
	   return super.onCreateOptionsMenu(menu);
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if(item.getItemId() == 1)
		{
			Intent i = new Intent(getApplicationContext(), SobreActivity.class);
			startActivity(i);
			
		}
		if(item.getItemId() == 2)
		{
			refresh();
		}
		return true;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
				
		String codigo = null;
		try {
			codigo = page.get();
		} catch (InterruptedException e) {
			//e.printStackTrace();
		} catch (ExecutionException e) {
			//e.printStackTrace();
		}
		
		if(codigo != null)
		{
			trabalharCodigo(codigo);			
				
			adapter = new NoticiaAdapter(getApplicationContext(),noticias);											
		}
		
		msg = new Message();
		msg.arg1 = 1;
		handler.sendMessage(msg);
		
	}

	
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg)
		{
			if(msg.arg1 == 1)
			{
				barra.dismiss();
			
				list.setAdapter(adapter);
			}
			
		}
	};	

}
