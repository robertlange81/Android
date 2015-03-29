/**
 * FuelStationAdapter.java
 * 
 * (c) Robert Lange 2015
 * Alle Rechte beim Autor.
 */
package com.robertlange.sparesprit;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Diese Klasse bildet das Modell fuer die Listenansicht der App.
 * 
 * @author Robert Lange
 * @see BaseAdapter
 */
public class FuelStationAdapter extends BaseAdapter {

	private final List<FuelStation> stationList;
	private final LayoutInflater inflator;

	public FuelStationAdapter(Context context) {
		inflator = LayoutInflater.from(context);
		stationList = new ArrayList<FuelStation>();
		// FuelStation fuer alle Monate ermitteln
        for(FuelStation fs : FuelStations.getAll())
        {
			stationList.add(fs);
		}
        notifyDataSetChanged();
	}

    public enum SortOrder {
        DISTANCE,
        PRICE,
        COMBINED
    }

    public void sortBy(SortOrder o)
    {
        if(o.equals(SortOrder.DISTANCE))
            Collections.sort(stationList, new DistanceComparator());

        if(o.equals(SortOrder.PRICE))
            Collections.sort(stationList, new PriceComparator());

        if(o.equals(SortOrder.COMBINED))
            Collections.sort(stationList, new CombinedComparator());

        notifyDataSetChanged();
    }

	@Override
	public int getCount() {
		return stationList.size();
	}

	@Override
	public Object getItem(int position) {
		return stationList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View rowView, ViewGroup parent) {
		ViewHolder holder;

		// falls noetig, rowView bauen
		if (rowView == null) {
			// Layoutdatei entfalten
			rowView = inflator.inflate(R.layout.row, parent, false);

			// Holder erzeugen
			holder = new ViewHolder();
            holder.icon = (ImageView) rowView.findViewById(R.id.icon);
			holder.name = (TextView) rowView.findViewById(R.id.stationName);
			holder.distance = (TextView) rowView
					.findViewById(R.id.distance);
            holder.price = (TextView) rowView
                    .findViewById(R.id.price);
            holder.openingTimes = (TextView) rowView
                    .findViewById(R.id.openingTimes);

			rowView.setTag(holder);
		} else {
			// Holder bereits vorhanden
			holder = (ViewHolder) rowView.getTag();
		}

		Context context = parent.getContext();
		FuelStation station = (FuelStation) getItem(position);
        holder.icon.setImageResource(station.getIdForDrawable());
		holder.name.setText(station.getName(context));
        holder.distance.setText(String.format("%.2f", station.getDistance()) + " km");
        holder.price.setText(String.format("%.2f", station.getPrice()) + " €");
        holder.openingTimes.setText(station.getOpenFrom() + "-" + station.getOpenTo());

		if (++position >= getCount()) {
			position = 0;
		}
		station = (FuelStation) getItem(position);

		return rowView;
	}

	static class ViewHolder {
		TextView name, distance, price, openingTimes;
		ImageView icon;
	}
}
