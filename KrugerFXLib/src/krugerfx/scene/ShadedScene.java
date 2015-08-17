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
package krugerfx.scene;

import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 *
 * @author kleberkruger
 */
public class ShadedScene extends Scene {

    private static final Paint DEFAULT_FILL = Color.WHITE;
    private static final Color DEFAULT_SHADOW_COLOR = Color.BLACK;
    private static final double DEFAULT_SHADOW_RADIUS = 20;

    private final ShadedRootContainer container;

    private double shadowRadiusChanged = 0;

    private Stage stage;

    //<editor-fold defaultstate="collapsed" desc="Public Constructors">
    /**
     * Creates a ShadedScene for a specific root Node.
     *
     * @param root the root node of the scene graph
     *
     * @throws NullPointerException if root is null
     */
    public ShadedScene(Parent root) {
        this(root, -1, -1, DEFAULT_FILL, DEFAULT_SHADOW_COLOR, DEFAULT_SHADOW_RADIUS);
    }

    /**
     * Creates a ShadedScene for a specific root Node with a fill.
     *
     * @param root the parent
     * @param fill the fill
     *
     * @throws NullPointerException if root is null
     */
    public ShadedScene(Parent root, Paint fill) {
        this(root, -1, -1, fill, DEFAULT_SHADOW_COLOR, DEFAULT_SHADOW_RADIUS);
    }

    /**
     * Creates a ShadedScene for a specific root Node with a fill and a shadow color.
     *
     * @param root the parent
     * @param fill the fill
     * @param shadowColor the color of the shadow
     *
     * @throws NullPointerException if root is null
     */
    public ShadedScene(Parent root, Paint fill, Color shadowColor) {
        this(root, -1, -1, fill, shadowColor, DEFAULT_SHADOW_RADIUS);
    }

    /**
     * Creates a ShadedScene for a specific root Node with a fill, a shadow color and a shadow
     * radius.
     *
     * @param root the parent
     * @param fill the fill
     * @param shadowColor the color of the shadow
     * @param shadowRadius the radius of the shadow
     *
     * @throws NullPointerException if root is null
     */
    public ShadedScene(Parent root, Paint fill, Color shadowColor, double shadowRadius) {
        this(root, -1, -1, fill, shadowColor, shadowRadius);
    }

    /**
     * Creates a ShadedScene for a specific root Node with a specific size.
     *
     * @param root the root node of the scene graph
     * @param width the width of the scene
     * @param height the height of the scene
     *
     * @throws NullPointerException if root is null
     */
    public ShadedScene(Parent root, double width, double height) {
        this(root, width, height, DEFAULT_FILL, DEFAULT_SHADOW_COLOR, DEFAULT_SHADOW_RADIUS);
    }

    /**
     * Creates a ShadedScene for a specific root Node with a specific size and fill.
     *
     * @param root the root node of the scene graph
     * @param width the width of the scene
     * @param height the height of the scene
     * @param fill the fill
     *
     * @throws NullPointerException if root is null
     */
    public ShadedScene(Parent root, double width, double height, Paint fill) {
        this(root, width, height, fill, DEFAULT_SHADOW_COLOR, DEFAULT_SHADOW_RADIUS);
    }

    /**
     * Creates a ShadedScene for a specific root Node with a specific size, a fill and a shadow
     * color.
     *
     * @param root the root node of the scene graph
     * @param width the width of the scene
     * @param height the height of the scene
     * @param fill the fill
     * @param shadowColor the color of the shadow
     *
     * @throws NullPointerException if root is null
     */
    public ShadedScene(Parent root, double width, double height, Paint fill, Color shadowColor) {
        this(root, width, height, fill, shadowColor, DEFAULT_SHADOW_RADIUS);
    }

