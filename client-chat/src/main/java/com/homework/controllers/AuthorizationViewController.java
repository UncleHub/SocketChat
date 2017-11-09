package com.homework.controllers;

import com.homework.entity.User;
import com.homework.service.AuthorizationService;
import com.homework.utils.Context;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Serega on 05.11.2017.
 */
public class AuthorizationViewController {


    public Label emailWrongLabel;
    public Label passwordWrongLabel;
    public TextField emailField;
    public PasswordField passwordField;
    public Button signUpButton;


    public void loginPress(ActionEvent actionEvent) {
        if (!emailField.getText().isEmpty() && !passwordField.getText().isEmpty()) {

            User user = new User(emailField.getText(), passwordField.getText());

            AuthorizationService authorizationService = new AuthorizationService();
            User approvedUser = authorizationService.login(user);
            if (approvedUser != null) {
                Context.getInstance().setUser(approvedUser);
                System.out.println("user was put in context");
                try {
                    setWindow("chatWindow.fxml", "Chat", actionEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                emailWrongLabel.setText("Some thing gone wrong.");
                passwordWrongLabel.setText("May be password incorrect.");
                System.out.println("no user in context");
            }
        }
    }

    public void signUpPress(ActionEvent actionEvent) {
        passwordWrongLabel.setText(" ");
        if (!emailField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
            User user = new User(emailField.getText(), passwordField.getText());

            AuthorizationService authorizationService = new AuthorizationService();
            User approvedUser = authorizationService.signUpUser(user);
            if (approvedUser != null) {
                Context.getInstance().setUser(approvedUser);
                try {
                    setWindow("chatWindow.fxml", "Chat", actionEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                emailWrongLabel.setText("User with this email already exists");
            }
        }
    }

    public void setWindow(String name, String title, ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource(name));
        Scene scene = new Scene(parent);
        Stage nextStage = ( Stage ) (( Node ) actionEvent.getSource()).getScene().getWindow();
        nextStage.setScene(scene);
        nextStage.setTitle(title);
        nextStage.show();
    }
}
