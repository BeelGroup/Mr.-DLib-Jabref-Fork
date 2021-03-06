package net.sf.jabref.gui;

import java.nio.file.Path;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

import net.sf.jabref.gui.util.FileDialogConfiguration;
import net.sf.jabref.logic.l10n.Localization;

/**
 * This interface provides methods to create dialogs and show them to the user.
 */
public interface DialogService {

    /**
     * This will create and display a new information dialog.
     * It will include a blue information icon on the left and
     * a single OK Button. To create an information dialog with custom
     * buttons see also {@link #showCustomButtonDialogAndWait(Alert.AlertType, String, String, ButtonType...)}
     */
    void showInformationDialogAndWait(String title, String content);

    /**
     * This will create and display a new information dialog.
     * It will include a yellow warning icon on the left and
     * a single OK Button. To create a warning dialog with custom
     * buttons see also {@link #showCustomButtonDialogAndWait(Alert.AlertType, String, String, ButtonType...)}
     */
    void showWarningDialogAndWait(String title, String content);

    /**
     * This will create and display a new error dialog.
     * It will include a red error icon on the left and
     * a single OK Button. To create a error dialog with custom
     * buttons see also {@link #showCustomButtonDialogAndWait(Alert.AlertType, String, String, ButtonType...)}
     */
    void showErrorDialogAndWait(String title, String content);

    /**
     * Create and display error dialog displaying the given exception.
     * @param message the error message
     * @param exception the exception causing the error
     */
    void showErrorDialogAndWait(String message, Throwable exception);

    /**
     * Create and display error dialog displaying the given exception.
     * @param exception the exception causing the error
     */
    default void showErrorDialogAndWait(Exception exception) {
        showErrorDialogAndWait(Localization.lang("Unhandled exception occurred."), exception);
    }

    /**
     * Create and display error dialog displaying the given message.
     * @param message the error message
     */
    void showErrorDialogAndWait(String message);

    /**
     * This will create and display a new confirmation dialog.
     * It will include a blue question icon on the left and
     * a OK and Cancel Button. To create a confirmation dialog with custom
     * buttons see also {@link #showCustomButtonDialogAndWait(Alert.AlertType, String, String, ButtonType...)}
     *
     * @return Optional with the pressed Button as ButtonType
     */
    Optional<ButtonType> showConfirmationDialogAndWait(String title, String content);

    /**
     * This will create and display a new dialog of the specified
     * {@link Alert.AlertType} but with user defined buttons as optional
     * {@link ButtonType}s.
     *
     * @return Optional with the pressed Button as ButtonType
     */
    Optional<ButtonType> showCustomButtonDialogAndWait(Alert.AlertType type, String title, String content,
            ButtonType... buttonTypes);

    /**
     * This will create and display a new dialog showing a custom {@link DialogPane}
     * and using custom {@link ButtonType}s.
     *
     * @return Optional with the pressed Button as ButtonType
     */
    Optional<ButtonType> showCustomDialogAndWait(String title, DialogPane contentPane, ButtonType... buttonTypes);

    /**
     * Notify the user in an non-blocking way (i.e., update status message instead of showing a dialog).
     * @param message the message to show.
     */
    void notify(String message);

    /**
     * Shows a new file save dialog. The method doesn't return until the
     * displayed file save dialog is dismissed. The return value specifies the
     * file chosen by the user or an empty {@link Optional} if no selection has been made.
     *
     * @return the selected file or an empty {@link Optional} if no file has been selected
     */
    Optional<Path> showSaveDialog(FileDialogConfiguration fileDialogConfiguration);

    /**
     * Shows a new file open dialog. The method doesn't return until the
     * displayed open dialog is dismissed. The return value specifies
     * the file chosen by the user or an empty {@link Optional} if no selection has been
     * made.
     *
     * @return the selected file or an empty {@link Optional} if no file has been selected
     */
    Optional<Path> showOpenDialog(FileDialogConfiguration fileDialogConfiguration);
}
