let logArr = [];
let typeArr = [];
let backgroundColor = [{t:"появился",c:"#000000"},{t:"снялся",c:"#444444"}, {t:"квитировано",c:"#004400"}] 	// - это цвета фона сообщений с разным статусом
let typeColor = [{t:"вход",c:"#00FF00"},												// - цвета текста для сообщений разного типа
				 {t:"авария",c:"#FF4444"},
				 {t:"режим",c:"#8888FF"},
				 {t:"неисправность",c:"#FFA000"}];
let Q_MSG = 14;
function addType(t){
	for(let i=0; i<typeArr.length; i++) if(typeArr[i].t == t) return;
	typeArr.push({t:t, f: true});
}

function getFiltFlag(t){
	for(let i=0; i<typeArr.length; i++) if(typeArr[i].t == t) return typeArr[i].f;
}

function typeColorGet(t){
	for(let i=0; i < typeColor.length; i++) if(typeColor[i].t == t) return typeColor[i].c;
	return "#FFFFFF";																		// - цвет текста для сообщений неизвесного типа
}
function backgroundColorGet(t){
	for(let i=0; i < backgroundColor.length; i++) if(backgroundColor[i].t == t) return backgroundColor[i].c;
	return "#440000";																		// - цвет фона для сообщений с неизвестным статусом
}

function readLogFile(input) {
	let file = input.files[0];
	let reader = new FileReader();
	reader.readAsText(file);
	reader.onload = function() {
		logProcessing(reader.result);
		logOutput();
	};
	reader.onerror = function() {
		document.getElementById("filecontents") = reader.error;
	};
}

let msgLen, typeLen, statusLen;
function logProcessing(textData){
	if(textData.length == 0) {alert("В выбранном файле нет данных"); return;}
	searchData(textData);
	if(knowDataFormat == 0) {alert("В выбранном файле нет даты"); return;}
	logArr = [];
	let strEnd = textData.indexOf("\n", 0);
	let tmpStr  = textData.slice(0, strEnd);
	let lexEnd = tmpStr.indexOf("\t");
	let data1 = dateConvert(tmpStr.slice(0, lexEnd));
	let strStart = strEnd + 1;
	lexEnd = textData.indexOf("\t",strStart);
	tmpStr  = textData.slice(strStart, lexEnd);
	let data2 = dateConvert(tmpStr.slice(0, lexEnd));
	let newInTop = data2 > data1;
	
	msgLen = 0;
	typeLen = 0;
	statusLen = 0;
	
	let j = 0;
	strStart = 0;
	while(strEnd >= 0){
		strEnd = textData.indexOf("\n", strStart);
		tmpStr = textData.slice(strStart, strEnd);
		
		let lexStart = 0;
		lexEnd = tmpStr.indexOf("\t");
		let tmpObj = Array(4);
		for(j = 0; j < 4; j++){
			tmpObj[j] = tmpStr.slice(lexStart, lexEnd);
			lexStart = lexEnd + 1;
			lexEnd = tmpStr.indexOf("\t", lexStart);
		}
		if(tmpObj[0] && tmpObj[1]){
			if(msgLen    < tmpObj[1].length) msgLen    = tmpObj[1].length;
			if(typeLen   < tmpObj[2].length) typeLen   = tmpObj[2].length;
			if(statusLen < tmpObj[3].length) statusLen = tmpObj[3].length;
			
			if(newInTop)logArr.unshift({dt:dateConvert(tmpObj[0]),textMsg:tmpObj[1],type:tmpObj[2], direct:tmpObj[3]});
			else           logArr.push({dt:dateConvert(tmpObj[0]),textMsg:tmpObj[1],type:tmpObj[2], direct:tmpObj[3]});
			addType(tmpObj[2]);
		}
		strStart = strEnd + 1;
	}
	startLog=0;
	endLog=0;
}

