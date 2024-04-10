package com.example.project;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

public class FocusMode extends Application {
      int time=0;
    double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
    double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
      boolean isFocusModeOn =false;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Focus Mode JavaFX");
        GridPane gridPane = createFocusModePane();
        addUIControls(gridPane);
        Scene scene = new Scene(gridPane, 800, 500);
        gridPane.setStyle("-fx-background-color: blueviolet");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private GridPane createFocusModePane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40, 40, 40, 40));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        Label headerLabel = new Label("Put your PC away");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        headerLabel.setTextFill(Color.WHITE);
        gridPane.add(headerLabel, 0,0,3,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

        Label label = new Label("Focus on something important");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
      label.setTextFill(Color.KHAKI);
        gridPane.add(label, 0,1,3,1);
        GridPane.setHalignment(label, HPos.CENTER);
        GridPane.setMargin(label, new Insets(20, 0,20,0));

        Text selectedTime = new Text("Selected Time: ");
        selectedTime.setFill(Color.DARKTURQUOISE);
        gridPane.add(selectedTime, 0, 3, 3, 1);
        GridPane.setHalignment(selectedTime, HPos.CENTER);
        GridPane.setMargin(selectedTime, new Insets(20, 0,20,0));


        Button timeButton= new Button("1 min");
        timeButton.setBorder(null);
        timeButton.setPrefHeight(40);
        timeButton.setPrefWidth(100);
        timeButton.setFocusTraversable(false);
        gridPane.add(timeButton, 0, 4, 1, 1);
        GridPane.setHalignment(timeButton, HPos.CENTER);
        GridPane.setMargin(timeButton, new Insets(20, 0,20,0));
        timeButton.setOnAction(e->{
            time=10;
            selectedTime.setText("Selected Time: 1min");
        });

        Button timeButton2= new Button("2 min");
        timeButton2.setBorder(null);
        timeButton2.setPrefHeight(40);
        timeButton2.setPrefWidth(100);
        timeButton2.setFocusTraversable(false);
        gridPane.add(timeButton2, 1, 4, 1, 1);
        GridPane.setHalignment(timeButton2, HPos.CENTER);
        GridPane.setMargin(timeButton2, new Insets(20, 0,20,0));
        timeButton2.setOnAction(e->{
            time=20;
            selectedTime.setText("Selected Time: 2min");
        });
        Button timeButton3= new Button("3 min");
        timeButton3.setBorder(null);
        timeButton3.setPrefHeight(40);
        timeButton3.setPrefWidth(100);
        timeButton3.setFocusTraversable(false);
        gridPane.add(timeButton3, 2, 4, 1, 1);
        GridPane.setHalignment(timeButton3, HPos.CENTER);
        GridPane.setMargin(timeButton3, new Insets(20, 0,20,0));
        timeButton3.setOnAction(e->{
            time=30;
            selectedTime.setText("Selected Time: 3min");
        });
       /* Button timeButton4= new Button("4 min");
        timeButton4.setBorder(null);
        timeButton4.setPrefHeight(40);
        timeButton4.setPrefWidth(100);
        timeButton4.setFocusTraversable(false);
        gridPane.add(timeButton4, 3, 4, 1, 1);
        GridPane.setHalignment(timeButton4, HPos.CENTER);
        GridPane.setMargin(timeButton4, new Insets(20, 0,20,0));
        timeButton4.setOnAction(e->{
            time=40;
            selectedTime.setText("Selected Time: 4min");
        });*/

        Button submitButton = new Button("Submit");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        gridPane.add(submitButton, 0, 5, 3, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0,20,0));
        submitButton.setOnAction(e->{
            if(time == 0){
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(),"Error","Enter time");
            }else {
                if (!isFocusModeOn){
                    System.out.println("Start for "+time/60+"min");
                    TurnFocusMode(time,gridPane);
                }
            }
        });

    }
    private void TurnFocusMode(int time,GridPane gridPane) {
        isFocusModeOn = true;
        Stage timedStage = new Stage();
        timedStage.setTitle("Temporary Window");
        Label text = new Label("Stay Focused");
        text.setFont(Font.font("Roboto", FontWeight.BOLD, 45));
        text.setTextFill(Color.WHITE);
        Label label = new Label("This window will disappear in "+time+" seconds");
        label.setFont(Font.font("Roboto", FontWeight.BOLD, 34));
        label.setTextFill(Color.ORANGERED);

        VBox root = new VBox();
        root.setStyle("-fx-alignment: center;");


        root.getChildren().addAll(text,label);
        timedStage.setFullScreen(true);
        timedStage.setResizable(false);
        timedStage.setOnCloseRequest(event -> event.consume());
        timedStage.fullScreenExitHintProperty().setValue("");
        timedStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        root.setBackground(
                new Background(
                        Collections.singletonList(new BackgroundFill(
                                Color.WHITE,
                                new CornerRadii(0),
                                new Insets(0))),
                        Collections.singletonList(new BackgroundImage(
                                new Image("file:C:\\Users\\acer\\IdeaProjects\\Project\\src\\main\\java\\background.png", screenWidth, screenHeight, false, true),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.DEFAULT,
                                new BackgroundSize(1.0, 1.0, true, true, false, false)
                        ))));
        timedStage.setScene(new Scene(root, screenWidth, screenHeight));
        timedStage.show();

        AtomicInteger timeInMillis = new AtomicInteger(time);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    timeInMillis.decrementAndGet();
                    if (timeInMillis.get() > 0) {
                        label.setText("This window will disappear in " + timeInMillis.get() + " seconds");
                    } else {
                        label.setText("Time's up!");
                        Timeline timeline1 = new Timeline(
                                new KeyFrame(Duration.seconds(1),event1->{
                                    timedStage.close();
                                    isFocusModeOn=false;
                                })
                        );
                        timeline1.play();
                        showAlert(Alert.AlertType.INFORMATION, gridPane.getScene().getWindow(),"Congratulations","You stayed focusing for "+time+"seconds");
                    }
                })
        );
        timeline.setCycleCount(time);
        timeline.play();
    }
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
