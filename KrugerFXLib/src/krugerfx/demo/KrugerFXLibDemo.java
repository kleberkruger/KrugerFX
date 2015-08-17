/*
 * Copyright (C) 2015 kleberkruger
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package krugerfx.demo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import krugerfx.scene.ShadedScene;

/**
 *
 * @author kleberkruger
 */
public class KrugerFXLibDemo extends Application {

    @Override
    public void start(Stage stage) {

//        StageButtons sb = new StageButtons();
        HBox root = new HBox();
        
        Button b1 = new Button();
        b1.setOnAction((ActionEvent event) -> {
            ((Stage)((Button)event.getSource()).getScene().getWindow()).close();
        });
        Button b2 = new Button();
        b2.setOnAction((ActionEvent event) -> {
            Stage st = ((Stage)((Button)event.getSource()).getScene().getWindow());
            st.setIconified(!st.isIconified());
        });
        Button b3 = new Button();
        b3.setOnAction((ActionEvent event) -> {
            Stage st = ((Stage)((Button)event.getSource()).getScene().getWindow());
            st.setMaximized(!st.isMaximized());
        });
        Button b4 = new Button();
        b4.setOnAction((ActionEvent event) -> {
            Stage st = ((Stage)((Button)event.getSource()).getScene().getWindow());
            st.setFullScreen(!st.isFullScreen());
        });
        
        root.getChildren().addAll(b1, b2, b3, b4);
        root.setStyle("-fx-background-color: transparent;");
//        Scene scene = new Scene(p);
        ShadedScene scene = new ShadedScene(root, 600, 400, Color.rgb(128, 128, 128, 0.5), Color.BLACK, 10);
//        Scene scene = new Scene(root, 200, 200);
//        scene.setShadowRadius(120);
//        scene.setResizable(true);
//        scene.shadowRadiusProperty().set(150);
//        scene.setRoot_(new Button("botao")); // Está cinza por causa deste botão!!!
        scene.setIconified(true);
        
        stage.setTitle("KrugerFX Library Demo - ShadedScene");
        stage.setScene(scene);
        stage.centerOnScreen();
//        scene.setShadowRadius(100);
        
//        scene.setFill(Color.ROYALBLUE);
        
        stage.show();
//        scene.setShadowRadius(15);
        
//        Scene s = new Scene(p);
//        s.effectiveNodeOrientationProperty();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
