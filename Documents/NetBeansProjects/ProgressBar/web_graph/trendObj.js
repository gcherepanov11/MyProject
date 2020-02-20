let grColors = ["#FF0000","#008800","#0000FF","#800000","#FF00FF","#008888","#000000","#888888","#888800","#880088",
				"#4F0040","#008000","#00444F","#8000F0","#444F4F","#008008","#00F00F","#800888","#808800","#440088",
				"#4F4444","#666F66","#00000F","#800F00","#0F00FF","#000880","#0F40F4","#888008","#084844","#884444",
				"#088808","#88004F"];
let cx = document.querySelector("canvas").getContext("2d");
let canvasTop, canvasLeft, canvasW, canvasH, canvasOffset = 0, canvasSqueeze = 0;
let Q_TREND = 32;
let trends = [{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0},{p:0}];
let trendTable = document.getElementById('table1');
let trendList = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
let notRangeArr = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
let notShowTrend = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
let QX = 10, QY = 10;
let offsetRengeLine;
let startTime, endTime, deltaT, commStartTrend, commEndTrend;
let showRangers = true, showLabels = true;

function drawAll(){
	drawGrid();
	offsetRengeLine = 1;
	if(showRangers) for(let i=0; i<Q_TREND; i++) if(trendList[i] > 0 && trendArray[0].length > trendList[i] && !notShowTrend[i] && !notRangeArr[i] && !trends[i].p.flagBool) trends[i].p.drawRanger();
	                for(let i=0; i<Q_TREND; i++) if(trendList[i] > 0 && trendArray[0].length > trendList[i] && !notShowTrend[i]) trends[i].p.drawTrend();
	if(showLabels)  for(let i=0; i<Q_TREND; i++) if(trendList[i] > 0 && trendArray[0].length > trendList[i] && !notShowTrend[i]) trends[i].p.drawLabel();
	
	let step = (endTime - startTime)/10;
	for(let i=0; i<11; i++){
		tmp = document.getElementById("tr"+i);
		tmp.innerHTML = timeFormat(startTime + i*step);
	}
}

function createTrends(){
	if(trendArray[0].length < 2) return;
	
	startTime = +trendArray[1][0];
	endTime = +trendArray[trendArray.length - 1][0];
	if(startTime > endTime){
		let tmp = startTime;
		startTime = endTime;
		endTime = tmp;
	}
	else if(endTime == startTime) return;
	
	deltaT = endTime - startTime;
	
	commStartTrend = 1;
	commEndTrend = trendArray.length - 1;
	
	for(let i=0; i<Q_TREND; i++) if(trendList[i] > 0 && trendArray[0].length > trendList[i]) createOneTrend(trends[i], trendList[i],i);
	//drawAll();
}

function reCreateTrends(){
	if((trendArray[0].length < 2) ||
	   (endTime == startTime) ||
	   (startTime > +trendArray[1][0] &&	startTime > +trendArray[trendArray.length - 1][0]) ||
	   (endTime   > +trendArray[1][0] &&	endTime   > +trendArray[trendArray.length - 1][0]) ||
	   (startTime < +trendArray[1][0] &&	startTime < +trendArray[trendArray.length - 1][0]) ||
	   (endTime   < +trendArray[1][0] &&	endTime   < +trendArray[trendArray.length - 1][0])    ) return;
	   
	if(startTime > endTime){
		let tmp = startTime;
		startTime = endTime;
		endTime = tmp;
	}
	
	deltaT = endTime - startTime;
	
	for(commStartTrend = 1; commStartTrend < trendArray.length; commStartTrend++) if(trendArray[commStartTrend][0] >= startTime && trendArray[commStartTrend][0] <= endTime) break;
	for(commEndTrend = trendArray.length - 1; commEndTrend > 0; commEndTrend--)   if(trendArray[commEndTrend][0]   >= startTime && trendArray[commEndTrend][0]   <= endTime) break;
	
	for(let i=0; i<Q_TREND; i++) if(trendList[i] > 0 && trendArray[0].length > trendList[i]) createOneTrend(trends[i], trendList[i],i);
	drawAll();
}

