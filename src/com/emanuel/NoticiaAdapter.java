package com.emanuel;


import java.util.List;
 
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;
 
public class NoticiaAdapter extends BaseAdapter {
		
	private List<Noticia> noticias;
	private LayoutInflater mInflater;
	private ViewHolder holder;
 
 
	static class ViewHolder{
		private TextView tvTitulo;			
	} 
 
	public NoticiaAdapter(Context context, List<Noticia> noticias) {
		mInflater = LayoutInflater.from(context);
		this.noticias = noticias;
	}
 
	@Override
	public int getCount() {
		return noticias.size();
	}
 
	@Override
	public Object getItem(int index) {
		return noticias.get(index);
	}
 
	@Override
	public long getItemId(int index) {		
		return index;
	}
 
	@Override
	public View getView(int posicao, View convertView, ViewGroup arg2) {
 
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.noticia_adapter, null);
			holder = new ViewHolder();
 
			holder.tvTitulo = (TextView) convertView.findViewById(R.id.titulo);
						 
			convertView.setTag(holder);
 
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
 
 
		Noticia p = noticias.get(posicao);
 
		holder.tvTitulo.setText(p.getTitulo());		
 
		return convertView;
	}

	public List<Noticia> getNoticias() {
		return noticias;
	}

	public void setNoticias(List<Noticia> noticias) {
		this.noticias = noticias;
	}
 
}
