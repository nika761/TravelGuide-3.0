package com.travelguide.travelguide.ui.login.loggedUsers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.travelguide.travelguide.R;
import com.travelguide.travelguide.helper.HelperMedia;
import com.travelguide.travelguide.model.response.LoginResponse;
import com.travelguide.travelguide.ui.login.password.FPasswordActivity;
import com.travelguide.travelguide.ui.login.signIn.SignInActivity;
import com.travelguide.travelguide.ui.home.HomePageActivity;
import com.travelguide.travelguide.helper.HelperClients;
import com.travelguide.travelguide.helper.HelperPref;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.travelguide.travelguide.helper.HelperPref.FACEBOOK;
import static com.travelguide.travelguide.helper.HelperPref.GOOGLE;

public class SavedUserAdapter extends RecyclerSwipeAdapter<SavedUserAdapter.SimpleViewHolder> {

    private Context mContext;
    private List<LoginResponse.User> userList;
    private int selectedUserPosition = -1;
    private GoogleSignInClient googleSignInClient;

    public SavedUserAdapter(Context context, List<LoginResponse.User> users) {
        this.mContext = context;
        this.userList = users;
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_user, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull final SimpleViewHolder viewHolder, final int position) {
        if (position == selectedUserPosition) {
            viewHolder.linearLayout.setVisibility(View.VISIBLE);
        } else {
            viewHolder.linearLayout.setVisibility(View.GONE);
        }

        viewHolder.bindView(position);

        onSwipeLayout(viewHolder, position);

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

    private void onSwipeLayout(SimpleViewHolder viewHolder, int position) {

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

//        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));

        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wraper));

//        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
//            @Override
//            public void onStartOpen(SwipeLayout layout) {
//
//            }
//
//            @Override
//            public void onOpen(SwipeLayout layout) {
//
//            }
//
//            @Override
//            public void onStartClose(SwipeLayout layout) {
//
//            }
//
//            @Override
//            public void onClose(SwipeLayout layout) {
//
//            }
//
//            @Override
//            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
//
//            }
//
//            @Override
//            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
//
//            }
//        });

//        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(v -> {
//            if (linearVisibility) {
//                viewHolder.linearLayout.setVisibility(View.VISIBLE);
//                linearVisibility = false;
//            } else {
//                viewHolder.linearLayout.setVisibility(View.GONE);
//                linearVisibility = true;
//            }
//        });

//        viewHolder.btnLocation.setOnClickListener(v -> Toast.makeText(v.getContext(), "Clicked on Information " + viewHolder.userName.getText().toString(), Toast.LENGTH_SHORT).show());

//        viewHolder.Share.setOnClickListener(view -> Toast.makeText(view.getContext(), "Clicked on Share " + viewHolder.userName.getText().toString(), Toast.LENGTH_SHORT).show());

//        viewHolder.Edit.setOnClickListener(view -> Toast.makeText(view.getContext(), "Clicked on Edit  " + viewHolder.userName.getText().toString(), Toast.LENGTH_SHORT).show());

