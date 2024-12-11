//Constantes et var communes
const nbQuestion = 5;
const notion = "[Notion]";
const titreEval = "[Titre éval]";
const difficulte = "[Difficulté]";
const score = "[Score]" ; 
const ressource = "[Ressource]" ;
const estChronometrer = true;
const dureeTotal = 120; //Secondes

let questionActuelle = 1;
let completion = 0;
let timer;
let tabCompletion = [false,false,false,false,false,false,false,false,false,false];
let nbEliminiationQ5 = [false,false,false,false,false]; //Seulement pour les éliminations
let docAfficher = false;

const imgTF =' <img class="imgDif" src="./src/imgDif/TF.png">';
const imgF  =' <img class="imgDif" src="./src/imgDif/F.png">';
const imgM  =' <img class="imgDif" src="./src/imgDif/M.png">';
const imgD  =' <img class="imgDif" src="./src/imgDif/D.png">';

let tabSelections = [ //Doit faire nbQuestions + 1
					[false,false,false,false],
					[false,false,false,false],
					[false,false,false,false],
					[false,false,false,false],
					[false,false,false,false],
					[false,false,false,false]
					];

//Pour le qcm à relier
let lignes = 
[
	[],
	[],
	[],
	[]
]



function resetVariables()
{
	questionActuelle = 1;
	completion = 0;
	timer;
	seconds = 0;
	tabCompletion = [false,false,false,false,false,false,false,false,false,false];
	nbEliminiationQ5 = [false,false,false,false,false]; //Seulement pour les éliminations
	docAfficher = false;

	tabSelections = [
		[false,false,false,false],
		[false,false,false,false],
		[false,false,false,false],
		[false,false,false,false],
		[false,false,false,false],
		[false,false,false,false]
		];

	 lignes = 
			[
			[],
			[],
			[],
			[]
			]
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
		afficherReponse(bonnesRep, typeQuestion);

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
						        X Qestion Tres Facile <br> <!-- Avoir le nb Question avec JS -->
						        X Qestion Facile <br> <!-- Avoir le nb Question avec JS -->
						        X Qestion Moyen <br> <!-- Avoir le nb Question avec JS -->
						        X Qestion Difficile <!-- Avoir le nb Question avec JS -->
						    </p>
						    <div class="styleBut" onclick="question1()">Commencer</div>
							<div id='chrono'></div>
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
						<h2> Score global : `+score+`</h2>
						<br>
						<div class="styleBut" onclick="index()">Revenir a l'index du questionnaire</div>
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

/*Question 1 */
/* POUR QCM */
function question1()
{
	//Variables
	let bonnesRep = [false,true,true,false];
	const difficulte = "[difficulté]";
	const tempsDeReponse = 8;



	let contenuPage =`<!DOCTYPE html>
					<html lang="en">
					<head>
						<meta charset="UTF-8">
						<title>QCMBuilder</title>
						<link href="style.css" rel="stylesheet">
					</head>
					<body>
					    <div class="timer" id="chrono">00:00</div>
						<div class="progress-container">
							<div class="progress-bar" id="progressBar">0%</div>
						</div>
						<p class="progress-text">Questions traitées : <span id="questionsDone">0</span>/`+ nbQuestion +`</p>

						<script src="./script.js"></script>

						<h1> Question `+ questionActuelle +` : </h1>
						<h2> Notion : `+ notion +` <!-- Avoir la notion de la question avec JS--> </h2>
						<h2> Difficulté : `+ difficulte +` <!-- Avoir la difficulté de la question avec JS--> </h2>

						<br>
						<!-- Pour un QCM -->

						<h3> [Texte de la question] </h3>
						<div id="zoneRep">
							<div class="reponseBox" id="rep1" >[TextRep 1]</div>
							<div class="reponseBox" id="rep2" >[TextRep 2]</div>
							<div class="reponseBox" id="rep3" >[TextRep 3]</div>
							<div class="reponseBox" id="rep4" >[TextRep 4]</div>
						</div>
						<footer>
						    <nav>
						        <div class="styleBut" id="btnPreced">Précédent</div>
						        <div class="styleBut" id="valider">Valider</div>
						        <div class="styleBut">Feedback</div>
						        <div class="styleBut" id="btnSuiv">Suivant</div>
						    </nav>
						</footer>
					</body>
					</html>`;

	creerHtml(contenuPage);

	updateProgress();

	changerSelections();



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
					validerQuestion(bonnesRep, 'QCM')
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

	document.getElementById("valider").onclick = function() { validerQuestion(bonnesRep, 'QCM'), stopTimerQuestion() };
}



//Question 2
/* POUR VRAI OU FAUX */
function question2()
{
	//Variables
	let bonnesRep = [false,true];
	const difficulte = "[difficulté]";
	const tempsDeReponse = 10;


	let contenuPage =`<!DOCTYPE html>
					<html lang="en">
					<head>
						<meta charset="UTF-8">
						<title>QCMBuilder</title>
						<link href="style.css" rel="stylesheet">
					</head>
					<body>
					    <div class="timer" id="chrono">00:00</div>
						<div class="progress-container">
							<div class="progress-bar" id="progressBar">0%</div>
						</div>
						<p class="progress-text">Questions traitées : <span id="questionsDone">0</span>/`+ nbQuestion +`</p>

						<script src="./script.js"></script>

						<h1> Question `+ questionActuelle +` : </h1>
						<h2> Notion : `+ notion +` <!-- Avoir la notion de la question avec JS--> </h2>
						<h2> Difficulté : `+ difficulte +` <!-- Avoir la difficulté de la question avec JS--> </h2>

						<br>
						<!-- Pour un V/F -->

						<h3> [Texte de la question] </h3>
						<a href="./src/secu.mp3" download="secu.mp3" target="_blank"> Télécharger le fichier complémentaire</a>

						<div id="zoneRep">
							<div class="reponseBox" id="rep1" > Vrai </div>
							<div class="reponseBox" id="rep2" > Faux </div>
						</div>
						<footer>
						    <nav>
						        <div class="styleBut" id="btnPreced">Précédent</div>
						        <div class="styleBut" id="valider">Valider</div>
						        <div class="styleBut">Feedback</div>
						        <div class="styleBut" id="btnSuiv">Suivant</div>
						    </nav>
						</footer>
					</body>
					</html>`;

	creerHtml(contenuPage);

	updateProgress();

	changerSelections();


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
				validerQuestion(bonnesRep, 'QCM')
			}
		}, 1000);
	}

	// Fonction pour arrêter le chronomètre
	function stopTimerQuestion() {
		isRunning = false;
		clearInterval(timer);
	}



	if(tabCompletion[questionActuelle])
	{
		afficherReponse(bonnesRep, 'QCM');

	}


    // Mettre à jour les événements de clic

    document.getElementById("rep1").onclick = function() { clicRep(0, "vrai-faux") };
    document.getElementById("rep2").onclick = function() { clicRep(1, "vrai-faux") };

	document.getElementById("btnPreced").onclick = function() {if(!estChronometrer){questionPrecedante(), question1()}}
	document.getElementById("btnSuiv").onclick = function() {if(estChronometrer){if(tabCompletion[questionActuelle]){questionSuivante(), question3()}}else{questionSuivante(), question3()}};


	document.getElementById("valider").onclick = function() { validerQuestion(bonnesRep, "QCM"), stopTimerQuestion() };
}



