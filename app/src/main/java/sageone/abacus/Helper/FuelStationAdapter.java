package sageone.abacus.Helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import sageone.abacus.Comparator.CombinedComparator;
import sageone.abacus.Comparator.DistanceComparator;
import sageone.abacus.Comparator.PriceComparator;
import sageone.abacus.Models.FuelStation;
import sageone.abacus.Models.FuelStations;
import sageone.abacus.Models.StationList;
import sageone.abacus.R;

/**
 * Diese Klasse bildet das Modell fuer die Listenansicht der App.
 *
 * @author Robert Lange
 * @see BaseAdapter
 */
public class FuelStationAdapter extends BaseAdapter {

    private final List<StationList> stationList;
    private final LayoutInflater inflator;
    public  static Context contextHolder;

    public FuelStationAdapter(Context context) {
        contextHolder = context;
        inflator = LayoutInflater.from(context);
        stationList = new ArrayList<StationList>();
        /* FuelStation fuer alle Monate ermitteln
        for(StationList fs : FuelStations.getAll())
        {
            stationList.add(fs);
        }
        notifyDataSetChanged();*/
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
            holder.favorit = (CheckBox) rowView
                    .findViewById(R.id.favorite);

            holder.favorit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Set<Integer> favorits = FuelStationAdapter.readPrefs("favorits");
                    if (((CheckBox) v).isChecked()) {
                        favorits.add(v.getId());
                    } else {
                        favorits.remove(v.getId());
                    }
                    FuelStationAdapter.savePrefs("favorits", favorits);
                }
            });

            rowView.setTag(holder);
        } else {
            // Holder bereits vorhanden
            holder = (ViewHolder) rowView.getTag();
        }

        Context context = parent.getContext();
        FuelStation station = (FuelStation) getItem(position);
        holder.favorit.setEnabled(true);
        holder.favorit.setId(station.getId());
        Set<Integer> favorits = this.readPrefs("favorits");
        if(favorits.contains(station.getId())) {
            holder.favorit.setChecked(true);
        }

        //holder.icon.setImageResource(station.getIdForDrawable());
        holder.name.setText(station.getOwner());
        holder.distance.setText(String.format("%.2f", station.getDistance()) + " km");
        holder.price.setText(String.format("%.3f", station.getPrice()) + " €/L");
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
        CheckBox favorit;
    }

    public static boolean savePrefs(String name, Set<Integer> set)
    {
        SharedPreferences prefs = contextHolder.getSharedPreferences(name, 0);
        SharedPreferences.Editor editor = prefs.edit();

        String value = "";
        for(Integer x : set) {
            value += x.toString() + ";";
        }
        editor.putString(name, value);

        return editor.commit();
    }

    public static Set<Integer> readPrefs(String name)
    {
        SharedPreferences prefs = contextHolder.getSharedPreferences(name, 0);
        String value = prefs.getString(name, null);
        String[] parts;
        Set set = new LinkedHashSet();

        if(value != null) {
            parts = value.split(";");
        } else {
            parts = new String[0];
        }

        for(String x : parts) {
            try {
                set.add(Integer.parseInt(x));
            } catch (Exception ex) {
                Log.i(ex.getMessage(), "Kann Favorit " + x + " nicht parsen.");
            }
        }
        return set;
    }
}
