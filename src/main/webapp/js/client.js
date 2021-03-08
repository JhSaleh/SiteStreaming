/**
 * @author: Jean-Hanna SALEH
 */
class Client{
    constructor(nom, prenom, civilite, mail, password, birthdate) {
        this.nom = nom;
        this.prenom = prenom;
        this.civilite = civilite;
        this.mail = mail;
        this.password = password;
        this.birthdate = birthdate;
    }



    printConsoleAllData = function () {
        console.log("---Client Info---")
        console.log("nom :"+this.nom);
        console.log("prenom :"+this.prenom);
        console.log("civilite :"+this.civilite);
        console.log("mail :"+this.mail);
        console.log("password :"+this.password);
        console.log("birthdate :"+this.birthdate);
    };
}