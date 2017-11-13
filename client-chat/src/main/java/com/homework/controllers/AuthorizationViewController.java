package com.homework.controllers;

import com.homework.entity.User;
import com.homework.service.AuthorizationService;
import com.homework.utils.Context;
import javafx.application.Platform;
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
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by Serega on 05.11.2017.
 */
public class AuthorizationViewController {

    final static Logger logger = Logger.getLogger(AuthorizationViewController.class);


    public Label emailWrongLabel;
    public Label passwordWrongLabel;
    public TextField emailField;
    public PasswordField passwordField;
    public Button signUpButton;


    public void loginPress(ActionEvent actionEvent) {
        if (!emailField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
            logger.info("user try to login");

            User user = new User(emailField.getText(), passwordField.getText());

            AuthorizationService authorizationService = new AuthorizationService();
            User approvedUser = authorizationService.login(user);
            if (approvedUser != null) {
                Context.getInstance().setUser(approvedUser);
                logger.info("user join");
                try {
                    setWindow("chatWindow.fxml", "Chat", actionEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                emailWrongLabel.setText("Some thing gone wrong.");
                passwordWrongLabel.setText("May be password incorrect.");
                logger.error("user was refused for login");
            }
        }
    }

    public void signUpPress(ActionEvent actionEvent) {
        passwordWrongLabel.setText(" ");
        if (!emailField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
            User user = new User(emailField.getText(), passwordField.getText());
            logger.info("user try to sign up");

            AuthorizationService authorizationService = new AuthorizationService();
            User approvedUser = authorizationService.signUpUser(user);
            if (approvedUser != null) {
                Context.getInstance().setUser(approvedUser);
                logger.info("user sign upp successfully");
                try {
                    setWindow("chatWindow.fxml", "Chat", actionEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                emailWrongLabel.setText("User with this email already exists");
                logger.error("sign up was denied");
            }
        }
    }

    public void setWindow(String name, String title, ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(name));
        Parent parent = fxmlLoader.load();
        ChatWindowController controller = fxmlLoader.getController();
        Scene scene = new Scene(parent);
        Stage nextStage = ( Stage ) (( Node ) actionEvent.getSource()).getScene().getWindow();
        nextStage.setOnHidden(e -> {
            controller.shutdown();
            Platform.exit();
        });
        nextStage.setScene(scene);
        nextStage.setTitle(title);

        nextStage.show();
    }
}
