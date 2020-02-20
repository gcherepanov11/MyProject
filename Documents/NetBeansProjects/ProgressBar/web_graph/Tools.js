function formatNum(y, q){
	let x = +y;
	if(!x) return y;
	let pow = Math.floor(Math.log10(x));
	if(q > pow) return x.toFixed(q-pow);
	else return Math.round(x);
}

function searchData(textData){
	knowDataFormat = 0;
	for(let i=0; i<textData.length && knowDataFormat == 0; i++) identDateFormat(textData.slice(i, i+10));
}

let labelArr = ["0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","F","G","H","I","J","K","L","K","M","N","P","R","S","T","U","W","X","Y","Z"];

function numToLet(i){
	if(i<labelArr.length) return labelArr[i];
	let tmp = 9824 + i - labelArr.length
	return "&#" + tmp + ";";
}

function checkTime(x){
	if(x == "") {stepTimeToShift = 0; return;}
	stepTimeToShift = getDeltaT(x);
	if(stepTimeToShift <= 0){
		stepTimeToShift = 0;
		alert("Неправильный формат даты для сдвига.\nПример правильного формата: 12h34m56s789ms\nДопускается сокращённый формат, например: 30s или 20m или 2h30m");
	}
}

function getDeltaT(x){
	let h = 0,m = 0,s = 0, ms = 0;
	let end = x.indexOf("h"), start = 0;
	if(end > 0) { 
		h = +x.slice(start,end);
		if(!h && h !=0 || h < 0 || h > 60) return -1;
		start = end+1;
	}
	end = x.indexOf("m",start);
	if(end > 0 && x[end+1] != "s") { 
		m = +x.slice(start,end);
		if(!m && m !=0 || m < 0 || m > 60) return -1;
		start = end+1;
	}
	end = x.indexOf("s",start);
	if(end > 0 && x[end-1] != "m") { 
		s = +x.slice(start,end);
		if(!s && s !=0 || s < 0 || s > 60) return -1;
		start = end+1;
	}
	end = x.indexOf("ms",start);
	if(end > 0) { 
		ms = +x.slice(start,end);
		if(!ms && ms !=0 || ms < 0 || ms > 1000) return -1;
	}
	return ret = h*3660000 + m*60000 + s*1000 + ms*1;
}

function logorifm10(x){
	readInternalFile(x);
}

function readInternalFile(fileName) {
	out.innerHTML = "";
	let file = fileName;
	let reader = new FileReader();
	reader.readAsText(file);
	reader.onload = function() {
		arrayConstructor(reader.result,true);
		//reader = null;
	};
	reader.onerror = function() {
		alert("No this file!");
		out.innerHTML = reader.error;
	};
}

function calcLim(n, hiLim){
	let sign = 1;
	if(n < 0){
		sign = -1;
		hiLim = !hiLim;
	}
	x = n * sign;
	if(!x) return n;
	let pow = Math.floor(Math.log10(x));
	let base = x / Math.pow(10, pow);
	if(hiLim){
		if(base == 1.0) return sign *  Math.pow(10, pow);
		else if(base <= 1.5) return sign *  1.5 * Math.pow(10, pow);
		else if(base <= 2.0) return sign *  2 * Math.pow(10, pow);
		else if(base <= 2.5) return sign *  2.5 * Math.pow(10, pow);
		else return sign *  Math.ceil(base) * Math.pow(10, pow);
	}else{
		if(base < 1.5) return sign *  Math.pow(10, pow);
		else if(base < 2.0) return sign *  1.5 * Math.pow(10, pow);
		else if(base < 2.5) return sign *  2 * Math.pow(10, pow);
		else if(base < 3.0) return sign *  2.5 * Math.pow(10, pow);
		else return sign *  Math.floor(base) * Math.pow(10, pow);
	}
}

function timeFormat(dataX){
	let d = new Date(dataX);
	let ms = d.getMilliseconds(); //----------------
	if     (ms<10) ms = "00" + ms;
	else if(ms<100) ms = "0" + ms;
	
	let s = d.getSeconds(); //----------------
	if     (s<10) s = "0" + s;
	
	let m = d.getMinutes(); //---------------
	if     (m<10) m = "0" + m;
	
	let h = d.getUTCHours(); //---------------
	if     (h<10) h = "0" + h;

	return h + ":" + m + ":" + s + "." + ms;
}

