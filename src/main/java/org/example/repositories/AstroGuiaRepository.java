package org.example.repositories;

import org.example.model.AstroGuia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AstroGuiaRepository {
    public static final String URL_CONNECTION = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    public static final String USER = "rm553351";
    public static final String PASSWORD = "120303";


    public List<AstroGuia> findAll(){
        List<AstroGuia> listAstroGuia = new ArrayList<>();

        try (
                Connection conn = DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
                PreparedStatement psmt = conn.prepareStatement("SELECT * FROM astro");
                ResultSet rs = psmt.executeQuery()
        ){
            while (rs.next()){
                AstroGuia astroGuia = new AstroGuia();
                astroGuia.setId(rs.getInt("ID"));
                astroGuia.setText(rs.getString("TEXT"));
                astroGuia.setLink(rs.getString("LINK"));
                listAstroGuia.add(astroGuia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listAstroGuia;
    }

    public Optional<AstroGuia> findAstroById(int id){
        AstroGuia astro = null;
        try (
                Connection conn = DriverManager.getConnection(URL_CONNECTION,USER,PASSWORD);
                PreparedStatement psmt = conn.prepareStatement("SELECT * FROM astro WHERE ID = ? ORDER BY ID")
        ){
            psmt.setInt(1, id);
            ResultSet rs = psmt.executeQuery();

            if(rs.next()){
                astro = new AstroGuia();
                astro.setId(rs.getInt("ID"));
                astro.setText(rs.getString("TEXT"));
                astro.setLink(rs.getString("LINK"));

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.ofNullable(astro);

    }

    public int createAstro(AstroGuia astro){
        try(
                Connection conn = DriverManager.getConnection(URL_CONNECTION,USER,PASSWORD);
                PreparedStatement psmt = conn.prepareStatement("INSERT INTO astro (ID,TEXT,LINK) VALUES(?,?,?)")
        ){
            psmt.setInt(1, astro.getId());
            psmt.setString(2, astro.getText());
            psmt.setString(3, astro.getLink());


            return psmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public int updateAstro(AstroGuia astro){
        try (
                Connection conn = DriverManager.getConnection(URL_CONNECTION,USER,PASSWORD);
                PreparedStatement psmt = conn.prepareStatement("UPDATE astro SET TEXT=?,LINK=? WHERE ID = ?")
        ){
            psmt.setString(1, astro.getText());
            psmt.setString(2, astro.getLink());
            psmt.setInt(3, astro.getId());

            return psmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteAstro(int id){
        try(
                Connection conn = DriverManager.getConnection(URL_CONNECTION,USER,PASSWORD);
                PreparedStatement psmt = conn.prepareStatement("DELETE astro WHERE ID = ?")
        ){
            psmt.setInt(1, id);
            return psmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}
