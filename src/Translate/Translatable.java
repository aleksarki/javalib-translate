package Translate;

import Translate.Exceptions.TranslationException;

import java.util.Set;

public interface Translatable {

    /**
     * Defines new translation of the string in a language.
     *
     * @param language Name of the language
     * @param translation Translation of the string
     */
    void setTranslation(String language, String translation);

    /**
     * Returns translation of the string in a language.
     *
     * @param language Name of the language
     * @return Returns translation
     * @throws TranslationException if there is no translation for the language
     */
    String getTranslation(String language) throws TranslationException;

    /**
     * Returns true if a translation for the language is provided,
     * false otherwise.
     *
     * @param language Name of the language
     * @return Returns if translation is provided
     */
    boolean hasTranslation(String language);

    /**
     * Deletes translation for the provided language.
     *
     * @param language name of the language
     * @throws TranslationException if there is no translation for the language
     */
    void deleteTranslation(String language) throws TranslationException;

    /**
     * Returns set of supported languages.
     * @return set of languages
     */
    Set<String> getLanguageSet();

}