        viewHolder.deleteUser.setOnClickListener(v -> deleteUser(position, viewHolder));

//        viewHolder.userImage.setOnClickListener(v -> startActivityWithGoogle());

    }

    private void deleteUser(int selectedUserPosition, SimpleViewHolder viewHolder) {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                .setTitle("Delete User?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                    HelperPref.deleteUser(mContext, userList.get(selectedUserPosition));
                    userList.remove(selectedUserPosition);
                    notifyItemRemoved(selectedUserPosition);
                    userList = HelperPref.getSavedUsers(mContext);
//                    notifyItemRangeChanged(selectedUserPosition, userList.size());
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

    private void startActivityWithGoogle() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(mContext);
        if (account != null) {
            String personPhotoUrl;
            Uri personPhotoUri = account.getPhotoUrl();
            if (personPhotoUri != null) {
                personPhotoUrl = personPhotoUri.toString();
            } else {
                personPhotoUrl = null;
            }
            Intent intent = new Intent(mContext, HomePageActivity.class);
//            User user = new User(account.getGivenName(), account.getFamilyName(), personPhotoUrl, account.getId(), account.getEmail(), "google");
//            intent.putExtra("loggedUser", user);

            mContext.startActivity(intent);

        } else {
            googleSignInClient = HelperClients.googleSignInClient(mContext);
        }
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        SwipeLayout swipeLayout;
        CircleImageView userImage;
        TextView userName, signInBtn, cancelBtn, passwordHead, forgotPassword;
        LinearLayout linearLayout, linearMainContainer;
        EditText savedUserPassword;
        ImageView loginTypeImg;
        ImageButton deleteUser, btnLocation;
        boolean linearVisibility = true;
        String userLoginType;

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

            linearMainContainer = itemView.findViewById(R.id.linear_main_container);
            linearMainContainer.setOnClickListener(this);

            linearLayout = itemView.findViewById(R.id.saved_user_hide_content);

            signInBtn = itemView.findViewById(R.id.saved_user_sign_in_btn);
            signInBtn.setOnClickListener(this);

            cancelBtn = itemView.findViewById(R.id.saved_user_cancel_btn);
            cancelBtn.setOnClickListener(this);

            savedUserPassword = itemView.findViewById(R.id.saved_user_password_edit);
            passwordHead = itemView.findViewById(R.id.saved_user_password_head);

            forgotPassword = itemView.findViewById(R.id.save_user_forgot_password);
            forgotPassword.setOnClickListener(this);

            loginTypeImg = itemView.findViewById(R.id.saved_user_login_type);
        }

        private void bindView(int position) {
            if (userList.get(position).getProfile_pic() != null) {
                HelperMedia.loadCirclePhoto(mContext, userList.get(position).getProfile_pic(), userImage);
            } else {
                userImage.setMinimumHeight(72);
                userImage.setMinimumWidth(72);
                userImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                userImage.setBackground(mContext.getResources().getDrawable(R.drawable.image_account));
            }
            userName.setText(String.format("%s %s %s", mContext.getString(R.string.continue_as), userList.get(position).getName(), userList.get(position).getLastname()));

//        if (currentUser.getLoginType() != null) {
//            switch (currentUser.getLoginType()) {
//                case FACEBOOK:
//                    viewHolder.userLoginType = currentUser.getLoginType();
//                    viewHolder.loginTypeImg.setBackground(mContext.getDrawable(R.drawable.facebook_little));
//                    break;
//
//                case GOOGLE:
//                    viewHolder.userLoginType = currentUser.getLoginType();
//                    viewHolder.loginTypeImg.setBackground(mContext.getDrawable(R.drawable.google_little));
//                    break;
//            }
//        }
        }


        private void checkUserLoginType() {
            if (userLoginType != null) {
                switch (userLoginType) {
                    case GOOGLE:
                        startActivityWithGoogle();
                        break;
                    case FACEBOOK:
                        Toast.makeText(mContext, "With Facebook", Toast.LENGTH_SHORT).show();
                        break;
                }
            } else {
                if (linearVisibility) {
                    linearLayout.setVisibility(View.VISIBLE);
                    linearVisibility = false;
                } else {
                    linearLayout.setVisibility(View.GONE);
                    linearVisibility = true;
                }
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.linear_main_container:
                    checkUserLoginType();
                    break;

                case R.id.saved_user_cancel_btn:
                    linearLayout.setVisibility(View.GONE);
                    linearVisibility = true;
                    break;

                case R.id.save_user_forgot_password:
                    Intent intent = new Intent(mContext, FPasswordActivity.class);
                    mContext.startActivity(intent);
                    break;

                case R.id.saved_user_sign_in_btn:
                    Toast.makeText(mContext, "Enter Password", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }
}