let startLog=0, endLog=0;
function logOutput(){
	if(!startLog) startLog = logArr[0].dt;
	if(!endLog) endLog = logArr[logArr.length-1].dt;
	if(endLog < startLog) {let tmp = endLog; endLog = startLog; startLog = tmp;}
	
	document.getElementById("dt1").value = dateTimeFormat(startLog);
	document.getElementById("dt2").value = dateTimeFormat(endLog);
	fastLogOutput();
}
function fastLogOutput(){	
	let tmp = logOutputTable();
	let fc = document.getElementById("filecontents");
	fc.innerHTML = tmp;
	let vsbLeft = fc.offsetWidth + (+fc.offsetLeft) - 1;
	document.getElementById("verScrollBox").style="position:absolute; left:" + vsbLeft + "; top:"+ fc.style.top +";";
	
	minYvertScroll = +fc.offsetTop + 20;
	maxYvertScroll = +fc.offsetTop + fc.offsetHeight - 40;
	document.getElementById("logScroll").style.left = vsbLeft;
}

let qFiltElem = 0, newInTop = false;

function setVertScrollPos(){
	let tmp = qFiltElem - Q_MSG;//maxYvertScroll = 902, minYvertScroll = 710;
	if(tmp <= 0) document.getElementById("logScroll").style.top = minYvertScroll + "px";
	else document.getElementById("logScroll").style.top = Math.round(Q_Skip/tmp * (maxYvertScroll - minYvertScroll) + minYvertScroll)+ "px";
}

function logOutputTable(){
	let start = 0, step = 1;
	newInTop = document.getElementById("newInTop").checked;
	if(newInTop){ start = logArr.length - 1; step = -1;}
	
	let firstFiltElem = -1;
	qFiltElem = 0;
	for(let i = start; i>= 0 && i<logArr.length; i+= step){
		if(logArr[i].dt >= startLog && logArr[i].dt <= endLog && getFiltFlag(logArr[i].type)){
			if(firstFiltElem < 0) firstFiltElem = i;
			qFiltElem++;
		}
	}
	
	if(Q_Skip > qFiltElem - Q_MSG) Q_Skip = qFiltElem - Q_MSG;
	if(Q_Skip < 0) Q_Skip = 0;
	
	setVertScrollPos();
	
	let msgCnt = 0, skipCnt = 0;
	let tmp = "<table cellpadding=\"1\" cellspacing=\"1\">";
	for(let i = firstFiltElem; i>= 0 && i<logArr.length && msgCnt < Q_MSG; i+= step){
		if(logArr[i].dt >= startLog && logArr[i].dt <= endLog && getFiltFlag(logArr[i].type)){
			if(skipCnt >= Q_Skip){ tmp += 
				"<tr style=\"font-size:14px;background-color:"+backgroundColorGet(logArr[i].direct)+";font-weight:bold; color:"+typeColorGet(logArr[i].type)+";\">"+
					"<td style=\"padding:0;\">&nbsp;&nbsp;" + dateTimeFormat(logArr[i].dt) +   "&nbsp;&nbsp;</td>"+ //&#160;
					"<td style=\"padding:0; width:" + (msgLen+2)*8  + " ;\">&nbsp;&nbsp;" + logArr[i].textMsg + "</td>" + 
					"<th style=\"padding:0; width:" + (typeLen+2)*8  + "\">" + logArr[i].type + "</th>"+
					"<th style=\"padding:0; width:" + (statusLen+2)*8 + ";\">" + logArr[i].direct + "</th>" +
				"</tr>";
				msgCnt++;
			} else skipCnt++;
		}
	}
	tmp += "</table>";
	return tmp;
}

let Q_Skip = 0;

