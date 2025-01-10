//Constantes et var communes
const nbQuestion = 5;
const estChronometrer = false;
const dureeTotal = 720; //Secondes
const noteMax = 15;


//Init variables
let questionActuelleEstBon = false;
let questionActuelle = 1;
let completion = 0;
let totalPoints = 0;
let timer;
let tabCompletion = [false,false,false,false,false]

const imgTF =' <img class="imgDif" src="./data/imgDif/TF.png">';
const imgF  =' <img class="imgDif" src="./data/imgDif/F.png">';
const imgM  =' <img class="imgDif" src="./data/imgDif/M.png">';
const imgD  =' <img class="imgDif" src="./data/imgDif/D.png">';

let tabSelections = 
[
[],
[false,false,],
[],
[false,false,],
[false,false,],
[],];
//Pour le qcm à relier
let lignesQ2 = [[[],[]],[[],[]],];


let nbEliminationQ5 = [];

function resetVariables()
{
    questionActuelle = 1;
    completion = 0;
    timer;
    seconds = 0;
    totalPoints = 0;
    tabCompletion = [false,false,false,false,false];
	nbEliminationQ5 = [];

    docAfficher = false;

	tabSelections = 
[
[],
[false,false,],
[],
[false,false,],
[false,false,],
[],];
	lignesQ2 = [[[],[]],[[],[]],];

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

            if(tabSelections[questionActuelle][index]){
                document.getElementById("img" + (index+1)).src = "./data/imgBtn/CheckPlein.png"
            }
            else{
                document.getElementById("img" + (index+1)).src = "./data/imgBtn/CheckVide.png"
            }
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
                    document.getElementById("img" + (i+1)).src = "./data/imgBtn/RadVide.png"
                }else{
                    tabSelections[questionActuelle][i] = true;
                    document.getElementById("img" + (i+1)).src = "./data/imgBtn/RadPlein.png"
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
                {                    if(tabSelections[questionActuelle][i] == true)
                    {
                        tabSelections[questionActuelle][i] = false;
                            document.getElementById("img" + (i+1)).src = "./data/imgBtn/RadVide.png";
                        }
                    }

                    tabSelections[questionActuelle][index] = true;
                    tmp.classList.toggle('selected');
                    document.getElementById("img" + (index+1)).src = "./data/imgBtn/RadPlein.png";
                }
            }
            break;
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
                            <h2>feeuuurr<!-- Avoir la matiere avec JS --></h2>
                            <h2>Temps pour finir : 720 Secondes<!-- Avoir la durér avec JS --></h2>
                            <h2>Sur la matière : Init dev <!-- Avoir la matiere avec JS --></h2>
                            <h2>Sur les notions suivante :</h2>
                            <ul>
<li> Les sous-classe </li>
                            </ul> 
                            <h2> Qui contient :</h2>
                            <p>
                                1 Question `+imgTF+` <br> <!-- Avoir le nb Question avec JS -->
                                3 Question `+imgF+` <br> <!-- Avoir le nb Question avec JS -->
                                1 Question `+imgM+` <br> <!-- Avoir le nb Question avec JS -->
                                0 Question `+imgD+` <!-- Avoir le nb Question avec JS -->
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
                        <h2> Ressource : Init dev</h2>
                        <h2> Nombre de questions : `+ nbQuestion +` dont 1 `+ imgTF +` , 3 ` + imgF +` , 1 `+ imgM +` et 0 `+ imgD + `</h2>
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
if(canvas != null){	const ctx = canvas.getContext('2d');


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
	}}
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
        }

    questionActuelleEstBon = estBon;

    return estBon;
}



