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
public enum StageButtonsStyle {

    /**
     * Defines a {@code StageButtons} with a simple style based on pill-buttons.
     */
    SIMPLE,
    
    /**
     * Defines a {@code StageButtons} with the Window XP style.
     */
    WINDOWS_XP,
    
    /**
     * Defines a {@code StageButtons} with the Window 7 style.
     */
    WINDOWS_7,
    
    /**
     * Defines a {@code StageButtons} with the Window 8 style.
     */
    WINDOWS_8,
    
    /**
     * Defines a {@code StageButtons} with the Window 10 style.
     */
    WINDOWS_10,
    
    /**
     * Defines a {@code StageButtons} with the Gnome style.
     */
    LINUX_GNONE,
    
    /**
     * Defines a {@code StageButtons} with the KDE style.
     */
    LINUX_KDE,
    
    /**
     * Defines a {@code StageButtons} with the Mac Mavericks style.
     */
    MAC_OS_MAVERICKS,
    
    /**
     * Defines a {@code StageButtons} with the Mac Mavericks (Grey) style.
     */
    MAC_OS_MAVERICKS_GRAY,
    
    /**
     * Defines a {@code StageButtons} with the Mac Yosemite style.
     */
    MAC_OS_YOSEMITE,
    
    /**
     * Defines a {@code StageButtons} with the Mac Yosemite (Grey) style.
     */
    MAC_OS_YOSEMITE_GRAY;

    @Override
    public String toString() {
        switch (this) {
            case SIMPLE:
                return "Simple";
            case WINDOWS_XP:
                return "Windows XP";
            case WINDOWS_7:
                return "Windows XP";
            case WINDOWS_8:
                return "Windows XP";
            case WINDOWS_10:
                return "Windows XP";
            case LINUX_GNONE:
                return "Linux Gnome";
            case LINUX_KDE:
                return "Linux KDE";
            case MAC_OS_MAVERICKS:
                return "MacOS Mavericks";
            case MAC_OS_MAVERICKS_GRAY:
                return "MacOS Mavericks (Gray)";
            case MAC_OS_YOSEMITE:
                return "MacOS Yosemite";
            case MAC_OS_YOSEMITE_GRAY:
                return "MacOS Yosemite (Gray)";
            default:
                return null;
        }
    }
}
