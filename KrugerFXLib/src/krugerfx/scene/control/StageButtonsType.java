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

/**
 *
 * @author kleberkruger
 */
public enum StageButtonsType {

    /**
     * Defines a {@code StageButtons} with only a close button.
     */
    CLOSE,
    
    /**
     * Defines a {@code StageButtons} with only a fullscreen button.
     */
    FULLSCREEN,
    
    /**
     * Defines a {@code StageButtons} with the buttons in the order: close, minimize and maximize.
     */
    CLOSE_MINIMIZE_MAXIMIZE,
    
    /**
     * Defines a {@code StageButtons} with the buttons in the order: close, minimize and fullscreen.
     */
    CLOSE_MINIMIZE_FULLSCREEN,
    
    /**
     * Defines a {@code StageButtons} with the buttons in the order: close, minimize, maximize and
     * fullscreen.
     */
    CLOSE_MINIMIZE_MAXIMIZE_FULLSCREEN,
    
    /**
     * Defines a {@code StageButtons} with the buttons in the order: minimize, maximize and close.
     */
    MINIMIZE_MAXIMIZE_CLOSE,
    
    /**
     * Defines a {@code StageButtons} with the buttons in the order: minimize, fullscreen and close.
     */
    MINIMIZE_FULLSCREEN_CLOSE,
    
    /**
     * Defines a {@code StageButtons} with the buttons in the order: fullscreen, minimize, maximize
     * and close.
     */
    FULLSCREEN_MINIMIZE_MAXIMIZE_CLOSE;

    public boolean hasClose() {
        return this != StageButtonsType.FULLSCREEN;
    }

    public boolean hasMinimize() {
        return this != StageButtonsType.CLOSE && this != StageButtonsType.FULLSCREEN;
    }

    public boolean hasMaximize() {
        return this == StageButtonsType.CLOSE_MINIMIZE_MAXIMIZE
                || this == StageButtonsType.CLOSE_MINIMIZE_MAXIMIZE_FULLSCREEN
                || this == StageButtonsType.MINIMIZE_MAXIMIZE_CLOSE
                || this == StageButtonsType.FULLSCREEN_MINIMIZE_MAXIMIZE_CLOSE;
    }

    public boolean hasFullscreen() {
        return this != StageButtonsType.CLOSE
                && this != StageButtonsType.CLOSE_MINIMIZE_MAXIMIZE
                && this != MINIMIZE_MAXIMIZE_CLOSE;
    }

    @Override
    public String toString() {
        switch (this) {
            case CLOSE:
                return "[Close]";
            case FULLSCREEN:
                return "[Fullscreen]";
            case CLOSE_MINIMIZE_MAXIMIZE:
                return "[Close, Minimize, Maximize]";
            case CLOSE_MINIMIZE_FULLSCREEN:
                return "[Close, Minimize, Fullscreen]";
            case CLOSE_MINIMIZE_MAXIMIZE_FULLSCREEN:
                return "[Close, Minimize, Maximize, Fullscreen]";
            case MINIMIZE_MAXIMIZE_CLOSE:
                return "[Minimize, Maximize, Close]";
            case MINIMIZE_FULLSCREEN_CLOSE:
                return "[Minimize, Fullscreen, Close]";
            case FULLSCREEN_MINIMIZE_MAXIMIZE_CLOSE:
                return "[Fullscreen, Minimize, Maximize, Close]";
        }
        return "[]";
    }
}