/*Question 3 */
/* POUR QCM avec images*/
function question3()
{
	//Variables
	let bonnesRep = [false,true,true,false];
	const difficulte = "[difficulté]"
	const tempsDeReponse = 18;




	let contenuPage =`<!DOCTYPE html>
					<html lang="en">
					<head>
						<meta charset="UTF-8">
						<title>QCMBuilder</title>
						<link href="style.css" rel="stylesheet">
					</head>
					<body>
					    <div class="timer" id="chrono">00:00</div>
						<div class="progress-container">
							<div class="progress-bar" id="progressBar">0%</div>
						</div>
						<p class="progress-text">Questions traitées : <span id="questionsDone">0</span>/`+ nbQuestion +`</p>

						<script src="./script.js"></script>

						<h1> Question `+ questionActuelle +` : </h1>
						<h2> Notion : `+ notion +` <!-- Avoir la notion de la question avec JS--> </h2>
						<h2> Difficulté : `+ difficulte +` <!-- Avoir la difficulté de la question avec JS--> </h2>

						<br>
						<!-- Pour un QCM -->

						<h3> [Texte de la question] </h3>
						<img class="imgQuestion" src="./src/14.jpg" draggable="false">
						<div id="zoneRep">
							<div class="reponseBox" id="rep1"><img class="imgRep" src="./src/65.jpeg" draggable="false"></div>
							<div class="reponseBox" id="rep2" >[TextRep 2]</div>
							<div class="reponseBox" id="rep3" >[TextRep 3]</div>
							<div class="reponseBox" id="rep4" ><img class="imgRep" src="./src/86.jpg" draggable="false"></div>
						</div>
						<footer>
						    <nav>
						        <div class="styleBut" id="btnPreced">Précédent</div>
						        <div class="styleBut" id="valider">Valider</div>
						        <div class="styleBut">Feedback</div>
						        <div class="styleBut" id="btnSuiv">Suivant</div>
						    </nav>
						</footer>
					</body>
					</html>`;

	creerHtml(contenuPage);

	updateProgress();

	changerSelections();


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
				validerQuestion(bonnesRep, 'QCM')
			}
		}, 1000);
	}

	// Fonction pour arrêter le chronomètre
	function stopTimerQuestion() {
		isRunning = false;
		clearInterval(timer);
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

	document.getElementById("btnPreced").onclick = function() {if(!estChronometrer){questionPrecedante(), question2()}}
	document.getElementById("btnSuiv").onclick = function() {if(estChronometrer){if(tabCompletion[questionActuelle]){questionSuivante(), question4()}}else{questionSuivante(), question4()}};


	document.getElementById("valider").onclick = function() { validerQuestion(bonnesRep,"QCM"), stopTimerQuestion() };
}




