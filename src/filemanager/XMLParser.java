package src.filemanager;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import src.auxiliary.Convertible;
import src.baseclasses.Coordinates;
import src.baseclasses.Location;
import src.baseclasses.Movie;
import src.baseclasses.Person;
import src.baseenums.Color;
import src.baseenums.MovieGenre;
import src.baseenums.MpaaRating;
import src.moviecollection.MovieCol;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.SimpleDateFormat;

import static src.filemanager.writer.Writer.logErrors;

/**
 * XMLParser class extends the Parser class and is used to parse XML data and store it in a MovieCol collection.
 *
 * @author dorlneylon
 * @version 3.999(9)
 * @since the day this all happened
 */
public class XMLParser extends Parser<MovieCol> {

	private Document d;

	/**
	 * Constructor for XMLParser class.
	 *
	 * @param filename the name of the file to be parsed
	 * @param movies the MovieCol collection to store the parsed data
	 */
	public XMLParser(String filename, MovieCol movies) {
		super(filename, movies);
	}

	/**
	 * Parses the data from the XML file and stores it in the movies collection.
	 *
	 * @return the movies collection
	 * @throws Exception if there is an issue with the format of the data in the file
	 */
	@Override
	public MovieCol parseData()  {
		readData();
		NodeList first = d.getElementsByTagName("Movie");
		for (int i = 0; i < first.getLength(); i++) {
			NodeList second = first.item(i).getChildNodes();

			for (int j = 0; j < second.getLength(); j++) {
				try {
					prsMovVals(d, i, j);
				} catch (Exception e) {
					logErrors(e);
				}
			}
			writeData();
		}
		return movies;
	}

	public MovieCol parseData(String filename)  {
		readData(filename);
		NodeList first = d.getElementsByTagName("Movie");
		for (int i = 0; i < first.getLength(); i++) {
			NodeList second = first.item(i).getChildNodes();

			for (int j = 0; j < second.getLength(); j++) {
				try {
					prsMovVals(d, i, j);
				} catch (Exception e) {
					logErrors(e);
				}
			}
			writeData();
		}
		return movies;
	}

	/**
	 * Reads the data from the file and creates xmlstring which is easier to parse.
	 *
	 * @throws Exception if there is an issue with reading the data from the file
	 */
	@Override
	protected void readData() {
			File f = new File(filename);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			logErrors(e);
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while (true) {
			try {
				if ((line = br.readLine()) == null) break;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			sb.append(line.trim());
		}

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			d = dbf.newDocumentBuilder().parse(new InputSource(new StringReader(sb.toString())));
		} catch (SAXException | IOException | ParserConfigurationException e) {
			logErrors(e);
		}
	}


	protected void readData(String filename)  {
		File f = new File(filename);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			logErrors(e);
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while (true) {
			try {
				if ((line = br.readLine()) == null) break;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			sb.append(line.trim());
		}

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			d = dbf.newDocumentBuilder().parse(new InputSource(new StringReader(sb.toString())));
		} catch (SAXException | IOException | ParserConfigurationException e) {
			logErrors(e);
		}
	}

	/**
	 * Updates the values of the movie.
	 *
	 * @param d the document to be parsed
	 * @param i the index of the movie in the document
	 * @param j the index of the element in the movie
	 * @throws Exception if there is an issue with the format of the data in the document
	 */
	private static void prsMovVals(Document d, int i, int j) {
		NodeList first = d.getElementsByTagName("Movie");
		NodeList second = first.item(i).getChildNodes();
		Node item = second.item(j);
		switch (item.getNodeName()) {
			case "id" -> parseId(item);
			case "name" -> parseName(item);
			case "coordinates" -> parseCoordinates(item);
			case "creationDate" -> parseCreationDate(item);
			case "oscarsCount" -> parseOscarsCount(item);
			case "mpaaRating" -> parseMpaaRating(item);
			case "genre" -> parseGenre(item);
			default -> {
				if (!item.getNodeName().equals("director")) {
					throw new IllegalArgumentException("Incorrect XML format");
				} else {
					for (int k = 0; k < item.getChildNodes().getLength(); k++) {
						prsDirVals(d, i, j, k);
					}
				}
			}
		}
	}

	/**
	 * Inserts the values into the collection.
	 * @throws Exception if there is an issue with the format or some field is null.
	 */
	@Override
	protected void writeData() {
		if (!movies.containsKey(id) && id != null && name != null && coordinates != null && creationDate != null && oscarsCount > 0 && genre != null && mpaaRating != null && dname != null && ddate != null && dheight > 0 && dcolor != null && dlocation != null) {
			Movie nm = new Movie(id, name, coordinates, creationDate, oscarsCount, genre, mpaaRating, new Person(dname, ddate, dheight, dcolor, dlocation));
			movies.insert(id, nm);
		} else
			throw new IllegalArgumentException();
	}

	/**
	 * Parses the contents of id tag.
	 * @param item the item to be parsed
	 * @ if there is an issue with the format of the data in the document
	 */
	private static void parseId(Node item) {
		try {
			id = Long.parseLong(item.getTextContent());
		} catch (Exception e) {
			throw new IllegalArgumentException("Incorrect XML format");
		}
	}

	/**
	 * Parses the contents of name tag.
	 * @param item the item to be parsed
	 * @ if there is an issue with the format of the data in the document
	 */
	private static void parseName(Node item)  {
		try {
			name = item.getTextContent();
		} catch (Exception e) {
			throw new IllegalArgumentException("Incorrect XML format");
		}
	}

