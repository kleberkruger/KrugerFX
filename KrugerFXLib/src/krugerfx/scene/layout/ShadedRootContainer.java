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
package krugerfx.scene.layout;

import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 *
 * @author kleberkruger
 */
public class ShadedRootContainer extends Region {

    private final Rectangle backgroundRectangle;
    private final Rectangle shadowRectangle;
    private final DropShadow shadowEffect;

    /**
     * Creates a container for a specific root Node.
     *
     * @param root
     * @param fill
     * @param shadowColor
     * @param shadowRadius
     */
    public ShadedRootContainer(Parent root, Paint fill, Color shadowColor, double shadowRadius) {
        if (!Platform.isSupported(ConditionalFeature.TRANSPARENT_WINDOW)) {
            throw new RuntimeException("This platform does not support transparent window");
        } else if (shadowRadius < 0 || shadowRadius > 127) {
            throw new IllegalArgumentException("Shadow radius value (" + shadowRadius + ") must be in the range 0.0-127.0");
        }
        
        if (shadowColor == null) {
            shadowRadius = 0.0;
        }
//        setStyle("-fx-background-color: transparent;");

        shadowEffect = new DropShadow(shadowRadius, shadowColor);
        shadowRectangle = createShadowRectangle(shadowEffect);
        backgroundRectangle = createBackgroundRectangle(fill);

        getChildren().addAll(shadowRectangle, backgroundRectangle, root);
    }

    private Rectangle createBackgroundRectangle(Paint fill) {
        Rectangle rectangle = new Rectangle();
//        rectangle.getStyleClass().setAll("scene-background");
        rectangle.setFill(fill);
        rectangle.setMouseTransparent(true);
        return rectangle;
    }

    private Rectangle createShadowRectangle(DropShadow shadow) {
        Rectangle rectangle = new Rectangle();
//        shadowRectangle.getStyleClass().setAll("scene-shadow");
        // Do not intercept mouse events on stage's shadow
        rectangle.setMouseTransparent(true);
        rectangle.setEffect(shadow);
        rectangle.layoutBoundsProperty().addListener((ObservableValue<? extends Bounds> observable, Bounds oldBounds, Bounds newBounds) -> {
            if (shadow.getRadius() != 0) {
                rectangle.setVisible(true);
                setShadowClip(newBounds);
            } else {
                rectangle.setVisible(false);
            }
        });
        return rectangle;
    }

    /**
     * Compute the needed clip for stage's shadow border
     *
     * @param newBounds
     * @param shadowVisible
     */
    private void setShadowClip(Bounds newBounds) {

        final Rectangle internal = new Rectangle();
        final Rectangle external = new Rectangle();
        double shadowRadius = shadowEffect.getRadius();

        external.relocate(newBounds.getMinX() - shadowRadius, newBounds.getMinY() - shadowRadius);
        internal.setX(shadowRadius);
        internal.setY(shadowRadius);
        internal.setWidth(newBounds.getWidth());
        internal.setHeight(newBounds.getHeight());
        internal.setArcWidth(shadowRectangle.getArcWidth());    // shadowRectangle CSS cannot be applied on this
        internal.setArcHeight(shadowRectangle.getArcHeight());

        external.setWidth(newBounds.getWidth() + shadowRadius * 2);
        external.setHeight(newBounds.getHeight() + shadowRadius * 2);
        shadowRectangle.setClip(Shape.subtract(external, internal));
    }

    @Override
    protected void layoutChildren() {
        final double ROUNDED_DELTA = 0; // shadow.getArcWidth() / 4;
        Bounds b = getLayoutBounds();
        double w = b.getWidth();
        double h = b.getHeight();
        double shadowRadius = shadowEffect.getRadius();

        ObservableList<Node> children = getChildren();

        children.stream().forEach((node) -> {
            if (node == shadowRectangle || node == backgroundRectangle) {
                Rectangle rectangle = (Rectangle) node;
                rectangle.setWidth(w - shadowRadius * 2);
                rectangle.setHeight(h - shadowRadius * 2);
                rectangle.setX(shadowRadius);
                rectangle.setY(shadowRadius);
            } else {
                node.resize(w - shadowRadius * 2 - ROUNDED_DELTA * 2, h - shadowRadius * 2 - ROUNDED_DELTA * 2);
                node.setLayoutX(shadowRadius + ROUNDED_DELTA);
                node.setLayoutY(shadowRadius + ROUNDED_DELTA);
            }
        });
    }

    /**
     * Defines the root of this {@code ShadedStage}.
     */
    private ObjectProperty<Parent> root;

    public final void setRoot(Parent value) {
        rootProperty().set(value);
    }

    public final Parent getRoot() {
        return root.get();
    }

    public final ObjectProperty<Parent> rootProperty() {
        if (root == null) {
            root = new SimpleObjectProperty<>(ShadedRootContainer.this, "root", null);
        }
        return root;
    }

    public final void setFill(Paint value) {
        backgroundRectangle.setFill(value);
    }

    public final Paint getFill() {
        return backgroundRectangle.getFill();
    }

    public final ObjectProperty<Paint> fillProperty() {
        return backgroundRectangle.fillProperty();
    }

    public final void setShadowColor(Color value) {
        shadowEffect.setColor(value);
    }

    public final Color getShadowColor() {
        return shadowEffect.getColor();
    }

    public final ObjectProperty<Color> shadowColorProperty() {
        return shadowEffect.colorProperty();
    }

    public final void setShadowRadius(double value) {
        shadowEffect.setRadius(value);
    }

    public final double getShadowRadius() {
        return shadowEffect.getRadius();
    }

    public final DoubleProperty shadowRadiusProperty() {
        return shadowEffect.radiusProperty();
    }
}
