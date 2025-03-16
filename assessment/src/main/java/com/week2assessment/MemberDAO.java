package com.week2assessment;

import java.util.List;

public interface MemberDAO {
    public void addMember(Member member);
    public Member getMemberById(int memberId);
    public void updateMember(Member member);
    public void deleteMember(int memberId);
    public List<Member> getAllMembers();
}
