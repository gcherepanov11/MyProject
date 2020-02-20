function putTrend(tmpStr){
	let lexStart = 0, lexEnd = tmpStr.indexOf("\t");
	let lex = tmpStr.slice(lexStart, lexEnd);
	if(lex == "Start"){
		trendArray = new Array(1);
		trendArray[0] = new Array(1);
		trendArray[0][0] = lex;
		trendInd = 0;
		knowDataFormat = 0;
	}else{
		if(knowDataFormat == 0) searchData(tmpStr);
		if(knowDataFormat == 0) return 1; // в первой строке данных трендов нет даты
		trendArray.push(0);
		trendInd++;
		trendArray[trendInd] = new Array(1);
		trendArray[trendInd][0] = dateConvert(lex);
	}
	
	lexStart = lexEnd + 1;
	lexEnd = tmpStr.indexOf("\t", lexStart);
	while(lexEnd >= lexStart){
		trendArray[trendInd].push(formatNum(tmpStr.slice(lexStart, lexEnd),4));
		lexStart = lexEnd + 1;
		lexEnd = tmpStr.indexOf("\t", lexStart);
	}
	trendArray[trendInd].push(formatNum(tmpStr.slice(lexStart),4));
	return 0;
}

function checkJavaComm(){
	putExampleTrends();
	drawTrendsFromJava();
	
	startLogFromJava();
	putLog("31.05.2019 11:18:47.000	Разрешите представить! Просмотрщик архивов \"Вега-РЕТРО\"	вход	появился");
	putLog("31.05.2019 11:18:48.000	Разработано в Обособленном подразделении ООО \"Вега-ГАЗ\" в г.Санкт-Петербург	вход	появился");
	putLog("31.05.2019 11:18:49.000	Версия 0.7.3	вход	снялся");
	putLog("31.05.2019 11:18:50.000	Разработчики: Зиндер Л.В., Назаров Д.О.	вход	снялся");
	/*
	putLog("31.05.2019 11:18:51.000	Важное сообщение N 5	режим	появился");
	putLog("31.05.2019 11:18:52.000	Важное сообщение N 6	неисправность	появился");
	putLog("31.05.2019 11:18:53.000	Важное сообщение N 7	вход	появился");
	putLog("31.05.2019 11:18:54.000	Важное сообщение N 8	авария	снялся");
	putLog("31.05.2019 11:18:55.000	Важное сообщение N 9	вход	квитировано");
	putLog("31.05.2019 11:18:56.000	Важное сообщение N 10	режим	снялся");
	putLog("31.05.2019 11:18:57.000	Важное сообщение N 11	вход	снялся");
	putLog("31.05.2019 11:18:58.000	Важное сообщение N 12	неисправность	снялся");
	putLog("31.05.2019 11:18:59.000	Важное сообщение N 13	Предупреждение	появился");
	putLog("31.05.2019 11:19:00.000	Важное сообщение N 14	авария	снялся");
	putLog("31.05.2019 11:19:01.000	Важное сообщение N 15	неисправность	появился");
	putLog("31.05.2019 11:19:02.000	Важное сообщение N 16	авария	появился");
	putLog("31.05.2019 11:19:03.000	Важное сообщение N 17	вход	квитировано");
	putLog("31.05.2019 11:19:04.000	Важное сообщение N 18	режим	появился");
	putLog("31.05.2019 11:19:05.000	Важное сообщение N 19	неисправность	снялся");
	putLog("31.05.2019 11:19:06.000	Важное сообщение N 20	вход	пришло");
	putLog("31.05.2019 11:19:07.000	Важное сообщение N 21	неисправность	ушло");
	putLog("31.05.2019 11:19:08.000	Важное сообщение N 22	режим	снялся");
	putLog("31.05.2019 11:19:09.000	Важное сообщение N 23	авария	снялся");
	putLog("31.05.2019 11:19:10.000	Важное сообщение N 24	вход	возникло");
	putLog("31.05.2019 11:19:11.000	Важное сообщение N 25	выход	снялся");
	putLog("31.05.2019 11:19:12.000	Важное сообщение N 26	авария	исчезло");
	putLog("31.05.2019 11:19:13.000	Важное сообщение N 27	вход	снялся");
	putLog("31.05.2019 11:19:14.000	Важное сообщение N 28	авария	снялся");
	putLog("31.05.2019 11:19:15.000	Важное сообщение N 29	вход	появился");
	putLog("31.05.2019 11:19:16.000	Важное сообщение N 30	режим	снялся");
	putLog("31.05.2019 11:19:17.000	Важное сообщение N 31	авария	снялся");
	putLog("31.05.2019 11:19:18.000	Важное сообщение N 32	неисправность	снялся");
	putLog("31.05.2019 11:19:19.000	Важное сообщение N 33	режим	снялся");
	putLog("31.05.2019 11:19:20.000	Важное сообщение N 34	неисправность	появился");
	putLog("31.05.2019 11:19:21.000	Важное сообщение N 35	авария	появился");
	putLog("31.05.2019 11:19:22.000	Важное сообщение N 36	неисправность	появился");
	putLog("31.05.2019 11:19:23.000	Важное сообщение N 37	режим	снялся");
	*/
	drawLogFromJava();
}

