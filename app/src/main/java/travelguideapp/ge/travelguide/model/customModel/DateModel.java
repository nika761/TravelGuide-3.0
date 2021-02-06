package travelguideapp.ge.travelguide.model.customModel;

import android.os.Parcel;
import android.os.Parcelable;

public class DateModel implements Parcelable {
    private int year;
    private int month;
    private int day;

    public DateModel(int year, int month, int day) {
        this.year = year;
        this.month = month + 1;
        this.day = day;
    }

    protected DateModel(Parcel in) {
        year = in.readInt();
        month = in.readInt();
        day = in.readInt();
    }

    public static final Creator<DateModel> CREATOR = new Creator<DateModel>() {
        @Override
        public DateModel createFromParcel(Parcel in) {
            return new DateModel(in);
        }

        @Override
        public DateModel[] newArray(int size) {
            return new DateModel[size];
        }
    };

    @Override
    public String toString() {
        return year + "-" + getStringValueOfMonth(month) + "-" + getStringValueOfDay(day);
    }

    private static String getStringValueOfMonth(int number) {
        if (number < 10)
            return "0" + number;
        else
            return "" + number;
    }

    private static String getStringValueOfDay(int number) {
        if (number < 10)
            return "0" + number;
        else
            return "" + number;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month + 1;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(year);
        dest.writeInt(month);
        dest.writeInt(day);
    }
}
