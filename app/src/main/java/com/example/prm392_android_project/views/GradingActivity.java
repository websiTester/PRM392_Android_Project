package com.example.prm392_android_project.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class GradingActivity extends AppCompatActivity {

    private GradingViewModel viewModel;

    // View trong layout
    private TextView tvAssignmentName, tvGroupName;
    private TextInputEditText etDocumentLink;
    private EditText etOverallGrade, etOverallComment;
    private MaterialButton btnSaveGroupGrade, btnOpenLink;
    private LinearLayout layoutMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grading);

        // Ánh xạ view
        tvAssignmentName = findViewById(R.id.tv_assignment_name);
        tvGroupName = findViewById(R.id.tv_group_name);
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
                // ===== TÊN BÀI TẬP & TÊN NHÓM =====
                tvAssignmentName.setText("Bài tập: " +
                        (grading.getAssignmentName() != null ? grading.getAssignmentName() : "Không rõ"));

                tvGroupName.setText("Nhóm: " +
                        (grading.getGroupName() != null ? grading.getGroupName() : "Không rõ"));
                // ===== GÁN DỮ LIỆU NHÓM =====
                String link = grading.getSubmissionLink();
                etDocumentLink.setText(link != null ? link : "");
                etOverallGrade.setText(grading.getGroupGrade() != null ? grading.getGroupGrade().toString() : "");
                etOverallComment.setText(grading.getGroupComment() != null ? grading.getGroupComment() : "");

                // 👉 NẾU KHÔNG CÓ LINK THÌ KHÔNG CHO NHẬP / LƯU
                boolean canEdit = link != null && !link.trim().isEmpty();
                etOverallGrade.setEnabled(canEdit);
                etOverallComment.setEnabled(canEdit);
                btnSaveGroupGrade.setEnabled(canEdit);

                // Hiển thị danh sách thành viên
                layoutMembers.removeAllViews();

                if (grading.getMembers() != null && !grading.getMembers().isEmpty()) {
                    for (MemberGradingModel member : grading.getMembers()) {
                        LinearLayout memberLayout = new LinearLayout(this);
                        memberLayout.setOrientation(LinearLayout.VERTICAL);
                        memberLayout.setPadding(0, 24, 0, 24);

                        // ===== TÊN THÀNH VIÊN =====
                        TextView tvName = new TextView(this);
                        tvName.setText(member.getFullName());
                        tvName.setTextSize(18);
                        tvName.setTypeface(null, Typeface.BOLD);
                        memberLayout.addView(tvName);

                        // ===== Ô ĐIỂM TV =====
                        EditText etScore = new EditText(this);
                        etScore.setHint("Điểm TV");
                        etScore.setInputType(
                                InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL
                        );
                        etScore.setBackgroundResource(android.R.drawable.edit_text);
                        etScore.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        ));

                        // 👉 GÁN ĐIỂM ĐÃ LƯU (NẾU CÓ)
                        if (member.getGrade() != null) {
                            etScore.setText(String.valueOf(member.getGrade()));
                        }
                        // 🔒 KHÓA NẾU KHÔNG CÓ LINK
                        etScore.setEnabled(canEdit);

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
                        // 🔒 KHÓA NẾU KHÔNG CÓ LINK
                        etComment.setEnabled(canEdit);

                        memberLayout.addView(etComment);

                        // ===== TIẾN ĐỘ CÔNG VIỆC (TÍNH TỪ TASK) =====
                        TextView tvProgressTitle = new TextView(this);
                        tvProgressTitle.setText("Tiến độ công việc");
                        tvProgressTitle.setTextSize(14);
                        tvProgressTitle.setPadding(0, 12, 0, 4);
                        memberLayout.addView(tvProgressTitle);

                        int todo = 0;
                        int doing = 0;
                        int done = 0;

                        if (member.getTasks() != null) {
                            for (TaskModel task : member.getTasks()) {
                                String status = task.getStatus();
                                if (status == null) continue;

                                if (status.equalsIgnoreCase("Pending")
                                        || status.equalsIgnoreCase("To Do")
                                        || status.equalsIgnoreCase("Todo")) {
                                    todo++;
                                } else if (status.equalsIgnoreCase("In Progress")
                                        || status.equalsIgnoreCase("Doing")) {
                                    doing++;
                                } else if (status.equalsIgnoreCase("Completed")
                                        || status.equalsIgnoreCase("Done")
                                        || status.equalsIgnoreCase("Finished")) {
                                    done++;
                                }
                            }
                        }

                        String base = "Cần làm: " + todo +
                                "   Đang làm: " + doing +
                                "   Hoàn thành: " + done;

                        SpannableString progressText = new SpannableString(base);

                        int idxTodo = base.indexOf("Cần làm:");
                        int idxDoing = base.indexOf("Đang làm:");
                        int idxDone = base.indexOf("Hoàn thành:");

                        if (idxTodo >= 0) {
                            progressText.setSpan(
                                    new ForegroundColorSpan(Color.RED),
                                    idxTodo,
                                    idxTodo + "Cần làm:".length(),
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                            );
                        }
                        if (idxDoing >= 0) {
                            progressText.setSpan(
                                    new ForegroundColorSpan(Color.BLUE),
                                    idxDoing,
                                    idxDoing + "Đang làm:".length(),
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                            );
                        }
                        if (idxDone >= 0) {
                            progressText.setSpan(
                                    new ForegroundColorSpan(Color.parseColor("#008000")),
                                    idxDone,
                                    base.length(),
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                            );
                        }

                        TextView tvProgress = new TextView(this);
                        tvProgress.setText(progressText);
                        tvProgress.setTextSize(14);
                        tvProgress.setPadding(0, 0, 0, 8);
                        memberLayout.addView(tvProgress);
                        // ===== NÚT XEM THÊM TASK =====
                        MaterialButton btnViewTasks = new MaterialButton(this);
                        btnViewTasks.setText("Xem thêm");
                        btnViewTasks.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        ));
                        btnViewTasks.setPadding(0, 8, 0, 8);

