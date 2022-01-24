package program;

import CRUD.CRUD_Category;
import CRUD.CRUD_News;
import models.Categories;
import models.News;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String strConn = "jdbc:mariadb://localhost:3306/news";
        // Накатать таблицы
        //CreateTBL_DB(strConn);
        //System.out.println("Successful connection");

        // CRUD для категорій
//        CRUD_Category category = new CRUD_Category();
//
//        category.InsertIntoDB(strConn);
//        List<Categories> list = category.SelectFromDB(strConn);
//        category.PrintCategoriesList(list);
//        category.UpdateForDB(strConn);
//        category.PrintCategoriesList(category.SelectFromDB(strConn));
//        category.DeleteFromDB(strConn);
//        category.PrintCategoriesList(category.SelectFromDB(strConn));

        // CRUD для категорій
        CRUD_News news = new CRUD_News();

        news.InsertIntoDBNews(strConn);
        List<News> listNews = news.SelectFromDBNews(strConn);
        news.PrintNewsList(listNews);
        news.UpdateForDBNews(strConn);
        news.PrintNewsList(news.SelectFromDBNews(strConn));
        news.DeleteFromDB(strConn);
        news.PrintNewsList(news.SelectFromDBNews(strConn));


    }

    private static  void CreateTBL_DB(String strConn) {
        try(Connection conn = DriverManager.getConnection(strConn, "root", "")) {
            System.out.println("Successful connection");
            String query_categories = "CREATE TABLE categories (ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(50))";
            String query_news = "CREATE TABLE news (ID INT PRIMARY KEY AUTO_INCREMENT, TEXT VARCHAR(200), CATEGORY_ID INT NOT NULL REFERENCES categories(ID))";
            try (Statement statement = conn.createStatement()) {
                statement.executeUpdate(query_categories);
                statement.executeUpdate(query_news);

            }
            catch (Exception ex) {
                System.out.println("Error statements: " + ex.getMessage());
            }

        } catch (Exception ex) {
            System.out.println("Error connection: " + ex.getMessage());
        }

    }

}
