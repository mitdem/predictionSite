package com.example.predictionSite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.*;
import java.util.Random;

@Controller
public class MainController {

    @GetMapping("/prediction")
    public String getPrediction(Model model) {

        // Массив с несколькими предсказаниями
        String[] predictions = new String[]{
                "ты найдёш монетку",
                "я не знаю",
                "найдёш пицу на скамейке в парке",
                "купиш шаверму",
                "поговориш с другом",
                "пойдёш в школ,универ,работу",
                "Вероятно что ты это я",
                ":)",
                "Затрудняюсь ответить",
                "=^-^=",
                "EROR"
        };

        Random random = new Random();
        int num = random.nextInt(11);
        // Переменная для нашего предсказания
        String ourPrediction = null;
        try {
            // Переменная с именем драйвера для поделючения
            String driverName = "org.h2.Driver";
            // URL, где база находится и имя базы
            String url = "jdbc:h2:mem:testdb";
            // Имя пользователя
            String user = "sa";
            // Пароль
            String password = "sa";
            // Наше подключение
            Connection connection = DriverManager.getConnection(url,user,password);
            // Наш запрос
            String query = "SELECT * FROM prediction WHERE id =" + num + ";";
            // Создаем заявку для нашего запроса (сеанс)
            Statement statement = connection.createStatement();
            // Выполняем запрос и записываем результат в ResultSet
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                ourPrediction = resultSet.getString("name");
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        model.addAttribute("message", predictions[num]);
        return "prediction";
    }
}
