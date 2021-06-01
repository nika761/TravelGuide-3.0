package travelguideapp.ge.travelguide.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import travelguideapp.ge.travelguide.helper.AuthorizationManager;
import travelguideapp.ge.travelguide.helper.DialogManager;
import travelguideapp.ge.travelguide.helper.MyToaster;

public abstract class BaseFragment<Presenter extends BasePresenter<?>> extends Fragment implements BaseViewListener {

    private int toastCount = 0;
    protected Dialog loader;
    private static AlertDialog alertDialog;

    protected Presenter presenter;

    protected void attachPresenter(Presenter presenter) {
        this.presenter = presenter;
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
    public void onStart() {
        super.onStart();
        attachLoader();
    }

    @Override
    public void onStop() {
        super.onStop();
        detachLoader();
        detachPresenter();
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
            loader = DialogManager.progressDialog(getActivity());
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
    public void onUnknownError() {
        try {
            DialogManager.defaultDialog(getActivity(), null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionError() {
        try {
            if (alertDialog == null) {
                alertDialog = DialogManager.noInternetConnectionDialog(getActivity());
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
            AuthorizationManager.logOut(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void openKeyBoard(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void closeKeyBoard(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void loadAnimation(View target, int animationId, int offset) {
        try {
            Animation animation = AnimationUtils.loadAnimation(getContext(), animationId);
            animation.setStartOffset(offset);
            target.startAnimation(animation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected boolean isNullOrEmpty(String val) {
        return val == null || val.isEmpty();
    }

    protected void showToast(String message) {
        try {
            if (toastCount == 0) {
                toastCount++;
                MyToaster.showToast(getContext(), message);
                new Handler().postDelayed(() -> toastCount = 0, 1800);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setSystemBarTheme(final Activity activity, final boolean isDark) {
        try {
            final int lFlags = activity.getWindow().getDecorView().getSystemUiVisibility();
            activity.getWindow().getDecorView().setSystemUiVisibility(isDark ? (lFlags & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) : (lFlags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
