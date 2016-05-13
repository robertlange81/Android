package sageone.abacus.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Created by otomaske on 04.02.2016.
 *
 * The calculation result data class.
 */
public class CalculationData implements Parcelable {

    public double latitude;
    public double longitude;

    private static NumberFormat format;

    protected CalculationData(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<CalculationData> CREATOR = new Creator<CalculationData>() {
        @Override
        public CalculationData createFromParcel(Parcel in) {
            return new CalculationData(in);
        }

        @Override
        public CalculationData[] newArray(int size) {
            return new CalculationData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    private static Double parse(String value) throws ParseException {
        if(format == null) {
            format = NumberFormat.getInstance(Locale.GERMANY);
        }

        return format.parse(value).doubleValue();
    }
}