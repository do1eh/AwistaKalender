package de.do1eh;



import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Summary;

import java.io.*;
        import java.util.*;
        import java.util.stream.Collectors;



public class ICSFileSplitter {

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 2) {
            System.out.println("Usage: java ICSFileSplitter <input ICS file> <output directory>");
            return;
        }

        String inputFile = args[0];
        String outputDir = args[1];

        try {
            // Datei einlesen
            FileInputStream fin = new FileInputStream(inputFile);
            CalendarBuilder builder = new CalendarBuilder();
            net.fortuna.ical4j.model.Calendar calendar = builder.build(fin);

            // Alle Events extrahieren
            List<VEvent> events = calendar.getComponents(Component.VEVENT).stream()
                    .map(component -> (VEvent) component)
                    .toList();

            // Events nach Summary gruppieren
            Map<String, List<VEvent>> groupedBySummary = new HashMap<>();


            for (VEvent event : events) {

                Summary summary=new Summary();
                summary=event.getSummary().get();
                //Summary umbenennen

                switch (summary.getValue()) {
                    case "Papier (Teilservice)":
                        summary.setValue("Altpapier");
                        break;
                    case "Restmüll (Vollservice)":
                        summary.setValue("graue Tonne");
                        break;
                    case "Wertstofftonne (Vollservice)":
                        summary.setValue("gelbe Tonne");
                        break;
                }

                String summary2 = summary.getValue();
                groupedBySummary
                        .computeIfAbsent(summary2, k -> new ArrayList<>())
                        .add(event);
            }

            // Neue ICS-Dateien für jedes Summary erstellen
            for (Map.Entry<String, List<VEvent>> entry : groupedBySummary.entrySet()) {
                String summary = entry.getKey();
                List<VEvent> groupEvents = entry.getValue();

                // Neues Kalenderobjekt erstellen
                Calendar newCalendar = new Calendar();


                for (VEvent event : groupEvents) {
                    newCalendar.add(event);
                }
                // Datei speichern
                String outputFileName = summary.replaceAll("[^a-zA-Z0-9]", "_") + ".ics";
                File outputFile = new File(outputDir, outputFileName);
                outputFile.getParentFile().mkdirs();

                FileOutputStream fout = new FileOutputStream(outputFile);
                CalendarOutputter outputter = new CalendarOutputter();
                outputter.output(newCalendar, fout);
                fout.close();

                System.out.println("Datei erstellt: " + outputFile.getAbsolutePath());
            }


        } catch (ParserException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    }