package org.n52.v3d.worldviz.demoapplications.ene.datageneration;

import java.io.FileNotFoundException;

import org.n52.v3d.worldviz.dataaccess.importtools.CsvImporter;
import org.n52.v3d.worldviz.helper.RelativePaths;

public class FillDataset_HardCoal {

	public static void main(String[] args) {
		String xmlFileLocation = RelativePaths.HARD_COAL_RESOURCES_XML;
		String csvFileLocation = RelativePaths.HARD_COAL_RESOURCES_CSV;
		Character csvSeperator = ';';
		String[] csvHeaders = new String[] { "Kuerzel", "Foerderung", "Reserven",
				"Ressourcen", "Verbl. Potenzial" };
		String nullValue = "-";
		String zeroValue = "NODATA";
		int numberOfInitLinesToSkip = 0;

		CsvImporter fillCO2Emissions = new CsvImporter(xmlFileLocation,
				csvFileLocation, csvSeperator, csvHeaders, nullValue,
				zeroValue, numberOfInitLinesToSkip);
		try {
			fillCO2Emissions.fillDatasetEntries();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
