package Metier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.rtf.RTFEditorKit;

public class EliminationReponse extends Question {
	private String reponseCorrecte;
	private HashMap<String,Integer[]> hmReponses;

	/**
	 * Liste des réponses dans l'ordre d'élimination.
	 * @param reponseCorrecte La réponse correcte.
	 * @param hmReponses         Les réponses incorrectes et la réponses correcte dans l'ordre définit par le concepteur.
	 * @param pointsPerdusParElimination Les points perdus par élimination.
	 */
	public EliminationReponse(String intitule, Difficulte difficulté, Notion notion, int temps, int points, Metier metier) {
		super(intitule, difficulté, notion, temps, points, metier);
		this.reponseCorrecte = "";
		this.hmReponses = new HashMap<>();
	}

	public EliminationReponse(String intitule, Difficulte difficulté, Notion notion, int temps, int points, Metier metier, String explication) {
		super(intitule, difficulté, notion, temps, points, metier, explication);
		this.reponseCorrecte = "";
		this.hmReponses = new HashMap<>();
	}

	// Getters
	public String getReponseCorrecte() {
		return this.reponseCorrecte;
	}

	public HashMap<String,Integer[]> getHmReponses() {
		return this.hmReponses;
	}

	public int getLastIndex() {
		int indexMax = 0;
		for (Integer[] index : this.hmReponses.values()) {
			if (index[1] > indexMax) {
				indexMax = index[1];
			}
		}
		return indexMax + 1;
	}

	public boolean isReponseCorrecte(String reponse) {
		return this.reponseCorrecte.equals(reponse);
	}

	// Setters
	public void setReponseCorrecte(String reponseCorrecte) {
		this.reponseCorrecte = reponseCorrecte;
	}

	public void setHmReponses(HashMap<String,Integer[]>  hmReponses) {
		this.hmReponses = hmReponses;
	}


	// Methods to add and remove incorrect responses
	public void ajouterReponse(String reponse, int points, int ordre) {
		if (!this.hmReponses.containsKey(reponse) && ordre <= this.getLastIndex()+1) {
			this.hmReponses.put(reponse, new Integer[]{points, ordre});
		}
	}

	public void removeReponse(String reponse) {
		this.hmReponses.remove(reponse);
	}

