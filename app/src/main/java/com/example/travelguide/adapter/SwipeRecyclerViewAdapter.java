package com.example.travelguide.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.travelguide.R;
import com.example.travelguide.activity.SignInActivity;
import com.example.travelguide.model.User;
import com.example.travelguide.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SwipeRecyclerViewAdapter extends RecyclerSwipeAdapter<SwipeRecyclerViewAdapter.SimpleViewHolder> {
    private Context mContext;
    private List<User> userList;
    private int selectedUserPosition = -1;

    public SwipeRecyclerViewAdapter(Context context, List<User> objects) {
        this.mContext = context;
        this.userList = objects;
    }


    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_layout, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull final SimpleViewHolder viewHolder, final int position) {
        final User currentUser = userList.get(position);

        if (position == selectedUserPosition) {
            viewHolder.linearLayout.setVisibility(View.VISIBLE);
        } else {
            viewHolder.linearLayout.setVisibility(View.GONE);
        }

        setDataOnViews(viewHolder, currentUser);

        onSwipeLayout(viewHolder, position, currentUser);

        mItemManger.bindView(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    private void setDataOnViews(SimpleViewHolder viewHolder, User currentUser) {

        if (currentUser.getUrl() != null) {
            Glide.with(mContext)
                    .applyDefaultRequestOptions(new RequestOptions().centerCrop())
                    .load(currentUser.getUrl())
                    .into(viewHolder.userImage);
        } else {
            Glide.with(mContext)
                    .applyDefaultRequestOptions(new RequestOptions().centerCrop())
                    .load(R.drawable.default_profile_image)
                    .centerCrop()
                    .into(viewHolder.userImage);
            viewHolder.userImage.setMinimumHeight(78);
            viewHolder.userImage.setMinimumWidth(78);
        }

        viewHolder.userName.setText(mContext.getString(R.string.continue_as) + " " + currentUser.getName() + " " + currentUser.getLastName() + "");
        if (currentUser.getLoginType() != null && currentUser.getLoginType().equals("facebook")) {
            viewHolder.loginTypeImg.setBackground(mContext.getDrawable(R.drawable.facebook_little));
        } else if (currentUser.getLoginType() != null && currentUser.getLoginType().equals("google")) {
            viewHolder.loginTypeImg.setBackground(mContext.getDrawable(R.drawable.google_little));
        }
    }

    private void onSwipeLayout(SimpleViewHolder viewHolder, int position, User currentUser) {

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));

        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wraper));

        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(v -> Toast.makeText(mContext, " Click : " + currentUser.getName() + " \n" + currentUser.getLastName(), Toast.LENGTH_SHORT).show());

        viewHolder.btnLocation.setOnClickListener(v -> Toast.makeText(v.getContext(), "Clicked on Information " + viewHolder.userName.getText().toString(), Toast.LENGTH_SHORT).show());

//        viewHolder.Share.setOnClickListener(view -> Toast.makeText(view.getContext(), "Clicked on Share " + viewHolder.userName.getText().toString(), Toast.LENGTH_SHORT).show());

//        viewHolder.Edit.setOnClickListener(view -> Toast.makeText(view.getContext(), "Clicked on Edit  " + viewHolder.userName.getText().toString(), Toast.LENGTH_SHORT).show());

        viewHolder.deleteUser.setOnClickListener(v -> deleteUser(position, viewHolder));

    }

    private void deleteUser(int selectedUserPosition, SimpleViewHolder viewHolder) {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                .setTitle("Delete User?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                    Utils.deleteUser(mContext, userList.get(selectedUserPosition));
                    userList.remove(selectedUserPosition);
                    notifyItemRemoved(selectedUserPosition);
                    userList = Utils.getSavedUsers(mContext);
                    notifyItemRangeChanged(selectedUserPosition, userList.size());
                    mItemManger.closeAllItems();
                    Toast.makeText(mContext, "User Deleted", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                    if (userList.size() == 0) {
                        Intent intent = new Intent(mContext, SignInActivity.class);
                        mContext.startActivity(intent);
                    }

                }).setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss()).create();
        alertDialog.show();
    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        CircleImageView userImage;
        TextView userName, signInBtn, cancelBtn, passwordHead, forgotPassword;
        LinearLayout linearLayout;
        EditText savedUserPassword;
        ImageView loginTypeImg;
        ImageButton deleteUser, btnLocation;

        SimpleViewHolder(View itemView) {
            super(itemView);
            initUI(itemView);
        }


        private void initUI(View itemView) {
            swipeLayout = itemView.findViewById(R.id.swipe);
            deleteUser = itemView.findViewById(R.id.Delete);
            btnLocation = itemView.findViewById(R.id.btnLocation);
            userImage = itemView.findViewById(R.id.saved_user_image);
            userName = itemView.findViewById(R.id.saved_user_name);
            linearLayout = itemView.findViewById(R.id.saved_user_hide_content);
            signInBtn = itemView.findViewById(R.id.saved_user_sign_in_btn);
            cancelBtn = itemView.findViewById(R.id.saved_user_cancel_btn);
            savedUserPassword = itemView.findViewById(R.id.saved_user_password_edit);
            passwordHead = itemView.findViewById(R.id.saved_user_password_head);
            forgotPassword = itemView.findViewById(R.id.save_user_forgot_password);
            loginTypeImg = itemView.findViewById(R.id.saved_user_login_type);
        }

    }
}
