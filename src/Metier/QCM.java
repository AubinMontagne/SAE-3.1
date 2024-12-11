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

public class QCM extends Question {
	private HashMap<String, Boolean> hmReponses;

	public QCM(String intitule, Difficulte difficulté, Notion notion, int temps, int points, Metier metier) {
		super(intitule, difficulté, notion, temps, points, metier);
		this.hmReponses = new HashMap<String, Boolean>();
	}

	public QCM(String intitule, Difficulte difficulté, Notion notion, int temps, int points, Metier metier,
			String explication) {
		super(intitule, difficulté, notion, temps, points, metier, explication);
		this.hmReponses = new HashMap<String, Boolean>();
	}

	// Get

	public HashMap<String, Boolean> getReponses() {
		return this.hmReponses;
	}

	// Set

	public void setHmReponses(HashMap<String, Boolean> hmReponses) {
		this.hmReponses = hmReponses;
	}

	// Méthode pour gérer les réponse ajouter/enlever
	public void ajouterReponse(String reponse, Boolean correct) {
		if (this.hmReponses.containsKey(reponse)) {
			this.hmReponses.replace(reponse, correct);
		}
		else {
			this.hmReponses.put(reponse, correct);
		}
	}

	public void enleverReponse(String reponse) {
		this.hmReponses.remove(reponse);
	}

	public void getAsData(String directoryPath) {
		try {
			File directory = new File(directoryPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			String fileName = Paths.get(directoryPath, "QCM.rtf").toString();
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
				doc.insertString(doc.getLength(), "- " + reponse + "\n", style);
			}

			// Ajouter les réponses correctes
			style.addAttribute(StyleConstants.FontFamily, "Serif");
			doc.insertString(doc.getLength(), "Bonnes réponses:\n", style);

			for (HashMap.Entry<String, Boolean> entry : this.hmReponses.entrySet()) {
				if (entry.getValue()) {
					doc.insertString(doc.getLength(), "- " + entry.getKey() + "\n", style);
				}
			}
			
			FileOutputStream fos = new FileOutputStream(fileName);
			RTFEditorKit rtfKit = new RTFEditorKit();
			rtfKit.write(fos, doc, 0, doc.getLength());

		} catch (IOException | BadLocationException e) {
			e.printStackTrace();
		}
	}
	public static QCM getAsInstance(String pathDirectory, Metier metier) {
		try {
			FileInputStream fis = new FileInputStream(pathDirectory + "QCM.rtf");
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

			// Créer une instance de QCM
			QCM qcm = new QCM(intitule, difficulte, notion, temps, points, metier, explication);

			// Récupérer les réponses
			int lastReponse = 7;
			for (int i = 7; i < lines.length; i++) {
				if (lines[i].contains("Bonnes")) {
					lastReponse = i;
					break;
				}
				if (lines[i].contains("-")) {
					qcm.ajouterReponse(lines[i].substring(2), false);
				}
			}

			// Récupérer les bonnes réponses
			for (int i = lastReponse; i < lines.length; i++) {
				if (lines[i].contains("-")) {
					qcm.ajouterReponse(lines[i].substring(2), true);
				}
			}
			return qcm;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String toString() {
		String res = super.toString();
		res += "Réponses : \n";
		for (HashMap.Entry<String, Boolean> entry : this.hmReponses.entrySet()) {
			res += entry.getKey() + " -> " + entry.getValue() + "\n";
		}
		return res;
	}
}