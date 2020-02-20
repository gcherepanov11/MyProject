let trendArray = [[],[]], trendInd = 0;

function arrayConstructor(textData, allTrends){
	if(textData.length == 0) {alert("В выбранном файле нет данных"); return;}
	searchData(textData);
	if(knowDataFormat == 0) {alert("В выбранном файле нет даты"); return;}
	trendArray = [[""],[""]];
	for(let i = 0; i < Q_TREND; i++) trends[i].p = 0;
	let strStart = 0;
	let strEnd = 0;//textData.indexOf("\n", strStart);
	let i = 0;
	let j = 0;
	let tmpStr;
	let maxJ = 0;
	while(strEnd >= 0){
		strEnd = textData.indexOf("\n", strStart);
		tmpStr = textData.slice(strStart, strEnd);
		
		let lexStart = 0, lexEnd = tmpStr.indexOf("\t");
		j = 1;
		while(lexEnd >= lexStart){
			trendArray[i].push(formatNum(tmpStr.slice(lexStart, lexEnd),4));//tmpStr.slice(lexStart, lexEnd
			lexStart = lexEnd + 1;
			lexEnd = tmpStr.indexOf("\t", lexStart);
			j++;
		}
		if(j > maxJ) maxJ = j;
		trendArray[i].push(formatNum(tmpStr.slice(lexStart),4));
		
		if(i == 0) trendArray[0][0] = trendArray[0][1];
		else trendArray[i][0] = dateConvert(trendArray[i][1]);
		
		if(!trendArray[i][0]) delete trendArray[i];
		else {
			trendArray[i].splice(1, 1); // начиная с позиции 1, удалить 1 элемент;
			trendArray.push(""); 
			i++;
		}
		trendArray[i] = new Array(1);
		strStart = strEnd + 1;
		//document.getElementById('progress').innerHTML = i;
	}
	for(i = trendArray.length-1; i>0 && (!trendArray[i] || !trendArray[i][0]); i--) trendArray.pop();
	
	setNewTrendList(allTrends);
	//tableConstructor(trendArray.length,maxJ);
}
function tableConstructor (I, J){
    let table = document.createElement('table'); // таблица с информацией
	table.style.border = "3pt solid #0000FF";
	let row = table.insertRow();
 	for(var i = 0; i < I; i++){  
		for(var j = 0; j < J; j++){  
		let cell = row.insertCell();
			cell.style.border = "1pt solid #00FF00";
			cell.innerHTML = trendArray[i][j];
		}
	}
	out.append(table);
}

let tagList, dx, dy;

function choiceTrend(ptr) {
	let i;
	for(i=0; i < trendList.length; i++) if(trendList[i] == +ptr.id){
		ptr.style = "background-color:white";
		trendList[i] = 0;
		return;
	}
	for(i=0; i < trendList.length; i++){ if(trendList[i] == 0) break; }
	if(i == trendList.length){
		alert("Достигнуто максимальное количество тэгов");
		return;
	}
	trendList[i] = +ptr.id;
	ptr.style= "background-color:#EEEEEE; color:" + grColors[i];
}
function delTrendList(){
	tagList.remove();
}
function putOnTrendList(){
	tagList.remove();
	drawInCanvas();
	setCanvasOffset();
	drawAll();
	drawVarTable();
	startSetTimeScrollings();
}
function setAllTrendListBtn(){
	setAllTrendList();
	putOnTrendList();
}
function setNewTrendList(allTrends){
	trendList = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
	notRangeArr = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
	notShowTrend = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
	for(let i = 0; i < Q_TREND; i++) trends[i].p = 0;
	
	if(allTrends) setAllTrendList();
	else setTrendList();
}

function setAllTrendList(){
	for(let i = 0; i < trendArray[0].length - 1 && i < trendList.length; i++) trendList[i] = i + 1;
}

