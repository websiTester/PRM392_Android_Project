package com.example.prm392_android_project.actionbindings;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import com.example.prm392_android_project.dtos.GetUserForDropdownResponse;

public class BindingAdapters {
    @BindingAdapter("android:selectedUser")
    public static void setSelectedUser(Spinner spinner, int assignedToId) {
        if (assignedToId <= 0) return;

        if (spinner.getAdapter() instanceof ArrayAdapter) {
            ArrayAdapter<GetUserForDropdownResponse> adapter = (ArrayAdapter<GetUserForDropdownResponse>) spinner.getAdapter();
            for (int i = 0; i < adapter.getCount(); i++) {
                GetUserForDropdownResponse item = adapter.getItem(i);
                if (item != null && item.getUserId() == assignedToId) {
                    spinner.setSelection(i);
                    break;
                }
            }
        }
    }

    @InverseBindingAdapter(attribute = "android:selectedUser", event = "android:selectedUserAttrChanged")
    public static int getSelectedUser(Spinner spinner) {
        GetUserForDropdownResponse selectedItem = (GetUserForDropdownResponse) spinner.getSelectedItem();
        if (selectedItem != null) {
            return selectedItem.getUserId();
        }else {
            GetUserForDropdownResponse defaultItem =(GetUserForDropdownResponse)spinner.getAdapter().getItem(0);
            return defaultItem.getUserId();
        }

    }
    @BindingAdapter("android:selectedUserAttrChanged")
    public static void setListeners(Spinner spinner, final InverseBindingListener listener) {
        if (listener != null) {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    listener.onChange();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    listener.onChange();
                }
            });
        }
    }
    @BindingAdapter("android:point")
    public static void setInt(EditText view, Integer value) {
        String oldValue = view.getText().toString();
        String newValue = value == null ? "" : String.valueOf(value);
        if (!oldValue.equals(newValue)) {
            view.setText(newValue);
        }
    }

    @InverseBindingAdapter(attribute = "android:point" , event = "android:textAttrChanged")
    public static Integer getInt(EditText view) {
        try {
            return Integer.parseInt(view.getText().toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
