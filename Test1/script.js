//Constantes et var communes
const nbQuestion = 6;
const estChronometrer = false;
const dureeTotal = 111; //Secondes
const noteMax = 12;


//Init variables
let questionActuelleEstBon = false;
let questionActuelle = 1;
let completion = 0;
let totalPoints = 0;
let timer;
let tabCompletion = [false,false,false,false,false,false]

const imgTF =' <img class="imgDif" src="./src/imgDif/TF.png">';
const imgF  =' <img class="imgDif" src="./src/imgDif/F.png">';
const imgM  =' <img class="imgDif" src="./src/imgDif/M.png">';
const imgD  =' <img class="imgDif" src="./src/imgDif/D.png">';

let tabSelections = 
[
[],
[false,false,false,false,],
[false,false,false,false,],
[false,false,],
[],
[false,false,false,],
[],];
//Pour le qcm à relier
let lignes1 = [
[],
[],
];
let lignes2 = [
[],
[],
[],
[],
];


nbEliminationQ1 = [false,false,false,];

function resetVariables()
{
    questionActuelle = 1;
    completion = 0;
    timer;
    seconds = 0;
    totalPoints = 0;
    tabCompletion = [false,false,false,false,false,false];
	nbEliminationQ1 = [false,false,false,];

    docAfficher = false;

	tabSelections = 
[
[],
[false,false,false,false,],
[false,false,false,false,],
[false,false,],
[],
[false,false,false,],
[],];
	lignes1 = [
[],
[],
];
lignes2 = [
[],
[],
[],
[],
];

}

function formatTime(s) {
    let minutes = Math.floor(s / 60);
    let remainingSeconds = s % 60;
    return `${minutes < 10 ? '0' : ''}${minutes}:${remainingSeconds < 10 ? '0' : ''}${remainingSeconds}`;
}

function updateProgress() 
{
    const progressBar = document.getElementById('progressBar');
    const questionsDone = document.getElementById('questionsDone');

    const percentage = (completion / nbQuestion) * 100;
    progressBar.style.width = percentage + '%';
    progressBar.textContent = Math.round(percentage) + '%';
    questionsDone.textContent = completion;
}

function questionSuivante()
{
    if(questionActuelle < nbQuestion)
    {
        questionActuelle ++;
    }
    updateProgress();
}

function validerQuestion(bonnesRep, typeQuestion)
{
    if(!tabCompletion[questionActuelle])
    {
        afficherReponse(bonnesRep, typeQuestion);
    }

    if(!tabCompletion[questionActuelle])
    {
        tabCompletion[questionActuelle] = true;
        completion++;
    }
    
    updateProgress();
}

function questionPrecedante()
{    
    if(questionActuelle > 1 && !estChronometrer)
    {
        questionActuelle --;
    }

    updateProgress();
}

function afficherReponse(bonnesRep, typeQuestion) //Bonnes Rep est le tableau qui contient les réponses ou vrai = bonne rep et faux = mauvaise
{

    switch(typeQuestion)
    {
        case 'QCM' :
        {
            for(let i = 0; i < bonnesRep.length ; i++)
            {

                if((!bonnesRep[i] && tabSelections[questionActuelle][i]) || (bonnesRep[i] && !tabSelections[questionActuelle][i]))
                {
                    document.getElementById("rep" + (i+1)).classList.toggle('faux');
                }
        
                if(bonnesRep[i])
                {
                    document.getElementById("rep" + (i+1)).classList.toggle('vrai');
                }

            }
            break;
        }
        
        case 'EA' :
        {
            drawLines(bonnesRep, 'correction');
            break;
        }

        case 'ELIMINATION' :
        {
            for(let i = 0; i < bonnesRep.length ; i++)
            {

                if((!bonnesRep[i] && tabSelections[questionActuelle][i]) || (bonnesRep[i] && !tabSelections[questionActuelle][i]))
                {
                    document.getElementById("rep" + (i+1)).classList.toggle('faux');
                }
        
                if(bonnesRep[i])
                {
                    document.getElementById("rep" + (i+1)).classList.toggle('vrai');
                }
            }
            break;
        }
        
    }

}


