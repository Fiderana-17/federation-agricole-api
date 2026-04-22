package com.hei.federation_api.Repository;

import com.hei.federation_api.Config.DataSource;
import com.hei.federation_api.Entity.Member;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MemberRepository {

    private final DataSource dataSource;

    public MemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(Member member) {

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement ps = conn.prepareStatement("""
                INSERT INTO members(id, first_name, last_name, email)
                VALUES (?, ?, ?, ?)
            """);

            ps.setString(1, member.id);
            ps.setString(2, member.firstName);
            ps.setString(3, member.lastName);
            ps.setString(4, member.email);

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsById(String id) {

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement ps = conn.prepareStatement("""
                SELECT COUNT(*) FROM members WHERE id = ?
            """);

            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

            return false;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Member findById(String id) {

        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement ps = conn.prepareStatement("""
                SELECT id, first_name, last_name, email
                FROM members
                WHERE id = ?
            """);

            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Member m = new Member();
                m.id = rs.getString("id");
                m.firstName = rs.getString("first_name");
                m.lastName = rs.getString("last_name");
                m.email = rs.getString("email");

                return m;
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Member> findAll() {

        List<Member> list = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            ResultSet rs = conn.createStatement().executeQuery("""
                SELECT id, first_name, last_name, email
                FROM members
            """);

            while (rs.next()) {
                Member m = new Member();
                m.id = rs.getString("id");
                m.firstName = rs.getString("first_name");
                m.lastName = rs.getString("last_name");
                m.email = rs.getString("email");

                list.add(m);
            }

            return list;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}