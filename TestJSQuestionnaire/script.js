//Constantes et var communes
const nbQuestion = 7;
const estChronometrer = true;
const dureeTotal = 131; //Secondes
const noteMax = 14;


//Init variables
let questionActuelleEstBon = false;
let questionActuelle = 1;
let completion = 0;
let totalPoints = 0;
let timer;
let tabCompletion = [false,false,false,false,false,false,false]

const imgTF =' <img class="imgDif" src="./data/imgDif/TF.png">';
const imgF  =' <img class="imgDif" src="./data/imgDif/F.png">';
const imgM  =' <img class="imgDif" src="./data/imgDif/M.png">';
const imgD  =' <img class="imgDif" src="./data/imgDif/D.png">';

let tabSelections = 
[
[],
[false,false,false,false,],
[false,false,false,false,],
[false,false,],
[],
[false,false,false,false,],
[],
[false,false,false,false,false,],];
//Pour le qcm à relier
let lignesQ4 = [[[],[]],[[],[]],[[],[]],[[],[]],];
let lignesQ6 = [[[],[]],[[],[]],[[],[]],[[],[]],];


let nbEliminationQ5 = [false,false,false,false,];

function resetVariables()
{
    questionActuelle = 1;
    completion = 0;
    timer;
    seconds = 0;
    totalPoints = 0;
    tabCompletion = [false,false,false,false,false,false,false];
	nbEliminationQ5 = [false,false,false,false,];

    docAfficher = false;

	tabSelections = 
[
[],
[false,false,false,false,],
[false,false,false,false,],
[false,false,],
[],
[false,false,false,false,],
[],
[false,false,false,false,false,],];
	lignesQ4 = [[[],[]],[[],[]],[[],[]],[[],[]],];
lignesQ6 = [[[],[]],[[],[]],[[],[]],[[],[]],];

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
	document.getElementById("valider").classList.add("griser");
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
                for(let i = 1 ; document.getElementById("rep" + i) != null ; i++)
                {
                    if(document.getElementById("rep" + i).classList.contains('selected'))
                    {
                        document.getElementById("rep" + i).classList.remove('selected');
                    }
                }

                       				for(let i = 0 ; i < tabSelections[questionActuelle].length ; i++)
				{
					if(i != index)
					{
						tabSelections[questionActuelle][i] = false;
					}else{
						tabSelections[questionActuelle][i] = true;
					}
				}
                document.getElementById("rep" + (index+1)).classList.add('selected');
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
                            <h1>Bienvenue dans un Questionnaire !</h1>
                            <h2>TestJSQuestionnaire<!-- Avoir la matiere avec JS --></h2>
                            <h2>Temps pour finir : 131 Secondes<!-- Avoir la durér avec JS --></h2>
                            <h2>Sur la matière : DevWEB <!-- Avoir la matiere avec JS --></h2>
                            <h2>Sur les notions suivante :</h2>
                            <ul>
<li> Ecriture en PHP </li>
                            </ul> 
                            <h2> Qui contient :</h2>
                            <p>
                                1 Question `+imgTF+` <br> <!-- Avoir le nb Question avec JS -->
                                1 Question `+imgF+` <br> <!-- Avoir le nb Question avec JS -->
                                4 Question `+imgM+` <br> <!-- Avoir le nb Question avec JS -->
                                1 Question `+imgD+` <!-- Avoir le nb Question avec JS -->
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
                        <h2> Ressource : DevWEB</h2>
                        <h2> Nombre de questions : `+ nbQuestion +` dont 1 `+ imgTF +` , 1 ` + imgF +` , 4 `+ imgM +` et 1 `+ imgD + `</h2>
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
			for(let v = 0 ; v < 2 ; v++)
			{
				if ( j < lignes.length && lignes[index][j][v] != null) 
				{
					let leftElement = document.getElementById("rep" + ((lignes[index][j][0])+1));
					let rightElement = document.getElementById("rep" + ((lignes[index][j][1])+1));
	

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
    let notion = 'Ecriture en PHP';



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


                   <header>
                        <div class="compteurs">
                            <div class="timer, compteurs-div" id="chrono">00:00</div>
                            <div class="compteurs-div">Questions traitées : <span id="questionsDone">0</span>/`+ nbQuestion +`</div>
                            <div class="compteurs-div">Question sur `+points+` points</div>
                            <div class="compteurs-div" id="note">Total des points : `+ totalPoints+` / `+ noteMax +`</div>
                        </div>
                        <div class="progress-container">
                            <div class="progress-bar" id="progressBar">0%</div>
                        </div>
                   </header>

                        <script src="./script.js"></script>

                        <h1> Question `+ questionActuelle +` : </h1>
                        <h2> Notion : `+ notion +` <!-- Avoir la notion de la question avec JS--> </h2>
                        <h2> Difficulté : `+ difficulte +` <!-- Avoir la difficulté de la question avec JS--> </h2>

                        <br>
                        <!-- Pour un QCM -->

                        <h2> Type : QCM ( Il y a plusieurs réponses possibles )</h2>                        <h3> "Comment utiliser le PHP ?" </h3>
                        <div id="zoneRep">
                             <div class="reponseBox" id="rep1" >'XAMPP'</div>
<div class="reponseBox" id="rep2" >'Docker'</div>
<div class="reponseBox" id="rep3" >'rien'</div>
<div class="reponseBox" id="rep4" >'Test'</div>

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
        		document.getElementById("valider").classList.add("griser");
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
    let bonnesRep = [true,false,true,false,];
    const difficulte = "Facile";
    const tempsDeReponse = 15;
    let points = 1;
    let texteExplications = "Vous devez utilisr VSCODE";
    let notion = 'Ecriture en PHP';



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


                   <header>
                        <div class="compteurs">
                            <div class="timer, compteurs-div" id="chrono">00:00</div>
                            <div class="compteurs-div">Questions traitées : <span id="questionsDone">0</span>/`+ nbQuestion +`</div>
                            <div class="compteurs-div">Question sur `+points+` points</div>
                            <div class="compteurs-div" id="note">Total des points : `+ totalPoints+` / `+ noteMax +`</div>
                        </div>
                        <div class="progress-container">
                            <div class="progress-bar" id="progressBar">0%</div>
                        </div>
                   </header>

                        <script src="./script.js"></script>

                        <h1> Question `+ questionActuelle +` : </h1>
                        <h2> Notion : `+ notion +` <!-- Avoir la notion de la question avec JS--> </h2>
                        <h2> Difficulté : `+ difficulte +` <!-- Avoir la difficulté de la question avec JS--> </h2>

                        <br>
                        <!-- Pour un QCM -->

                        <h2> Type : QCM ( Il y a plusieurs réponses possibles )</h2>                        <h3> "Quels outils ?" </h3>
                        <div id="zoneRep">
                             <div class="reponseBox" id="rep1" >'Intellij'</div>
<div class="reponseBox" id="rep2" >'rien'</div>
<div class="reponseBox" id="rep3" >'VS code'</div>
<div class="reponseBox" id="rep4" >'Eclypse'</div>

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
        		document.getElementById("valider").classList.add("griser");
        afficherReponse(bonnesRep, 'QCM');
    }

    // Mettre à jour les événements de clic

document.getElementById("rep1").onclick = function() { clicRep(0, "QCM") };
document.getElementById("rep2").onclick = function() { clicRep(1, "QCM") };
document.getElementById("rep3").onclick = function() { clicRep(2, "QCM") };
document.getElementById("rep4").onclick = function() { clicRep(3, "QCM") };

    
    document.getElementById("btnPreced").onclick = function() {if(!estChronometrer){questionPrecedante(), question1()}};
    document.getElementById("btnSuiv").onclick = function() {if(estChronometrer){if(tabCompletion[questionActuelle]){questionSuivante(), question3()}}else{questionSuivante(), question3()}};

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
    let notion = 'Ecriture en PHP';



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


                   <header>
                        <div class="compteurs">
                            <div class="timer, compteurs-div" id="chrono">00:00</div>
                            <div class="compteurs-div">Questions traitées : <span id="questionsDone">0</span>/`+ nbQuestion +`</div>
                            <div class="compteurs-div">Question sur `+points+` points</div>
                            <div class="compteurs-div" id="note">Total des points : `+ totalPoints+` / `+ noteMax +`</div>
                        </div>
                        <div class="progress-container">
                            <div class="progress-bar" id="progressBar">0%</div>
                        </div>
                   </header>

                        <script src="./script.js"></script>

                        <h1> Question `+ questionActuelle +` : </h1>
                        <h2> Notion : `+ notion +` <!-- Avoir la notion de la question avec JS--> </h2>
                        <h2> Difficulté : `+ difficulte +` <!-- Avoir la difficulté de la question avec JS--> </h2>

                        <br>
                        <!-- Pour un QCM -->

                        <h2> Type : QCM à choix unique ( Il n'y a qu'une seule bonne réponse )</h2>                        <h3> "Comment definir une var ?" </h3>
                        <div id="zoneRep">
                             <div class="reponseBox" id="rep1" >'Machin'</div>
<div class="reponseBox" id="rep2" >'Chouette'</div>

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
        		document.getElementById("valider").classList.add("griser");
        afficherReponse(bonnesRep, 'QCM');
    }

    // Mettre à jour les événements de clic

document.getElementById("rep1").onclick = function() { clicRep(0, "vrai-faux") };
document.getElementById("rep2").onclick = function() { clicRep(1, "vrai-faux") };

    
    document.getElementById("btnPreced").onclick = function() {if(!estChronometrer){questionPrecedante(), question2()}};
    document.getElementById("btnSuiv").onclick = function() {if(estChronometrer){if(tabCompletion[questionActuelle]){questionSuivante(), question4()}}else{questionSuivante(), question4()}};

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

async function question4()
{
    //Variables
    let reponses = [[[0,7],[0,7]],[[2,5],[2,5]],[[4,1],[4,1]],[[6,3],[6,3]],]
                    
                
    const difficulte = 'Moyen';
    const tempsDeReponse = 23;
    let points = 3;
    let texteExplications = 'Explications';
    let notion = 'Ecriture en PHP';

    


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


                    <header>
                        <div class="compteurs">
                            <div class="timer, compteurs-div" id="chrono">00:00</div>
                            <div class="compteurs-div">Questions traitées : <span id="questionsDone">0</span>/`+ nbQuestion +`</div>
                            <div class="compteurs-div">Question sur `+points+` points</div>
                            <div class="compteurs-div" id="note">Total des points : `+ totalPoints+` / `+ noteMax +`</div>
                        </div>
                        <div class="progress-container">
                            <div class="progress-bar" id="progressBar">0%</div>
                        </div>
                   </header>
                        <script src="./script.js"></script>

                        <h1> Question `+ questionActuelle +` : </h1>
                        <h2> Notion : `+ notion +` <!-- Avoir la notion de la question avec JS--> </h2>
                        <h2> Difficulté : `+ difficulte +` <!-- Avoir la difficulté de la question avec JS--> </h2>

                        <br>
                        <!-- Pour un QCM -->
                        <h2> Type : Association d'éléments ( Un seul élément à gauche pour un seul à droite ) </h2>
                        <h3> "Reliez les éléments entre eux :" </h3>
                        <!--<img class="imgQuestion" src="./src/14.jpg" id="imgTxt" draggable="false">!-->

                        <div id="zoneRepAssos">
                            <div class="casesInternes" id="repGauche">
<div class="reponseBoxAssos" id="rep1">La deux</div>
<div class="reponseBoxAssos" id="rep3">quoi</div>
<div class="reponseBoxAssos" id="rep5">La 5</div>
<div class="reponseBoxAssos" id="rep7">La une</div>
                            </div>
                            <div><canvas id="canvas"></canvas></div>
                            <div class="casesInternes" id="repDroite">
<div class="reponseBoxAssos" id="rep2">La 6</div>
<div class="reponseBoxAssos" id="rep4">La trois</div>
<div class="reponseBoxAssos" id="rep6">feur</div>
<div class="reponseBoxAssos" id="rep8">La 4</div>
                            </div>
                        </div>


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

    await creerHtml(contenuPage);
                    
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




    if(tabCompletion[questionActuelle])
    {
		document.getElementById("valider").classList.add("griser");
        afficherReponse(reponses, 'EA');
    }


    //Gestion du timer
    if(estChronometrer)
    {
        startTimerQuestion(tempsDeReponse);
    }else{
        document.getElementById('chrono').textContent = "Temps estimé : " + tempsDeReponse;
    }



    //Timer pour les questions
    function startTimerQuestion(tempsDeReponse) {
        isRunning = true;
        let tps = tempsDeReponse;
        timer = setInterval(() => {
            tps--;
            if(document.getElementById('chrono') != null)
                document.getElementById('chrono').textContent = "Compte à rebours : " + formatTime(tps) + " secondes";
            if(tps <= 0)
            {
                stopTimerQuestion();
                if(!tabCompletion[questionActuelle])
                {
                    drawLines(lignesQ4, 'erreurs');
                }

                checkErrors();
                validerQuestion(reponses,"EA");

                if(tabCompletion[questionActuelle])
                {
                    showFeedBack(questionActuelleEstBon, points, texteExplications);
                }            }
        }, 1000);
    }

    // Fonction pour arrêter le chronomètre
    function stopTimerQuestion() {
        isRunning = false;
        tps = 0;
        clearInterval(timer);
    }

    const canvas = document.getElementById('canvas');
    const ctx = canvas.getContext('2d');

    canvas.height = 250 * reponses.length;

    
    canvas.width = canvas.offsetWidth;
    
    // Recalcule et redessine si la fenêtre est redimensionnée
    window.onresize = drawLines;

    // Récupérer les éléments à connecter
    const leftElementsDiv = document.querySelectorAll("#repGauche .reponseBoxAssos");
    const rightElementsDiv = document.querySelectorAll("#repDroite .reponseBoxAssos");

    let leftElements = [];
    let rightElements = [];

    let selectGauche = -1;
    let selectDroite = -1;


    leftElementsDiv.forEach(function(element, index, leftElement) {
        leftElements[index] = index;
    });

    rightElementsDiv.forEach(function(element, index, rightElementsDiv) {
        rightElements[index] = index;
    });


    function checkErrors()
    {
        let estBon = true;

        let cptBon = 0;

        let tR = reponses.sort();
        let tL = lignesQ4.sort();


        for(let i = 0 ; i < reponses.length ; i++)
        {

            if((tR[i][0][0] === tL[i][0][0] && tR[i][0][1] === tL[i][0][1]) || (tR[i][0][1] === tL[i][0][0] && tR[i][0][0] === tL[i][0][1]))
            {
                cptBon++;
            }

            console.log(cptBon)
        }

        if(cptBon < reponses.length)
        {
            estBon = false;
        }


        if(!tabCompletion[questionActuelle] && estBon)
        {        
            totalPoints += points;
            document.getElementById("note").innerHTML = ("Total des points : " + totalPoints + " / " + noteMax)
        }

        questionActuelleEstBon = estBon;

        return estBon;
    }


    function selection(element, index)
    {
        if(tabCompletion[questionActuelle])
        {
            return;
        }

        for(let i = 0 ; i < reponses.length*2 ; i++)
        {
            document.getElementById("rep" + (i + 1)).classList.remove('selected');
        }


        if(index%2 == 0)
        {

            if(selectGauche == index)
            {
                selectGauche = -1;
            }else{
                selectGauche = index;
            }
        }else{
            if(selectDroite == index)
            {
                selectDroite = -1;
            }else{
                if(selectGauche > -1)
                    selectDroite = index;
            }
        }

        if(selectGauche >= 0 && selectDroite >= 0)
        {
            for(let i = 0 ; i < lignesQ4.length ; i++)
            {
                if((lignesQ4[i][selectGauche%2][0] == selectGauche && lignesQ4[i][selectGauche%2][1] == selectDroite) || (lignesQ4[i][selectDroite%2][0] == selectGauche && lignesQ4[i][selectDroite%2][1] == selectDroite))
                {
                    lignesQ4[i][selectGauche%2] = [];
                    lignesQ4[i][selectDroite%2] = [];
                    document.getElementById("rep" + (selectGauche+1)).classList.remove('selected');
                    document.getElementById("rep" + (selectDroite+1)).classList.remove('selected');

                    selectGauche = -1;
                    selectDroite = -1;


                    ctx.clearRect(0, 0, canvas.width, canvas.height);

                    drawLines(lignesQ4);

                    return;
                }

                if(lignesQ4[i][0][0] == selectGauche)
                {
                    document.getElementById("rep" + (selectGauche+1)).classList.remove('selected');
                    selectGauche = -1;
                    selectDroite = -1;
                    return;
                }

                if(lignesQ4[i][1][1] == selectDroite)
                {
                    document.getElementById("rep" + (selectDroite+1)).classList.remove('selected');
                    selectGauche = -1;
                    selectDroite = -1;
                    return;
                }
            }

            lignesQ4[Math.floor(index/2)][selectGauche%2][0] = selectGauche;
            lignesQ4[Math.floor(index/2)][selectGauche%2][1] = selectDroite;

            lignesQ4[Math.floor(index/2)][selectDroite%2][0] = selectGauche;
            lignesQ4[Math.floor(index/2)][selectDroite%2][1] = selectDroite;
            
            selectDroite = -1;
            selectGauche = -1;
        }

        if(selectGauche > -1)
            document.getElementById("rep" + (selectGauche+1)).classList.toggle('selected');
        if(selectDroite > -1 && selectGauche > -1)
            document.getElementById("rep" + (selectDroite+1)).classList.toggle('selected');



        drawLines(lignesQ4);
    }

    window.onload = function() {
        drawLines(lignesQ4);
    }



    if(tabCompletion[questionActuelle])
    {
        drawLines(lignesQ4, 'erreurs');
        drawLines(reponses, 'correction');
    }
    
    updateProgress();

    changerSelections();



    // Mettre à jour les événements de clic

    if(!tabCompletion[questionActuelle])
    {
        document.getElementById("rep1").onclick = function() { selection(leftElements, 0) };
        document.getElementById("rep2").onclick = function() { selection(rightElements, 1) };
        document.getElementById("rep3").onclick = function() { selection(leftElements, 2) };
        document.getElementById("rep4").onclick = function() { selection(rightElements, 3) };
        document.getElementById("rep5").onclick = function() { selection(leftElements, 4) };
        document.getElementById("rep6").onclick = function() { selection(rightElements, 5) };
        document.getElementById("rep7").onclick = function() { selection(leftElements, 6) };
        document.getElementById("rep8").onclick = function() { selection(rightElements, 7) };

        document.getElementById("valider").onclick = function() 
        {
            if(!tabCompletion[questionActuelle])
            {
                drawLines(lignesQ4, 'erreurs');
            }

            checkErrors();
            validerQuestion(reponses,"EA");
            stopTimerQuestion();

            if(tabCompletion[questionActuelle])
            {
                showFeedBack(questionActuelleEstBon, points, texteExplications);
            }
        };
    }

    

    
    document.getElementById("btnPreced").onclick = function() {if(!estChronometrer){questionPrecedante(), question3()}}
    document.getElementById("btnSuiv").onclick = function() {if(estChronometrer){if(tabCompletion[questionActuelle]){questionSuivante(), question5()}}else{questionSuivante(), question5()}};

}function question5()
{
    //Variables
    let bonnesRep = [false,false,false,true,];
    let eliminations = [2,1,-1,-1,];
    let nbEliminations = 0;
    let points = 2;
    let coutElimination = 0.5 ;
    let texteExplications = 'Temp A remplacer';



    const difficulte = 'Difficile';
    const tempsDeReponse = 12;



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


                   <header>
                        <div class="compteurs">
                            <div class="timer, compteurs-div" id="chrono">00:00</div>
                            <div class="compteurs-div">Questions traitées : <span id="questionsDone">0</span>/`+ nbQuestion +`</div>
                            <div class="compteurs-div">Question sur `+points+` points</div>
                            <div class="compteurs-div" id="note">Total des points : `+ totalPoints+` / `+ noteMax +`</div>
                        </div>
                        <div class="progress-container">
                            <div class="progress-bar" id="progressBar">0%</div>
                        </div>
                   </header>

                        <script src="./script.js"></script>

                        <h1> Question `+ questionActuelle +` : </h1>
                        <h2> Difficulté : `+ difficulte +` <!-- Avoir la difficulté de la question avec JS--> </h2>

                        <br>
                        <!-- Pour un Elimination -->
                        <h2> Type : Elimination de réponses ( Cliquer sur supprimer enlèvera une mauvaise réponse mais vous enlèvera des points en contre partie ) </h2>
                        <h3> "Choisissez la bonne réponse :" </h3>
                        <div id="zoneRep">
                        <div class="reponseBox" id="rep1" >'Yop !'</div>
<div class="reponseBox" id="rep2" >'Salut !'</div>
<div class="reponseBox" id="rep3" >'Hey !'</div>
<div class="reponseBox" id="rep4" >'Nope !'</div>
                        </div>
                        <div class="styleBut" id=btnSup>Eliminer une réponse</div>
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
        showFeedBack(questionActuelleEstBon, points, texteExplications);
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
    function startTimerQuestion(tempsDeReponse) {
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
                validerQuestion(bonnesRep,"QCM");
                showFeedBack(questionActuelleEstBon    ,points,texteExplications);
            }
        }, 1000);
    }

    // Fonction pour arrêter le chronomètre
    function stopTimerQuestion() {
        isRunning = false;
        clearInterval(timer);
    }


    function suppQuestion(eliminations)
    {
        if(!tabCompletion[questionActuelle])
        {
            for(let i = 0 ;  i < eliminations.length ; i++)
            {
                if(nbEliminations+1 == eliminations[i])
                {
                    document.getElementById("rep" + (i+1)).classList.remove('selected');
                    document.getElementById("rep" + (i+1)).classList.add('eliminer');
                    nbEliminationQ5[i] = true;
                    nbEliminations++;
                    points -= coutElimination;
                   if(points < 0)                    {                        points = 0;                     }                }
            }
        }
    }



    if(tabCompletion[questionActuelle])
    {
		document.getElementById("valider").classList.add("griser");
        afficherReponse(bonnesRep, 'QCM');
    }



    // Mettre à jour les événements de clic

    if(!tabCompletion[questionActuelle])
    {
    document.getElementById("rep1").onclick = function() { clicRep(0, "elimination") };
document.getElementById("rep2").onclick = function() { clicRep(1, "elimination") };
document.getElementById("rep3").onclick = function() { clicRep(2, "elimination") };
document.getElementById("rep4").onclick = function() { clicRep(3, "elimination") };

        document.getElementById("btnSup").onclick = function() { suppQuestion(eliminations) };
        document.getElementById("valider").onclick = function()
         {
            if(!tabCompletion[questionActuelle])
            {
                stopTimerQuestion();
                verifierReponses(bonnesRep, points)
                validerQuestion(bonnesRep, "QCM"); 
                showFeedBack(questionActuelleEstBon    ,points,texteExplications);
            }
        };
    }else{

        for(let i = 0 ; i < nbEliminationQ5.length ; i++)
        {
            if(nbEliminationQ5[i] == true)
            {
                document.getElementById("rep" + (i+1)).classList.add('eliminer');
            }
        }
    }

    document.getElementById("btnPreced").onclick = function() {if(!estChronometrer){questionPrecedante(), question4()}}
    document.getElementById("btnSuiv").onclick = function() {if(estChronometrer){if(tabCompletion[questionActuelle]){questionSuivante(), question6()}}else{questionSuivante(), question6()}};
}

