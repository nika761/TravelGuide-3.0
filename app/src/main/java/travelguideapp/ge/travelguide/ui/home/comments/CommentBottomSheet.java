package travelguideapp.ge.travelguide.ui.home.comments;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class CommentBottomSheet extends BottomSheetDialog {

    public CommentBottomSheet(@NonNull Context context) {
        super(context);
    }

    public CommentBottomSheet(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected CommentBottomSheet(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

}
