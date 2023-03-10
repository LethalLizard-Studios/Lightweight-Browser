package net.lelandcarter.lightweightBrowser;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

/* Created By: Leland Carter
-- Last Modified 03/09/2023
*/

public class MainController implements Initializable {

    @FXML
    private TextField searchBar;
    @FXML
    private Label webTitle;

    @FXML
    private WebView webView;
    @FXML
    private WebEngine webEngine;

    @FXML
    private ToolBar favoritesBar;

    @FXML
    private Button favoriteBtn;
    @FXML
    private Button fullscreenBtn;
    @FXML
    private Button refreshBtn;
    @FXML
    private Button homeBtn;
    @FXML
    private Button forwardBtn;
    @FXML
    private Button backBtn;

    private ReadJSON reader;
    private String currentUrl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Load User's Profile
        reader = ReadJSON.getInstance();
        reader.getProfile();

        //Load Favorites
        loadBookmarksBar();

        webEngine = webView.getEngine();
        loadWebPage(reader.homeUrl);
    }

    private void loadBookmarksBar() {
        favoritesBar.getItems().clear();
        favoritesBar.getItems().addAll(fullscreenBtn, backBtn, forwardBtn, homeBtn, favoriteBtn, searchBar, refreshBtn);

        for (Bookmarks bookmark : reader.bookmarks) {
            Button button = new Button();
            button.setOnAction((ActionEvent event) -> {
                loadWebPage(bookmark.url);
            });
            button.setText(bookmark.name);
            button.setStyle("-fx-background-color: #3d3e43; -fx-background-radius: 16; -fx-text-fill: #ffffff");
            button.setPrefHeight(26);

            favoritesBar.getItems().add(button);
        }
    }

    public void loadWebPage(String url) {
        final String httpUrl = "https://";
        final String wwwUrl = "www.";

        String finalUrl = url;

        if (url.startsWith("https://") || url.startsWith("http://")) {
            webEngine.load(url);
        }
        else {
            if (url.startsWith("www.")) {
                webEngine.load(httpUrl+url);
                finalUrl = httpUrl+url;
            }
            else {
                if (url.contains(".")) {
                    webEngine.load(httpUrl+wwwUrl+url);
                    finalUrl = httpUrl+wwwUrl+url;
                }
                else {
                    webEngine.load(reader.searchUrl+url);
                    finalUrl = reader.searchUrl+url;
                }
            }
        }

        setName(finalUrl);
    }

    private void setName(String str) {
        searchBar.setText(str);
        webTitle.setText(str);
        currentUrl = str;
    }

    @FXML
    protected void back(ActionEvent event) {
        WebHistory history = webEngine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();

        if (history.getCurrentIndex() > 0)
            history.go(-1);

        setName(entries.get(history.getCurrentIndex()).getUrl());
    }

    @FXML
    protected void forward(ActionEvent event) {
        WebHistory history = webEngine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();

        if (history.getCurrentIndex()+1 < entries.size())
            history.go(+1);

        setName(entries.get(history.getCurrentIndex()).getUrl());
    }

    @FXML
    protected void refresh(ActionEvent event) {
        webEngine.reload();
    }

    @FXML
    protected void search(ActionEvent event) {
        loadWebPage(searchBar.getText().toString());
    }

    @FXML
    protected void home(ActionEvent event) {
        loadWebPage(reader.homeUrl);
    }

    @FXML
    protected void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    protected void fullscreen(ActionEvent event) {
        Stage stage = MainApplication.stage;
        stage.setFullScreen(!stage.isFullScreen());

        if (stage.isFullScreen())
            fullscreenBtn.setText("Fullscreen [X] ");
        else
            fullscreenBtn.setText("Fullscreen [  ]");
    }

    @FXML
    protected void addToFavorites(ActionEvent event) {
        String name = webEngine.getTitle();

        if (name == null)
            name = currentUrl;

        Bookmarks book = new Bookmarks(name, currentUrl);

        reader.bookmarks.add(book);
        loadBookmarksBar();
        //reader.addBookmark(book);
    }
}