package Translate;

import Translate.Exceptions.LanguageException;
import Translate.Exceptions.ModuleException;
import java.util.*;

public class Translator {
    private List<String> languageOrder;
    private final Map<String, TranslatorModule> modules;
    private int maxPassAmount;

    public Translator() {
        this.languageOrder = new ArrayList<String>();
        this.modules = new HashMap<String, TranslatorModule>();
        this.maxPassAmount = -1;
    }

    public boolean hasLanguage(String language) {
        return languageOrder.contains(language);
    }

    public void addLanguage(String language) throws LanguageException {
        if (hasLanguage(language))
            throw new LanguageException();

        languageOrder.add(language);
    }

    public void deleteLanguage(String language) throws LanguageException {
        if (!hasLanguage(language))
            throw new LanguageException();

        languageOrder.remove(language);
    }

    public List<String> getLanguageOrder() {
        return languageOrder;
    }

    public void setLanguageOrder(String... languages) throws LanguageException {
        List<String> oldLanguageOrder = new ArrayList<String>(languageOrder);
        List<String> newLanguageOrder = new ArrayList<String>();

        for (String language: languages) {
            if (!hasLanguage(language))
                throw new LanguageException();

            if (oldLanguageOrder.contains(language)) {
                oldLanguageOrder.remove(language);
                newLanguageOrder.add(language);
            }
            else if (newLanguageOrder.contains(language)) {
                newLanguageOrder.remove(language);
                newLanguageOrder.add(language);
            }
        }

        newLanguageOrder.addAll(oldLanguageOrder);
        languageOrder = newLanguageOrder;
    }

    // by key
    public boolean hasModule(String name) {
        return modules.containsKey(name);
    }

    // by value
    public boolean hasModule(TranslatorModule module) {
        return modules.containsValue(module);
    }

    public void addModule(String name, String path) throws ModuleException {
        if (hasModule(name))
            throw new ModuleException();

        TranslatorModule module = new TranslatorModule(this, path);
        modules.put(name, module);
    }

    public TranslatorModule getModule(String name) throws ModuleException {
        if (!hasModule(name))
            throw new ModuleException();

        return modules.get(name);
    }

    public void deleteModule (String name) throws ModuleException {
        if (!hasModule(name))
            throw new ModuleException();

        modules.remove(name);
    }

    public Set<String> getModuleNameSet() {
        return modules.keySet();
    }

    public int getMaxPassAmount() {
        return maxPassAmount;
    }

    public void setMaxPassAmount() {
        this.maxPassAmount = -1;
    }

    public void setMaxPassAmount(int maxPassAmount) {
        this.maxPassAmount = maxPassAmount;
    }

}
