package travelguideapp.ge.travelguide.base;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import travelguideapp.ge.travelguide.helper.AuthorizationManager;
import travelguideapp.ge.travelguide.helper.DialogManager;

public abstract class BasePresenterActivity<Presenter extends BasePresenter<?>> extends HomeParentActivity implements BaseViewListener {

    protected Dialog loader;
    private static AlertDialog alertDialog;

    protected Presenter presenter;

    protected void attachPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachLoader();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detachLoader();
        detachPresenter();
    }

    @Override
    public void detachPresenter() {
        try {
            if (presenter != null) {
                presenter.detachView();
                presenter = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showLoader() {
        try {
            loader.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hideLoader() {
        try {
            if (loader.isShowing())
                loader.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void attachLoader() {
        try {
            loader = DialogManager.progressDialog(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void detachLoader() {
        try {
            if (loader != null) {
                loader.dismiss();
                loader = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionError() {
        try {
            if (alertDialog == null) {
                alertDialog = DialogManager.noInternetConnectionDialog(this);
                alertDialog.setOnDismissListener(dialog -> alertDialog = null);
                alertDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAuthenticationError() {
        try {
            AuthorizationManager.logOut(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUnknownError() {
        try {
            DialogManager.defaultDialog(this, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
