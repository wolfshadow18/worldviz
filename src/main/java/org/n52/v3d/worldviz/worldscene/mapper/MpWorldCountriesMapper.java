package org.n52.v3d.worldviz.worldscene.mapper;

import java.util.List;

import org.n52.v3d.triturus.core.T3dException;
import org.n52.v3d.triturus.t3dutil.T3dColor;
import org.n52.v3d.triturus.vgis.VgAttrFeature;
import org.n52.v3d.worldviz.dataaccess.load.dataset.XmlDataset;
import org.n52.v3d.worldviz.extensions.mappers.MpValue2ColoredAttrFeature;
import org.n52.v3d.worldviz.extensions.mappers.MpValue2ExtrudedAttrFeature;
import org.n52.v3d.worldviz.helper.StringParser;
import org.n52.v3d.worldviz.worldscene.VsAbstractWorldScene;
import org.n52.v3d.worldviz.worldscene.VsWorldCountriesOnASphereScene;
import org.n52.v3d.worldviz.worldscene.helper.FindExtrudeAndColorMissingCountriesHelper;

import de.hsbo.fbg.worldviz.WvizConfigDocument;
import de.hsbo.fbg.worldviz.WvizConfigDocument.WvizConfig.GlobeVisualization;
import de.hsbo.fbg.worldviz.WvizConfigDocument.WvizConfig.GlobeVisualization.WorldCountries.PolygonVisualizer;
import de.hsbo.fbg.worldviz.WvizConfigDocument.WvizConfig.GlobeVisualization.WorldCountries.PolygonVisualizer.ColorMapper;
import de.hsbo.fbg.worldviz.WvizConfigDocument.WvizConfig.GlobeVisualization.WorldCountries.PolygonVisualizer.ColorMapper.ColorPalette;
import de.hsbo.fbg.worldviz.WvizConfigDocument.WvizConfig.GlobeVisualization.WorldCountries.PolygonVisualizer.ColorMapper.ColorPalette.ColorEntry;
import de.hsbo.fbg.worldviz.WvizConfigDocument.WvizConfig.GlobeVisualization.WorldCountries.PolygonVisualizer.CountryBorders;
import de.hsbo.fbg.worldviz.WvizConfigDocument.WvizConfig.GlobeVisualization.WorldCountries.PolygonVisualizer.CountryBorders.BorderColor;
import de.hsbo.fbg.worldviz.WvizConfigDocument.WvizConfig.GlobeVisualization.WorldCountries.PolygonVisualizer.ExtrusionMapper;
import de.hsbo.fbg.worldviz.WvizConfigDocument.WvizConfig.GlobeVisualization.WorldCountries.PolygonVisualizer.ExtrusionMapper.ExtrusionPalette;
import de.hsbo.fbg.worldviz.WvizConfigDocument.WvizConfig.GlobeVisualization.WorldCountries.PolygonVisualizer.ExtrusionMapper.ExtrusionPalette.ExtrusionEntry;
import de.hsbo.fbg.worldviz.WvizConfigDocument.WvizConfig.GlobeVisualization.WorldCountries.PolygonVisualizer.SteinerPoints;

public class MpWorldCountriesMapper extends MpAbstractXmlDatasetToGlobeVisualizer {

	private MpValue2ColoredAttrFeature colorMapper;
	private MpValue2ExtrudedAttrFeature extrusionMapper;

	public MpWorldCountriesMapper(WvizConfigDocument wVizConfigFile, String attributeNameForMapping) {
		super(wVizConfigFile, attributeNameForMapping);
	}

	@Override
	public VsAbstractWorldScene transformToSingleScene(XmlDataset xmlDataset) {

		VsWorldCountriesOnASphereScene worldCountriesScene = new VsWorldCountriesOnASphereScene();

		this.parameterizeScene(worldCountriesScene, this.wVizConfigFile);

		this.colorMapper = initializeColorMapper(this.wVizConfigFile);

		this.extrusionMapper = initializeExtrusionMapper(this.wVizConfigFile);

		List<VgAttrFeature> geoObjects = xmlDataset.getFeatures();

		List<VgAttrFeature> sceneObjects = transformGeoObjectsToSceneObjects(worldCountriesScene, geoObjects);

		addSceneObjectsToWorldScene(worldCountriesScene, sceneObjects);

		return worldCountriesScene;

	}