/*Question 4 */
/* POUR associations */
function question4()
{
	//Variables
	let bonnesRep = [	
						[1,5],
						[7],
						[1],
						[3]
					];
					
				
	const difficulte = "[difficulté]";
	const tempsDeReponse = 12;


	let contenuPage =`<!DOCTYPE html>
					<html lang="en">
					<head>
						<meta charset="UTF-8">
						<title>QCMBuilder</title>
						<link href="style.css" rel="stylesheet">
					</head>
					<body>
					    <div class="timer" id="chrono">00:00</div>
						<div class="progress-container">
							<div class="progress-bar" id="progressBar">0%</div>
						</div>
						<p class="progress-text">Questions traitées : <span id="questionsDone">0</span>/`+ nbQuestion +`</p>

						<script src="./script.js"></script>

						<h1> Question `+ questionActuelle +` : </h1>
						<h2> Notion : `+ notion +` <!-- Avoir la notion de la question avec JS--> </h2>
						<h2> Difficulté : `+ difficulte +` <!-- Avoir la difficulté de la question avec JS--> </h2>

						<br>
						<!-- Pour un QCM -->

						<h3> [Texte de la question] </h3>
						<img class="imgQuestion" src="./src/14.jpg" id="imgTxt" draggable="false">

						<div id="zoneRepAssos">
							<div class="casesInternes" id="repGauche">
								<div class="reponseBoxAssos" id="rep1"><img class="imgRep" src="./src/14.jpg" draggable="false"></div>
								<div class="reponseBoxAssos" id="rep3">[TextRep 2]</div>
								<div class="reponseBoxAssos" id="rep5"><img class="imgRep" src="./src/65.jpeg" draggable="false"></div>
								<div class="reponseBoxAssos" id="rep7">[TextRep 2]</div>
							</div>
							<div><canvas id="canvas"></canvas></div>
							<div class="casesInternes" id="repDroite">
								<div class="reponseBoxAssos" id="rep2">[TextRep 3]</div>
								<div class="reponseBoxAssos" id="rep4"><img class="imgRep" src="./src/86.jpg" draggable="false"></div>
								<div class="reponseBoxAssos" id="rep6">[TextRep 3]</div>
								<div class="reponseBoxAssos" id="rep8"><img class="imgRep" src="./src/14.jpg" draggable="false"></div>
							</div>
						</div>


						</div>
						<footer>
						    <nav>
						        <div class="styleBut" id="btnPreced">Précédent</div>
						        <div class="styleBut" id="valider">Valider</div>
						        <div class="styleBut">Feedback</div>
						        <div class="styleBut" id="btnSuiv">Suivant</div>
						    </nav>
						</footer>
					</body>
					</html>`;

	creerHtml(contenuPage);


	const canvas = document.getElementById('canvas');
	const ctx = canvas.getContext('2d');

	// Définir les dimensions du canvas

	let tmp = document.getElementById("repDroite");
	let tmp2 = document.getElementById("repGauche");


	if(tmp.clientHeight > tmp2.clientHeight)
	{
		canvas.height = tmp.clientHeight;
	}else{
		canvas.height = tmp2.clientHeight;
	}
	
	canvas.width = canvas.offsetWidth;



	if(tabCompletion[questionActuelle])
	{
		afficherReponse(bonnesRep, 'EA');
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
				drawLines(lignes, 'erreurs');
				validerQuestion(bonnesRep,"EA");
				clearInterval(this);
				return;
			}
		}, 1000);
	}

	// Fonction pour arrêter le chronomètre
	function stopTimerQuestion() {
		isRunning = false;
		tps = 0;
		clearInterval(timer);
	}



	
	// Recalcule et redessine si la fenêtre est redimensionnée
	window.onresize = drawLines;


	// Récupérer les éléments à connecter
	const leftElementsDiv = document.querySelectorAll("#repGauche .reponseBoxAssos");
	const rightElementsDiv = document.querySelectorAll("#repDroite .reponseBoxAssos");

	let leftElements = [];
	let rightElements = [];

	let selectGauche = -1;
	let selectDroite = -1;
	let indexGauche = -1;


	leftElementsDiv.forEach(function(element, index, leftElement) {
		leftElements[index] = index;
	});

	rightElementsDiv.forEach(function(element, index, rightElementsDiv) {
		rightElements[index] = index;
	});



	function selection(element, index)
	{
		if(tabCompletion[questionActuelle])
		{
			return;
		}


		if(index % 2 == 0)
		{
			document.getElementById("rep" + (index+1)).classList.toggle('selected');
			indexGauche = index+1;
			selectGauche = index/2;
		}else{
			document.getElementById("rep" + (index+1)).classList.toggle('selected');
			selectDroite = index;
		}

		if(selectDroite != -1 && selectGauche != -1)
		{
			if(lignes[selectGauche].indexOf(selectDroite) != -1)
			{
				lignes[selectGauche].pop(selectDroite);
			}else{
				lignes[selectGauche].push(selectDroite);
			}

			ctx.clearRect(0, 0, canvas.width, canvas.height);


			if(document.getElementById("rep" + (selectDroite+1)).classList.contains('selected'))
			{
				document.getElementById("rep" + (selectDroite+1)).classList.toggle('selected');
			}

			if(document.getElementById("rep" + indexGauche).classList.contains('selected'))
			{
				document.getElementById("rep" + (indexGauche)).classList.toggle('selected');
			}

			selectGauche = -1;
			selectDroite = -1;
			indexGauche = -1;
		}


		drawLines(lignes);
	}


	drawLines(lignes);


	if(tabCompletion[questionActuelle])
	{
		drawLines(lignes, 'erreurs');
		drawLines(bonnesRep, 'correction');
	}
	
	updateProgress();

	changerSelections();



    // Mettre à jour les événements de clic

	if(!tabCompletion[questionActuelle])
	{
		document.getElementById("rep1").onclick = function() { selection(leftElements, 0) };
		document.getElementById("rep2").onclick = function() { selection(rightElements, 1) };
		document.getElementById("rep3").onclick = function() { selection(leftElements, 2)};
		document.getElementById("rep4").onclick = function() { selection(rightElements, 3)};
		document.getElementById("rep5").onclick = function() { selection(leftElements, 4)};
		document.getElementById("rep6").onclick = function() { selection(rightElements, 5)};
		document.getElementById("rep7").onclick = function() { selection(leftElements, 6)};
		document.getElementById("rep8").onclick = function() { selection(rightElements, 7)};

		document.getElementById("valider").onclick = function() { if(!tabCompletion[questionActuelle]){drawLines(lignes, 'erreurs')}; validerQuestion(bonnesRep,"EA"), stopTimerQuestion() }; //Affichage en rouge si besoin
	}

	
	document.getElementById("btnPreced").onclick = function() {if(!estChronometrer){questionPrecedante(), question3()}}
	document.getElementById("btnSuiv").onclick = function() {if(estChronometrer){if(tabCompletion[questionActuelle]){questionSuivante(), question5()}}else{questionSuivante(), question5()}};


}





