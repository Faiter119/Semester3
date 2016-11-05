/**
 * Created by OlavH on 16/08/31.
 */



var tiles = document.getElementsByClassName("tabellrute");


var turn = 0; // 0 = first, 1 = second
console.log("tiles: "+tiles.length);

for(i=0; i<tiles.length; i++){

    console.log("tile: "+i);

    var tile = tiles[i];
    var height = tile.height;
    var width = tile.width;

    tile.onmousedown = function () {

        if (this.className == "playerOne" || this.className == "playerTwo"){

            if ( (this.className == "playerOne" && turn==0) || (this.className == "playerTwo" && turn==1)){
                return;
            }

            turn = turn ==0 ? 1 : 0;
            this.className = "tabellrute";
            return;
        }

        if ( turn == 0 && this.className != "playerTwo") {
            this.className = "playerOne";
            turn = 1;
        }
        else if ( turn == 1 && this.className != "playerOne"){
            this.className = "playerTwo";

            turn = 0;
        }
        else if (clicks >= 2){clicks = 0;}

    }
}

function reset() {

    for(i=0; i<tiles.length; i++){

        tiles[i].style = "";
    }
}