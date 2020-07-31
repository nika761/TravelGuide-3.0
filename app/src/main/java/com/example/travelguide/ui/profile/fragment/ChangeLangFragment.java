package com.example.travelguide.ui.profile.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.travelguide.R;
import com.example.travelguide.ui.profile.adapter.recycler.ChangeLangAdapter;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.ui.profile.interfaces.IChangeLangFragment;
import com.example.travelguide.model.request.ChangeLangRequestModel;
import com.example.travelguide.model.response.ChangeLangResponseModel;
import com.example.travelguide.model.response.LanguagesResponseModel;
import com.example.travelguide.ui.profile.presenter.ChangeLangPresenter;

import java.util.List;
import java.util.Objects;

public class ChangeLangFragment extends DialogFragment implements IChangeLangFragment {
    private Context context;
    private ChangeLangPresenter changeLangPresenter;
    private TextView chooseLang, currentLanguage;
    private RecyclerView recyclerView;
    private LottieAnimationView lottieAnimationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_lang, container, false);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow())
                .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        chooseLang = view.findViewById(R.id.choose_language_head);
        recyclerView = view.findViewById(R.id.language_chooser_recycler);
        lottieAnimationView = view.findViewById(R.id.loading_languages);
        changeLangPresenter = new ChangeLangPresenter(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        changeLangPresenter.sentLanguageRequest();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onGetLanguages(LanguagesResponseModel languagesResponseModel) {

        if (languagesResponseModel.getStatus() == 0) {
            initLanguageRecycler(languagesResponseModel.getLanguage());
            lottieAnimationView.setVisibility(View.GONE);
        } else {
            Toast.makeText(context, "Error Language", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onLanguageChange(ChangeLangResponseModel changeLangResponseModel) {

        if (changeLangResponseModel.getStatus() == 0) {
            Toast.makeText(context, "Language Changed", Toast.LENGTH_SHORT).show();
            Objects.requireNonNull(getDialog()).dismiss();
        } else {
            Toast.makeText(context, "Error Language Change", Toast.LENGTH_SHORT).show();
        }

    }

    private void initLanguageRecycler(List updatedLanguagesList) {
        ChangeLangAdapter adapter = new ChangeLangAdapter(context, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setLanguageList(updatedLanguagesList);
    }

    @Override
    public void onLanguageChoose(int langId) {
        HelperPref.saveLanguageId(context, langId);
        ChangeLangRequestModel changeLangRequestModel = new ChangeLangRequestModel(String.valueOf(HelperPref.getLanguageId(context)));
        changeLangPresenter.sentChangeLanguageRequest(changeLangRequestModel,
                "Bearer" + " " + HelperPref.getCurrentAccessToken(context));
    }
}
