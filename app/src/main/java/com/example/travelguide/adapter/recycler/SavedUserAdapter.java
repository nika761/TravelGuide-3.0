package com.example.travelguide.adapter.recycler;

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
import com.example.travelguide.R;
import com.example.travelguide.activity.ForgotPasswordActivity;
import com.example.travelguide.activity.SavedUserActivity;
import com.example.travelguide.activity.SignInActivity;
import com.example.travelguide.activity.UserPageActivity;
import com.example.travelguide.model.User;
import com.example.travelguide.utils.UtilsGlide;
import com.example.travelguide.utils.UtilsGoogle;
import com.example.travelguide.utils.UtilsPref;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.travelguide.utils.UtilsPref.FACEBOOK;
import static com.example.travelguide.utils.UtilsPref.GOOGLE;

public class SavedUserAdapter extends RecyclerSwipeAdapter<SavedUserAdapter.SimpleViewHolder> {
    private Context mContext;
    private List<User> userList;
    private int selectedUserPosition = -1;
    private GoogleSignInClient googleSignInClient;

    public SavedUserAdapter(Context context, List<User> objects) {
        this.mContext = context;
        this.userList = objects;
    }

    @NotNull
    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_user_item, parent, false);
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
            UtilsGlide.loadCirclePhoto(mContext, currentUser.getUrl(), viewHolder.userImage);
        } else {
            viewHolder.userImage.setMinimumHeight(72);
            viewHolder.userImage.setMinimumWidth(72);
            viewHolder.userImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewHolder.userImage.setBackground(mContext.getResources().getDrawable(R.drawable.account_image));
        }
        viewHolder.userName.setText(String.format("%s %s %s", mContext.getString(R.string.continue_as), currentUser.getName(), currentUser.getLastName()));

        if (currentUser.getLoginType() != null) {
            switch (currentUser.getLoginType()) {
                case FACEBOOK:
                    viewHolder.userLoginType = currentUser.getLoginType();
                    viewHolder.loginTypeImg.setBackground(mContext.getDrawable(R.drawable.facebook_little));
                    break;

                case GOOGLE:
                    viewHolder.userLoginType = currentUser.getLoginType();
                    viewHolder.loginTypeImg.setBackground(mContext.getDrawable(R.drawable.google_little));
                    break;
            }
        }
    }

    private void onSwipeLayout(SimpleViewHolder viewHolder, int position, User currentUser) {

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

//        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));

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
                    UtilsPref.deleteUser(mContext, userList.get(selectedUserPosition));
                    userList.remove(selectedUserPosition);
                    notifyItemRemoved(selectedUserPosition);
                    userList = UtilsPref.getSavedUsers(mContext);
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
            Intent intent = new Intent(mContext, UserPageActivity.class);
            User user = new User(account.getGivenName(), account.getFamilyName(), personPhotoUrl, account.getId(), account.getEmail(), "google");
            intent.putExtra("loggedUser", user);

            mContext.startActivity(intent);

        } else {
            googleSignInClient = UtilsGoogle.initGoogleSignInClient(mContext);
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
            setClickListeners();
        }


        private void initUI(View itemView) {
            swipeLayout = itemView.findViewById(R.id.swipe);
            deleteUser = itemView.findViewById(R.id.Delete);
            btnLocation = itemView.findViewById(R.id.btnLocation);
            userImage = itemView.findViewById(R.id.saved_user_image);
            userName = itemView.findViewById(R.id.saved_user_name);
            linearMainContainer = itemView.findViewById(R.id.linear_main_container);
            linearLayout = itemView.findViewById(R.id.saved_user_hide_content);
            signInBtn = itemView.findViewById(R.id.saved_user_sign_in_btn);
            cancelBtn = itemView.findViewById(R.id.saved_user_cancel_btn);
            savedUserPassword = itemView.findViewById(R.id.saved_user_password_edit);
            passwordHead = itemView.findViewById(R.id.saved_user_password_head);
            forgotPassword = itemView.findViewById(R.id.save_user_forgot_password);
            loginTypeImg = itemView.findViewById(R.id.saved_user_login_type);
        }

        private void setClickListeners() {
            linearMainContainer.setOnClickListener(this);
            signInBtn.setOnClickListener(this);
            cancelBtn.setOnClickListener(this);
            forgotPassword.setOnClickListener(this);
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
                    Intent intent = new Intent(mContext, ForgotPasswordActivity.class);
                    mContext.startActivity(intent);
                    break;

                case R.id.saved_user_sign_in_btn:
                    Toast.makeText(mContext, "Enter Password", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }
}