    /**
     * Creates a ShadedScene for a specific root Node with a specific size, a fill and a shadow
     * color.
     *
     * @param root the root node of the scene graph
     * @param width the width of the scene
     * @param height the height of the scene
     * @param fill the fill
     * @param shadowColor the color of the shadow
     * @param shadowRadius the radius of the shadow
     *
     * @throws NullPointerException if root is null
     */
    public ShadedScene(Parent root, double width, double height, Paint fill,
            Color shadowColor, double shadowRadius) {

        this(new ShadedRootContainer(root, fill, shadowColor, shadowRadius),
                shadowColor == null ? width : width + shadowRadius * 2,
                shadowColor == null ? height : height + shadowRadius * 2);
    }

    //</editor-fold>
    /**
     * Creates a ShadedScene of a specific size for a root Node within a container with fill, shadow
     * color and radius defined.
     *
     * @param container the root container
     * @param width the width of the scene
     * @param height the height of the scene
     */
    private ShadedScene(ShadedRootContainer container, double width, double height) {
        super(container, width, height, Color.TRANSPARENT);
        this.container = container;

        initialize();
    }

    private void initialize() {
        addDefaultListeners();
        setResizable(true);
    }

    private void addDefaultListeners() {
        addRootListener();
        addFillListener();
        addWindowListener();
        addShadowRadiusListener();
    }

    private void addRootListener() {
        rootProperty().addListener((ObservableValue<? extends Parent> observable, Parent oldRoot, Parent newRoot) -> {
            if (!(newRoot instanceof ShadedRootContainer)) {
                setRoot(container);
                container.setRoot(newRoot);
            }
        });
    }

    private void addFillListener() {
        fillProperty().addListener((ObservableValue<? extends Paint> observable, Paint oldFill, Paint newFill) -> {
            setFill(Color.TRANSPARENT);
            container.setFill(newFill);
        });
    }

    private void addWindowListener() {
        windowProperty().addListener((ObservableValue<? extends Window> observable, Window oldWindow, Window newWindow) -> {
            if (newWindow instanceof Stage) {
                stage = (Stage) newWindow;
                stageChanged(stage);
            } else {
                stage = null;
            }
        });
    }

