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

import java.util.Locale;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.BoundingBox;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Skin;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import krugerfx.internal.scene.control.skin.StageButtonsSkin;

/**
 *
 * @author kleberkruger
 */
public class StageButtons extends Button {

    private static final String osName = System.getProperty("os.name").toLowerCase(Locale.ROOT);

    private final CloseButton closeButton = new CloseButton();
    private final MinimizeButton minimizeButton = new MinimizeButton();
    private final MaximizeButton maximizeButton = new MaximizeButton();
    private final FullscreenButton fullscreenButton = new FullscreenButton();

    private final ObservableList<StageButton> buttons = FXCollections.<StageButton>observableArrayList();

    /**
     * Creates a default {@code StageButton}.
     */
    public StageButtons() {
        initialize();
    }

    /**
     * Creates a {@code StageButton}.
     *
     * @param type
     */
    public StageButtons(StageButtonsType type) {
        typeProperty().setValue(type);
        initialize();
    }

    private void initialize() {
        getStyleClass().setAll("stage-buttons");
        setButtonsList(typeProperty().get());
//        typeProperty().addListener((ObservableValue<? extends StageButtonsType> observable, StageButtonsType oldValue, StageButtonsType newValue) -> {
//            if (newValue != oldValue) {
//                setButtonsList(newValue);
//            }
//        });
    }

    private void setButtonsList(StageButtonsType type) {
        switch (type) {
            case CLOSE:
                buttons.add(closeButton);
                break;
            case FULLSCREEN:
                buttons.add(fullscreenButton);
                break;
            case CLOSE_MINIMIZE_MAXIMIZE:
                buttons.addAll(closeButton, minimizeButton, maximizeButton);
                break;
            case CLOSE_MINIMIZE_FULLSCREEN:
                buttons.addAll(closeButton, minimizeButton, fullscreenButton);
                break;
            case CLOSE_MINIMIZE_MAXIMIZE_FULLSCREEN:
                buttons.addAll(closeButton, minimizeButton, maximizeButton, fullscreenButton);
                break;
            case MINIMIZE_MAXIMIZE_CLOSE:
                buttons.addAll(minimizeButton, maximizeButton, closeButton);
                break;
            case MINIMIZE_FULLSCREEN_CLOSE:
                buttons.addAll(minimizeButton, fullscreenButton, closeButton);
                break;
            case FULLSCREEN_MINIMIZE_MAXIMIZE_CLOSE:
                buttons.addAll(fullscreenButton, minimizeButton, maximizeButton, closeButton);
                break;
        }
    }