function dateTimeFormat(dataX){
	let d = new Date(dataX);
	let ms = d.getMilliseconds(); //----------------
	if     (ms<10) ms = "00" + ms;
	else if(ms<100) ms = "0" + ms;
	
	let s = d.getSeconds(); //----------------
	if     (s<10) s = "0" + s;
	
	let m = d.getMinutes(); //---------------
	if     (m<10) m = "0" + m;
	
	let h = d.getUTCHours(); //---------------
	if     (h<10) h = "0" + h;
	
	let mm = d.getUTCMonth()+1; //----------------
	if(mm<10) mm = "0" + mm;

	let yyyy = d.getUTCFullYear() + "";
	
	let ret;
	
	switch(knowDataFormat){
		case 0: return 0;
		case 1: return d.getUTCFullYear() + "-" + mm + "-" + d.getUTCDate()+" "+ h + ":" + m + ":" + s + "." + ms;		
		case 2: return d.getUTCDate() + "/" + mm +"/"+ yyyy+" "+ h + ":" + m + ":" + s + "." + ms;
		case 3: return d.getUTCDate() + "." + mm +"."+ yyyy.slice(2)+" "+ h + ":" + m + ":" + s + "." + ms;
		default: return 0
	}
	return 0;
}

let knowDataFormat = 0;

function identDateFormat(dateTime){
	knowDataFormat = identDateFormatOnLine(dateTime);
}
function identDateFormatOnLine(dateTime){
	let yy = dateTime.slice(0,4);
	let mm = dateTime.slice(5,7);
	let dd = dateTime.slice(8,10);
	if( yy.indexOf(".")<0 && yy.indexOf("-")<0 && yy.indexOf("/")<0 && yy.indexOf(" ")<0 && 
		mm.indexOf(".")<0 && mm.indexOf("-")<0 && mm.indexOf("/")<0 && mm.indexOf(" ")<0 &&
		dd.indexOf(".")<0 && dd.indexOf("-")<0 && dd.indexOf("/")<0 && dd.indexOf(" ")<0 &&
		+yy>1969 && +yy<2200 && +mm>=1 && +mm<=12 && +dd>=1 && +dd<=31) 					return 1;
	
	dd = +dateTime.slice(0,2);
	mm = +dateTime.slice(3,5);
	yy = dateTime.slice(6,10);
	if(yy.indexOf(".")<0 && yy.indexOf("-")<0 && yy.indexOf("/")<0 && yy.indexOf(" ")<0 && 
		+yy>1969 && +yy<2200 && mm>=1 && mm<=12 && dd>=1 && dd<=31) 						return 2;
	
	dd = +dateTime.slice(0,2);
	mm = +dateTime.slice(3,5);
	yy = +dateTime.slice(6,8);
	if(yy>=19 && yy<=20 && mm>=1 && mm<=12 && dd>=1 && dd<=31) 								return 3;
	
	return 0;
}	

function dateConvert(dateTime){
	switch(knowDataFormat){
		case 0: return 0;
		case 1: return Date.parse(dateTime.slice(0,4)+"-"+dateTime.slice(5,7)+"-"+dateTime.slice(8,10)+"T"+dateTime.slice(11)+"Z");
		case 2: return Date.parse(dateTime.slice(6,10)+"-"+dateTime.slice(3,5)+"-"+dateTime.slice(0,2)+"T"+dateTime.slice(11)+"Z");
		case 3: return Date.parse("20"+dateTime.slice(6,8)+"-"+dateTime.slice(3,5)+"-"+dateTime.slice(0,2)+"T"+dateTime.slice(9)+"Z");
		default: return 0
	}
}

let mouseX, mouseY;
document.onmousedown = function(e) {
	mouseX = e.pageX;
	mouseY = e.pageY;
}

//----------------- Освобождение мышки от всех функций при отпускании кнопки ---------------------------------------
document.onmouseup = function() {
    document.onmousemove = null;
    scr.onmouseup = null;
    scr2.onmouseup = null;
	logSckl.onmouseup = null;
	if(tagList) tagList.onmouseup = null;
}

function delMyDiv(ptr) {
    ptr.remove();
}

function moveMyDiv(e,ptr) {
    ptr.style.left = e.pageX - dx;// + 'px';
    ptr.style.top = e.pageY - dy;// + 'px';
}

