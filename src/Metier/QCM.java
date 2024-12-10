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
	private HashMap<String, Boolean> reponses;

	public QCM(String intitule, Difficulte difficulté, Notion notion, int temps, int points, Metier metier) {
		super(intitule, difficulté, notion, temps, points, metier);
		this.reponses = new HashMap<String, Boolean>();
	}

	public QCM(String intitule, Difficulte difficulté, Notion notion, int temps, int points, Metier metier,
			String explication) {
		super(intitule, difficulté, notion, temps, points, metier, explication);
		this.reponses = new HashMap<String, Boolean>();
	}

	// Get

	public HashMap<String, Boolean> getReponses() {
		return this.reponses;
	}

	// Set

	public void setReponses(HashMap<String, Boolean> reponses) {
		this.reponses = reponses;
	}

	// Méthode pour gérer les réponse ajouter/enlever
	public void ajouterReponse(String reponse, Boolean correct) {
		this.reponses.put(reponse, correct);
	}

	public void enleverReponse(String reponse) {
		this.reponses.remove(reponse);
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
			doc.insertString(doc.getLength(), "Difficulté: " + this.getDifficulte() + "\n", style);

			// Ajouter la notion
			doc.insertString(doc.getLength(), this.getNotion().toString() + "\n", style);

			// Ajouter le temps
			doc.insertString(doc.getLength(), "Temps: " + this.getTemps() + " secondes\n", style);

			// Ajouter les points
			doc.insertString(doc.getLength(), "Points: " + this.getPoint() + "\n", style);

			if (this.getExplication() != null && !this.getExplication().isEmpty()) {
				// Ajouter l'explication
				doc.insertString(doc.getLength(), "Explication: " + this.getExplication() + "\n", style);
			}

			style.addAttribute(StyleConstants.FontFamily, "Serif");
			doc.insertString(doc.getLength(), "Réponses possibles:\n", style);

			for (String reponse : this.reponses.keySet()) {
				doc.insertString(doc.getLength(), "- " + reponse + "\n", style);
			}

			style.addAttribute(StyleConstants.FontFamily, "Serif");
			doc.insertString(doc.getLength(), "Bonnes réponses:\n", style);

			for (HashMap.Entry<String, Boolean> entry : this.reponses.entrySet()) {
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
// Corriger le getAsInstance ,car il faut qu'il prenne en compte que toutes ces informations sont dans un seul fichier
	public static QCM getAsInstance(String pathDirectory, Metier metier) {
		try {
			FileInputStream fis = new FileInputStream(pathDirectory + "QCM.rtf");
			DefaultStyledDocument doc = new DefaultStyledDocument();
			RTFEditorKit rtfKit = new RTFEditorKit();

			rtfKit.read(fis, doc, 0);

			String content = doc.getText(0, doc.getLength());

			String[] lines = content.split("\n");
			String intitule = lines[0].split(": ")[1];

			Difficulte difficulte = Difficulte.valueOf(lines[1].split(": ")[1]);

			Notion notion = metier.getNotionById(Integer.parseInt(lines[2].split(": ")[1]));

			int temps = Integer.parseInt(lines[3].split(": ")[1].split(" ")[0]);

			int points = Integer.parseInt(lines[4].split(": ")[1]);

			String explication = "";
			if (lines.length > 5) {
				explication = lines[5].split(": ")[1];
			}

			QCM qcm = new QCM(intitule, difficulte, notion, temps, points, metier, explication);
			FileInputStream fisReponses = new FileInputStream(pathDirectory + "reponses_QCM.rtf");
			DefaultStyledDocument docReponses = new DefaultStyledDocument();
			RTFEditorKit rtfKitReponses = new RTFEditorKit();
			rtfKitReponses.read(fisReponses, docReponses, 0);
			String contentReponses = docReponses.getText(0, docReponses.getLength());
			String[] linesReponses = contentReponses.split("\n");
			for (int i = 1; i < linesReponses.length; i++) {
				qcm.ajouterReponse(linesReponses[i].substring(2), false);
			}
			FileInputStream fisBonnesReponses = new FileInputStream(pathDirectory + "bonnes_reponses_QCM.rtf");
			DefaultStyledDocument docBonnesReponses = new DefaultStyledDocument();
			RTFEditorKit rtfKitBonnesReponses = new RTFEditorKit();
			rtfKitBonnesReponses.read(fisBonnesReponses, docBonnesReponses, 0);
			String contentBonnesReponses = docBonnesReponses.getText(0, docBonnesReponses.getLength());
			String[] linesBonnesReponses = contentBonnesReponses.split("\n");
			for (int i = 1; i < linesBonnesReponses.length; i++) {
				qcm.ajouterReponse(linesBonnesReponses[i].substring(2), true);
			}
			return qcm;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String toString() {
		String res = "QCM : " + this.getIntitule() + "\n";
		res += "Difficulté : " + this.getDifficulte() + "\n";
		res += "Notion : " + this.getNotion() + "\n";
		res += "Temps : " + this.getTemps() + " secondes\n";
		res += "Points : " + this.getPoint() + "\n";
		if (this.getExplication() != null && !this.getExplication().isEmpty()) {
			res += "Explication : " + this.getExplication() + "\n";
		}
		res += "Réponses : \n";
		for (HashMap.Entry<String, Boolean> entry : this.reponses.entrySet()) {
			res += entry.getKey() + " -> " + entry.getValue() + "\n";
		}
		return res;
	}

	public static void main(String[] args) {
		Metier metier = new Metier();
		Notion notion = new Notion(1, "Notion", new Ressource(1,"Ressource","R"), metier);
		metier.ajouterNotion(notion);
		QCM qcm = new QCM("QCM", Difficulte.FACILE, notion, 10, 10,
				metier);
		qcm.ajouterReponse("A", true);
		qcm.ajouterReponse("B", false);
		qcm.ajouterReponse("C", true);
		qcm.getAsData("QCM/");
		//QCM qcm2 = getAsInstance("QCM/", metier);
		//System.out.println(qcm2);
	}
}