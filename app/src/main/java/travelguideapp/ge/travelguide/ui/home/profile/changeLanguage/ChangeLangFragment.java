package travelguideapp.ge.travelguide.ui.home.profile.changeLanguage;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;
import travelguideapp.ge.travelguide.model.request.ChangeLangRequest;
import travelguideapp.ge.travelguide.model.response.ChangeLangResponse;
import travelguideapp.ge.travelguide.model.response.LanguagesResponse;

import java.util.List;


public class ChangeLangFragment extends DialogFragment implements ChangeLangListener {
    private Context context;
    private ChangeLangPresenter changeLangPresenter;
    private TextView chooseLang, currentLanguage;
    private RecyclerView recyclerView;
    private LottieAnimationView lottieAnimationView;

    private int languageId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_lang, container, false);
        try {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        chooseLang = view.findViewById(R.id.choose_language_head);
        recyclerView = view.findViewById(R.id.language_chooser_recycler);
        lottieAnimationView = view.findViewById(R.id.loading_languages);
        changeLangPresenter = new ChangeLangPresenter(this);

        return view;
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
    public void onGetLanguages(LanguagesResponse languagesResponse) {

        if (languagesResponse.getStatus() == 0) {
            initLanguageRecycler(languagesResponse.getLanguage());
            lottieAnimationView.setVisibility(View.GONE);
        } else {
            MyToaster.getErrorToaster(context, "Try Again");
        }

    }

    @Override
    public void onLanguageChange(ChangeLangResponse changeLangResponse) {
        if (changeLangResponse.getStatus() == 0) {
            GlobalPreferences.saveLanguageId(context, languageId);
//            Toast.makeText(context, "Language Changed", Toast.LENGTH_SHORT).show();
            if (getDialog() != null)
                getDialog().dismiss();
        }
//            Toast.makeText(context, "Error Language Change", Toast.LENGTH_SHORT).show();

    }

    private void initLanguageRecycler(List<LanguagesResponse.Language> updatedLanguagesList) {
        ChangeLangAdapter adapter = new ChangeLangAdapter(context, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setLanguageList(updatedLanguagesList);
    }

    @Override
    public void onLanguageChoose(int langId) {
        try {
            this.languageId = langId;
            ChangeLangRequest changeLangRequest = new ChangeLangRequest(String.valueOf(GlobalPreferences.getLanguageId(context)));
            changeLangPresenter.sentChangeLanguageRequest(changeLangRequest, GlobalPreferences.getAccessToken(context));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError() {
        MyToaster.getErrorToaster(context, "Try Again");
    }

}
