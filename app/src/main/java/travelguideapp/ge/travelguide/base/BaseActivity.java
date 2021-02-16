package travelguideapp.ge.travelguide.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.login.LoginManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.ClientManager;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.helper.SystemManager;
import travelguideapp.ge.travelguide.model.customModel.Report;
import travelguideapp.ge.travelguide.model.customModel.ReportParams;
import travelguideapp.ge.travelguide.model.parcelable.PostDataSearch;
import travelguideapp.ge.travelguide.model.request.SetCommentReportRequest;
import travelguideapp.ge.travelguide.model.request.SetPostReportRequest;
import travelguideapp.ge.travelguide.model.request.SetUserReportRequest;
import travelguideapp.ge.travelguide.model.response.SetReportResponse;
import travelguideapp.ge.travelguide.ui.home.customerUser.CustomerProfileActivity;
import travelguideapp.ge.travelguide.ui.home.feed.HomeFragment;
import travelguideapp.ge.travelguide.ui.home.report.ReportAdapter;
import travelguideapp.ge.travelguide.ui.login.signIn.SignInActivity;
import travelguideapp.ge.travelguide.ui.search.posts.PostByLocationActivity;
import travelguideapp.ge.travelguide.utility.GlobalPreferences;


public class BaseActivity extends AppCompatActivity implements BaseActivityListener {

    public static final int SHARING_INTENT_REQUEST_CODE = 500;

    private final List<Report> reports = new ArrayList<>();
    private final List<Integer> reportsId = new ArrayList<>();

    private BasePresenter basePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemManager.setLanguage(this);
        basePresenter = new BasePresenter(this);
    }

    public FragmentManager getCurrentChildFragmentManager() {
        try {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.home_fragment_container);
            if (fragment instanceof HomeFragment) {
                return fragment.getChildFragmentManager();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        FragmentManager currentChildFragmentManager = getCurrentChildFragmentManager();
        if (currentChildFragmentManager != null) {
            if (currentChildFragmentManager.getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                currentChildFragmentManager.popBackStackImmediate();
//                try {
//                    CommentFragment commentFragment = (CommentFragment) currentChildFragmentManager.findFragmentByTag(COMMENT_FRAGMENT_TAG);
//                    if (commentFragment != null && commentFragment.isVisible()) {
//                        if (commentFragment.keyBoardShowing) {
//                            commentFragment.bottomSheetDialog.dismiss();
//                        }
//                    } else {
//                        currentChildFragmentManager.popBackStackImmediate();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        } else {
            super.onBackPressed();
        }
    }

    public void shareContent(String content) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        startActivityForResult(sharingIntent, SHARING_INTENT_REQUEST_CODE);
    }

    protected void onAuthenticateError(String message) {
        MyToaster.getToast(this, message);

        Intent intent = new Intent(this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    protected void onConnectionError() {
        MyToaster.getToast(this, getString(R.string.no_internet_connection));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SHARING_INTENT_REQUEST_CODE:
                /*Supposedly TO-DO : handle sharing request by result **/
                break;
        }
    }

    protected void getKeyboard(boolean show, View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                if (show) {
                    inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
                } else {
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openReportDialog(ReportParams reportParams) {
        try {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            View bottomSheetLayout = View.inflate(this, R.layout.dialog_report, null);

            TextView sentBtn = bottomSheetLayout.findViewById(R.id.report_sent);
            sentBtn.setOnClickListener(v -> {
                switch (reportParams.getReportType()) {
                    case USER:
                        basePresenter.setUserReport(GlobalPreferences.getAccessToken(BaseActivity.this), new SetUserReportRequest(reportParams.getUserId(), reportsId));
                        break;
                    case POST:
                        basePresenter.setPostReport(GlobalPreferences.getAccessToken(BaseActivity.this), new SetPostReportRequest(reportParams.getPostId(), reportsId));
                        break;
                    case COMMENT:
                        basePresenter.setCommentReport(GlobalPreferences.getAccessToken(BaseActivity.this), new SetCommentReportRequest(reportParams.getCommentId(), reportsId));
                        break;
                }
                bottomSheetDialog.dismiss();
            });

            RecyclerView recyclerView = bottomSheetLayout.findViewById(R.id.report_recycler);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new ReportAdapter(getReports(), new ReportAdapter.OnReportChooseCallback() {
                @Override
                public void onReportAdd(Report report) {
                    reportsId.add(report.getReportReasonId());
                }

                @Override
                public void onReportRemove(Report report) {
                    reportsId.remove(report.getReportReasonId());
                }
            }));

            bottomSheetDialog.setContentView(bottomSheetLayout);
            bottomSheetDialog.setOnDismissListener(dialog -> reportsId.clear());
            bottomSheetDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private List<Report> getReports() {
        List<Report> reports = new ArrayList<>();
        reports.add(new Report(getString(R.string.report_profile_picture), 1));
        reports.add(new Report(getString(R.string.report_sexual_content), 2));
        reports.add(new Report(getString(R.string.report_spam), 3));
        reports.add(new Report(getString(R.string.report_hate_speech), 4));
        reports.add(new Report(getString(R.string.report_fraud), 5));
        reports.add(new Report(getString(R.string.report_other), 6));
        return reports;
    }

    public void startLogOut() {
        try {
            String loginType = GlobalPreferences.getLoginType(this);

            switch (loginType) {
                case GlobalPreferences.FACEBOOK:
                    logOutFromFacebook();
                    break;

                case GlobalPreferences.GOOGLE:
                    logOutFromGoogle();
                    break;

                case GlobalPreferences.TRAVEL_GUIDE:
                    onLogOutSuccess();
                    break;
            }
        } catch (Exception e) {
            MyToaster.getUnknownErrorToast(this);
            e.printStackTrace();
        }
    }

    public void logOutFromFacebook() {
        try {
            LoginManager.getInstance().logOut();
            onLogOutSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logOutFromGoogle() {
        ClientManager.googleSignInClient(this).signOut()
                .addOnCompleteListener(this, task -> onLogOutSuccess())
                .addOnCanceledListener(this, () -> MyToaster.getToast(this, "Canceled"))
                .addOnFailureListener(this, e -> MyToaster.getToast(this, e.getMessage() + "Google Failed"));
    }

    public void onLogOutSuccess() {
        GlobalPreferences.removeAccessToken(this);
        GlobalPreferences.removeLoginType(this);
        GlobalPreferences.removeUserId(this);
        GlobalPreferences.removeUserRole(this);
        GlobalPreferences.removeUserProfileInfo(this);
        Intent intent = new Intent(BaseActivity.this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void startCustomerActivity(int userId) {
        try {
            if (GlobalPreferences.getUserId(this) != userId) {
                Intent intent = new Intent(this, CustomerProfileActivity.class);
                intent.putExtra("id", userId);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_activity_slide_in_right, R.anim.anim_activity_slide_out_left);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startSearchPostActivity(Parcelable data) {
        try {
            Intent intent = new Intent(this, PostByLocationActivity.class);
            intent.putExtra(PostDataSearch.INTENT_KEY_SEARCH, data);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if (basePresenter != null)
            basePresenter = null;
        super.onDestroy();
    }

    @Override
    public void onReported(SetReportResponse setReportResponse) {
        try {
            MyToaster.getToast(this, setReportResponse.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