/*
//---------- Форма с кнопками 1,2,..,9,0 и "ОК" и "Х" --------------------------------------
let out123 = document.getElementById('tmpOut');
let form123, return123, dx123, dy123;

function delForm123(){
	form123.remove();
}
function putOnForm123(){
	return123=document.getElementById('newVal').value;
	alert(+return123);
	form123.remove();
	alert(return123);
}
function addN(x){
	if(+x > 10) document.getElementById('newVal').value = document.getElementById('newVal').value.slice(0, -1);
	else if(+x < 0 ) document.getElementById('newVal').value = document.getElementById('newVal').value + ".";
	else document.getElementById('newVal').value = document.getElementById('newVal').value + x;
}

function formaForNum(title, val){
	form123 = document.createElement("div");
	form123.style= "position:absolute; border: 3px double blue; z-index:100; background-color:white; font-weight:bold; align:"
	form123.id = "tagList";
	let tmpStr = title+" <input type=\"button\" id=\"del\" onclick=\"delForm123()\" value=\"X\" style=\"float:right;background-color:red; font-weight:bold\">" +
				        "<input type=\"button\" id=\"ok\" onclick=\"putOnForm123()\" value=\"OK\" style=\"float:right;background-color:green; font-weight:bold\">" + 
						"<input type=\"text\" id=\"newVal\" value="+val+" style=\"width:50;\"></br>" + 
				        "<input type=\"button\" onclick=\"addN(1)\" value=\"1\" style=\"width:50;font-weight:bold\">" + 
				        "<input type=\"button\" onclick=\"addN(2)\" value=\"2\" style=\"width:50;font-weight:bold\">" + 
				        "<input type=\"button\" onclick=\"addN(3)\" value=\"3\" style=\"width:50;font-weight:bold\"></br>" +
				        "<input type=\"button\" onclick=\"addN(4)\" value=\"4\" style=\"width:50;font-weight:bold\">" + 
				        "<input type=\"button\" onclick=\"addN(5)\" value=\"5\" style=\"width:50;font-weight:bold\">" + 
				        "<input type=\"button\" onclick=\"addN(6)\" value=\"6\" style=\"width:50;font-weight:bold\"></br>" + 
				        "<input type=\"button\" onclick=\"addN(7)\" value=\"7\" style=\"width:50;font-weight:bold\">" +
				        "<input type=\"button\" onclick=\"addN(8)\" value=\"8\" style=\"width:50;font-weight:bold\">" + 
				        "<input type=\"button\" onclick=\"addN(9)\" value=\"9\" style=\"width:50;font-weight:bold\"></br>" + 
				        "<input type=\"button\" onclick=\"addN(0)\" value=\"0\" style=\"width:50;font-weight:bold\">" +
				        "<input type=\"button\" onclick=\"addN(-1)\" value=\",\" style=\"width:50;font-weight:bold\">" +
				        "<input type=\"button\" onclick=\"addN(+100)\" value=\"<-\" style=\"width:50;font-weight:bold\">"; 
	form123.innerHTML =  tmpStr;
	document.body.append(form123);
	
	form123.onmousedown = function(e) { // 1. отследить нажатие
		dx123 = e.pageX - this.offsetLeft;//
		dy123 = e.pageY - this.offsetTop;// 
		document.onmousemove = function(e) {
			moveForm123(e);
		}

		//отследить окончание переноса
		form123.onmouseup = function() {
			document.onmousemove = null;
			this.onmouseup = null;
		}
	}

	document.onmouseup = function() {
		document.onmousemove = null;
		form123.onmouseup = null;
	}
	return return123;
}

function moveForm123(e) {
    form123.style.left = e.pageX - dx123;// + 'px';
    form123.style.top = e.pageY - dy123;// + 'px';
}
*/

/*
function dateConvertOld(dateTime){
	let yyyy = +dateTime.slice(0,4);
	let ret;
	if(yyyy && yyyy > 1970 && yyyy < 3000){
		ret = Date.parse(dateTime + " GMT");
		if(ret) return ret;
		
		ret = Date.parse(dateTime);
		if(ret) return ret;
	}
	
	ret = Date.parse(dateTime.slice(6,10)+"-"+dateTime.slice(3,5)+"-"+dateTime.slice(0,2)+"T"+dateTime.slice(11)+"Z");
	if(ret) return ret;
	
	return Date.parse("20"+dateTime.slice(6,8)+"-"+dateTime.slice(3,5)+"-"+dateTime.slice(0,2)+"T"+dateTime.slice(9)+"Z");
}
	let ret = {date: d.getUTCDate() + "/" + mm +"/"+d.getUTCFullYear(), time: h + ":" + m + ":" + s + "." + ms};
		case 1: ret = {date: d.getUTCFullYear() + "-" + mm + "-" + d.getUTCDate(), time: h + ":" + m + ":" + s + "." + ms};	        break;		
		case 2: ret = {date: d.getUTCDate() + "/" + mm +"/"+ yyyy, time: h + ":" + m + ":" + s + "." + ms};			break;
		case 3: ret = {date: d.getUTCDate() + "/" + mm +"/"+ yyyy.slice(2), time: h + ":" + m + ":" + s + "." + ms};	break;
	

*/