	/**
	 * Parses the contents of coordinates tag.
	 * @param item the item to be parsed
	 * @ if there is an issue with the format of the data in the document
	 * @see Coordinates
	 */
	private static void parseCoordinates(Node item)  {
		try {
			NodeList coordinatesNodes = item.getChildNodes();
			String x = coordinatesNodes.item(0).getTextContent().trim();
			String y = coordinatesNodes.item(1).getTextContent().trim();
			coordinates = new Coordinates(Float.parseFloat(x), Integer.parseInt(y));
		} catch (Exception e) {
			throw new IllegalArgumentException("Incorrect XML format");
		}
	}

	/**
	 * Parses the contents of creationDate tag.
	 * @param item the item to be parsed
	 * @ if there is an issue with the format of the data in the document
	 */
	private static void parseCreationDate(Node item)  {
		try {
			creationDate = java.time.ZonedDateTime.parse(item.getTextContent());
		} catch (Exception e) {
			throw new IllegalArgumentException("Incorrect XML format");
		}
	}

	/**
	 * Parses the contents of oscarsCount tag.
	 * @param item the item to be parsed
	 * @ if there is an issue with the format of the data in the document
	 */
	private static void parseOscarsCount(Node item)  {
		try {
			oscarsCount = Long.parseLong(item.getTextContent());
		} catch (Exception e) {
			throw new IllegalArgumentException("Incorrect XML format");
		}
	}

	/**
	 * Parses the contents of mpaaRating tag.
	 * @param item the item to be parsed
	 * @ if there is an issue with the format of the data in the document
	 * @see MpaaRating
	 */
	private static void parseMpaaRating(Node item)  {
		try {
			mpaaRating = Convertible.convert(item.getTextContent(), MpaaRating.class);
		} catch (Exception e) {
			throw new IllegalArgumentException("Incorrect XML format");
		}
	}

	/**
	 * Parses the contents of genre tag.
	 * @param item the item to be parsed
	 * @ if there is an issue with the format of the data in the document
	 * @see MovieGenre
	 */
	private static void parseGenre(Node item)  {
		try {
			genre = Convertible.convert(item.getTextContent(), MovieGenre.class);
		} catch (Exception e) {
			throw new IllegalArgumentException("Incorrect XML format");
		}
	}

	/**
	 * Parses the contents of director tag.
	 * @param d the document to be parsed
	 * @param i the index of the movie
	 * @param j the index of the director
	 * @param k the index of the director's field
	 * @ if there is an issue with the format of the data in the document
	 * @see Person
	 * @see Color
	 * @see Location
	 */
	private static void prsDirVals(Document d, int i, int j, int k)  {
		NodeList first = d.getElementsByTagName("Movie");
		NodeList second = first.item(i).getChildNodes();
		NodeList third = second.item(j).getChildNodes();
		Node item = third.item(k);

		switch (item.getNodeName()) {
			case "name" -> parseDName(item);
			case "height" -> parseDHeight(item);
			case "hairColor" -> parseDColor(item);
			case "location" -> parseDLocation(item);
			case "birthday" -> parseDBirthday(item);
			default -> throw new IllegalArgumentException("Incorrect XML format");
		}
	}

	/**
	 * Parses the contents of director's name tag.
	 * @param item
	 * @ if the format is incorrect or the field is empty.
	 */
	private static void parseDName(Node item)  {
		try {
			dname = item.getTextContent();
		} catch (Exception e) {
			throw new IllegalArgumentException("Incorrect XML format");
		}
	}

	/**
	 * Parses the contents of director's height tag.
	 * @param item
	 * @ if the format is incorrect or the field is empty.
	 */
	private static void parseDHeight(Node item)  {
		try {
			dheight = Integer.parseInt(item.getTextContent());
		} catch (Exception e) {
			throw new IllegalArgumentException("Incorrect XML format");
		}
	}

	/**
	 * Parses the contents of director's hairColor tag.
	 * @param item
	 * @ if the format is incorrect or the field is empty.
	 * @see Color
	 */
	private static void parseDColor(Node item)  {
		try {
			dcolor = Convertible.convert(item.getTextContent(), Color.class);
		} catch (Exception e) {
			throw new IllegalArgumentException("Incorrect XML format");
		}
	}

	/**
	 * Parses the contents of director's location tag.
	 * @param item
	 * @ if the format is incorrect or the field is empty.
	 * @see Location
	 */
	private static void parseDLocation(Node item)  {
		try {
			NodeList t = item.getChildNodes();
			String x = t.item(0).getTextContent().trim();
			String y = t.item(1).getTextContent().trim();
			String z = t.item(2).getTextContent().trim();
			if (x.isEmpty() || y.isEmpty() || z.isEmpty()) {
				System.err.println("Incorrect XML format");
				return;
			}
			dlocation = new Location(
					Long.parseLong(x),
					Double.parseDouble(y),
					Double.parseDouble(z)
			);
		} catch (Exception e) {
			throw new IllegalArgumentException("Incorrect XML format");
		}
	}

	/**
	 * Parses the contents of director's birthday tag.
	 * @param item
	 * @ if the format is incorrect or the field is empty.
	 */
	private static void parseDBirthday(Node item)  {
		try {
			ddate = new SimpleDateFormat("dd.MM.yyyy").parse(item.getTextContent());
		} catch (Exception e) {
			throw new IllegalArgumentException("Incorrect XML format");
		}
	}
}