async function question6()
{
    //Variables
    let reponses = [[[0,3],[0,3]],[[2,5],[2,5]],[[4,1],[4,1]],[[7,6],[7,6]],]
                    
                
    const difficulte = 'Moyen';
    const tempsDeReponse = 23;
    let points = 3;
    let texteExplications = 'Explications';
    let notion = 'Ecriture en PHP';

    


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


                    <header>
                        <div class="compteurs">
                            <div class="timer, compteurs-div" id="chrono">00:00</div>
                            <div class="compteurs-div">Questions traitées : <span id="questionsDone">0</span>/`+ nbQuestion +`</div>
                            <div class="compteurs-div">Question sur `+points+` points</div>
                            <div class="compteurs-div" id="note">Total des points : `+ totalPoints+` / `+ noteMax +`</div>
                        </div>
                        <div class="progress-container">
                            <div class="progress-bar" id="progressBar">0%</div>
                        </div>
                   </header>
                        <script src="./script.js"></script>

                        <h1> Question `+ questionActuelle +` : </h1>
                        <h2> Notion : `+ notion +` <!-- Avoir la notion de la question avec JS--> </h2>
                        <h2> Difficulté : `+ difficulte +` <!-- Avoir la difficulté de la question avec JS--> </h2>

                        <br>
                        <!-- Pour un QCM -->
                        <h2> Type : Association d'éléments ( Un seul élément à gauche pour un seul à droite ) </h2>
                        <h3> "Asso 2 :" </h3>
                        <!--<img class="imgQuestion" src="./src/14.jpg" id="imgTxt" draggable="false">!-->

                        <div id="zoneRepAssos">
                            <div class="casesInternes" id="repGauche">
<div class="reponseBoxAssos" id="rep1">La deuxE</div>
<div class="reponseBoxAssos" id="rep3">La une</div>
<div class="reponseBoxAssos" id="rep5">La deux</div>
<div class="reponseBoxAssos" id="rep7">Feur</div>
                            </div>
                            <div><canvas id="canvas"></canvas></div>
                            <div class="casesInternes" id="repDroite">
<div class="reponseBoxAssos" id="rep2">La 4</div>
<div class="reponseBoxAssos" id="rep4">La 4</div>
<div class="reponseBoxAssos" id="rep6">La trois</div>
<div class="reponseBoxAssos" id="rep8">Coubeh</div>
                            </div>
                        </div>


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

    await creerHtml(contenuPage);
                    
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




    if(tabCompletion[questionActuelle])
    {
		document.getElementById("valider").classList.add("griser");
        afficherReponse(reponses, 'EA');
    }


    //Gestion du timer
    if(estChronometrer)
    {
        startTimerQuestion(tempsDeReponse);
    }else{
        document.getElementById('chrono').textContent = "Temps estimé : " + tempsDeReponse;
    }



    //Timer pour les questions
    function startTimerQuestion(tempsDeReponse) {
        isRunning = true;
        let tps = tempsDeReponse;
        timer = setInterval(() => {
            tps--;
            if(document.getElementById('chrono') != null)
                document.getElementById('chrono').textContent = "Compte à rebours : " + formatTime(tps) + " secondes";
            if(tps <= 0)
            {
                stopTimerQuestion();
                if(!tabCompletion[questionActuelle])
                {
                    drawLines(lignesQ4, 'erreurs');
                }

                checkErrors();
                validerQuestion(reponses,"EA");

                if(tabCompletion[questionActuelle])
                {
                    showFeedBack(questionActuelleEstBon, points, texteExplications);
                }            }
        }, 1000);
    }

    // Fonction pour arrêter le chronomètre
    function stopTimerQuestion() {
        isRunning = false;
        tps = 0;
        clearInterval(timer);
    }

    const canvas = document.getElementById('canvas');
    const ctx = canvas.getContext('2d');

    canvas.height = 250 * reponses.length;

    
    canvas.width = canvas.offsetWidth;
    
    // Recalcule et redessine si la fenêtre est redimensionnée
    window.onresize = drawLines;

    // Récupérer les éléments à connecter
    const leftElementsDiv = document.querySelectorAll("#repGauche .reponseBoxAssos");
    const rightElementsDiv = document.querySelectorAll("#repDroite .reponseBoxAssos");

    let leftElements = [];
    let rightElements = [];

    let selectGauche = -1;
    let selectDroite = -1;


    leftElementsDiv.forEach(function(element, index, leftElement) {
        leftElements[index] = index;
    });

    rightElementsDiv.forEach(function(element, index, rightElementsDiv) {
        rightElements[index] = index;
    });


    function checkErrors()
    {
        let estBon = true;

        let cptBon = 0;

        let tR = reponses.sort();
        let tL = lignesQ6.sort();


        for(let i = 0 ; i < reponses.length ; i++)
        {

            if((tR[i][0][0] === tL[i][0][0] && tR[i][0][1] === tL[i][0][1]) || (tR[i][0][1] === tL[i][0][0] && tR[i][0][0] === tL[i][0][1]))
            {
                cptBon++;
            }

            console.log(cptBon)
        }

        if(cptBon < reponses.length)
        {
            estBon = false;
        }


        if(!tabCompletion[questionActuelle] && estBon)
        {        
            totalPoints += points;
            document.getElementById("note").innerHTML = ("Total des points : " + totalPoints + " / " + noteMax)
        }

        questionActuelleEstBon = estBon;

        return estBon;
    }


    function selection(element, index)
    {
        if(tabCompletion[questionActuelle])
        {
            return;
        }

        for(let i = 0 ; i < reponses.length*2 ; i++)
        {
            document.getElementById("rep" + (i + 1)).classList.remove('selected');
        }


        if(index%2 == 0)
        {

            if(selectGauche == index)
            {
                selectGauche = -1;
            }else{
                selectGauche = index;
            }
        }else{
            if(selectDroite == index)
            {
                selectDroite = -1;
            }else{
                if(selectGauche > -1)
                    selectDroite = index;
            }
        }

        if(selectGauche >= 0 && selectDroite >= 0)
        {
            for(let i = 0 ; i < lignesQ6.length ; i++)
            {
                if((lignesQ6[i][selectGauche%2][0] == selectGauche && lignesQ6[i][selectGauche%2][1] == selectDroite) || (lignesQ6[i][selectDroite%2][0] == selectGauche && lignesQ6[i][selectDroite%2][1] == selectDroite))
                {
                    lignesQ6[i][selectGauche%2] = [];
                    lignesQ6[i][selectDroite%2] = [];
                    document.getElementById("rep" + (selectGauche+1)).classList.remove('selected');
                    document.getElementById("rep" + (selectDroite+1)).classList.remove('selected');

                    selectGauche = -1;
                    selectDroite = -1;


                    ctx.clearRect(0, 0, canvas.width, canvas.height);

                    drawLines(lignesQ6);

                    return;
                }

                if(lignesQ6[i][0][0] == selectGauche)
                {
                    document.getElementById("rep" + (selectGauche+1)).classList.remove('selected');
                    selectGauche = -1;
                    selectDroite = -1;
                    return;
                }

                if(lignesQ6[i][1][1] == selectDroite)
                {
                    document.getElementById("rep" + (selectDroite+1)).classList.remove('selected');
                    selectGauche = -1;
                    selectDroite = -1;
                    return;
                }
            }

            lignesQ6[Math.floor(index/2)][selectGauche%2][0] = selectGauche;
            lignesQ6[Math.floor(index/2)][selectGauche%2][1] = selectDroite;

            lignesQ6[Math.floor(index/2)][selectDroite%2][0] = selectGauche;
            lignesQ6[Math.floor(index/2)][selectDroite%2][1] = selectDroite;
            
            selectDroite = -1;
            selectGauche = -1;
        }

        if(selectGauche > -1)
            document.getElementById("rep" + (selectGauche+1)).classList.toggle('selected');
        if(selectDroite > -1 && selectGauche > -1)
            document.getElementById("rep" + (selectDroite+1)).classList.toggle('selected');



        drawLines(lignesQ6);
    }

    window.onload = function() {
        drawLines(lignesQ6);
    }



    if(tabCompletion[questionActuelle])
    {
        drawLines(lignesQ6, 'erreurs');
        drawLines(reponses, 'correction');
    }
    
    updateProgress();

    changerSelections();



    // Mettre à jour les événements de clic

    if(!tabCompletion[questionActuelle])
    {
        document.getElementById("rep1").onclick = function() { selection(leftElements, 0) };
        document.getElementById("rep2").onclick = function() { selection(rightElements, 1) };
        document.getElementById("rep3").onclick = function() { selection(leftElements, 2) };
        document.getElementById("rep4").onclick = function() { selection(rightElements, 3) };
        document.getElementById("rep5").onclick = function() { selection(leftElements, 4) };
        document.getElementById("rep6").onclick = function() { selection(rightElements, 5) };
        document.getElementById("rep7").onclick = function() { selection(leftElements, 6) };
        document.getElementById("rep8").onclick = function() { selection(rightElements, 7) };

        document.getElementById("valider").onclick = function() 
        {
            if(!tabCompletion[questionActuelle])
            {
                drawLines(lignesQ6, 'erreurs');
            }

            checkErrors();
            validerQuestion(reponses,"EA");
            stopTimerQuestion();

            if(tabCompletion[questionActuelle])
            {
                showFeedBack(questionActuelleEstBon, points, texteExplications);
            }
        };
    }

    

    
    document.getElementById("btnPreced").onclick = function() {if(!estChronometrer){questionPrecedante(), question5()}}
    document.getElementById("btnSuiv").onclick = function() {if(estChronometrer){if(tabCompletion[questionActuelle]){questionSuivante(), question7()}}else{questionSuivante(), question7()}};

}

