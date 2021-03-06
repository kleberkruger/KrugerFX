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
package krugerfx.scene.control;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.collections.ObservableList;
import javafx.geometry.BoundingBox;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import krugerfx.scene.ShadedScene;

/**
 *
 * @author kleberkruger
 */
public class MaximizeButton extends StageButton {

    private BoundingBox savedBounds;
    private boolean maximized = false;

    /**
     * Creates a {@code MaximizeButton}.
     */
    public MaximizeButton() {
        super();
    }

    /**
     * Creates a {@code MaximizeButton}.
     *
     * @param graphic
     */
    public MaximizeButton(Node graphic) {
        super(graphic);
    }

    private void restoreSavedBounds(Stage stage /*, boolean fullscreen */) {

        stage.setX(savedBounds.getMinX());
        stage.setY(savedBounds.getMinY());
        stage.setWidth(savedBounds.getWidth());
        stage.setHeight(savedBounds.getHeight());

        savedBounds = null;
    }

    @Override
    protected void action() {
        // TODO Implementar corretamente o que deverá ser feito caso o Stage esteja em fullscreen.   
//        Scene scene = getScene();
//        if (scene instanceof ShadedScene) {
//            ((ShadedScene) scene).setMaximized(!((ShadedScene) scene).isMaximized()); // Não funciona!!! Bug em Mac OS X
//        } else {
//            Stage stage = (Stage) scene.getWindow();
//            stage.setMaximized(!stage.isMaximized());
//        }
        
        // Implementação antiga
        Stage stage = (Stage) getScene().getWindow();

        if (maximized) {
            restoreSavedBounds(stage);
            savedBounds = null;
            maximized = false;
        } else {
            Scene scene = getScene();
            double d = scene instanceof ShadedScene ? ((ShadedScene) scene).getShadowRadius() : 0;

            ObservableList<Screen> screensForRectangle = Screen.getScreensForRectangle(
                    stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
            Screen screen = screensForRectangle.get(0);
            Rectangle2D visualBounds = screen.getVisualBounds();
            savedBounds = new BoundingBox(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
//            undecorator.setShadow(false);
            stage.setX(visualBounds.getMinX() - d);
            stage.setY(visualBounds.getMinY() - d);
            stage.setWidth(visualBounds.getWidth() + d * 2);
            stage.setHeight(visualBounds.getHeight() + d * 2);
            maximized = true;
        }
    }

    @Override
    public StageButtonType getType() {
        return StageButtonType.MAXIMIZE;
    }

    public final boolean isMaximized() {
        return isActuated();
    }

    public final ReadOnlyBooleanProperty minimizedProperty() {
        return actuatedProperty();
    }
}
