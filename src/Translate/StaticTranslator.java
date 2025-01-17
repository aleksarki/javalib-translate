package Translate;

import Translate.Exceptions.LanguageException;
import Translate.Exceptions.ModuleException;

import java.util.*;

public class StaticTranslator {

    private static List<String> languageOrder;
    private static final Map<String, TranslatorModule> modules;
    private static int maxPassAmount;

    static {
        languageOrder = new ArrayList<String>();
        modules = new HashMap<String, TranslatorModule>();
        maxPassAmount = -1;
    }

    public static boolean hasLanguage(String language) {
        return languageOrder.contains(language);
    }

    public static void addLanguage(String language) throws LanguageException {
        if (hasLanguage(language))
            throw new LanguageException();

        languageOrder.add(language);
    }

    public static void deleteLanguage(String language) throws LanguageException {
        if (!hasLanguage(language))
            throw new LanguageException();

        languageOrder.remove(language);
    }

    public static List<String> getLanguageOrder() {
        return languageOrder;
    }

    public static void setLanguageOrder(String... languages) throws LanguageException {
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
    public static boolean hasModule(String name) {
        return modules.containsKey(name);
    }

    // by value
    public static boolean hasModule(TranslatorModule module) {
        return modules.containsValue(module);
    }

    public static void addModule(String name, String path) throws ModuleException {

        if (hasModule(name))
            throw new ModuleException();

        TranslatorModule module = new TranslatorModule(StaticTranslator.class, path);
        modules.put(name, module);
    }

    public static TranslatorModule getModule(String name) throws ModuleException {
        if (!hasModule(name))
            throw new ModuleException();

        return modules.get(name);
    }

    public static void deleteModule (String name) throws ModuleException {
        if (!hasModule(name))
            throw new ModuleException();

        modules.remove(name);
    }

    public static Set<String> getModuleNameSet() {
        return modules.keySet();
    }

    public static int getMaxPassAmount() {
        return maxPassAmount;
    }

    public static void setMaxPassAmount() {
        maxPassAmount = -1;
    }

    public static void setMaxPassAmount(int maxPassAmount) {
        StaticTranslator.maxPassAmount = maxPassAmount;
    }

}