function drawVarTable(){
	let dataX = canvXtoAbsX(0);
	let dtObj = dateTimeFormat(dataX);
	outTime1.innerHTML = dtObj;//.date + " " + dtObj.time;
 	document.getElementById('tableTime1').innerHTML = dtObj;//.date + " " + dtObj.time;
	dataX = canvXtoAbsX(canvasW);
	dtObj = dateTimeFormat(dataX);
	outTime2.innerHTML = dtObj;//.date + " " + dtObj.time;
 	document.getElementById('tableTime2').innerHTML = dtObj;//.date + " " + dtObj.time;

	let tmp="",tmp1, tmp2, tmp3, tmp4, tmp5;
	for(let i = 0; i<trendList.length; i++){
		if(trendList[i] > 0){
			if(trends[i].p.flagBool){
				tmp1 = "<span style=\"text-align:center; color:"+grColors[i]+";\">false</span>"; 
				tmp2 = "<span style=\"text-align:center; color:"+grColors[i]+";\">true</span>"; 
				tmp3 = "";
				tmp4 = trends[i].p.getValTrend(startTime);
				tmp5 = trends[i].p.getValTrend(endTime);
			}
			else {
				tmp1 = "<input type=\"text\" id=\"min"+i+"\" value="+formatNum(+trends[i].p.min,2)+" style=\"text-align:center; font-weight:bold; width:50; color:"+grColors[i]+";\" onchange=\"reSetMin("+i+")\">";
				tmp2 = "<input type=\"text\" id=\"max"+i+"\" value="+formatNum(+trends[i].p.max,2)+" style=\"text-align:center; font-weight:bold; width:50; color:"+grColors[i]+";\" onchange=\"reSetMax("+i+")\">";
				tmp3 = "<input id=\"notRange"+i+ "\" type=\"checkbox\" onchange=\"notRange("+i+")\">"
				tmp4 = +formatNum(trends[i].p.getValTrend(startTime),4);
				tmp5 = +formatNum(trends[i].p.getValTrend(endTime),4);
			}
			tmp = tmp+"<tr>"+
				"<th style= \"color:"+grColors[i]+";border:1px "+grColors[i]+" solid;\"> "+numToLet(i)+" </th>"+
				"<th style= \"color:"+grColors[i]+";border:1px "+grColors[i]+" solid;\" ondblclick=\"delPar("+i+")\"> "+trendArray[0][trendList[i]]+" </th>"+
				"<th style= \"border:1px "+grColors[i]+ " solid;\">"+tmp1+"</th>" + 
				"<th style= \"border:1px "+grColors[i]+ " solid;\">"+tmp2+"</th>" + 
				//"<th id=\"max"+i+ "\" style= \"color:"+grColors[i]+";border:1px "+grColors[i]+" solid;\" ondblclick=\"reSetMax("+i+")\">"+formatNum(+trends[i].p.max,2)+"</th>" +
				"<th id=\"val"+i+ "_1\" style= \"color:"+grColors[i]+";border:1px "+grColors[i]+" solid;\">"+ tmp4 +"</th>" +
				"<th id=\"val"+i+ "_2\" style= \"color:"+grColors[i]+";border:1px "+grColors[i]+" solid;\">"+ tmp5 +"</th>" +
				"<th style= \"border:1px "+grColors[i]+" solid;\"><input  id=\"notShow"+i+ "\" type=\"checkbox\"  onchange=\"notShowTr("+i+")\"></th>" +
				"<th style= \"border:1px "+grColors[i]+" solid;\">"+tmp3+"</th>" +
			"</tr>";
		}
	}
	trendTable.innerHTML = trendTable.innerHTML+tmp;
	for(let i = 0; i<trendList.length; i++) if(trendList[i] > 0){
		let tmp = document.getElementById('notRange' + i);
		if(tmp) tmp.checked = notRangeArr[i];
		document.getElementById('notShow' + i).checked = notShowTrend[i];
	}

}

