/**
 * @author: Jean-Hanna SALEH
 */
window.addEventListener("load", function(){
    waitForElement = function (elementId, callBack){
        window.setTimeout(function(){
            var element = document.getElementById(elementId);
            if(element){
                callBack(elementId, element);
            }else{
                waitForElement(elementId, callBack);
            }
        },500)
    }
})