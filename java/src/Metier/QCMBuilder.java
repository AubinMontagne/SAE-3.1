package src.Metier;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class QCMBuilder
{
    private Questionnaire questionnaire;
    private Scanner scanner;
    private FileWriter fileWriter;
    private String path;

    private String nbOccurenceFichier;

    // Constructeur
    /**
     * Constructeur de la cass QCMBuilder
     * @param questionnaire Le questionnaire contenant des questions
     * @param path          Le chemin ou créér le dossier contenant les fichier du questionnaire en HTML/CSS/JS
     */
    public QCMBuilder(Questionnaire questionnaire, String path)
    {
        this.questionnaire = questionnaire;
        this.path = path;
        this.nbOccurenceFichier = "";

        this.creerDossier(path);

        try
        {
            this.fileWriter = new FileWriter(path + "/" +questionnaire.getNom() + this.nbOccurenceFichier + "/index.html");

            ecrireHTML();
            ecrireStyle();
            ecrireImage();
            ecrireJS();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //Methode

    /**
     * Methode creerDossier
     * Cette methode créé un fichier a l'endroit demander
     * @param path  Le chemin ou créer le dossier
     * @return      Vrai si le fichier a pu être créé, sinon faux
     */
    public void creerDossier(String path)
    {
        File file = new File(path + "/" + this.questionnaire.getNom() );
        File fileT;

        if(file.exists())
        {
            int i = 0;
            do
            {
                fileT = new File(file.getPath() + i);
                i++;
            }while(fileT.exists());

            this.nbOccurenceFichier = "" + (i-1);

            fileT.mkdir();
            file = fileT;
        }
        else
        {
            file.mkdir();
        }

        for(Question q : this.questionnaire.getLstQuestion())
        {
            File dossierTemp = new File(path + "/" + this.questionnaire.getNom() + this.nbOccurenceFichier + "/data/Question " + q.getId() + "/Compléments" );

            dossierTemp.mkdirs();
        }


        try
        {

            for(Question q : this.questionnaire.getLstQuestion()) {
                for (String s : q.getListeFichiers())
                {
                    if (s != null)
                    {
                        Path fileSource = Paths.get("java/data/" + q.getNotion().getRessourceAssociee().getNom() + "/" + q.getNotion().getNom() + "/Question " + q.getId() + "/Compléments/" + s);
                        Path fileDest = Paths.get(path + "/" + this.questionnaire.getNom() + this.nbOccurenceFichier +  "/data/Question " + q.getId() + "/Compléments/" + s);
                        Files.copy(fileSource, fileDest, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
                if (q.getImageChemin() != null && ! q.getImageChemin().isEmpty() && !q.getImageChemin().equals("null"))
                {
                    Path fileSource = Paths.get("java/data/" + q.getNotion().getRessourceAssociee().getNom() + "/" + q.getNotion().getNom() + "/Question " + q.getId() + "/Compléments/" + q.getImageChemin());
                    Path fileDest = Paths.get(path + "/" + this.questionnaire.getNom() + this.nbOccurenceFichier +  "/data/Question " + q.getId() + "/Compléments/" + q.getImageChemin());
                    Files.copy(fileSource, fileDest, StandardCopyOption.REPLACE_EXISTING);
                }
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            System.err.println("Erreur : " + ex.getMessage());
        }
    }

    /**
     * Metode ecrireHTML
     * Cette métode permet l'écriture de index.html commun a tous les questionnaires
     * @return  Vrai si le fichier a pu être écrit, sinon faux
     */
    public boolean ecrireHTML()
    {
        try
        {
            String listeNotions = "";

            int nbTresFacile = 0;
            int nbFacile = 0;
            int nbMoyen = 0;
            int nbDifficile = 0;

            for(Question q : this.questionnaire.getLstQuestion())
            {
                switch (q.getDifficulte().getIndice())
                {
                    case 1 -> nbTresFacile++;
                    case 2 -> nbFacile++;
                    case 3 -> nbMoyen++;
                    case 4 -> nbDifficile++;
                }

            }

            for(Notion n : this.questionnaire.getLstNotions())
            {
                listeNotions = listeNotions + "<li> " + n.getNom() + " </li>\n";
            }

            String html =   "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Auto-Evaluation - " + this.questionnaire.getRessource().getNom() + "</title>\n" +
                    "    <link href=\"style.css\" rel=\"stylesheet\">\n" +
                    "    <script src=\"./script.js\"></script>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <h1>Bienvenue dans un Questionnaire en " + this.questionnaire.getRessource().getNom() +"!</h1>\n" +
                    "    <h2>"+ this.questionnaire.getNom() +"</h2>\n" +
                    "    <h2>Temps pour finir : "+ questionnaire.getTempsEstimee() +" Secondes</h2>\n" +
                    "    <h2>Sur la matière : "+ this.questionnaire.getRessource().getNom() +"</h2>\n" +
                    "    <h2>Sur les notions suivante :</h2>\n" +
                    "    <ul>\n" +
                    listeNotions +
                    "    </ul> \n" +
                    "    <h2> Qui contient :</h2>\n" +
                    "    <p>\n" +
                    "        "+ nbTresFacile + " Question <img class=\"imgDif\" src=\"./data/imgDif/TF.png\"> <br> <!-- Avoir le nb Question avec JS -->\n" +
                    "        "+ nbFacile +" Question <img class=\"imgDif\" src=\"./data/imgDif/F.png\"> <br> <!-- Avoir le nb Question avec JS -->\n" +
                    "        "+ nbMoyen +" Question <img class=\"imgDif\" src=\"./data/imgDif/M.png\"> <br> <!-- Avoir le nb Question avec JS -->\n" +
                    "        "+ nbDifficile + " Question <img class=\"imgDif\" src=\"./data/imgDif/D.png\"> <!-- Avoir le nb Question avec JS -->\n" +
                    "    </p>\n" +
                    "    <div class=\"styleBut\" onclick=\"question1()\">Commencer</div>\n" +
                    "\n" +
                    "    <!--Eviter les bugs-->\n" +
                    "\t<div id='chrono'></div>\n" +
                    "\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>";

            BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/" + this.questionnaire.getNom() + this.nbOccurenceFichier +  "/index.html"));
            writer.write(html);

            writer.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Metode ecrireStyle
     * Cette métode permet l'écriture de style.css commun a tous les questionnaires
     * @return  Vrai si le fichier a pu être écrit, sinon faux
     */
    public boolean ecrireStyle()
    {
        try
        {
            String css = "";

            this.scanner = new Scanner(new FileReader(System.getProperty("user.dir") + "/out/production/SAE-31/docs/style.css"));

            while(scanner.hasNextLine())
            {
                css = css + scanner.nextLine()+"\n";
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/" + this.questionnaire.getNom() + this.nbOccurenceFichier + "/style.css"));
            writer.write(css);

            writer.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Metode ecrireImage
     * Cette métode permet la création des images nécéssaires au questionnaire
     * @return  Vrai si les fichiers ont pu être écrits, sinon faux
     */
    public boolean ecrireImage()
    {
        String[] tabImg = {"TF.png", "F.png", "M.png", "D.png"};
        String[] tabBtn = {"CheckVide.png", "CheckPlein.png", "RadVide.png", "RadPlein.png"};

        try
        {
            Path folderPath = Path.of(path + "/"  + this.questionnaire.getNom() + this.nbOccurenceFichier + "/data/imgDif/");
            Files.createDirectories(folderPath);

            folderPath = Path.of(path  + "/" +  this.questionnaire.getNom() + this.nbOccurenceFichier + "/data/imgBtn/");
            Files.createDirectories(folderPath);

            for(String img : tabImg)
            {

                //String newPath = System.getProperty("user.dir") + "/out/production/SAE-31/data/Images/imgDif/" + img;
                String newPath = "java/data/Images/imgDif/" + img;
                String newPath2 = path  + "/" +  this.questionnaire.getNom() + this.nbOccurenceFichier + "/data/imgDif/" + img;

                Files.copy(Path.of(newPath), Path.of(newPath2), StandardCopyOption.REPLACE_EXISTING);

            }

            for(String btn : tabBtn)
            {
                //String newPath = System.getProperty("user.dir") + "/out/production/SAE-31/data/Images/imgBtn/" + btn;
                String newPath = "java/data/Images/imgBtn/" + btn;
                String newPath2 = path + "/" + this.questionnaire.getNom() + this.nbOccurenceFichier + "/data/imgBtn/" + btn;

                Files.copy(Path.of(newPath), Path.of(newPath2), StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Metode ecrireJS
     * Cette métode permet l'écriture du script JS commun a tout les questionnaires
     * @return  Vrai si le fichier a pu être écrit, sinon faux
     */
    public void ecrireJS()
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/"+ this.questionnaire.getNom() + this.nbOccurenceFichier + "/script.js"));

            String delimiteur = "\n";

            // Initialiser le Scanner avec la chaîne de caractères
            scanner = new Scanner((communJS() + "\n" + questionsJS()));
            // Définir le délimiteur
            scanner.useDelimiter(delimiteur);

            // Lire les parties une par une
            while (scanner.hasNext())
            {
                String partie = scanner.next();
                writer.write(partie);
                writer.newLine();
                writer.flush();
            }

        }
        catch (Exception e) {e.printStackTrace();}
    }

    public String communJS()
    {
        int noteMax = 0;

        //Début tab completion
        String tabCompletion = "[";
        String lignes = "";
        String lignesReInit = "";
        String tabEliminations = "";
        int indice = 0;

        for (int i = 0; i < this.questionnaire.getLstQuestion().size(); i++)
        {
            tabCompletion = tabCompletion + "false,";
        }

        tabCompletion = tabCompletion.substring(0, tabCompletion.length() - 1);
        tabCompletion = tabCompletion + "]";

        for (Question q : this.questionnaire.getLstQuestion())
        {
            noteMax += q.getPoint();
        }

        //Fin tabCompletion

        //Debut tabSelection

        String tabSelection = "\n[\n[],\n[";

        for (Question q : this.questionnaire.getLstQuestion())
        {
            if (q instanceof QCM || q instanceof EliminationReponse)
            {
                if (q instanceof QCM)
                {
                    for (int i = 0; i < ((QCM) q).getNbReponses(); i++)
                    {
                        tabSelection = tabSelection + "false,";
                    }
                }
                else
                {
                    for (int i = 0; i < ((EliminationReponse) q).getHmReponses().size(); i++)
                    {
                        tabSelection = tabSelection + "false,";
                    }
                }
            }
            tabSelection = tabSelection + "],\n[";
        }

        tabSelection = tabSelection.substring(0, tabSelection.length() - 2);

        tabSelection = tabSelection + "];";


        //Fin tabSelection

        //Debut lignes
        int indiceLigne = 1;

        for(Question q : this.questionnaire.getLstQuestion())
        {

            if(q instanceof AssociationElement)
            {
                lignes += "let lignesQ" + indiceLigne + " = [";
                AssociationElement ae = (AssociationElement)(q);

                for ( String gauche : ae.getAssociations().keySet())
                {
                    lignes += "[[],[]],";
                }

                lignes = lignes + "];\n";

            }
            indiceLigne++;
        }

        indiceLigne = 1;

        for(Question q : this.questionnaire.getLstQuestion())
        {

            if(q instanceof AssociationElement)
            {
                lignesReInit += "lignesQ" + indiceLigne + " = [";
                AssociationElement ae = (AssociationElement)(q);

                for ( String gauche : ae.getAssociations().keySet())
                {
                    lignesReInit += "[[],[]],";
                }

                lignesReInit = lignesReInit + "];\n";

            }
            indiceLigne++;
        }

        //fin lignes

        //Debut elimination

        indice = 1;

        for (Question q : this.questionnaire.getLstQuestion())
        {
            if (q instanceof EliminationReponse)
            {
                tabEliminations = tabEliminations + "nbEliminationQ" + (indice) + " = [";
                for (int i = 0; i < ((EliminationReponse) q).getHmReponses().size(); i++)
                {
                    tabEliminations = tabEliminations + "false,";
                }
                tabEliminations = tabEliminations + "];\n";
            }
            indice++;
        }

        String tabEliminationsInit = "";
        if(tabEliminations.length() > 0 )
        {
            tabEliminationsInit = "let " + tabEliminations;
        }



        int nbTresFacile = 0;
        int nbFacile = 0;
        int nbMoyen = 0;
        int nbDifficile = 0;

        for (Question q : this.questionnaire.getLstQuestion())
        {
            switch (q.getDifficulte().getIndice()) {
                case 1 -> nbTresFacile++;
                case 2 -> nbFacile++;
                case 3 -> nbMoyen++;
                case 4 -> nbDifficile++;
            }
        }

        String listeNotions = "";
        for(Notion n : this.questionnaire.getLstNotions())
        {
            listeNotions = listeNotions + "<li> " + n.getNom() + " </li>\n";
        }

        String commun = "//Constantes et var communes\n" +
                "const nbQuestion = " + this.questionnaire.getLstQuestion().size() + ";\n" +
                "const estChronometrer = " + this.questionnaire.getChronoBool() + ";\n" +
                "const dureeTotal = " + questionnaire.getTempsEstimee() + "; //Secondes\n" +
                "const noteMax = " + noteMax + ";\n" +
                "\n" +
                "\n" +
                "//Init variables\n" +
                "let questionActuelleEstBon = false;\n" +
                "let questionActuelle = 1;\n" +
                "let completion = 0;\n" +
                "let totalPoints = 0;\n" +
                "let timer;\n" +
                "let tabCompletion = " + tabCompletion + "\n" +
                "\n" +
                "const imgTF =' <img class=\"imgDif\" src=\"./data/imgDif/TF.png\">';\n" +
                "const imgF  =' <img class=\"imgDif\" src=\"./data/imgDif/F.png\">';\n" +
                "const imgM  =' <img class=\"imgDif\" src=\"./data/imgDif/M.png\">';\n" +
                "const imgD  =' <img class=\"imgDif\" src=\"./data/imgDif/D.png\">';\n" +
                "\n" +
                "let tabSelections = " + tabSelection +
                "\n" +
                "//Pour le qcm à relier\n" +
                lignes +
                "\n" +
                "\n" +
                tabEliminationsInit +
                "\n" +
                "function resetVariables()\n" +
                "{\n" +
                "    questionActuelle = 1;\n" +
                "    completion = 0;\n" +
                "    timer;\n" +
                "    seconds = 0;\n" +
                "    totalPoints = 0;\n" +
                "    tabCompletion = " + tabCompletion + ";\n" +
                "\t" + tabEliminations +
                "\n" +
                "    docAfficher = false;\n" +
                "\n" +
                "\ttabSelections = " + tabSelection +
                "\n" +
                "\t" + lignesReInit + "\n" +
                "}\n" +
                "\n" +
                "function formatTime(s) {\n" +
                "    let minutes = Math.floor(s / 60);\n" +
                "    let remainingSeconds = s % 60;\n" +
                "    return `${minutes < 10 ? '0' : ''}${minutes}:${remainingSeconds < 10 ? '0' : ''}${remainingSeconds}`;\n" +
                "}\n" +
                "\n" +
                "function updateProgress() \n" +
                "{\n" +
                "    const progressBar = document.getElementById('progressBar');\n" +
                "    const questionsDone = document.getElementById('questionsDone');\n" +
                "\n" +
                "    const percentage = (completion / nbQuestion) * 100;\n" +
                "    progressBar.style.width = percentage + '%';\n" +
                "    progressBar.textContent = Math.round(percentage) + '%';\n" +
                "    questionsDone.textContent = completion;\n" +
                "}\n" +
                "\n" +
                "function questionSuivante()\n" +
                "{\n" +
                "    if(questionActuelle < nbQuestion)\n" +
                "    {\n" +
                "        questionActuelle ++;\n" +
                "    }\n" +
                "    updateProgress();\n" +
                "}\n" +
                "\n" +
                "function validerQuestion(bonnesRep, typeQuestion)\n" +
                "{\n" +
                "\tdocument.getElementById(\"valider\").classList.add(\"griser\");\n" +
                "    if(!tabCompletion[questionActuelle])\n" +
                "    {\n" +
                "        afficherReponse(bonnesRep, typeQuestion);\n" +
                "    }\n" +
                "\n" +
                "    if(!tabCompletion[questionActuelle])\n" +
                "    {\n" +
                "        tabCompletion[questionActuelle] = true;\n" +
                "        completion++;\n" +
                "    }\n" +
                "    \n" +
                "    updateProgress();\n" +
                "}\n" +
                "\n" +
                "function questionPrecedante()\n" +
                "{    \n" +
                "    if(questionActuelle > 1 && !estChronometrer)\n" +
                "    {\n" +
                "        questionActuelle --;\n" +
                "    }\n" +
                "\n" +
                "    updateProgress();\n" +
                "}\n" +
                "\n" +
                "function afficherReponse(bonnesRep, typeQuestion) //Bonnes Rep est le tableau qui contient les réponses ou vrai = bonne rep et faux = mauvaise\n" +
                "{\n" +
                "\n" +
                "    switch(typeQuestion)\n" +
                "    {\n" +
                "        case 'QCM' :\n" +
                "        {\n" +
                "            for(let i = 0; i < bonnesRep.length ; i++)\n" +
                "            {\n" +
                "\n" +
                "                if((!bonnesRep[i] && tabSelections[questionActuelle][i]) || (bonnesRep[i] && !tabSelections[questionActuelle][i]))\n" +
                "                {\n" +
                "                    document.getElementById(\"rep\" + (i+1)).classList.toggle('faux');\n" +
                "                }\n" +
                "        \n" +
                "                if(bonnesRep[i])\n" +
                "                {\n" +
                "                    document.getElementById(\"rep\" + (i+1)).classList.toggle('vrai');\n" +
                "                }\n" +
                "\n" +
                "            }\n" +
                "            break;\n" +
                "        }\n" +
                "        \n" +
                "        case 'EA' :\n" +
                "        {\n" +
                "            drawLines(bonnesRep, 'correction');\n" +
                "            break;\n" +
                "        }\n" +
                "\n" +
                "        case 'ELIMINATION' :\n" +
                "        {\n" +
                "            for(let i = 0; i < bonnesRep.length ; i++)\n" +
                "            {\n" +
                "\n" +
                "                if((!bonnesRep[i] && tabSelections[questionActuelle][i]) || (bonnesRep[i] && !tabSelections[questionActuelle][i]))\n" +
                "                {\n" +
                "                    document.getElementById(\"rep\" + (i+1)).classList.toggle('faux');\n" +
                "                }\n" +
                "        \n" +
                "                if(bonnesRep[i])\n" +
                "                {\n" +
                "                    document.getElementById(\"rep\" + (i+1)).classList.toggle('vrai');\n" +
                "                }\n" +
                "            }\n" +
                "            break;\n" +
                "        }\n" +
                "        \n" +
                "    }\n" +
                "\n" +
                "}\n" +
                "\n" +
                "\n" +
                "function showFeedBack(estBon, nbPoints, explications)\n" +
                "{\n" +
                "\n" +
                "\n" +
                "    if(tabCompletion[questionActuelle])\n" +
                "    {\n" +
                "        popupI.classList.remove('hidden');\n" +
                "        popupI.classList.add('show');\n" +
                "        popup.classList.remove('hiddenP');\n" +
                "        popup.classList.add('popup');\n" +
                "\n" +
                "\n" +
                "        let txtFeedBack = document.getElementById('textFeedBack');\n" +
                "        let reponseBonne = document.getElementById('estReponseBonne')\n" +
                "\n" +
                "        if(estBon)\n" +
                "        {\n" +
                "            reponseBonne.classList.add('reponseBonne');\n" +
                "            reponseBonne.innerHTML = 'Bonne réponse !';\n" +
                "            txtFeedBack.innerHTML = 'Nombre de points de la question : ' + nbPoints + \"<br>Votre note : \" + totalPoints + \" / \" + noteMax + \"<br>Explications : <br>\" + explications;\n" +
                "\n" +
                "        }else{\n" +
                "            reponseBonne.classList.add('reponseFausse');\n" +
                "            reponseBonne.innerHTML = 'Mauvaise réponse !';\n" +
                "            txtFeedBack.innerHTML = 'Nombre de points de la question : ' + nbPoints + \"<br>Votre note : \" + totalPoints + \" / \" + noteMax + \"<br>Explications : <br>\" + explications;\n" +
                "        }\n" +
                "\n" +
                "\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "\n" +
                "function cacheFeedBack()\n" +
                "{\n" +
                "    popupI.classList.remove('show');\n" +
                "    popupI.classList.add('hidden');\n" +
                "    popup.classList.remove('popup');\n" +
                "    popup.classList.add('hiddenP');\n" +
                "}\n" +
                "\n" +
                "\n" +
                "function changerSelections()\n" +
                "{\n" +
                "    for(let i = 0 ; i < tabSelections[questionActuelle].length ; i++)\n" +
                "    {\n" +
                "        if(tabSelections[questionActuelle][i])\n" +
                "        {\n" +
                "            document.getElementById(\"rep\" + (i+1)).classList.toggle('selected');\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "\n" +
                "function clicRep(index, typeQuestion)\n"+
                "{\n"+
                "switch(typeQuestion)\n"+
                "{\n"+
                "    case \"QCM\" :\n"+
                "    {\n"+
                "        if(!tabCompletion[questionActuelle])\n"+
                "        {\n"+
                "            tabSelections[questionActuelle][index] = !tabSelections[questionActuelle][index];\n"+
                "\n"+
                "            document.getElementById(\"rep\" + (index+1)).classList.toggle('selected');\n"+
                "\n"+
                "            if(tabSelections[questionActuelle][index]){\n"+
                "                document.getElementById(\"img\" + (index+1)).src = \"./data/imgBtn/CheckPlein.png\"\n"+
                "            }\n"+
                "            else{\n"+
                "                document.getElementById(\"img\" + (index+1)).src = \"./data/imgBtn/CheckVide.png\"\n"+
                "            }\n"+
                "        }\n"+
                "        break;\n"+
                "    }\n"+
                "\n"+
                "    case \"vrai-faux\" :\n"+
                "    {\n"+
                "        if(!tabCompletion[questionActuelle])\n"+
                "        {\n"+
                "            for(let i = 1 ; document.getElementById(\"rep\" + i) != null ; i++)\n"+
                "            {\n"+
                "                if(document.getElementById(\"rep\" + i).classList.contains('selected'))\n"+
                "                {\n"+
                "                    document.getElementById(\"rep\" + i).classList.remove('selected');\n"+
                "                }\n"+
                "            }\n"+
                "\n"+
                "            for(let i = 0 ; i < tabSelections[questionActuelle].length ; i++)\n"+
                "            {\n"+
                "                if(i != index)\n"+
                "                {\n"+
                "                    tabSelections[questionActuelle][i] = false;\n"+
                "                    document.getElementById(\"img\" + (i+1)).src = \"./data/imgBtn/RadVide.png\"\n"+
                "                }else{\n"+
                "                    tabSelections[questionActuelle][i] = true;\n"+
                "                    document.getElementById(\"img\" + (i+1)).src = \"./data/imgBtn/RadPlein.png\"\n"+
                "                }\n"+
                "            }\n"+
                "            document.getElementById(\"rep\" + (index+1)).classList.add('selected');\n"+
                "        }\n"+
                "        break;\n"+
                "    }\n"+
                "    case \"elimination\" :\n"+
                "    {\n"+
                "        if(!tabCompletion[questionActuelle])\n"+
                "        {\n"+
                "    const elementsDiv = document.querySelectorAll(\".reponseBox\");\n"+
                "\n"+
                "            elementsDiv.forEach(element => {\n"+
                "\n"+
                "            if(element.classList.contains('selected') && !element.classList.contains('eliminer'))\n"+
                "            {\n"+
                "                element.classList.remove('selected'); // Retire explicitement la classe\n"+
                "            }\n"+
                "    });\n"+
                "\n"+
                "            let tmp = document.getElementById(\"rep\" + (index+1));\n"+
                "\n"+
                "           if(!tmp.classList.contains('eliminer'))\n"+
                "            {\n"+
                "                for(let i = 0 ; i < tabSelections[questionActuelle].length ; i++)\n"+
                "                {"+
                "                    if(tabSelections[questionActuelle][i] == true)\n"+
                "                    {\n"+
                "                        tabSelections[questionActuelle][i] = false;\n"+
                "                            document.getElementById(\"img\" + (i+1)).src = \"./data/imgBtn/RadVide.png\";\n"+
                "                        }\n"+
                "                    }\n"+
                "\n"+
                "                    tabSelections[questionActuelle][index] = true;\n"+
                "                    tmp.classList.toggle('selected');\n"+
                "                    document.getElementById(\"img\" + (index+1)).src = \"./data/imgBtn/RadPlein.png\";\n"+
                "                }\n"+
                "            }\n"+
                "            break;\n"+
                "        }\n"+
                "    }\n"+
                "}\n"+
                "function finQuestionnaire()\n" +
                "{\n" +
                "    if (completion == nbQuestion || confirm(\"Vous n'avez pas répondu à toutes les questions, êtes-vous sûr de vouloir terminer le questionnaire ?\")) \n" +
                "    {\n" +
                "        resultat();\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "\n" +
                "function index()\n" +
                "{\n" +
                "    resetVariables();\n" +
                "    completion = 0;\n" +
                "    questionActuelle = 1;\n" +
                "    \n" +
                "    let contenuPage =`<!DOCTYPE html>\n" +
                "                        <html lang=\"en\">\n" +
                "                        <head>\n" +
                "                            <meta charset=\"UTF-8\">\n" +
                "                            <title>QCMBuilder</title>\n" +
                "                            <link href=\"style.css\" rel=\"stylesheet\">\n" +
                "                            <script src=\"./script.js\"></script>\n" +
                "                        </head>\n" +
                "                        <body>\n" +
                "                            <h1>Bienvenue dans un Questionnaire !</h1>\n" +
                "                            <h2>"+ questionnaire.getNom() +"<!-- Avoir la matiere avec JS --></h2>\n" +
                "                            <h2>Temps pour finir : " + questionnaire.getTempsEstimee() + " Secondes<!-- Avoir la durér avec JS --></h2>\n" +
                "                            <h2>Sur la matière : " + questionnaire.getRessource().getNom() + " <!-- Avoir la matiere avec JS --></h2>\n" +
                "                            <h2>Sur les notions suivante :</h2>\n" +
                "                            <ul>\n" +
                listeNotions +
                "                            </ul> \n" +
                "                            <h2> Qui contient :</h2>\n" +
                "                            <p>\n" +
                "                                " + nbTresFacile + " Question `+imgTF+` <br> <!-- Avoir le nb Question avec JS -->\n" +
                "                                " + nbFacile + " Question `+imgF+` <br> <!-- Avoir le nb Question avec JS -->\n" +
                "                                " + nbMoyen + " Question `+imgM+` <br> <!-- Avoir le nb Question avec JS -->\n" +
                "                                " + nbDifficile + " Question `+imgD+` <!-- Avoir le nb Question avec JS -->\n" +
                "                            </p>\n" +
                "                            <div class=\"styleBut\" onclick=\"question1()\">Commencer</div>\n" +
                "                                <div id='chrono'></div>\n" +
                "                                <div id='feedBack'></div>\n" +
                "                                <div id='popup'></div>\n" +
                "                                <div id='popupI'></div>\n" +
                "                                <div id='closePopupBtn'></div>\n" +
                "                        </body>\n" +
                "                        </html>`;\n" +
                "\n" +
                "    creerHtml(contenuPage);\n" +
                "}\n" +
                "\n" +
                "\n" +
                "\n" +
                "function resultat()\n" +
                "{    \n" +
                "\n" +
                "    let contenuPage =`<!DOCTYPE html>\n" +
                "                    <html lang=\"en\">\n" +
                "                    <head>\n" +
                "                        <meta charset=\"UTF-8\">\n" +
                "                        <title>QCMBuilder</title>\n" +
                "                        <link href=\"style.css\" rel=\"stylesheet\">\n" +
                "                        <script src=\"./script.js\"></script>\n" +
                "                    </head>\n" +
                "                    <body>\n" +
                "                        <h1> Questionnaire Terminé ! </h1>\n" +
                "                        <h2> Ressource : " + questionnaire.getRessource().getNom() + "</h2>\n" +
                "                        <h2> Nombre de questions : `+ nbQuestion +` dont " + nbTresFacile + " `+ imgTF +` , " + nbFacile + " ` + imgF +` , " + nbMoyen + " `+ imgM +` et " + nbDifficile + " `+ imgD + `</h2>\n" +
                "                        <h2> Score global : `+ totalPoints+` / `+ noteMax +`</h2>\n" +
                "                        <br>\n" +
                "                        <div class=\"styleBut\" onclick=\"index()\">Revenir a l'index du questionnaire</div>\n" +
                "                        <div id='chrono'></div>\n" +
                "                        <div id='feedBack'></div>\n" +
                "                        <div id='popup'></div>\n" +
                "                        <div id='popupI'></div>\n" +
                "                        <div id='closePopupBtn'></div>\n" +
                "                    </body>\n" +
                "                    </html>`;\n" +
                "\n" +
                "    creerHtml(contenuPage);\n" +
                "}\n" +
                "\n" +
                "\n" +
                "function creerHtml(html)\n" +
                "{\n" +
                "    document.body.innerHTML = html;\n" +
                "}\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "function drawLines(lignes, affichage) \n" +
                "{\n" +
                "\tconst canvas = document.getElementById('canvas');\n" +
                "if(canvas != null){" +
                "\tconst ctx = canvas.getContext('2d');\n" +
                "\n" +
                "\n" +
                "\t// Parcourir les éléments et tracer les lignes\n" +
                "\n" +
                "\tfor(let index = 0 ; index < lignes.length ; index++) \n" +
                "\t{\n" +
                "\t\tfor(let j = 0; j < lignes[index].length ; j++)\n" +
                "\t\t{\n" +
                "\t\t\tfor(let v = 0 ; v < 2 ; v++)\n" +
                "\t\t\t{\n" +
                "\t\t\t\tif ( j < lignes.length && lignes[index][j][v] != null) \n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\tlet leftElement = document.getElementById(\"rep\" + ((lignes[index][j][0])+1));\n" +
                "\t\t\t\t\tlet rightElement = document.getElementById(\"rep\" + ((lignes[index][j][1])+1));\n" +
                "\t\n" +
                "\n" +
                "\t\t\t\t\tconst leftRect = leftElement.getBoundingClientRect();\n" +
                "\t\t\t\t\tconst rightRect = rightElement.getBoundingClientRect();\n" +
                "\t\t\n" +
                "\t\t\t\t\tconst canvasRect = canvas.getBoundingClientRect();\n" +
                "\t\t\n" +
                "\t\t\t\t\tconst x1 = leftRect.right - canvasRect.left; // Coordonnée X de l'élément gauche (bord droit)\n" +
                "\t\t\t\t\tconst y1 = leftRect.top + leftRect.height / 2 - canvasRect.top; // Milieu vertical de l'élément gauche\n" +
                "\t\t\n" +
                "\t\t\t\t\tconst x2 = rightRect.left - canvasRect.left; // Coordonnée X de l'élément droit (bord gauche)\n" +
                "\t\t\t\t\tconst y2 = rightRect.top + rightRect.height / 2 - canvasRect.top; // Milieu vertical de l'élément droit\n" +
                "\t\t\n" +
                "\t\t\t\t\t// Tracer une ligne entre les deux éléments\n" +
                "\t\t\t\t\tctx.beginPath();\n" +
                "\t\t\t\t\tctx.moveTo(x1, y1);\n" +
                "\t\t\t\t\tctx.lineTo(x2, y2);\n" +
                "\t\n" +
                "\t\t\t\t\tif(affichage == 'correction')\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\tctx.strokeStyle = \"green\";\n" +
                "\t\t\t\t\t}else if(affichage == 'erreurs')\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\tctx.strokeStyle = \"red\";\n" +
                "\t\t\t\t\t}else{\n" +
                "\t\t\t\t\t\tctx.strokeStyle = \"black\";\n" +
                "\t\t\t\t\t}\n" +
                "\t\n" +
                "\t\t\t\t\tctx.lineWidth = 4;\n" +
                "\t\t\t\t\tctx.stroke();\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}}\n" +
                "}"+
                "\n" +
                "\n" +
                "function verifierReponses(tabBonnesRep, nbPoints)\n" +
                "{\n" +
                "\n" +
                "    let estBon = true;\n" +
                "\n" +
                "    for(let i = 0 ; i < tabBonnesRep.length ; i++)\n" +
                "    {\n" +
                "\n" +
                "        if(tabBonnesRep[i] != tabSelections[questionActuelle][i])\n" +
                "        {\n" +
                "            estBon = false;\n" +
                "            break;\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    if(estBon && !tabCompletion[questionActuelle])\n" +
                "    {\n" +
                "        totalPoints += nbPoints;\n" +
                "        }\n" +
                "\n" +
                "    questionActuelleEstBon = estBon;\n" +
                "\n" +
                "    return estBon;\n" +
                "}\n";

        return commun;
    }



    /**
     * Methode questionsJS
     * Cette métode permet l'écriture de script JS pour chaques questions
     * @return  Vrai si le fichier a pu être écrit, sinon faux
     */
    public String questionsJS()
    {
        String res = "";

        for (Question q : questionnaire.getLstQuestion())
        {
            if (q instanceof QCM)
            {
                String reponses = "";
                String bonnesRep = "[";
                String onClicFonctions = "";
                String questionSuivante = "";
                String questionPrecedente = "";
                String txtChoixRep = "";

                if ((questionnaire.getLstQuestion().indexOf(q) == 0))
                {
                    questionPrecedente = "question1()";
                }
                else
                {
                    questionPrecedente = "question" + ((questionnaire.getLstQuestion().indexOf(q))) + "()";
                }

                if ((questionnaire.getLstQuestion().indexOf(q) == questionnaire.getLstQuestion().size() - 1))
                {
                    questionSuivante = "finQuestionnaire()";
                }
                else
                {
                    questionSuivante = "question" + ((questionnaire.getLstQuestion().indexOf(q)) + 2) + "()";
                }

                String fichierComplementaires = "";
                int numFichierComp = 1;

                for(String liens : q.getListeFichiers())
                {
                    if(liens.substring(liens.length()-3) == "png" || liens.substring(liens.length()-3) == "jpg" || liens.substring(liens.length()-4) == "jpeg")
                    {
                        fichierComplementaires = fichierComplementaires + "<img class=\"imgQuestion\" src=\"java/data/" + q.getNotion().getRessourceAssociee().getNom() + "/" + q.getNotion().getNom() + "/" + q.getId() + "/" + liens + "\" draggable=\"false\">\n";
                    }
                    else
                    {
                        fichierComplementaires = fichierComplementaires + "<a href=\"./data/Question " + q.getId() + "/Compléments/" + liens + "\" download=\"" + liens + "\" target=\"_blank\"> Télécharger le fichier complémentaire numéro " + numFichierComp +"</a>\n";
                    }
                    numFichierComp++;
                }


                int i = 1;

                for (String reponse : ((QCM) q).getReponses().keySet())
                {

                    if (((QCM) q).getReponses().get(reponse))
                    {
                        bonnesRep = bonnesRep + "true,";
                    }
                    else
                    {
                        bonnesRep = bonnesRep + "false,";
                    }
                    if(!((QCM)q).estVraiouFaux())
                    {
                        reponses = reponses + "<div class=\"reponseBox\" id=\"rep" + i + "\" ><img id=\"img" + i + "\" class=\"imgBtn\" src=\"./data/imgBtn/CheckVide.png\">'" + reponse + "'</div>\n";
                        onClicFonctions = onClicFonctions + "document.getElementById(\"rep" + i + "\").onclick = function() { clicRep(" + (i - 1) + ", \"QCM\") };\n";
                    }
                    else
                    {
                        reponses = reponses + "<div class=\"reponseBox\" id=\"rep" + i + "\" ><img id=\"img" + i + "\" class=\"imgBtn\" src=\"./data/imgBtn/RadVide.png\">'" + reponse + "'</div>\n";
                        onClicFonctions = onClicFonctions + "document.getElementById(\"rep" + i + "\").onclick = function() { clicRep(" + (i - 1) + ", \"vrai-faux\") };\n";
                    }
                    i++;
                }

                bonnesRep = bonnesRep + "];";

                if (!((QCM)q).estVraiouFaux())
                {
                    txtChoixRep = "QCM";
                }
                else
                {
                    txtChoixRep = "Question à choix unique";
                }

                String imageEnonce = null;

                System.out.println(q.getImageChemin() + "Image chemin");

                if(!q.getImageChemin().equals("null") && !q.getImageChemin().isEmpty())
                {
                    imageEnonce = "<img class=\"imgQuestion\" src=\"./data/Question " + q.getId() + "/Compléments/" + q.getImageChemin() +"\">\n";
                }

                res += "\n\nfunction question" + (questionnaire.getLstQuestion().indexOf(q) + 1) + "()\n" +
                        "{\n" +
                        "    //Variables\n" +
                        "    let bonnesRep = " + bonnesRep + "\n" +
                        "    const difficulte = \"" + q.getDifficulte().getNom() + "\";\n" +
                        "    const tempsDeReponse = " + q.getTemps() + ";\n" +
                        "    let points = " + q.getPoint() + ";\n" +
                        "    let texteExplications = '" + q.getExplicationText() + "';\n" +
                        "    let notion = '" + q.getNotion() + "';\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "    let contenuPage =`<!DOCTYPE html>\n" +
                        "                    <html lang=\"en\">\n" +
                        "                    <head>\n" +
                        "                        <meta charset=\"UTF-8\">\n" +
                        "                        <title>QCMBuilder</title>\n" +
                        "                        <link href=\"style.css\" rel=\"stylesheet\">\n" +
                        "                    </head>\n" +
                        "                    <body>\n" +
                        "                        <div id=\"popup\" class=\"hiddenP\">\n" +
                        "                            <div id=\"popupI\" class=\"hidden\">\n" +
                        "                                <span id=\"closePopupBtn\" class=\"close\">&times;</span>\n" +
                        "                                <h2 id=\"estReponseBonne\"></h2>\n" +
                        "                                <p id=\"textFeedBack\"></p>\n" +
                        "                            </div>\n" +
                        "                        </div>\n" +
                        "\n" +
                        "\n" +
                        "                   <header>\n"+
                        "                        <div class=\"compteurs\">\n" +
                        "                            <div class=\"timer, compteurs-div\" id=\"chrono\">00:00</div>\n" +
                        "                            <div class=\"compteurs-div\">Questions traitées : <span id=\"questionsDone\">0</span>/`+ nbQuestion +`</div>\n" +
                        "                        </div>\n" +
                        "                        <div class=\"progress-container\">\n" +
                        "                            <div class=\"progress-bar\" id=\"progressBar\">0%</div>\n" +
                        "                        </div>\n" +
                        "                   </header>\n"+
                        "\n" +
                        "                        <script src=\"./script.js\"></script>\n" +
                        "                   <div class=\"pageQuestion\">\n" +
                        "                        <h1> Question `+ questionActuelle +` : </h1>\n" +
                        "                        <h2> Notion : `+ notion +`  </h2>\n" +
                        "                        <h2> Difficulté : `+ difficulte +`  </h2>\n" +
                        "\n" +
                        "                        <br>\n" +
                        "                        <!-- Pour un QCM -->\n" +
                        "\n" +
                        "                        <h2> Type : " + txtChoixRep + "</h2>" +
                                                imageEnonce +
                        "                        <h3> " + q.getEnonceText() + " </h3>\n" +
                        "                        " + fichierComplementaires                          +
                        "                        <div id=\"zoneRep\">\n" +
                        "                             " + reponses + "\n" +
                        "                        </div>\n" +
                        "                   </div>\n" +
                        "                        <footer>\n" +
                        "                            <nav>\n" +
                        "                                <div class=\"styleBut\" id=\"btnPreced\">Précédent</div>\n" +
                        "                                <div class=\"styleBut\" id=\"valider\">Valider</div>\n" +
                        "                                <div class=\"styleBut\" id=\"feedBack\">Feedback</div>\n" +
                        "                                <div class=\"styleBut\" id=\"btnSuiv\">Suivant</div>\n" +
                        "                            </nav>\n" +
                        "                        </footer>\n" +
                        "                    </body>\n" +
                        "                    </html>`;\n" +
                        "\n" +
                        "    creerHtml(contenuPage);\n" +
                        "\n" +
                        "    updateProgress();\n" +
                        "\n" +
                        "    changerSelections();\n" +
                        "\n" +
                        "    //Génération du popup\n" +
                        "    const popup = document.getElementById('popup');\n" +
                        "    const closePopupBtn = document.getElementById('closePopupBtn');\n" +
                        "    const popupI = document.getElementById('popupI');\n" +
                        "    const feedBackBtn = document.getElementById('feedBack');\n" +
                        "\n" +
                        "\n" +
                        "    feedBackBtn.addEventListener('click', () => {\n" +
                        "        showFeedBack(questionActuelleEstBon,points,texteExplications);\n" +
                        "    });\n" +
                        "    \n" +
                        "    // Cacher le popup\n" +
                        "    closePopupBtn.addEventListener('click', () => {\n" +
                        "        cacheFeedBack();\n" +
                        "    });\n" +
                        "    \n" +
                        "    // Optionnel : Fermer le popup en cliquant en dehors\n" +
                        "    popup.addEventListener('click', (event) => \n" +
                        "    {\n" +
                        "        if (event.target === popup) {\n" +
                        "            cacheFeedBack();\n" +
                        "        }\n" +
                        "    });\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "    //Gestion du timer\n" +
                        "    if(estChronometrer)\n" +
                        "    {\n" +
                        "        startTimerQuestion(tempsDeReponse);\n" +
                        "    }else{\n" +
                        "        document.getElementById('chrono').textContent = \"Temps estimé : \" + tempsDeReponse;\n" +
                        "    }\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "    //Timer pour les questions\n" +
                        "    function startTimerQuestion(tempsDeReponse) \n" +
                        "    {\n" +
                        "        if(estChronometrer)\n" +
                        "        {\n" +
                        "            isRunning = true;\n" +
                        "            let tps = tempsDeReponse;\n" +
                        "            timer = setInterval(() => {\n" +
                        "                tps--;\n" +
                        "                if(document.getElementById('chrono') != null)\n" +
                        "                    document.getElementById('chrono').textContent = \"Compte à rebours : \" + formatTime(tps) + \" secondes\";\n" +
                        "                if(tps <= 0)\n" +
                        "                {\n" +
                        "                    stopTimerQuestion();\n" +
                        "                    verifierReponses(bonnesRep, points)\n" +
                        "                    validerQuestion(bonnesRep, 'QCM');\n" +
                        "                    showFeedBack(questionActuelleEstBon, points, texteExplications);\n" +
                        "                }\n" +
                        "            }, 1000);\n" +
                        "        }\n" +
                        "    }\n" +
                        "\n" +
                        "\n" +
                        "    // Fonction pour arrêter le chronomètre\n" +
                        "    function stopTimerQuestion() \n" +
                        "    {\n" +
                        "        if(estChronometrer)\n" +
                        "        {\n" +
                        "            isRunning = false;\n" +
                        "            clearInterval(timer);\n" +
                        "        }\n" +
                        "    }\n" +
                        "\n" +
                        "\n" +
                        "    if(tabCompletion[questionActuelle])\n" +
                        "    {\n" +
                        "        \t\tdocument.getElementById(\"valider\").classList.add(\"griser\");\n" +
                        "        afficherReponse(bonnesRep, 'QCM');\n" +
                        "    }\n" +
                        "\n" +
                        "    // Mettre à jour les événements de clic\n" +
                        "\n" +
                        onClicFonctions + "\n" +
                        "    \n" +
                        "    document.getElementById(\"btnPreced\").onclick = function() {if(!estChronometrer){questionPrecedante(), " + questionPrecedente + "}};\n" +
                        "    document.getElementById(\"btnSuiv\").onclick = function() {if(estChronometrer){if(tabCompletion[questionActuelle]){questionSuivante(), " + questionSuivante + "}}else{questionSuivante(), " + questionSuivante + "}};\n" +
                        "\n" +
                        "    document.getElementById(\"valider\").onclick = function() \n" +
                        "    {\n" +
                        "        if(!tabCompletion[questionActuelle])\n" +
                        "        {\n" +
                        "            verifierReponses(bonnesRep, points)\n" +
                        "            validerQuestion(bonnesRep, 'QCM');\n" +
                        "            showFeedBack(questionActuelleEstBon ,points, texteExplications);\n" +
                        "            stopTimerQuestion();\n" +
                        "        }\n" +
                        "    };\n" +
                        "}";
            }
            else if (q instanceof AssociationElement)
            {
                AssociationElement ae = (AssociationElement)(q);
                String clickDetection = "";

                String questionSuivante = "";
                String questionPrecedente = "";

                if ((questionnaire.getLstQuestion().indexOf(q) == 0))
                {
                    questionPrecedente = "question1()";
                }
                else
                {
                    questionPrecedente = "question" + ((questionnaire.getLstQuestion().indexOf(q))) + "()";
                }

                if ((questionnaire.getLstQuestion().indexOf(q) == questionnaire.getLstQuestion().size() - 1))
                {
                    questionSuivante = "finQuestionnaire()";
                } else {
                    questionSuivante = "question" + ((questionnaire.getLstQuestion().indexOf(q)) + 2) + "()";
                }


                for(int i = 0 ; i < ae.getAssociations().size()*2 ; i+=2)
                {
                    clickDetection += "        document.getElementById(\"rep" + (i+1) + "\").onclick = function() { selection(leftElements, " + i + ") };\n";

                    clickDetection += "        document.getElementById(\"rep" + (i+2) + "\").onclick = function() { selection(rightElements, " + (i+1) + ") };\n";

                }

                String bonnesRep = "[";

                String[][][] tabRep = new String[ae.getAssociations().size()][2][2];
                String[] tabRep2 = new String[ae.getAssociations().size()*2];

                ArrayList<String> random = new ArrayList<>(ae.getAssociations().keySet());

                Collections.shuffle(random);

                for(int i = 0; i < random.size() ; i++)
                {
                    tabRep[i][0][0]= random.get(i);
                    tabRep[i][0][1]= ae.getAssociations().get(random.get(i));
                }

                Collections.shuffle(random);

                for(int i = 0; i < random.size() ; i++)
                {
                    tabRep[i][1][1]= ae.getAssociations().get(random.get(i));
                    tabRep[i][1][0]= random.get(i);
                }

                for(int i = 0 ; i < tabRep.length; i++)
                {
                    tabRep2[i*2]   = tabRep[i][0][0];
                    tabRep2[(i*2)+1] = tabRep[i][1][0];
                }

                for(int i = 0 ; i < tabRep.length ; i++)
                {
                    bonnesRep += "[";

                    if(!tabRep[i][0][0].equals(tabRep[i][1][0]) || !tabRep[i][0][1].equals(tabRep[i][1][1])) {
                        for (int j = 0; j < tabRep.length; j++) {
                            if (tabRep[i][0][0].equals(tabRep[j][1][0]) && tabRep[i][0][1].equals(tabRep[j][1][1])) {
                                bonnesRep += "[" + (i * 2) + "," + (j * 2 + 1) + "],[" + (i * 2) + "," + (j * 2 + 1) + "]";

                            }
                        }
                    }
                    else
                    {
                        bonnesRep += "[" + (i*2+1) + "," + (i*2) + "],[" + (i*2+1)  + "," + (i*2) + "]";
                    }
                    bonnesRep += "],";

                }
                bonnesRep += "]";

                String imageEnonce = null;

                if(!q.getImageChemin().equals(null) && !q.getImageChemin().isEmpty())
                {
                    imageEnonce = "<img class=\"imgQuestion\" src=\"./data/Question " + q.getId() + "/Compléments/" + q.getImageChemin() +"\">\n";
                }



                res += "\n\nasync function question" + (questionnaire.getLstQuestion().indexOf(q) + 1) + "()\n" +
                        "{\n" +
                        "    //Variables\n" +
                        "    let reponses = " + bonnesRep + "\n" +
                        "                    \n" +
                        "                \n" +
                        "    const difficulte = '"+ q.getDifficulte().getNom() +"';\n" +
                        "    const tempsDeReponse = "+ q.getTemps() +";\n" +
                        "    let points = " + q.getPoint() +";\n" +
                        "    let texteExplications = '" + q.getExplicationText() + "';\n" +
                        "    let notion = '" + q.getNotion().getNom() + "';\n" +
                        "\n" +
                        "    \n" +
                        "\n" +
                        "\n" +
                        "    let contenuPage =`<!DOCTYPE html>\n" +
                        "                    <html lang=\"en\">\n" +
                        "                    <head>\n" +
                        "                        <meta charset=\"UTF-8\">\n" +
                        "                        <title>QCMBuilder</title>\n" +
                        "                        <link href=\"style.css\" rel=\"stylesheet\">\n" +
                        "                    </head>\n" +
                        "                    <body>\n" +
                        "                        <div id=\"popup\" class=\"hiddenP\">\n" +
                        "                            <div id=\"popupI\" class=\"hidden\">\n" +
                        "                                <span id=\"closePopupBtn\" class=\"close\">&times;</span>\n" +
                        "                                <h2 id=\"estReponseBonne\"></h2>\n" +
                        "                                <p id=\"textFeedBack\"></p>\n" +
                        "                            </div>\n" +
                        "                        </div>\n" +
                        "\n" +
                        "\n" +
                        "                    <header>\n"+
                        "                        <div class=\"compteurs\">\n" +
                        "                            <div class=\"timer, compteurs-div\" id=\"chrono\">00:00</div>\n" +
                        "                            <div class=\"compteurs-div\">Questions traitées : <span id=\"questionsDone\">0</span>/`+ nbQuestion +`</div>\n" +
                        "                        </div>\n" +
                        "                        <div class=\"progress-container\">\n" +
                        "                            <div class=\"progress-bar\" id=\"progressBar\">0%</div>\n" +
                        "                        </div>\n" +
                        "                   </header>\n"+
                        "                        <script src=\"./script.js\"></script>\n" +
                        "                   <div class=\"pageQuestion\">\n" +
                        "                        <h1> Question `+ questionActuelle +` : </h1>\n" +
                        "                        <h2> Notion : `+ notion +` </h2>\n" +
                        "                        <h2> Difficulté : `+ difficulte +`  </h2>\n" +
                        "\n" +
                        "                        <br>\n" +
                        "                        <!-- Pour un QCM -->\n" +
                        "                        <h2> Type : Association d'éléments ( Un seul élément à gauche pour un seul à droite ) </h2>\n" +
                        imageEnonce + "\n" +
                        "                        <h3> " + q.getEnonceText() + " </h3>\n" +
                        "\n" +
                        "                        <div id=\"zoneRepAssos\">\n" +
                        "                            <div class=\"casesInternes\" id=\"repGauche\">\n";


                int indice = 1;
                for (int i = 0 ; i < tabRep.length ; i++)
                {

                    res +="<div class=\"reponseBoxAssos\" id=\"rep"+indice+"\">"+ tabRep[i][0][0] +"</div>\n";
                    indice+=2;
                }

                res += "                            </div>\n" +
                        "                            <div><canvas id=\"canvas\"></canvas></div>\n" +
                        "                            <div class=\"casesInternes\" id=\"repDroite\">\n";

                indice = 2;
                for (int i = 0 ; i < tabRep.length ; i++)
                {
                    res +="<div class=\"reponseBoxAssos\" id=\"rep"+indice+"\">"+tabRep[i][1][1]+"</div>\n";
                    indice+=2;
                }

                res +="                            </div>\n" +
                        "                        </div>\n" +
                        "\n" +
                        "\n" +
                        "                        </div>\n"+
                        "                   </div>\n" +
                        "                        <footer>\n" +
                        "                            <nav>\n" +
                        "                                <div class=\"styleBut\" id=\"btnPreced\">Précédent</div>\n" +
                        "                                <div class=\"styleBut\" id=\"valider\">Valider</div>\n" +
                        "                                <div class=\"styleBut\" id=\"feedBack\">Feedback</div>\n" +
                        "                                <div class=\"styleBut\" id=\"btnSuiv\">Suivant</div>\n" +
                        "                            </nav>\n" +
                        "                        </footer>\n" +
                        "                    </body>\n" +
                        "                    </html>`;\n" +
                        "\n" +
                        "    await creerHtml(contenuPage);\n" +
                        "                    \n" +
                        "    //Génération du popup\n" +
                        "    const popup = document.getElementById('popup');\n" +
                        "    const closePopupBtn = document.getElementById('closePopupBtn');\n" +
                        "    const popupI = document.getElementById('popupI');\n" +
                        "    const feedBackBtn = document.getElementById('feedBack');\n" +
                        "\n" +
                        "\n" +
                        "    feedBackBtn.addEventListener('click', () => {\n" +
                        "        showFeedBack(questionActuelleEstBon,points,texteExplications);\n" +
                        "    });\n" +
                        "    \n" +
                        "    // Cacher le popup\n" +
                        "    closePopupBtn.addEventListener('click', () => {\n" +
                        "        cacheFeedBack();\n" +
                        "    });\n" +
                        "    \n" +
                        "    // Optionnel : Fermer le popup en cliquant en dehors\n" +
                        "    popup.addEventListener('click', (event) => \n" +
                        "    {\n" +
                        "        if (event.target === popup) {\n" +
                        "            cacheFeedBack();\n" +
                        "        }\n" +
                        "    });\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "    if(tabCompletion[questionActuelle])\n" +
                        "    {\n" +
                        "\t\tdocument.getElementById(\"valider\").classList.add(\"griser\");\n" +
                        "        afficherReponse(reponses, 'EA');\n" +
                        "    }\n" +
                        "\n" +
                        "\n" +
                        "    //Gestion du timer\n" +
                        "    if(estChronometrer)\n" +
                        "    {\n" +
                        "        startTimerQuestion(tempsDeReponse);\n" +
                        "    }else{\n" +
                        "        document.getElementById('chrono').textContent = \"Temps estimé : \" + tempsDeReponse;\n" +
                        "    }\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "    //Timer pour les questions\n" +
                        "    function startTimerQuestion(tempsDeReponse) {\n" +
                        "        isRunning = true;\n" +
                        "        let tps = tempsDeReponse;\n" +
                        "        timer = setInterval(() => {\n" +
                        "            tps--;\n" +
                        "            if(document.getElementById('chrono') != null)\n" +
                        "                document.getElementById('chrono').textContent = \"Compte à rebours : \" + formatTime(tps) + \" secondes\";\n" +
                        "            if(tps <= 0)\n" +
                        "            {\n" +
                        "                stopTimerQuestion();\n" +
                        "                if(!tabCompletion[questionActuelle])\n" +
                        "                {\n" +
                        "                    drawLines(lignesQ4, 'erreurs');\n" +
                        "                }\n" +
                        "\n" +
                        "                checkErrors();\n" +
                        "                validerQuestion(reponses,\"EA\");\n" +
                        "\n" +
                        "                if(tabCompletion[questionActuelle])\n" +
                        "                {\n" +
                        "                    showFeedBack(questionActuelleEstBon, points, texteExplications);\n" +
                        "                }" +
                        "            }\n" +
                        "        }, 1000);\n" +
                        "    }\n" +
                        "\n" +
                        "    // Fonction pour arrêter le chronomètre\n" +
                        "    function stopTimerQuestion() {\n" +
                        "        isRunning = false;\n" +
                        "        tps = 0;\n" +
                        "        clearInterval(timer);\n" +
                        "    }\n" +
                        "\n" +
                        "    const canvas = document.getElementById('canvas');\n" +
                        "    const ctx = canvas.getContext('2d');\n" +
                        "\n" +
                        "    canvas.height = 250 * reponses.length;\n" +
                        "\n" +
                        "    \n" +
                        "    canvas.width = canvas.offsetWidth;\n" +
                        "    \n" +
                        "    // Recalcule et redessine si la fenêtre est redimensionnée\n" +
                        "    window.onresize = drawLines;\n" +
                        "\n" +
                        "    // Récupérer les éléments à connecter\n" +
                        "    const leftElementsDiv = document.querySelectorAll(\"#repGauche .reponseBoxAssos\");\n" +
                        "    const rightElementsDiv = document.querySelectorAll(\"#repDroite .reponseBoxAssos\");\n" +
                        "\n" +
                        "    let leftElements = [];\n" +
                        "    let rightElements = [];\n" +
                        "\n" +
                        "    let selectGauche = -1;\n" +
                        "    let selectDroite = -1;\n" +
                        "\n" +
                        "\n" +
                        "    leftElementsDiv.forEach(function(element, index, leftElement) {\n" +
                        "        leftElements[index] = index;\n" +
                        "    });\n" +
                        "\n" +
                        "    rightElementsDiv.forEach(function(element, index, rightElementsDiv) {\n" +
                        "        rightElements[index] = index;\n" +
                        "    });\n" +
                        "\n" +
                        "\n" +
                        "    function checkErrors()\n" +
                        "    {\n" +
                        "        let estBon = true;\n" +
                        "\n" +
                        "        let cptBon = 0;\n" +
                        "\n" +
                        "        let tR = reponses.sort();\n" +
                        "        let tL = lignesQ"+ (questionnaire.getLstQuestion().indexOf(q) + 1) +".sort();\n" +
                        "\n" +
                        "\n" +
                        "        for(let i = 0 ; i < reponses.length ; i++)\n" +
                        "        {\n" +
                        "\n" +
                        "            if((tR[i][0][0] === tL[i][0][0] && tR[i][0][1] === tL[i][0][1]) || (tR[i][0][1] === tL[i][0][0] && tR[i][0][0] === tL[i][0][1]))\n" +
                        "            {\n" +
                        "                cptBon++;\n" +
                        "            }\n" +
                        "\n" +
                        "        }\n" +
                        "\n" +
                        "        if(cptBon < reponses.length)\n" +
                        "        {\n" +
                        "            estBon = false;\n" +
                        "        }\n" +
                        "\n" +
                        "\n" +
                        "        if(!tabCompletion[questionActuelle] && estBon)\n" +
                        "        {        \n" +
                        "            totalPoints += points;\n" +
                        "        }\n" +
                        "\n" +
                        "        questionActuelleEstBon = estBon;\n" +
                        "\n" +
                        "        return estBon;\n" +
                        "    }" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "    function selection(element, index)\n" +
                        "    {\n" +
                        "        if(tabCompletion[questionActuelle])\n" +
                        "        {\n" +
                        "            return;\n" +
                        "        }\n" +
                        "\n" +
                        "        for(let i = 0 ; i < reponses.length*2 ; i++)\n" +
                        "        {\n" +
                        "            document.getElementById(\"rep\" + (i + 1)).classList.remove('selected');\n" +
                        "        }\n" +
                        "\n" +
                        "\n" +
                        "        if(index%2 == 0)\n" +
                        "        {\n" +
                        "\n" +
                        "            if(selectGauche == index)\n" +
                        "            {\n" +
                        "                selectGauche = -1;\n" +
                        "            }else{\n" +
                        "                selectGauche = index;\n" +
                        "            }\n" +
                        "        }else{\n" +
                        "            if(selectDroite == index)\n" +
                        "            {\n" +
                        "                selectDroite = -1;\n" +
                        "            }else{\n" +
                        "                if(selectGauche > -1)\n" +
                        "                    selectDroite = index;\n" +
                        "            }\n" +
                        "        }\n" +
                        "\n" +
                        "        if(selectGauche >= 0 && selectDroite >= 0)\n" +
                        "        {\n" +
                        "            for(let i = 0 ; i < lignesQ"+ (questionnaire.getLstQuestion().indexOf(q) + 1) +".length ; i++)\n" +
                        "            {\n" +
                        "                if((lignesQ"+ (questionnaire.getLstQuestion().indexOf(q) + 1) +"[i][selectGauche%2][0] == selectGauche && lignesQ"+ (questionnaire.getLstQuestion().indexOf(q) + 1) +"[i][selectGauche%2][1] == selectDroite) || (lignesQ"+ (questionnaire.getLstQuestion().indexOf(q) + 1) +"[i][selectDroite%2][0] == selectGauche && lignesQ"+ (questionnaire.getLstQuestion().indexOf(q) + 1) +"[i][selectDroite%2][1] == selectDroite))\n" +
                        "                {\n" +
                        "                    lignesQ"+ (questionnaire.getLstQuestion().indexOf(q) + 1) +"[i][selectGauche%2] = [];\n" +
                        "                    lignesQ"+ (questionnaire.getLstQuestion().indexOf(q) + 1) +"[i][selectDroite%2] = [];\n" +
                        "                    document.getElementById(\"rep\" + (selectGauche+1)).classList.remove('selected');\n" +
                        "                    document.getElementById(\"rep\" + (selectDroite+1)).classList.remove('selected');\n" +
                        "\n" +
                        "                    selectGauche = -1;\n" +
                        "                    selectDroite = -1;\n" +
                        "\n" +
                        "\n" +
                        "                    ctx.clearRect(0, 0, canvas.width, canvas.height);\n" +
                        "\n" +
                        "                    drawLines(lignesQ"+ (questionnaire.getLstQuestion().indexOf(q) + 1) +");\n" +
                        "\n" +
                        "                    return;\n" +
                        "                }\n" +
                        "\n" +
                        "                if(lignesQ"+ (questionnaire.getLstQuestion().indexOf(q) + 1) +"[i][0][0] == selectGauche)\n" +
                        "                {\n" +
                        "                    document.getElementById(\"rep\" + (selectGauche+1)).classList.remove('selected');\n" +
                        "                    selectGauche = -1;\n" +
                        "                    selectDroite = -1;\n" +
                        "                    return;\n" +
                        "                }\n" +
                        "\n" +
                        "                if(lignesQ"+ (questionnaire.getLstQuestion().indexOf(q) + 1) +"[i][1][1] == selectDroite)\n" +
                        "                {\n" +
                        "                    document.getElementById(\"rep\" + (selectDroite+1)).classList.remove('selected');\n" +
                        "                    selectGauche = -1;\n" +
                        "                    selectDroite = -1;\n" +
                        "                    return;\n" +
                        "                }\n" +
                        "            }\n" +
                        "\n" +
                        "            lignesQ"+ (questionnaire.getLstQuestion().indexOf(q) + 1) +"[Math.floor(index/2)][selectGauche%2][0] = selectGauche;\n" +
                        "            lignesQ"+ (questionnaire.getLstQuestion().indexOf(q) + 1) +"[Math.floor(index/2)][selectGauche%2][1] = selectDroite;\n" +
                        "\n" +
                        "            lignesQ"+ (questionnaire.getLstQuestion().indexOf(q) + 1) +"[Math.floor(index/2)][selectDroite%2][0] = selectGauche;\n" +
                        "            lignesQ"+ (questionnaire.getLstQuestion().indexOf(q) + 1) +"[Math.floor(index/2)][selectDroite%2][1] = selectDroite;\n" +
                        "            \n" +
                        "            selectDroite = -1;\n" +
                        "            selectGauche = -1;\n" +
                        "        }\n" +
                        "\n" +
                        "        if(selectGauche > -1)\n" +
                        "            document.getElementById(\"rep\" + (selectGauche+1)).classList.toggle('selected');\n" +
                        "        if(selectDroite > -1 && selectGauche > -1)\n" +
                        "            document.getElementById(\"rep\" + (selectDroite+1)).classList.toggle('selected');\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "        drawLines(lignesQ"+ (questionnaire.getLstQuestion().indexOf(q) + 1) +");\n" +
                        "    }\n" +
                        "\n" +
                        "    window.onload = function() {\n" +
                        "        drawLines(lignesQ"+ (questionnaire.getLstQuestion().indexOf(q) + 1) +");\n" +
                        "    }\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "    if(tabCompletion[questionActuelle])\n" +
                        "    {\n" +
                        "        drawLines(lignesQ" + (questionnaire.getLstQuestion().indexOf(q) + 1) + ", 'erreurs');\n" +
                        "        drawLines(reponses, 'correction');\n" +
                        "    }\n" +
                        "    \n" +
                        "    updateProgress();\n" +
                        "\n" +
                        "    changerSelections();\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "    // Mettre à jour les événements de clic\n" +
                        "\n" +
                        "    if(!tabCompletion[questionActuelle])\n" +
                        "    {\n" +
                        clickDetection +
                        "\n" +
                        "        document.getElementById(\"valider\").onclick = function() \n" +
                        "        {\n" +
                        "            if(!tabCompletion[questionActuelle])\n" +
                        "            {\n" +
                        "                drawLines(lignesQ"+ (questionnaire.getLstQuestion().indexOf(q) + 1) +", 'erreurs');\n" +
                        "            }\n" +
                        "\n" +
                        "            checkErrors();\n" +
                        "            validerQuestion(reponses,\"EA\");\n" +
                        "            stopTimerQuestion();\n" +
                        "\n" +
                        "            if(tabCompletion[questionActuelle])\n" +
                        "            {\n" +
                        "                showFeedBack(questionActuelleEstBon, points, texteExplications);\n" +
                        "            }\n" +
                        "        };\n" +
                        "    }\n" +
                        "\n" +
                        "    \n" +
                        "\n" +
                        "    \n" +
                        "    document.getElementById(\"btnPreced\").onclick = function() {if(!estChronometrer){questionPrecedante(), " + questionPrecedente + "}}\n" +
                        "    document.getElementById(\"btnSuiv\").onclick = function() {if(estChronometrer){if(tabCompletion[questionActuelle]){questionSuivante(), " + questionSuivante + "}}else{questionSuivante(), " + questionSuivante + "}};\n" +
                        "\n" +
                        "}";
            }
            else if ((q instanceof EliminationReponse))
            {
                String reponses = "";
                String bonnesRep = "[";
                String onClicFonctions = "";
                String questionSuivante = "";
                String questionPrecedente = "";

                if ((questionnaire.getLstQuestion().indexOf(q) == 0))
                {
                    questionPrecedente = "question1()";
                }
                else
                {
                    questionPrecedente = "question" + ((questionnaire.getLstQuestion().indexOf(q))) + "()";
                }

                if ((questionnaire.getLstQuestion().indexOf(q) == questionnaire.getLstQuestion().size() - 1))
                {
                    questionSuivante = "finQuestionnaire()";
                }
                else
                {
                    questionSuivante = "question" + ((questionnaire.getLstQuestion().indexOf(q)) + 2) + "()";
                }

                int i = 1;

                for (String reponse : ((EliminationReponse) q).getHmReponses().keySet())
                {
                    if (((EliminationReponse) q).estReponseCorrecte(reponse))
                    {
                        bonnesRep = bonnesRep + "true,";
                    }
                    else
                    {
                        bonnesRep = bonnesRep + "false,";
                    }
                    reponses = reponses + "<div class=\"reponseBox\" id=\"rep" + i + "\" ><img id=\"img"+ i +"\" class=\"imgBtn\" src=\"./data/imgBtn/RadVide.png\">'" + reponse + "'</div>\n";
                    onClicFonctions = onClicFonctions + "document.getElementById(\"rep" + i + "\").onclick = function() { clicRep(" + (i - 1) + ", \"elimination\") };\n";
                    i++;
                }

                bonnesRep = bonnesRep + "];";

                String eliminations = "[";


                double coutElimination = 0.0;

                for(String reponse : ((EliminationReponse)q).getHmReponses().keySet())
                {
                    if (((EliminationReponse) q).getHmReponses().get(reponse)[1] > 0)
                    {
                        eliminations += ((EliminationReponse) q).getHmReponses().get(reponse)[1].intValue() + ",";
                    }
                    else
                    {
                        eliminations += "-1,";
                    }
                    if(((EliminationReponse) q).getHmReponses().get(reponse)[0] > coutElimination)
                    {
                        coutElimination = ((EliminationReponse) q).getHmReponses().get(reponse)[0];
                    }

                }

                eliminations += "];";

                String imageEnonce = null;

                if(!q.getImageChemin().equals(null) && !q.getImageChemin().isEmpty())
                {
                    imageEnonce = "<img class=\"imgQuestion\" src=\"./data/Question " + q.getId() + "/Compléments/" + q.getImageChemin() +"\">\n";
                }




                res += "function question" + (questionnaire.getLstQuestion().indexOf(q) + 1) + "()\n" +
                        "{\n" +
                        "    //Variables\n" +
                        "    let bonnesRep = " + bonnesRep + "\n" +
                        "    let eliminations = " + eliminations + "\n" +
                        "    let nbEliminations = 0;\n" +
                        "    let points = " + q.getPoint() + ";\n" +
                        "    let coutElimination = " + coutElimination + " ;\n" +
                        "    let texteExplications = '" + q.getExplicationText() + "';\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "    const difficulte = '" + q.getDifficulte().getNom() + "';\n" +
                        "    const tempsDeReponse = " + q.getTemps() +";\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "    let contenuPage =`<!DOCTYPE html>\n" +
                        "                    <html lang=\"en\">\n" +
                        "                    <head>\n" +
                        "                        <meta charset=\"UTF-8\">\n" +
                        "                        <title>QCMBuilder</title>\n" +
                        "                        <link href=\"style.css\" rel=\"stylesheet\">\n" +
                        "                    </head>\n" +
                        "                    <body>\n" +
                        "                        <div id=\"popup\" class=\"hiddenP\">\n" +
                        "                            <div id=\"popupI\" class=\"hidden\">\n" +
                        "                                <span id=\"closePopupBtn\" class=\"close\">&times;</span>\n" +
                        "                                <h2 id=\"estReponseBonne\"></h2>\n" +
                        "                                <p id=\"textFeedBack\"></p>\n" +
                        "                            </div>\n" +
                        "                        </div>\n" +
                        "\n" +
                        "\n" +
                        "                   <header>\n"+
                        "                        <div class=\"compteurs\">\n" +
                        "                            <div class=\"timer, compteurs-div\" id=\"chrono\">00:00</div>\n" +
                        "                            <div class=\"compteurs-div\">Questions traitées : <span id=\"questionsDone\">0</span>/`+ nbQuestion +`</div>\n" +
                        "                        </div>\n" +
                        "                        <div class=\"progress-container\">\n" +
                        "                            <div class=\"progress-bar\" id=\"progressBar\">0%</div>\n" +
                        "                        </div>\n" +
                        "                   </header>\n"+
                        "\n" +
                        "                        <script src=\"./script.js\"></script>\n" +
                        "                   <div class=\"pageQuestion\">\n" +
                        "                        <h1> Question `+ questionActuelle +` : </h1>\n" +
                        "                        <h2> Difficulté : `+ difficulte +` <!-- Avoir la difficulté de la question avec JS--> </h2>\n" +
                        "\n" +
                        "                        <br>\n" +
                        "                        <!-- Pour un Elimination -->\n" +
                        "                        <h2> Type : Elimination de réponses </h2>\n" +
                        imageEnonce + "\n" +
                        "                        <h3> " + q.getEnonceText() + " </h3>\n" +
                        "                        <div id=\"zoneRep\">\n" +
                        "                        "  + reponses +
                        "                        </div>\n" +
                        "                        <div class=\"styleBut\" id=btnSup>Eliminer une réponse</div>\n" +
                        "                   </div>\n" +
                        "                        <footer>\n" +
                        "                            <nav>\n" +
                        "                                <div class=\"styleBut\" id=\"btnPreced\">Précédent</div>\n" +
                        "                                <div class=\"styleBut\" id=\"valider\">Valider</div>\n" +
                        "                                <div class=\"styleBut\" id=\"feedBack\">Feedback</div>\n" +
                        "                                <div class=\"styleBut\" id=\"btnSuiv\">Suivant</div>\n" +
                        "                            </nav>\n" +
                        "                        </footer>\n" +
                        "                    </body>\n" +
                        "                    </html>`;\n" +
                        "\n" +
                        "    creerHtml(contenuPage);\n" +
                        "\n" +
                        "    updateProgress();\n" +
                        "\n" +
                        "    changerSelections();\n" +
                        "\n" +
                        "    \n" +
                        "    //Génération du popup\n" +
                        "    const popup = document.getElementById('popup');\n" +
                        "    const closePopupBtn = document.getElementById('closePopupBtn');\n" +
                        "    const popupI = document.getElementById('popupI');\n" +
                        "    const feedBackBtn = document.getElementById('feedBack');\n" +
                        "\n" +
                        "\n" +
                        "    feedBackBtn.addEventListener('click', () => {\n" +
                        "        showFeedBack(questionActuelleEstBon, points, texteExplications);\n" +
                        "    });\n" +
                        "    \n" +
                        "    // Cacher le popup\n" +
                        "    closePopupBtn.addEventListener('click', () => {\n" +
                        "        cacheFeedBack();\n" +
                        "    });\n" +
                        "    \n" +
                        "    // Optionnel : Fermer le popup en cliquant en dehors\n" +
                        "    popup.addEventListener('click', (event) => \n" +
                        "    {\n" +
                        "        if (event.target === popup) {\n" +
                        "            cacheFeedBack();\n" +
                        "        }\n" +
                        "    });\n" +
                        "\n" +
                        "\n" +
                        "    //Gestion du timer\n" +
                        "    if(estChronometrer)\n" +
                        "    {\n" +
                        "        startTimerQuestion(tempsDeReponse);\n" +
                        "    }else{\n" +
                        "        document.getElementById('chrono').textContent = \"Temps estimé : \" + tempsDeReponse;\n" +
                        "    }\n" +
                        "\n" +
                        "\n" +
                        "    //Timer pour les questions\n" +
                        "    function startTimerQuestion(tempsDeReponse) {\n" +
                        "        isRunning = true;\n" +
                        "        let tps = tempsDeReponse;\n" +
                        "        timer = setInterval(() => {\n" +
                        "            tps--;\n" +
                        "            if(document.getElementById('chrono') != null)\n" +
                        "                document.getElementById('chrono').textContent = \"Compte à rebours : \" + formatTime(tps) + \" secondes\";\n" +
                        "            if(tps <= 0)\n" +
                        "            {\n" +
                        "                stopTimerQuestion();\n" +
                        "                verifierReponses(bonnesRep, points)\n" +
                        "                validerQuestion(bonnesRep,\"QCM\");\n" +
                        "                showFeedBack(questionActuelleEstBon    ,points,texteExplications);\n" +
                        "            }\n" +
                        "        }, 1000);\n" +
                        "    }\n" +
                        "\n" +
                        "    // Fonction pour arrêter le chronomètre\n" +
                        "    function stopTimerQuestion() {\n" +
                        "        isRunning = false;\n" +
                        "        clearInterval(timer);\n" +
                        "    }\n" +
                        "\n" +
                        "\n" +
                        "    function suppQuestion(eliminations)\n" +
                        "    {\n" +
                        "        if(!tabCompletion[questionActuelle])\n" +
                        "        {\n" +
                        "            for(let i = 0 ;  i < eliminations.length ; i++)\n" +
                        "            {\n" +
                        "                if(nbEliminations+1 == eliminations[i])\n" +
                        "                {\n" +
                        "                    document.getElementById(\"rep\" + (i+1)).classList.remove('selected');\n" +
                        "                    document.getElementById(\"rep\" + (i+1)).classList.add('eliminer');\n" +
                        "                    nbEliminationQ" + (questionnaire.getLstQuestion().indexOf(q) + 1) + "[i] = true;\n" +
                        "                    nbEliminations++;\n" +
                        "                    points -= coutElimination;\n" +
                        "                   if(points < 0) "+
                        "                   {" +
                        "                        points = 0; " +
                        "                    }" +
                        "                }\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "    if(tabCompletion[questionActuelle])\n" +
                        "    {\n" +
                        "\t\tdocument.getElementById(\"valider\").classList.add(\"griser\");\n" +
                        "        afficherReponse(bonnesRep, 'QCM');\n" +
                        "    }\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "    // Mettre à jour les événements de clic\n" +
                        "\n" +
                        "    if(!tabCompletion[questionActuelle])\n" +
                        "    {\n" +
                        "    " + onClicFonctions +
                        "\n" +
                        "        document.getElementById(\"btnSup\").onclick = function() { suppQuestion(eliminations) };\n" +
                        "        document.getElementById(\"valider\").onclick = function()\n" +
                        "         {\n" +
                        "            if(!tabCompletion[questionActuelle])\n" +
                        "            {\n" +
                        "                stopTimerQuestion();\n" +
                        "                verifierReponses(bonnesRep, points)\n" +
                        "                validerQuestion(bonnesRep, \"QCM\"); \n" +
                        "                showFeedBack(questionActuelleEstBon    ,points,texteExplications);\n" +
                        "            }\n" +
                        "        };\n" +
                        "    }else{\n" +
                        "\n" +
                        "        for(let i = 0 ; i < nbEliminationQ" + (questionnaire.getLstQuestion().indexOf(q) + 1) + ".length ; i++)\n" +
                        "        {\n" +
                        "            if(nbEliminationQ" + (questionnaire.getLstQuestion().indexOf(q) + 1) + "[i] == true)\n" +
                        "            {\n" +
                        "                document.getElementById(\"rep\" + (i+1)).classList.add('eliminer');\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "\n" +
                        "    document.getElementById(\"btnPreced\").onclick = function() {if(!estChronometrer){questionPrecedante(), " + questionPrecedente + "}}\n" +
                        "    document.getElementById(\"btnSuiv\").onclick = function() {if(estChronometrer){if(tabCompletion[questionActuelle]){questionSuivante(), " + questionSuivante + "}}else{questionSuivante(), " + questionSuivante + "}};\n" +
                        "}";
            }
        }
        return res;
    }

}