	public void getAsData(String directoryPath) {
		try {
			File directory = new File(directoryPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			String fileName = Paths.get(directoryPath, "QuestionElimination.rtf").toString();
			DefaultStyledDocument doc = new DefaultStyledDocument();
			StyleContext context = new StyleContext();
			Style style = context.addStyle("Style", null);

			// Ajouter l'intitulé de la question
			style.addAttribute(StyleConstants.FontFamily, "Serif");
			style.addAttribute(StyleConstants.Bold, true);
			doc.insertString(doc.getLength(), "Intitulé: " + this.getIntitule() + "\n", style);

			// Ajouter la difficulté
			style = context.addStyle("Style", null);
			style.addAttribute(StyleConstants.Italic, true);
			doc.insertString(doc.getLength(), "Difficulté: " + this.getDifficulte().toString() + "\n", style);

			// Ajouter la notion
			doc.insertString(doc.getLength(), this.getNotion().toString() + "\n", style);

			// Ajouter le temps
			doc.insertString(doc.getLength(), "Temps: " + this.getTemps() + " secondes\n", style);

			// Ajouter les points
			doc.insertString(doc.getLength(), "Points: " + this.getPoint() + "\n", style);

			// Ajouter l'explication
			if (this.getExplication() != null && !this.getExplication().isEmpty()) {
				doc.insertString(doc.getLength(), "Explication: " + this.getExplication() + "\n", style);
			}
			else {
				doc.insertString(doc.getLength(), "\n", style);
			}

			// Ajouter les réponses possibles
			style.addAttribute(StyleConstants.FontFamily, "Serif");
			doc.insertString(doc.getLength(), "Réponses possibles:\n", style);

			for (String reponse : this.hmReponses.keySet()) {
				int pointPerdus = this.hmReponses.get(reponse)[0];
				if (reponse.equals(this.reponseCorrecte)){
					pointPerdus = 0;
				}
				doc.insertString(doc.getLength(), "- " + reponse + "( Point Perdus : " + pointPerdus + " ,Numero : " + this.hmReponses.get(reponse)[1] + ")\n", style);
			}

			// Ajouter les réponses correctes
			style.addAttribute(StyleConstants.FontFamily, "Serif");
			doc.insertString(doc.getLength(), "La bonne reponse:\n", style);

			doc.insertString(doc.getLength(), "- " + this.reponseCorrecte + "\n", style);
			
			FileOutputStream fos = new FileOutputStream(fileName);
			RTFEditorKit rtfKit = new RTFEditorKit();
			rtfKit.write(fos, doc, 0, doc.getLength());

		} catch (IOException | BadLocationException e) {
			e.printStackTrace();
		}
	}

	public static EliminationReponse getAsInstance(String pathDirectory, Metier metier) {
		try {
			FileInputStream fis = new FileInputStream(pathDirectory + "QuestionElimination.rtf");
			DefaultStyledDocument doc = new DefaultStyledDocument();
			RTFEditorKit rtfKit = new RTFEditorKit();

			rtfKit.read(fis, doc, 0);

			// Récupérer le contenu du fichier
			String content = doc.getText(0, doc.getLength());

			// Récupérer les lignes du contenu
			String[] lines = content.split("\n");
			String intitule = lines[0].split(": ")[1];

			// Récupérer la difficulté
			Difficulte difficulte = Difficulte.getDifficulteByIndice(Integer.parseInt(lines[1].split("\\(")[1].split("\\)")[0]));

			// Récupérer la notion
			String id = lines[2].split(": ")[2];
			id = id.substring(0, id.length()-1);
			Notion notion = metier.getNotionById(Integer.parseInt(id));

			// Récupérer le temps
			int temps = Integer.parseInt(lines[4].split(": ")[1].split(" ")[0]);

			// Récupérer les points
			int points = Integer.parseInt(lines[5].split(": ")[1]);

			// Récupérer l'explication
			String explication = "";
			if (lines.length > 6 && lines[6].contains("Explication")) {
				explication = lines[6].split(": ")[1];
			}

			// Créer une instance de EliminationReponse
			EliminationReponse eq = new EliminationReponse(intitule, difficulte, notion, temps, points, metier, explication);

			// Récupérer les réponses
			int lastReponse = 7;
			for (int i = 7; i < lines.length; i++) {
				if (lines[i].contains("bonne")) {
					lastReponse = i;
					break;
				}
				if (lines[i].contains("-")) {
					String ligne = lines[i].substring(2);
					
					String reponse = ligne.substring(0, ligne.indexOf("("));
					int pointPerdus = Integer.parseInt(ligne.split("Point Perdus : ")[1].split(" ,Numero")[0]);
					int numero = Integer.parseInt(ligne.split("Numero : ")[1].split("\\)")[0]);

					eq.ajouterReponse(reponse, pointPerdus, numero);
				}
			}

			// Récupérer les bonnes réponses
			for (int i = lastReponse; i < lines.length; i++) {
				if (lines[i].contains("-")) {
					eq.setReponseCorrecte(lines[i].substring(2));
				}
			}
			return eq;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String toString(){
		String res = super.toString();
		res += "Reponse correcte : " + this.reponseCorrecte + "\n";
		res += "Reponses : \n";
		for (String reponse : this.hmReponses.keySet()) {
			res += reponse + " : " + this.hmReponses.get(reponse)[0] + " , " + this.hmReponses.get(reponse)[1] + "\n";
		}
		return res;
	}

	public void echangerPlace(String reponse1, String reponse2) {
		if (this.hmReponses.containsKey(reponse1) && this.hmReponses.containsKey(reponse2)) {
			int index1 = this.hmReponses.get(reponse1)[1];
			int index2 = this.hmReponses.get(reponse2)[1];
			this.hmReponses.get(reponse1)[1] = index2;
			this.hmReponses.get(reponse2)[1] = index1;
		}
	}
}