/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

// Wait for the deviceready event before using any of Cordova's device APIs.
// See https://cordova.apache.org/docs/en/latest/cordova/events/events.html#deviceready



document.addEventListener('deviceready', onDeviceReady, false);

function onDeviceReady() {
    // Cordova is now initialized. Have fun!

    console.log('Running cordova-' + cordova.platformId + '@' + cordova.version);
    document.getElementById('deviceready').classList.add('ready');
}

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
    converToHash(password);

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
