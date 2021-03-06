package net.sf.jabref.logic.layout.format;

import net.sf.jabref.logic.layout.LayoutFormatter;
import net.sf.jabref.model.entry.AuthorList;

/**
 * <ul>
 * <li>Names are given as first name, von and last name.</li>
 * <li>First names will be abbreviated.</li>
 * <li>Individual authors separated by comma.</li>
 * <li>There is no command in front the and of a list of three or more authors.</li>
 * </ul>
 * 
 * @author Christopher Oezbek <oezi@oezi.de>
 */
public class AuthorFirstAbbrLastCommas implements LayoutFormatter {

    @Override
    public String format(String fieldText) {
        return AuthorList.fixAuthorFirstNameFirstCommas(fieldText, true, false);
    }
}
