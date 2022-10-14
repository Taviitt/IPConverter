package net.tavx.ipconverter;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ButtonController {

    private final String DEFAULT_IP_TEXT = "Enter IP Address...";

    Image exitBtnOff = getImageFromPath("/images/close-icon.png");
    Image exitBtnOn = getImageFromPath("/images/close-icon-on.png");
    Image minBtnOff = getImageFromPath("/images/minimize-icon.png");
    Image minBtnOn = getImageFromPath("/images/minimize-icon-on.png");

    @FXML
    private ImageView exit_button, minimize_button;

    @FXML
    private Pane clearPane;

    @FXML Label ipLabel, binaryLabel;

    private String ip = "";
    private final List<KeyCode> allowed_keys = Arrays.asList(
            KeyCode.DIGIT1,
            KeyCode.DIGIT2,
            KeyCode.DIGIT3,
            KeyCode.DIGIT4,
            KeyCode.DIGIT5,
            KeyCode.DIGIT6,
            KeyCode.DIGIT7,
            KeyCode.DIGIT8,
            KeyCode.DIGIT9,
            KeyCode.DIGIT0,
            KeyCode.BACK_SPACE,
            KeyCode.DELETE,
            KeyCode.ENTER,
            KeyCode.PERIOD,
            KeyCode.C
    );

    @FXML
    public void init(Scene scene) {
        // add event for typing in main window scene
        scene.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();

            if(!allowed_keys.contains(key)) return;

            switch (key) {
                case C -> {
                    this.ip = "";
                    ipLabel.setText(DEFAULT_IP_TEXT);
                    binaryLabel.setText("");
                }
                case BACK_SPACE, DELETE -> {
                    if(this.ip.equals("")) return;
                    // reset to default text after last char is removed
                    if(this.ip.length() == 1) {
                        this.ip = "";
                        ipLabel.setText(DEFAULT_IP_TEXT);
                        return;
                    }
                    // remove last char
                    this.ip = this.ip.substring(0, this.ip.length() - 1);
                    ipLabel.setText(this.ip);
                }
                case ENTER -> {
                    // check if valid ip address then convert to binary
                    this.ip = this.ip.replace(".", "");
                    ipLabel.setText(DEFAULT_IP_TEXT);
                    this.ip = convertIPToBinary(this.ip);
                    binaryLabel.setText(this.ip);
                    this.ip = "";
                }
                default -> {
                    if(this.ip.replace(".", "").length() == 15) return;
                    this.ip = this.ip + key.getChar();
                    ipLabel.setText(this.ip);
                }
            }
        });
    }

    /**
     *
     * Called when exit button is clicked
     *
     * @param event | Mouse event
     */
    @FXML
    void closeApplication(MouseEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     *
     * Called when minimize button is clicked
     *
     * @param event | Mouse event
     */
    @FXML
    void minimizeApplication(MouseEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     *
     * Called when clear button is pressed
     *
     */
    @FXML
    void onClear() {
        if(!this.ip.equals("") || binaryLabel.getText().length() != 0) {
            this.ip = "";
            ipLabel.setText(DEFAULT_IP_TEXT);
            binaryLabel.setText("");
        }
    }

    /**
     *
     * Called when mouse hovers exit button
     *
     */
    @FXML
    void mouseEnter() {
        exit_button.setImage(exitBtnOn);
    }

    /**
     *
     * Called when mouse stops hovering exit button
     *
     */
    @FXML
    void mouseExit() {
        exit_button.setImage(exitBtnOff);
    }

    /**
     *
     * Called when mouse stops hovering minimize button
     *
     */
    @FXML
    void minimizeExit() {
        minimize_button.setImage(minBtnOff);
    }

    /**
     *
     * Called when mouse hovers minimize button
     *
     */
    @FXML
    void minimizeEnter() {
        minimize_button.setImage(minBtnOn);
    }

    @FXML
    void onMouseEnterClear() {
        clearPane.setStyle("-fx-background-color: #1e1e1e; -fx-background-radius: 20");
    }

    @FXML
    void onMouseExitClear() {
        clearPane.setStyle("-fx-background-color: #272727; -fx-background-radius: 20");
    }

    /**
     *
     * Get image from specified path
     *
     * @param path | Where image is located
     */
    private Image getImageFromPath(String path) {
        return new Image(Objects.requireNonNull(IPConverter.class.getResourceAsStream(path)));
    }

    /**
     *
     * Convert IP address to binary form
     *
     * @param input | provided IP address
     * @return converted IP address
     */
    private String convertIPToBinary(String input) {
        String[] octetArray = input.split("\\.");
        for (String string : octetArray) {
            long octet = Long.parseLong(string);
            return Long.toBinaryString(octet);
        }
        return "N/A";
    }

}
