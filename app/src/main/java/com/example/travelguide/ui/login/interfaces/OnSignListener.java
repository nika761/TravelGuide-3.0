package com.example.travelguide.ui.login.interfaces;

import com.example.travelguide.model.response.VerifyEmailResponse;

public interface OnSignListener {

    void onVerify(VerifyEmailResponse verifyEmailResponse);

    void onVerifyError(String message);
}
