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

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author kleberkruger
 */
public class CloseButton extends StageButton {

    /**
     * Creates a {@code CloseButton}.
     */
    public CloseButton() {
        super();
    }

    /**
     * Creates a {@code CloseButton}.
     *
     * @param graphic
     */
    public CloseButton(Node graphic) {
        super(graphic);
    }

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
