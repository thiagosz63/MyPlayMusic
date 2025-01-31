package controller;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import utils.Utils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private @FXML ImageView imgGifReload;
    private @FXML Label labelCounterReload;
    private @FXML ImageView audio;
    private @FXML ImageView btnNext;
    private @FXML ImageView btnOpen;
    private @FXML ImageView btnPlay;
    private @FXML ImageView btnPreview;
    private @FXML ImageView btnReload;
    private @FXML ImageView btnStop;
    private @FXML Label currentTime;
    private @FXML MediaView mediaView;
    private @FXML Label musicName;
    private @FXML Slider musicTime;
    private @FXML AnchorPane telaAPP;
    private @FXML Label timeTotal;
    private @FXML TextField txtReload;
    private @FXML Slider volumeSelector;

    private MediaPlayer mediaPlayer;
    private final List<File> musics = new ArrayList<>();
    private int currentMusicIndex;
    private double volume = 30;
    private int control;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        volumeSelector.setValue(volume);
        musicMoveName();
        legend();
    }

    private void timeSet() {
        mediaPlayer.setOnReady(() -> {
            Duration durationTotalTimeMusic = mediaPlayer.getTotalDuration();
            int TotalSeconds = (int) durationTotalTimeMusic.toSeconds();
            int minutes = TotalSeconds / 60;
            int seconds = TotalSeconds % 60;
            timeTotal.setText(String.format("%02d:%02d", minutes, seconds));
        });

        musicTime.valueProperty().addListener((
                observable, oldValue, newValue) -> {
            if (musicTime.isValueChanging()) {
                double position = newValue.doubleValue() / 100;
                changeMusicTempo(position);
            }
        });

        mediaPlayer.currentTimeProperty().addListener((
                        observable, oldValue, newValue) -> {
                    Duration DurationCurrentTime = mediaPlayer.getCurrentTime();
                    int currentSeconds = (int) DurationCurrentTime.toSeconds();
                    int minutes = currentSeconds / 60;
                    int seconds = currentSeconds % 60;
                    currentTime.setText(String.format("%02d:%02d", minutes, seconds));

                    if (!musicTime.isValueChanging()) {
                        musicTime.setValue(newValue.toSeconds() /
                                mediaPlayer.getTotalDuration().toSeconds() * 100);
                    }
                }
        );

        if (musics.size() > 1) {
            labelCounterReload.setText((control / musics.size()) + " Reload");
        } else {
            labelCounterReload.setText((control) + " Reload");
        }

        if (control >= (Utils.tryParseToInt(txtReload.getText()) * musics.size())) {
            mediaPlayer.setOnEndOfMedia(this::changeMusic);
        } else {
            control++;
            mediaPlayer.setOnEndOfMedia(this::next);
        }

    }

    private void changeMusicTempo(double position) {
        Duration duration = mediaPlayer.getTotalDuration();
        Duration newDuration = duration.multiply(position);
        mediaPlayer.seek(newDuration);
    }

    private void musicMoveName() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(30), musicName);
        transition.setFromX(350);
        transition.setToX(-670);
        transition.setCycleCount(TranslateTransition.INDEFINITE);
        transition.play();
    }

    private void formatMusicName() {
        String name = musics.get(currentMusicIndex).getName();
        musicName.setText(name);
    }

    private void uploadMusic() {
        currentMusicIndex = 0;
        String currentMusic = musics.get(currentMusicIndex).toURI().toString();
        Media media = new Media(currentMusic);
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setVolume(volume);

        formatMusicName();
        musicMoveName();
        timeSet();
    }

    private void changeCurrentMusic() {
        String currentMusic = musics.get(currentMusicIndex).toURI().toString();
        Media media = new Media(currentMusic);
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        timeSet();
        formatMusicName();
        btnPlay.setImage(new Image(Objects.requireNonNull(getClass().getResource("/image/pause.png")).toExternalForm()));
        mediaPlayer.play();
    }

    private void changeMusic() {

        stop();
        currentMusicIndex++;
        if (currentMusicIndex >= musics.size()) {
            uploadMusic();
            control = 1;
        } else {
            changeCurrentMusic();
        }
    }

    private void changeVolume() {
        volumeSelector.valueProperty().addListener((
                        observable, oldValue, newValue) -> {
                    volume = newValue.doubleValue() / 100;
                    mediaPlayer.setVolume(volume);

                    if (volume != 0) {
                        mediaPlayer.setMute(false);
                    }
                    audio.setImage(volume > 0 ? new Image(Objects.requireNonNull(getClass().getResource("/image/audioOn.png")).toExternalForm()) :
                            new Image(Objects.requireNonNull(getClass().getResource("/image/audioOff.png")).toExternalForm()));
                }
        );
    }

    private void checkMute() {
        if (volume == 0) {
            mediaPlayer.setMute(true);
            mediaPlayer.setVolume(0);
        }
    }

    private void legend() {
        Tooltip.install(btnPlay, new Tooltip("Play & Pause"));
        Tooltip.install(btnPreview, new Tooltip("Preview"));
        Tooltip.install(btnStop, new Tooltip("Stop"));
        Tooltip.install(btnNext, new Tooltip("Next"));
        Tooltip.install(btnOpen, new Tooltip("Open"));
        Tooltip.install(audio, new Tooltip("Mute"));
        Tooltip.install(btnReload, new Tooltip("Reload"));

    }

    @FXML
    private void minimize(MouseEvent mouseEvent) {
        Stage stage = Utils.currentStage(mouseEvent);
        stage.setIconified(true);
    }

    @FXML
    private void close() {
        Platform.exit();
    }

    @FXML
    private void player() {
        if (musics.isEmpty()) {
            open();
        } else {
            if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
                mediaPlayer.pause();
                btnPlay.setImage(new Image(Objects.requireNonNull(getClass().getResource("/image/play.png")).toExternalForm()));
            } else if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED)) {
                mediaPlayer.play();
                checkMute();
                btnPlay.setImage(new Image(Objects.requireNonNull(getClass().getResource("/image/pause.png")).toExternalForm()));
            } else {
                uploadMusic();
                mediaPlayer.play();
                checkMute();
                btnPlay.setImage(new Image(Objects.requireNonNull(getClass().getResource("/image/pause.png")).toExternalForm()));

            }
        }

    }

    @FXML
    private void stop() {
        if (musics.isEmpty()) {
            open();
        } else {
            mediaPlayer.stop();
            btnPlay.setImage(new Image(Objects.requireNonNull(getClass().getResource("/image/play.png")).toExternalForm()));
        }
    }

    @FXML
    private void preview() {
        if (musics.isEmpty()) {
            open();
        } else {
            stop();
            currentMusicIndex--;
            if (currentMusicIndex < 0) {
                currentMusicIndex = musics.size() - 1;
            }
            changeCurrentMusic();
        }
    }

    @FXML
    private void next() {
        if (musics.isEmpty()) {
            open();
        } else {
            stop();
            currentMusicIndex++;
            if (currentMusicIndex >= musics.size()) {
                currentMusicIndex = 0;
            }
            changeCurrentMusic();
        }
    }

    @FXML
    private void mute() {
        if (musics.isEmpty()) {
            open();
        } else {
            if (mediaPlayer.isMute()) {
                mediaPlayer.setMute(false);
                audio.setImage(new Image(Objects.requireNonNull(getClass().getResource("/image/audioOn.png")).toExternalForm()));
                mediaPlayer.setVolume(volumeSelector.getValue());
                volume = volumeSelector.getValue();
            } else {
                mediaPlayer.setMute(true);
                audio.setImage(new Image(Objects.requireNonNull(getClass().getResource("/image/audioOff.png")).toExternalForm()));
                volume = 0;
            }
        }
    }

    @FXML
    private void open() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP3", "*.mp3"),
                new FileChooser.ExtensionFilter("MP4", "*.mp4"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));

        Stage stage = (Stage) telaAPP.getScene().getWindow();
        List<File> file = fileChooser.showOpenMultipleDialog(stage);

        if (file != null) {

            if (mediaPlayer != null) {
                stop();
                musics.clear();
            }
            control = 0;
            musics.addAll(file);
            uploadMusic();
            changeVolume();
            player();

        }
    }

    public void reload() {
        txtReload.setVisible(!txtReload.isVisible());
        labelCounterReload.setVisible(!labelCounterReload.isVisible());
        imgGifReload.setVisible(!imgGifReload.isVisible());
    }
}