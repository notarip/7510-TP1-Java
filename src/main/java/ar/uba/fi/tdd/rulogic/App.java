package ar.uba.fi.tdd.rulogic;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ar.uba.fi.tdd.rulogic.model.KnowledgeBase;

/**
 * Console application.
 *
 */
public class App
{
	public static void main(String[] args) {
		
		String fileName = "rules.db";
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		URL resource = classloader.getResource(fileName);

		try (Stream<String> stream = Files.lines(Paths.get(resource.getPath()))) {
			String db = stream.collect(Collectors.joining());
			KnowledgeBase kb = new KnowledgeBase();
			kb.setDatabase(db);
			readQuerys(kb);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	private static void readQuerys(KnowledgeBase kb) {
     
		Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.print("Shall I help you ? (q: exit) ");
            String input = scanner.nextLine();

            if ("q".equals(input)) {
                System.out.println("Bye!");
                break;
            }

            try {
            	System.out.println(kb.answer(input)?"SI":"NO");
            }catch (Exception e) {
            	System.out.println("NO"); 
			}

        }

        scanner.close();

    }

}
