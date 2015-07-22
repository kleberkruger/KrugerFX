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

/**
 *
 * @author kleberkruger
 */
public class FullscreenButton extends StageButton {

    /**
     * Creates a {@code FullscreenButton}.
     */
    public FullscreenButton() {
        super();
    }

    /**
     * Creates a {@code FullscreenButton}.
     *
     * @param graphic
     */
    public FullscreenButton(Node graphic) {
        super(graphic);
    }

    private void setFullscreen(boolean value) {
        Stage stage = (Stage) getScene().getWindow();
        stage.setFullScreen(value);
    }

    @Override
    protected void action() {
        final Stage stage = (Stage) getScene().getWindow();
        // Invoke runLater even if it's on EDT: Crash apps on Mac?
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
