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
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
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
        Pane p = new Pane();
//        p.setStyle("-fx-background-color: orange;");
//        Scene scene = new Scene(p);
        ShadedScene scene = new ShadedScene(p, 600, 400, Color.AQUA, Color.BLACK, 127);
        scene.setShadowColor(Color.BLUEVIOLET);
        scene.setShadowRadius(150);

        stage.setTitle("KrugerFX Library Demo - ShadedScene");
        stage.setScene(scene);
        stage.centerOnScreen();
//        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
