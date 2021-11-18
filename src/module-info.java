module slotcartiming {
	requires javafx.base;
	requires javafx.controls;	
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.media;
	requires javafx.swing;
	requires javafx.web;
	requires java.xml;
	
	opens GUI.practice to javafx.graphics,javafx.fxml,javafx.base,javafx.controls,javafx.media,javafx.swing,javafx.web,org.tinylog.api,org.tinylog.impl;
	opens GUI.qualifing to javafx.graphics,javafx.fxml,javafx.base,javafx.controls,javafx.media,javafx.swing,javafx.web,org.tinylog.api,org.tinylog.impl;
	opens GUI.race to javafx.graphics,javafx.fxml,javafx.base,javafx.controls,javafx.media,javafx.swing,javafx.web,org.tinylog.api,org.tinylog.impl;
	opens GUI.utilities to javafx.graphics,javafx.fxml,javafx.base,javafx.controls,javafx.media,javafx.swing,javafx.web,org.tinylog.api,org.tinylog.impl;
	opens GUI to javafx.graphics,javafx.fxml,javafx.base,javafx.controls,javafx.media,javafx.swing,javafx.web,org.tinylog.api,org.tinylog.impl;

}