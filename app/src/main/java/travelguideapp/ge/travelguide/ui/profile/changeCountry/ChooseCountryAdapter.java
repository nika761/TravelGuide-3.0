package travelguideapp.ge.travelguide.ui.profile.changeCountry;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.model.customModel.Country;

import java.util.List;

public class ChooseCountryAdapter extends RecyclerView.Adapter<ChooseCountryAdapter.CountryHolder> {

    private List<Country> countries;

    private final ChooseCountryListener chooseCountryListener;

    ChooseCountryAdapter(ChooseCountryListener chooseCountryListener) {
        this.chooseCountryListener = chooseCountryListener;
    }

    @NonNull
    @Override
    public CountryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_countries, parent, false);
        return new CountryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemCount() {
        return countries.size();
    }

    void setCountries(List<Country> countries) {
        this.countries = countries;
        notifyDataSetChanged();
    }

    class CountryHolder extends RecyclerView.ViewHolder {

        TextView countryName;

        CountryHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.item_country_name);
            countryName.setOnClickListener(v -> {
                try {
                    countryName.setTextColor(countryName.getContext().getColor(R.color.yellowTextView));
                    chooseCountryListener.onChoseCountry(countries.get(getLayoutPosition()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        void onBind(int position) {
            try {
                countryName.setText(countries.get(position).getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