function showFeedBack(estBon, nbPoints, explications)
{


    if(tabCompletion[questionActuelle])
    {
        popupI.classList.remove('hidden');
        popupI.classList.add('show');
        popup.classList.remove('hiddenP');
        popup.classList.add('popup');


        let txtFeedBack = document.getElementById('textFeedBack');
        let reponseBonne = document.getElementById('estReponseBonne')

        if(estBon)
        {
            reponseBonne.classList.add('reponseBonne');
            reponseBonne.innerHTML = 'Bonne réponse !';
            txtFeedBack.innerHTML = 'Nombre de points de la question : ' + nbPoints + "<br>Votre note : " + totalPoints + " / " + noteMax + "<br>Explications : <br>" + explications;

        }else{
            reponseBonne.classList.add('reponseFausse');
            reponseBonne.innerHTML = 'Mauvaise réponse !';
            txtFeedBack.innerHTML = 'Nombre de points de la question : ' + nbPoints + "<br>Votre note : " + totalPoints + " / " + noteMax + "<br>Explications : <br>" + explications;
        }


    }
}


function cacheFeedBack()
{
    popupI.classList.remove('show');
    popupI.classList.add('hidden');
    popup.classList.remove('popup');
    popup.classList.add('hiddenP');
}


function changerSelections()
{
    for(let i = 0 ; i < tabSelections[questionActuelle].length ; i++)
    {
        if(tabSelections[questionActuelle][i])
        {
            document.getElementById("rep" + (i+1)).classList.toggle('selected');
        }
    }
}


function clicRep(index, typeQuestion) 
{
    switch(typeQuestion)
    {
        case "QCM" : 
        {
            if(!tabCompletion[questionActuelle])
            {
                tabSelections[questionActuelle][index] = !tabSelections[questionActuelle][index];
        
                document.getElementById("rep" + (index+1)).classList.toggle('selected');
            }
            break;
        }

        case "vrai-faux" : 
        {
            if(!tabCompletion[questionActuelle])
            {
                if(document.getElementById("rep1").classList.contains('selected'))
                {
                    document.getElementById("rep1").classList.toggle('selected');
                }
                if(document.getElementById("rep2").classList.contains('selected'))
                {
                    document.getElementById("rep2").classList.toggle('selected');
                }

                let tmp = tabSelections[questionActuelle][index];
                tabSelections[questionActuelle] = [false,false];
                tabSelections[questionActuelle][index] = !tmp;

                document.getElementById("rep" + (index+1)).classList.toggle('selected');
            }
            break;
        }

        case "elimination" :
        {
            if(!tabCompletion[questionActuelle])
            {
                const elementsDiv = document.querySelectorAll(".reponseBox");

                elementsDiv.forEach(element => {

                    if(element.classList.contains('selected') && !element.classList.contains('eliminer'))
                    {
                        element.classList.remove('selected'); // Retire explicitement la classe
                    }
                });

                let tmp = document.getElementById("rep" + (index+1));

                if(!tmp.classList.contains('eliminer'))
                {
                    for(let i = 0 ; i < tabSelections[questionActuelle].length ; i++)
                    {
                        if(tabSelections[questionActuelle][i] == true)
                        {
                            tabSelections[questionActuelle][i] = false;
                        }
                    }
    
                    tabSelections[questionActuelle][index] = true;
                    tmp.classList.toggle('selected');
                }
                break;
            }
        }
    }

}

function finQuestionnaire()
{
    if (completion == nbQuestion || confirm("Vous n'avez pas répondu à toutes les questions, êtes-vous sûr de vouloir terminer le questionnaire ?")) 
    {
        resultat();
    }
}