    /**
     * Create a new instance of the default skin for this control. This is called to create a skin
     * for the control if no skin is provided via CSS {@code -fx-skin} or set explicitly in a
     * sub-class with {@code  setSkin(...)}.
     *
     * @return new instance of default skin for this control. If null then the control will have no
     * skin unless one is provided by css
     *
     * @since JavaFX 8.0
     */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new StageButtonsSkin(this);
    }

    /**
     * An implementation may specify its own user-agent styles for this Region, and its children, by
     * overriding this method. These styles are used in addition to whatever user-agent stylesheets
     * are in use. This provides a mechanism for third parties to introduce styles for custom
     * controls.
     * <p>
     * The URL is a hierarchical URI of the form [scheme:][//authority][path]. If the URL does not
     * have a [scheme:] component, the URL is considered to be the [path] component only. Any
     * leading '/' character of the [path] is ignored and the [path] is treated as a path relative
     * to the root of the application's classpath.
     *
     * @return A string URL
     *
     * @since JavaFX 8u40
     */
    @Override
    public String getUserAgentStylesheet() {
        return getClass().getResource("/krugerfx/internal/scene/control/" + getClass().
                getSimpleName() + ".css").toExternalForm();
    }

    private ObjectProperty<StageButtonsType> type;

    /**
     * The type of stage buttons.
     *
     * @return the type property
     */
    public final ObjectProperty<StageButtonsType> typeProperty() {
        if (type == null) {
            type = new SimpleObjectProperty<>(osName.contains("windows")
                    ? StageButtonsType.MINIMIZE_MAXIMIZE_CLOSE
                    : StageButtonsType.CLOSE_MINIMIZE_MAXIMIZE);
        }
        return type;
    }

    /**
     * @return the type
     */
    public final StageButtonsType getType() {
        return typeProperty().getValue();
    }

    /**
     * @param type the type to set
     */
    public final void setType(StageButtonsType type) {
        this.typeProperty().setValue(type);
    }

    private ObjectProperty<StageButtonsStyle> style;

    /**
     * The style of stage buttons.
     *
     * @return the style property
     */
    public final ObjectProperty<StageButtonsStyle> style_____Property() {
        if (style == null) {
            super.styleProperty();
        }
        return style;
    }

    /**
     * @return the style
     */
    public final StageButtonsStyle get_____Style() {
        return style_____Property().getValue();
    }

    /**
     * @param style the style to set
     */
    public final void set_____Style(StageButtonsStyle style) {
        this.style_____Property().setValue(style);
    }

    /**
     * @return the close button
     */
    public final Button getCloseButton() {
        return typeProperty().getValue().hasClose() ? closeButton : null;
    }

    /**
     * @return the minimize button
     */
    public final Button getMinimizeButton() {
        return typeProperty().getValue().hasMinimize() ? minimizeButton : null;
    }

    /**
     * @return the maximize button
     */
    public final Button getMaximizeButton() {
        return typeProperty().getValue().hasMaximize() ? maximizeButton : null;
    }

    /**
     * @return the fullscreen button
     */
    public final Button getFullscreenButton() {
        return typeProperty().getValue().hasFullscreen() ? fullscreenButton : null;
    }

    /**
     * The buttons to show within this StageButtons.
     *
     * If this ObservableList is modified at runtime, the StageButton will update as expected. ???
     * This list is unmodifiable. ???
     *
     * @return the buttons list
     *
     * @see StageButton
     */
    public final ObservableList<StageButton> getButtons() {
        return FXCollections.unmodifiableObservableList(buttons);
    }
    
    //<editor-fold defaultstate="collapsed" desc="Private classes">
    private class CloseButton extends StageButton {

        @Override
        protected void action() {
            final Stage stage = (Stage) getScene().getWindow();
            Platform.runLater(() -> {
                stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            });
        }

        @Override
        public StageButtonType getType() {
            return StageButtonType.CLOSE;
        }

        public final boolean isClosed() {
            return isActuated();
        }

        public final ReadOnlyBooleanProperty closedProperty() {
            return actuatedProperty();
        }
    }

    private class MinimizeButton extends StageButton {

        private void minimize() {
            Stage stage = (Stage) getScene().getWindow();
            stage.setIconified(true);
        }

        @Override
        protected void action() {
            // Ensure on correct thread else hangs X under Unbuntu
            if (!Platform.isFxApplicationThread()) {
                Platform.runLater(this::minimize);
            } else {
                minimize();
            }
        }

        @Override
        public StageButtonType getType() {
            return StageButtonType.MINIMIZE;
        }

        public final boolean isMinimized() {
            return isActuated();
        }

        public final ReadOnlyBooleanProperty minimizedProperty() {
            return actuatedProperty();
        }
    }

    private class MaximizeButton extends StageButton {

        private BoundingBox savedBounds;
        private boolean maximized = false;

        private void restoreSavedBounds(Stage stage /*, boolean fullscreen */) {

            stage.setX(savedBounds.getMinX());
            stage.setY(savedBounds.getMinY());
            stage.setWidth(savedBounds.getWidth());
            stage.setHeight(savedBounds.getHeight());

            savedBounds = null;
        }

        @Override
        protected void action() {
            Stage stage = (Stage) getScene().getWindow();

            if (maximized) {
                restoreSavedBounds(stage);
                savedBounds = null;
                maximized = false;
            } else {
                ObservableList<Screen> screensForRectangle = Screen.getScreensForRectangle(
                        stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
                Screen screen = screensForRectangle.get(0);
                Rectangle2D visualBounds = screen.getVisualBounds();
                savedBounds = new BoundingBox(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
//                undecorator.setShadow(false);
                stage.setX(visualBounds.getMinX());
                stage.setY(visualBounds.getMinY());
                stage.setWidth(visualBounds.getWidth());
                stage.setHeight(visualBounds.getHeight());
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

    private class FullscreenButton extends StageButton {

        private void setFullscreen(boolean value) {
            Stage stage = (Stage) getScene().getWindow();
            stage.setFullScreen(value);
        }

        @Override
        protected void action() {
            final Stage stage = (Stage) getScene().getWindow();
            // Invoke runLater even if it's on EDT: Crash apps on Mac
            Platform.runLater(() -> {
                setFullscreen(!stage.isFullScreen());
            });
        }

        @Override
        public StageButtonType getType() {
            return StageButtonType.FULLSCREEN;
        }

        public final boolean isFullscreen() {
            return isActuated();
        }

        public final ReadOnlyBooleanProperty fullscreenProperty() {
            return actuatedProperty();
        }
    }
    //</editor-fold>
}