	@Override
	protected void parameterizeScene(VsAbstractWorldScene scene, WvizConfigDocument wVizConfigFile) {
		super.parameterizeScene(scene, wVizConfigFile);

		VsWorldCountriesOnASphereScene worldCountriesScene = (VsWorldCountriesOnASphereScene) scene;

		GlobeVisualization globeVisualization = wVizConfigFile.getWvizConfig().getGlobeVisualization();

		PolygonVisualizer polygonVisualizer = globeVisualization.getWorldCountries().getPolygonVisualizer();

		// COUNTRY BORDER
		CountryBorders countryBorders = polygonVisualizer.getCountryBorders();
		BorderColor borderColor = countryBorders.getBorderColor();
		String borderRgbColorAsString = borderColor.getStringValue();
		T3dColor borderColorRGB = StringParser.parseStringAsRgbColor(borderRgbColorAsString);

		worldCountriesScene.setDefaultCountryBordersColor(borderColorRGB);

		worldCountriesScene.setDrawBorders(Boolean.parseBoolean(countryBorders.getDrawborders()));

		// TODO offset for borders???

		// EXTRUSION
		worldCountriesScene
				.setExtrudeCountries(Boolean.parseBoolean(polygonVisualizer.getExtrusion().getExtrudeCountries()));

		// ADDITIONAL INNER POINTS (STEINER POINTS)
		SteinerPoints steinerPoints = polygonVisualizer.getSteinerPoints();
		worldCountriesScene.setGenerateAdditionalInnerPolygonPoints(
				Boolean.parseBoolean(steinerPoints.getGenerateSteinerPoints()));

		worldCountriesScene.setxRasterWidth(steinerPoints.getXRasterWidth());
		worldCountriesScene.setyRasterWidth(steinerPoints.getYRasterWidth());

		// RADIUS FOR GLOBE-TRANSFORMATION
		worldCountriesScene.setRadius(globeVisualization.getGlobe().getGlobeRadius());

	}

	private MpValue2ColoredAttrFeature initializeColorMapper(WvizConfigDocument wVizConfigFile) {
		MpValue2ColoredAttrFeature colorMapper = new MpValue2ColoredAttrFeature();

		ColorMapper colorMapperConfig = wVizConfigFile.getWvizConfig().getGlobeVisualization().getWorldCountries()
				.getPolygonVisualizer().getColorMapper();

		// interpolation mode
		boolean linearInterpolation = true;
		String interpolationType = colorMapperConfig.getInterpolationMode().getType();
		if (!interpolationType.equalsIgnoreCase("linear"))
			linearInterpolation = false;

		// color palette
		ColorPalette colorPalette = colorMapperConfig.getColorPalette();
		ColorEntry[] colorEntryArray = colorPalette.getColorEntryArray();

		int numberOfColorEntries = colorEntryArray.length;

		double[] attributeValues = new double[numberOfColorEntries];
		T3dColor[] colors = new T3dColor[numberOfColorEntries];

		for (int i = 0; i < colorEntryArray.length; i++) {
			ColorEntry colorEntry = colorEntryArray[i];

			attributeValues[i] = colorEntry.getInputValue();

			String outputColorString = colorEntry.getOutputColor().getStringValue();

			colors[i] = StringParser.parseStringAsRgbColor(outputColorString);
		}

		colorMapper.setPalette(attributeValues, colors, linearInterpolation);

		String neutralColorConfig = this.wVizConfigFile.getWvizConfig().getGlobeVisualization().getWorldCountries()
				.getPolygonVisualizer().getColorMapper().getNeutralColor().getStringValue();
		colorMapper.setNeutralColor(StringParser.parseStringAsRgbColor(neutralColorConfig));

		return colorMapper;
	}

