package com.example.project;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventTarget;
import com.example.project.HelloController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import java.io.*;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javafx.geometry.Pos.*;

public class HelloApplication extends Application {
    FXMLLoader contactBtnLoader = new FXMLLoader(HelloApplication.class.getResource("contactBtn.fxml"));
    FXMLLoader addBtnLoader = new FXMLLoader(HelloController.class.getResource("addBtn.fxml"));
    FXMLLoader mahmulatorLoader= new FXMLLoader(HelloController.class.getResource("mahmulator.fxml"));
    boolean is_google = true;
    HBox h = new HBox();
    HBox h2 = new HBox();
    double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
    double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

    WebView webView = new WebView();
    WebView currentWebView = webView;
    WebEngine webEngine = currentWebView.getEngine();
    HBox hbox = new HBox();
    Button yandex_Btn = new Button("y");
    Button google_Btn = new Button("g");
    Button p = new Button("Полноэкранный режим");
    int tabsNumber = 1;
    VBox vBox = new VBox();
    ArrayList<Text> tabText = new ArrayList<>();
    TextField textField = new TextField();
    Button addButton = new Button();
    ArrayList<Button> tabs = new ArrayList<>();
    Button linkGo = new Button("go!");
    ArrayList<WebView> panels = new ArrayList<>();
    Button backButton = new Button("<-Назад");
    Button forwardButton = new Button("->Вперёд");
    Button closeWindow = new Button("×");
    Button mainTab = new Button();
    Button currentTab;

    boolean is_fullScreen = false;
    boolean is_theme_light = true;
    Stage mahmulatorStage =new Stage();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        /**/
        Scene mahmulatorScene = new Scene(mahmulatorLoader.load(),400,500);
        mahmulatorStage.setScene(mahmulatorScene);
        mahmulatorStage.setTitle("Mahmulator_5000");
        mahmulatorStage.setResizable(false);
    //    mahmulatorStage.setAlwaysOnTop(true);
       // mahmulatorStage.initStyle(StageStyle.UTILITY);



        check();
        addButton.setGraphic(addBtnLoader.load());
        addButton.setBorder(null);
        addButton.setPadding(new Insets(0));
        addButton.setStyle("-fx-background-color: green");
        mainTab.setStyle("-fx-border: none");
        Rectangle roundedRectangle = new Rectangle(80, screenHeight / 16);
        roundedRectangle.setArcWidth(10);
        roundedRectangle.setArcHeight(10);
        roundedRectangle.setFill(Color.BLUE);
        mainTab.setGraphic(new StackPane(roundedRectangle, new Text("Tab")));
        mainTab.setBorder(null);
        mainTab.setPadding(new Insets(0, 4, 0, 4));
        mainTab.setStyle("-fx-background-color: skyblue");

        hbox.setStyle("-fx-background-color: skyblue;");
        hbox.setPrefWidth(screenWidth / 1.5);
        hbox.setPrefHeight(screenHeight / 13);
        hbox.setAlignment(CENTER);
        hbox.setSpacing(2);

        vBox.setPrefWidth(screenWidth);
        vBox.setPrefHeight(screenHeight * 14 / 15);



