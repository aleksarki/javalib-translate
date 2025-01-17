package Translate;

import Translate.Exceptions.TranslationException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TranslatableString implements Translatable {
    private final Map<String, String> translations;  // here 'language: translation' pairs are stored

    public TranslatableString() {
        this.translations = new HashMap<String, String>();
    }

    @Override
    public void setTranslation(String language, String translation) {
        translations.put(language, translation);
    }

    @Override
    public String getTranslation(String language) throws TranslationException {
        if (!hasTranslation(language))
            throw new TranslationException();
        return translations.get(language);
    }

    @Override
    public boolean hasTranslation(String language) {
        return translations.containsKey(language);
    }

    @Override
    public void deleteTranslation(String language) throws TranslationException {
        if (!hasTranslation(language))
            throw new TranslationException();

        translations.remove(language);
    }

    @Override
    public Set<String> getLanguageSet() {
        return translations.keySet();
    }
}
