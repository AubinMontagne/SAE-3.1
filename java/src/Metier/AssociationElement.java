package src.Metier;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class AssociationElement extends Question
{
    private HashMap<String, String> hmAssociations;

    // Constructeur
	/**
	 * Constructeur de la class AssociationElement
	 * @param dossierChemin	L'intituler de la question type Association-Element
	 * @param difficulte    La difficulté de la question, qui peut être : très facile, facile, moyen, difficile.
     * @param notion        La notion concernée par la question.
     * @param temps         Le temps nécessaire pour répondre à la question en millisecondes.
     * @param points        Le nombre de points que rapporte la question.
	 */
    public AssociationElement(String dossierChemin, Difficulte difficulte, Notion notion, int temps, int points, String imageChemin, int id){
        super(dossierChemin, difficulte, notion, temps, points, imageChemin, id);
        this.hmAssociations = new HashMap<>();
    }

    // Methode
    /**
     * Méthode ajouterAssociation
     * Cette mèthode sert a ajouter une association entre une réponse de gauche
     * et une réponse de droite dans la HashMap des associations
     * @param gauche La réponse à gauche
     * @param droite La réponse à droite
     */
    public void ajouterAssociation(String gauche, String droite){
        if (!(gauche == null || droite == null) &&
                (!this.hmAssociations.containsKey(gauche) || !this.hmAssociations.containsValue(droite)) &&
                (!this.hmAssociations.containsKey(droite) || !this.hmAssociations.containsValue(gauche))){
            this.hmAssociations.put(gauche, droite);
        }
    }

    /**
     * Méthode supprimerAssociation
     * Cette métode sert a enlever une association d'une réponse de gauche
     * @param gauche La réponse à gauche
     */
    public void supprimerAssociation(String gauche){this.hmAssociations.remove(gauche); }

    public String toString(){
        String res = super.toString();
        res += "Associations : \n";
        for (String gauche : this.hmAssociations.keySet()) {
            res += gauche + " -> " + this.hmAssociations.get(gauche) + "\n";
        }
        return res;
    }

    // Getter
    public HashMap<String, String> getAssociations(){return this.hmAssociations; }

    public String getAsData(){
        String res = "AE;" + super.getAsData() + ";" ;
        for (String gauche : this.hmAssociations.keySet()) {
            res += gauche + "," + this.hmAssociations.get(gauche) + "|";
        }
        return res;
    }

    public static AssociationElement getAsInstance(String ligne, Metier metier){
        Scanner scanner = new Scanner(ligne);
        scanner.useDelimiter(";");

        String[] parts = new String[10];
        for (int i = 0; i < 10; i++) {
            parts[i] = scanner.next();
        }

        AssociationElement associationElement = new AssociationElement(parts[1], Difficulte.getDifficulteByIndice(Integer.parseInt(parts[2])), metier.getNotionByNom(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), parts[7], Integer.parseInt(parts[6]));

        Scanner associationScanner = new Scanner(parts[9]);
        associationScanner.useDelimiter("\\|");
        while (associationScanner.hasNext()) {
            String association = associationScanner.next();
            Scanner associationPartsScanner = new Scanner(association);
            associationPartsScanner.useDelimiter(",");
            String[] associationParts = new String[2];
            for (int i = 0; i < 2; i++) {
                associationParts[i] = associationPartsScanner.next();
            }
            associationElement.ajouterAssociation(associationParts[0], associationParts[1]);
            associationPartsScanner.close();
        }

        associationScanner = new Scanner(parts[8]);
        associationScanner.useDelimiter(",");
        while (associationScanner.hasNext()) {
            String fichier = associationScanner.next();
            associationElement.ajouterFichier(fichier);
        }

        return associationElement;
    }
}
