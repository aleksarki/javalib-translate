package Translate;

import Translate.Exceptions.TranslationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class TranslatorModule {
    private final Translator translator;  // Translator object where properties of translating are stored
    private final String path;
    private final Map<String, TranslatableString> strings;  // here 'name: translatable string' pairs are stored

    /**
     * Initializes a translator module instance;
     * parses an xml file of translatable strings.
     *
     * @param translator instance of Translator class which stores properties of translating
     * @param path path to the module localization file
     */
    public TranslatorModule(Translator translator, String path) {
        this.translator = translator;
        this.path = path;
        this.strings = new HashMap<String, TranslatableString>();
        parseStrings();
    }

    /**
     * Initializes a translator module instance;
     * parses an xml file of translatable strings.
     *
     * @param staticTranslatorClass StaticTranslator class which stores properties of translating
     * @param path path to the module localization file
     */
    public TranslatorModule(Class<StaticTranslator> staticTranslatorClass, String path) {
        this.translator = null;
        this.path = path;
        this.strings = new HashMap<String, TranslatableString>();
        parseStrings();
    }

    /**
     * Internal method which parses an xml file of translatable strings.
     */
    private void parseStrings() {
        try {  // parsing here below
            var documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            var document = documentBuilder.parse(path);
            Node moduleNode = document.getDocumentElement();  // node of the module
            NodeList stringNodes = moduleNode.getChildNodes();  // nodes of translatable strings

            for (int i = 0; i < stringNodes.getLength(); i++) {
                Node stringNode = stringNodes.item(i);
                if (stringNode.getNodeType() != Node.TEXT_NODE) {  // we got only string nodes (more like excluded text nodes)

                    String stringName = stringNode.getAttributes().getNamedItem("name").getTextContent();  // name of the string
                    TranslatableString translatableString = new TranslatableString();
                    NodeList stringTranslationNodes = stringNode.getChildNodes();  // nodes of the translations of the string

                    for (int j = 0; j < stringTranslationNodes.getLength(); j++) {
                        Node stringTranslationNode = stringTranslationNodes.item(j);
                        if (stringTranslationNode.getNodeType() != Node.TEXT_NODE) {  // we got only translation nodes

                            String languageName = stringTranslationNode.getAttributes().getNamedItem("lang").getTextContent();
                            String translationText = stringTranslationNode.getTextContent();
                            translatableString.setTranslation(languageName, translationText);  // add translation to the string
                        }
                    }

                    strings.put(stringName, translatableString);  // add translatable string
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
            e.printStackTrace();
        }
    }

    /*
     * Internal method which return either its Translator instance,
     * or StaticTranslator class, depending on what is defined.
     * Used to get access to its translator properties.
     *
     * @return StaticTranslator class of instance of Translator class.
     *
     *
    private Object getTranslator() {
        if (translator == null)
            return staticTranslator;
        return translator;
    }
     */

    /**
     * Returns path to the module localization file.
     * @return path to the localization file
     */
    public String getPath() {
        return path;
    }

    /**
     * Returns true if module contains translatable string
     * with provided name, false otherwise.
     *
     * @param name name of translatable string
     * @return if module contains the string
     */
    public boolean hasString(String name) {
        return strings.containsKey(name);
    }

    /**
     * Returns string translated in preferred order.
     * If translation for preferred language is absent,
     * translation for the next language will be done.
     *
     * @param stringName name of the string to be translated
     * @return translation of the string
     * @throws TranslationException in a case...
     */
    public String getString(String stringName) throws TranslationException {
        if (!hasString(stringName))
            throw new TranslationException();

        TranslatableString string = strings.get(stringName);
        int passAmount;
        List<String> languageOrder;

        if (translator == null) {
            languageOrder = StaticTranslator.getLanguageOrder();
            passAmount = StaticTranslator.getMaxPassAmount();
        } else {
            languageOrder = translator.getLanguageOrder();
            passAmount = translator.getMaxPassAmount();
        }

        if (passAmount < 0)                      // negative maximum pass-amount
            passAmount += languageOrder.size();  // means counting from the end

        for (String language: languageOrder) {           // we run through preferred order of languages
            if (string.hasTranslation(language))         // if there is translation provided for the language,
                return string.getTranslation(language);  // we return it

            if (passAmount == 0)
                throw new TranslationException();  // we ran out of translation attempts

            passAmount--;
        }
        throw new TranslationException();  // there is no translation for the string altogether ~
    }

    // same as getString
    public String get(String stringName) throws TranslationException {
        return getString(stringName);
    }

    /**
     * Returns set of available string names.
     * @return set of names of strings
     */
    public Set<String> getStringNameSet() {
        return strings.keySet();
    }
}
