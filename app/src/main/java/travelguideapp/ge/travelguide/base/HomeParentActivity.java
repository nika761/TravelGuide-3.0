package travelguideapp.ge.travelguide.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.helper.MyToaster;
import travelguideapp.ge.travelguide.model.customModel.Report;
import travelguideapp.ge.travelguide.model.parcelable.ReportParams;
import travelguideapp.ge.travelguide.model.parcelable.SearchPostParams;
import travelguideapp.ge.travelguide.model.request.SetCommentReportRequest;
import travelguideapp.ge.travelguide.model.request.SetPostReportRequest;
import travelguideapp.ge.travelguide.model.request.SetUserReportRequest;
import travelguideapp.ge.travelguide.model.response.SetReportResponse;
import travelguideapp.ge.travelguide.ui.home.customerUser.CustomerProfileActivity;
import travelguideapp.ge.travelguide.ui.home.feed.HomeFragment;
import travelguideapp.ge.travelguide.ui.home.report.ReportAdapter;
import travelguideapp.ge.travelguide.ui.search.posts.PostByLocationActivity;
import travelguideapp.ge.travelguide.preferences.GlobalPreferences;

/**
 * Created by n.butskhrikidze on 15/11/2020.
 * <p>
 * ეს ჯიგარი ექთივითი იყოს მშობელი ყველა ექთივითისა სადაც ვხსნით პოსტებს სიმონ.
 * <p>
 * თუ ასე არ იზამ, ღმერთმა ხელი მოგიმართოს.
 * <p>
 */

public class HomeParentActivity extends BaseActivity implements HomeParentListener {

    public static final int SHARING_INTENT_REQUEST_CODE = 500;

    private final List<Integer> reportsId = new ArrayList<>();

    private HomeParentPresenter homeParentPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeParentPresenter = new HomeParentPresenter(this);
    }

    public FragmentManager getCurrentChildFragmentManager() {
        try {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.home_fragment_container);
            if (fragment instanceof HomeFragment) {
                return fragment.getChildFragmentManager();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        try {
            FragmentManager currentChildFragmentManager = getCurrentChildFragmentManager();
            if (currentChildFragmentManager == null || currentChildFragmentManager.getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                currentChildFragmentManager.popBackStackImmediate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            super.onBackPressed();
        }
    }

    public void shareContent(String content) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        startActivityForResult(sharingIntent, SHARING_INTENT_REQUEST_CODE);
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

    public void openReportDialog(ReportParams reportParams) {
        try {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            View bottomSheetLayout = View.inflate(this, R.layout.dialog_report, null);

            TextView sentBtn = bottomSheetLayout.findViewById(R.id.report_sent);
            sentBtn.setOnClickListener(v -> {
                switch (reportParams.getReportType()) {
                    case USER:
                        homeParentPresenter.setReport(new SetUserReportRequest(reportParams.getUserId(), reportsId), ReportParams.Type.USER);
                        break;
                    case POST:
                        homeParentPresenter.setReport(new SetPostReportRequest(reportParams.getPostId(), reportsId), ReportParams.Type.POST);
                        break;
                    case COMMENT:
                        homeParentPresenter.setReport(new SetCommentReportRequest(reportParams.getCommentId(), reportsId), ReportParams.Type.COMMENT);
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

    public void startCustomerActivity(int userId) {
        try {
            if (GlobalPreferences.getUserId() != userId) {
                Intent intent = new Intent(this, CustomerProfileActivity.class);
                intent.putExtra("id", userId);
                startActivity(intent);
//                overridePendingTransition(R.anim.anim_activity_slide_in_right, R.anim.anim_activity_slide_out_left);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startSearchPostActivity(Parcelable data) {
        try {
            Intent intent = new Intent(this, PostByLocationActivity.class);
            intent.putExtra(SearchPostParams.POST_SEARCH_PARAMS, data);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if (homeParentPresenter != null)
            homeParentPresenter = null;
        super.onDestroy();
    }

    @Override
    public void onReported(SetReportResponse setReportResponse) {
        try {
            MyToaster.showToast(this, setReportResponse.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