    private void addShadowRadiusListener() {
        shadowRadiusProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            Window window = getWindow();
            double difference = newValue.doubleValue() - oldValue.doubleValue();
            if (window != null && window instanceof Stage) {
                ajustStageBounds(difference);
            } else {
                shadowRadiusChanged = difference;
            }
        });
    }

    private void stageChanged(Stage stage) {
        if (!stage.isShowing()) {
            stage.initStyle(StageStyle.TRANSPARENT);
        }
        stage.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldFocused, Boolean newFocused) -> {
            container.setShaded(newFocused);
        });
        // TODO: Melhorar esta parte! Ideia: Tem evento pendente? Se tiver, dispare-o.
        if (shadowRadiusChanged != 0) {
            ajustStageBounds(shadowRadiusChanged);
            shadowRadiusChanged = 0;
        }
    }

    private void ajustStageBounds(double difference) {
//        Stage stage = (Stage) getWindow();
        stage.setWidth(getWidth() + difference * 2);
        stage.setHeight(getHeight() + difference * 2);
        if (!Double.isNaN(stage.getX()) && !Double.isNaN(stage.getY())) {
            stage.setX(stage.getX() - difference);
            stage.setY(stage.getY() - difference);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    /**
     * Sets the root {@code Node} of the scene graph. If a {@code Group} is used as the root, the
     * contents of the scene graph will be clipped by the scene's width and height and changes to
     * the scene's size (if user resizes the stage) will not alter the layout of the scene graph. If
     * a resizable node (layout {@code Region} or {@code Control}) is set as the root, then the
     * root's size will track the scene's size, causing the contents to be relayed out as necessary.
     *
     * Scene doesn't accept null root.
     *
     * @param root the root node of the scene graph
     */
    public final void setRoot_(Parent root) {
        container.setRoot(root);
    }

    /**
     * Gets the root {@code Node} of the scene graph.
     *
     * @return the root node of the scene graph
     */
    public final Parent getRoot_() {
        return container.getRoot();
    }

    /**
     * Gets the root {@code Node} property of the scene graph.
     *
     * @return the root node property of the scene graph
     */
    public final ObjectProperty<Parent> rootProperty_() {
        return container.rootProperty();
    }

    /**
     * Sets the background fill of this {@code Scene}. Both a {@code null} value meaning 'paint no
     * background' and a {@link javafx.scene.paint.Paint} with transparency are supported. The
     * default fill of the Scene is {@link Color#WHITE}, but it is more commonly the case that the
     * initial color shown to users is the background fill of the {@link #rootProperty() root node}
     * of the {@code Scene}, as it typically is stretched to take up all available space in the
     * {@code Scene}. The root node of the {@code Scene} is given the CSS style class 'root', and
     * the default user agent stylesheets that ship with JavaFX (presently Caspian and Modena) apply
     * styling on to this root style class. In the case of Caspian this does not impact the
     * background fill color of the root node, but in the case of Modena the default fill is set to
     * be a light gray color.
     *
     * @param fill the fill
     */
    protected final void setFill_(Paint fill) {
        container.setFill(fill);
    }

    /**
     * Gets the background fill of this {@code Scene}.
     *
     * @return the fill
     */
    public final Paint getFill_() {
        return container.getFill();
    }

    /**
     * Gets the background fill property of this {@code Scene}.
     *
     * @return the fill property
     */
    public final ObjectProperty<Paint> fillProperty_() {
        return container.fillProperty();
    }

    /**
     * Sets the color of the shadow in this {@code Scene}
     *
     * @param color the color of the shadow
     */
    public final void setShadowColor(Color color) {
        container.setShadowColor(color);
    }

    /**
     * Gets the color of the shadow in this {@code Scene}
     *
     * @return the color of the shadow
     */
    public final Color getShadowColor() {
        return container.getShadowColor();
    }

    /**
     * Gets the shadow color property in this {@code Scene}
     *
     * @return the shadow color property
     */
    public final ObjectProperty<Color> shadowColorProperty() {
        return container.shadowColorProperty();
    }

    /**
     * Sets the radius of the shadow in this {@code Scene}
     *
     * @param radius the radius of the shadow
     */
    public final void setShadowRadius(double radius) {
        container.setShadowRadius(radius);
    }

    /**
     * Gets the radius of the shadow in this {@code Scene}
     *
     * @return the radius of the shadow
     */
    public final double getShadowRadius() {
        return container.getShadowRadius();
    }

    /**
     * Gets the shadow radius property in this {@code Scene}
     *
     * @return the shadow radius property
     */
    public final DoubleProperty shadowRadiusProperty() {
        return container.shadowRadiusProperty();
    }

    /**
     * Gets the shaded attribute for this scene.
     *
     * @return {@code true} if this {@code ShadedScene} is shaded
     */
    public final boolean isShaded() {
        return container.isShaded();
    }

    /**
     * Gets the shaded property for this scene.
     *
     * @return the shaded property
     */
    public final ReadOnlyBooleanProperty shadedProperty() {
        return container.shadedProperty();
    }

    /**
     * OK!!!
     *
     * @param value
     */
    public final void setIconified(boolean value) {
        if (stage != null) {
            stage.setIconified(value);
        }
    }

    public final boolean isIconified() {
        if (stage != null) {
            return stage.isIconified();
        }
        return false;
    }

    public final ReadOnlyBooleanProperty iconifiedProperty() {
        if (stage != null) {
            return stage.iconifiedProperty();
        }
        return null;
    }

    private ReadOnlyBooleanWrapper maximized;

    public final void setMaximized(boolean value) {
        maximizedPropertyImpl().set(value);
        if (stage != null) {
            // TODO: Implementar a ação de maximizar
        }
    }

    public final boolean isMaximized() {
        return maximized == null ? false : maximized.get();
    }

    public final ReadOnlyBooleanProperty maximizedProperty() {
        return maximizedPropertyImpl().getReadOnlyProperty();
    }

    private ReadOnlyBooleanWrapper maximizedPropertyImpl() {
        if (maximized == null) {
            maximized = new ReadOnlyBooleanWrapper(ShadedScene.this, "maximized");
        }
        return maximized;
    }

    /**
     * OK!!!
     *
     * @param value
     */
    public final void setFullScreen(boolean value) {
        if (stage != null) {
            stage.setFullScreen(value);
        }
    }

    public final boolean isFullScreen() {
        if (stage != null) {
            return stage.isFullScreen();
        }
        return false;
    }

    public final ReadOnlyBooleanProperty fullScreenProperty() {
        if (stage != null) {
            return stage.fullScreenProperty();
        }
        return null;
    }

    public final void setAlwaysOnTop(boolean value) {
        if (stage != null) {
            stage.setAlwaysOnTop(value);
        }
    }

    public final boolean isAlwaysOnTop() {
        if (stage != null) {
            return stage.isAlwaysOnTop();
        }
        return false;
    }

    public final ReadOnlyBooleanProperty alwaysOnTopProperty() {
        if (stage != null) {
            return stage.alwaysOnTopProperty();
        }
        return null;
    }

    private BooleanProperty resizable;

    public final void setResizable(boolean value) {
        resizableProperty().set(value);
    }

    public final boolean isResizable() {
        return resizable == null ? false : resizable.get();
    }

    public final BooleanProperty resizableProperty() {
        if (resizable == null) {
            resizable = new SimpleBooleanProperty(ShadedScene.this, "resizable", false) {

                @Override
                protected void invalidated() {
                    ResizeListener l = get() ? listener : null;
                    setOnMouseMoved(l);
                    setOnMousePressed(l);
                    setOnMouseDragged(l);
                    setOnMouseEntered(l);
                    setOnMouseExited(l);
                }
            };
        }
        return resizable;
    }

    private final ResizeListener listener = new ResizeListener();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Inner Class (ShadedRootContainer)">
    private static class ShadedRootContainer extends Region {

        private final Rectangle backgroundRectangle = new Rectangle();
        private final Rectangle shadowRectangle = new Rectangle();
        private final DropShadow shadowEffect = new DropShadow();

//        private double initX = -1;
//        private double initY = -1;
        /**
         * Creates a container for a specific root Node.
         *
         * @param root the root node of the scene graph
         * @param fill the fill
         * @param shadowColor the color of the shadow
         * @param shadowRadius the radius of the shadow
         */
        public ShadedRootContainer(Parent root, Paint fill, Color shadowColor, double shadowRadius) {
            checkParameters(root, shadowRadius);

            setRoot(root);
            setFill(fill);
            setShadowColor(shadowColor);
            setShadowRadius(shadowRadius);
            setStyle("-fx-background-color: transparent;");

            createUI();

            addDragListener();
            addResizeListener();
        }

        /**
         * Checks the parameters
         *
         * @param root the root node of the scene graph
         * @param shadowRadius the radius of the shadow
         *
         * @throws RuntimeException if platform does not support transparent window
         * @throws NullPointerException if root is null
         * @throws IllegalArgumentException if shadow radius value is out of the range 0.0 - 127.0
         */
        private void checkParameters(Parent root, double shadowRadius) {

            if (!Platform.isSupported(ConditionalFeature.TRANSPARENT_WINDOW)) {
                throw new RuntimeException("This platform does not support transparent window to create the shadow effect");
            } else if (root == null) {
                throw new NullPointerException("Root cannot be null");
            } else if (shadowRadius < 0 || shadowRadius > 127) {
                throw new IllegalArgumentException("Shadow radius value (" + shadowRadius + ") must be in the range 0.0 - 127.0");
            }
        }

        private void createUI() {
            initShadowRectangle();
            initBackgroundRectangle();
            getChildren().setAll(shadowRectangle, backgroundRectangle, getRoot());
        }

        private void initBackgroundRectangle() {
            backgroundRectangle.getStyleClass().setAll("scene-background");
            backgroundRectangle.setMouseTransparent(true);
        }

        private void initShadowRectangle() {
            shadowRectangle.getStyleClass().setAll("scene-shadow");
            // Do not intercept mouse events on stage's shadow
            shadowRectangle.setMouseTransparent(true);
            shadowRectangle.setEffect(shadowEffect);
            shadowRectangle.layoutBoundsProperty().addListener((observable, oldBounds, newBounds) -> {
                if (shadowEffect.getRadius() != 0) {
                    shadowRectangle.setVisible(true);
                    setShadowClip(newBounds);
                } else {
                    shadowRectangle.setVisible(false);
                }
            });
        }

        /**
         * Computes the needed clip for stage's shadow border
         *
         * @param newBounds
         * @param shadowVisible
         */
        private void setShadowClip(Bounds newBounds) {

            final Rectangle internal = new Rectangle();
            final Rectangle external = new Rectangle();
            double radius = shadowEffect.getRadius();

            external.relocate(newBounds.getMinX() - radius, newBounds.getMinY() - radius);
            internal.setX(radius);
            internal.setY(radius);
            internal.setWidth(newBounds.getWidth());
            internal.setHeight(newBounds.getHeight());
            internal.setArcWidth(shadowRectangle.getArcWidth());    // shadowRectangle CSS cannot be applied on this
            internal.setArcHeight(shadowRectangle.getArcHeight());

            external.setWidth(newBounds.getWidth() + radius * 2);
            external.setHeight(newBounds.getHeight() + radius * 2);
            shadowRectangle.setClip(Shape.subtract(external, internal));
        }

        private void addDragListener() {
            setOnMouseClicked((MouseEvent event) -> {
            });
            setOnMousePressed((MouseEvent event) -> {
            });
            setOnMouseDragged((MouseEvent event) -> {
            });
            setOnMouseReleased((MouseEvent event) -> {
            });
            setOnMouseExited((MouseEvent event) -> {
            });
        }

        private void addResizeListener() {
            setOnMouseClicked((MouseEvent event) -> {
            });
            setOnMousePressed((MouseEvent event) -> {
            });
            setOnMouseDragged((MouseEvent event) -> {
            });
            setOnMouseMoved((MouseEvent event) -> {
            });
        }

        @Override
        protected void layoutChildren() {
            final double ROUNDED_DELTA = 0; // shadow.getArcWidth() / 4;
            Bounds b = getLayoutBounds();
            double w = b.getWidth();
            double h = b.getHeight();
            double radius = shadowEffect.getRadius();

            ObservableList<Node> children = getChildren();

            children.stream().forEach((node) -> {
                if (node == shadowRectangle || node == backgroundRectangle) {
                    Rectangle rectangle = (Rectangle) node;
                    rectangle.setWidth(w - radius * 2);
                    rectangle.setHeight(h - radius * 2);
                    rectangle.setX(radius);
                    rectangle.setY(radius);
                } else {
                    node.resize(w - radius * 2 - ROUNDED_DELTA * 2, h - radius * 2 - ROUNDED_DELTA * 2);
                    node.setLayoutX(radius + ROUNDED_DELTA);
                    node.setLayoutY(radius + ROUNDED_DELTA);
                }
            });
        }

        private ObjectProperty<Parent> root;

        public final void setRoot(Parent root) {
            rootProperty().set(root);
        }

        public final Parent getRoot() {
            return root == null ? null : rootProperty().get();
        }

        public final ObjectProperty<Parent> rootProperty() {
            if (root == null) {
                root = new SimpleObjectProperty<Parent>(ShadedRootContainer.this, "root") {

                    @Override
                    protected void invalidated() {
                        Parent newRoot = get();

                        if (newRoot == null) {
                            if (isBound()) {
                                unbind();
                            }
                            throw new NullPointerException("Scene's root cannot be null");
                        }

                        if (newRoot.getParent() != null) {
                            if (isBound()) {
                                unbind();
                            }
                            throw new IllegalArgumentException(newRoot + " is already inside a "
                                    + "scene-graph and cannot be set as root");
                        }

//                        if (children.size() == 3) {
//                            children.set(2, newRoot);
//                        }
                        newRoot.getStyleClass().add(0, "root");
                    }
                };
            }
            return root;
        }

        public final void setFill(Paint fill) {
            backgroundRectangle.setFill(fill);
        }

        public final Paint getFill() {
            return backgroundRectangle.getFill();
        }

        public final ObjectProperty<Paint> fillProperty() {
            return backgroundRectangle.fillProperty();
        }

        private Color notFocusedColor;
        private ObjectProperty<Color> shadowColor;

        public final void setShadowColor(Color color) {
            shadowColorProperty().set(color);
        }

        public final Color getShadowColor() {
            return shadowColor == null ? DEFAULT_SHADOW_COLOR : shadowColorProperty().get();
        }

        public final ObjectProperty<Color> shadowColorProperty() {
            if (shadowColor == null) {
                shadowColor = new SimpleObjectProperty<Color>(ShadedRootContainer.this, "shadowColor") {

                    private Color getBrighterShadowColor(Color base) {
                        for (int i = 0; i < 10; i++) {
                            base = base.brighter();
                        }
                        return base;
                    }

                    @Override
                    protected void invalidated() {
                        notFocusedColor = getBrighterShadowColor(get());
                    }
                };
            }
            return shadowColor;
        }

        private DoubleProperty shadowRadius;

        public final void setShadowRadius(double radius) {
            shadowRadiusProperty().set(radius);
        }

        public final double getShadowRadius() {
            return shadowRadius == null ? DEFAULT_SHADOW_RADIUS : shadowRadiusProperty().get();
        }

        public final DoubleProperty shadowRadiusProperty() {
            if (shadowRadius == null) {
                shadowRadius = new SimpleDoubleProperty(ShadedRootContainer.this, "shadowRadius") {

                    @Override
                    protected void invalidated() {
                        double value = get();
                        if (value < 0 || value > 127) {
                            throw new IllegalArgumentException("Shadow radius value (" + value + ") "
                                    + "must be in the range 0.0 - 127.0");
                        }
                        // TODO: Fazer redimensionamento?
                    }
                };
                shadowRadius.bindBidirectional(shadowEffect.radiusProperty());
            }
            return shadowRadius;
        }

        private ReadOnlyBooleanWrapper shaded;

        public final void setShaded(boolean shaded) {
            shadedPropertyImpl().set(shaded);
        }

        public final boolean isShaded() {
            return shaded == null ? false : shadedPropertyImpl().get();
        }

        public final ReadOnlyBooleanProperty shadedProperty() {
            return shadedPropertyImpl().getReadOnlyProperty();
        }

        private ReadOnlyBooleanWrapper shadedPropertyImpl() {
            if (shaded == null) {
                shaded = new ReadOnlyBooleanWrapper(ShadedRootContainer.this, "shaded") {

                    @Override
                    protected void invalidated() {
                        shadowEffect.setColor(get() ? shadowColor.get() : notFocusedColor);
                    }
                };
            }
            return shaded;
        }
    }

//    private static class ShadowRadiusChangedEvent {
//    }
    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="ResizeListener private class">
    /**
     * Private class that implements the resize listeners
     *
     * @author kleberkruger
     */
    private static class ResizeListener implements EventHandler<MouseEvent> {

        private double dx;
        private double dy;
        private double deltaX;
        private double deltaY;
        private boolean moveH;
        private boolean moveV;
        private boolean resizeH = false;
        private boolean resizeV = false;
        private final double border = 10; // getShadowRadius();

        @Override
        public void handle(MouseEvent e) {

            Object obj = e.getSource();

            final Scene scene = (obj instanceof Scene) ? (Scene) obj
                    : (obj instanceof Node) ? ((Node) obj).getScene() : null;

            if (scene != null && (obj = scene.getWindow()) instanceof Stage) {

                final Stage stage = (Stage) obj;

                double eX = e.getX();
                double eY = e.getY();
                double width = scene.getWidth();
                double height = scene.getHeight();

                if (MouseEvent.MOUSE_MOVED.equals(e.getEventType())) {
                    if (eX < border && eY < border) {
                        scene.setCursor(Cursor.NW_RESIZE);
                        resizeH = true;
                        resizeV = true;
                        moveH = true;
                        moveV = true;
                    } else if (eX < border && eY > height - border) {
                        scene.setCursor(Cursor.SW_RESIZE);
                        resizeH = true;
                        resizeV = true;
                        moveH = true;
                        moveV = false;
                    } else if (eX > width - border && eY < border) {
                        scene.setCursor(Cursor.NE_RESIZE);
                        resizeH = true;
                        resizeV = true;
                        moveH = false;
                        moveV = true;
                    } else if (eX > width - border && eY > height - border) {
                        scene.setCursor(Cursor.SE_RESIZE);
                        resizeH = true;
                        resizeV = true;
                        moveH = false;
                        moveV = false;
                    } else if (eX < border || eX > width - border) {
                        scene.setCursor(Cursor.H_RESIZE);
                        resizeH = true;
                        resizeV = false;
                        moveH = (eX < border);
                        moveV = false;
                    } else if (eY < border || eY > height - border) {
                        scene.setCursor(Cursor.V_RESIZE);
                        resizeH = false;
                        resizeV = true;
                        moveH = false;
                        moveV = (eY < border);
                    } else {
                        scene.setCursor(Cursor.DEFAULT);
                        resizeH = false;
                        resizeV = false;
                        moveH = false;
                        moveV = false;
                    }
                } else if (MouseEvent.MOUSE_PRESSED.equals(e.getEventType())) {
                    dx = stage.getWidth() - eX;
                    dy = stage.getHeight() - eY;
                } else if (MouseEvent.MOUSE_DRAGGED.equals(e.getEventType())) {
                    if (resizeH) {
                        double stageWidth = stage.getWidth();
                        if (stageWidth <= stage.getMinWidth()) {
                            if (moveH) {
                                deltaX = stage.getX() - e.getScreenX();
                                if (eX < 0) {// if new > old, it's permitted
                                    stage.setWidth(deltaX + stageWidth);
                                    stage.setX(e.getScreenX());
                                }
                            } else {
                                if (eX + dx - stageWidth > 0) {
                                    stage.setWidth(eX + dx);
                                }
                            }
                        } else if (stageWidth > stage.getMinWidth()) {
                            if (moveH) {
                                deltaX = stage.getX() - e.getScreenX();
                                stage.setWidth(deltaX + stageWidth);
                                stage.setX(e.getScreenX());
                            } else {
                                stage.setWidth(eX + dx);
                            }
                        }
                    }
                    if (resizeV) {
                        double stageHeight = stage.getHeight();
                        if (stageHeight <= stage.getMinHeight()) {
                            if (moveV) {
                                deltaY = stage.getY() - e.getScreenY();
                                if (eY < 0) { // if new > old, it's permitted
                                    stage.setHeight(deltaY + stageHeight);
                                    stage.setY(e.getScreenY());
                                }
                            } else {
                                if (eY + dy - stageHeight > 0) {
                                    stage.setHeight(eX + dy);
                                }
                            }
                        } else if (stageHeight > stage.getMinHeight()) {
                            if (moveV) {
                                deltaY = stage.getY() - e.getScreenY();
                                stage.setHeight(deltaY + stageHeight);
                                stage.setY(e.getScreenY());
                            } else {
                                stage.setHeight(eY + dy);
                            }
                        }
                    }
                } else if (MouseEvent.MOUSE_ENTERED.equals(e.getEventType())) {
                    if (!e.isPrimaryButtonDown()) {
                        scene.setCursor(Cursor.DEFAULT);
                    }
                } else if (MouseEvent.MOUSE_EXITED.equals(e.getEventType())) {
                    if (!e.isPrimaryButtonDown()) {
                        scene.setCursor(Cursor.DEFAULT);
                    }
                }
            }
        }
    }
    // </editor-fold>
}
