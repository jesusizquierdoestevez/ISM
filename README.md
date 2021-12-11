# ISM
## Repositorio para la aplicacion de ISM
* Para crear el proyecto y editarlo :
  * cordova create ISM com.example.ISM ISM
  * cd ISM
  * cordova platform add android
* Para emularlo, con adnroid studio instalado, desde el directorio con el proyecto Phonegap
  * cordova emulate android
* Para probar y modificar con los cambios de github
  * sustituir la carpeta www y el config.xml
* Para generar la APK, desde el directorio donde empieza el proyecto de Phonegap (Cordova)
  * cordova build android 
  * phonegap build android 
  * (La apk se encuentra en plataform/android/app/build/output/apk/debug)
* CUIDADO con a version de java, la cual se debe cambiar en el fichero para asi poder generar la APK
  * plataforms/android/cordova/lib/check_reqs.js
   * se sustituye la siguiente variable 
    * const EXPECTED_JAVA_VERSION = '1.8.x'; 
   * por la que tengamos (por ejemplo EXPECTED_JAVA_VERSION = '13.0.x')
 * Plugins instalados
   * http://phonegap-plugins.com/plugins/chariotsolutions/phonegap-nfc 
   * http://phonegap-plugins.com/plugins/randdusing/bluetoothle 
   * https://github.com/mapsplugin/cordova-plugin-googlemaps
   * http://phonegap-plugins.com/plugins/rohfosho/cordovacallnumberplugin
   * https://awesomeopensource.com/project/Festify/cordova-spotify?categoryPage=29
   * https://github.com/nchutchind/cordova-plugin-streaming-media
  
  *Se puede revisar si estan instalados si aparecen dentro de la carpeta de plugins en el proyecto de phonegap*
  
