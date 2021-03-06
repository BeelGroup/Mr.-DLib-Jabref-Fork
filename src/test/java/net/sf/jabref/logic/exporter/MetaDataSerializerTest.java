package net.sf.jabref.logic.exporter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.jabref.logic.formatter.casechanger.LowerCaseFormatter;
import net.sf.jabref.logic.util.OS;
import net.sf.jabref.model.bibtexkeypattern.GlobalBibtexKeyPattern;
import net.sf.jabref.model.cleanup.FieldFormatterCleanup;
import net.sf.jabref.model.cleanup.FieldFormatterCleanups;
import net.sf.jabref.model.metadata.ContentSelector;
import net.sf.jabref.model.metadata.MetaData;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MetaDataSerializerTest {

    private MetaData metaData;
    private GlobalBibtexKeyPattern pattern;

    @Before
    public void setUp() {
        metaData = new MetaData();
        pattern = GlobalBibtexKeyPattern.fromPattern("[auth][year]");
    }

    @Test
    public void serializeNewMetadataReturnsEmptyMap() throws Exception {
        assertEquals(Collections.emptyMap(), MetaDataSerializer.getSerializedStringMap(metaData, pattern));
    }

    @Test
    public void serializeSingleSaveAction() {
        FieldFormatterCleanups saveActions = new FieldFormatterCleanups(true,
                Collections.singletonList(new FieldFormatterCleanup("title", new LowerCaseFormatter())));
        metaData.setSaveActions(saveActions);

        Map<String, String> expectedSerialization = new TreeMap<>();
        expectedSerialization.put("saveActions",
                "enabled;" + OS.NEWLINE + "title[lower_case]" + OS.NEWLINE + ";");
        assertEquals(expectedSerialization, MetaDataSerializer.getSerializedStringMap(metaData, pattern));
    }

    @Test
    public void serializeSingleContentSelectors() {
        List<String> values = new ArrayList(4);
        values.add("approved");
        values.add("captured");
        values.add("received");
        values.add("status");

        metaData.addContentSelector(new ContentSelector("status", values));

        Map<String, String> expectedSerialization = new TreeMap<>();
        expectedSerialization.put("selector_status",
                "approved;captured;received;status;");
        assertEquals(expectedSerialization, MetaDataSerializer.getSerializedStringMap(metaData, pattern));
    }
}