function drawTrendsFromJava(){
	setNewTrendList(true)
	drawInCanvas();
	setCanvasOffset();
	drawAll();
	drawVarTable();
	startSetTimeScrollings();
	return 0;
}

function startLogFromJava(){
	logArr = [];
	typeArr = [];
	msgLen = 0;
	typeLen = 0;
	statusLen = 0;
	startLog = 0;
	endLog = 0;
	knowDataFormat = 0;
}

function putLog(tmpStr){
	//if(!tmpStr || tmpStr.length = 0) return 3;
	if(tmpStr.length = 0) return 1;
	
	if(knowDataFormat == 0) searchData(tmpStr);
	if(knowDataFormat == 0) return 2; // В первой строке лога нет даты.
	let lexStart = 0;
	let lexEnd = tmpStr.indexOf("\t");
	let tmpObj = Array(4);
	for(j = 0; j < 3; j++){
		tmpObj[j] = tmpStr.slice(lexStart, lexEnd);
		lexStart = lexEnd + 1;
		lexEnd = tmpStr.indexOf("\t", lexStart);
	}
	tmpObj[3] = tmpStr.slice(lexStart);
	logArr.push({dt:dateConvert(tmpObj[0]),textMsg:tmpObj[1],type:tmpObj[2], direct:tmpObj[3]});
	addType(tmpObj[2]);
	if(msgLen    < tmpObj[1].length) msgLen    = tmpObj[1].length;
	if(typeLen   < tmpObj[2].length) typeLen   = tmpObj[2].length;
	if(statusLen < tmpObj[3].length) statusLen = tmpObj[3].length;
	return 0;
}