function index()
{
    resetVariables();
    completion = 0;
    questionActuelle = 1;
    
    let contenuPage =`<!DOCTYPE html>
                        <html lang="en">
                        <head>
                            <meta charset="UTF-8">
                            <title>QCMBuilder</title>
                            <link href="style.css" rel="stylesheet">
                            <script src="./script.js"></script>
                        </head>
                        <body>
                            <header></header>
                            <h1>Bienvenue dans un Questionnaire !</h1>
                            <h2>[Titre éval]<!-- Avoir la matiere avec JS --></h2>
                            <h2>Temps pour finir : [Durée]<!-- Avoir la durér avec JS --></h2>
                            <h2>Sur la matière : [Matiere] <!-- Avoir la matiere avec JS --></h2>
                            <h2>Sur les notions suivante :</h2>
                            <ul>
                                <li> [notion1] </li>
                                <li> [notion2] </li>
                                <li> [notion3] </li>
                            </ul> 
                            <h2> Qui contient :</h2>
                            <p>
                                X Question `+imgTF+` <br> <!-- Avoir le nb Question avec JS -->
                                X Question `+imgF+` <br> <!-- Avoir le nb Question avec JS -->
                                X Question `+imgM+` <br> <!-- Avoir le nb Question avec JS -->
                                X Question `+imgD+` <!-- Avoir le nb Question avec JS -->
                            </p>
                            <div class="styleBut" onclick="question1()">Commencer</div>
                                <div id='chrono'></div>
                                <div id='feedBack'></div>
                                <div id='popup'></div>
                                <div id='popupI'></div>
                                <div id='closePopupBtn'></div>
                        </body>
                        </html>`;

    creerHtml(contenuPage);
}



function resultat()
{    

    let contenuPage =`<!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <title>QCMBuilder</title>
                        <link href="style.css" rel="stylesheet">
                        <script src="./script.js"></script>
                    </head>
                    <body>
                        <h1> Questionnaire Terminé ! </h1>
                        <h2> Ressource : `+ ressource +` <!-- Avoir la ressource de la question avec JS--> </h2>
                        <h2> Notion : `+ notion +` <!-- Avoir la notion de la question avec JS--> </h2>
                        <h2> Nombre de questions : `+ nbQuestion +` dont X `+ imgTF +` , X ` + imgF +` , X `+ imgM +` et X `+ imgD + `</h2>
                        <h2> Score global : `+ totalPoints+` / `+ noteMax +`</h2>
                        <br>
                        <div class="styleBut" onclick="index()">Revenir a l'index du questionnaire</div>
                        <div id='chrono'></div>
                        <div id='feedBack'></div>
                        <div id='popup'></div>
                        <div id='popupI'></div>
                        <div id='closePopupBtn'></div>
                    </body>
                    </html>`;

    creerHtml(contenuPage);
}


function creerHtml(html)
{
    document.body.innerHTML = html;
}




function drawLines(lignes, affichage) 
{
    const canvas = document.getElementById('canvas');
    const ctx = canvas.getContext('2d');


    // Parcourir les éléments et tracer les lignes

    for(let index = 0 ; index < lignes.length ; index++) 
    {
        for(let j = 0; j < lignes[index].length ; j++)
        {
            if ( j < lignes.length && lignes[index][j] != null) 
            {
                let leftElement = document.getElementById("rep" + ((index*2)+1));
                let rightElement = document.getElementById("rep" + (lignes[index][j] + 1));

                const leftRect = leftElement.getBoundingClientRect();
                const rightRect = rightElement.getBoundingClientRect();
    
                const canvasRect = canvas.getBoundingClientRect();
    
                const x1 = leftRect.right - canvasRect.left; // Coordonnée X de l'élément gauche (bord droit)
                const y1 = leftRect.top + leftRect.height / 2 - canvasRect.top; // Milieu vertical de l'élément gauche
    
                const x2 = rightRect.left - canvasRect.left; // Coordonnée X de l'élément droit (bord gauche)
                const y2 = rightRect.top + rightRect.height / 2 - canvasRect.top; // Milieu vertical de l'élément droit
    
                // Tracer une ligne entre les deux éléments
                ctx.beginPath();
                ctx.moveTo(x1, y1);
                ctx.lineTo(x2, y2);

                if(affichage == 'correction')
                {
                    ctx.strokeStyle = "green";
                }else if(affichage == 'erreurs')
                {
                    ctx.strokeStyle = "red";
                }else{
                    ctx.strokeStyle = "black";
                }

                ctx.lineWidth = 4;
                ctx.stroke();
            }
        }
    }
}


