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
package krugerfx.scene;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import krugerfx.scene.layout.ShadedRootContainer;

/**
 *
 * @author kleberkruger
 */
public class ShadedScene extends Scene {

    private static final Paint DEFAULT_FILL = Color.WHITE;
    private static final Color DEFAULT_SHADOW_COLOR = Color.BLACK;
    private static final double DEFAULT_SHADOW_RADIUS = 15;

    private final ShadedRootContainer container;

    /**
     * Creates a ShadedScene for a specific root Node.
     *
     * @param root The root node of the scene graph
     *
     * @throws NullPointerException if root is null
     */
    public ShadedScene(Parent root) {
        this(root, -1, -1, DEFAULT_FILL, DEFAULT_SHADOW_COLOR, DEFAULT_SHADOW_RADIUS);
    }

    /**
     * Creates a ShadedScene for a specific root Node with a fill.
     *
     * @param root The parent
     * @param fill The fill
     *
     * @throws NullPointerException if root is null
     */
    public ShadedScene(Parent root, Paint fill) {
        this(root, -1, -1, fill, DEFAULT_SHADOW_COLOR, DEFAULT_SHADOW_RADIUS);
    }

    /**
     * Creates a ShadedScene for a specific root Node with a fill and a shadow color.
     *
     * @param root The parent
     * @param fill The fill
     * @param shadowColor The shadow color of the scene
     *
     * @throws NullPointerException if root is null
     */
    public ShadedScene(Parent root, Paint fill, Color shadowColor) {
        this(root, -1, -1, fill, shadowColor, DEFAULT_SHADOW_RADIUS);
    }

    /**
     * Creates a ShadedScene for a specific root Node with a fill, a shadow color and a shadow
     * radius.
     *
     * @param root The parent
     * @param fill The fill
     * @param shadowColor The shadow color of the scene
     * @param shadowRadius The shadow radius of the scene
     *
     * @throws NullPointerException if root is null
     */
    public ShadedScene(Parent root, Paint fill, Color shadowColor, double shadowRadius) {
        this(root, -1, -1, fill, shadowColor, shadowRadius);
    }

    /**
     * Creates a ShadedScene for a specific root Node with a specific size.
     *
     * @param root The root node of the scene graph
     * @param width The width of the scene
     * @param height The height of the scene
     *
     * @throws NullPointerException if root is null
     */
    public ShadedScene(Parent root, double width, double height) {
        this(root, width, height, DEFAULT_FILL, DEFAULT_SHADOW_COLOR, DEFAULT_SHADOW_RADIUS);
    }

    /**
     * Creates a ShadedScene for a specific root Node with a specific size and fill.
     *
     * @param root The root node of the scene graph
     * @param width The width of the scene
     * @param height The height of the scene
     * @param fill The fill
     *
     * @throws NullPointerException if root is null
     */
    public ShadedScene(Parent root, double width, double height, Paint fill) {
        this(root, width, height, fill, DEFAULT_SHADOW_COLOR, DEFAULT_SHADOW_RADIUS);
    }

    /**
     * Creates a ShadedScene for a specific root Node with a specific size, a fill and a shadow
     * color.
     *
     * @param root The root node of the scene graph
     * @param width The width of the scene
     * @param height The height of the scene
     * @param fill The fill
     * @param shadowColor The shadow color of the scene
     *
     * @throws NullPointerException if root is null
     */
    public ShadedScene(Parent root, double width, double height, Paint fill, Color shadowColor) {
        this(root, width, height, fill, shadowColor, DEFAULT_SHADOW_RADIUS);
    }

    /**
     * Creates a ShadedScene for a specific root Node with a specific size, a fill and a shadow
     * color.
     *
     * @param root The root node of the scene graph
     * @param width The width of the scene
     * @param height The height of the scene
     * @param fill The fill
     * @param shadowColor The shadow color of the scene
     * @param shadowRadius The shadow radius of the scene
     *
     * @throws NullPointerException if root is null
     */
    public ShadedScene(Parent root, double width, double height, Paint fill,
            Color shadowColor, double shadowRadius) {

        this(new ShadedRootContainer(root, fill, shadowColor, shadowRadius), 
                width + shadowRadius * 2, height + shadowRadius * 2);
    }

    /**
     * Creates a ShadedScene with a specific size for a specific root Node within a container with
     * fill, shadow color and radius defined.
     *
     * @param container
     * @param width
     * @param height
     */
    private ShadedScene(ShadedRootContainer container, double width, double height) {
        super(container, width, height, Color.TRANSPARENT);
        this.container = container;

        initialize();
    }

    private void initialize() {
        addDefaultListeners();
    }

    private void addDefaultListeners() {
        // Add listener to get the stage
        windowProperty().addListener((ObservableValue<? extends Window> observable, Window oldWindow, Window newWindow) -> {
            if (newWindow instanceof Stage) {
                setStage((Stage) newWindow);
            }
        });
        // Add listeners to capture root changes
        rootProperty().addListener((ObservableValue<? extends Parent> observable, Parent oldRoot, Parent newRoot) -> {
            // TODO: e se o usuário alterar o root desta Scene?
            container.setRoot(newRoot);
        });
    }

    private void setStage(Stage stage) {
        stage.initStyle(StageStyle.TRANSPARENT);
        // Add focus listener on stage
        stage.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
//            container.setShaded(newValue);
        });
    }

    public final void setRoot_(Parent root) {
        container.setRoot(root);
    }

    public final Parent getRoot_() {
        return container.getRoot();
    }

    public final ObjectProperty<Parent> rootProperty_() {
        return container.rootProperty();
    }

    public final void setFill_(Paint fill) {
        container.setFill(fill);
    }

    public final Paint getFill_() {
        return container.getFill();
    }

    public final ObjectProperty<Paint> fillProperty_() {
        return container.fillProperty();
    }

    public final void setShadowColor(Color shadowColor) {
        container.setShadowColor(shadowColor);
    }

    public final Color getShadowColor() {
        return container.getShadowColor();
    }

    public final ObjectProperty<Color> shadowColorProperty() {
        return container.shadowColorProperty();
    }

    public final void setShadowRadius(double shadowRadius) {
        container.setShadowRadius(shadowRadius);
    }

    public final double getShadowRadius() {
        return container.getShadowRadius();
    }

    public final DoubleProperty shadowRadiusProperty() {
        return container.shadowRadiusProperty();
    }
}
