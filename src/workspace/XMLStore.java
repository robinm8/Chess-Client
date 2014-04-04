package workspace;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 * Links the game to save and load local xml data from "Chess Data.xml" in the
 * user working directory.
 * 
 * @author Mark Robinson
 * 
 */
public class XMLStore {

	File xmlFile = new File(System.getProperty("user.dir") + "\\Chess Data.xml");

	/**
	 * When you ask to "create", the method creates a new XML document if it
	 * does not already exist. Then it saves the document in the user working
	 * directory. When you ask to "save", the method saves Link.doc to the XML
	 * document in the application's working directory.
	 * 
	 * @param w
	 *            ("save", "create")
	 */
	public void Do(String w) {

		if (w == "load") {
			try {
				if (xmlFile.exists()) {
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory
							.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(xmlFile);
					doc.getDocumentElement().normalize();
					Link.doc = doc;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (w == "create") {
			try {
				if (xmlFile.exists()) {
					this.Do("load");
				} else {

					DocumentBuilderFactory docFactory = DocumentBuilderFactory
							.newInstance();
					DocumentBuilder docBuilder = docFactory
							.newDocumentBuilder();

					Document doc = docBuilder.newDocument();

					Element rootElement = doc.createElement("Servers");
					doc.appendChild(rootElement);

					Link.doc = doc;
					OutputFormat format = new OutputFormat(Link.doc);
					format.setIndenting(true);

					XMLSerializer serializer;
					serializer = new XMLSerializer(new FileOutputStream(
							new File(System.getProperty("user.dir")
									+ "\\Chess Data.xml")), format);
					serializer.serialize(Link.doc);

					docFactory = null;
					docBuilder = null;
					rootElement = null;
					doc = null;
					format = null;
					serializer = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (w == "save" && Link.doc != null) {

			OutputFormat format = new OutputFormat(Link.doc);
			format.setIndenting(true);

			XMLSerializer serializer;

			try {
				serializer = new XMLSerializer(new FileOutputStream(new File(
						System.getProperty("user.dir") + "\\Chess Data.xml")),
						format);
				serializer.serialize(Link.doc);
				serializer = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			format = null;

		}
		try {
			finalize();
		} catch (Throwable e) {
		}
	}
}