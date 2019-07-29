package org.architecturemining.fam.IO;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JMenu;

import org.architecturemining.fam.model.Trace;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**Handles the exporting of the created traces to a new .xml document.
 * 
 * @author Nick
 */
public class TraceExportXES {
	
	public static void main() {
		
		}
	
	/** Writes an XML document based on the given traceList.
	 * 
	 * @param traceList		array with all the created traces by the user
	 */
	@SuppressWarnings("unused")
	public static void writeXES(ArrayList<Trace> traceList) {
		try {
		Document doc = new Document();
		//create root
		Element theRoot = new Element("log");
		theRoot.setAttribute("xes.version", "2.0");
		theRoot.setAttribute("xes.features","");
		doc.setRootElement(theRoot);
		
			/*set extensions
			Element extension = new Element("extension");
			extension.setAttribute("name","Context");
			extension.setAttribute("prefix","context");
			extension.setAttribute("uri","http://www.xes-standard.org/concept.xesext");
			theRoot.addContent(extension); */
		
			//set global scope
			//global trace
			Element global1 = new Element("global");
			global1.setAttribute("scope","trace");
			
			Element g1string1 = new Element("string");
			g1string1.setAttribute("key","name");
			g1string1.setAttribute("value","");
			
			Element g1string2 = new Element("string");
			g1string2.setAttribute("key","description");
			g1string2.setAttribute("value","");
			
			global1.addContent(g1string1);
			global1.addContent(g1string2);
			
			//global event
			Element global2 = new Element("global");
			global2.setAttribute("scope","event");
			
			Element g2string1 = new Element("string");
			g2string1.setAttribute("key","order");
			g2string1.setAttribute("value","");
			
			Element g2string2 = new Element("string");
			g2string2.setAttribute("key","id");
			g2string2.setAttribute("value","");
			
			Element g2string3 = new Element("string");
			g2string3.setAttribute("key","name");
			g2string3.setAttribute("value","");
			
			global2.addContent(g2string1);
			global2.addContent(g2string2);
			global2.addContent(g2string3);
			
			//add global scope to root
			theRoot.addContent(global1);
			theRoot.addContent(global2);
		
			//loop through all traces
			for(int i = 0; traceList.size() > i+1 ; i++) {
				Element trace = new Element("trace");
				trace.setAttribute("id", traceList.get(i).getIdTrace());
			
				Element name = new Element("string");
				name.setAttribute("key","name");
				name.setAttribute("value",traceList.get(i).getNameTrace());
			
				Element description = new Element("string");
				description.setAttribute("key","description");
				description.setAttribute("value",traceList.get(i).getDescriptionTrace());
			
				trace.addContent(name);
				trace.addContent(description);
			
				//loop through all events
				for(int j = 0; traceList.get(i).getNameFeatureList().size() > j + 1 ; j++) {
				
					Element event = new Element("event");
					
					Element order = new Element("string");
					order.setAttribute("key","order");
					order.setAttribute("value",""+j);
					event.addContent(order);
					
					/*Element id = new Element("string");
					id.setAttribute("key","id");
					id.setAttribute("value",traceList.get(i).getNameFeatureList().get(j).getId());
					event.addContent(id); */
					
					Element callerid = new Element("string");
					callerid.setAttribute("key","caller_id");
					callerid.setAttribute("value",traceList.get(i).getNameFeatureList().get(j).getId());
					event.addContent(callerid);
					
					Element calleeid = new Element("string");
					calleeid.setAttribute("key","callee_id");
					calleeid.setAttribute("value",traceList.get(i).getNameFeatureList().get(j + 1).getId());
					event.addContent(calleeid);
					
					Element callername = new Element("string");
					callername.setAttribute("key","caller_name");
					callername.setAttribute("value",traceList.get(i).getNameFeatureList().get(j).getName());
					event.addContent(callername);
					
					Element calleename = new Element("string");
					calleename.setAttribute("key","callee_name");
					calleename.setAttribute("value",traceList.get(i).getNameFeatureList().get(j + 1).getName());
					event.addContent(calleename);
					
					trace.addContent(event);
				}
			
				theRoot.addContent(trace);
			}
	
		XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());	
		
		JMenu menu = new JMenu("File");
		
		//Create a file chooser
		JFileChooser fc = new JFileChooser();
		
		int returnVal = fc.showSaveDialog(menu);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
        	File file = fc.getSelectedFile();
        	//This is the code to save the file
        	xmlOutput.output(doc, new FileOutputStream(file + ".XES"));
            System.out.println("Saving: " + file.getName() + ".");
        } else {
        	System.out.println("Save command cancelled by user.");
        }
		
		System.out.println("Traces exported in XES");
		}
		
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}