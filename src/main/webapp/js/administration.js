/**
 * @author: Romain BARDINET
 */

window.addEventListener("load", function(){

    document.getElementById("byAll").addEventListener("click",checkboxHandler("byAll"));

    function checkboxHandler(checkboxname){
        if (checkboxname.equals("byAll")){
            document.getElementById("byMonth").checked = !document.getElementById("byAll").checked;

        }else{
            document.getElementById("byAll").checked = !document.getElementById("byMonth").checked;
        }
        console.log("checkboxHandler loaded");
    }

}