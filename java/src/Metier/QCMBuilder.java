package src.Metier;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class QCMBuilder
{

    private Questionnaire questionnaire;
    private Scanner scanner;
    private FileWriter fileWriter;
    private String path;

    public QCMBuilder(Questionnaire questionnaire, String path){
        this.questionnaire = questionnaire;
        this.path = path;

        if(!this.creerDossier(path)){
            System.out.println("Erreur, ce questionnaire existe déjà !");
            return;
        }

        try{
            this.fileWriter = new FileWriter(path + questionnaire.getNom() + "/index.html");
            this.fileWriter = new FileWriter(path + questionnaire.getNom() + "/index.html");

            ecrireHTML();
            ecrireStyle();
            ecrireImage();
            ecrireJS();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public boolean creerDossier(String path){
        File file = new File(path + this.questionnaire.getNom() );

        if(file.exists()){
            return false;
        }else {
            file.mkdir();
        }
        return true;
    }


    /*Cette méthode permet l'écriture du index.html commun à tous les questionnaires.*/

    public boolean ecrireHTML(){
        try{
            String listeNotions = "";

            int nbTresFacile = 0;
            int nbFacile = 0;
            int nbMoyen = 0;
            int nbDifficile = 0;

            for(Question q : this.questionnaire.getLstQuestion()){
                switch (q.getDifficulte().getIndice()){
                    case 1:
                        nbTresFacile++;
                        break;
                    case 2:
                        nbFacile++;
                        break;
                    case 3:
                        nbMoyen++;
                        break;
                    case 4:
                        nbDifficile++;
                        break;
                }

            }

            for(Notion n : this.questionnaire.getLstNotions()){
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
                            "    <header></header>\n" +
                            "    <h1>Bienvenue dans un Questionnaire en " + this.questionnaire.getRessource().getNom() +"!</h1>\n" +
                            "    <h2>"+ this.questionnaire.getNom() +"\n" +
                            "    <h2>Temps pour finir : "+ questionnaire.getTempsEstimée() +"\n" +
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

            BufferedWriter writer = new BufferedWriter(new FileWriter(path + this.questionnaire.getNom() + "/index.html"));
            writer.write(html);

            writer.close();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }



    public boolean ecrireStyle(){
        try{
            String css = "";

            this.scanner = new Scanner(new FileReader(System.getProperty("user.dir") + "/out/production/SAE-3.1/docs/style.css"));

            while(scanner.hasNextLine()){
                css = css + scanner.nextLine()+"\n";
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(path + this.questionnaire.getNom() + "/style.css"));
            writer.write(css);

            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean ecrireImage(){
        String[] tabImg = {"TF.png", "F.png", "M.png", "D.png"};


        try{
            Path folderPath = Path.of(path + this.questionnaire.getNom() + "/data/imgDif/");
            Files.createDirectories(folderPath);

            for(String img : tabImg){

                String newPath = System.getProperty("user.dir") + "/out/production/SAE-3.1/data/Images/imgDif/" + img;
                String newPath2 = path + this.questionnaire.getNom() + "/data/imgDif/" + img;

                Files.copy(Path.of(newPath), Path.of(newPath2), StandardCopyOption.REPLACE_EXISTING);

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }


    public void ecrireJS(){
        int noteMax = 0;


        //Début tab completion
        String tabCompletion = "[";
        String lignes = ";;

        for(int i = 0 ; i < this.questionnaire.getLstQuestion().size() ; i++){
            tabCompletion = tabCompletion + "false,";
        }

        tabCompletion = tabCompletion.substring(0, tabCompletion.length()-1);
        tabCompletion = tabCompletion + "];";

        for(Question q : this.questionnaire.getLstQuestion()){
            noteMax += q.getPoint();
        }

        //Fin tabCompletion


        //Debut tabSelection

        String tabSelection = "\n[\n[],\n[";

        for(Question q : this.questionnaire.getLstQuestion()){
            if(q instanceof QCM){
                for(int i = 0 ; i < ((QCM) q).getNbReponses() ; i ++){
                    tabSelection = tabSelection + "false,";
                }
            }
            tabSelection = tabSelection + "],\n[";
        }

        tabSelection = tabSelection.substring(0, tabSelection.length()-2);

        tabSelection = tabSelection + ";";


        //Fin tabSelection

        //Debut lignes


        for(Question q : this.questionnaire.getLstQuestion()){
            lignes = lignes + "let lignes" + indice + " = [";
            if(q instanceof AssociationElement){
                for(int i = 0 ; i < ((AssociationElement) q).getAssociations().size() ; i ++){
                    lignes = lignes + "[],\n";
                }
            }
        }


        String commun =
                    "//Constantes et var communes\n" +
                    "const nbQuestion = "+ this.questionnaire.getLstQuestion().size() + ";\n" +
                    "const estChronometrer = "+ questionnaire.getChronoBool() +";\n" +
                    "const dureeTotal = "+ questionnaire.getTempsEstimée() +"; //Secondes\n" +
                    "const noteMax = "+ noteMax +";\n" +
                    "\n" +
                    "\n" +
                    "//Init variables\n" +
                    "let questionActuelleEstBon = false;\n" +
                    "let questionActuelle = 1;\n" +
                    "let completion = 0;\n" +
                    "let totalPoints = 0;\n" +
                    "let timer;\n" +
                    "let tabCompletion = "+ tabCompletion +"\n" +
                    "\n" +
                    "const imgTF =' <img class=\"imgDif\" src=\"./src/imgDif/TF.png\">';\n" +
                    "const imgF  =' <img class=\"imgDif\" src=\"./src/imgDif/F.png\">';\n" +
                    "const imgM  =' <img class=\"imgDif\" src=\"./src/imgDif/M.png\">';\n" +
                    "const imgD  =' <img class=\"imgDif\" src=\"./src/imgDif/D.png\">';\n" +
                    "\n" +
                    "tabSelections = " + tabSelection +
                    "\n" +
                    "//Pour le qcm à relier\n" +
                    "let lignes = \n" +
                    "[\n" +
                    "    [],\n" +
                    "    [],\n" +
                    "    [],\n" +
                    "    []\n" +
                    "]\n" +
                    "\n" +
                    "\n" +
                    "function resetVariables()\n" +
                    "{\n" +
                    "    questionActuelle = 1;\n" +
                    "    completion = 0;\n" +
                    "    timer;\n" +
                    "    seconds = 0;\n" +
                    "    totalPoints = 0;\n" +
                    "    tabCompletion = [false,false,false,false,false,false,false,false,false,false];\n" +
                    "    //nbEliminiationQ5 = [false,false,false,false,false]; //Seulement pour les éliminations\n" +
                    "    docAfficher = false;\n" +
                    "\n" +
                    "    tabSelections = [\n" +
                    "        [],\n" +
                    "        [false,false, false, false],\n" +
                    "        [false,false],\n" +
                    "        [false,false,false,false],\n" +
                    "        [],\n" +
                    "        [false,false,false,false,false]\n" +
                    "        ];\n" +
                    "\n" +
                    "     lignes = \n" +
                    "            [\n" +
                    "            [],\n" +
                    "            [],\n" +
                    "            [],\n" +
                    "            []\n" +
                    "            ]\n" +
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
                    "    console.log(estBon)\n" +
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
                    "function clicRep(index, typeQuestion) \n" +
                    "{\n" +
                    "    switch(typeQuestion)\n" +
                    "    {\n" +
                    "        case \"QCM\" : \n" +
                    "        {\n" +
                    "            if(!tabCompletion[questionActuelle])\n" +
                    "            {\n" +
                    "                tabSelections[questionActuelle][index] = !tabSelections[questionActuelle][index];\n" +
                    "        \n" +
                    "                document.getElementById(\"rep\" + (index+1)).classList.toggle('selected');\n" +
                    "            }\n" +
                    "            break;\n" +
                    "        }\n" +
                    "\n" +
                    "        case \"vrai-faux\" : \n" +
                    "        {\n" +
                    "            if(!tabCompletion[questionActuelle])\n" +
                    "            {\n" +
                    "                if(document.getElementById(\"rep1\").classList.contains('selected'))\n" +
                    "                {\n" +
                    "                    document.getElementById(\"rep1\").classList.toggle('selected');\n" +
                    "                }\n" +
                    "                if(document.getElementById(\"rep2\").classList.contains('selected'))\n" +
                    "                {\n" +
                    "                    document.getElementById(\"rep2\").classList.toggle('selected');\n" +
                    "                }\n" +
                    "\n" +
                    "                let tmp = tabSelections[questionActuelle][index];\n" +
                    "                tabSelections[questionActuelle] = [false,false];\n" +
                    "                tabSelections[questionActuelle][index] = !tmp;\n" +
                    "\n" +
                    "                document.getElementById(\"rep\" + (index+1)).classList.toggle('selected');\n" +
                    "            }\n" +
                    "            break;\n" +
                    "        }\n" +
                    "\n" +
                    "        case \"elimination\" :\n" +
                    "        {\n" +
                    "            if(!tabCompletion[questionActuelle])\n" +
                    "            {\n" +
                    "                const elementsDiv = document.querySelectorAll(\".reponseBox\");\n" +
                    "\n" +
                    "                elementsDiv.forEach(element => {\n" +
                    "\n" +
                    "                    if(element.classList.contains('selected') && !element.classList.contains('eliminer'))\n" +
                    "                    {\n" +
                    "                        element.classList.remove('selected'); // Retire explicitement la classe\n" +
                    "                    }\n" +
                    "                });\n" +
                    "\n" +
                    "                let tmp = document.getElementById(\"rep\" + (index+1));\n" +
                    "\n" +
                    "                if(!tmp.classList.contains('eliminer'))\n" +
                    "                {\n" +
                    "                    for(let i = 0 ; i < tabSelections[questionActuelle].length ; i++)\n" +
                    "                    {\n" +
                    "                        if(tabSelections[questionActuelle][i] == true)\n" +
                    "                        {\n" +
                    "                            tabSelections[questionActuelle][i] = false;\n" +
                    "                        }\n" +
                    "                    }\n" +
                    "    \n" +
                    "                    tabSelections[questionActuelle][index] = true;\n" +
                    "                    tmp.classList.toggle('selected');\n" +
                    "                }\n" +
                    "                break;\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "\n" +
                    "}\n" +
                    "\n" +
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
                    "                            <header></header>\n" +
                    "                            <h1>Bienvenue dans un Questionnaire !</h1>\n" +
                    "                            <h2>[Titre éval]<!-- Avoir la matiere avec JS --></h2>\n" +
                    "                            <h2>Temps pour finir : [Durée]<!-- Avoir la durér avec JS --></h2>\n" +
                    "                            <h2>Sur la matière : [Matiere] <!-- Avoir la matiere avec JS --></h2>\n" +
                    "                            <h2>Sur les notions suivante :</h2>\n" +
                    "                            <ul>\n" +
                    "                                <li> [notion1] </li>\n" +
                    "                                <li> [notion2] </li>\n" +
                    "                                <li> [notion3] </li>\n" +
                    "                            </ul> \n" +
                    "                            <h2> Qui contient :</h2>\n" +
                    "                            <p>\n" +
                    "                                X Question `+imgTF+` <br> <!-- Avoir le nb Question avec JS -->\n" +
                    "                                X Question `+imgF+` <br> <!-- Avoir le nb Question avec JS -->\n" +
                    "                                X Question `+imgM+` <br> <!-- Avoir le nb Question avec JS -->\n" +
                    "                                X Question `+imgD+` <!-- Avoir le nb Question avec JS -->\n" +
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
                    "                        <h2> Ressource : `+ ressource +` <!-- Avoir la ressource de la question avec JS--> </h2>\n" +
                    "                        <h2> Notion : `+ notion +` <!-- Avoir la notion de la question avec JS--> </h2>\n" +
                    "                        <h2> Nombre de questions : `+ nbQuestion +` dont X `+ imgTF +` , X ` + imgF +` , X `+ imgM +` et X `+ imgD + `</h2>\n" +
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
                    "    const canvas = document.getElementById('canvas');\n" +
                    "    const ctx = canvas.getContext('2d');\n" +
                    "\n" +
                    "\n" +
                    "    // Parcourir les éléments et tracer les lignes\n" +
                    "\n" +
                    "    for(let index = 0 ; index < lignes.length ; index++) \n" +
                    "    {\n" +
                    "        for(let j = 0; j < lignes[index].length ; j++)\n" +
                    "        {\n" +
                    "            if ( j < lignes.length && lignes[index][j] != null) \n" +
                    "            {\n" +
                    "                let leftElement = document.getElementById(\"rep\" + ((index*2)+1));\n" +
                    "                let rightElement = document.getElementById(\"rep\" + (lignes[index][j] + 1));\n" +
                    "\n" +
                    "                const leftRect = leftElement.getBoundingClientRect();\n" +
                    "                const rightRect = rightElement.getBoundingClientRect();\n" +
                    "    \n" +
                    "                const canvasRect = canvas.getBoundingClientRect();\n" +
                    "    \n" +
                    "                const x1 = leftRect.right - canvasRect.left; // Coordonnée X de l'élément gauche (bord droit)\n" +
                    "                const y1 = leftRect.top + leftRect.height / 2 - canvasRect.top; // Milieu vertical de l'élément gauche\n" +
                    "    \n" +
                    "                const x2 = rightRect.left - canvasRect.left; // Coordonnée X de l'élément droit (bord gauche)\n" +
                    "                const y2 = rightRect.top + rightRect.height / 2 - canvasRect.top; // Milieu vertical de l'élément droit\n" +
                    "    \n" +
                    "                // Tracer une ligne entre les deux éléments\n" +
                    "                ctx.beginPath();\n" +
                    "                ctx.moveTo(x1, y1);\n" +
                    "                ctx.lineTo(x2, y2);\n" +
                    "\n" +
                    "                if(affichage == 'correction')\n" +
                    "                {\n" +
                    "                    ctx.strokeStyle = \"green\";\n" +
                    "                }else if(affichage == 'erreurs')\n" +
                    "                {\n" +
                    "                    ctx.strokeStyle = \"red\";\n" +
                    "                }else{\n" +
                    "                    ctx.strokeStyle = \"black\";\n" +
                    "                }\n" +
                    "\n" +
                    "                ctx.lineWidth = 4;\n" +
                    "                ctx.stroke();\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "}\n" +
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
                    "        document.getElementById(\"note\").innerHTML = (\"Total des points : \"+ totalPoints+\" / \"+ noteMax)\n" +
                    "        }\n" +
                    "\n" +
                    "    questionActuelleEstBon = estBon;\n" +
                    "\n" +
                    "    return estBon;\n" +
                    "}\n";

    }
}