function shiftLnUp(){
	if(logArr.length - Q_Skip >= Q_MSG) Q_Skip++;
	document.getElementById("filecontents").innerHTML = "";
	fastLogOutput();
}
function shiftLnDn(){
	if(Q_Skip > 0) Q_Skip--;
	document.getElementById("filecontents").innerHTML = "";
	fastLogOutput();
}
function shiftPg(){
	if(document.getElementById("logScroll").style.top.replace('px','')*1 > mouseY) shiftPgDn();
	else shiftPgUp();
}
function shiftPgUp(){
	if(logArr.length - Q_Skip >= Q_MSG) Q_Skip += Q_MSG - 1;
	if(Q_Skip > logArr.length - Q_MSG) Q_Skip = logArr.length - Q_MSG
	document.getElementById("filecontents").innerHTML = "";
	fastLogOutput();
}
function shiftPgDn(){
	if(Q_Skip > 0) Q_Skip -= Q_MSG-1;
	if(Q_Skip < 0) Q_Skip = 0;
	document.getElementById("filecontents").innerHTML = "";
	fastLogOutput();
}
function reDrawLog(){
	document.getElementById("filecontents").innerHTML = "";
	logOutput();
}
function checkDT1(dt){
	let dataForm = identDateFormatOnLine(dt.value);
	if(!dataForm) {
		alert("Неправильный формат даты-времени.\nПример правильного формата: 2019-06-03 13:45:22.123 или 03.06.2019 13:45:22.123 или 03.06.19 13:45:22.123\nгде 2019 - год, 06 - месяц, 03 день, 13 - часы, 45 - минуты, 22 - секунды, 123 - миллисекунды");
		if(dt.id == "dt1") dt.value = dateTimeFormat(startLog);
		else               dt.value = dateTimeFormat(endLog);
	}
}
function cutLog(){
	let tmpLogStart = document.getElementById("dt1").value;
	let tmpLogEnd   = document.getElementById("dt2").value;
	let dataForm = identDateFormatOnLine(tmpLogStart);
	let tmpDTformat = knowDataFormat;
	knowDataFormat = dataForm;
	startLog = dateConvert(tmpLogStart);
	endLog = dateConvert(tmpLogEnd);
	knowDataFormat = tmpDTformat;
	Q_Skip = 0;
	logOutput();
}

function unCutLog(){
	startLog=0; 
	endLog=0;
	logOutput();
}

let logSckl = document.getElementById("logScroll")
let maxYvertScroll = 902, minYvertScroll = 710;

function moveVert(ptr, e) {
	let tmp = e.pageY - ptr.offsetHeight / 2;
	if(tmp >= maxYvertScroll) ptr.style.top = maxYvertScroll;
	else if(tmp <= minYvertScroll) ptr.style.top = minYvertScroll;
	else ptr.style.top = tmp;
	
	let deltaSkip = qFiltElem - Q_MSG;
	Q_Skip = Math.round(deltaSkip * (ptr.style.top.replace('px','')*1 - minYvertScroll)/(maxYvertScroll - minYvertScroll));
	logOutput();
}

logSckl.onmousedown = function(e) {
    document.onmousemove = function(e) {
		moveVert(logSckl, e);
    }
}
let isSynchro = false;
function synchroWithTags(ptr){
	isSynchro = ptr.checked;
	if(isSynchro){
		document.getElementById("dt1").value = document.getElementById("outTime1").innerHTML;
		document.getElementById("dt2").value = document.getElementById("outTime2").innerHTML;
	}
}

function getNumberMsgByDT(dt, i){
	if(isSynchro) document.getElementById("dt"+i).value = dateTimeFormat(dt);
}

let filtrForm = null;

function applyFiltr(filtrForm){
	delMyDiv(filtrForm);
	logOutput();
}

function setFiltr(){
	if(filtrForm)filtrForm.remove();
	filtrForm = document.createElement("div");
	filtrForm.style= "position:absolute; border: 3px double gray; z-index:100; background-color:white; font-weight:bold; left:600; top:500"
	//filtrForm.id = "filtrForm";
	let tmpStr = "<input type=\"button\" id=\"ok\" onclick=\"applyFiltr(filtrForm)\" value=\"OK\"" +
				"style=\"float:right;background-color:green; font-weight:bold\"><span>Отображаемые</br>типы сигналов</span> ";
	document.body.append(filtrForm);
	
	tmpStr =  tmpStr + "<table border=\"1\">";
	for(let i=0; i < typeArr.length; i++)tmpStr +=  
		"<tr style=\" color:"+ typeColorGet(typeArr[i].t) + ";background-color:#000000;\">" + 
			"<th><input type=\"checkbox\" id=\"flt" + i + "\" onchange=\"setFiltrOnType("+i+")\"></th>"+
			"<th style=\"width:150;\">" + typeArr[i].t + "</th>" + 
		"</tr>";
		
	filtrForm.innerHTML =  tmpStr + "</table>";
	
	for(let i=0; i < typeArr.length; i++) document.getElementById("flt" + i).checked = typeArr[i].f;

	filtrForm.onmousedown = function(e) { // 1. отследить нажатие
		dx = e.pageX - this.offsetLeft;//
		dy = e.pageY - this.offsetTop;// 
		document.onmousemove = function(e) {
			moveMyDiv(e,filtrForm);
		}
		//отследить окончание переноса
		filtrForm.onmouseup = function() {
			document.onmousemove = null;
			this.onmouseup = null;
		}
	}
}

