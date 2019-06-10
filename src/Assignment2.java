// Steven Causley
// CS-102, Spring 2019
// Assignment 2

import TennisDatabase.TennisDatabase;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import TennisDatabase.TennisDatabase;
import TennisDatabase.TennisPlayer;
import TennisDatabase.TennisMatch;
import TennisDatabase.TennisDatabaseRuntimeException;
import TennisDatabase.TennisDatabaseException;

public class Assignment2 extends Application {
    static TennisDatabase database = new TennisDatabase();

    public static void main(String[] args) {launch(args);}

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tennis Database Manager");

        TableView<TennisPlayer> playerTableView = new TableView<TennisPlayer>();
        TableView<TennisMatch> matchTableView = new TableView<TennisMatch>();

        TableColumn<TennisPlayer, String> id = new TableColumn<TennisPlayer, String>("Id");
        id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        TableColumn<TennisPlayer, String> name = new TableColumn<TennisPlayer, String>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("FullName"));
        TableColumn<TennisPlayer, String> birthYear = new TableColumn<TennisPlayer, String>("BirthYear");
        birthYear.setCellValueFactory(new PropertyValueFactory<>("BirthYear"));
        TableColumn<TennisPlayer, String> country = new TableColumn<TennisPlayer, String>("Country");
        country.setCellValueFactory(new PropertyValueFactory<>("Country"));
        TableColumn<TennisPlayer, String> wins = new TableColumn<TennisPlayer, String>("Wins");
        wins.setCellValueFactory(new PropertyValueFactory<>("Wins"));
        TableColumn<TennisPlayer, String> losses = new TableColumn<TennisPlayer, String>("Losses");
        losses.setCellValueFactory(new PropertyValueFactory<>("Losses"));

        playerTableView.getColumns().addAll(id, name, birthYear, country, wins, losses);

        TableColumn<TennisMatch, String> id1 = new TableColumn<TennisMatch, String>("Player 1 Id");
        id1.setCellValueFactory(new PropertyValueFactory<>("IdPlayer1"));
        TableColumn<TennisMatch, String> id2 = new TableColumn<TennisMatch, String>("Player 2 Id");
        id2.setCellValueFactory(new PropertyValueFactory<>("IdPlayer2"));
        TableColumn<TennisMatch, String> date = new TableColumn<TennisMatch, String>("Date");
        date.setCellValueFactory(new PropertyValueFactory<>("DateString"));
        TableColumn<TennisMatch, String> tournament = new TableColumn<TennisMatch, String>("Tournament");
        tournament.setCellValueFactory(new PropertyValueFactory<>("Tournament"));
        TableColumn<TennisMatch, String> scores = new TableColumn<TennisMatch, String>("Scores");
        scores.setCellValueFactory(new PropertyValueFactory<>("MatchScore"));

        matchTableView.getColumns().addAll(id1, id2, date, tournament, scores);

        playerTableView.setItems(this.getPlayers());
        matchTableView.setItems(this.getMatches());

        Button loadButton = new Button("Load From File");
        TextField loadFileName = new TextField();
        loadFileName.setPromptText("File Name");
        loadButton.setOnAction(
                eventLoadFromFile -> {
                    try {
                        String fileName = loadFileName.getText();
                        database.loadFromFile(fileName);
                        playerTableView.setItems(this.getPlayers());
                        matchTableView.setItems(this.getMatches());
                    } catch (TennisDatabaseException | TennisDatabaseRuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                }
        );

        Button saveButton = new Button("Save to File");
        TextField saveFileName = new TextField();
        saveFileName.setPromptText("File Name");
        saveButton.setOnAction(
                eventSaveToFile -> {
                    try {
                        String fileName = saveFileName.getText();
                        database.saveToFile(fileName);
                    } catch (TennisDatabaseException e) {
                        System.out.println(e.getMessage());
                    }
                }
        );

        Button resetButton = new Button("Reset Database");
        resetButton.setOnAction(
                eventReset -> {
                    try {
                        database.reset();
                        playerTableView.setItems(this.getPlayers());
                        matchTableView.setItems(this.getMatches());
                    } catch (TennisDatabaseRuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                }
        );

        Button deleteButton = new Button("Delete Player");
        TextField deleteId = new TextField();
        deleteId.setPromptText("Player Id");
        deleteButton.setOnAction(
                deleteEvent -> {
                    try{
                        database.deletePlayer(deleteId.getText());
                        playerTableView.setItems(this.getPlayers());
                        matchTableView.setItems(this.getMatches());
                    } catch (TennisDatabaseRuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                }
        );

        Button insertPlayerButton = new Button("Insert Player");
        TextField insertId = new TextField();
        insertId.setPromptText("Player Id");
        TextField insertFName = new TextField();
        insertFName.setPromptText("First Name");
        TextField insertLName = new TextField();
        insertLName.setPromptText("Last Name");
        TextField insertBirthYear = new TextField();
        insertBirthYear.setPromptText("Birth Year <YYYY>");
        TextField insertCountry = new TextField();
        insertCountry.setPromptText("Country");
        insertPlayerButton.setOnAction(
                insertPlayerEvent -> {
                    try {
                        database.insertPlayer(insertId.getText(), insertFName.getText(), insertLName.getText(), Integer.valueOf(insertBirthYear.getText()), insertCountry.getText());
                        playerTableView.setItems(this.getPlayers());
                        matchTableView.setItems(this.getMatches());
                    } catch (TennisDatabaseException e) {
                        System.out.println(e.getMessage());
                    }
                }
        );

        Button insertMatchButton = new Button("Insert Match");
        TextField insertId1 = new TextField();
        insertId1.setPromptText("Player1 Id");
        TextField insertId2 = new TextField();
        insertId2.setPromptText("Player 2 Id");
        TextField insertYear = new TextField();
        insertYear.setPromptText("Year");
        TextField insertMonth = new TextField();
        insertMonth.setPromptText("Month");
        TextField insertDay = new TextField();
        insertDay.setPromptText("Day");
        TextField insertTournament = new TextField();
        insertTournament.setPromptText("Tournament");
        TextField insertScore = new TextField();
        insertScore.setPromptText("Score");
        insertMatchButton.setOnAction(
                insertMatchEvent -> {
                    try {
                        database.insertMatch(insertId1.getText(), insertId2.getText(),
                                Integer.valueOf(insertYear.getText()),Integer.valueOf(insertMonth.getText()),
                                Integer.valueOf(insertDay.getText()), insertTournament.getText(), insertScore.getText() );
                        playerTableView.setItems(this.getPlayers());
                        matchTableView.setItems(this.getMatches());
                    } catch (TennisDatabaseException e) {
                        System.out.println(e.getMessage());
                    }
                }
        );




        HBox databaseHBox = new HBox(30);
        databaseHBox.getChildren().addAll(playerTableView, matchTableView);

        VBox vBox = new VBox(30);
        vBox.getChildren().addAll(databaseHBox, loadButton, loadFileName, saveButton, saveFileName, resetButton, deleteButton, deleteId);

        VBox playerBox = new VBox(30);
        playerBox.getChildren().addAll(insertPlayerButton, insertId, insertFName, insertLName, insertBirthYear,
                insertCountry, insertMatchButton, insertId1, insertId2, insertYear, insertMonth, insertDay, insertTournament, insertScore);

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(vBox);
        borderPane.setRight(playerBox);
        Scene scene = new Scene(borderPane, 1500, 800);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public ObservableList getPlayers() {
        ObservableList<TennisPlayer> players = FXCollections.observableArrayList();

        try {
            players.addAll(database.getAllPlayers());
        } catch (TennisDatabaseRuntimeException e) {
            throw e;
        }
        return players;
    }

    public ObservableList getMatches() {
        ObservableList<TennisMatch> matches = FXCollections.observableArrayList();

        try {
            matches.addAll(database.getAllMatches());
        } catch (TennisDatabaseRuntimeException e) {
            throw e;
        }
        return matches;
    }

}
