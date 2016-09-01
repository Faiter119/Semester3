/**
 * Created by OlavH on 16/09/01.
 */

var date = new Date(); // Current day

var putDate = document.getElementById("days");
putDate.innerHTML = date.toISOString().split("T")[0]; // removes that trash at the end

var christmas = parseIsoDatetime("2016-12-24");
var putDaysUntil = document.getElementById("daysUntilChristmas");
putDaysUntil.innerHTML = dateDiffInDays(date, christmas);

var monthsUntilChrismas = dateDiffInDays(date, christmas) / 30;
document.getElementById("daysUntilChristmas").className = (monthsUntilChrismas > 3) ? "overThreeMonths" : (monthsUntilChrismas <= 1) ?
    "underAMonth" : "oneToThreeMonths";

function parseIsoDatetime(dtstr) { // http://stackoverflow.com/questions/4829569/help-parsing-iso-8601-date-in-javascript
    var dt = dtstr.split(/[: T-]/).map(parseFloat);
    return new Date(dt[0], dt[1] - 1, dt[2], dt[3] || 0, dt[4] || 0, dt[5] || 0, 0);
}
function dateDiffInDays(a, b) {// http://stackoverflow.com/questions/3224834/get-difference-between-2-dates-in-javascript

    var utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
    var utc2 = Date.UTC(b.getFullYear(), b.getMonth(), b.getDate());

    return Math.floor((utc2 - utc1) / (1000 * 60 * 60 * 24)); // ms per dag
}
function dateDiffInMonths(start, end) {
    return dateDiffInDays(start, end) / 30;
}

function setClassByDateContent(id) {

    var text = document.getElementById(id).innerHTML;
    var date = parseIsoDatetime(text);

    console.log(date);
    var diffInMonths = dateDiffInMonths(date, christmas);
    console.log(dateDiffInDays(date, christmas));

    document.getElementById(id).className = (diffInMonths <= 1) ? "underAMonth" : (diffInMonths > 3) ? "overThreeMonths" : "oneToThreeMonths";
}
setClassByDateContent("1month");
setClassByDateContent("2months");
