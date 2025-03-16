package com.week2assessment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAOImpl implements MemberDAO {
    
    private final Connection connection;

    public MemberDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addMember(Member member) {
        String sqlQuery = "INSERT INTO members (name, email, phone) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, member.getName());
            preparedStatement.setString(2, member.getEmail());
            preparedStatement.setString(3, member.getPhone());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Member added successfully");
            } else {
                System.out.println("Failed to add member");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public Member getMemberById(int memberId) {
        String sqlQuery = "SELECT * FROM members WHERE member_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery))   {
            preparedStatement.setInt(1, memberId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Member(resultSet.getInt("member_id"), resultSet.getString("name"), resultSet.getString("email"), resultSet.getString("phone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateMember(Member member) {
        String sqlQuery = "UPDATE members SET name = ?, email = ?, phone = ? WHERE member_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, member.getName());
            preparedStatement.setString(2, member.getEmail());
            preparedStatement.setString(3, member.getPhone());
            preparedStatement.setInt(4, member.getMemberId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Member updated successfully");
            } else {
                System.out.println("Failed to update member");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMember(int memberId) {
        String sqlQuery = "DELETE FROM members WHERE member_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, memberId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Member deleted successfully");
            } else {
                System.out.println("Failed to delete member");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        String sqlQuery = "SELECT * FROM members";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                members.add(new Member(resultSet.getInt("member_id"), resultSet.getString("name"), resultSet.getString("email"), resultSet.getString("phone")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }
}