function createOneTrend(trendPtr, numAnPar, numTrend){//tArr, 
	let flagBool = false, max, min;
	if(trendPtr.p.max){
		flagBool = trendPtr.p.flagBool;
		max = trendPtr.p.max;
		min = trendPtr.p.min;
	}else{
		max = +trendArray[1][numAnPar];
		min = +trendArray[1][numAnPar];
		if(max && min || max == 0 && min == 0){
			for(let i=2; i<trendArray.length; i++){
				if     (+trendArray[i][numAnPar] > max) max = +trendArray[i][numAnPar];
				else if(+trendArray[i][numAnPar] < min) min = +trendArray[i][numAnPar];
			}
			max = calcLim(max, true);
			min = calcLim(min, false);
			let pow1 = Math.log10(max);
			let pow2 = Math.log10(min);
			if(pow1 > pow2 + 1) min = 0;
			else if(pow2 > pow1 + 1) max = 0;
			if(max == min) max = min + 1;
		}else{ 
			max = 1; min = 0; 
			let s = trendArray[1][numAnPar] + "";
			flagBool = s.toUpperCase()=="TRUE" || s.toUpperCase()=="FALSE";
		}
	}
	trendPtr.p = new TrendConstr(min, max, numAnPar, numTrend, flagBool); //tArr,(date,min,max,start,end, numAnPar, numTrend)
	//trendPtr.p.drawRanger();
}

function drawInCanvas(){
	createTrends();
	trendTable.innerHTML =  "<tr>"+
								"<th style= \"color:black; border:1px black solid;\">№</th>" +
								"<th style= \"color:black; border:1px black solid;\">    Название тега   </th>" +
								"<th style= \"color:black; border:1px black solid; width:40; \">мин</th>" +
								"<th style= \"color:black; border:1px black solid; width:40; \">макс</th>" +
								"<th id=\"tableTime1\" style= \"color:black; border:1px black solid; width:80; \"> время 1 </th>" +
								"<th id=\"tableTime2\" style= \"color:black; border:1px black solid; width:80; \"> время 2 </th>" +
								"<th style= \"color:red; border:1px black solid; width:30; \">&#10008;</th>" +
								"<th style= \"color:black; border:1px black solid; width:30; cursor: pointer;\" ondblclick=\"resetAllShowRange()\"><s>Ш</s></th>"+
							"</tr>";
}

function resetAllShowRange(){
	for(let i = 0; i<trendList.length; i++) if(trendList[i] > 0){
		let tmp = document.getElementById('notRange' + i);
		if(tmp) {tmp.checked = true; notRangeArr[i] = true;}
	}
	setCanvasOffset();
	drawAll();
	startSetTimeScrollings();
}

function setCanvasOffset(){
	canvasOffset = 0;
	if(showRangers) 
		for(let i=0; i<Q_TREND; i++) 
			if(trendList[i] > 0 && trendArray[0].length > trendList[i] && !notShowTrend[i] && !notRangeArr[i] && !trends[i].p.flagBool) canvasOffset += trends[i].p.measureWidthRanger();
	canvasSqueeze = canvasOffset;
	if(canvasSqueeze > 500) canvasSqueeze = 500;
	
	let canvasPtr = document.getElementById("canvas1");
	canvasLeft = canvasOffset;//canvasPtr.style.left.replace('px','')*1 + canvasOffset;
	canvasTop = canvasPtr.style.top.replace('px','')*1;
	canvasW = 1200 - canvasSqueeze;
	canvasH = canvasPtr.height-7;
	canvasPtr.width = 1200 + canvasOffset - canvasSqueeze;
	
	var tmp; 
	let gridXstep = canvasW/10;
	for(let i=0; i<11; i++){
		tmp = document.getElementById("tr"+i);
		tmp.style.left = canvasLeft+i*gridXstep-40
		tmp.style.top = canvasTop+canvasH+5
	}
	trendTable.style.left = canvasLeft + canvasW;
	if(document.getElementById("val0_1") && (+scrollLine1.style.left.replace("px","") < canvasLeft || +scrollLine1.style.left.replace("px","") > (canvasLeft + canvasW) ||
											 +scrollLine2.style.left.replace("px","") < canvasLeft || +scrollLine2.style.left.replace("px","") > (canvasLeft + canvasW))) startSetTimeScrollings();
}

window.onload = function(){
	for(let i=0; i<11; i++){
		tmp = document.createElement("div");
		tmp.style= "position:absolute; width:80; height:27; text-align: center; font-size:12px;font-weight:bold;color:#777777;";
		tmp.id = "tr"+i;
		document.body.append(tmp);
	}
	//setCanvasOffset();
	checkJavaComm();
}

