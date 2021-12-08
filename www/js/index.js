document.addEventListener('deviceready', onDeviceReady, false);
function onDeviceReady() {
    // Cordova is now initialized. Have fun!

    console.log('Running cordova-' + cordova.platformId + '@' + cordova.version);
    document.getElementById('deviceready').classList.add('ready');
}



//------------------------------------
function encenderBlueTooth()
{
    bluetoothle.enable(enableSuccess=>console.log("OK"), enableError=>alert("Bluettoth already enabled"));
}
//https://github.com/randdusing/cordova-plugin-bluetoothle#enable

//OJO
//conectar o simplemente encender y que se conecte predefinido¿?¿?¿?¿?¿?¿?¿?¿?¿
function buscarConectarBlueTooth()
{
    //busca, para de buscar y conecta
    bluetoothle.startScan(startScanSuccess, startScanError, params);
    bluetoothle.stopScan(stopScanSuccess, stopScanError);
    bluetoothle.bond(bondSuccess, bondError, params);

}


//------------------------------------
function llamarNumero(num)//funcion para llamara a un numero determinado
{
    //OJO importante que tenga el +34 vergas
    //revisar onload y tal 
    var numComp="tel:"+ num;
    <a href={numComp}></a>
    
}

//------------------------------------
/*
Para este plugin he tenido que añadir al config.xml dos lineas (no se si en los demas sera necesario)
Function nfc.write writes an NdefMessage to a NFC tag.
On Android this method must be called from within an NDEF Event Handler.
 */
function escribirTag(tagId)//funcion para escribir en un tagNFC
{
    var escribir=[ndef.textRecord(tagId)];
    nfc.write(escribir, success =>alert("Success"), error =>alert("Error"));
}

function leerTag()//funcion para leer en un tagNFC
//https://stackoverflow.com/questions/36006013/nfc-reader-apache-cordova
{ //entiendo que esto solo muestra la info, adaptar para que con la info la mande al server
    app.receivedEvent('deviceready');

    // Read NDEF formatted NFC Tags
    nfc.addNdefListener (
        function (nfcEvent) {
            var tag = nfcEvent.tag,
                ndefMessage = tag.ndefMessage;

            // dump the raw json of the message
            // note: real code will need to decode
            // the payload from each record
            alert(JSON.stringify(ndefMessage));

            // assuming the first record in the message has
            // a payload that can be converted to a string.
            alert(nfc.bytesToString(ndefMessage[0].payload).substring(3));
        },
        function () { // success callback
            alert("Waiting for NDEF tag");
        },
        function (error) { // error callback
            alert("Error adding NDEF listener " + JSON.stringify(error));
        }
    );
}
//------------------------------------

function checkInformation()
{
    var email = document.getElementById('mail').value;
    var direccion = document.getElementById('direccion').value;
    var nombre = document.getElementById('name').value;
    var mailformat = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

    if ( (email== "") || (direccion=="") || (nombre==""))
    {
        window.alert('Debe rellenar todos los campos del formulario');
        return false;
    }

    if(!email.match(mailformat))
    {
        window.alert("El correo introducido no tiene el formato correcto");
        return false;
    }

    return true;
}

function converToHash(message)
{
    const msgBuffer = new TextEncoder().encode(message);                    

    const hashBuffer = await crypto.subtle.digest('SHA-256', msgBuffer);

    
}

function checkPassword()
{
    var password = document.getElementById('password').value;
    var regularExpression  = /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,16}$/;
    //converToHash(password);

    if (password.length < 8) 
    {
        window.alert("Debes escribir mas de 8 caracteres");
        return false;
    }

     if(!regularExpression.test(password)) {
        alert("La clave debe contener al menos un numero y un caracter especial");
        return false;
    }

    return true;
}

function checkInfoLogin()
{
    var email = document.getElementById('mail').value;
    var password = document.getElementById('password').value;
    var mailformat = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

    if ((email == "") || (password==""))
    {
        window.alert("Debe rellenar ambos campos para acceder");
        return false;
    }

    if(!email.match(mailformat))
    {
        window.alert("El correo introducido no tiene le formato correcto");
        return false;
    }

    return true;
}
