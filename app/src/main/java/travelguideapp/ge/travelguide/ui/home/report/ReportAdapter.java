package travelguideapp.ge.travelguide.ui.home.report;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import travelguideapp.ge.travelguide.R;
import travelguideapp.ge.travelguide.model.customModel.Report;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportHolder> {

    private final List<Report> reports;
    private final OnReportChooseCallback callback;

    public ReportAdapter(List<Report> reports, OnReportChooseCallback callback) {
        this.reports = reports;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ReportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReportHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReportHolder holder, int position) {
        holder.bindUI(position);
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public class ReportHolder extends RecyclerView.ViewHolder {

        TextView reportText;
        CheckBox reportBox;
        Animation animation;

        public ReportHolder(@NonNull View itemView) {
            super(itemView);
            reportText = itemView.findViewById(R.id.report_text);

            reportBox = itemView.findViewById(R.id.report_box);
            reportBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                try {
                    if (isChecked) {
                        callback.onReportAdd(reports.get(getLayoutPosition()));
                    } else {
                        callback.onReportRemove(reports.get(getLayoutPosition()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            animation = AnimationUtils.loadAnimation(reportBox.getContext(), R.anim.anim_activity_slide_in_right);
        }

        void bindUI(int position) {
            try {
                itemView.startAnimation(animation);
                reportText.setText(reports.get(position).getReportReason());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface OnReportChooseCallback {

        void onReportAdd(Report report);

        void onReportRemove(Report report);

    }
}