function drawGrid(){
	cx.clearRect(0, 0, canvasW, canvasH+7);
	cx.fillStyle   = "#EEEEEE";
	cx.fillRect(canvasLeft, 0, canvasW, canvasH);

	cx.strokeStyle = "#888888";
	cx.lineWidth = 1;
	cx.beginPath();
	let stepX = canvasW/QX;
	let stepY = canvasH/QY;

	for(let i = 0; i <= QX; i++){
		cx.moveTo(i*stepX + canvasLeft, 0);
		cx.lineTo(i*stepX + canvasLeft, canvasH+5);
	}
	for(let i = 0; i <= QY; i++){
		cx.moveTo(canvasLeft, i*stepY);
		cx.lineTo(canvasW + canvasLeft, i*stepY);
	}
	cx.stroke();
}
function absXtoCanvX(x, min, range){//абсолютное значение, левый край сетки, диапазон по х
	return (x - min)/range * canvasW + canvasLeft;
}
function absYtoCanvY(y, min, range){//абсолютное значение, нижний край сетки, диапазон по у
	return canvasH - (y - min)/range * canvasH;
}
function canvXtoAbsX(x){//координаты по х, левый край сетки, диапазон по х
	return (x - canvasLeft)/canvasW * deltaT + startTime;
}

function TrendConstr(min, max, numAnPar, numTrend, flagBool){//trendArray,
	this.flagBool = flagBool;
	this.numAnPar = numAnPar;
	this.numTrend = numTrend;
	this.stepT = trendArray[2][0] - trendArray[1][0]; //предполагается, что дата и время в столбце [0]
	
	if(this.stepT == 0 || deltaT == 0 ){
		this.qStepT = 0;
		this.stepX = 0;
	} else {
		this.qStepT = deltaT/this.stepT;
		this.stepX = canvasW/this.qStepT;
	}
	
	if(flagBool){
		this.min = 0;
		this.max = 1;
	}else if(min < max){
		this.min = min;
		this.max = max;
	}
	this.range = this.max - this.min;
	if(this.range == 0) this.range = 100;
	
	//------------------------------------------------------
	this.drawTrend = function(){
		cx.beginPath();
		cx.strokeStyle = grColors[this.numTrend];
		cx.fillStyle = grColors[this.numTrend];
		let x = absXtoCanvX(trendArray[commStartTrend][0], startTime, deltaT); //абсолютное значение, левый край сетки, диапазон по х
		if(this.flagBool){
			let y = canvasH - this.numTrend * 20 - 20;
			let h = 1, offset = 0;
			let x0 = x;
			let prevVal = trendArray[commStartTrend][this.numAnPar].toUpperCase();
			let val;
			for(let i = commStartTrend; i < commEndTrend; i++){
				x = absXtoCanvX(trendArray[i][0],startTime, deltaT);//абсолютное значение, левый край сетки, диапазон по х
				val = trendArray[i][this.numAnPar].toUpperCase();
				if(val != prevVal){
					if(val == "TRUE") cx.fillRect(x0,y,    x-x0,1);
					else 			  cx.fillRect(x0,y - 5,x-x0,9);
					x0 = x;
				}
				prevVal = val;
			}
			if(val == "FALSE") cx.fillRect(x0,y,    x-x0,1);
			else 			   cx.fillRect(x0,y - 5,x-x0,9);
		}else{
			let y = absYtoCanvY(trendArray[commStartTrend][this.numAnPar], this.min, this.range); //абсолютное значение, нижний край сетки, диапазон по у
			cx.moveTo(x, y);
			let step = Math.round((commEndTrend - commStartTrend)/3000);
			if(step < 1) step = 1;
			//for(let i = commStartTrend; i < trendArray.length && trendArray[i][0] >= startTime && trendArray[i][0] <= endTime; i++){
			for(let i = commStartTrend; i < commEndTrend; i+=step){
				x = absXtoCanvX(trendArray[i][0],startTime, deltaT);//абсолютное значение, левый край сетки, диапазон по х
				y = absYtoCanvY(trendArray[i][this.numAnPar], this.min,   this.range); //абсолютное значение, нижний край сетки, диапазон по у
				cx.lineTo(x, y);
			}
			cx.stroke();
		}		
	}
	this.drawRanger = function(){
		if(this.flagBool) return;
		cx.beginPath();
		cx.strokeStyle = grColors[this.numTrend];
		cx.font = "13px Arial";
		cx.fillStyle = grColors[this.numTrend];
		let x = offsetRengeLine+1;
		offsetRengeLine += this.measureWidthRanger();
		let y = 0;
		cx.moveTo(x, y);
		cx.lineTo(x, y + canvasH);
		y = 0;
		cx.moveTo(x, y+1);
		cx.lineTo(x+5, y+1);
		cx.fillText(formatNum(this.max, 2), x+5, y+12);
		for(let i = 1; i< QY; i++){
			y = i*canvasH/QY;
			cx.moveTo(x, y);
			cx.lineTo(x +5, y);
			cx.fillText(formatNum(this.max - this.range/QY * i, 2), x+5, y+5);
		}
		cx.moveTo(x, canvasH - 1);
		cx.lineTo(x+5, canvasH - 1);
		cx.fillText(formatNum(this.min, 2), x+5, canvasH-5);
		cx.stroke();		
	}
	this.measureWidthRanger = function(){
		let l1 = (formatNum(this.max, 2)+"").length;
		let l2 = (formatNum(this.min, 2)+"").length;
		if(l1 > l2) return l1 * 7 + 12;
		return l2 * 7 + 12; 
	}
	//------------------------------------------------------
	this.getTrendPiece = function(val){ // val - Значение времени
		let s = commStartTrend, e = commEndTrend;
		let j;
		if(this.stepT > 0){
			if(trendArray[s+1][0] >= val) return s;
			if(trendArray[e-1][0] <  val) return e-1;
			
			j = Math.floor((e + s)/2);
			while(j > s){
				if(trendArray[j][0] <= val && trendArray[j+1][0] > val) return j;
				else if(trendArray[j][0] < val) s = j;
				else e = j;
				j = Math.floor((e + s)/2);
			}
		}else{
			if(trendArray[s+1][0] <  val) return s;
			if(trendArray[e-1][0] >= val) return e-1;
			
			j = Math.floor((e + s)/2);
			while(j > s){
				if(trendArray[j][0] >= val && trendArray[j+1][0] < val) return j;
				else if(trendArray[j][0] > val) s = j;
				else e = j;
				j = Math.floor((e + s)/2);
			}
		}
		//for(j = commStartTrend; j < (trendArray.length-1) && ((trendArray[j][0] > val && trendArray[j+1][0] > val) || (trendArray[j+1][0] < val && trendArray[j][0] < val)); j++){}
		return j
	}
	//--------------------------------------------------------
	this.getValTrend = function(time){ //, dateкоордината по х (время), данные, 
		let j = this.getTrendPiece(time);
		return this.getValTrendFromInd(j, time);
	}
	
	this.getValTrendFromInd = function(j, time){
		if(this.flagBool || j >= trendArray.length-1) return trendArray[j][this.numAnPar];
		
		let x0 = +trendArray[j][0], y0 = +trendArray[j][this.numAnPar];
		return (time - x0)/(+trendArray[j+1][0] - x0)*(+trendArray[j+1][this.numAnPar] - y0) + y0;
		//return +trendArray[j][this.numAnPar];
	}
	//-------------------------------------------------------
	this.drawLabel = function(){ //date
		let Q_LAB = 5;
		let stepLabel = deltaT/Q_LAB;
		let xLabelTime, x, y;
		for(let i = 0; i < Q_LAB; i++){
			xLabelTime = startTime + (1/Q_TREND * this.numTrend + i)*stepLabel + 5;
			x = absXtoCanvX(xLabelTime, startTime, deltaT);//абсолютное значение, левый край сетки, диапазон по х
			if(this.flagBool)y = canvasH - this.numTrend * 20 - 20;
			else             y = absYtoCanvY(this.getValTrend(xLabelTime), this.min, this.range);//, dateабсолютное значение, нижний край сетки, диапазон по у;
			cx.fillStyle   = grColors[this.numTrend];
			cx.fillRect(x-6,y-7,11,14);
			cx.fillStyle = "#FFFFFF";
			cx.fillText(numToLet(this.numTrend), x-5, y+5);
		}
	} 
}