//Question 5
/* POUR ELIMINATION DE REPONSE */
function question5()
{
	//Variables
	let bonnesRep = [false,false,true,false,false];
	let eliminations = [-1,-1,-1,2,1];
	let nbEliminations = 0;


	const difficulte = "[difficulté]";
	const tempsDeReponse = 15;



	let contenuPage =`<!DOCTYPE html>
					<html lang="en">
					<head>
						<meta charset="UTF-8">
						<title>QCMBuilder</title>
						<link href="style.css" rel="stylesheet">
					</head>
					<body>
					    <div class="timer" id="chrono">00:00</div>
						<div class="progress-container">
							<div class="progress-bar" id="progressBar">0%</div>
						</div>
						<p class="progress-text">Questions traitées : <span id="questionsDone">0</span>/`+ nbQuestion +`</p>

						<script src="./script.js"></script>

						<h1> Question `+ questionActuelle +` : </h1>
						<h2> Notion : `+ notion +` <!-- Avoir la notion de la question avec JS--> </h2>
						<h2> Difficulté : `+ difficulte +` <!-- Avoir la difficulté de la question avec JS--> </h2>

						<br>
						<!-- Pour un V/F -->

						<h3>[Texte de la question]</h3>
						<div id="zoneRep">
							<div class="reponseBox" id="rep1"><img class="imgRep" src="./src/14.jpg" draggable="false"></div>
							<div class="reponseBox" id="rep2">[TextRep 2]</div>
							<div class="reponseBox" id="rep3">[TextRep 3]</div>
							<div class="reponseBox" id="rep4">[TextRep 4]</div>
							<div class="reponseBox" id="rep5">[TextRep 5]</div>
						</div>
						<div class="styleBut" id=btnSup>Eliminer une réponse</div>
						<footer>
						    <nav>
						        <div class="styleBut" id="btnPreced">Précédent</div>
						        <div class="styleBut" id="valider">Valider</div>
						        <div class="styleBut">Feedback</div>
						        <div class="styleBut" id="btnSuiv">Suivant</div>
						    </nav>
						</footer>
					</body>
					</html>`;

	creerHtml(contenuPage);

	updateProgress();

	changerSelections();


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
				validerQuestion(bonnesRep,"QCM");
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
					nbEliminiationQ5[i] = true;
					nbEliminations++;
				}
			}
		}
	}



	if(tabCompletion[questionActuelle])
	{
		afficherReponse(bonnesRep, 'QCM');
	}



    // Mettre à jour les événements de clic

	if(!tabCompletion[questionActuelle])
	{
		document.getElementById("rep1").onclick = function() { clicRep(0, "elimination") };
		document.getElementById("rep2").onclick = function() { clicRep(1, "elimination") };
		document.getElementById("rep3").onclick = function() { clicRep(2, "elimination") };
		document.getElementById("rep4").onclick = function() { clicRep(3, "elimination") };
		document.getElementById("rep5").onclick = function() { clicRep(4, "elimination") };

		document.getElementById("btnSup").onclick = function() { suppQuestion(eliminations) };
		document.getElementById("valider").onclick = function() { validerQuestion(bonnesRep, "QCM"), stopTimerQuestion() };
	}else{

		for(let i = 0 ; i < nbEliminiationQ5.length ; i++)
		{
			if(nbEliminiationQ5[i] == true)
			{
				document.getElementById("rep" + (i+1)).classList.add('eliminer');
			}
		}
	}

	document.getElementById("btnPreced").onclick = function() {if(!estChronometrer){questionPrecedante(), question4()}}
	document.getElementById("btnSuiv").onclick = function() {if(estChronometrer){if(tabCompletion[questionActuelle]){finQuestionnaire()}}else{finQuestionnaire()}};

}