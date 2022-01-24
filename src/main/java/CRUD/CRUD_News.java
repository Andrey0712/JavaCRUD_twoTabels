package CRUD;


import models.News;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CRUD_News {
    public static Scanner in = new Scanner(System.in);

    public  void PrintNewsList(List<News> prods) {
        for (News p : prods) {
            System.out.println(p.toString());
        }
    }


    public  void InsertIntoDBNews(String strConn) {
        try(Connection con = DriverManager.getConnection(strConn, "root", "")) {
            System.out.println("Successful connection");
            System.out.println("Инсертим новость ");
            String query = "INSERT INTO `news` (`text`,`categoryId`) " +
                    "VALUES (?,?);";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                String name;int categoryId;

                System.out.print("Enter name: ");
                name = in.nextLine();
                System.out.print("Enter categoryId: ");
                categoryId = Integer.parseInt(in.nextLine());

                stmt.setString(1, name);
                stmt.setInt(2, categoryId);

                int rows = stmt.executeUpdate();
                System.out.println("Update rows: " +rows);

            }
            catch (Exception ex) {
                System.out.println("Error statements: " + ex.getMessage());
            }

        } catch (Exception ex) {
            System.out.println("Error connection: " + ex.getMessage());
        }

    }

    public  List<News> SelectFromDBNews(String strConn) {
        try(Connection con = DriverManager.getConnection(strConn, "root", "")) {
            String selectSql = "SELECT * FROM news";
            try {
                PreparedStatement ps = con.prepareStatement(selectSql);
                ResultSet resultSet = ps.executeQuery();
                List<News> products = new ArrayList<>();
                while (resultSet.next()) {
                    News p = new News(resultSet.getInt("id"),
                            resultSet.getString("text"),
                            resultSet.getInt("categoryId")
                            );
                    products.add(p);
                }
                return products;
            } catch (Exception ex) {
                System.out.println("Error executeQuery: " + ex.getMessage());
            }
        } catch (Exception ex) {
            System.out.println("Error connection: " + ex.getMessage());
        }
        return null;
    }

    public void UpdateForDBNews(String strConn) {
        try(Connection con = DriverManager.getConnection(strConn, "root", "")) {
            System.out.println("Обновляем новость ");
            String query = "UPDATE news SET text = ? WHERE id = ?";
            try(PreparedStatement stmt = con.prepareStatement(query)) {
                System.out.print("\nEnter id: ");
                int id = in.nextInt();
                System.out.print("Enter new text: ");
                in.nextLine();
                String text = in.nextLine();
                stmt.setString(1, text);
                stmt.setInt(2, id);

                int rows = stmt.executeUpdate();

                System.out.println("Successful update "+ rows);
            }
            catch (Exception ex) {
                System.out.println("Error update:" + ex.getMessage());
            }
        } catch (Exception ex) {
            System.out.println("Error connection: " + ex.getMessage());
        }
    }

    public void DeleteFromDB(String strConn) {
        SelectFromDBNews(strConn);
        try(Connection con = DriverManager.getConnection(strConn, "root", "")) {
            System.out.println("Удаляем новость ");
            String query = "DELETE FROM news WHERE id = ?";
            try(PreparedStatement stmt = con.prepareStatement(query)) {
                System.out.print("Enter Id: ");
                int id = in.nextInt();
                stmt.setInt(1, id);
                int rows = stmt.executeUpdate();
                System.out.println("Successful delete " + rows);

            } catch (Exception ex) {
                System.out.println("Error delete: " + ex.getMessage());
            }

        } catch (Exception ex) {
            System.out.println("Error connection: " + ex.getMessage());
        }

    }


}
