package travelguideapp.ge.travelguide.ui.home.profile.changeCountry;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding4.widget.RxTextView;
import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.model.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;

public class ChooseCountryFragment extends DialogFragment implements ChooseCountryListener {

    public static ChooseCountryFragment getInstance(List<Country> countries, ChooseCountryListener chooseCountryListener) {
        ChooseCountryFragment chooseCountryFragment = new ChooseCountryFragment();
        chooseCountryFragment.chooseCountryListener = chooseCountryListener;
        chooseCountryFragment.countries = countries;
        return chooseCountryFragment;
    }

    private List<Country> countries;
    private ChooseCountryListener chooseCountryListener;

    private EditText searchCountry;
    private ChooseCountryAdapter chooseCountryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_country, container, false);

        try {
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            RecyclerView countryRecycler = view.findViewById(R.id.country_chooser_recycler);
            countryRecycler.setLayoutManager(new LinearLayoutManager(countryRecycler.getContext()));
            countryRecycler.setHasFixedSize(true);
            chooseCountryAdapter = new ChooseCountryAdapter(this);
            chooseCountryAdapter.setCountries(countries);
            countryRecycler.setAdapter(chooseCountryAdapter);

            searchCountry = view.findViewById(R.id.choose_country_search);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            RxTextView.textChanges(searchCountry)
                    .debounce(50, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(CharSequence::toString)
                    .subscribe((Consumer<CharSequence>) charSequence -> {
                        if (!charSequence.toString().isEmpty()) {
                            List<Country> searchedCountries = new ArrayList<>();
                            for (Country current : countries) {
                                if (current.getName().toLowerCase().contains(charSequence) || current.getName().contains(charSequence)) {
                                    if (!searchedCountries.contains(current)) {
                                        searchedCountries.add(current);
                                    }
                                }
                            }
                            if (searchedCountries.size() > 0) {
                                chooseCountryAdapter.setCountries(searchedCountries);
                            } else {
                                chooseCountryAdapter.setCountries(countries);
                            }
                        } else {
                            chooseCountryAdapter.setCountries(countries);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onChooseCountry(Country country) {
        try {
            if (country != null) {
                chooseCountryListener.onChooseCountry(country);
                if (getDialog() != null)
                    getDialog().dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