function question7()
{
    //Variables
    let bonnesRep = [false,false,false,false,true,];
    const difficulte = "Moyen";
    const tempsDeReponse = 20;
    let points = 2;
    let texteExplications = "Julien evidemment ";
    let notion = 'Ecriture en PHP';



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


                   <header>
                        <div class="compteurs">
                            <div class="timer, compteurs-div" id="chrono">00:00</div>
                            <div class="compteurs-div">Questions traitées : <span id="questionsDone">0</span>/`+ nbQuestion +`</div>
                            <div class="compteurs-div">Question sur `+points+` points</div>
                            <div class="compteurs-div" id="note">Total des points : `+ totalPoints+` / `+ noteMax +`</div>
                        </div>
                        <div class="progress-container">
                            <div class="progress-bar" id="progressBar">0%</div>
                        </div>
                   </header>

                        <script src="./script.js"></script>

                        <h1> Question `+ questionActuelle +` : </h1>
                        <h2> Notion : `+ notion +` <!-- Avoir la notion de la question avec JS--> </h2>
                        <h2> Difficulté : `+ difficulte +` <!-- Avoir la difficulté de la question avec JS--> </h2>

                        <br>
                        <!-- Pour un QCM -->

                        <h2> Type : QCM à choix unique ( Il n'y a qu'une seule bonne réponse )</h2>                        <h3> "Le vrai ou faux qui est le goat ? " </h3>
                        <div id="zoneRep">
                             <div class="reponseBox" id="rep1" >'Entoi-nne'</div>
<div class="reponseBox" id="rep2" >'Obein'</div>
<div class="reponseBox" id="rep3" >'Tybolt'</div>
<div class="reponseBox" id="rep4" >'Tymauter'</div>
<div class="reponseBox" id="rep5" >'Jeullian'</div>

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
        		document.getElementById("valider").classList.add("griser");
        afficherReponse(bonnesRep, 'QCM');
    }

    // Mettre à jour les événements de clic

document.getElementById("rep1").onclick = function() { clicRep(0, "vrai-faux") };
document.getElementById("rep2").onclick = function() { clicRep(1, "vrai-faux") };
document.getElementById("rep3").onclick = function() { clicRep(2, "vrai-faux") };
document.getElementById("rep4").onclick = function() { clicRep(3, "vrai-faux") };
document.getElementById("rep5").onclick = function() { clicRep(4, "vrai-faux") };

    
    document.getElementById("btnPreced").onclick = function() {if(!estChronometrer){questionPrecedante(), question6()}};
    document.getElementById("btnSuiv").onclick = function() {if(estChronometrer){if(tabCompletion[questionActuelle]){questionSuivante(), finQuestionnaire()}}else{questionSuivante(), finQuestionnaire()}};

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
