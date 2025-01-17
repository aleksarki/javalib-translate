package Translate;

import Translate.Exceptions.LanguageException;
import Translate.Exceptions.ModuleException;
import Translate.Exceptions.TranslationException;

public class Test {
    /* public static void print(Object o) {
        System.out.println(o);
    } */

    public static void main(String[] args) throws LanguageException, ModuleException, TranslationException {
        /*
        Translator translator = new Translator();
        translator.addLanguage("rus");
        translator.addLanguage("eng");
        translator.addLanguage("fra");
        print(translator.getLanguageOrder());

        translator.deleteLanguage("eng");
        print(translator.getLanguageOrder());

        String moduleName = "<module1>";
        translator.addModule(moduleName, ".");
        print(translator.getModuleNameSet());

        Module module = translator.getModule(moduleName);
        print(module.getName());

        moduleName = "<new name>";
        module.setName(moduleName);
        print(module.getName());
        print(translator.getModuleNameSet());

        translator.deleteModule(moduleName);
        print(translator.getModuleNameSet());

        translator.addLanguage("eng");
        translator.setLanguageOrder();
        print(translator.getLanguageOrder());

        translator.setLanguageOrder("eng", "eng");
        print(translator.getLanguageOrder());

        translator.setLanguageOrder("fra", "rus");
        print(translator.getLanguageOrder());
        */

        // Testing Translator

        var translator = new Translator();

        for (var languageName: new String[]{"en", "ru", "fr"})
            translator.addLanguage(languageName);

        translator.setLanguageOrder("ru");
        translator.setMaxPassAmount(1);

        var moduleName1 = "<module1>";
        translator.addModule(moduleName1, "test.xml");
        var module1 = translator.getModule(moduleName1);

        for (var stringName: module1.getStringNameSet()) {
            System.out.println(module1.get(stringName));
        }


        // Testing StaticTranslator

        for (var languageName: new String[]{"en", "ru", "fr"})
            StaticTranslator.addLanguage(languageName);

        StaticTranslator.setLanguageOrder("fr");
        StaticTranslator.setMaxPassAmount(1);

        var moduleName2 = "<module1>";
        StaticTranslator.addModule(moduleName2, "test.xml");
        var module2 = StaticTranslator.getModule(moduleName2);

        for (var stringName: module2.getStringNameSet()) {
            System.out.println(module2.get(stringName));
        }

    }
}