// Cần biến final để dùng trong lambda
                        final MemberGradingModel currentMember = member;
                        btnViewTasks.setOnClickListener(v -> showTasksDialog(currentMember));

                        memberLayout.addView(btnViewTasks);
                        // ===== DÒNG KẺ NGĂN CÁCH =====
                        View divider = new View(this);
                        divider.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                2
                        ));
                        divider.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        memberLayout.addView(divider);

                        // Thêm block thành viên vào layout cha
                        layoutMembers.addView(memberLayout);
                    }

                    // 🔹 Nút lưu đánh giá thành viên
                    MaterialButton btnSaveMemberGrades = new MaterialButton(this);
                    btnSaveMemberGrades.setText("Lưu đánh giá thành viên");
                    btnSaveMemberGrades.setBackgroundColor(
                            getResources().getColor(R.color.colorAccent)
                    );
                    btnSaveMemberGrades.setTextColor(Color.WHITE);
                    btnSaveMemberGrades.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));

                    // 🔒 KHÓA NẾU KHÔNG CÓ LINK
                    btnSaveMemberGrades.setEnabled(canEdit);

                    btnSaveMemberGrades.setOnClickListener(v -> {
                        if (!canEdit) {
                            Toast.makeText(this,
                                    "Chưa có đường dẫn tài liệu, không thể chấm điểm.",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

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
                        SharedPreferences sharedPreferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE);
                        int teacherId = sharedPreferences.getInt("userId",-1); // hoặc lấy từ Intent
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
        SharedPreferences sharedPreferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE);
        int groupId = sharedPreferences.getInt("groupId",-1); // hoặc lấy từ Intent
        int assignmentId = sharedPreferences.getInt("assignmentId",-1);
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
    private void showTasksDialog(MemberGradingModel member) {
        androidx.appcompat.app.AlertDialog.Builder builder =
                new androidx.appcompat.app.AlertDialog.Builder(this);

        String title = member.getFullName() != null
                ? member.getFullName()
                : "Thành viên";

        builder.setTitle("Công việc của " + title);

        // Root layout cho dialog (có scroll)
        android.widget.ScrollView scrollView = new android.widget.ScrollView(this);
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        int padding = (int) (16 * getResources().getDisplayMetrics().density);
        container.setPadding(padding, padding, padding, padding);

        if (member.getTasks() == null || member.getTasks().isEmpty()) {
            TextView tvEmpty = new TextView(this);
            tvEmpty.setText("Không có task nào.");
            tvEmpty.setTextSize(14);
            tvEmpty.setTextColor(Color.DKGRAY);
            container.addView(tvEmpty);
        } else {
            int index = 1;
            for (TaskModel task : member.getTasks()) {
                // Tên task
                TextView tvTaskTitle = new TextView(this);
                tvTaskTitle.setText(index + ". " +
                        (task.getTitle() != null ? task.getTitle() : "Không có tiêu đề"));
                tvTaskTitle.setTextSize(15);
                tvTaskTitle.setTypeface(null, Typeface.BOLD);
                tvTaskTitle.setTextColor(Color.BLACK);
                tvTaskTitle.setPadding(0, (index == 1 ? 0 : 16), 0, 4);
                container.addView(tvTaskTitle);

                // Trạng thái + điểm
                String status = task.getStatus() != null ? task.getStatus() : "Không rõ";
                String detail = "Trạng thái: " + status + "\n" +
                        "Điểm: " + task.getPoints();

                TextView tvTaskDetail = new TextView(this);
                tvTaskDetail.setText(detail);
                tvTaskDetail.setTextSize(14);
                tvTaskDetail.setTextColor(Color.DKGRAY);
                container.addView(tvTaskDetail);

                index++;
            }
        }

        scrollView.addView(container);
        builder.setView(scrollView);

        builder.setPositiveButton("Đóng", null);

        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
    }


}
