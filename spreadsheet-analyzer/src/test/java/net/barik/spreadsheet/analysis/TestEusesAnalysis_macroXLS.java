package net.barik.spreadsheet.analysis;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestEusesAnalysis_macroXLS {
	
	private static SpreadsheetAnalyzer analyzer;

	@BeforeClass
	public static void loadSmallWorksheet() throws Exception{
		InputStream is = TestEusesAnalysis_macroXLS.class.getResourceAsStream("/wmacro.xls");
		assertNotNull(is);
		analyzer = SpreadsheetAnalyzer.doAnalysis(is);
		assertNotNull(analyzer);
		
	}

    @Test
    public void testContainsMacro() throws Exception{
    	boolean b = analyzer.getContainsMacro();
    	assertTrue(b);
    }
	
}