function verifierReponses(tabBonnesRep, nbPoints)
{

    let estBon = true;

    for(let i = 0 ; i < tabBonnesRep.length ; i++)
    {

        if(tabBonnesRep[i] != tabSelections[questionActuelle][i])
        {
            estBon = false;
            break;
        }
    }

    if(estBon && !tabCompletion[questionActuelle])
    {
        totalPoints += nbPoints;
        document.getElementById("note").innerHTML = ("Total des points : "+ totalPoints+" / "+ noteMax)
        }

    questionActuelleEstBon = estBon;

    return estBon;
}



function question1()
{
    //Variables
    let bonnesRep = [true,true,false,false,];
    const difficulte = "Moyen";
    const tempsDeReponse = 20;
    let points = 2;
    let texteExplications = "Pour aller coder en PHP vous devez d'abord lancer un serveur";



    let contenuPage =`<!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <title>QCMBuilder</title>
                        <link href="style.css" rel="stylesheet">
                    </head>
                    <body>
                        <div id="popup" class="hiddenP">
                            <div id="popupI" class="hidden">
                                <span id="closePopupBtn" class="close">&times;</span>
                                <h2 id="estReponseBonne"></h2>
                                <p id="textFeedBack"></p>
                            </div>
                        </div>


                        <div class="compteurs">
                            <div class="timer, compteurs-div" id="chrono">00:00</div>
                            <div class="compteurs-div">Questions traitées : <span id="questionsDone">0</span>/`+ nbQuestion +`</div>
                            <div class="compteurs-div">Question sur `+points+` points</div>
                            <div class="compteurs-div" id="note">Total des points : `+ totalPoints+` / `+ noteMax +`</div>
                        </div>
                        <div class="progress-container">
                            <div class="progress-bar" id="progressBar">0%</div>
                        </div>

                        <script src="./script.js"></script>

                        <h1> Question `+ questionActuelle +` : </h1>
                        <h2> Notion : `Ecriture en PHP` <!-- Avoir la notion de la question avec JS--> </h2>
                        <h2> Difficulté : `+ difficulte +` <!-- Avoir la difficulté de la question avec JS--> </h2>

                        <br>
                        <!-- Pour un QCM -->

                        <h3> "Pour aller coder en PHP vous devez d'abord lancer un serveur" </h3>
                        <div id="zoneRep">
                             <div class="reponseBox" id="rep-1" >'XAMPP'</div>
<div class="reponseBox" id="rep0" >'Docker'</div>
<div class="reponseBox" id="rep1" >'rien'</div>
<div class="reponseBox" id="rep2" >'Test'</div>

                        </div>
                        <footer>
                            <nav>
                                <div class="styleBut" id="btnPreced">Précédent</div>
                                <div class="styleBut" id="valider">Valider</div>
                                <div class="styleBut" id="feedBack">Feedback</div>
                                <div class="styleBut" id="btnSuiv">Suivant</div>
                            </nav>
                        </footer>
                    </body>
                    </html>`;

    creerHtml(contenuPage);

    updateProgress();

    changerSelections();

    //Génération du popup
    const popup = document.getElementById('popup');
    const closePopupBtn = document.getElementById('closePopupBtn');
    const popupI = document.getElementById('popupI');
    const feedBackBtn = document.getElementById('feedBack');


    feedBackBtn.addEventListener('click', () => {
        showFeedBack(questionActuelleEstBon,points,texteExplications);
    });
    
    // Cacher le popup
    closePopupBtn.addEventListener('click', () => {
        cacheFeedBack();
    });
    
    // Optionnel : Fermer le popup en cliquant en dehors
    popup.addEventListener('click', (event) => 
    {
        if (event.target === popup) {
            cacheFeedBack();
        }
    });



    //Gestion du timer
    if(estChronometrer)
    {
        startTimerQuestion(tempsDeReponse);
    }else{
        document.getElementById('chrono').textContent = "Temps estimé : " + tempsDeReponse;
    }



    //Timer pour les questions
    function startTimerQuestion(tempsDeReponse) 
    {
        if(estChronometrer)
        {
            isRunning = true;
            let tps = tempsDeReponse;
            timer = setInterval(() => {
                tps--;
                if(document.getElementById('chrono') != null)
                    document.getElementById('chrono').textContent = "Compte à rebours : " + formatTime(tps) + " secondes";
                if(tps <= 0)
                {
                    stopTimerQuestion();
                    verifierReponses(bonnesRep, points)
                    validerQuestion(bonnesRep, 'QCM');
                    showFeedBack(questionActuelleEstBon, points, texteExplications);
                }
            }, 1000);
        }
    }


    // Fonction pour arrêter le chronomètre
    function stopTimerQuestion() 
    {
        if(estChronometrer)
        {
            isRunning = false;
            clearInterval(timer);
        }
    }


    if(tabCompletion[questionActuelle])
    {
        afficherReponse(bonnesRep, 'QCM');
    }

    // Mettre à jour les événements de clic

    document.getElementById("rep1").onclick = function() { clicRep(0, "QCM") };
    document.getElementById("rep2").onclick = function() { clicRep(1, "QCM") };
    document.getElementById("rep3").onclick = function() { clicRep(2, "QCM") };
    document.getElementById("rep4").onclick = function() { clicRep(3, "QCM") };
    
    document.getElementById("btnPreced").onclick = function() {if(!estChronometrer){questionPrecedante(), question1()}};
    document.getElementById("btnSuiv").onclick = function() {if(estChronometrer){if(tabCompletion[questionActuelle]){questionSuivante(), question2()}}else{questionSuivante(), question2()}};

    document.getElementById("valider").onclick = function() 
    {
        if(!tabCompletion[questionActuelle])
        {
            verifierReponses(bonnesRep, points)
            validerQuestion(bonnesRep, 'QCM');
            showFeedBack(questionActuelleEstBon ,points, texteExplications);
            stopTimerQuestion();
        }
    };
}