function question1()
{
    //Variables
    let bonnesRep = [true,false,];
    const difficulte = "Facile";
    const tempsDeReponse = 30;
    let points = 1;
    let texteExplications = '<style>       <!--       -->     </style><p class=default>       <span style="color: #000000; font-family: Monospaced">         hgjhugju       </span>     </p>';
    let notion = 'Les sous-classe';



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
                        </div>
                        <div class="progress-container">
                            <div class="progress-bar" id="progressBar">0%</div>
                        </div>
                   </header>

                        <script src="./script.js"></script>
                   <div class="pageQuestion">
                        <h1> Question `+ questionActuelle +` : </h1>
                        <h2> Notion : `+ notion +`  </h2>
                        <h2> Difficulté : `+ difficulte +`  </h2>

                        <br>
                        <!-- Pour un QCM -->

                        <h2> Type : Question à choix unique</h2><img class="imgQuestion" src="./data/Question 1/Compléments/null" alt="null">
                        <h3> <style>
      <!--
      -->
    </style><p class=default>
      <span style="color: #000000; font-family: Monospaced">
        <b>rjyhffghjygyh</b>
      </span>
      <span style="color: #000000; font-family: Monospaced">
        
      </span>
    </p> </h3>
                                                <div id="zoneRep">
                             <div class="reponseBox" id="rep1" ><img id="img1" class="imgBtn" src="./data/imgBtn/RadVide.png">'Réponse 2'</div>
<div class="reponseBox" id="rep2" ><img id="img2" class="imgBtn" src="./data/imgBtn/RadVide.png">'Réponse 1'</div>

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

async function question2()
{
    //Variables
    let reponses = [[[0,3],[0,3]],[[2,1],[2,1]],]
                    
                
    const difficulte = 'Facile';
    const tempsDeReponse = 0;
    let points = 0;
    let texteExplications = '<style>       <!--       -->     </style><p class=default>       <span style="color: #000000; font-family: Monospaced">         aFSSFE       </span>     </p>';
    let notion = 'Les sous-classe';

    


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
                        </div>
                        <div class="progress-container">
                            <div class="progress-bar" id="progressBar">0%</div>
                        </div>
                   </header>
                        <script src="./script.js"></script>
                   <div class="pageQuestion">
                        <h1> Question `+ questionActuelle +` : </h1>
                        <h2> Notion : `+ notion +` </h2>
                        <h2> Difficulté : `+ difficulte +`  </h2>

                        <br>
                        <!-- Pour un QCM -->
                        <h2> Type : Association d'éléments ( Un seul élément à gauche pour un seul à droite ) </h2>
<img class="imgQuestion" src="./data/Question 4/Compléments/null" alt="null">

                        <h3> <style>
      <!--
      -->
    </style><p class=default>
      <span style="color: #000000; font-family: Monospaced">
        QESRGEFGGSE
      </span>
    </p> </h3>

                        <div id="zoneRepAssos">
                            <div class="casesInternes" id="repGauche">
<div class="reponseBoxAssos" id="rep1">Entité 1</div>
<div class="reponseBoxAssos" id="rep3">Entité 2</div>
                            </div>
                            <div><canvas id="canvas"></canvas></div>
                            <div class="casesInternes" id="repDroite">
<div class="reponseBoxAssos" id="rep2">Association 2</div>
<div class="reponseBoxAssos" id="rep4">Association 1</div>
                            </div>
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
        let tL = lignesQ2.sort();


        for(let i = 0 ; i < reponses.length ; i++)
        {

            if((tR[i][0][0] === tL[i][0][0] && tR[i][0][1] === tL[i][0][1]) || (tR[i][0][1] === tL[i][0][0] && tR[i][0][0] === tL[i][0][1]))
            {
                cptBon++;
            }

        }

        if(cptBon < reponses.length)
        {
            estBon = false;
        }


        if(!tabCompletion[questionActuelle] && estBon)
        {        
            totalPoints += points;
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
            for(let i = 0 ; i < lignesQ2.length ; i++)
            {
                if((lignesQ2[i][selectGauche%2][0] == selectGauche && lignesQ2[i][selectGauche%2][1] == selectDroite) || (lignesQ2[i][selectDroite%2][0] == selectGauche && lignesQ2[i][selectDroite%2][1] == selectDroite))
                {
                    lignesQ2[i][selectGauche%2] = [];
                    lignesQ2[i][selectDroite%2] = [];
                    document.getElementById("rep" + (selectGauche+1)).classList.remove('selected');
                    document.getElementById("rep" + (selectDroite+1)).classList.remove('selected');

                    selectGauche = -1;
                    selectDroite = -1;


                    ctx.clearRect(0, 0, canvas.width, canvas.height);

                    drawLines(lignesQ2);

                    return;
                }

                if(lignesQ2[i][0][0] == selectGauche)
                {
                    document.getElementById("rep" + (selectGauche+1)).classList.remove('selected');
                    selectGauche = -1;
                    selectDroite = -1;
                    return;
                }

                if(lignesQ2[i][1][1] == selectDroite)
                {
                    document.getElementById("rep" + (selectDroite+1)).classList.remove('selected');
                    selectGauche = -1;
                    selectDroite = -1;
                    return;
                }
            }

            lignesQ2[Math.floor(index/2)][selectGauche%2][0] = selectGauche;
            lignesQ2[Math.floor(index/2)][selectGauche%2][1] = selectDroite;

            lignesQ2[Math.floor(index/2)][selectDroite%2][0] = selectGauche;
            lignesQ2[Math.floor(index/2)][selectDroite%2][1] = selectDroite;
            
            selectDroite = -1;
            selectGauche = -1;
        }

        if(selectGauche > -1)
            document.getElementById("rep" + (selectGauche+1)).classList.toggle('selected');
        if(selectDroite > -1 && selectGauche > -1)
            document.getElementById("rep" + (selectDroite+1)).classList.toggle('selected');



        drawLines(lignesQ2);
    }

    window.onload = function() {
        drawLines(lignesQ2);
    }



    if(tabCompletion[questionActuelle])
    {
        drawLines(lignesQ2, 'erreurs');
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

        document.getElementById("valider").onclick = function() 
        {
            if(!tabCompletion[questionActuelle])
            {
                drawLines(lignesQ2, 'erreurs');
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

    

    
    document.getElementById("btnPreced").onclick = function() {if(!estChronometrer){questionPrecedante(), question1()}}
    document.getElementById("btnSuiv").onclick = function() {if(estChronometrer){if(tabCompletion[questionActuelle]){questionSuivante(), question3()}}else{questionSuivante(), question3()}};

}

function question3()
{
    //Variables
    let bonnesRep = [false,true,];
    const difficulte = "Facile";
    const tempsDeReponse = 660;
    let points = 3;
    let texteExplications = '<style>       <!--       -->     </style><p class=default>       <span style="color: #000000; font-family: Monospaced">         Die krakf       </span>     </p>';
    let notion = 'Les sous-classe';



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
                        </div>
                        <div class="progress-container">
                            <div class="progress-bar" id="progressBar">0%</div>
                        </div>
                   </header>

                        <script src="./script.js"></script>
                   <div class="pageQuestion">
                        <h1> Question `+ questionActuelle +` : </h1>
                        <h2> Notion : `+ notion +`  </h2>
                        <h2> Difficulté : `+ difficulte +`  </h2>

                        <br>
                        <!-- Pour un QCM -->

                        <h2> Type : Question à choix unique</h2><img class="imgQuestion" src="./data/Question 2/Compléments/fic00000.png" alt="fic00000.png">
                        <h3> <style>
      <!--
      -->
    </style><p class=default>
      <span style="color: #000000; font-family: Monospaced">
        Die krakf
      </span>
    </p> </h3>
                        <a href="./data/Question 2/Compléments/fic00001.pdf" download="fic00001.pdf" target="_blank"> Télécharger le fichier complémentaire numéro 1</a>
                        <div id="zoneRep">
                             <div class="reponseBox" id="rep1" ><img id="img1" class="imgBtn" src="./data/imgBtn/RadVide.png">'Réponse 2'</div>
<div class="reponseBox" id="rep2" ><img id="img2" class="imgBtn" src="./data/imgBtn/RadVide.png">'Réponse 1'</div>

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

function question4()
{
    //Variables
    let bonnesRep = [true,false,];
    const difficulte = "Moyen";
    const tempsDeReponse = 30;
    let points = 11;
    let texteExplications = '<style>       <!--       -->     </style><p class=default>       <span style="color: #000000; font-family: Monospaced">         sghdtfjhfdhj       </span>     </p>';
    let notion = 'Les sous-classe';



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
                        </div>
                        <div class="progress-container">
                            <div class="progress-bar" id="progressBar">0%</div>
                        </div>
                   </header>

                        <script src="./script.js"></script>
                   <div class="pageQuestion">
                        <h1> Question `+ questionActuelle +` : </h1>
                        <h2> Notion : `+ notion +`  </h2>
                        <h2> Difficulté : `+ difficulte +`  </h2>

                        <br>
                        <!-- Pour un QCM -->

                        <h2> Type : Question à choix unique</h2><img class="imgQuestion" src="./data/Question 3/Compléments/null" alt="null">
                        <h3> <style>
      <!--
      -->
    </style><p class=default>
      <span style="color: #000000; font-family: Monospaced">
        sdfgfdgsfdgfhgdd
      </span>
    </p> </h3>
                                                <div id="zoneRep">
                             <div class="reponseBox" id="rep1" ><img id="img1" class="imgBtn" src="./data/imgBtn/RadVide.png">'Réponse 2'</div>
<div class="reponseBox" id="rep2" ><img id="img2" class="imgBtn" src="./data/imgBtn/RadVide.png">'Réponse 1'</div>

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

    
    document.getElementById("btnPreced").onclick = function() {if(!estChronometrer){questionPrecedante(), question3()}};
    document.getElementById("btnSuiv").onclick = function() {if(estChronometrer){if(tabCompletion[questionActuelle]){questionSuivante(), question5()}}else{questionSuivante(), question5()}};

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
}function question5()
{
    //Variables
    let bonnesRep = [];
    let eliminations = [];
    let nbEliminations = 0;
    let points = 0;
    let coutElimination = 0.0 ;
    let texteExplications = '<style>       <!--       -->     </style><p class=default>            </p>';



    const difficulte = 'Tres facile';
    const tempsDeReponse = 0;



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
                        </div>
                        <div class="progress-container">
                            <div class="progress-bar" id="progressBar">0%</div>
                        </div>
                   </header>

                        <script src="./script.js"></script>
                   <div class="pageQuestion">
                        <h1> Question `+ questionActuelle +` : </h1>
                        <h2> Difficulté : `+ difficulte +` <!-- Avoir la difficulté de la question avec JS--> </h2>

                        <br>
                        <!-- Pour un Elimination -->
                        <h2> Type : Elimination de réponses </h2>
<img class="imgQuestion" src="./data/Question 5/Compléments/fic00000.png" alt="fic00000.png">

                        <h3> <style>
      <!--
      -->
    </style><p class=default>
      
    </p> </h3>
                        <div id="zoneRep">
                                                </div>
                        <div class="styleBut" id=btnSup>Eliminer une réponse</div>
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
    document.getElementById("btnSuiv").onclick = function() {if(estChronometrer){if(tabCompletion[questionActuelle]){questionSuivante(), finQuestionnaire()}}else{questionSuivante(), finQuestionnaire()}};
}