        tabs.add(mainTab);
        BorderPane border = new BorderPane();
        Scene scene = new Scene(border, screenWidth / 1.3, screenHeight / 1.3);
        panels.add(webView);
        currentWebView = webView;
        webEngine.load("https://google.com");
        webView.setPrefHeight(screenHeight);

        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
               if(!textField.getText().isEmpty()){
                   linkGo.fire();
               }
            }
        });

        google_Btn.setOnAction(e -> {
            currentWebView.getEngine().load("https://google.com");
            is_google = true;
            System.out.println(is_google);
            backButton.setDisable(false);
            check();
        });
        yandex_Btn.setOnAction(e -> {
            currentWebView.getEngine().load("https://yandex.ru");
            is_google = false;
            System.out.println(is_google);
            backButton.setDisable(false);
            check();
        });
        p.setOnAction(e -> {
            if (is_fullScreen) {
                stage.setFullScreen(false);
                is_fullScreen = false;
                p.setText("Полноэкранный режим");
            } else {
                stage.setFullScreen(true);
                is_fullScreen = true;
                p.setText("Выйти из полноэкранного режима");
            }

        });
        backButton.setOnAction(event -> {
            if (currentWebView.getEngine().getHistory().getCurrentIndex() > 0) {
                currentWebView.getEngine().getHistory().go(-1);
                forwardButton.setDisable(false);
            } else {
                backButton.setDisable(true);
            }
        });

        forwardButton.setOnAction(event -> {
            if (currentWebView.getEngine().getHistory().getCurrentIndex() < currentWebView.getEngine().getHistory().getEntries().size() - 1) {
                currentWebView.getEngine().getHistory().go(1);
                backButton.setDisable(false);
            } else {
                forwardButton.setDisable(true);
            }
        });

        Button deleteTab = new Button("Закрыть вкладку");
        deleteTab.setOnAction(actionEvent -> {
            if (tabsNumber == 1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initOwner(stage);
                alert.setTitle("Подтверждение");
                alert.setHeaderText("Выйти?");
                alert.setContentText("Если закроете последнюю вкладку, приложение закроется.");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    stage.close();
                }
            } else {
                int indexToRemove = panels.indexOf(currentWebView);
                if (indexToRemove != -1) {
                    Button buttonToRemove = tabs.get(indexToRemove);
                    hbox.getChildren().remove(buttonToRemove);
                    tabsNumber--;
                }
                if (indexToRemove - 1 > 0) {
                    WebView nextWebView = panels.get(indexToRemove - 1);
                    vBox.getChildren().remove(currentWebView);
                    vBox.getChildren().add(nextWebView);
                    currentWebView = nextWebView;
                    for (Button tab : tabs) {
                        tab.setPadding(new Insets(0, 0, 0, 0));
                        tab.setStyle("-fx-background-color: green");
                    }
                    tabs.get(indexToRemove - 1).setPadding(new Insets(0, 4, 0, 4));
                    tabs.get(indexToRemove - 1).setStyle("-fx-background-color: skyblue");

                }
                if (indexToRemove - 1 < 1) {
                    WebView nextWebView = panels.get(indexToRemove + 1);
                    vBox.getChildren().remove(currentWebView);
                    vBox.getChildren().add(nextWebView);
                    currentWebView = nextWebView;
                    for (Button tab : tabs) {
                        tab.setPadding(new Insets(0, 0, 0, 0));
                        tab.setStyle("-fx-background-color: green");
                    }
                    tabs.get(indexToRemove + 1).setPadding(new Insets(0, 4, 0, 4));
                    tabs.get(indexToRemove + 1).setStyle("-fx-background-color: skyblue");
                }

            }
        });
        Text urlText = new Text(" url:");


        linkGo.setOnAction(actionEvent -> {
            if(textField.getText().equals("google game")){
                currentWebView.getEngine().load("https://mrdoob.com/projects/chromeexperiments/google-gravity/");
            } else if (textField.getText().equals("google vacuum")) {
                currentWebView.getEngine().load("https://mrdoob.com/projects/chromeexperiments/google-space/");
            } else if (textField.getText().equals("mahmulator.run")) {
                if(!mahmulatorStage.isShowing()){
                        mahmulatorStage.show();
                }
            } else if (isURL(textField.getText())) {
                currentWebView.getEngine().load(textField.getText());
                textField.setText("");
            } else {
                showAlert(Alert.AlertType.INFORMATION, stage, "Введите ссылку", "Это поле только для ссылок");
            }
        });

        Button backchat = new Button();
        backchat.setGraphic(contactBtnLoader.load());
        backchat.setStyle("-fx-border: none; -fx-background-color: rgb(1,128,128);");
        backchat.setPadding(new Insets(0));
       /* backchat.setOnMouseEntered(mouseEvent -> {
            backchat.setStyle("-fx-border-color: #808080 ; -fx-background-color: 'darkgray';");
        });
        backchat.setOnMouseExited(mouseEvent -> {
            backchat.setStyle("-fx-border: none; -fx-background-color: rgb(128,128,128);");
        });*/
        backchat.setOnAction(actionEvent -> {
            currentWebView.getEngine().load("https://t.me/JTUFD");
        });
        Tooltip tooltip = new Tooltip("Chat with author!");
      //  tooltip.setText("");
        Tooltip.install(backchat, tooltip);
        backchat.setPrefHeight(30);
       // backchat.setPrefWidth(backchat.getPrefHeight());

        Button refresh = new Button("refresh");
        refresh.setOnAction(actionEvent -> {
            if(isInternetAvailable()){
            currentWebView.getEngine().reload();}
            else {
                checkInternetConnection(stage);
            }
        });
        Button changeTheme = new Button();
        changeTheme.setPrefWidth(screenWidth/40);
        changeTheme.setFocusTraversable(false);
        changeTheme.setStyle("-fx-shape: 'M7.5 2c-1.79 1.15-3 3.18-3 5.5s1.21 4.35 3.03 5.5C4.46 13 2 10.54 2 7.5A5.5 5.5 0 0 1 7.5 2m11.57 1.5l1.43 1.43L4.93 20.5L3.5 19.07zm-6.18 2.43L11.41 5L9.97 6l.42-1.7L9 3.24l1.75-.12l.58-1.65L12 3.1l1.73.03l-1.35 1.13zm-3.3 3.61l-1.16-.73l-1.12.78l.34-1.32l-1.09-.83l1.36-.09l.45-1.29l.51 1.27l1.36.03l-1.05.87zM19 13.5a5.5 5.5 0 0 1-5.5 5.5c-1.22 0-2.35-.4-3.26-1.07l7.69-7.69c.67.91 1.07 2.04 1.07 3.26m-4.4 6.58l2.77-1.15l-.24 3.35zm4.33-2.7l1.15-2.77l2.2 2.54zm1.15-4.96l-1.14-2.78l3.34.24zM9.63 18.93l2.77 1.15l-2.53 2.19z';-fx-background-color: black");
     //   changeTheme.setGraphic(changeThemeLoader.load());
        currentWebView.getEngine().setJavaScriptEnabled(true);
        Tooltip changeThemeTitle = new Tooltip("Switch theme");
        //  tooltip.setText("");
        Tooltip.install(backchat, changeThemeTitle);
        changeTheme.setOnAction(actionEvent -> {
            if (is_theme_light){
                is_theme_light =false;
                h2.setStyle("-fx-background-color: black");
                //currentWebView.getEngine().executeScript("document.documentElement.setAttribute('data-theme', 'dark');");
                changeTheme.setStyle("-fx-shape: 'M7.5 2c-1.79 1.15-3 3.18-3 5.5s1.21 4.35 3.03 5.5C4.46 13 2 10.54 2 7.5A5.5 5.5 0 0 1 7.5 2m11.57 1.5l1.43 1.43L4.93 20.5L3.5 19.07zm-6.18 2.43L11.41 5L9.97 6l.42-1.7L9 3.24l1.75-.12l.58-1.65L12 3.1l1.73.03l-1.35 1.13zm-3.3 3.61l-1.16-.73l-1.12.78l.34-1.32l-1.09-.83l1.36-.09l.45-1.29l.51 1.27l1.36.03l-1.05.87zM19 13.5a5.5 5.5 0 0 1-5.5 5.5c-1.22 0-2.35-.4-3.26-1.07l7.69-7.69c.67.91 1.07 2.04 1.07 3.26m-4.4 6.58l2.77-1.15l-.24 3.35zm4.33-2.7l1.15-2.77l2.2 2.54zm1.15-4.96l-1.14-2.78l3.34.24zM9.63 18.93l2.77 1.15l-2.53 2.19z';-fx-background-color: white");
            }else {is_theme_light = true;
                h2.setStyle("-fx-background-color: rgb(128,128,128)");
                changeTheme.setStyle("-fx-shape: 'M7.5 2c-1.79 1.15-3 3.18-3 5.5s1.21 4.35 3.03 5.5C4.46 13 2 10.54 2 7.5A5.5 5.5 0 0 1 7.5 2m11.57 1.5l1.43 1.43L4.93 20.5L3.5 19.07zm-6.18 2.43L11.41 5L9.97 6l.42-1.7L9 3.24l1.75-.12l.58-1.65L12 3.1l1.73.03l-1.35 1.13zm-3.3 3.61l-1.16-.73l-1.12.78l.34-1.32l-1.09-.83l1.36-.09l.45-1.29l.51 1.27l1.36.03l-1.05.87zM19 13.5a5.5 5.5 0 0 1-5.5 5.5c-1.22 0-2.35-.4-3.26-1.07l7.69-7.69c.67.91 1.07 2.04 1.07 3.26m-4.4 6.58l2.77-1.15l-.24 3.35zm4.33-2.7l1.15-2.77l2.2 2.54zm1.15-4.96l-1.14-2.78l3.34.24zM9.63 18.93l2.77 1.15l-2.53 2.19z';-fx-background-color: black");
            }
        });

        h.setAlignment(CENTER_LEFT);
        h.setSpacing(2);
        h.getChildren().addAll(google_Btn, yandex_Btn,refresh, p, forwardButton, backButton, urlText, textField, linkGo, deleteTab,changeTheme,backchat);


        //    h.setStyle("-fx-background-color: blue;");
        h.setPrefHeight(screenHeight / 25);
        h.setPrefWidth(screenWidth);
        h2.setPrefHeight(screenHeight / 25);
        h2.setPrefWidth(screenWidth);
        h2.setAlignment(CENTER_LEFT);
        closeWindow.setStyle("-fx-background-color: white;-fx-text-fill: black;-fx-font-weight: 800;-fx-font-size: 20");
        closeWindow.setOnMouseEntered(mouseEvent -> {
            closeWindow.setStyle("-fx-background-color: red;-fx-text-fill: white;-fx-font-weight: 800;-fx-font-size: 20");
        });
        closeWindow.setOnMouseExited(mouseEvent -> {
            closeWindow.setStyle("-fx-background-color: white;-fx-text-fill: black;-fx-font-weight: 800;-fx-font-size: 20");
        });
      //  closeWindow.setPadding(new Insets(2, 6, 2, 6));
        closeWindow.setPrefHeight(h2.getPrefHeight());
        closeWindow.setPrefWidth(h2.getPrefHeight());
        closeWindow.setBorder(null);
        closeWindow.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(stage);
            alert.setTitle("Подтверждение");
            alert.setHeaderText("Выйти?");
            alert.setContentText("Вы уверены что хотите выйти?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                stage.close();
                if (mahmulatorStage.isShowing()){
                    mahmulatorStage.close();
                }
            }
        });
        h2.getChildren().addAll(h, closeWindow);


        //  vBox.getChildren().addAll(h,currentWebView);
        hbox.setAlignment(CENTER);
        for (Button tab : tabs) {
            tab.setPrefHeight(screenHeight / 15);
            hbox.getChildren().add(tab);
        }
        hbox.getChildren().add(addButton);
        addButton.setOnAction(e -> {
            if (tabsNumber < 9) {
                Rectangle newroundedRectangle = new Rectangle(80, screenHeight / 16);
                Button newButton = new Button();
                newroundedRectangle.setArcWidth(10);
                newroundedRectangle.setArcHeight(10);
                newroundedRectangle.setFill(Color.BLUE);
                newButton.setGraphic(new StackPane(newroundedRectangle, new Text("new tab")));
                newButton.setStyle("-fx-background-color: green");
                newButton.setPadding(new Insets(0));
                newButton.setBorder(null);
                newButton.setPrefHeight(screenHeight / 15);
                tabs.add(newButton);
                hbox.getChildren().add(hbox.getChildren().size() - 1, newButton);
                WebView newwebView = new WebView();
                newwebView.setPrefHeight(screenHeight);
                newwebView.getEngine().load("https://www.google.com/");
                panels.add(newwebView);
                for (int i = 0; i < tabs.size(); i++) {
                    Button tabButton = tabs.get(i);
                    WebView tabWebView = panels.get(i);
                    tabButton.setOnAction(actionEvent -> {
                        vBox.getChildren().remove(currentWebView);
                        vBox.getChildren().add(tabWebView);
                        currentWebView = tabWebView;
                        for (Button tab : tabs) {
                            tab.setPadding(new Insets(0, 0, 0, 0));
                            tab.setStyle("-fx-background-color: green");
                            ((Text)((StackPane)tab.getGraphic()).getChildren().get(1)).setFill(Color.BLACK);
                            ((Text)((StackPane)tab.getGraphic()).getChildren().get(1)).setStyle("-fx-font-weight: 200");
                        }
                        tabButton.setPadding(new Insets(4, 4, 4, 4));
                        tabButton.setStyle("-fx-background-color: skyblue");
                        ((Text)((StackPane)tabButton.getGraphic()).getChildren().get(1)).setFill(Color.WHITE);
                        ((Text)((StackPane)tabButton.getGraphic()).getChildren().get(1)).setStyle("-fx-font-weight: 700");
                        System.out.println("my text should be white!");
                    });
                }
                tabsNumber++;
            } else {
                showAlert(Alert.AlertType.WARNING, stage, "Can't add a tab", "Reached the maximum number of tabs");
            }

        });
