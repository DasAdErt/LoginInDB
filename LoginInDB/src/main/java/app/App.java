package app;

import database.DBConnector;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void start(){
        System.out.print("Выберите действие:\n1. Login\n2. Register\nВаш выбор: ");
        switch (checkChoice()){
            case 1 -> enterSystem();
            case 2 -> registrationSystem();
            default -> {
                System.out.println("No No No No");
                start();
            }
        }
    }

    private static void enterSystem() {
//        try {
//            String request = "SELECT * FROM public.users";
//            Statement statement = DBConnector.connect().createStatement();
//            ResultSet resultSet = statement.executeQuery(request);
            //String enterLogin = enterData("Введите логин: ");
            //String enterPassword = enterData("Введите пароль: ");

//            while (resultSet.next()) {
//                String login = resultSet.getString("login");
//                String password = resultSet.getString("password");
//
//                if (login.equals(enterLogin)){
//                    if (password.equals(enterPassword)){
//                        System.out.println("Вы успешно вошли в аккаунт!");
//                        break;
//                    } else {
//                        System.out.println("Не верный пароль!");
//                    }
//                } else {
//                    System.out.println("Нет логина!\n");
//                }
//            }
//        }
//        catch (SQLException exception) {
//            System.out.println(exception);
//        }

        try (Connection connection = DBConnector.connect()) {
            String sql = "SELECT * FROM users WHERE login = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, enterData("Введите логин: "));
                preparedStatement.setString(2, enterData("Введите пароль: "));

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    System.out.println("Вы вошли!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void registrationSystem() {
        System.out.print("Введите id: ");
        int id = new Scanner(System.in).nextInt();
        System.out.print("Введите name: ");
        String name = new Scanner(System.in).nextLine();
        System.out.print("Введите login: ");
        String login = new Scanner(System.in).nextLine();
        System.out.print("Введите password: ");
        String password = new Scanner(System.in).nextLine();

        String request = "INSERT INTO users (id, name, login, password) VALUES (?, ?, ?, ?)";

        if (!checkLoginAndId(login, id)){
            try (Connection conn = DBConnector.connect();
                 PreparedStatement registerStatement = conn.prepareStatement(request)) {

                registerStatement.setInt(1, id);
                registerStatement.setString(2, name);
                registerStatement.setString(3, login);
                registerStatement.setString(4, password);

                int rowsAffected = registerStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Регистрация прошла успешно!");
                } else {
                    System.out.println("Ошибка при регистрации пользователя.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Есть дубликат!");
        }
    }

    private static boolean checkLoginAndId(String login, int id){
        String checkLoginAndId = "SELECT * FROM users WHERE login = ? AND Id = ?";
        try (Connection conn = DBConnector.connect();
             PreparedStatement loginStatement = conn.prepareStatement(checkLoginAndId)) {

            loginStatement.setString(1, login);
            loginStatement.setInt(2, id);

            ResultSet requestCheck = loginStatement.executeQuery();

            if (requestCheck.next()){
                return true;
            } else {
                return false;
            }

        } catch (PSQLException e) {
            System.out.println("PSQL Exception");
            return false;
        } catch (SQLException e) {
            System.out.println("SQL Exception");
            return false;
        }
    }

    private static String enterData(String message) {
        System.out.print(message);
        return new Scanner(System.in).nextLine();
    }

    private static int checkChoice(){
        try {
            return new Scanner(System.in).nextInt();
        }
        catch (InputMismatchException e){
            System.out.println("Error input");
            return 0;
        }
    }
}
