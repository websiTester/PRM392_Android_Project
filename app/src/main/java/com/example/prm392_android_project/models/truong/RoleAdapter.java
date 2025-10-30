package com.example.prm392_android_project.models.truong;

import android.widget.RadioGroup;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import com.example.prm392_android_project.R;

public class RoleAdapter {
    @BindingAdapter("role")
    public static void setGender(RadioGroup radioGroup, int roleId) {
        if (roleId == 0) return;

        int selectedId = -1;
        if (roleId==1) {
            selectedId = R.id.rdoStudent;
        } else if (roleId==2) {
            selectedId = R.id.rdoTeacher;
        }
        radioGroup.check(selectedId);
    }

    @InverseBindingAdapter(attribute = "role", event = "roleAttrChanged")
    public static int getGender(RadioGroup radioGroup) {
        int checkedId = radioGroup.getCheckedRadioButtonId();
        if (checkedId == R.id.rdoStudent) {
            return 1;
        } else if (checkedId == R.id.rdoTeacher) {
            return 2;
        }
        return 0;
    }

    @BindingAdapter("roleAttrChanged")
    public static void setGenderListener(RadioGroup radioGroup, final InverseBindingListener listener) {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (listener != null) {
                listener.onChange();
            }
        });
    }
}