//        currentWebView.getEngine().getLoadWorker().stateProperty().addListener(
//                (ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) -> {
//                    if (newValue == Worker.State.SUCCEEDED) {
//                        enableDownload(currentWebView.getEngine());
//                    }
//                }
//        );
        ContextMenu contextMenu = new ContextMenu();
        MenuItem customMenuItem = new MenuItem("Refresh");
        customMenuItem.setOnAction(event -> {
            System.out.println("refresh");
            refresh.fire();
        });

        MenuItem customMenuItem2 = new MenuItem("Close Window");
        customMenuItem2.setOnAction(event -> {
            System.out.println("Выполнено пользовательское действие close window");
            closeWindow.fire();
        });

        MenuItem customMenuItem3 = new MenuItem("Contact with author");
        customMenuItem3.setStyle("-fx-font-weight:700;");
        customMenuItem3.setOnAction(event -> {
            System.out.println("Выполнено пользовательское действие Contact with author");
            backchat.fire();
        });
        MenuItem customMenuItemCopy = new MenuItem("Copy");
        customMenuItemCopy.setOnAction(event -> {
            String selectedText = (String) currentWebView.getEngine().executeScript("window.getSelection().toString()");
            if (!selectedText.isEmpty()) {
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                content.putString(selectedText);
                clipboard.setContent(content);
                System.out.println("Text copied to clipboard: " + selectedText);
            }
            System.out.println("Выполнено пользовательское действие copy");
        });
        MenuItem customMenuItemPaste = new MenuItem("Paste");
        customMenuItemPaste.setOnAction(event -> {
            System.out.println("Выполнено пользовательское действие paste");
                Clipboard clipboard = Clipboard.getSystemClipboard();
                    String textFromClipboard = clipboard.getString();
                    currentWebView.getEngine().executeScript("document.execCommand('insertText', false, '" + textFromClipboard + "')");
                    System.out.println("Text pasted from clipboard: " + textFromClipboard);
        });
        contextMenu.setStyle("-fx-font-size:15;-fx-background-color : gray;");
        contextMenu.getItems().addAll(customMenuItem, customMenuItem2, customMenuItem3);

        currentWebView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(currentWebView, event.getScreenX(), event.getScreenY());
                String selectedText = (String) currentWebView.getEngine().executeScript("window.getSelection().toString()");
                if (!selectedText.isEmpty()) {
                    System.out.println("yes");
                    contextMenu.getItems().add(0,customMenuItemCopy);
                } else {
                    System.out.println("no");
                }
                Clipboard clipboard = Clipboard.getSystemClipboard();
                if (clipboard.hasString()) {
                    contextMenu.getItems().add(0,customMenuItemPaste);
                }
            }
            else {
                contextMenu.hide();
            }
        });
        currentWebView.setContextMenuEnabled(false);

        vBox.getChildren().addAll(hbox, currentWebView);

        border.setTop(h2);
        border.setCenter(vBox);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Browser");
        stage.getIcons().add(new Image("C:\\Users\\acer\\IdeaProjects\\Project\\src\\main\\java\\browser-icon.png"));
        stage.setScene(scene);
        stage.show();
        checkInternetConnection(stage);

    }



    public static boolean isInternetAvailable() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("www.google.com", 80), 3000);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    public void stagePin(boolean is_pinned){
        if(is_pinned){
            mahmulatorStage.setAlwaysOnTop(true);
        }
        else {
            mahmulatorStage.setAlwaysOnTop(false);
        }
    }
    void check() {
        if (is_google) {
            google_Btn.setStyle("-fx-background-color: red");
            yandex_Btn.setStyle("-fx-background-color: rgb(128,128,128)");
        } if(!is_google) {
            yandex_Btn.setStyle("-fx-background-color: red");
            google_Btn.setStyle("-fx-background-color: rgb(128,128,128)");
        }


    }
    void checkInternetConnection(Stage stage){
        if(!isInternetAvailable()) {
            showAlert(Alert.AlertType.WARNING, stage, "Ooops...", "Looks like you are out of Internet connection :/");
        }
    }
    public static boolean isURL(String text) {
        String urlRegex = "^(https?|ftp)://.*$";
        Pattern pattern = Pattern.compile(urlRegex);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }



//    private void enableDownload(WebEngine webEngine) {
//        webEngine.setOnAlert(event -> {
//            String url = event.getData();
//            downloadFile(url, System.getProperty("user.home") + "/Desktop/");
//        });
//    }
//
//    private void downloadFile(String fileUrl, String saveDir) {
//        try {
//            URL url = new URL(fileUrl);
//            String fileName = url.getFile().substring(url.getFile().lastIndexOf('/') + 1);
//            File saveFile = new File(saveDir + fileName);
//
//            ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
//            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
//            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
//
//            fileOutputStream.close();
//            readableByteChannel.close();
//
//            System.out.println("File downloaded to: " + saveFile.getAbsolutePath());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