function drawLogFromJava(){
	startLog=0;
	endLog=0;

	logOutput();
	return 0;
}
function putExampleTrends(){
putTrend("Start	Ознакомительный график 1	Ознакомительный график 2");
putTrend("31.05.2019 11:18:00.000	0	100");
putTrend("31.05.2019 11:18:01.001	8.715574275	98.4807753");
putTrend("31.05.2019 11:18:02.002	17.36481777	93.96926208");
putTrend("31.05.2019 11:18:03.003	25.88190451	86.60254038");
putTrend("31.05.2019 11:18:04.004	34.20201433	76.60444431");
putTrend("31.05.2019 11:18:05.005	42.26182617	64.27876097");
putTrend("31.05.2019 11:18:06.006	50	50");
putTrend("31.05.2019 11:18:07.007	57.35764364	34.20201433");
putTrend("31.05.2019 11:18:08.008	64.27876097	17.36481777");
putTrend("31.05.2019 11:18:09.009	70.71067812	6.12574E-15");
putTrend("31.05.2019 11:18:10.010	76.60444431	-17.36481777");
putTrend("31.05.2019 11:18:11.011	81.91520443	-34.20201433");
putTrend("31.05.2019 11:18:12.012	86.60254038	-50");
putTrend("31.05.2019 11:18:13.013	90.6307787	-64.27876097");
putTrend("31.05.2019 11:18:14.014	93.96926208	-76.60444431");
putTrend("31.05.2019 11:18:15.015	96.59258263	-86.60254038");
putTrend("31.05.2019 11:18:16.016	98.4807753	-93.96926208");
putTrend("31.05.2019 11:18:17.017	99.61946981	-98.4807753");
putTrend("31.05.2019 11:18:18.018	100	-100");
putTrend("31.05.2019 11:18:19.019	99.61946981	-98.4807753");
putTrend("31.05.2019 11:18:20.020	98.4807753	-93.96926208");
putTrend("31.05.2019 11:18:21.021	96.59258263	-86.60254038");
putTrend("31.05.2019 11:18:22.022	93.96926208	-76.60444431");
putTrend("31.05.2019 11:18:23.023	90.6307787	-64.27876097");
putTrend("31.05.2019 11:18:24.024	86.60254038	-50");
putTrend("31.05.2019 11:18:25.025	81.91520443	-34.20201433");
putTrend("31.05.2019 11:18:26.026	76.60444431	-17.36481777");
putTrend("31.05.2019 11:18:27.027	70.71067812	-1.83772E-14");
putTrend("31.05.2019 11:18:28.028	64.27876097	17.36481777");
putTrend("31.05.2019 11:18:29.029	57.35764364	34.20201433");
putTrend("31.05.2019 11:18:30.030	50	50");
putTrend("31.05.2019 11:18:31.031	42.26182617	64.27876097");
putTrend("31.05.2019 11:18:32.032	34.20201433	76.60444431");
putTrend("31.05.2019 11:18:33.033	25.88190451	86.60254038");
putTrend("31.05.2019 11:18:34.034	17.36481777	93.96926208");
putTrend("31.05.2019 11:18:35.035	8.715574275	98.4807753");
putTrend("31.05.2019 11:18:36.036	1.22515E-14	100");
putTrend("31.05.2019 11:18:37.037	-8.715574275	98.4807753");
putTrend("31.05.2019 11:18:38.038	-17.36481777	93.96926208");
putTrend("31.05.2019 11:18:39.039	-25.88190451	86.60254038");
putTrend("31.05.2019 11:18:40.040	-34.20201433	76.60444431");
putTrend("31.05.2019 11:18:41.041	-42.26182617	64.27876097");
putTrend("31.05.2019 11:18:42.042	-50	50");
putTrend("31.05.2019 11:18:43.043	-57.35764364	34.20201433");
putTrend("31.05.2019 11:18:44.044	-64.27876097	17.36481777");
putTrend("31.05.2019 11:18:45.045	-70.71067812	3.06287E-14");
putTrend("31.05.2019 11:18:46.046	-76.60444431	-17.36481777");
putTrend("31.05.2019 11:18:47.047	-81.91520443	-34.20201433");
putTrend("31.05.2019 11:18:48.048	-86.60254038	-50");
putTrend("31.05.2019 11:18:49.049	-90.6307787	-64.27876097");
putTrend("31.05.2019 11:18:50.050	-93.96926208	-76.60444431");
putTrend("31.05.2019 11:18:51.051	-96.59258263	-86.60254038");
putTrend("31.05.2019 11:18:52.052	-98.4807753	-93.96926208");
putTrend("31.05.2019 11:18:53.053	-99.61946981	-98.4807753");
putTrend("31.05.2019 11:18:54.054	-100	-100");
putTrend("31.05.2019 11:18:55.055	-99.61946981	-98.4807753");
putTrend("31.05.2019 11:18:56.056	-98.4807753	-93.96926208");
putTrend("31.05.2019 11:18:57.057	-96.59258263	-86.60254038");
putTrend("31.05.2019 11:18:58.058	-93.96926208	-76.60444431");
putTrend("31.05.2019 11:18:59.059	-90.6307787	-64.27876097");
putTrend("31.05.2019 11:19:00.060	-86.60254038	-50");
putTrend("31.05.2019 11:19:01.061	-81.91520443	-34.20201433");
putTrend("31.05.2019 11:19:02.062	-76.60444431	-17.36481777");
putTrend("31.05.2019 11:19:03.063	-70.71067812	-4.28802E-14");
putTrend("31.05.2019 11:19:04.064	-64.27876097	17.36481777");
putTrend("31.05.2019 11:19:05.065	-57.35764364	34.20201433");
putTrend("31.05.2019 11:19:06.066	-50	50");
putTrend("31.05.2019 11:19:07.067	-42.26182617	64.27876097");
putTrend("31.05.2019 11:19:08.068	-34.20201433	76.60444431");
putTrend("31.05.2019 11:19:09.069	-25.88190451	86.60254038");
putTrend("31.05.2019 11:19:10.070	-17.36481777	93.96926208");
putTrend("31.05.2019 11:19:11.071	-8.715574275	98.4807753");
putTrend("31.05.2019 11:19:12.072	-2.4503E-14	100");
putTrend("31.05.2019 11:19:13.073	8.715574275	98.4807753");
putTrend("31.05.2019 11:19:14.074	17.36481777	93.96926208");
putTrend("31.05.2019 11:19:15.075	25.88190451	86.60254038");
putTrend("31.05.2019 11:19:16.076	34.20201433	76.60444431");
putTrend("31.05.2019 11:19:17.077	42.26182617	64.27876097");
putTrend("31.05.2019 11:19:18.078	50	50");
putTrend("31.05.2019 11:19:19.079	57.35764364	34.20201433");
putTrend("31.05.2019 11:19:20.080	64.27876097	17.36481777");
putTrend("31.05.2019 11:19:21.081	70.71067812	2.32767E-13");
putTrend("31.05.2019 11:19:22.082	76.60444431	-17.36481777");
putTrend("31.05.2019 11:19:23.083	81.91520443	-34.20201433");
putTrend("31.05.2019 11:19:24.084	86.60254038	-50");
putTrend("31.05.2019 11:19:25.085	90.6307787	-64.27876097");
putTrend("31.05.2019 11:19:26.086	93.96926208	-76.60444431");
putTrend("31.05.2019 11:19:27.087	96.59258263	-86.60254038");
putTrend("31.05.2019 11:19:28.088	98.4807753	-93.96926208");
putTrend("31.05.2019 11:19:29.089	99.61946981	-98.4807753");
putTrend("31.05.2019 11:19:30.090	100	-100");
putTrend("31.05.2019 11:19:31.091	99.61946981	-98.4807753");
putTrend("31.05.2019 11:19:32.092	98.4807753	-93.96926208");
putTrend("31.05.2019 11:19:33.093	96.59258263	-86.60254038");
putTrend("31.05.2019 11:19:34.094	93.96926208	-76.60444431");
putTrend("31.05.2019 11:19:35.095	90.6307787	-64.27876097");
putTrend("31.05.2019 11:19:36.096	86.60254038	-50");
putTrend("31.05.2019 11:19:37.097	81.91520443	-34.20201433");
putTrend("31.05.2019 11:19:38.098	76.60444431	-17.36481777");
putTrend("31.05.2019 11:19:39.099	70.71067812	1.10253E-13");
putTrend("31.05.2019 11:19:40.100	64.27876097	17.36481777");
putTrend("31.05.2019 11:19:41.101	57.35764364	34.20201433");
putTrend("31.05.2019 11:19:42.102	50	50");
putTrend("31.05.2019 11:19:43.103	42.26182617	64.27876097");
putTrend("31.05.2019 11:19:44.104	34.20201433	76.60444431");
putTrend("31.05.2019 11:19:45.105	25.88190451	86.60254038");
putTrend("31.05.2019 11:19:46.106	17.36481777	93.96926208");
putTrend("31.05.2019 11:19:47.107	8.715574275	98.4807753");
putTrend("31.05.2019 11:19:48.108	3.67545E-14	100");
putTrend("31.05.2019 11:19:49.109	-8.715574275	98.4807753");
putTrend("31.05.2019 11:19:50.110	-17.36481777	93.96926208");
putTrend("31.05.2019 11:19:51.111	-25.88190451	86.60254038");
putTrend("31.05.2019 11:19:52.112	-34.20201433	76.60444431");
putTrend("31.05.2019 11:19:53.113	-42.26182617	64.27876097");
putTrend("31.05.2019 11:19:54.114	-50	50");
putTrend("31.05.2019 11:19:55.115	-57.35764364	34.20201433");
putTrend("31.05.2019 11:19:56.116	-64.27876097	17.36481777");
putTrend("31.05.2019 11:19:57.117	-70.71067812	-9.8001E-14");
putTrend("31.05.2019 11:19:58.118	-76.60444431	-17.36481777");
putTrend("31.05.2019 11:19:59.119	-81.91520443	-34.20201433");
}