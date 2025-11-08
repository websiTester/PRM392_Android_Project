package com.example.prm392_android_project.views;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.models.GradingModel;
import com.example.prm392_android_project.models.MemberGradingModel;
import com.example.prm392_android_project.models.TaskModel;
import com.example.prm392_android_project.viewmodels.GradingViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class GradingActivity extends AppCompatActivity {

    private GradingViewModel viewModel;

    // View trong layout
    private TextInputEditText etDocumentLink;
    private EditText etOverallGrade, etOverallComment;
    private MaterialButton btnSaveGroupGrade, btnOpenLink;
    private LinearLayout layoutMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grading);

        // Ánh xạ view
        etDocumentLink = findViewById(R.id.et_document_link);
        etOverallGrade = findViewById(R.id.et_overall_grade);
        etOverallComment = findViewById(R.id.et_overall_comment);
        btnSaveGroupGrade = findViewById(R.id.btn_save_group_grade);
        btnOpenLink = findViewById(R.id.btn_open_link);
        layoutMembers = findViewById(R.id.layout_members);

        // Khởi tạo ViewModel
        viewModel = new ViewModelProvider(this).get(GradingViewModel.class);

        // Quan sát dữ liệu từ ViewModel
        viewModel.gradingData.observe(this, grading -> {
            if (grading != null) {
                Log.d("UI_UPDATE", "Dữ liệu nhận được: " + grading);

                // Gán dữ liệu nhóm
                etDocumentLink.setText(grading.getSubmissionLink() != null ? grading.getSubmissionLink() : "");
                etOverallGrade.setText(grading.getGroupGrade() != null ? grading.getGroupGrade().toString() : "");
                etOverallComment.setText(grading.getGroupComment() != null ? grading.getGroupComment() : "");

                // Hiển thị danh sách thành viên
                layoutMembers.removeAllViews();

                if (grading.getMembers() != null && !grading.getMembers().isEmpty()) {
                    for (MemberGradingModel member : grading.getMembers()) {
                        LinearLayout memberLayout = new LinearLayout(this);
                        memberLayout.setOrientation(LinearLayout.VERTICAL);
                        memberLayout.setPadding(0, 24, 0, 24);

                        // Tên
                        TextView tvName = new TextView(this);
                        tvName.setText(member.getFullName());
                        tvName.setTextSize(18);
                        tvName.setTypeface(null, Typeface.BOLD);
                        memberLayout.addView(tvName);

                        // ===== Ô ĐIỂM TV =====
                        EditText etScore = new EditText(this);
                        etScore.setHint("Điểm TV");
                        etScore.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        etScore.setBackgroundResource(android.R.drawable.edit_text);
                        etScore.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        ));

                        // 👉 GÁN GIÁ TRỊ ĐÃ LƯU (NẾU CÓ)
                        if (member.getGrade() != null) {
                            etScore.setText(String.valueOf(member.getGrade()));
                        }

                        memberLayout.addView(etScore);

                        // ===== Ô NHẬN XÉT CÁ NHÂN =====
                        EditText etComment = new EditText(this);
                        etComment.setHint("Nhận xét về sự đóng góp của thành viên này...");
                        etComment.setMinLines(3);
                        etComment.setGravity(Gravity.TOP | Gravity.START);
                        etComment.setBackgroundResource(android.R.drawable.edit_text);
                        etComment.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        ));

                        // 👉 GÁN COMMENT ĐÃ LƯU (NẾU CÓ)
                        if (member.getComment() != null) {
                            etComment.setText(member.getComment());
                        }

                        memberLayout.addView(etComment);

                        // ... phần Tiến độ công việc + divider
                        layoutMembers.addView(memberLayout);
                    }


                    // 🔹 Nút lưu đánh giá thành viên
                    MaterialButton btnSaveMemberGrades = new MaterialButton(this);
                    btnSaveMemberGrades.setText("Lưu đánh giá thành viên");
                    btnSaveMemberGrades.setBackgroundColor(getResources().getColor( R.color.colorAccent));
                    btnSaveMemberGrades.setTextColor(Color.WHITE);
                    btnSaveMemberGrades.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    btnSaveMemberGrades.setOnClickListener(v -> {
                        GradingModel grading1 = viewModel.gradingData.getValue();
                        if (grading1 == null || grading1.getMembers() == null) {
                            Toast.makeText(this, "Không có dữ liệu thành viên để lưu", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        int childCount = layoutMembers.getChildCount() - 1; // bỏ nút cuối
                        int memberIndex = 0;

                        for (int i = 0; i < childCount && memberIndex < grading1.getMembers().size(); i++) {
                            View child = layoutMembers.getChildAt(i);
                            if (!(child instanceof LinearLayout)) continue;

                            MemberGradingModel member = grading1.getMembers().get(memberIndex);
                            LinearLayout memberLayout = (LinearLayout) child;

                            for (int j = 0; j < memberLayout.getChildCount(); j++) {
                                View inner = memberLayout.getChildAt(j);
                                if (inner instanceof EditText) {
                                    EditText et = (EditText) inner;
                                    String hint = et.getHint() != null ? et.getHint().toString() : "";

                                    if (hint.contains("Điểm")) {
                                        try {
                                            double score = Double.parseDouble(et.getText().toString());
                                            member.setGrade(score);
                                        } catch (NumberFormatException e) {
                                            member.setGrade(0.0);
                                        }
                                    } else if (hint.contains("Nhận xét")) {
                                        member.setComment(et.getText().toString());
                                    }
                                }
                            }

                            memberIndex++;
                        }

                        // Cập nhật thông tin nhóm (server bắt buộc)
                        grading1.setSubmissionLink(etDocumentLink.getText().toString());
                        grading1.setGroupComment(etOverallComment.getText().toString());
                        // assignmentName, groupName, fullName đã có sẵn từ GET lần đầu

                        int teacherId = 1; // hoặc lấy từ Intent
                        viewModel.saveMemberGrades(grading1, teacherId);
                    });




                    layoutMembers.addView(btnSaveMemberGrades);
                } else {
                    TextView tvEmpty = new TextView(this);
                    tvEmpty.setText("Chưa có thành viên trong nhóm.");
                    layoutMembers.addView(tvEmpty);
                }
            }
        });

        // Quan sát thông báo
        viewModel.message.observe(this, msg ->
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        );

        // Gọi API
        int groupId = 1;
        int assignmentId = 2;
        viewModel.fetchGrading(groupId, assignmentId);

        // Mở link tài liệu
        btnOpenLink.setOnClickListener(v -> {
            String url = etDocumentLink.getText().toString().trim();
            if (!url.isEmpty()) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, "Không thể mở liên kết", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Chưa có đường dẫn tài liệu", Toast.LENGTH_SHORT).show();
            }
        });

        // Lưu đánh giá nhóm
        btnSaveGroupGrade.setOnClickListener(v -> {
            GradingModel model = viewModel.gradingData.getValue();
            if (model != null) {
                try {
                    double grade = Double.parseDouble(etOverallGrade.getText().toString());
                    model.setGroupGrade(grade);
                } catch (NumberFormatException e) {
                    model.setGroupGrade(0.0);
                }
                model.setGroupComment(etOverallComment.getText().toString());
                viewModel.saveGroupGrade(model);
            } else {
                Toast.makeText(this, "Không có dữ liệu nhóm để lưu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
