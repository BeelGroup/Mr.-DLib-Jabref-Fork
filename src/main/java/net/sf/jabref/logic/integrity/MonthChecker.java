package net.sf.jabref.logic.integrity;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import net.sf.jabref.logic.l10n.Localization;
import net.sf.jabref.model.database.BibDatabaseContext;

public class MonthChecker implements ValueChecker {

    private static final Predicate<String> ONLY_AN_INTEGER = Pattern.compile("[1-9]|10|11|12")
            .asPredicate();
    private static final Predicate<String> MONTH_NORMALIZED = Pattern
            .compile("#jan#|#feb#|#mar#|#apr#|#may#|#jun#|#jul#|#aug#|#sep#|#oct#|#nov#|#dec#")
            .asPredicate();

    private final BibDatabaseContext bibDatabaseContextMonth;


    public MonthChecker(BibDatabaseContext bibDatabaseContext) {
        this.bibDatabaseContextMonth = Objects.requireNonNull(bibDatabaseContext);
    }

    /**
     * BibLaTeX package documentation (Section 2.3.9):
     * The month field is an integer field.
     * The bibliography style converts the month to a language-dependent string as required.
     * For backwards compatibility, you may also use the following three-letter abbreviations in the month field:
     * jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec.
     * Note that these abbreviations are BibTeX strings which must be given without any braces or quotes.
     */
    @Override
    public Optional<String> checkValue(String value) {
        //BibLaTeX
        if (bibDatabaseContextMonth.isBiblatexMode()
                && !(ONLY_AN_INTEGER.test(value.trim()) || MONTH_NORMALIZED.test(value.trim()))) {
            return Optional.of(Localization.lang("should be an integer or normalized"));
        }

        //BibTeX
        if (!bibDatabaseContextMonth.isBiblatexMode() && !MONTH_NORMALIZED.test(value.trim())) {
            return Optional.of(Localization.lang("should be normalized"));
        }

        return Optional.empty();
    }
}