function setFiltrOnType(i){
	typeArr[i].f = document.getElementById("flt" + i).checked;
}

function searchTime1(){
	//let start = 0, step = 1;
	//newInTop = document.getElementById("newInTop").checked;
	//if(newInTop){ start = logArr.length - 1; step = -1;}
	let st = dateConvert(document.getElementById("dt1").value);
	
	//let firstFiltElem = -1;
	Q_Skip = 0;
	//for(let i = start; i>= 0 && i<logArr.length - 1; i+= step){
	for(let i = 0; i<logArr.length && logArr[i].dt < st; i++){
		if(logArr[i].dt >= startLog && logArr[i].dt <= endLog && getFiltFlag(logArr[i].type)){
			if(logArr[i].dt) Q_Skip++;
		}
	}
	Q_Skip--;
	fastLogOutput();
}

let startSearch = 0;
let tmpQ_Skip = 0;

function searchSubStp(){
	startSearch = 0;
	tmpQ_Skip = 0;
	searchNext();
}

function searchNext(){
	let ss = document.getElementById("subStrForSearch").value;
	let i;
	for(i = startSearch; i<logArr.length; i++){
		if(logArr[i].dt >= startLog && logArr[i].dt <= endLog && getFiltFlag(logArr[i].type)){
			if(logArr[i].textMsg.indexOf(ss) >= 0){
				Q_Skip = tmpQ_Skip;
				break;
			}else tmpQ_Skip++;
		}
	}
	startSearch = i+1;
	tmpQ_Skip++;
	fastLogOutput();
}

let searchSubStpForm;

function searchSubStpFormConstr(){
	if(searchSubStpForm)searchSubStpForm.remove();
	searchSubStpForm = document.createElement("div");
	searchSubStpForm.style= "position:absolute; border: 3px double gray; z-index:100; background-color:white; font-weight:bold; left:600; top:500"
	document.body.append(searchSubStpForm);
	let tmpStr = 
		"<div style= \"background-color:#888888;height:22;\">"+
			"<input type=\"button\" onclick=\"delMyDiv(searchSubStpForm)\" value=\"X\"" +
				"style=\"float:right;background-color:#FF2222; font-weight:bold\">"+
			"<input type=\"button\" onclick=\"searchNext()\" value=\"Следующее\"" +
			"style=\"float:right;background-color:#4444FF; font-weight:bold\">"+
				"<input type=\"button\" onclick=\"searchSubStp()\" value=\"Найти первое\"" +
			"style=\"float:right;background-color:green; font-weight:bold\">"+
			"<span>Поиск по тексту сообщения</span>"+
		"</div>"+
		"<div style= \"background-color:#cccccc;height:22;\">Подстрока для поиска:"+
			"<input type=\"text\" id=\"subStrForSearch\" style=\"width:500\">" +
		"</div>";
	
	searchSubStpForm.innerHTML =  tmpStr;
	document.getElementById("subStrForSearch").focus();
	searchSubStpForm.onmousedown = function(e) { // 1. отследить нажатие
		dx = e.pageX - this.offsetLeft;//
		dy = e.pageY - this.offsetTop;// 
		document.onmousemove = function(e) {
			moveMyDiv(e,searchSubStpForm);
		}
		//отследить окончание переноса
		searchSubStpForm.onmouseup = function() {
			document.onmousemove = null;
			this.onmouseup = null;
		}
	}
}
