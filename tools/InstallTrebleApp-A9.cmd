

adb root
adb remount /system
adb push treble-overlay-telephony-hw-ims.apk /system/app/treble-overlay-telephony-hw-ims/
adb shell chmod 644 /system/app/treble-overlay-telephony-hw-ims/treble-overlay-telephony-hw-ims.apk 

adb push TrebleApp-A9.apk /system/priv-app/TrebleApp/TrebleApp.apk
adb shell chmod 644 /system/priv-app/TrebleApp/TrebleApp.apk

adb reboot
