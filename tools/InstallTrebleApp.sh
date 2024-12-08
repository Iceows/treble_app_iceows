mv /media/iceows/Projets/iceows/treble_app_iceows/app/build/outputs/apk/release/app-release-unsigned.apk .
java -jar apksigner.jar sign  --key platform.pk8 --cert platform.x509.pem  --v4-signing-enabled false --out "TrebleApp.apk" "app-release-unsigned.apk"


adb root
adb remount
adb shell 'rm -rf /system/priv-app/TrebleApp/*'
adb push TrebleApp.apk /system/priv-app/TrebleApp/TrebleApp.apk
adb shell chmod 755 /system/priv-app/TrebleApp/TrebleApp.apk 

adb reboot

