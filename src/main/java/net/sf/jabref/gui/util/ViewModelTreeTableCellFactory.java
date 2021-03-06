package net.sf.jabref.gui.util;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * Constructs a {@link TreeTableCell} based on the view model of the row and a bunch of specified converter methods.
 *
 * @param <S> view model
 * @param <T> cell value
 */
public class ViewModelTreeTableCellFactory<S, T> implements Callback<TreeTableColumn<S, T>, TreeTableCell<S, T>> {

    private Callback<S, String> toText;
    private Callback<S, Node> toGraphic;
    private Callback<S, EventHandler<? super MouseEvent>> toOnMouseClickedEvent;
    private Callback<S, String> toTooltip;

    public ViewModelTreeTableCellFactory<S, T> withText(Callback<S, String> toText) {
        this.toText = toText;
        return this;
    }

    public ViewModelTreeTableCellFactory<S, T> withGraphic(Callback<S, Node> toGraphic) {
        this.toGraphic = toGraphic;
        return this;
    }

    public ViewModelTreeTableCellFactory<S, T> withIcon(Callback<S, String> toIcon) {
        this.toGraphic = viewModel -> {
            Text graphic = new Text(toIcon.call(viewModel));
            graphic.getStyleClass().add("icon");
            return graphic;
        };
        return this;
    }

    public ViewModelTreeTableCellFactory<S, T> withTooltip(Callback<S, String> toTooltip) {
        this.toTooltip = toTooltip;
        return this;
    }

    public ViewModelTreeTableCellFactory<S, T> withOnMouseClickedEvent(
            Callback<S, EventHandler<? super MouseEvent>> toOnMouseClickedEvent) {
        this.toOnMouseClickedEvent = toOnMouseClickedEvent;
        return this;
    }

    @Override
    public TreeTableCell<S, T> call(TreeTableColumn<S, T> param) {

        return new TreeTableCell<S, T>() {

            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getTreeTableRow() == null || getTreeTableRow().getItem() == null) {
                    setText(null);
                    setGraphic(null);
                    setOnMouseClicked(null);
                } else {
                    S viewModel = getTreeTableRow().getItem();
                    if (toText != null) {
                        setText(toText.call(viewModel));
                    }
                    if (toGraphic != null) {
                        setGraphic(toGraphic.call(viewModel));
                    }
                    if (toTooltip != null) {
                        setTooltip(new Tooltip(toTooltip.call(viewModel)));
                    }
                    if (toOnMouseClickedEvent != null) {
                        setOnMouseClicked(toOnMouseClickedEvent.call(viewModel));
                    }
                }
            }
        };
    }
}
