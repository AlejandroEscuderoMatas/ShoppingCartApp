// Generated by view binder compiler. Do not edit!
package com.example.carrito.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.carrito.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DialogBuyBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button buttonLogoutNo;

  @NonNull
  public final Button buttonLogoutYes;

  @NonNull
  public final Guideline guideline21;

  @NonNull
  public final TextView textTotalPrice;

  private DialogBuyBinding(@NonNull ConstraintLayout rootView, @NonNull Button buttonLogoutNo,
      @NonNull Button buttonLogoutYes, @NonNull Guideline guideline21,
      @NonNull TextView textTotalPrice) {
    this.rootView = rootView;
    this.buttonLogoutNo = buttonLogoutNo;
    this.buttonLogoutYes = buttonLogoutYes;
    this.guideline21 = guideline21;
    this.textTotalPrice = textTotalPrice;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static DialogBuyBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DialogBuyBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.dialog_buy, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DialogBuyBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.button_logout_no;
      Button buttonLogoutNo = ViewBindings.findChildViewById(rootView, id);
      if (buttonLogoutNo == null) {
        break missingId;
      }

      id = R.id.button_logout_yes;
      Button buttonLogoutYes = ViewBindings.findChildViewById(rootView, id);
      if (buttonLogoutYes == null) {
        break missingId;
      }

      id = R.id.guideline21;
      Guideline guideline21 = ViewBindings.findChildViewById(rootView, id);
      if (guideline21 == null) {
        break missingId;
      }

      id = R.id.text_total_price;
      TextView textTotalPrice = ViewBindings.findChildViewById(rootView, id);
      if (textTotalPrice == null) {
        break missingId;
      }

      return new DialogBuyBinding((ConstraintLayout) rootView, buttonLogoutNo, buttonLogoutYes,
          guideline21, textTotalPrice);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
