package com.example.prm392_android_project.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GroupModel {
    @SerializedName("groupId")
    private int groupId;
    @SerializedName("groupName")
    private String groupName;
    @SerializedName("leader")
    private ClassMemberModel leader;
    @SerializedName("members")
    private List<ClassMemberModel> members;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ClassMemberModel getLeader() {
        return leader;
    }

    public void setLeader(ClassMemberModel leader) {
        this.leader = leader;
    }

    public List<ClassMemberModel> getMembers() {
        return members;
    }

    public void setMembers(List<ClassMemberModel> members) {
        this.members = members;
    }
}
