package com.optimizely.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.optimizely.model.*;
import com.optimizely.service.AuthorService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

public class CSVHelper {
    public static String TYPE = "text/csv";
    public static String preDefinedDateFormat = "dd.MM.yyyy";
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(preDefinedDateFormat)
            .withLocale(Locale.GERMANY);

    public static boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<Megazine> csvToMegazine(InputStream is, AuthorService authorService) throws Exception {
        CSVParser csvParser = getCsvParser(is);

        List<Megazine> megazines = new ArrayList<>();

        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for (CSVRecord csvRecord : csvRecords) {
            String title = csvRecord.get("Titel");
            String authorsEmails = csvRecord.get("Autor");
            String publicationDateString = csvRecord.get("Erscheinungsdatum");
            String isbn = csvRecord.get("ISBN-Nummer");

            if (!Util.validateParam(title) && !Util.validateParam(authorsEmails) && !Util.validateParam(publicationDateString) && !Util.validateParam(isbn)) {
                System.out.println("Skipping entry. parameter missing");
                continue;
            }

            LocalDate publicationDate = LocalDate.parse(csvRecord.get("Erscheinungsdatum"), formatter);

            Megazine megazine = new Megazine(
                    title,
                    isbn,
                    publicationDate
            );

            //now we save the authors has books
            List<String> authorsEmail = Arrays.stream(authorsEmails.split(",")).collect(Collectors.toList());
            for (String email : authorsEmail) {
                Author byEmail = authorService.findByEmail(email);

                megazine.addAuthorHasMegazine(new AuthorHasMegazine(megazine, byEmail));
            }

            megazines.add(megazine);
        }
        return megazines;
    }

    public static List<Book> csvToBooks(InputStream is, AuthorService userService) throws Exception {
        CSVParser csvParser = getCsvParser(is);

        List<Book> books = new ArrayList<>();

        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for (CSVRecord csvRecord : csvRecords) {
            String title = csvRecord.get("Titel");
            String authorsEmails = csvRecord.get("Autoren");
            String description = csvRecord.get("Kurzbeschreibung");
            String isbn = csvRecord.get("ISBN-Nummer");

            if (!Util.validateParam(title) && !Util.validateParam(authorsEmails) && !Util.validateParam(description) && !Util.validateParam(isbn)) {
                System.out.println("Skipping entry. parameter missing");
                continue;
            }

            Book book = new Book(
                    title,
                    description,
                    isbn
            );

            //now we save the authors has books
            List<String> authorsEmail = Arrays.stream(authorsEmails.split(",")).collect(Collectors.toList());
            for (String email : authorsEmail) {
                Author byEmail = userService.findByEmail(email);

                book.addAuthorHasBook(new AuthorHasBook(book, byEmail));
            }

            books.add(book);
        }
        return books;
    }

    private static CSVParser getCsvParser(InputStream is) throws IOException {
        CSVParser csvParser = null;

        BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim().withDelimiter(';'));

        return csvParser;
    }


    public static List<Author> csvToAuthors(InputStream is) throws Exception {
        CSVParser csvParser = getCsvParser(is);

        List<Author> authors = new ArrayList<>();
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for (CSVRecord csvRecord : csvRecords) {
            String name = csvRecord.get("Nachname");
            String firstName = csvRecord.get("Vorname");
            String email = csvRecord.get("Emailadresse");

            if (!Util.validateParam(name) && !Util.validateParam(firstName) && !Util.validateParam(email)) {
                System.out.println("Skipping entry. parameter missing");
                continue;
            }

            Author author = new Author(
                    name,
                    firstName,
                    email
            );

            authors.add(author);
        }

        return authors;
    }

}
