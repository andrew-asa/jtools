package com.asa.jtools.alfred;

import com.asa.base.log.LoggerFactory;
import com.asa.base.utils.StringUtils;
import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * @author andrew_asa
 * @date 2021/11/25.
 */
public class AlfredHistoryTest extends TestCase {

    @Test
    @Ignore
    public void testGetOptHistory() {

        AlfredHistory alfredHistory = new AlfredHistory();
        List<String> el = alfredHistory.getOptHistory(StringUtils.EMPTY);
        LoggerFactory.getLogger().debug("{}",el);
    }
}