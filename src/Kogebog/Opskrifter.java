package Kogebog;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Opskrifter {
    private ArrayList<String> opskrifter = new ArrayList<>();
    private ArrayList<String> Ingredienser = new ArrayList<>();
    private static final String FILNAVN = "opskrifter.txt";

    public Opskrifter() {
        indlæsOpskrifterFraFil();
    }

    public void tilføjOpskrift(String opskrift) {
        if (opskrifter.size() < 9) {
            opskrifter.add(opskrift);
            System.out.println("Opskrift tilføjet til kogebogen.");
            gemOpskrifterTilFil();
        } else {
            System.out.println("Kogebogen er fuld. Kan ikke tilføje flere opskrifter.");
        }
    }

    public void visOpskrifter() {
        System.out.println("Kogebogen indeholder følgende opskrifter:");
        for (int i = 0; i < opskrifter.size(); i++) {
            System.out.println((i + 1) + ". " + opskrifter.get(i));
        }
    }

    private void indlæsOpskrifterFraFil() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILNAVN));
            String linje;
            while ((linje = reader.readLine()) != null) {
                opskrifter.add(linje);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Fejl ved indlæsning af opskrifter fra fil.");
        }
    }

    private void gemOpskrifterTilFil() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILNAVN));
            for (String opskrift : opskrifter) {
                writer.write(opskrift);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Fejl ved gemning af opskrifter til fil.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Kogebog.Opskrifter kogebog = new Kogebog.Opskrifter();

        while (true) {
            System.out.println("\nVælg en handling:");
            System.out.println("1. Tilføj opskrift");
            System.out.println("2. Vis opskrifter");
            System.out.println("3. Søg efter opskrifter");
            System.out.println("4. Generer indkøbsliste for opskrift");
            System.out.println("3. Afslut");

            int valg = scanner.nextInt();
            scanner.nextLine();  // Scanner bug

            switch (valg) {
                case 1:
                    System.out.println("Indtast en opskrift:");
                    String opskrift = scanner.nextLine();
                    kogebog.tilføjOpskrift(opskrift);
                    break;
                case 2:
                    kogebog.visOpskrifter();
                    break;
                case 3:
                    System.out.println("Indtast søgeord:");
                    String søgeord = scanner.nextLine();
                    kogebog.søgOpskrifter(søgeord);
                    break;
                case 4:
                    System.out.println("Indtast navn på opskrift for indkøbsliste:");
                    String opskriftNavn = scanner.nextLine();
                    kogebog.genererIndkøbsliste(opskriftNavn);
                    break;
                case 5:
                    System.out.println("Farvel!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Ugyldigt valg. Prøv igen.");
            }
        }
    }

    public void søgOpskrifter(String søgeord) {
        System.out.println("Resultater for søgning efter: " + søgeord);
        for (String opskrift : opskrifter) {
            if (opskrift.toLowerCase().contains(søgeord.toLowerCase())) {
                System.out.println("- " + opskrift);
            }
        }
    }

    public String[] genererIndkøbsliste(String opskriftNavn) {
        for (String opskrift : opskrifter) {
            if (opskrift.equalsIgnoreCase(opskriftNavn)) {
                String[] ingredienser = opskrift.split(", ");
                System.out.println("Indkøbsliste for " + opskriftNavn + ":");
                for (String ingrediens : ingredienser) {
                    System.out.println("- " + ingrediens);
                }
                return ingredienser;
            }
        }
        System.out.println("Opskriften: '" + opskriftNavn + "' blev ikke fundet.");
        return new String[0];
    }
}
