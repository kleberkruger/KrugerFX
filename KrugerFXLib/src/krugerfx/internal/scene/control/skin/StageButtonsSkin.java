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
package krugerfx.internal.scene.control.skin;

import com.sun.javafx.scene.control.skin.BehaviorSkinBase;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import krugerfx.internal.scene.control.behavior.StageButtonsBehavior;
import krugerfx.scene.control.StageButton;
import krugerfx.scene.control.StageButtons;

/**
 *
 * @author kleberkruger
 */
public class StageButtonsSkin extends /* SkinBase<StageButtons> */
        BehaviorSkinBase<StageButtons, StageButtonsBehavior> {

    private final HBox container = new HBox();

    public StageButtonsSkin(StageButtons control) {
        super(control, new StageButtonsBehavior(control));
        createUI();
        addStyleClasses();
    }

    private void createUI() {
        container.getStyleClass().setAll("container");
        container.getChildren().setAll(getSkinnable().getButtons());
        getChildren().setAll(container);
    }

    private void addStyleClasses() {
        ObservableList<StageButton> buttons = getSkinnable().getButtons();
        if (buttons.size() == 1) {
            buttons.get(0).getStyleClass().add("single");
        } else {
            buttons.get(0).getStyleClass().add("left");
            for (int i = 1; i < buttons.size() - 1; i++) {
                buttons.get(i).getStyleClass().add("center");
            }
            buttons.get(buttons.size() - 1).getStyleClass().add("right");
        }
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset,
            double bottomInset, double leftInset) {

        return computePrefWidth(height, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computeMinHeight(double width, double topInset, double rightInset,
            double bottomInset, double leftInset) {

        return computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computeMaxWidth(double height, double topInset, double rightInset,
            double bottomInset, double leftInset) {

        return computePrefWidth(height, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computeMaxHeight(double width, double topInset, double rightInset,
            double bottomInset, double leftInset) {

        return computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset,
            double bottomInset, double leftInset) {

        double prefWidth = 0;
        prefWidth = container.getChildren().stream().map((node) -> snapSize(node.prefWidth(-1))).
                reduce(prefWidth, (accumulator, _item) -> accumulator + _item);
        prefWidth += (container.getChildren().size() - 1) * container.getSpacing();
        return leftInset + prefWidth + rightInset;
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset,
            double bottomInset, double leftInset) {

        double prefHeight = 0;
        for (Node node : container.getChildren()) {
            prefHeight = Math.max(prefHeight, snapSize(node.prefHeight(-1)));
        }
        return topInset + prefHeight + bottomInset;
    }
}
