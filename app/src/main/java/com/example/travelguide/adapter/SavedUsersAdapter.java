package com.example.travelguide.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.travelguide.R;
import com.example.travelguide.activity.UserPageActivity;
import com.example.travelguide.helper.HelperPref;
import com.example.travelguide.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SavedUsersAdapter extends RecyclerView.Adapter<SavedUsersAdapter.MyViewHolder> {


    private Context context;
    private List<User> users;
    private int selectedUserPosition = -1;
    private String adapterLoginType;
    private GoogleSignInClient mGoogleSignInClient;

    public SavedUsersAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_users_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (position == selectedUserPosition) {
            holder.linearLayout.setVisibility(View.VISIBLE);
        } else {
            holder.linearLayout.setVisibility(View.GONE);
        }

        Glide.with(context)
                .applyDefaultRequestOptions(new RequestOptions().centerCrop())
                .load(users.get(position).getUrl())
                .into(holder.userImage);

        holder.userName.setText(context.getString(R.string.continue_as) + " " + users.get(position).getName() + " " + users.get(position).getLastName() + "");
        if (users.get(position).getLoginType() != null && users.get(position).getLoginType().equals("facebook")) {
            holder.loginTypeImg.setBackground(context.getDrawable(R.drawable.facebook_little));
        } else if (users.get(position).getLoginType() != null && users.get(position).getLoginType().equals("google")) {
            holder.loginTypeImg.setBackground(context.getDrawable(R.drawable.google_little));
        } else {
            holder.loginTypeImg.setBackground(context.getDrawable(R.drawable.icon_profile));
        }

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        CircleImageView userImage;
        TextView userName, signInBtn, cancelBtn, passwordHead, forgotPassword;
        LinearLayout linearLayout;
        EditText savedUserPassword;
        ImageView loginTypeImg;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            initUI(itemView);
            setClickListeners();
        }

        private void closeFragment() {
            AppCompatActivity appCompatActivity = (AppCompatActivity) context;
            appCompatActivity.finish();
        }

        private void initUI(View itemView) {
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

        private void setClickListeners() {
            itemView.setOnClickListener(this);
            userName.setOnLongClickListener(this);
            signInBtn.setOnClickListener(this);
            cancelBtn.setOnClickListener(this);
            userName.setOnClickListener(this);
            forgotPassword.setOnClickListener(this);
            userImage.setOnClickListener(this);
        }

        private void checkUserPassword() {
            if (savedUserPassword.getText().toString().isEmpty()) {
                StyleableToast.makeText(context, "Password !", Toast.LENGTH_LONG, R.style.errorToast).show();
                YoYo.with(Techniques.Shake)
                        .duration(300)
                        .playOn(savedUserPassword);
            } else {
                Intent intent = new Intent(context, UserPageActivity.class);
                context.startActivity(intent);
            }
        }

        private void startActivityWithGoogle() {
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
            if (account != null) {
                String personPhotoUrl;
                Uri personPhotoUri = account.getPhotoUrl();
                if (personPhotoUri != null) {
                    personPhotoUrl = personPhotoUri.toString();
                } else {
                    personPhotoUrl = null;
                }
                Intent intent = new Intent(context, UserPageActivity.class);
                intent.putExtra("name", account.getGivenName());
                intent.putExtra("lastName", account.getFamilyName());
                intent.putExtra("email", account.getEmail());
                intent.putExtra("url", personPhotoUrl);
                intent.putExtra("id", account.getId());
                intent.putExtra("loginType", "google");
                context.startActivity(intent);
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.saved_user_name:
                    selectedUserPosition = getAdapterPosition();
                    notifyDataSetChanged();
                    break;

                case R.id.saved_user_cancel_btn:
                    linearLayout.setVisibility(View.GONE);
                    break;

                case R.id.saved_user_sign_in_btn:
                    checkUserPassword();
                    break;

                case R.id.save_user_forgot_password:
//                    ((SavedUserActivity) context).loadForgotPswFragment();
                    break;

                case R.id.saved_user_image:
                    startActivityWithGoogle();
                    break;
            }
        }

        @Override
        public boolean onLongClick(View v) {

            AlertDialog alertDialog = new AlertDialog.Builder(context)
                    .setTitle("გსურთ წაშლა?")
                    .setPositiveButton("OK", (dialog, which) -> {
                        HelperPref.deleteUser(context, users.get(getAdapterPosition()));
                        users = HelperPref.getSavedUsers(context);
                        Toast.makeText(context, "წაიშალა", Toast.LENGTH_LONG).show();
                        notifyDataSetChanged();
                        dialog.dismiss();

                        if (users.size() == 0) {
                            AppCompatActivity appCompatActivity = (AppCompatActivity) context;
                            appCompatActivity.finish();
                        }

                    }).setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss()).create();
            alertDialog.show();
            return false;
        }


    }
}
