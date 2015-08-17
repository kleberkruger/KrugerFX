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

import com.sun.javafx.css.converters.SizeConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableValue;
import javafx.css.CssMetaData;
import javafx.css.PseudoClass;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;

/**
 *
 * @author kleberkruger
 */
public abstract class StageButton extends Button {

    /**
     * Creates a abstract {@code StageButton}.
     */
    public StageButton() {
        super();
        initialize();
    }

    /**
     * Creates a abstract {@code StageButton}.
     *
     * @param graphic
     */
    public StageButton(Node graphic) {
        super(null, graphic);
        initialize();
    }

    private void initialize() {
        addResizeListener();
        getStyleClass().add(getType().toString().toLowerCase());
        setFocusTraversable(false);
        setOnAction((ActionEvent event) -> {
            actuatedPropertyImpl().set(!actuatedPropertyImpl().get());
        });
        actuatedProperty().addListener((observable, oldValue, newValue) -> {
            action();
        });

        registerPseudoClass();

        // TODO: Utilizar os próprios listeners, propriedades e funções do Stage para fechar, minimizar, maximizar e fullscreen
    }

    private void addResizeListener() {
        prefWidthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            double w = newValue.doubleValue();
            double min = getMinWidth();
            if (min == -1 || w < min) {
                setMinWidth(w);
            }
            if (w > getMaxWidth()) {
                setMaxWidth(w);
            }
        });
        prefHeightProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            double h = newValue.doubleValue();
            double min = getMinHeight();
            if (min == -1 || h < min) {
                setMinHeight(h);
            }
            if (h > getMaxHeight()) {
                setMaxHeight(h);
            }
        });
    }

    private void registerPseudoClass() {
        switch (getType()) {
            case CLOSE:
                pseudoClassStateChanged(CLOSE_PSEUDOCLASS, true);
                break;
            case MINIMIZE:
                pseudoClassStateChanged(MINIMIZE_PSEUDOCLASS, true);
                break;
            case MAXIMIZE:
                pseudoClassStateChanged(MAXIMIZE_PSEUDOCLASS, true);
                break;
            case FULLSCREEN:
                pseudoClassStateChanged(FULLSCREEN_PSEUDOCLASS, true);
                break;
        }
    }

    private ReadOnlyBooleanWrapper actuated;

    public final boolean isActuated() {
        return actuated == null ? false : actuated.get();
//        switch (getType()) {
//            case MINIMIZE:
//                return ((Stage) getScene().getWindow()).isIconified();
//            case MAXIMIZE:
//                return ((Stage) getScene().getWindow()).isMaximized();
//            case FULLSCREEN:
//                return ((Stage) getScene().getWindow()).isFullScreen();
//            default:
//                return actuated == null ? false : actuated.get();
//        }
    }

    public final ReadOnlyBooleanProperty actuatedProperty() {
        return actuatedPropertyImpl().getReadOnlyProperty();
//        switch (getType()) {
//            case MINIMIZE:
//                return ((Stage) getScene().getWindow()).iconifiedProperty();
//            case MAXIMIZE:
//                return ((Stage) getScene().getWindow()).maximizedProperty();
//            case FULLSCREEN:
//                return ((Stage) getScene().getWindow()).fullScreenProperty();
//            default:
//                return actuatedPropertyImpl().getReadOnlyProperty();
//        }
    }

    private ReadOnlyBooleanWrapper actuatedPropertyImpl() {
        if (actuated == null) {
            actuated = new ReadOnlyBooleanWrapper(StageButton.this, "actuated");
        }
        return actuated;
    }

    private static class StyleableProperties {

        private static final double DEFAULT_SIZE = 0.0;

        private static final CssMetaData<StageButton, Number> STAGE_BUTTON_WIDTH
                = new CssMetaData<StageButton, Number>("-fx-stage-button-width",
                        SizeConverter.getInstance(), DEFAULT_SIZE) {

                    @Override
                    public boolean isSettable(StageButton sb) {
                        return !sb.prefWidthProperty().isBound();
                    }

                    @Override
                    public StyleableProperty<Number> getStyleableProperty(StageButton sb) {
                        return (StyleableProperty<Number>) (WritableValue<Number>) sb.prefWidthProperty();
                    }
                };

        private static final CssMetaData<StageButton, Number> STAGE_BUTTON_HEIGHT
                = new CssMetaData<StageButton, Number>("-fx-stage-button-height",
                        SizeConverter.getInstance(), DEFAULT_SIZE) {

                    @Override
                    public boolean isSettable(StageButton sb) {
                        return !sb.prefHeightProperty().isBound();
                    }

                    @Override
                    public StyleableProperty<Number> getStyleableProperty(StageButton sb) {
                        return (StyleableProperty<Number>) (WritableValue<Number>) sb.prefHeightProperty();
                    }
                };

        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables
                    = new ArrayList<>(Control.getClassCssMetaData());
            Collections.addAll(styleables, STAGE_BUTTON_WIDTH, STAGE_BUTTON_HEIGHT);
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    /**
     * @return The CssMetaData associated with this class, which may include the CssMetaData of its
     * super classes.
     *
     * @since JavaFX 8.0
     */
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StageButton.StyleableProperties.STYLEABLES;
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return getClassCssMetaData();
    }

    protected abstract void action();

    public abstract StageButtonType getType();

    private static final PseudoClass CLOSE_PSEUDOCLASS = PseudoClass.getPseudoClass("close");
    private static final PseudoClass MINIMIZE_PSEUDOCLASS = PseudoClass.getPseudoClass("minimize");
    private static final PseudoClass MAXIMIZE_PSEUDOCLASS = PseudoClass.getPseudoClass("maximize");
    private static final PseudoClass FULLSCREEN_PSEUDOCLASS = PseudoClass.getPseudoClass("fullscreen");
}
