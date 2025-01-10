//Constantes et var communes
const nbQuestion = 1;
const estChronometrer = false;
const dureeTotal = 30; //Secondes
const noteMax = 3;


//Init variables
let questionActuelleEstBon = false;
let questionActuelle = 1;
let completion = 0;
let totalPoints = 0;
let timer;
let tabCompletion = [false]

const imgTF =' <img class="imgDif" src="./data/imgDif/TF.png">';
const imgF  =' <img class="imgDif" src="./data/imgDif/F.png">';
const imgM  =' <img class="imgDif" src="./data/imgDif/M.png">';
const imgD  =' <img class="imgDif" src="./data/imgDif/D.png">';

let tabSelections = 
[
[],
[false,],];
//Pour le qcm à relier


let nbEliminationQ1 = [false,];

function resetVariables()
{
    questionActuelle = 1;
    completion = 0;
    timer;
    seconds = 0;
    totalPoints = 0;
    tabCompletion = [false];
	nbEliminationQ1 = [false,];

    docAfficher = false;

	tabSelections = 
[
[],
[false,],];
	
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
                            <h2>TEST<!-- Avoir la matiere avec JS --></h2>
                            <h2>Temps pour finir : 30 Secondes<!-- Avoir la durér avec JS --></h2>
                            <h2>Sur la matière : Init dev <!-- Avoir la matiere avec JS --></h2>
                            <h2>Sur les notions suivante :</h2>
                            <ul>
<li> Les sous-classe </li>
                            </ul> 
                            <h2> Qui contient :</h2>
                            <p>
                                1 Question `+imgTF+` <br> <!-- Avoir le nb Question avec JS -->
                                0 Question `+imgF+` <br> <!-- Avoir le nb Question avec JS -->
                                0 Question `+imgM+` <br> <!-- Avoir le nb Question avec JS -->
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
                        <h2> Nombre de questions : `+ nbQuestion +` dont 1 `+ imgTF +` , 0 ` + imgF +` , 0 `+ imgM +` et 0 `+ imgD + `</h2>
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
    let bonnesRep = [true,];
    let eliminations = [-1,];
    let nbEliminations = 0;
    let points = 3;
    let coutElimination = 0.0 ;
    let texteExplications = '<style>       <!--       -->     </style><p class=default>       <span style="color: #000000; font-family: Monospaced">         hfjgjy       </span>     </p>';



    const difficulte = 'Tres facile';
    const tempsDeReponse = 30;



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
<img class="imgQuestion" src="./data/Question 5/Compléments/fic00000.png">

                        <h3> <style>
      <!--
      -->
    </style><p class=default>
      <span style="color: #000000; font-family: Monospaced">
        hfjgjy
      </span>
    </p> </h3>
                        <div id="zoneRep">
                        <div class="reponseBox" id="rep1" ><img id="img1" class="imgBtn" src="./data/imgBtn/RadVide.png">'rhddh'</div>
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
                    nbEliminationQ1[i] = true;
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

        for(let i = 0 ; i < nbEliminationQ1.length ; i++)
        {
            if(nbEliminationQ1[i] == true)
            {
                document.getElementById("rep" + (i+1)).classList.add('eliminer');
            }
        }
    }

    document.getElementById("btnPreced").onclick = function() {if(!estChronometrer){questionPrecedante(), question1()}}
    document.getElementById("btnSuiv").onclick = function() {if(estChronometrer){if(tabCompletion[questionActuelle]){questionSuivante(), finQuestionnaire()}}else{questionSuivante(), finQuestionnaire()}};
}
