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
package krugerfx.internal.scene.control.behavior;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import java.util.ArrayList;
import krugerfx.scene.control.StageButtons;

/**
 *
 * @author kleberkruger
 */
public class StageButtonsBehavior extends BehaviorBase<StageButtons> {

    public StageButtonsBehavior(StageButtons control /*, List<KeyBinding> keyBindings */) {
        super(control, new ArrayList<>());
    }
}
