package CRUD;

import models.Categories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CRUD_Category {
    public static Scanner in = new Scanner(System.in);

    public  void PrintCategoriesList(List<Categories> prods) {
        for (Categories p : prods) {
            System.out.println(p.toString());
        }
    }


    public  void InsertIntoDB(String strConn) {
        try(Connection con = DriverManager.getConnection(strConn, "root", "")) {
            System.out.println("Successful connection");
            System.out.println("Инсертим категорию ");
            String query = "INSERT INTO `categories` (`name`) " +
                    "VALUES (?);";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                String name;

                System.out.print("Enter name: ");
                name = in.nextLine();

                stmt.setString(1, name);

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

    public  List<Categories> SelectFromDB(String strConn) {
        try(Connection con = DriverManager.getConnection(strConn, "root", "")) {
            String selectSql = "SELECT * FROM categories";
            try {
                PreparedStatement ps = con.prepareStatement(selectSql);
                ResultSet resultSet = ps.executeQuery();
                List<Categories> products = new ArrayList<>();
                while (resultSet.next()) {
                    Categories p = new Categories(resultSet.getInt("id"),
                            resultSet.getString("name")
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

    public void UpdateForDB(String strConn) {
        try(Connection con = DriverManager.getConnection(strConn, "root", "")) {
            System.out.println("Обновляем категорию ");
            String query = "UPDATE categories SET name = ? WHERE id = ?";
            try(PreparedStatement stmt = con.prepareStatement(query)) {
                System.out.print("\nEnter id: ");
                int id = in.nextInt();
                System.out.print("Enter new name: ");
                in.nextLine();
                String name = in.nextLine();
                stmt.setString(1, name);
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
        SelectFromDB(strConn);
        try(Connection con = DriverManager.getConnection(strConn, "root", "")) {
            System.out.println("Удаляем категорию ");
            String query = "DELETE FROM categories WHERE id = ?";
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