function question2()
{
    //Variables
    let bonnesRep = [false,false,true,false,];
    const difficulte = "Facile";
    const tempsDeReponse = 15;
    let points = 1;
    let texteExplications = "Vous devez utilisr VSCODE";



    let contenuPage =`<!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <title>QCMBuilder</title>
                        <link href="style.css" rel="stylesheet">
                    </head>
                    <body>
                        <div id="popup" class="hiddenP">
                            <div id="popupI" class="hidden">
                                <span id="closePopupBtn" class="close">&times;</span>
                                <h2 id="estReponseBonne"></h2>
                                <p id="textFeedBack"></p>
                            </div>
                        </div>


                        <div class="compteurs">
                            <div class="timer, compteurs-div" id="chrono">00:00</div>
                            <div class="compteurs-div">Questions traitées : <span id="questionsDone">0</span>/`+ nbQuestion +`</div>
                            <div class="compteurs-div">Question sur `+points+` points</div>
                            <div class="compteurs-div" id="note">Total des points : `+ totalPoints+` / `+ noteMax +`</div>
                        </div>
                        <div class="progress-container">
                            <div class="progress-bar" id="progressBar">0%</div>
                        </div>

                        <script src="./script.js"></script>

                        <h1> Question `+ questionActuelle +` : </h1>
                        <h2> Notion : `Ecriture en PHP` <!-- Avoir la notion de la question avec JS--> </h2>
                        <h2> Difficulté : `+ difficulte +` <!-- Avoir la difficulté de la question avec JS--> </h2>

                        <br>
                        <!-- Pour un QCM -->

                        <h3> "Vous devez utilisr VSCODE" </h3>
                        <div id="zoneRep">
                             <div class="reponseBox" id="rep-1" >'Intellij'</div>
<div class="reponseBox" id="rep0" >'rien'</div>
<div class="reponseBox" id="rep1" >'VS code'</div>
<div class="reponseBox" id="rep2" >'Eclypse'</div>

                        </div>
                        <footer>
                            <nav>
                                <div class="styleBut" id="btnPreced">Précédent</div>
                                <div class="styleBut" id="valider">Valider</div>
                                <div class="styleBut" id="feedBack">Feedback</div>
                                <div class="styleBut" id="btnSuiv">Suivant</div>
                            </nav>
                        </footer>
                    </body>
                    </html>`;

    creerHtml(contenuPage);

    updateProgress();

    changerSelections();

    //Génération du popup
    const popup = document.getElementById('popup');
    const closePopupBtn = document.getElementById('closePopupBtn');
    const popupI = document.getElementById('popupI');
    const feedBackBtn = document.getElementById('feedBack');


    feedBackBtn.addEventListener('click', () => {
        showFeedBack(questionActuelleEstBon,points,texteExplications);
    });
    
    // Cacher le popup
    closePopupBtn.addEventListener('click', () => {
        cacheFeedBack();
    });
    
    // Optionnel : Fermer le popup en cliquant en dehors
    popup.addEventListener('click', (event) => 
    {
        if (event.target === popup) {
            cacheFeedBack();
        }
    });



    //Gestion du timer
    if(estChronometrer)
    {
        startTimerQuestion(tempsDeReponse);
    }else{
        document.getElementById('chrono').textContent = "Temps estimé : " + tempsDeReponse;
    }



    //Timer pour les questions
    function startTimerQuestion(tempsDeReponse) 
    {
        if(estChronometrer)
        {
            isRunning = true;
            let tps = tempsDeReponse;
            timer = setInterval(() => {
                tps--;
                if(document.getElementById('chrono') != null)
                    document.getElementById('chrono').textContent = "Compte à rebours : " + formatTime(tps) + " secondes";
                if(tps <= 0)
                {
                    stopTimerQuestion();
                    verifierReponses(bonnesRep, points)
                    validerQuestion(bonnesRep, 'QCM');
                    showFeedBack(questionActuelleEstBon, points, texteExplications);
                }
            }, 1000);
        }
    }


    // Fonction pour arrêter le chronomètre
    function stopTimerQuestion() 
    {
        if(estChronometrer)
        {
            isRunning = false;
            clearInterval(timer);
        }
    }


    if(tabCompletion[questionActuelle])
    {
        afficherReponse(bonnesRep, 'QCM');
    }

    // Mettre à jour les événements de clic

    document.getElementById("rep1").onclick = function() { clicRep(0, "QCM") };
    document.getElementById("rep2").onclick = function() { clicRep(1, "QCM") };
    document.getElementById("rep3").onclick = function() { clicRep(2, "QCM") };
    document.getElementById("rep4").onclick = function() { clicRep(3, "QCM") };
    
    document.getElementById("btnPreced").onclick = function() {if(!estChronometrer){questionPrecedante(), question1()}};
    document.getElementById("btnSuiv").onclick = function() {if(estChronometrer){if(tabCompletion[questionActuelle]){questionSuivante(), question2()}}else{questionSuivante(), question2()}};

    document.getElementById("valider").onclick = function() 
    {
        if(!tabCompletion[questionActuelle])
        {
            verifierReponses(bonnesRep, points)
            validerQuestion(bonnesRep, 'QCM');
            showFeedBack(questionActuelleEstBon ,points, texteExplications);
            stopTimerQuestion();
        }
    };
}