	private MpValue2ExtrudedAttrFeature initializeExtrusionMapper(WvizConfigDocument wVizConfigFile) {
		MpValue2ExtrudedAttrFeature extrusionMapper = new MpValue2ExtrudedAttrFeature();

		ExtrusionMapper extrusionMapperConfig = wVizConfigFile.getWvizConfig().getGlobeVisualization()
				.getWorldCountries().getPolygonVisualizer().getExtrusionMapper();

		// interpolation mode
		boolean linearInterpolation = true;
		String interpolationType = extrusionMapperConfig.getInterpolationMode().getType();
		if (!interpolationType.equalsIgnoreCase("linear"))
			linearInterpolation = false;

		// color palette
		ExtrusionPalette extrusionPalette = extrusionMapperConfig.getExtrusionPalette();
		ExtrusionEntry[] extrusionEntryArray = extrusionPalette.getExtrusionEntryArray();

		int numberOfExtrusionEntries = extrusionEntryArray.length;

		double[] attributeValues = new double[numberOfExtrusionEntries];
		double[] outputExtrusionValues = new double[numberOfExtrusionEntries];

		for (int i = 0; i < extrusionEntryArray.length; i++) {
			ExtrusionEntry extrusionEntry = extrusionEntryArray[i];

			attributeValues[i] = extrusionEntry.getInputValue();

			outputExtrusionValues[i] = extrusionEntry.getOutputExtrusion();
		}

		extrusionMapper.setPalette(attributeValues, outputExtrusionValues, linearInterpolation);

		extrusionMapper.setNeutralExtrusionHeight(this.wVizConfigFile.getWvizConfig().getGlobeVisualization()
				.getWorldCountries().getPolygonVisualizer().getExtrusion().getDefaultExtrusionHeight());

		return extrusionMapper;
	}

	private List<VgAttrFeature> transformGeoObjectsToSceneObjects(VsWorldCountriesOnASphereScene worldCountriesScene,
			List<VgAttrFeature> geoObjects) {

		List<VgAttrFeature> coloredSceneObjects = this.colorMapper.transform(attributeNameForMapping, geoObjects);

		List<VgAttrFeature> extrudedColoredSceneObjects = this.extrusionMapper.transform(attributeNameForMapping,
				coloredSceneObjects);

		List<VgAttrFeature> allSceneObjects = processMissingCountries(extrudedColoredSceneObjects);

		return allSceneObjects;

	}

	private List<VgAttrFeature> processMissingCountries(List<VgAttrFeature> extrudedColoredSceneObjects) {
		FindExtrudeAndColorMissingCountriesHelper missingCountriesHelper = new FindExtrudeAndColorMissingCountriesHelper(
				this.countryBorderLOD);

		// missingCountriesHelper.findExtrudeAndColorMissingCountries(alreadyExtrudedAndColoredCountries,
		// neutralColor, neutralExtrusionHeight)
		List<VgAttrFeature> allSceneObjects = missingCountriesHelper.findExtrudeAndColorMissingCountries(
				extrudedColoredSceneObjects, this.colorMapper.getNeutralColor(),
				this.extrusionMapper.getNeutralExtrusionHeight());

		return allSceneObjects;
	}

	private void addSceneObjectsToWorldScene(VsWorldCountriesOnASphereScene worldCountriesScene,
			List<VgAttrFeature> sceneObjects) {
		for (VgAttrFeature sceneObject : sceneObjects) {
			worldCountriesScene.addWorldCountry(sceneObject);
		}

	}

	@Override
	public List<VsAbstractWorldScene> transformToMultipleScenes(XmlDataset xmlDataset, String outputFilePath,
			String fileName) {
		throw new T3dException(
				"This mapper ist not able to create multiple scenes as output. Please use method {@link #transformToSingleScene(XmlDataset)}");
	}

}
