package com.example.prm392_android_project.views;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.prm392_android_project.R;
import com.example.prm392_android_project.models.GradingModel;
import com.example.prm392_android_project.viewmodels.GradingViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class GradingActivity extends AppCompatActivity {
    private TextInputEditText etDocumentLink;
    private EditText etOverallGrade, etOverallComment;
    private MaterialButton btnSaveGroupGrade;

    // --- Phần đánh giá thành viên ---
    private EditText etMemberScore, etMemberComment;
    private MaterialButton btnSaveMemberGrade;

    // ======= Biến lưu dữ liệu tạm =======
    // Nhóm
    private String documentLink = "";
    private String overallGrade = "";
    private String overallComment = "";

    // Thành viên
    private String memberScore = "";
    private String memberComment = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grading);

        // === Ánh xạ view ===
        etDocumentLink = findViewById(R.id.et_document_link);
        etOverallGrade = findViewById(R.id.et_overall_grade);
        etOverallComment = findViewById(R.id.et_overall_comment);
        btnSaveGroupGrade = findViewById(R.id.btn_save_group_grade);

        etMemberScore = findViewById(R.id.member_score);
        etMemberComment = findViewById(R.id.member_comment);
        btnSaveMemberGrade = findViewById(R.id.btn_save_member_grade);

        // === Hiển thị dữ liệu cũ nếu có ===
        loadSavedData();

        // === Xử lý nút "Lưu đánh giá nhóm" ===
        btnSaveGroupGrade.setOnClickListener(v -> {
            documentLink = etDocumentLink.getText().toString().trim();
            overallGrade = etOverallGrade.getText().toString().trim();
            overallComment = etOverallComment.getText().toString().trim();

            if (overallGrade.isEmpty()) {
                Toast.makeText(this, "⚠️ Vui lòng nhập điểm tổng", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lưu tạm vào SharedPreferences
            getSharedPreferences("grading", MODE_PRIVATE)
                    .edit()
                    .putString("doc_link", documentLink)
                    .putString("overall_grade", overallGrade)
                    .putString("overall_comment", overallComment)
                    .apply();

            Toast.makeText(this, "✅ Lưu đánh giá nhóm thành công!", Toast.LENGTH_SHORT).show();
        });

        // === Xử lý nút "Lưu đánh giá thành viên" ===
        btnSaveMemberGrade.setOnClickListener(v -> {
            memberScore = etMemberScore.getText().toString().trim();
            memberComment = etMemberComment.getText().toString().trim();

            if (memberScore.isEmpty()) {
                Toast.makeText(this, "⚠️ Vui lòng nhập điểm thành viên", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lưu tạm vào SharedPreferences
            getSharedPreferences("grading", MODE_PRIVATE)
                    .edit()
                    .putString("member_score", memberScore)
                    .putString("member_comment", memberComment)
                    .apply();

            Toast.makeText(this, "✅ Lưu đánh giá thành viên thành công!", Toast.LENGTH_SHORT).show();
        });
    }

    // === Hàm nạp lại dữ liệu cũ từ SharedPreferences ===
    private void loadSavedData() {
        var prefs = getSharedPreferences("grading", MODE_PRIVATE);
        etDocumentLink.setText(prefs.getString("doc_link", ""));
        etOverallGrade.setText(prefs.getString("overall_grade", ""));
        etOverallComment.setText(prefs.getString("overall_comment", ""));
        etMemberScore.setText(prefs.getString("member_score", ""));
        etMemberComment.setText(prefs.getString("member_comment", ""));
    }
}