function question3()
{
    //Variables
    let bonnesRep = [false,true,];
    const difficulte = "Tres facile";
    const tempsDeReponse = 18;
    let points = 1;
    let texteExplications = "avec $nomVar = Tests";



    let contenuPage =`<!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <title>QCMBuilder</title>
                        <link href="style.css" rel="stylesheet">
                    </head>
                    <body>
                        <div id="popup" class="hiddenP">
                            <div id="popupI" class="hidden">
                                <span id="closePopupBtn" class="close">&times;</span>
                                <h2 id="estReponseBonne"></h2>
                                <p id="textFeedBack"></p>
                            </div>
                        </div>


                        <div class="compteurs">
                            <div class="timer, compteurs-div" id="chrono">00:00</div>
                            <div class="compteurs-div">Questions traitées : <span id="questionsDone">0</span>/`+ nbQuestion +`</div>
                            <div class="compteurs-div">Question sur `+points+` points</div>
                            <div class="compteurs-div" id="note">Total des points : `+ totalPoints+` / `+ noteMax +`</div>
                        </div>
                        <div class="progress-container">
                            <div class="progress-bar" id="progressBar">0%</div>
                        </div>

                        <script src="./script.js"></script>

                        <h1> Question `+ questionActuelle +` : </h1>
                        <h2> Notion : `Ecriture en PHP` <!-- Avoir la notion de la question avec JS--> </h2>
                        <h2> Difficulté : `+ difficulte +` <!-- Avoir la difficulté de la question avec JS--> </h2>

                        <br>
                        <!-- Pour un QCM -->

                        <h3> "avec $nomVar = Tests" </h3>
                        <div id="zoneRep">
                             <div class="reponseBox" id="rep-1" >'Machin'</div>
<div class="reponseBox" id="rep0" >'Chouette'</div>

                        </div>
                        <footer>
                            <nav>
                                <div class="styleBut" id="btnPreced">Précédent</div>
                                <div class="styleBut" id="valider">Valider</div>
                                <div class="styleBut" id="feedBack">Feedback</div>
                                <div class="styleBut" id="btnSuiv">Suivant</div>
                            </nav>
                        </footer>
                    </body>
                    </html>`;

    creerHtml(contenuPage);

    updateProgress();

    changerSelections();

    //Génération du popup
    const popup = document.getElementById('popup');
    const closePopupBtn = document.getElementById('closePopupBtn');
    const popupI = document.getElementById('popupI');
    const feedBackBtn = document.getElementById('feedBack');


    feedBackBtn.addEventListener('click', () => {
        showFeedBack(questionActuelleEstBon,points,texteExplications);
    });
    
    // Cacher le popup
    closePopupBtn.addEventListener('click', () => {
        cacheFeedBack();
    });
    
    // Optionnel : Fermer le popup en cliquant en dehors
    popup.addEventListener('click', (event) => 
    {
        if (event.target === popup) {
            cacheFeedBack();
        }
    });



    //Gestion du timer
    if(estChronometrer)
    {
        startTimerQuestion(tempsDeReponse);
    }else{
        document.getElementById('chrono').textContent = "Temps estimé : " + tempsDeReponse;
    }



    //Timer pour les questions
    function startTimerQuestion(tempsDeReponse) 
    {
        if(estChronometrer)
        {
            isRunning = true;
            let tps = tempsDeReponse;
            timer = setInterval(() => {
                tps--;
                if(document.getElementById('chrono') != null)
                    document.getElementById('chrono').textContent = "Compte à rebours : " + formatTime(tps) + " secondes";
                if(tps <= 0)
                {
                    stopTimerQuestion();
                    verifierReponses(bonnesRep, points)
                    validerQuestion(bonnesRep, 'QCM');
                    showFeedBack(questionActuelleEstBon, points, texteExplications);
                }
            }, 1000);
        }
    }


    // Fonction pour arrêter le chronomètre
    function stopTimerQuestion() 
    {
        if(estChronometrer)
        {
            isRunning = false;
            clearInterval(timer);
        }
    }


    if(tabCompletion[questionActuelle])
    {
        afficherReponse(bonnesRep, 'QCM');
    }

    // Mettre à jour les événements de clic

    document.getElementById("rep1").onclick = function() { clicRep(0, "QCM") };
    document.getElementById("rep2").onclick = function() { clicRep(1, "QCM") };
    document.getElementById("rep3").onclick = function() { clicRep(2, "QCM") };
    document.getElementById("rep4").onclick = function() { clicRep(3, "QCM") };
    
    document.getElementById("btnPreced").onclick = function() {if(!estChronometrer){questionPrecedante(), question1()}};
    document.getElementById("btnSuiv").onclick = function() {if(estChronometrer){if(tabCompletion[questionActuelle]){questionSuivante(), question2()}}else{questionSuivante(), question2()}};

    document.getElementById("valider").onclick = function() 
    {
        if(!tabCompletion[questionActuelle])
        {
            verifierReponses(bonnesRep, points)
            validerQuestion(bonnesRep, 'QCM');
            showFeedBack(questionActuelleEstBon ,points, texteExplications);
            stopTimerQuestion();
        }
    };
}
