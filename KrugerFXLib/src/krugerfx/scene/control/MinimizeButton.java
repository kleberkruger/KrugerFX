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
public class MinimizeButton extends StageButton {

    /**
     * Creates a {@code MinimizeButton}.
     */
    public MinimizeButton() {
        super();
    }

    /**
     * Creates a {@code MinimizeButton}.
     *
     * @param graphic
     */
    public MinimizeButton(Node graphic) {
        super(graphic);
    }

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