function notRange(i){
	notRangeArr[i] = document.getElementById('notRange' + i).checked;
	setCanvasOffset();
	drawAll();
	//startSetTimeScrollings();
}

function notShowTr(i){
	notShowTrend[i] = document.getElementById('notShow' + i).checked;
	setCanvasOffset();
	drawAll();
}
function delPar(i){
	if(confirm("Удалять тренд \""+trendArray[0][trendList[i]]+"\"?")){
		trendList[i] = 0;
		notRangeArr[i] = 0;
		notShowTrend[i] = 0;
		trends[i].p = 0;
		drawInCanvas();
		drawVarTable();
	}
}

function reDrawTrendsWithNewRange(){
	drawAll();
}

function reSetMin(i){
	let x = +formatNum(document.getElementById('min' + i).value, 3);
	if((x || x == 0) && trends[i].p.max > x){
		trends[i].p.min = x;
		trends[i].p.range = trends[i].p.max - trends[i].p.min;
		reDrawTrendsWithNewRange();
	}else {
		alert("Недопустипое значение минимума шкалы: "+document.getElementById('min' + i).value);
		document.getElementById('min' + i).value = trends[i].p.min;
	}
}	
function reSetMax(i){
	let x = +formatNum(document.getElementById('max' + i).value, 3);
	if((x || x == 0) && trends[i].p.min < x){
		trends[i].p.max = x;
		trends[i].p.range = trends[i].p.max - trends[i].p.min;
		reDrawTrendsWithNewRange();
	}else{
		alert("Недопустипое значение максимума шкалы: "+document.getElementById('max' + i).value);
		document.getElementById('max' + i).value = trends[i].p.max;
	}
}	
function setTrendList(){
	if(tagList)tagList.remove();
	tagList = document.createElement("div");
	tagList.style= "position:absolute; border: 3px double green; z-index:100; background-color:white; font-weight:bold"
	tagList.id = "tagList";
	let tmpStr = "Список тэгов <input type=\"button\" id=\"del\" onclick=\"delTrendList()\" value=\"X\"" +
				"style=\"float:right;background-color:red; font-weight:bold\">" +
				"<input type=\"button\" id=\"ok\" onclick=\"putOnTrendList()\" value=\"OK\"" +
				"style=\"float:right;background-color:green; font-weight:bold\">"+
				"<input type=\"button\" id=\"All\" onclick=\"setAllTrendListBtn()\" value=\"All\"" +
				"style=\"float:right;background-color:blue; font-weight:bold\">";
	document.body.append(tagList);
	tmpStr =  tmpStr + "<table border=\"1\">";
	for(let i=1; i < trendArray[0].length; i++){
		let j;
		for(j=0; j<trendList.length; j++) if(trendList[j] == i){
			tmpStr =  tmpStr + " <tr><td id = \"" + i + "\" style=\" color:"+grColors[j]+";background-color:#EEEEEE;\" ondblclick=\"choiceTrend(this)\"> " + trendArray[0][i] + " </td></tr> ";
			break;
		}
		if(j== trendList.length) tmpStr =  tmpStr + " <tr><td id = \"" + i + "\" style=\" color:#444444; background-color:#FFFFFF;\" ondblclick=\"choiceTrend(this)\"> " + trendArray[0][i] + " </td></tr> ";
	}
	tagList.innerHTML =  tmpStr + "</table>";
	tagList.onmousedown = function(e) { // 1. отследить нажатие
		dx = e.pageX - this.offsetLeft;//
		dy = e.pageY - this.offsetTop;// 
		document.onmousemove = function(e) {
			moveTrendList(e);
		}
		//отследить окончание переноса
		tagList.onmouseup = function() {
			document.onmousemove = null;
			this.onmouseup = null;
		}
	}
}

function moveTrendList(e) {
    tagList.style.left = e.pageX - dx;// + 'px';
    tagList.style.top = e.pageY - dy;// + 'px';
}

